package kp.kafka.consumers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySource;
import jakarta.inject.Inject;
import kp.kafka.KafkaTestResourceLifecycleManager;
import kp.services.AccountMongoService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.eclipse.microprofile.reactive.messaging.spi.Connector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static kp.TestConstants.*;

/**
 * The account consumer test.
 * <p>
 * This test is run without starting a Kafka broker.
 * The incoming channel (managed by the Kafka connector) is switched to 'in-memory'.
 * </p>
 */
@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
class AccountConsumerTest {
    /**
     * The 'in-memory' Kafka connector.
     */
    @Inject
    @Connector("smallrye-in-memory")
    InMemoryConnector connector;
    /**
     * The mocked service for the MongoDB.
     */
    @InjectMock
    AccountMongoService accountMongoService;

    /**
     * Should consume Kafka record with account in JSON format.
     *
     * @throws InterruptedException the exception
     */
    @Test
    @DisplayName("consume Kafka record with account")
    void shouldConsume() throws InterruptedException {
        // GIVEN
        Mockito.doNothing().when(accountMongoService).processPayload(Mockito.anyString(), Mockito.anyString());
        final ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>(TOPIC_CONS, 0, 0,
                RECORD_KEY, RECORD_JSON_PAYLOAD);
        final ConsumerRecords<String, String> consumerRecords = new ConsumerRecords<>(
                Map.of(new TopicPartition(TOPIC_CONS, 0), List.of(consumerRecord)));
        final InMemorySource<ConsumerRecords<String, String>> source = connector.source(CHANNEL_CONS);
        final ArgumentCaptor<String> keyCapture = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> payloadCapture = ArgumentCaptor.forClass(String.class);
        // WHEN
        source.send(consumerRecords);
        // THEN
        Assertions.assertFalse(new CountDownLatch(1).await(WAIT_AFTER_SEND, TimeUnit.MILLISECONDS));
        Mockito.verify(accountMongoService, ONCE).processPayload(keyCapture.capture(), payloadCapture.capture());
        Assertions.assertEquals(RECORD_KEY, keyCapture.getValue(), "Kafka record key");
        Assertions.assertEquals(RECORD_JSON_PAYLOAD, payloadCapture.getValue(), "Kafka record payload");
    }

    /**
     * Should not consume Kafka record when record key is empty.
     *
     * @throws InterruptedException the exception
     */
    @Test
    @DisplayName("do not consume Kafka record with empty record key")
    void shouldNotConsumeRecordWithEmptyRecordKey() throws InterruptedException {
        // GIVEN
        Mockito.doNothing().when(accountMongoService).processPayload(Mockito.anyString(), Mockito.anyString());
        final ConsumerRecord<String, String> consumerRecord =
                new ConsumerRecord<>(TOPIC_CONS, 0, 0, "", RECORD_JSON_PAYLOAD);
        final ConsumerRecords<String, String> consumerRecords = new ConsumerRecords<>(
                Map.of(new TopicPartition(TOPIC_CONS, 0), List.of(consumerRecord)));
        final InMemorySource<ConsumerRecords<String, String>> source = connector.source(CHANNEL_CONS);
        final ArgumentCaptor<String> keyCapture = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> payloadCapture = ArgumentCaptor.forClass(String.class);
        // WHEN
        source.send(consumerRecords);
        // THEN
        Assertions.assertFalse(new CountDownLatch(1).await(WAIT_AFTER_SEND, TimeUnit.MILLISECONDS));
        Mockito.verify(accountMongoService, Mockito.never()).processPayload(keyCapture.capture(),
                payloadCapture.capture());
    }

    /**
     * Should not consume Kafka record when payload is empty.
     *
     * @throws InterruptedException the exception
     */
    @Test
    @DisplayName("do not consume Kafka record with empty payload")
    void shouldNotConsumeRecordWithEmptyPayload() throws InterruptedException {
        // GIVEN
        Mockito.doNothing().when(accountMongoService).processPayload(Mockito.anyString(), Mockito.anyString());
        final ConsumerRecord<String, String> consumerRecord =
                new ConsumerRecord<>(TOPIC_CONS, 0, 0, RECORD_KEY, "");
        final ConsumerRecords<String, String> consumerRecords = new ConsumerRecords<>(
                Map.of(new TopicPartition(TOPIC_CONS, 0), List.of(consumerRecord)));
        final InMemorySource<ConsumerRecords<String, String>> source = connector.source(CHANNEL_CONS);
        final ArgumentCaptor<String> keyCapture = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> payloadCapture = ArgumentCaptor.forClass(String.class);
        // WHEN
        source.send(consumerRecords);
        // THEN
        Assertions.assertFalse(new CountDownLatch(1).await(WAIT_AFTER_SEND, TimeUnit.MILLISECONDS));
        Mockito.verify(accountMongoService, Mockito.never()).processPayload(keyCapture.capture(),
                payloadCapture.capture());
    }

}