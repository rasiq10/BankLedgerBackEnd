package dev.codescreen.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing balances of accounts.
 * This service handles the retrieval and updating of account balances using a thread-safe ConcurrentHashMap.
 */
@Service
public class BalanceService {
    // A ConcurrentHashMap to store account balances, ensuring thread safety.
    private ConcurrentHashMap<String, Double> balances = new ConcurrentHashMap<>();

    /**
     * Retrieves the current balance for a specified account.
     * If the account does not exist, it returns a default value of 0.0.
     *
     * @param accountId The unique identifier of the account whose balance is to be retrieved.
     * @return The current balance of the account. If no balance is found, returns 0.0.
     */
    public double getBalance(String accountId) {
        // The getOrDefault method returns the balance if the account exists, or 0.0 if not.
        return balances.getOrDefault(accountId, 0.0);
    }

    /**
     * Updates the balance for a specified account. If the account does not exist, it creates a new entry.
     * If the account already exists, it overwrites the existing balance with the new value.
     *
     * @param accountId The unique identifier of the account whose balance is to be updated.
     * @param newBalance The new balance to set for the account.
     */
    public void updateBalance(String accountId, double newBalance) {
        // The put method updates the existing balance or creates a new account entry if it does not exist.
        balances.put(accountId, newBalance);
    }
}
