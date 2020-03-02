package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.stream.Collectors;

// hell

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

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.getStylesheets().add("/FordhamBank/styles.css");
        
        // This HBox is used to have the accounts and Pie chart side by side
        HBox main = new HBox();
        
        VBox left = new VBox(), leftInner = new VBox();
        leftInner.setMinWidth(350);
        leftInner.setSpacing(10);
        ScrollPane leftScroll = new ScrollPane(leftInner);
        leftScroll.setMinWidth(360);
        leftScroll.setMaxHeight(500);
        leftScroll.setFitToWidth(true);
        
        left.setSpacing(10);
        left.setPadding(new Insets(10));

        Label name = new Label("Hello, " + user.GetFullName());
        root.getChildren().add(name);
        // bank accounts on the left
        for (BankAccount bankAccount : user.GetBankAccounts()) {
        	leftInner.getChildren().add(bankAccountListItem(bankAccount));
        }
        
        left.getChildren().add(leftScroll);
        
        Button add = new Button("Add Account");
        add.getStyleClass().add("button");
        left.getChildren().add(add);


        //donut chart on the right
        ObservableList<PieChart.Data> pieChartData = createData(user);

        DonutChart donut = new DonutChart(pieChartData);
        donut.setTitle("Total Balance: " + totalBalance(user));

        VBox right = new VBox(donut);

        // add the layouts to main
        main.getChildren().add(left);
        main.getChildren().add(right);

        root.getChildren().add(main);

        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Accounts Summary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getTypeString(AccountType type) {
    	String typeString = "";
    	
    	if (type == AccountType.CD) {
    		typeString = "CD";
    	} else if (type == AccountType.CHECKING) {
    		typeString = "Checking";
    	} else if (type == AccountType.SAVINGS) {
    		typeString = "Savings";
    	}
    	
    	return typeString;
    }

    private VBox bankAccountListItem(BankAccount bankAccount) {
        VBox container = new VBox();
        container.getStyleClass().add("bank-account-container");
        container.setPadding(new Insets(15));

        HBox infoContainer = new HBox(); // aligns the account name and account type labels.

        Label accountName = new Label(bankAccount.GetAccountName());
        Label accountType = new Label(getTypeString(bankAccount.GetAccountType()) + " - ");

        infoContainer.getChildren().addAll(accountType, accountName);

        Label balanceLabel = new Label("Available Balance");
        balanceLabel.getStyleClass().add("pt-10");
        Label balanceAmount = new Label("$" + Double.toString(bankAccount.GetBalance()));
        balanceAmount.getStyleClass().add("pb-10");

        container.getChildren().addAll(infoContainer, balanceLabel, balanceAmount);

        HBox buttonsList = new HBox();
        buttonsList.setSpacing(5);

        Button withdraw = new Button("Withdraw");
        withdraw.getStyleClass().add("button");
        Button deposit = new Button("Deposit");
        deposit.getStyleClass().add("button");
        Button transfer = new Button("Transfer");
        transfer.getStyleClass().add("button");
        Button history = new Button("History");
        history.getStyleClass().add("button");
        history.getStyleClass().add("button");
        buttonsList.getChildren().addAll(withdraw, deposit, transfer, history);
        buttonsList.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().add(buttonsList);

        return container;
    }

    private double totalBalance(User user) {
        double totalBalance = 0.0;

        for (BankAccount bankAccount : user.GetBankAccounts()) {
            totalBalance += bankAccount.GetBalance();
        }

        totalBalance = 0.01 * Math.floor(totalBalance * 100.0);
        return totalBalance;
    }

    private ObservableList<PieChart.Data> createData(User user) {
        return FXCollections.observableArrayList(user.GetBankAccounts().stream().map(bankAccount -> {
            return new PieChart.Data(bankAccount.GetAccountName(), bankAccount.GetBalance());
        }).collect(Collectors.toList()));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
