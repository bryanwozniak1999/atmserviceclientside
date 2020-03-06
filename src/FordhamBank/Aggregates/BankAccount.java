package FordhamBank.Aggregates;

import FordhamBank.Enums.AccountType;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Enums.TransactionType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BankAccount extends Aggregate {
    private UUID UserId;
    private double Balance;
    private List<Transaction> Transactions;
    private AccountType AccountType;
    private String AccountName;

    @Override
    public String toString() {
        return this.AccountName;
    }

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
        Balance = Math.floor(Balance * 100) / 100;
        return Balance;
    }

    public void Deposit(double amount) {
        amount = Math.floor(amount * 100) / 100;

        Balance += amount;

        Balance = Math.floor(Balance * 100) / 100;

        AddTransaction(new Transaction(new Date(), amount, Balance, TransactionType.DEPOSIT));
    }

    public AccountType GetAccountType() {
        return AccountType;
    }

    public OperationResult Withdraw(double amount) {
        amount = Math.floor(amount * 100) / 100;

        double newBalance = Balance - amount;

        if (newBalance <= 0) {
            return OperationResult.FAIL;
        }

        Balance = newBalance;

        Balance = Math.floor(Balance * 100) / 100;

        AddTransaction(new Transaction(new Date(), amount, Balance, TransactionType.WITHDRAWAL));

        return OperationResult.SUCCESS;
    }

    public UUID GetUserId() {
        return UserId;
    }

    public List<Transaction> GetTransactions() {
        return Transactions;
    }

    private void AddTransaction(Transaction transaction) {
        Transactions.add(transaction);
    }
}
