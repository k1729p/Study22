package kp.utils;

import kp.models.Status;
import kp.mongo.entities.AccountM;
import kp.mongo.entities.MoneyM;
import kp.mongo.entities.OwnerM;
import kp.postgres.entities.AccountP;
import kp.postgres.entities.MoneyP;
import kp.postgres.entities.OwnerP;

import java.util.ArrayList;
import java.util.List;

import static kp.TestConstants.*;

/**
 * Utility class for various test helper methods.
 */
public class TestUtils {
    /**
     * Private constructor to prevent instantiation.
     */
    private TestUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Prepares the account used with the MongoDB database.
     *
     * @return the prepared {@link AccountM}
     */
    public static AccountM prepareMongoAccount() {

        final AccountM accountM = new AccountM();
        accountM.name = ACCOUNT_NAME;
        accountM.number = ACCOUNT_NUMBER;
        accountM.status = Status.ACTIVE;
        accountM.createdAt = ACCOUNT_CREATED_AT;

        accountM.owners = new ArrayList<>();
        final OwnerM owner = new OwnerM();
        owner.name = OWNER_NAME;
        accountM.owners.add(owner);

        final MoneyM money = new MoneyM();
        money.amount = MONEY_AMOUNT;
        money.currency = MONEY_CURRENCY;
        accountM.money = money;
        return accountM;
    }

    /**
     * Prepares the account used with the PostgreSQL database.
     *
     * @return the prepared {@link AccountP}
     */
    public static AccountP preparePostgresAccount() {

        final AccountP accountP = new AccountP();
        accountP.setName(ACCOUNT_NAME);
        accountP.setNumber(ACCOUNT_NUMBER);
        accountP.setStatus(Status.ACTIVE);
        accountP.setCreatedAt(ACCOUNT_CREATED_AT);

        final OwnerP ownerP = new OwnerP();
        ownerP.setName(OWNER_NAME);
        accountP.setOwners(List.of(ownerP));

        final MoneyP moneyP = new MoneyP();
        moneyP.setAmount(MONEY_AMOUNT);
        moneyP.setCurrency(MONEY_CURRENCY);
        accountP.setMoney(moneyP);
        return accountP;
    }

}
