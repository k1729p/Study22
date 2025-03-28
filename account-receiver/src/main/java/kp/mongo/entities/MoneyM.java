package kp.mongo.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

import java.math.BigDecimal;

/**
 * Represents money in MongoDB.
 */
public class MoneyM extends PanacheMongoEntity {
    /**
     * Default constructor.
     */
    public MoneyM() {
		super();
    }

    /**
     * The amount.
     */
    @SuppressWarnings("java:S1104")
    // switch off Sonarqube rule 'Class variable fields should not have public accessibility'
    public BigDecimal amount;
    /**
     * The currency.
     */
    @SuppressWarnings("java:S1104")
    // switch off Sonarqube rule 'Class variable fields should not have public accessibility'
    public String currency;
}