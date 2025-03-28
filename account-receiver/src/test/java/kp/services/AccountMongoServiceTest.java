package kp.services;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import kp.models.Status;
import kp.mongo.entities.AccountM;
import kp.mongo.entities.MoneyM;
import kp.mongo.entities.OwnerM;
import kp.mongo.entities.RecordKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static kp.TestConstants.*;

/**
 * The unit tests for the {@link AccountMongoService}.
 * <p>
 * It uses the MongoDB database running in Docker.
 * </p>
 */
@QuarkusTest
class AccountMongoServiceTest {
    /**
     * The service for the MongoDB.
     */
    @Inject
    AccountMongoService accountMongoService;

    /**
     * Executed after each test.
     */
    @AfterEach
    void tearDown() {
        RecordKey.delete("key", RECORD_KEY);
        AccountM.delete("name", ACCOUNT_NAME);
        MoneyM.delete("currency", MONEY_CURRENCY);
        OwnerM.delete("name", OWNER_NAME);
    }

    /**
     * Should process the Kafka record payload and create an account in MongoDB.
     */
    @Test
    @DisplayName("create account in MongoDB")
    void shouldCreateAccount() {
        // GIVEN
        // WHEN
        accountMongoService.processPayload(RECORD_KEY, RECORD_JSON_PAYLOAD);
        // THEN
        final Optional<AccountM> accountOpt = accountMongoService.findAccount(ACCOUNT_NAME);
        Assertions.assertTrue(accountOpt.isPresent(), "account not found");
        checkAccount(accountOpt.get());
    }

    /**
     * Should not create an account in MongoDB when the record key exists in MongoDB.
     */
    @Test
    @DisplayName("do not create account in MongoDB")
    void shouldNotCreateAccountWhenKeyExists() {
        // GIVEN
        final RecordKey recordKey = new RecordKey();
        recordKey.key = RECORD_KEY;
        recordKey.persist();
        // WHEN
        accountMongoService.processPayload(RECORD_KEY, RECORD_JSON_PAYLOAD);
        // THEN
        final Optional<AccountM> accountOpt = accountMongoService.findAccount(ACCOUNT_NAME);
        Assertions.assertTrue(accountOpt.isEmpty(), "account found");
    }

    /**
     * Checks the account.
     *
     * @param account the actual account
     */
    private void checkAccount(AccountM account) {

        Assertions.assertNotNull(account.id, "account id");
        Assertions.assertEquals(ACCOUNT_NAME, account.name, "account name");
        Assertions.assertEquals(ACCOUNT_NUMBER, account.number, "account number");
        Assertions.assertEquals(Status.ACTIVE, account.status, "account status");
        Assertions.assertEquals(ACCOUNT_CREATED_AT, account.createdAt, "account created at");
        checkMoney(account.money);
        checkOwners(account.owners);
    }

    /**
     * Checks the money.
     *
     * @param money the actual money
     */
    private void checkMoney(MoneyM money) {

        Assertions.assertNotNull(money, "money");
        Assertions.assertNotNull(money.id, "money id");
        Assertions.assertEquals(MONEY_AMOUNT, money.amount, "money amount");
        Assertions.assertEquals(MONEY_CURRENCY, money.currency, "money currency");
    }

    /**
     * Checks the owners.
     *
     * @param ownerList the actual owners list
     */
    private void checkOwners(List<OwnerM> ownerList) {

        Assertions.assertNotNull(ownerList, "owners list");
        Assertions.assertFalse(ownerList.isEmpty(), "owners list empty");
        final OwnerM owner = ownerList.getFirst();
        Assertions.assertNotNull(owner.id, "owner id");
        Assertions.assertEquals(OWNER_NAME, owner.name, "owner name");
    }

}
