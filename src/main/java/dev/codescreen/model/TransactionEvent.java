package dev.codescreen.model;

import java.time.LocalDateTime;

public class TransactionEvent {
    private String accountId;
    private double amount;
    private String type; // "load" or "authorization"
    private LocalDateTime timestamp;
    private boolean success;
    private double newBalance;

    /**
     * Constructor for creating a new TransactionEvent.
     * This constructor initializes an event associated with a transaction on an account,
     * capturing all relevant details about the transaction.
     *
     * @param accountId The unique identifier of the account involved in the transaction.
     *                  This ID is essential for linking the event to a specific account.
     * @param amount The monetary amount involved in the transaction. This could be positive or negative,
     *               depending on the type of transaction (e.g., a deposit or withdrawal).
     * @param timestamp The exact date and time when the transaction occurred or was recorded.
     *                  If null, the current date and time are automatically assigned.
     *                  This is crucial for maintaining an accurate and chronological record of transactions.
     * @param newBalance The new balance of the account after the transaction has been processed.
     *                   This helps in quickly referencing the account status without recalculating the balance.
     * @param type The type of transaction, such as "deposit", "withdrawal", or "fee".
     *             This categorizes the event for easier management and reporting.
     * @param success Indicates whether the transaction was successful. A true/false value that
     *                helps in filtering and managing successful versus failed transactions.
     */
    public TransactionEvent(String accountId, double amount, LocalDateTime timestamp, double newBalance, String type, boolean success) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.success = success;
        this.newBalance = newBalance;
        this.timestamp = (timestamp != null) ? timestamp : LocalDateTime.now(); // Set timestamp at the time of event creation
    }

    // Getters and setters
    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTimeStamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", success=" + success +
                ", newBalance=" + newBalance +
                '}';
    }
}

