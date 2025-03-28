package kp.sender.utils;

import kp.sender.kafka.serializers.AccountSerializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.ScramMechanism;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static kp.sender.Constants.JAAS_LOGIN_CONTEXT_PARAMS;
import static kp.sender.Constants.SECONDS_TO_SLEEP;

/**
 * Utility class for various helper methods.
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Private constructor to prevent instantiation.
     */
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates the producer configuration.
     *
     * @param authorizationChoice the choice of using the authorization
     * @return the producer configuration
     */
    public static Properties createConfiguration(String authorizationChoice) {

        final Properties properties = loadProperties();
        final Properties producerConfiguration = createProducerConfiguration(properties);
        if (!"without-authorization".equals(authorizationChoice)) {
            addAuthorizationToProducerConfiguration(properties, producerConfiguration);
        }
        return producerConfiguration;
    }

    /**
     * Pauses execution for a default number of seconds.
     *
     * @return the flag
     */
    public static boolean sleepSeconds() {

        try {
            TimeUnit.SECONDS.sleep(SECONDS_TO_SLEEP);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
            logger.error("sleepSeconds(): InterruptedException[{}]", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Loads the application properties from the 'application.jar' file.
     *
     * @return the {@link Properties}
     */
    private static Properties loadProperties() {

        final Properties properties = new Properties();
        try (FileSystem fileSystem = FileSystems.newFileSystem(Path.of("application.jar"));
             InputStream inputStream = Files.newInputStream(fileSystem.getPath("application.properties"));
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            properties.load(reader);
        } catch (IOException e) {
            logger.error("loadProperties(): IOException[{}]", e.getMessage());
        }
        return properties;
    }

    /**
     * Creates the producer configuration without the authorization.
     *
     * @param properties the properties loaded from file
     * @return the producer configuration
     */
    private static Properties createProducerConfiguration(Properties properties) {

        final Properties producerConfiguration = new Properties();
        producerConfiguration.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                properties.getProperty("bootstrap.servers"));
        producerConfiguration.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfiguration.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AccountSerializer.class.getName());
        return producerConfiguration;
    }

    /**
     * Adds the authorization to the producer configuration.
     * <p>
     * Used acronyms:
     * </p>
     * <ul>
     * <li>JAAS : Java Authentication and Authorization Service.</li>
     * <li>SASL : Simple Authentication and Security Layer.</li>
     * <li>SCRAM : Salted Challenge Response Authentication Mechanism.</li>
     * </ul>
     *
     * @param properties            the properties loaded from file
     * @param producerConfiguration the producer configuration
     */
    private static void addAuthorizationToProducerConfiguration(
            Properties properties, Properties producerConfiguration) {

        producerConfiguration.put(SaslConfigs.SASL_MECHANISM, ScramMechanism.SCRAM_SHA_256.mechanismName());
        producerConfiguration.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name());
        producerConfiguration.put(SaslConfigs.SASL_JAAS_CONFIG, JAAS_LOGIN_CONTEXT_PARAMS
                .apply(properties.getProperty("username"), properties.getProperty("password")));
    }

}
