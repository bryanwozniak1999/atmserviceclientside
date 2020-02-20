package FordhamBank.Aggregates;

import java.util.ArrayList;
import java.util.List;

public class User extends Aggregate {
    private String FirstName;
    private String LastName;
    private List<BankAccount> BankAccounts;

    public User(String firstName, String lastName) {
        super();

        FirstName = firstName;
        LastName = lastName;
        BankAccounts = new ArrayList<>();
    }

    public String GetFirstName() {
        return FirstName;
    }

    public String GetLastName() {
        return LastName;
    }

    public String GetFullName() {
        return FirstName + ' ' + LastName;
    }

    public List<BankAccount> GetBankAccounts() {
        return BankAccounts;
    }

    public void AddBankAccount(BankAccount bankAccount) {
        if (bankAccount.GetUserId() != Id) {
            System.out.println("This bank account does not belong to this user");
            return;
        }

        BankAccounts.add(bankAccount);
    }
}
