package kp.resources;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import kp.models.Status;
import kp.mongo.entities.AccountM;
import kp.postgres.entities.AccountP;
import kp.services.AccountMongoService;
import kp.services.AccountPostgresService;
import kp.utils.TestUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.*;
import static kp.TestConstants.*;

/**
 * The unit tests for the {@link AccountResource}.
 * <p>
 * It uses the mocked services:
 * </p>
 * <ul>
 * <li>{@link AccountMongoService}</li>
 * <li>{@link AccountPostgresService}</li>
 * </ul>
 */
@QuarkusTest
class AccountResourceTest {
    private static final boolean VERBOSE = false;
    /**
     * The mocked service for the MongoDB.
     */
    @InjectMock
    AccountMongoService accountMongoService;
    /**
     * The mocked service for the PostgreSQL.
     */
    @InjectMock
    AccountPostgresService accountPostgresService;

    /**
     * Should read account from PostgreSQL database.
     */
    @Test
    @DisplayName("read account from postgres")
    void shouldReadAccountFromPostgres() {
        // GIVEN
        final AccountP accountP = TestUtils.preparePostgresAccount();
        final AccountM accountM = TestUtils.prepareMongoAccount();
        Mockito.when(accountPostgresService.findAccount(ACCOUNT_NAME)).thenReturn(Optional.of(accountP));
        Mockito.when(accountMongoService.findAccount(ACCOUNT_NAME)).thenReturn(Optional.of(accountM));
        Mockito.when(accountPostgresService.createAccount(accountM)).thenReturn(Optional.of(accountP));

        final RequestSpecification requestSpecification = RestAssured.given()
                .pathParam("name", ACCOUNT_NAME);
        // WHEN
        final Response response = requestSpecification.get("/accounts/{name}");
        // THEN
        Mockito.verify(accountPostgresService, ONCE).findAccount(ACCOUNT_NAME);
        Mockito.verify(accountMongoService, Mockito.never()).findAccount(ACCOUNT_NAME);
        Mockito.verify(accountPostgresService, Mockito.never()).createAccount(accountM);
        final ValidatableResponse validatableResponse = response.then();
        validateResponse(validatableResponse);
        if (VERBOSE) {
            validatableResponse.log().body();
        }
    }

    /**
     * Should not find account in PostgreSQL database.
     * <p>
     * From the account in MongoDB database it should create
     * the new account in PostgreSQL database and put it in response.
     * </p>
     */
    @Test
    @DisplayName("create account in postgres and return it")
    void shouldCreateAccountInPostgresAndReturnIt() {
        // GIVEN
        final AccountP accountP = TestUtils.preparePostgresAccount();
        final AccountM accountM = TestUtils.prepareMongoAccount();
        Mockito.when(accountPostgresService.findAccount(ACCOUNT_NAME)).thenReturn(Optional.empty());
        Mockito.when(accountMongoService.findAccount(ACCOUNT_NAME)).thenReturn(Optional.of(accountM));
        Mockito.when(accountPostgresService.createAccount(accountM)).thenReturn(Optional.of(accountP));
        final RequestSpecification requestSpecification = RestAssured.given()
                .pathParam("name", ACCOUNT_NAME);
        // WHEN
        final Response response = requestSpecification.get("/accounts/{name}");
        // THEN
        final ValidatableResponse validatableResponse = response.then();
        validateResponse(validatableResponse);
        Mockito.verify(accountPostgresService, ONCE).findAccount(ACCOUNT_NAME);
        Mockito.verify(accountMongoService, ONCE).findAccount(ACCOUNT_NAME);
        Mockito.verify(accountPostgresService, ONCE).createAccount(accountM);
        if (VERBOSE) {
            validatableResponse.log().body();
        }
    }

