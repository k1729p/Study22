package kp.sender;

import kp.sender.models.Account;
import kp.sender.models.Money;
import kp.sender.models.Owner;
import kp.sender.models.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Contains constant values used across the application.
 */
public final class Constants {
    /**
     * The list with the characters.
     */
    public static final List<String> CHAR_LIST = List.of("abcdefghijklmnopqrstuvwxyz".split(""));
    /**
     * The limit of the batch with producer records.
     */
    public static final int BATCH_SIZE = 26;
    /**
     * The account creator function
     */
    public static final Function<String, Account> ACCOUNT_FUN = name -> new Account(
            name, 12345L, Status.ACTIVE,
            LocalDateTime.parse("2020-01-01T12:34:56"), List.of(new Owner("abc")),
            new Money(BigDecimal.valueOf(123.45),
                    Currency.getInstance(Locale.of("fr", "FR")).getCurrencyCode()));
    /**
     * The JAAS login context parameters for SASL connections.
     */
    public static final BinaryOperator<String> JAAS_LOGIN_CONTEXT_PARAMS =
            "org.apache.kafka.common.security.scram.ScramLoginModule required username='%s' password='%s';"::formatted;
    /**
     * The Kafka producer topic for the accounts.
     */
    public static final String TOPIC_PROD = "accounts";
    /**
     * The number of seconds to sleep.
     */
    public static final int SECONDS_TO_SLEEP = 20;

    /**
     * Private constructor to prevent instantiation.
     */
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}