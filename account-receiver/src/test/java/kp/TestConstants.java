package kp;

import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Contains constant values used across the tests.
 */
public class TestConstants {
    /**
     * The Kafka consumer topic for the accounts.
     */
    public static final String TOPIC_CONS = "accounts";
    /**
     * The Kafka consumer channel for the accounts.
     */
    public static final String CHANNEL_CONS = "accounts-in";
    /**
     * The waiting time (in milliseconds) after Kafka record send.
     */
    public static final int WAIT_AFTER_SEND = 100;
    /**
     * The test account name.
     */
    public static final String ACCOUNT_NAME = "Test Account";
    /**
     * The account number.
     */
    public static final long ACCOUNT_NUMBER = 12345L;
    /**
     * The account creation date-time.
     */
    public static final LocalDateTime ACCOUNT_CREATED_AT = LocalDateTime.parse("2020-01-01T12:34:56");
    /**
     * The money amount.
     */
    public static final BigDecimal MONEY_AMOUNT = BigDecimal.valueOf(123.45);
    /**
     * The money currency.
     */
    public static final String MONEY_CURRENCY = "TST";
    /**
     * The test owner name.
     */
    public static final String OWNER_NAME = "Test Owner";
    /**
     * The test Kafka record key.
     */
    public static final String RECORD_KEY = "test-key";
    /**
     * The test Kafka record payload.
     */
    public static final String RECORD_JSON_PAYLOAD = """
            {"name":"Test Account","number":12345,"status":"ACTIVE","createdAt":[2020,1,1,12,34,56],
            "owners":[{"name":"Test Owner"}],"money":{"amount":123.45,"currency":"TST"}}
            """;
    /**
     * Verify that the method was invoked one time and no more.
     */
    public static final VerificationMode ONCE = Mockito.times(1);

    /**
     * Private constructor to prevent instantiation.
     */
    private TestConstants() {
        throw new IllegalStateException("Utility class");
    }
}
