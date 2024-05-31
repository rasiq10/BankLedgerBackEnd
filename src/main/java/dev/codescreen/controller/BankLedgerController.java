package dev.codescreen.controller;

import dev.codescreen.dto.BankLedgerRequest;
import dev.codescreen.service.BankLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class BankLedgerController {
    // Service layer dependency for handling bank ledger operations
    private final BankLedgerService bankLedgerService;

    // Autowiring the bank ledger service through the constructor for dependency injection
    @Autowired
    public BankLedgerController(BankLedgerService bankLedgerService) {
        this.bankLedgerService = bankLedgerService;
    }

    /**
     * Endpoint to load funds into an account.
     * @return ResponseEntity with JSON containing the account ID, new balance after loading, and timestamp.
     */
    @PutMapping("/load")
    public ResponseEntity<?> loadFunds(@RequestBody BankLedgerRequest request) {
        // Capture the current timestamp when the request is made
        LocalDateTime timestamp = LocalDateTime.now();
        // Delegate to the service layer to calculate the new balance after loading funds
        double newBalance = bankLedgerService.loadFunds(request.getAccountId(), request.getAmount(), timestamp);
        // Return the updated account information in the response body
        return ResponseEntity.ok("{\"accountId\": \"" + request.getAccountId() + "\", \"newBalance\": " + newBalance + ", \"timestamp\": \"" + timestamp + "\"}");
    }

    /**
     * Endpoint to authorize a transaction, which may include deducting funds if authorization is successful.
     * @return ResponseEntity indicating success or failure (201 created or 402 payment required) with JSON body showing updated account details.
     */
    @PutMapping("/authorization")
    public ResponseEntity<?> authorizeTransaction(@RequestBody BankLedgerRequest request) {
        // Capture the current timestamp when the request is made
        LocalDateTime timestamp = LocalDateTime.now();
        // Delegate to the service layer to process the transaction and calculate the new balance
        double newBalance = bankLedgerService.authorizeTransaction(request.getAccountId(), request.getAmount(), timestamp);
        // Determine response status based on whether the transaction was authorized successfully
        return ResponseEntity.status(newBalance != -1 ? 201 : 402).body("{\"accountId\": \"" + request.getAccountId() + "\", \"newBalance\": " + newBalance + ", \"timestamp\": \"" + timestamp + "\"}");
    }

    /**
     * Home endpoint to check if the application is running.
     * @return ResponseEntity with a message.
     */
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Bank Ledger Application!");
    }

}



