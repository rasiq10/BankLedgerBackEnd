package dev.codescreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Bank Ledger application.
 * This class serves as the entry point for the Spring Boot application, using the @SpringBootApplication annotation.
 * The @SpringBootApplication annotation enables several important features, including component scanning, auto-configuration, and property support.
 */
@SpringBootApplication
public class BankLedgerApplication {

    /**
     * The main method that launches the Spring Boot application.
     * This method uses SpringApplication.run to bootstrap the application, starting the embedded server,
     * initializing Spring ApplicationContext, and performing class path scanning.
     *
     * @param args Command line arguments passed during the application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(BankLedgerApplication.class, args);
    }
}
