import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import dev.codescreen.service.EventStore;
import dev.codescreen.service.BalanceService;
import dev.codescreen.service.impl.BankLedgerServiceImpl;
import dev.codescreen.model.TransactionEvent;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestBankLedgerServiceImpl {

    @Mock
    private EventStore eventStore;  // Mock of the EventStore to simulate database interactions for transaction events.

    @Mock
    private BalanceService balanceService;  // Mock of the BalanceService to handle operations on account balances without real database interaction.

    @InjectMocks
    private BankLedgerServiceImpl bankLedgerService;  // The service being tested, with mocked dependencies injected.

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);  // Initialize mocks and inject them before each test.
        bankLedgerService = new BankLedgerServiceImpl(eventStore, balanceService);  // Instantiate the service with mocked services.
    }

    @Test
    public void testLoadFunds() {
        // Arrange: Set up conditions for the test
        String accountId = "1";
        double amount = 100.00;
        LocalDateTime timestamp = LocalDateTime.now();
        when(balanceService.getBalance(accountId)).thenReturn(0.00);  // Assume initial balance is zero for simplicity.

        // Act: Perform the action to be tested
        bankLedgerService.loadFunds(accountId, amount, timestamp);

        // Assert: Verify the outcome is as expected
        verify(balanceService).updateBalance(accountId, 100.00); // Ensure the balance service updates the balance correctly.
    }

    @Test(expected = Exception.class)
    public void testLoadFundsWhenUpdateFails() {
        // Arrange: Prepare scenario where balance update fails
        doThrow(new RuntimeException()).when(balanceService).updateBalance(anyString(), anyDouble());

        // Act & Assert: Execute and expect an exception
        bankLedgerService.loadFunds("1", 100.00, LocalDateTime.now());
    }

    @Test
    public void testGetCurrentBalance() {
        // Arrange: Mock eventStore to return specific events
        String accountId = "123";
        when(eventStore.getEvents(accountId)).thenReturn(Arrays.asList(
                new TransactionEvent(accountId, 100.00, LocalDateTime.now(), 100, "load", true),
                new TransactionEvent(accountId, 50.00, LocalDateTime.now(), 50, "authorization", true),
                new TransactionEvent(accountId, 20.00, LocalDateTime.now(), 30, "authorization", false)  // This failed event should not affect the balance
        ));

        // Act: Retrieve current balance
        double balance = bankLedgerService.getCurrentBalance(accountId);

        // Assert: Check the balance is calculated correctly
        assertEquals(50.00, balance, 0.01);
    }

    @Test
    public void testAuthorizeTransactionSuccess() {
        // Arrange: Set up a successful transaction scenario
        String accountId = "1";
        double transactionAmount = 50.0;
        double initialBalance = 100.0;
        LocalDateTime timestamp = LocalDateTime.now();
        List<TransactionEvent> events = Arrays.asList(
                new TransactionEvent(accountId, 100.0, timestamp.minusDays(1), 100.0, "load", true)
        );

        when(eventStore.getEvents(accountId)).thenReturn(events);

        // Act: Authorize a transaction
        double result = bankLedgerService.authorizeTransaction(accountId, transactionAmount, timestamp);

        // Assert: Verify successful transaction processing
        assertEquals(50.0, result, 0.001);
        verify(balanceService).updateBalance(accountId, 50.0); // Ensure balance update occurs correctly
    }

    @Test
    public void testAuthorizeTransactionFailure() {
        // Arrange: Set up a failing transaction scenario due to insufficient funds
        String accountId = "1";
        double transactionAmount = 150.0;
        double initialBalance = 100.0;
        LocalDateTime timestamp = LocalDateTime.now();
        List<TransactionEvent> events = Arrays.asList(
                new TransactionEvent(accountId, 100.0, timestamp.minusDays(1), 100.0, "load", true)
        );

        when(eventStore.getEvents(accountId)).thenReturn(events);

        // Act: Attempt to authorize a transaction that should fail
        double result = bankLedgerService.authorizeTransaction(accountId, transactionAmount, timestamp);

        // Assert: Verify that the transaction fails as expected
        assertEquals(initialBalance, result, 0.001);
        verify(balanceService, never()).updateBalance(eq(accountId), anyDouble()); // Verify no balance update is made
        verify(eventStore).addEvent(any(TransactionEvent.class)); // Ensure the transaction is logged despite the failure
    }

}

