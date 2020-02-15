package FordhamBank.Aggregates;

import java.util.Date;

public class Transaction extends Aggregate {
    private Date TransactionDate;
    private String TransactionName;
    private double Amount;

    public Transaction(Date transactionDate, String transactionName, double amount) {
        super();

        TransactionDate = transactionDate;
        TransactionName = transactionName;
        Amount = amount;
    }

    public Date GetTransactionDate() {
        return TransactionDate;
    }

    public String GetTransactionName() {
        return TransactionName;
    }

    public double GetAmount() {
        return Amount;
    }
}
