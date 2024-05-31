package dev.codescreen.service.impl;

import dev.codescreen.model.TransactionEvent;
import dev.codescreen.service.BalanceService;
import dev.codescreen.service.BankLedgerService;
import dev.codescreen.service.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service implementation for handling bank ledger operations such as loading funds and authorizing transactions.
 */
@Service
public class BankLedgerServiceImpl implements BankLedgerService {
    private final EventStore eventStore; // Component used to store and retrieve transaction events.
    private final BalanceService balanceService; // Component for managing account balances.

    /**
     * Constructs a BankLedgerServiceImpl with necessary dependencies.
     * @param eventStore Service for managing event data related to transactions.
     * @param balanceService Service for maintaining and updating account balances.
     */
    @Autowired
    public BankLedgerServiceImpl(EventStore eventStore, BalanceService balanceService) {
        this.eventStore = eventStore;
        this.balanceService = balanceService;
    }

    /**
     * Loads funds into an account and logs the transaction event.
     * @param accountId The identifier of the account to which funds will be loaded.
     * @param amount The amount of funds to load.
     * @param timestamp The timestamp at which the transaction occurs.
     * @return The new balance after loading the funds.
     */
    @Override
    public double loadFunds(String accountId, double amount, LocalDateTime timestamp) {
        double currentBalance = getCurrentBalance(accountId); // Retrieve current balance.
        double newBalance = currentBalance + amount; // Calculate new balance by adding amount.
        TransactionEvent event = new TransactionEvent(accountId, amount, timestamp, newBalance, "load", true);
        eventStore.addEvent(event); // Log transaction event.
        balanceService.updateBalance(accountId, newBalance); // Update account balance.
        return newBalance;
    }

    /**
     * Authorizes a transaction by checking if the account balance is sufficient and logs the transaction event.
     * @param accountId The identifier of the account from which funds will be authorized.
     * @param amount The amount of funds to authorize.
     * @param timestamp The timestamp at which the authorization is attempted.
     * @return The new balance after attempting the authorization.
     */
    @Override
    public double authorizeTransaction(String accountId, double amount, LocalDateTime timestamp) {
        double currentBalance = getCurrentBalance(accountId); // Retrieve current balance.
        boolean success = currentBalance >= amount; // Check if the balance is sufficient.
        double newBalance = success ? currentBalance - amount : currentBalance; // Calculate new balance.

        TransactionEvent event = new TransactionEvent(accountId, amount, timestamp, newBalance, "authorization", success);
        eventStore.addEvent(event); // Log transaction event, whether successful or not.
        if (success) {
            balanceService.updateBalance(accountId, newBalance); // Update balance only on successful authorization.
        }
        return newBalance;
    }

    /**
     * Calculates the current balance of an account based on successful transaction events.
     * @param accountId The identifier of the account for which the balance is calculated.
     * @return The current calculated balance of the account.
     */
    public double getCurrentBalance(String accountId) {
        // Calculate the balance by summing up the amounts of all successful transaction events.
        return eventStore.getEvents(accountId).stream()
                .filter(TransactionEvent::isSuccess)
                .mapToDouble(e -> "load".equals(e.getType()) ? e.getAmount() : -e.getAmount())
                .sum();
    }
}
