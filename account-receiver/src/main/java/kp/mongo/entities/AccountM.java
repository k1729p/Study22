package kp.mongo.entities;

import java.time.LocalDateTime;
import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import kp.models.Status;

/**
 * The account in MongoDB.
 */
public class AccountM extends PanacheMongoEntity {
	/**
	 * The constructor.
	 */

	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public AccountM() {
		super();
	}

	/**
	 * The account name.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public String name;
	/**
	 * The account number.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public long number;
	/**
	 * The account status.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public Status status;
	/**
	 * The date-time of the account creation.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public LocalDateTime createdAt;
	/**
	 * The list of account owners.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public List<OwnerM> owners;
	/**
	 * The account money.
	 */
	@SuppressWarnings("java:S1104")
	// switch off Sonarqube rule 'Class variable fields should not have public
	// accessibility'
	public MoneyM money;
}
