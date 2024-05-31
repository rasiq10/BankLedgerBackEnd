package dev.codescreen.service;

import java.time.LocalDateTime;

/**
 * Interface for services managing bank ledger operations.
 * This interface defines methods for loading funds into an account and authorizing transactions.
 */
public interface BankLedgerService {

    /**
     * Loads a specified amount of funds into an account at a given timestamp.
     * This method is responsible for adding funds to an account and recording the transaction.
     *
     * @param accountId The unique identifier of the account to which funds will be loaded.
     * @param amount The amount of funds to be added to the account.
     * @param timestamp The date and time at which the transaction is initiated or recorded.
     * @return The new balance of the account after the funds have been added.
     */
    double loadFunds(String accountId, double amount, LocalDateTime timestamp);

    /**
     * Authorizes a transaction to withdraw a specified amount from an account at a given timestamp.
     * This method checks if the account has sufficient funds and, if so, deducts the specified amount.
     */
    double authorizeTransaction(String accountId, double amount, LocalDateTime timestamp);
}
