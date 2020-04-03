package FordhamBank;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import FordhamBank.Factories.BankAccountListFactory;
import FordhamBank.Factories.DonutChartFactory;
import FordhamBank.ServerUtils.socketUtils;
import FordhamBank.UI.AddBankAccountButton;
import FordhamBank.UI.HelpWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        Button helpButton = new Button();
        helpButton.setMaxHeight(50);
        helpButton.setMaxWidth(50);
        helpButton.setText("HELP");

        helpButton.setOnAction(event -> {
            HelpWindow helpWindow = new HelpWindow(primaryStage);

            helpWindow.show();
        });

        Button exitButton = new Button();
        exitButton.setText("EXIT");

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

    
    private void setAccounts(User user) {

        // if you can connect get the accounts from the file
        if (connected) {
            su.sendMessage("BankAccountsQuery>");
            String accountsAsString = su.recvMessage();
            String accountsAsList[] = accountsAsString.split("\\>");

            for (var account: accountsAsList) {
                String args[] = account.split(",");
                user.AddBankAccount(new BankAccount(user.GetId(), args[0], AccountType.valueOf(args[1])));
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
