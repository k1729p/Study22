package kp.mongo.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

/**
 * The account owner in MongoDB.
 */
public class OwnerM extends PanacheMongoEntity {
	/**
	 * The constructor.
	 */
	public OwnerM() {
		super();
	}

	/**
	 * The owner name.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public String name;
}
