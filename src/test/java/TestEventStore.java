import dev.codescreen.model.TransactionEvent;
import dev.codescreen.service.EventStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestEventStore {

    private EventStore eventStore; // The EventStore instance to be tested.
    private final String accountId = "12345"; // Sample account ID for testing.
    private final LocalDateTime timestamp = LocalDateTime.now(); // Timestamp used for events.

    /**
     * Sets up the EventStore and other prerequisites before each test.
     */
    @BeforeEach
    void setUp() {
        eventStore = new EventStore(); // Initialize a new EventStore for each test to ensure isolation.
    }

    /**
     * Tests the addition of a single event to the EventStore and verifies it is stored correctly.
     */
    @Test
    void testAddEvent() {
        TransactionEvent event = new TransactionEvent(accountId, 100.0, timestamp, 200.0, "load", true);
        eventStore.addEvent(event); // Add a single transaction event.

        List<TransactionEvent> events = eventStore.getEvents(accountId);
        assertNotNull(events, "Events list should not be null"); // Ensure the list is not null.
        assertFalse(events.isEmpty(), "Events list should not be empty"); // Ensure the list is not empty.
        assertEquals(1, events.size(), "Events list should contain one event"); // Check the list size.
        assertEquals(event, events.get(0), "Event should be the same as the one added"); // Verify the event added is the same as retrieved.
    }

    /**
     * Tests retrieval of events from an account with no events added.
     */
    @Test
    void testGetEventsWithNoEventsAdded() {
        List<TransactionEvent> events = eventStore.getEvents(accountId);
        assertNotNull(events, "Events list should not be null"); // Ensure the list is not null.
        assertTrue(events.isEmpty(), "Events list should be empty if no events have been added"); // Check that the list is empty.
    }

    /**
     * Tests the handling of multiple events for a single account.
     */
    @Test
    void testMultipleEventsForSingleAccount() {
        TransactionEvent firstEvent = new TransactionEvent(accountId, 50.0, timestamp, 150.0, "load", true);
        TransactionEvent secondEvent = new TransactionEvent(accountId, 30.0, timestamp, 120.0, "authorization", true);
        eventStore.addEvent(firstEvent);
        eventStore.addEvent(secondEvent); // Add multiple events.

        List<TransactionEvent> events = eventStore.getEvents(accountId);
        assertNotNull(events, "Events list should not be null"); // Ensure the list is not null.
        assertEquals(2, events.size(), "Events list should contain two events"); // Check the list size.
        assertTrue(events.contains(firstEvent) && events.contains(secondEvent), "Events list should contain both events added"); // Verify both events are in the list.
    }

    /**
     * Tests event storage when events are added for multiple different accounts.
     */
    @Test
    void testEventStorageForMultipleAccounts() {
        String anotherAccountId = "67890"; // Another sample account ID.
        TransactionEvent eventForFirstAccount = new TransactionEvent(accountId, 100.0, timestamp, 200.0, "load", true);
        TransactionEvent eventForSecondAccount = new TransactionEvent(anotherAccountId, 200.0, timestamp, 400.0, "authorization", false);
        eventStore.addEvent(eventForFirstAccount);
        eventStore.addEvent(eventForSecondAccount); // Add events for two different accounts.

        assertEquals(1, eventStore.getEvents(accountId).size(), "First account should have one event"); // Verify the first account has one event.
        assertEquals(1, eventStore.getEvents(anotherAccountId).size(), "Second account should have one event"); // Verify the second account has one event.
        assertNotEquals(eventStore.getEvents(accountId).get(0), eventStore.getEvents(anotherAccountId).get(0), "Events for different accounts should be different"); // Verify events are different.
    }
}
