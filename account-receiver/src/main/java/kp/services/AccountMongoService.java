package kp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import kp.Constants;
import kp.mongo.entities.AccountM;
import kp.mongo.entities.MoneyM;
import kp.mongo.entities.OwnerM;
import kp.mongo.entities.RecordKey;
import org.jboss.logging.Logger;

import java.util.Optional;

/**
 * The service for the MongoDB database.
 * <p>
 * This service uses the 'Active Record' design pattern.
 * </p>
 * <p>
 * The Quarkus guide proposes two solutions for MongoDB:
 * </p>
 * <ol>
 * <li>using the 'Active Record' pattern</li>
 * <li>using the 'Repository' pattern</li>
 * </ol>
 */
@ApplicationScoped
public class AccountMongoService {
    private final Logger logger;
    private final ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    @Inject
    public AccountMongoService(Logger logger) {
        this.logger = logger;
    }

    /**
     * Creates the account in MongoDB database from the payload in JSON format.
     *
     * @param key     the key
     * @param payload the payload in JSON format
     */
    @SuppressWarnings("java:S3252")
    // switch off Sonarqube rule 'static base class members should not be accessed via derived types'
    public void processPayload(String key, String payload) {

        if (RecordKey.count("key", key) > 0) {
            if (logger.isDebugEnabled()) {
                logger.debugf("processPayload(): account not created because key exists, key[%s]", key);
            }
            return;
        }
        final RecordKey recordKey = new RecordKey();
        recordKey.key = key;
        recordKey.persist();
        try {
            final AccountM accountM = objectMapper.readValue(payload, AccountM.class);
            accountM.money.persist();
            accountM.owners.getFirst().persist();
            accountM.persist();
            logger.infof("processPayload(): key[%s], account %s, name[%s]",
                    key, Constants.MONGO_ID_FUN.apply(accountM), accountM.name);
        } catch (Exception e) {
            logger.errorf("processPayload(): exception[%s]", e.getMessage());
        }
    }

    /**
     * Finds the {@link AccountM}.
     *
     * @param name the {@link AccountM} name
     * @return the {@link Optional} with the {@link AccountM}
     */
    @SuppressWarnings("java:S3252")
    // switch off Sonarqube rule 'static base class members should not be accessed via derived types'
    public Optional<AccountM> findAccount(String name) {
        return Optional.ofNullable(AccountM.find("name", name)).map(PanacheQuery::firstResult);
    }

    /**
     * Deletes all accounts in MongoDB database.
     */
    @SuppressWarnings("java:S3252")
    // switch off Sonarqube rule 'static base class members should not be accessed via derived types'
    public void deleteAllAccounts() {

        AccountM.deleteAll();
        MoneyM.deleteAll();
        OwnerM.deleteAll();
        RecordKey.deleteAll();
    }

}
