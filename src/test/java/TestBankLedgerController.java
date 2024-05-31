import dev.codescreen.service.BankLedgerService;
import dev.codescreen.BankLedgerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;


/**
 * Test class for BankLedgerController using Spring Boot's testing support to load the application context.
 * Tests ensure that the controller responds as expected to HTTP requests.
 */
@SpringBootTest(classes = BankLedgerApplication.class) // Load the context of the entire application for comprehensive integration testing.
@AutoConfigureMockMvc // Automatically configure MockMvc, which offers a powerful way to quickly test MVC controllers without starting a full HTTP server.
public class TestBankLedgerController {

    @Autowired
    private MockMvc mockMvc; // Injected MockMvc instance used to send HTTP requests and assert responses.

    @MockBean
    private BankLedgerService bankLedgerService; // MockBean to simulate the behavior of the bank ledger service within the test context.

    /**
     * Tests the home endpoint by performing a GET request and asserting the response.
     * Ensures the application returns a welcoming message correctly.
     */
    @Test
    public void testHomeEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()) // Assert that the HTTP status is 200 OK.
                .andExpect(content().string("Welcome to the Bank Ledger Application!")); // Assert the response body contains the correct welcome message.
    }

    /**
     * Tests the load funds endpoint by simulating a PUT request to load funds into an account.
     * Checks if the service layer is called correctly and if the response matches expected values.
     */
    @Test
    void testLoadFunds() throws Exception {
        // Setup mock response
        when(bankLedgerService.loadFunds(eq("123"), eq(100.0), any(LocalDateTime.class))).thenReturn(200.0);

        // Perform PUT request with JSON content and assert the results
        mockMvc.perform(MockMvcRequestBuilders.put("/load")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":\"123\",\"amount\":100.0,\"currency\":\"USD\",\"transactionType\":\"load\"}"))
                .andExpect(status().isOk()) // Assert that the HTTP status is 200 OK.
                .andExpect(jsonPath("$.accountId", is("123"))) // Assert JSON response contains the correct account ID.
                .andExpect(jsonPath("$.newBalance", is(200.0))); // Assert JSON response contains the correct new balance.

        // Verify that the service method was called with expected parameters
        verify(bankLedgerService).loadFunds(eq("123"), eq(100.0), any(LocalDateTime.class));
    }

    /**
     * Tests the authorization endpoint by simulating a PUT request to authorize a transaction.
     * Asserts that the response status and content are correct based on the simulated service behavior.
     */

    @Test
    void testAuthorizeTransaction() throws Exception {
        // Arrange expectations
        String accountId = "123";
        double amount = 50.0;
        double newBalance = 150.0;
        when(bankLedgerService.authorizeTransaction(eq(accountId), eq(amount), any(LocalDateTime.class))).thenReturn(newBalance);

        // Act by performing a PUT request and assert the response matches expected results
        mockMvc.perform(MockMvcRequestBuilders.put("/authorization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":\"" + accountId + "\", \"amount\":" + amount + ", \"currency\":\"USD\", \"transactionType\":\"authorization\"}"))
                .andExpect(status().isCreated())  // Expecting HTTP 201
                .andExpect(jsonPath("$.accountId", is(accountId))) // Assert JSON response contains the correct account ID.
                .andExpect(jsonPath("$.newBalance", is(newBalance))) // Assert JSON response contains the correct new balance.
                .andExpect(jsonPath("$.timestamp").exists());  // Ensure the timestamp is included in the response.

        // Verify service interaction
        verify(bankLedgerService).authorizeTransaction(eq(accountId), eq(amount), any(LocalDateTime.class));
    }
}
