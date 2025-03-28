package kp.sender.models;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the account.
 *
 * @param name      the account name
 * @param number    the account number
 * @param status    the account {@link Status}
 * @param createdAt the account creation time
 * @param owners    the list of the account's {@link Owner}s
 * @param money     the account {@link Money}
 */
public record Account(String name, long number, Status status,
                      LocalDateTime createdAt, List<Owner> owners, Money money) {
}