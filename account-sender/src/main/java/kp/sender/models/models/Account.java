package kp.sender.models.models;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The account.
 *
 * @param name      the name
 * @param number    the number
 * @param status    the {@link Status}
 * @param createdAt the created at {@link LocalDateTime}
 * @param owners    the list of {@link Owner}s
 * @param money     the {@link Money}
 */
public record Account(String name, long number, Status status, LocalDateTime createdAt, List<Owner> owners,
		Money money) {
}