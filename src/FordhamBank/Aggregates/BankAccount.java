package FordhamBank.Aggregates;

import FordhamBank.Enums.AccountType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccount extends Aggregate {
    private UUID UserId;
    private double Balance;
    private List<Transaction> Transactions;
    private AccountType AccountType;
    private String AccountName;

    public BankAccount(UUID userId, AccountType accountType, String accountName) {
        super();

        AccountType = accountType;
        AccountName = accountName;
        UserId = userId;
        Transactions = new ArrayList<>();
        Balance = 0;
    }

    public String GetAccountName() {
        return AccountName;
    }

    public double GetBalance() {
        return Balance;
    }

    public void Deposit(double amount) {
        Balance += amount;
    }

    public AccountType GetAccountType() {
        return AccountType;
    }

    public void Withdraw(double amount) {
        double newBalance = Balance - amount;

        if (newBalance <= 0) {
            //ToDo: remove this sysout
            System.out.println("Cannot have a negative balance");
            return;
        }

        Balance = newBalance;
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
