package kp.sender.models;

import java.math.BigDecimal;

/**
 * Represents the money in the account.
 *
 * @param amount   the money amount
 * @param currency the money currency
 */
public record Money(BigDecimal amount, String currency) {
}