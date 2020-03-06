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

import javafx.application.Platform;
import javafx.collections.FXCollections;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;

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
        
        // help button
        try {
            String imageSource = "https://image.flaticon.com/icons/png/128/84/84042.png";
            
            Image help = new Image(imageSource);
            ImageView helpBtn = new ImageView(help);
            helpBtn.setFitWidth(35);
            helpBtn.setFitHeight(35);
            helpBtn.setImage(help);
            helpBtn.setPreserveRatio(true);
            helpBtn.setSmooth(true);
            helpBtn.setCache(true);
            Button btn = new Button();
            btn.setMaxHeight(50);
            btn.setMaxWidth(50);
            btn.setGraphic(helpBtn);
            
            
            //creates the new stage with modality 
            btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	 
                Label secondLabel = new Label("Hello! Welcome to FordhamBank the number one bank of Fordham students. \nFrom this page you can make transactions, make a withdrawal, or make a depostit.\nYour money is displayed on the right hand side and each account has an in depth account transaction list!");
     
                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);
     
                Scene secondScene = new Scene(secondaryLayout, 750, 200);
     
                // New window (Stage)
                Stage helpWindow = new Stage();
                helpWindow.setTitle("Welcome to FordhamBank!");
                helpWindow.setScene(secondScene);
     
                // Specifies the modality for new window.
                helpWindow.initModality(Modality.WINDOW_MODAL);
     
                //parent window
                helpWindow.initOwner(primaryStage);
     
                // where the second window will be
                helpWindow.setX(primaryStage.getX() + 25);
                helpWindow.setY(primaryStage.getY() + 150);
     
                helpWindow.show();
             }
          });
            
            left.getChildren().add(btn);

        } catch(Exception e) {
            e.printStackTrace();
        }
        //clock
        
        Text clock = new Text();
    	boolean runTimer = true;

        Thread timer = new Thread(() -> {
            SimpleDateFormat hms = new SimpleDateFormat("hh:mm:ss");
            while(runTimer) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
                final String time = hms.format(new Date());
                Platform.runLater(()-> {
                	clock.setText(time);
                });
            }
            
        });
        timer.start();
        
        main.getChildren().add(clock);
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
