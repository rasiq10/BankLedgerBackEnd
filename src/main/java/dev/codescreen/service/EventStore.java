package dev.codescreen.service;

import dev.codescreen.model.TransactionEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A component responsible for storing and retrieving transaction events.
 * Uses a ConcurrentHashMap to maintain a thread-safe collection of events grouped by account IDs.
 */
@Component
public class EventStore {
    // Logger for logging information about the operations performed by the EventStore.
    private static final Logger logger = LoggerFactory.getLogger(EventStore.class);

    // A ConcurrentHashMap to hold lists of transaction events keyed by account ID.
    private final ConcurrentHashMap<String, List<TransactionEvent>> store = new ConcurrentHashMap<>();

    /**
     * Adds a transaction event to the store.
     * If no list exists for the given account ID, it creates a new list and adds the event to it.
     *
     * @param event The transaction event to add to the store.
     */
    public void addEvent(TransactionEvent event) {
        // The computeIfAbsent method ensures that a list is present for the given account ID
        // and adds the event to this list. If no list exists, it creates one and adds the event.
        store.computeIfAbsent(event.getAccountId(), k -> new ArrayList<>()).add(event);
        // Log the addition of a new event for traceability.
        logger.info("Event added: {}", event);
    }

    /**
     * Retrieves a list of transaction events for a specific account ID.
     * If no events are found for the account, it returns an empty list.
     *
     * @param accountId The account ID for which events are to be retrieved.
     * @return A list of transaction events associated with the given account ID.
     */
    public List<TransactionEvent> getEvents(String accountId) {
        // Return the list of events for the account or an empty list if no events exist.
        return store.getOrDefault(accountId, new ArrayList<>());
    }

    /**
     * Prints all events stored in the EventStore for each account.
     * This method is useful for debugging and verifying the contents of the store.
     */
    public void printAllEvents() {
        // Iterate over all account IDs in the store.
        for (String accountId : store.keySet()) {
            System.out.println("Events for account ID: " + accountId);
            List<TransactionEvent> events = store.get(accountId);
            // Print each event using the TransactionEvent's toString method.
            for (TransactionEvent event : events) {
                System.out.println(event);
            }
        }
    }
}
