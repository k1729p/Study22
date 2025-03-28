package kp.sender.kafka.producers;

import kp.sender.models.Account;
import kp.sender.utils.Utils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Properties;

import static kp.sender.Constants.*;

/**
 * {@link Account} producer.
 * <p>
 * Sends the {@link ProducerRecord}s using the {@link KafkaProducer}.
 * </p>
 */
public class AccountProducer {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final Properties properties;
    private static final List<String> accountNamesList = CHAR_LIST.stream().flatMap(
            str1 -> CHAR_LIST.stream().flatMap(
                    str2 -> CHAR_LIST.stream().flatMap(
                            str3 -> CHAR_LIST.stream().map(
                                    str4 -> "%s-%s-%s-%s".formatted(str1, str2, str3, str4))))).toList();
    private static final List<ProducerRecord<String, Account>> producerRecordsList = accountNamesList.stream()
            .limit(BATCH_SIZE)
            .map(name -> new ProducerRecord<>(TOPIC_PROD, name, ACCOUNT_FUN.apply(name))).toList();


    /**
     * Parameterized constructor.
     *
     * @param authorizationChoice the choice of using the authorization
     */
    public AccountProducer(String authorizationChoice) {
        this.properties = Utils.createConfiguration(authorizationChoice);
    }

    /**
     * Produces the {@link ProducerRecord}s with the {@link Account} as a payload.
     */
    public void produceRecords() {

        try (final Producer<String, Account> producer = new KafkaProducer<>(properties)) {
            producerRecordsList.forEach(producer::send);
        }
        if (logger.isInfoEnabled()) {
            logger.info("produceRecords(): count[{}], first[{}], last[{}]", producerRecordsList.size(),
                    accountNamesList.getFirst(), accountNamesList.get(producerRecordsList.size() - 1));
        }
    }
}
