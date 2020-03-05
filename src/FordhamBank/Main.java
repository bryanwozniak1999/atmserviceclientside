package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
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
        root.getStylesheets().add("/FordhamBank/Styles/styles.css");
        
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
        	bankAccountListContent.getChildren().add(BankAccountListItem.Create(user, bankAccount, donutChartContainer));
        }
        
        bankAccountListContainer.getChildren().add(leftScroll);

        Button add = AddBankAccountButton.Create(user, bankAccountListContent, donutChartContainer);

        bankAccountListContainer.getChildren().add(add);

        //donut chart on the right
        ObservableList<PieChart.Data> pieChartData = DonutChart.createData(user);

        DonutChart donut = new DonutChart(pieChartData);
        donut.setTitle("Total Balance: $" + user.getTotalBalance());

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
    
    public static void main(String[] args) {
        launch(args);
    }
}
