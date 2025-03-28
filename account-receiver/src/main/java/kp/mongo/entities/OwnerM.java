package kp.mongo.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

/**
 * Represents the account owner in MongoDB.
 */
public class OwnerM extends PanacheMongoEntity {
    /**
     * Default constructor.
     */
    public OwnerM() {
		super();
    }

    /**
     * The owner name.
     */
    @SuppressWarnings("java:S1104")
    // switch off Sonarqube rule 'Class variable fields should not have public accessibility'
    public String name;
}
