package kp.sender.models.models;

import java.math.BigDecimal;

/**
 * The money.
 *
 * @param amount   the amount
 * @param currency the currency
 */
public record Money(BigDecimal amount, String currency) {
}