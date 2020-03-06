package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.Transaction;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;

import FordhamBank.Enums.TransactionType;

import FordhamBank.Factories.BankAccountListFactory;
import FordhamBank.UI.AddBankAccountButton;
import FordhamBank.UI.BankAccountListItem;
import FordhamBank.UI.DonutChart;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.text.DecimalFormat;


public class Main extends Application {
    public static VBox bankAccountListContent = new VBox();
    public static VBox donutChartContainer = new VBox();

    @Override
    public void start(Stage primaryStage) {
        //data set up
        User user = new User("John", "Doe");
        setAccounts(user);

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.getStylesheets().add("/FordhamBank/Styles/styles.css");
        
        // This HBox is used to have the accounts and Pie chart side by side
        HBox main = new HBox();


        VBox bankAccountListContainer = new VBox();

        bankAccountListContent.setMinWidth(350);
        bankAccountListContent.setSpacing(10);
        ScrollPane leftScroll = new ScrollPane(bankAccountListContent);
        leftScroll.setMinWidth(360);
        leftScroll.setMaxHeight(500);
        leftScroll.setFitToWidth(true);
        
        bankAccountListContainer.setSpacing(10);
        bankAccountListContainer.setPadding(new Insets(10));

        Label name = new Label("Hello, " + user.GetFullName());
        root.getChildren().add(name);
        // bank accounts on the left
        BankAccountListFactory.CreateAndDisplay(user);
        
        bankAccountListContainer.getChildren().add(leftScroll);

        Button add = AddBankAccountButton.Create(user);

        bankAccountListContainer.getChildren().add(add);

        //donut chart on the right
        ObservableList<PieChart.Data> pieChartData = DonutChart.createData(user);

        DonutChart donut = new DonutChart(pieChartData);

        donut.setTitle("Total Balance: $" + new DecimalFormat("#.##").format(user.getTotalBalance()));


        donutChartContainer.getChildren().add(donut);
        // add the layouts to main
        main.getChildren().add(bankAccountListContainer);
        main.getChildren().add(donutChartContainer);

        root.getChildren().add(main);

        Scene scene = new Scene(root, 800, 600);
       
        
        
        primaryStage.setTitle("Accounts Summary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private void setAccounts(User user) {
    	BankAccount account1 = new BankAccount(user.GetId(), AccountType.CHECKING, "My Checking");
        account1.Deposit(35300.33);
        	
        account1.AddTransaction(new Transaction(new Date(), "ATM 1", -200, 35100.33, TransactionType.WITHDRAWAL));
        account1.AddTransaction(new Transaction(new Date(), "Work", 500, 35600.00, TransactionType.DEPOSIT));
        account1.AddTransaction(new Transaction(new Date(), "ATM 2", -600, 35000.00, TransactionType.WITHDRAWAL));
        
        BankAccount account2 = new BankAccount(user.GetId(), AccountType.CD, "My CD");
        account2.Deposit(39000);
        
        account2.AddTransaction(new Transaction(new Date(), "Deposit A", 1000, 40000.00, TransactionType.DEPOSIT));
        account2.AddTransaction(new Transaction(new Date(), "Work", 500, 45000.00, TransactionType.DEPOSIT));
        account2.AddTransaction(new Transaction(new Date(), "To Account 3", -600, 44400.00, TransactionType.TRANSFER));
        
        BankAccount account3 = new BankAccount(user.GetId(), AccountType.SAVINGS, "College Savings");
        account3.Deposit(11023);
        
        account3.AddTransaction(new Transaction(new Date(), "Received Transfer", 600, 11623, TransactionType.TRANSFER));
        account3.AddTransaction(new Transaction(new Date(), "Refund", 1200, 12823, TransactionType.DEPOSIT));
        account3.AddTransaction(new Transaction(new Date(), "ATM Withdrawal", -50, 12773, TransactionType.WITHDRAWAL));

        user.AddBankAccount(account1);
        user.AddBankAccount(account2);
        user.AddBankAccount(account3);
    }

    private VBox bankAccountListItem(BankAccount bankAccount) {
        VBox container = new VBox();
        container.setStyle("-fx-border-color: black");
        container.setPadding(new Insets(15));

        HBox infoContainer = new HBox(); // aligns the account name and account type labels.

        Label accountName = new Label(bankAccount.GetAccountName());
        Label accountType = new Label(getTypeString(bankAccount.GetAccountType()) + " - ");

        infoContainer.getChildren().addAll(accountType, accountName);

        Label balanceLabel = new Label("Available Balance");
        balanceLabel.setStyle("-fx-padding: 10 0 0 0");
        Label balanceAmount = new Label("$" + Double.toString(bankAccount.GetBalance()));
        balanceAmount.setStyle("-fx-padding: 0 0 10 0");
        Label yield = new Label("Annual Percentage Yield: 0.40%");

        container.getChildren().addAll(infoContainer, balanceLabel, balanceAmount, yield);

        HBox buttonsList = new HBox();
        buttonsList.setSpacing(5);

        Button withdraw = new Button("Withdraw");
        withdraw.setStyle("-fx-background-color: #800000; -fx-text-fill: white");
        Button deposit = new Button("Deposit");
        deposit.setStyle("-fx-background-color: #800000; -fx-text-fill: white");
        Button transfer = new Button("Transfer");
        transfer.setStyle("-fx-background-color: #800000; -fx-text-fill: white");
        Button history = new Button("History");
        
        history.setOnAction(e -> {
        	TransactionWindow window = new TransactionWindow(bankAccount);
        	window.show();
        });
        
        history.setStyle("-fx-background-color: #800000; -fx-text-fill: white");
        buttonsList.getChildren().addAll(withdraw, deposit, transfer, history);
        buttonsList.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().add(buttonsList);

        return container;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
