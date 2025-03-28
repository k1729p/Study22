package kp;

import kp.mongo.entities.AccountM;

import java.util.Optional;
import java.util.function.Function;

/**
 * Contains constant values used across the application.
 */
public final class Constants {
    /**
     * Helper function for shortening the id in the MongoDB database. It is used for logging.
     */
    public static final Function<AccountM, String> MONGO_ID_FUN = account -> Optional.ofNullable(account)
            .map(acc -> acc.id)
            .map(id -> "id[..%s]".formatted(id.toString().substring(22)))
            .orElse("id is null");

    /**
     * Private constructor to prevent instantiation.
     */
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
