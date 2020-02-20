package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //data set up
        User user = new User("John", "Doe");

        BankAccount account1 = new BankAccount(user.GetId(), AccountType.CHECKING, "My Checking");
        account1.Deposit(35300.33);
        BankAccount account2 = new BankAccount(user.GetId(), AccountType.CD, "My CD");
        account2.Deposit(39000);
        BankAccount account3 = new BankAccount(user.GetId(), AccountType.SAVINGS, "College Savings");
        account3.Deposit(11023);

        user.AddBankAccount(account1);
        user.AddBankAccount(account2);
        user.AddBankAccount(account3);

        System.out.println("Hello " + user.GetFullName());
        System.out.println("Bank Account List: ");
        for (BankAccount bankAccount : user.GetBankAccounts()) {
            //ToDo 1: replace these sysouts with GUI
            System.out.println(bankAccount.GetAccountName());
            System.out.println(bankAccount.GetBalance());

            //put buttons
        }

        //ToDo 2: Create Pie Chart
    }


    public static void main(String[] args) {
        launch(args);
    }
}
