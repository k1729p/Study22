package kp.kafka.consumers;

import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import kp.services.AccountMongoService;

/**
 * The Kafka consumer for the payload with account in JSON format.
 */
@ApplicationScoped
public class AccountConsumer {
	@SuppressWarnings("java:S6813")
	// switch off Sonarqube rule 'Avoid field dependency injection'
	@Inject
	private Logger logger;
	@SuppressWarnings("java:S6813")
	// switch off Sonarqube rule 'Avoid field dependency injection'
	@Inject
	private AccountMongoService accountMongoService;

	/**
	 * The constructor.
	 */
	public AccountConsumer() {
		super();
	}

	/**
	 * Consumes the {@link ConsumerRecords}.<br/>
	 * Receiving the Kafka Records in batch mode.<br/>
	 * If the consumer method receives a Record,<br/>
	 * then the message is acknowledged automatically on this method return.
	 *
	 * @param consumerRecords the {@link ConsumerRecords}
	 */
	@Incoming("accounts-in")
	@Blocking
	public void consume(ConsumerRecords<String, String> consumerRecords) {

		consumerRecords.iterator()
				.forEachRemaining(cr -> Optional.of(cr)
						.filter(rec -> Objects.nonNull(rec.key()) && !rec.key().isBlank()
								&& Objects.nonNull(rec.value()) && !rec.value().isBlank())
						.ifPresent(rec -> accountMongoService.processPayload(rec.key(), rec.value())));
		if (logger.isDebugEnabled()) {
			final TreeSet<String> set = new TreeSet<>();
			consumerRecords.forEach(rec -> set.add(rec.key()));
			logger.debugf("consume(): count[%d], last key[%s]", consumerRecords.count(), set.getLast());
		}
	}
}
