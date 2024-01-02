package kp.sender;

import java.util.Optional;

import kp.sender.kafka.producers.AccountProducer;
import kp.sender.utils.Utils;

/**
 * The main class for the account sender.
 */
public class Application {
	/**
	 * The hidden constructor.
	 */
	private Application() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * The entry point for the application.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {

		final String selection = Optional.ofNullable(args).filter(arr -> arr.length > 0)//
				.map(arr -> arr[0]).orElse("");
		final AccountProducer accountProducer = new AccountProducer(Utils.createConfiguration(selection));
		do {
			accountProducer.produceRecords();
		} while (Utils.sleepSeconds());
	}

}