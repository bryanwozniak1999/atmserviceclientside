package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.Transaction;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import FordhamBank.Enums.TransactionType;
import FordhamBank.Factories.BankAccountListFactory;
import FordhamBank.Factories.DonutChartFactory;
import FordhamBank.ServerUtils.socketUtils;
import FordhamBank.UI.AddBankAccountButton;
import FordhamBank.UI.HelpWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Main extends Application {
    public static VBox bankAccountListContent = new VBox();
    public static VBox donutChartContainer = new VBox();
    public static boolean connected = false;
    public static socketUtils su = new socketUtils();

    @Override
    public void start(Stage primaryStage) {
        connected = su.socketConnect();

        //data set up
        User user = selectUser();
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

        Button helpButton = new Button();
        helpButton.setMaxHeight(50);
        helpButton.setMaxWidth(50);
        helpButton.setText("HELP");
        helpButton.setTooltip(new Tooltip("Displays Help Window"));

        helpButton.setOnAction(event -> {
            HelpWindow helpWindow = new HelpWindow(primaryStage);

            helpWindow.show();
        });

        Button exitButton = new Button();
        exitButton.setText("EXIT");
        exitButton.setTooltip(new Tooltip("Exits the program."));

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        bankAccountListButtons.getChildren().addAll(helpButton, exitButton);

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
    
    private User selectUser() {
    	Stage window = new Stage();
    	//window.initStyle(StageStyle.UTILITY);
    	VBox container = new VBox();
    	container.getStylesheets().add("/FordhamBank/Styles/styles.css");
    	container.setSpacing(10);
        container.setPadding(new Insets(10));
    	
    	Label topMessage = new Label("Select a User");
    	container.getChildren().add(topMessage);
    	//User selectedUser;
    	
    	// drop down menu with users
    	// drop down of users names or ids
        String users[] = {"John Doe", "Jane Doe", "Anonymous ?"};
        
        //Label selected = new Label("Selected: " + users[0]);
        ComboBox<String> list = new ComboBox<String>(FXCollections.observableArrayList(users));
        list.getSelectionModel().selectFirst();
        list.setOnAction(e -> {
        	System.out.println("Selected: " + list.getValue());
        });
        
        // Button to select
        Button select = new Button("Select");
        select.getStyleClass().add("button");
        select.setTooltip(new Tooltip("Selects chosen user"));
        select.setOnAction(e -> {
        	window.hide();
        });
        
        container.getChildren().addAll(list, select);
        
        // window is displayed until user is chosen
        Scene scene = new Scene(container, 250, 250);
        window.setScene(scene);
        window.setTitle("Select User");
        window.showAndWait();
        
        // separates by white space 
        String[] splitStr = list.getValue().split("\\s+");
    	
		return new User(splitStr[0], splitStr[1]);
    }

    
    private void setAccounts(User user) {

        // if you can connect get the accounts from the file
        if (connected) {
            su.sendMessage("BankAccountsQuery>");
            String accountsAsString = su.recvMessage();
            String accountsAsList[] = accountsAsString.split("\\>");

            // if theres a NACK we know there are no bank accounts in the DB
            if (!accountsAsString.contains("NACK")) {
                for (var account: accountsAsList) {
                    String bankAccountArgs[] = account.split(",");

                    BankAccount accountObject = new BankAccount(user.GetId(), bankAccountArgs[0], AccountType.valueOf(bankAccountArgs[1]), Double.parseDouble(bankAccountArgs[2]),UUID.fromString(bankAccountArgs[3]));
                    su.sendMessage("TransactionsQuery>" + bankAccountArgs[3]);

                    String transactionsAsString = su.recvMessage();
                    String transactionsAsList[] = transactionsAsString.split("\\>");

                    if (!transactionsAsString.contains("NACK")) {
                        for (var transaction : transactionsAsList) {
                            String transactionArgs[] = transaction.split(",");

                            TimeZone.setDefault(TimeZone.getTimeZone("EST"));


                            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

                            Date formattedDate;

                            try {
                                formattedDate = formatter.parse(transactionArgs[4]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return;
                            }

                            Transaction transactionObject = new Transaction(formattedDate, Double.parseDouble(transactionArgs[3]), Double.parseDouble(transactionArgs[2]), TransactionType.valueOf(transactionArgs[0]));

                            accountObject.AddTransaction(transactionObject);
                        }
                    }

                    user.AddBankAccount(accountObject);
                }
            }
        } else { // get nothin
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("--- Network Communications Error ---");
            alert.setHeaderText("Unable to talk to Socket Server!");

            alert.showAndWait();
        }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
