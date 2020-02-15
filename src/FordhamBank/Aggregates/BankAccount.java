package FordhamBank.Aggregates;

import java.util.List;
import java.util.UUID;

public class BankAccount extends Aggregate {
    private UUID UserId;
    private double Balance;
    private List<Transaction> Transactions;

    public BankAccount(UUID userId) {
        super();

        UserId = userId;
    }

    public double GetBalance() {
        return Balance;
    }

    public void SetBalance(double balance) {
        Balance = balance;
    }

    public UUID GetUserId() {
        return UserId;
    }

    public List<Transaction> GetTransactions() {
        return Transactions;
    }

    public void AddTransaction(Transaction transaction) {
        Transactions.add(transaction);
    }
}
