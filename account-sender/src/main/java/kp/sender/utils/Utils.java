package kp.sender.utils;

import static kp.sender.Constants.CHAR_SUP;
import static kp.sender.Constants.JAAS_LOGIN_CONTEXT_PARAMS;
import static kp.sender.Constants.SECONDS_TO_SLEEP;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.ScramMechanism;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kp.sender.kafka.serializers.AccountSerializer;

/**
 * The utilities.
 */
public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	/**
	 * The hidden constructor.
	 */
	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Generates the names.
	 *
	 * @return the generated names list
	 */
	public static List<String> generateNames() {

		final List<String> nameList = new ArrayList<>();
		CHAR_SUP.get().forEach(str1 -> CHAR_SUP.get().forEach(str2 -> CHAR_SUP.get().forEach(str3 -> CHAR_SUP.get()
				.forEach(str4 -> nameList.add(String.format("%s-%s-%s-%s", str1, str2, str3, str4))))));
		return nameList;
	}

	/**
	 * Creates the producer configuration.
	 *
	 * @param selection the selection
	 * @return the producer configuration
	 */
	public static Properties createConfiguration(String selection) {

		final Properties properties = loadProperties();
		if ("without-authorization".equals(selection)) {
			return createConfigurationWithoutAuthorization(properties);
		} else {
			return createConfigurationWithAuthorization(properties);
		}
	}

	/**
	 * Sleeps the timeout.
	 *
	 * @return the flag
	 */
	public static boolean sleepSeconds() {

		try {
			TimeUnit.SECONDS.sleep(SECONDS_TO_SLEEP);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.error("sleepSeconds(): InterruptedException[{}]", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Loads the application properties from the file in jar.
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
	 * Creates the producer configuration with the authorization.<br/>
	 * <br/>
	 * JAAS : Java Authentication and Authorization Service<br/>
	 * SASL : Simple Authentication and Security Layer<br/>
	 * SCRAM : Salted Challenge Response Authentication Mechanism<br/>
	 *
	 * @param properties the properties loaded from file
	 * @return the producer configuration
	 */
	private static Properties createConfigurationWithAuthorization(Properties properties) {

		final Properties producerConfiguration = createConfigurationWithoutAuthorization(properties);
		producerConfiguration.put(SaslConfigs.SASL_MECHANISM, ScramMechanism.SCRAM_SHA_256.mechanismName());
		producerConfiguration.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name());
		producerConfiguration.put(SaslConfigs.SASL_JAAS_CONFIG, JAAS_LOGIN_CONTEXT_PARAMS
				.apply(properties.getProperty("username"), properties.getProperty("password")));
		return producerConfiguration;
	}

	/**
	 * Creates the producer configuration without the authorization.
	 *
	 * @param properties the properties loaded from file
	 * @return the producer configuration
	 */
	private static Properties createConfigurationWithoutAuthorization(Properties properties) {

		final Properties producerConfiguration = new Properties();
		producerConfiguration.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
				properties.getProperty("bootstrap.servers"));
		producerConfiguration.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerConfiguration.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AccountSerializer.class.getName());
		return producerConfiguration;
	}

}