    /**
     * Should not find account in both databases: PostgreSQL and MongoDB.
     */
    @Test
    @DisplayName("do not find account")
    void shouldNotFindAccount() {
        // GIVEN
        Mockito.when(accountPostgresService.findAccount(ACCOUNT_NAME)).thenReturn(Optional.empty());
        Mockito.when(accountMongoService.findAccount(ACCOUNT_NAME)).thenReturn(Optional.empty());
        final RequestSpecification requestSpecification = RestAssured.given()
                .pathParam("name", ACCOUNT_NAME);
        // WHEN
        final Response response = requestSpecification.get("/accounts/{name}");
        // THEN
        final ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(NOT_FOUND.getStatusCode());
        Mockito.verify(accountPostgresService, ONCE).findAccount(ACCOUNT_NAME);
        Mockito.verify(accountMongoService, ONCE).findAccount(ACCOUNT_NAME);
        Mockito.verify(accountPostgresService, Mockito.never()).createAccount(Mockito.any());
    }

    /**
     * Should not return account when it failed the account creation in PostgreSQL database.
     */
    @Test
    @DisplayName("do not return account when not created in postgres")
    void shouldNotReturnAccountWhenNotCreatedInPostgres() {
        // GIVEN
        final AccountM accountM = TestUtils.prepareMongoAccount();
        Mockito.when(accountPostgresService.findAccount(ACCOUNT_NAME)).thenReturn(Optional.empty());
        Mockito.when(accountMongoService.findAccount(ACCOUNT_NAME)).thenReturn(Optional.of(accountM));
        Mockito.when(accountPostgresService.createAccount(accountM)).thenReturn(Optional.empty());
        final RequestSpecification requestSpecification = RestAssured.given()
                .pathParam("name", ACCOUNT_NAME);
        // WHEN
        final Response response = requestSpecification.get("/accounts/{name}");
        // THEN
        final ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(NOT_FOUND.getStatusCode());
        Mockito.verify(accountPostgresService, ONCE).findAccount(ACCOUNT_NAME);
        Mockito.verify(accountMongoService, ONCE).findAccount(ACCOUNT_NAME);
        Mockito.verify(accountPostgresService, ONCE).createAccount(accountM);
    }

    /**
     * Should delete all accounts in both databases: PostgreSQL and MongoDB.
     */
    @Test
    @DisplayName("delete all accounts")
    void shouldDeleteAllAccounts() {
        // GIVEN
        Mockito.doNothing().when(accountPostgresService).deleteAllAccounts();
        Mockito.doNothing().when(accountMongoService).deleteAllAccounts();
        final RequestSpecification requestSpecification = RestAssured.given();
        // WHEN
        final Response response = requestSpecification.delete("/delete");
        // THEN
        Mockito.verify(accountPostgresService, ONCE).deleteAllAccounts();
        Mockito.verify(accountMongoService, ONCE).deleteAllAccounts();
        final ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(NO_CONTENT.getStatusCode());
    }

    /**
     * Validates the response.
     *
     * @param validatableResponse the {@link ValidatableResponse}
     */
    private void validateResponse(ValidatableResponse validatableResponse) {

        validatableResponse.statusCode(OK.getStatusCode());
        validatableResponse.contentType(ContentType.JSON);
        validatableResponse.body(CoreMatchers.containsString("\"id\":"),
                CoreMatchers.containsString("\"name\":\"" + ACCOUNT_NAME + "\""),
                CoreMatchers.containsString("\"number\":" + ACCOUNT_NUMBER),
                CoreMatchers.containsString("\"status\":\"" + Status.ACTIVE + "\""),
                CoreMatchers.containsString("\"createdAt\":\"" + ACCOUNT_CREATED_AT + "\""),
                CoreMatchers.containsString("\"name\":\"" + OWNER_NAME + "\""),
                CoreMatchers.containsString("\"amount\":" + MONEY_AMOUNT),
                CoreMatchers.containsString("\"currency\":\"" + MONEY_CURRENCY + "\""));
    }

}