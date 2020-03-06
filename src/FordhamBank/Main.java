package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;

import FordhamBank.Factories.BankAccountListFactory;
import FordhamBank.Factories.DonutChartFactory;
import FordhamBank.UI.AddBankAccountButton;
import FordhamBank.UI.DonutChart;

import FordhamBank.UI.HelpWindow;
import javafx.application.Application;

import javafx.application.Platform;


import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.text.SimpleDateFormat;

import java.util.Date;
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

        HBox bankAccountListButtons = new HBox();
        bankAccountListButtons.setSpacing(15);

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

        bankAccountListButtons.getChildren().add(add);

        //donut chart on the right
        DonutChartFactory.CreateAndDisplay(user);

        // add the layouts to main
        main.getChildren().add(bankAccountListContainer);
        main.getChildren().add(donutChartContainer);

        root.getChildren().add(main);

        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Paint.valueOf("WHITE"));

        primaryStage.setTitle("Accounts Summary");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button btn = new Button();
        btn.setMaxHeight(50);
        btn.setMaxWidth(50);
        btn.setText("HELP");

        btn.setOnAction(event -> {
            HelpWindow helpWindow = new HelpWindow(primaryStage);

            helpWindow.show();
        });

        bankAccountListButtons.getChildren().add(btn);

        bankAccountListContainer.getChildren().add(bankAccountListButtons);

        //clock
        
        Text clock = new Text();
    	boolean runTimer = true;

        Thread timer = new Thread(() -> {
            SimpleDateFormat hms = new SimpleDateFormat("hh:mm:ss");
            while(runTimer) {
                try {
                    Thread.sleep(1000);
                    final String time = hms.format(new Date());
                    Platform.runLater(() -> {
                        clock.setText(time);
                    });
                } catch(InterruptedException e) { }
            }
        });

        timer.setDaemon(true);

        timer.start();
        
        root.getChildren().add(clock);
    }

    
    private void setAccounts(User user) {
    	BankAccount account1 = new BankAccount(user.GetId(), AccountType.CHECKING, "My Checking");
        account1.Deposit(35300.33);
        
        BankAccount account2 = new BankAccount(user.GetId(), AccountType.CD, "My CD");
        account2.Deposit(39000);
        
        BankAccount account3 = new BankAccount(user.GetId(), AccountType.SAVINGS, "College Savings");
        account3.Deposit(11023);

        user.AddBankAccount(account1);
        user.AddBankAccount(account2);
        user.AddBankAccount(account3);
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
