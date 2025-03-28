package kp.kafka.consumers;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import kp.services.AccountMongoService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * Kafka consumer for the payload with account in JSON format.
 */
@ApplicationScoped
public class AccountConsumer {
    private final Logger logger;
    private final AccountMongoService accountMongoService;

    /**
     * Parameterized constructor.
     *
     * @param logger              the {@link Logger}
     * @param accountMongoService the {@link AccountMongoService}
     */
    @Inject
    public AccountConsumer(Logger logger, AccountMongoService accountMongoService) {
        this.logger = logger;
        this.accountMongoService = accountMongoService;
    }

    /**
     * Consumes the {@link ConsumerRecords}.
     * <p>
     * Receives the Kafka Records in batch mode.
     * If the consumer method receives a Kafka Record,
     * the message is acknowledged automatically on this method return.
     * </p>
     *
     * @param consumerRecords the {@link ConsumerRecords}
     */
    @Incoming("accounts-in")
    @Blocking
    public void consume(ConsumerRecords<String, String> consumerRecords) {

        final Predicate<? super ConsumerRecord<String, String>> predicate = rec ->
                Objects.nonNull(rec.key()) && !rec.key().isBlank() &&
                Objects.nonNull(rec.value()) && !rec.value().isBlank();

        consumerRecords.iterator().forEachRemaining(rec1 -> Optional.of(rec1)
                .filter(predicate).ifPresent(rec2 ->
                        accountMongoService.processPayload(rec2.key(), rec2.value())));
        if (logger.isDebugEnabled()) {
            final TreeSet<String> set = new TreeSet<>();
            consumerRecords.forEach(rec -> set.add(rec.key()));
            logger.debugf("consume(): count[%d], last key[%s]", consumerRecords.count(), set.last());
        }
    }
}
