package kp.sender.models.models;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The account.
 *
 * @param name      the name
 * @param number    the number
 * @param status    the {@link Status}
 * @param createdAt the created at date-time
 * @param owners    the list of owners
 * @param money     the money
 */
public record Account(String name, long number, Status status, LocalDateTime createdAt, List<Owner> owners,
		Money money) {
}