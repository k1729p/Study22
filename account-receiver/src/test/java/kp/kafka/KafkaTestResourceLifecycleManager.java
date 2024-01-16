package kp.kafka;

import static kp.TestConstants.CHANNEL_CONS;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;

/**
 * Kafka test resource lifecycle manager.
 */
public class KafkaTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {
	/**
	 * The constructor.
	 */
	public KafkaTestResourceLifecycleManager() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> start() {
		return new HashMap<>(InMemoryConnector.switchIncomingChannelsToInMemory(CHANNEL_CONS));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		InMemoryConnector.clear();
	}
}