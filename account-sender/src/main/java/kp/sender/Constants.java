package kp.sender;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import kp.sender.models.models.Account;
import kp.sender.models.models.Money;
import kp.sender.models.models.Owner;
import kp.sender.models.models.Status;

/**
 * The constants.
 */
public final class Constants {
	/**
	 * The JAAS login context parameters for SASL connections.
	 */
	public static final BinaryOperator<String> JAAS_LOGIN_CONTEXT_PARAMS = (username, password) -> String.format(
			"org.apache.kafka.common.security.scram.ScramLoginModule required username='%s' password='%s';", //
			username, password);
	/**
	 * The Kafka producer topic for the accounts.
	 */
	public static final String TOPIC_PROD = "accounts";
	/**
	 * The index of the last account in the produced batch.
	 */
	public static final int LAST_INDEX = 25;
	/**
	 * The number of seconds to sleep.
	 */
	public static final int SECONDS_TO_SLEEP = 20;
	/**
	 * The character supplier for the name generator.
	 */
	public static final Supplier<Stream<String>> CHAR_SUP = //
			() -> Arrays.stream("abcdefghijklmnopqrstuvwxyz".split(""));
	/**
	 * The account creator function.
	 */
	public static final Function<String, Account> ACCOUNT_FUN = name -> new Account(name, 12345L, Status.ACTIVE,
			LocalDateTime.parse("2020-01-01T12:34:56"), List.of(new Owner("abc")),
			new Money(BigDecimal.valueOf(123.45), Currency.getInstance(Locale.of("fr", "FR")).getCurrencyCode()));

	/**
	 * The hidden constructor.
	 */
	private Constants() {
		throw new IllegalStateException("Utility class");
	}
}