package kp.mongo.entities;

import java.math.BigDecimal;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

/**
 * The money in MongoDB.
 */
public class MoneyM extends PanacheMongoEntity {
	/**
	 * The constructor.
	 */
	public MoneyM() {
		super();
	}

	/**
	 * The amount
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public BigDecimal amount;
	/**
	 * The currency.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public String currency;
}