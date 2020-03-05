package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import FordhamBank.UI.BankAccountListItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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


        VBox bankAccountListContainer = new VBox(),
                bankAccountListContent = new VBox(),
                donutChartContainer = new VBox();

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
        for (BankAccount bankAccount : user.GetBankAccounts()) {
        	bankAccountListContent.getChildren().add(BankAccountListItem.Create(bankAccount));
        }
        
        bankAccountListContainer.getChildren().add(leftScroll);
        
        Button add = new Button("Add Account");
        add.setOnAction(e -> {
            fireAddAccountButtonClickEvent(user, bankAccountListContent, donutChartContainer);
        });
        add.getStyleClass().add("button");
        bankAccountListContainer.getChildren().add(add);


        //donut chart on the right
        ObservableList<PieChart.Data> pieChartData = createData(user);

        DonutChart donut = new DonutChart(pieChartData);
        donut.setTitle("Total Balance: " + totalBalance(user));

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

    private void fireAddAccountButtonClickEvent(User user, VBox bankAccountListContent, VBox donutChartContainer) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Add Account");

        GridPane content = new GridPane();
        content.setPadding(new Insets(15, 15, 15, 15));
        content.setVgap(20);

        Label accountNameLabel = new Label("Account Name: ");
        accountNameLabel.getStyleClass().add("pr-10");

        TextField accountNameTextField = new TextField();

        Label accountTypeLabel = new Label("Account Type: ");
        accountTypeLabel.getStyleClass().add("pr-10");

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        AccountType.SAVINGS.toString(),
                        AccountType.CHECKING.toString(),
                        AccountType.CD.toString()
                );

        ComboBox accountTypeDropdown = new ComboBox(options);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String accountType = (String) accountTypeDropdown.getValue();
            addAccount(user, bankAccountListContent, donutChartContainer, accountNameTextField.getText(), AccountType.valueOf(accountType));
        });

        content.add(accountNameLabel, 0, 0);
        content.add(accountNameTextField, 1, 0);

        content.add(accountTypeLabel, 0, 1);
        content.add(accountTypeDropdown, 1, 1);

        content.add(submitButton, 0, 2);

        initScene(content, modal);
    }

    private void initScene(Pane pane, Stage modal) {
        Scene scene = new Scene(pane, 500, 400);
        modal.setScene(scene);
        modal.show();
    }

    private void addAccount(User user, VBox bankAccountListContent, VBox donutChartContainer, String accountName, AccountType accountType) {
        BankAccount newAccount = new BankAccount(user.GetId(), accountType, accountName);

        user.AddBankAccount(newAccount);
        bankAccountListContent.getChildren().add(BankAccountListItem.Create(newAccount));

        ObservableList<PieChart.Data> newPieChartData = createData(user);
        DonutChart newDonut = new DonutChart(newPieChartData);

        donutChartContainer.getChildren().clear();
        donutChartContainer.getChildren().add(newDonut);
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
