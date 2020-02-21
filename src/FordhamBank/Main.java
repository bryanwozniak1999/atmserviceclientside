package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
        
        // This HBox is used to have the accounts and Pie chart side by side
        HBox main = new HBox();
        
        VBox left = new VBox(), leftInner = new VBox();
        leftInner.setMinWidth(350);
        ScrollPane leftScroll = new ScrollPane(leftInner);
        leftScroll.setMinWidth(350);
        leftScroll.setMaxHeight(500);
        
        left.setSpacing(10);
        left.setPadding(new Insets(10));
        
        VBox right = new VBox(); // for the pie chart.
        
        Label name = new Label("Hello " + user.GetFullName());
        Label listLabel = new Label("Bank Account List: ");
        
        root.getChildren().addAll(name, listLabel);
        
        for (BankAccount bankAccount : user.GetBankAccounts()) {
            //ToDo 1: replace these sysouts with GUI
        	
        	VBox container = new VBox();
        	container.setStyle("-fx-border-color: black");
        	container.setPadding(new Insets(15));
        	//container.setSpacing();
        	
        	HBox infoContainer = new HBox(); // aligns the account name and account type labels.
            
        	Label accountName = new Label(bankAccount.GetAccountName());
        	Label accountType = new Label(getTypeString(bankAccount.GetAccountType()));
        	
        	infoContainer.getChildren().addAll(accountType, accountName);
        	infoContainer.setSpacing(10);
        	
        	Label balanceLabel = new Label("Available Balance");
        	Label balanceAmount = new Label("$" + Double.toString(bankAccount.GetBalance()));
        	Label yield = new Label("Annual Percentage Yield: 0.40%");
        	
        	container.getChildren().addAll(infoContainer, balanceLabel, balanceAmount, yield);
        	
        	HBox buttonsList = new HBox();
        	buttonsList.setSpacing(5);
        	
        	Button withdraw = new Button("Withdraw");
        	Button deposit = new Button("Deposit");
        	Button transfer = new Button("Transfer");
        	Button history = new Button("History");
        	buttonsList.getChildren().addAll(withdraw, deposit, transfer, history);
        	buttonsList.setPadding(new Insets(10, 0, 0, 0));
        	
        	// apply color changes
        	for (int i = 0; i < 4; i++) {
        		buttonsList.getChildren().get(i).setStyle("-fx-background-color: #800000; -fx-text-fill: white");
        	}
        	
        	//System.out.println(bankAccount.GetAccountName());
            //System.out.println(bankAccount.GetBalance());

            container.getChildren().add(buttonsList);
            leftInner.getChildren().add(container);
            
        }
        
        left.getChildren().add(leftScroll);
        
        Button add = new Button("Add Account");
        add.setStyle("-fx-background-color: #800000; -fx-text-fill: white");
        left.getChildren().add(add);
        
        main.getChildren().add(left);
        root.getChildren().add(main);
        

        //ToDo 2: Create Pie Chart
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Accounts Summary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public String getTypeString(AccountType type) {
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
    
    public static void main(String[] args) {
        launch(args);
    }
}
