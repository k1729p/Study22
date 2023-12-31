package kp.mongo.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

/**
 * The record key from Kafka message.
 */
public class RecordKey extends PanacheMongoEntity {
	/**
	 * The constructor.
	 */
	public RecordKey() {
		super();
	}

	/**
	 * The key content.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public String key;
}
