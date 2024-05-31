package dev.codescreen.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BankLedgerRequest implements Serializable {
    private String accountId;
    private double amount;
    private String currency;
    private String transactionType;  // "load" or "authorization"
    private LocalDateTime timestamp;

    // Default constructor for JSON parsing
    public BankLedgerRequest() {
    }

    /**
     * Constructor for creating a new BankLedgerRequest object.
     * This constructor initializes a new transaction request with the provided details.
     *
     * @param accountId The unique identifier of the account involved in the transaction.
     *                  This ID is used to link the transaction to a specific account.
     * @param amount The monetary amount involved in the transaction.
     *               Positive values generally represent funds being added to or reserved in the account,
     *               depending on the transaction type.
     * @param currency The currency of the transaction amount, such as USD, EUR, etc.
     *
     * @param transactionType The type of transaction, such as "load" for adding funds
     *                        or "authorization" for holding funds pending approval.
     *                        This helps in determining how the transaction should be processed.
     * @param timestamp The date and time when the transaction is being created or processed.
     *                  If null, the current date and time are used as the transaction timestamp.
     *                  This timestamp can be important for logging, auditing, and ensuring
     *                  transaction sequence in historical records.
     */
    public BankLedgerRequest(String accountId, double amount, String currency, String transactionType, LocalDateTime timestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.timestamp = (timestamp != null) ? timestamp : LocalDateTime.now();
    }

    // Getters and setters
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp = LocalDateTime.now();
    }
}




