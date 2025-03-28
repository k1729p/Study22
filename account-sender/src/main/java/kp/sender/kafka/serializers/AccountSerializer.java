package kp.sender.kafka.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kp.sender.models.Account;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

/**
 * {@link Serializer} implementation for the {@link Account}.
 */
public class AccountSerializer implements Serializer<Account> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    /**
     * Default constructor.
     */
    public AccountSerializer() {
        // constructor is empty
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] serialize(String topic, Account account) {

        if (Objects.isNull(account)) {
            logger.error("serialize(): account to serialize is null");
            return new byte[0];
        }
        try {
            return objectMapper.writeValueAsBytes(account);
        } catch (Exception e) {
            logger.error("serialize(): exception[{}]", e.getMessage());
            return new byte[0];
        }
    }
}
