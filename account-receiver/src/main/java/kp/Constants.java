package kp;

import java.util.Optional;
import java.util.function.Function;

import kp.mongo.entities.AccountM;

/**
 * The constants.
 */
public final class Constants {
	/**
	 * The function for shorting the id in MongoDB database. Used for logging.
	 */
	public static final Function<AccountM, String> MONGO_ID_FUN = account -> Optional.ofNullable(account)
			.map(acc -> acc.id).map(id -> String.format("id[..%s]", id.toString().substring(22))).orElse("id is null");

	/**
	 * The hidden constructor.
	 */
	private Constants() {
		throw new IllegalStateException("Utility class");
	}
}
