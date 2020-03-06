package FordhamBank.Aggregates;

import java.util.Date;

import FordhamBank.Enums.TransactionType;

public class Transaction extends Aggregate {
    private Date TransactionDate;
    private String TransactionName;
    private double Amount;
    private double CurrentBalance;
    private TransactionType TransactionType;

    public Transaction(Date transactionDate, String transactionName, double amount, double balance, TransactionType type) {
        super();

        TransactionDate = transactionDate;
        TransactionName = transactionName;
        Amount = amount;
        CurrentBalance = balance;
        TransactionType = type;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public String getTransactionName() {
        return TransactionName;
    }

    public double getAmount() {
        return Amount;
    }
    
    public double getBalance() {
    	return CurrentBalance;
    }
    
    public TransactionType getTransactionType() {
    	return TransactionType;
    }
}
