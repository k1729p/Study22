package kp.kafka;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;

import java.util.HashMap;
import java.util.Map;

import static kp.TestConstants.CHANNEL_CONS;

/**
 * Kafka test resource lifecycle manager.
 */
public class KafkaTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

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