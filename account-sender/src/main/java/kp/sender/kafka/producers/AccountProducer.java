package kp.sender.kafka.producers;

import static kp.sender.Constants.ACCOUNT_FUN;
import static kp.sender.Constants.LAST_INDEX;
import static kp.sender.Constants.TOPIC_PROD;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kp.sender.models.models.Account;
import kp.sender.utils.Utils;

/**
 * The account producer.
 * <p>
 * Sends the {@link ProducerRecord}s with the {@link KafkaProducer}.
 */
public class AccountProducer {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
	private final Properties properties;
	private static final List<String> NAMES_LIST = Utils.generateNames();

	/**
	 * The constructor.
	 *
	 * @param properties the {@link Properties}
	 */
	public AccountProducer(Properties properties) {
		super();
		this.properties = properties;
	}

	/**
	 * Produces the {@link ProducerRecord}s with the {@link Account} as a payload.
	 */
	public void produceRecords() {

		try (final Producer<String, Account> producer = new KafkaProducer<>(properties)) {
			IntStream.rangeClosed(0, LAST_INDEX).boxed()//
					.map(index -> new ProducerRecord<>(//
							TOPIC_PROD, NAMES_LIST.get(index), ACCOUNT_FUN.apply(NAMES_LIST.get(index))))
					.forEach(producer::send);
		}
		if (logger.isInfoEnabled()) {
			logger.info("produceRecords(): count[{}], first[{}], last[{}]", LAST_INDEX + 1, NAMES_LIST.getFirst(),
					NAMES_LIST.get(LAST_INDEX));
		}
	}

}
