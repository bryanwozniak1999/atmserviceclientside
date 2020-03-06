package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
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
