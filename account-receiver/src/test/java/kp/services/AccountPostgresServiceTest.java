package kp.services;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import kp.models.Status;
import kp.mongo.entities.AccountM;
import kp.postgres.entities.AccountP;
import kp.postgres.entities.MoneyP;
import kp.postgres.entities.OwnerP;
import kp.postgres.repositories.AccountRepository;
import kp.postgres.repositories.MoneyRepository;
import kp.postgres.repositories.OwnerRepository;
import kp.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static kp.TestConstants.*;

/**
 * The unit tests for the {@link AccountPostgresService}.
 * <p>
 * It used the PostgreSQL database running in Docker.
 * </p>
 */
@QuarkusTest
class AccountPostgresServiceTest {
    /**
     * The service for the PostgreSQL.
     */
    @Inject
    AccountPostgresService accountPostgresService;
    /**
     * The account repository.
     */
    @Inject
    AccountRepository accountRepository;
    /**
     * The money repository.
     */
    @Inject
    MoneyRepository moneyRepository;
    /**
     * The owner repository.
     */
    @Inject
    OwnerRepository ownerRepository;

    /**
     * Executed after each test.
     */
    @Transactional
    @AfterEach
    void tearDown() {
        ownerRepository.delete("name", OWNER_NAME);
        accountRepository.delete("name", ACCOUNT_NAME);
        moneyRepository.delete("currency", MONEY_CURRENCY);
    }

    /**
     * Should create account in PostgreSQL from MongoDB account entity.
     */
    @Test
    @DisplayName("create account in PostgreSQL")
    void shouldCreateAccount() {
        // GIVEN
        final AccountM accountM = TestUtils.prepareMongoAccount();
        // WHEN
        final Optional<AccountP> accountPCreatedOpt = accountPostgresService.createAccount(accountM);
        final Optional<AccountP> accountPFoundOpt = accountPostgresService.findAccount(ACCOUNT_NAME);
        // THEN
        Assertions.assertTrue(accountPCreatedOpt.isPresent(), "created account not present");
        Assertions.assertTrue(accountPFoundOpt.isPresent(), "searched account not found");
        checkAccount(accountPCreatedOpt.orElseThrow());
        checkAccount(accountPFoundOpt.orElseThrow());
    }

    /**
     * Should not create account in PostgreSQL when MongoDB account entity is absent.
     */
    @Test
    @DisplayName("do not create account in PostgreSQL when MongoDB entity is absent")
    void shouldNotCreateAccountWhenMongoEntityIsAbsent() {
        // GIVEN
        // WHEN
        final Optional<AccountP> accountPCreatedOpt = accountPostgresService.createAccount(null);
        final Optional<AccountP> accountPFoundOpt = accountPostgresService.findAccount(ACCOUNT_NAME);
        // THEN
        Assertions.assertTrue(accountPCreatedOpt.isEmpty(), "account was created");
        Assertions.assertTrue(accountPFoundOpt.isEmpty(), "account was found");
    }

    /**
     * Checks the account.
     *
     * @param account the actual account
     */
    private void checkAccount(AccountP account) {

        Assertions.assertTrue(account.getId() > 0, "account id");
        Assertions.assertEquals(ACCOUNT_NAME, account.getName(), "account name");
        Assertions.assertEquals(ACCOUNT_NUMBER, account.getNumber(), "account number");
        Assertions.assertEquals(Status.ACTIVE, account.getStatus(), "account status");
        Assertions.assertEquals(ACCOUNT_CREATED_AT, account.getCreatedAt(), "account created at");
        checkMoney(account.getMoney());
        checkOwners(account.getOwners());
    }

    /**
     * Checks the money.
     *
     * @param money the actual money
     */
    private void checkMoney(MoneyP money) {

        Assertions.assertNotNull(money, "money");
        Assertions.assertTrue(money.getId() > 0, "money");
        Assertions.assertEquals(MONEY_AMOUNT, money.getAmount(), "money amount");
        Assertions.assertEquals(MONEY_CURRENCY, money.getCurrency(), "money currency");
    }

    /**
     * Checks the owners.
     *
     * @param ownerList the actual owners list
     */
    private void checkOwners(List<OwnerP> ownerList) {

        Assertions.assertNotNull(ownerList, "owners list");
        Assertions.assertFalse(ownerList.isEmpty(), "owners list empty");
        final OwnerP owner = ownerList.getFirst();
        Assertions.assertTrue(owner.getId() > 0, "owner id");
        Assertions.assertEquals(OWNER_NAME, owner.getName(), "owner name");
    }

}
