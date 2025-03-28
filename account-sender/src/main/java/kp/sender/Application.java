package kp.sender;

import kp.sender.kafka.producers.AccountProducer;
import kp.sender.utils.Utils;

import java.util.Optional;

/**
 * The main class for the account sender.
 */
public class Application {
    /**
     * Private constructor to prevent instantiation.
     */
    private Application() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The primary entry point for launching the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        final String authorizationChoice = Optional.ofNullable(args).filter(arr -> arr.length > 0)
                .map(arr -> arr[0]).orElse("");
        final AccountProducer accountProducer = new AccountProducer(authorizationChoice);
        do {
            accountProducer.produceRecords();
        } while (Utils.sleepSeconds());
    }

}