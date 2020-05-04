package FordhamBank.UI;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Factories.BankAccountListFactory;
import FordhamBank.Factories.DonutChartFactory;
import FordhamBank.Main;
import FordhamBank.ServerUtils.socketUtils;
import FordhamBank.fileIO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static FordhamBank.Main.su;

public class AddBankAccountButton {
    public static Button Create(User user) {
        Button add = new Button("Add Account");
        add.setOnAction(e -> {
            fireAddAccountButtonClickEvent(user);
        });
        add.getStyleClass().add("button");

        return add;
    }

    private static void fireAddAccountButtonClickEvent(User user) {
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

        Label resultLabel = new Label();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        String accountType = (String) accountTypeDropdown.getValue();
                        addAccount(user, accountNameTextField.getText(), AccountType.valueOf(accountType), resultLabel);
                    } catch(NullPointerException err) {
                        err.printStackTrace();
                        resultLabel.setTextFill(Color.RED);
                        resultLabel.setText("ERROR: Please enter all fields.");
                        resultLabel.setWrapText(true);
                    }
                }
            });
        });

        content.add(accountNameLabel, 0, 0);
        content.add(accountNameTextField, 1, 0);

        content.add(accountTypeLabel, 0, 1);
        content.add(accountTypeDropdown, 1, 1);

        content.add(submitButton, 0, 2);

        content.add(resultLabel, 0, 3);

        initScene(content, modal);
    }

    private static void initScene(Pane pane, Stage modal) {
        Scene scene = new Scene(pane, 400, 300);
        modal.setScene(scene);
        modal.show();
    }

    private static void addAccount(User user, String accountName, AccountType accountType, Label resultLabel) {
        if (Main.connected == true) {
            BankAccount newAccount = new BankAccount(user.GetId(), accountName, accountType);

            user.AddBankAccount(newAccount);

            String msg = "NewBankAccount>" + accountName + "," + accountType.toString() + "," + newAccount.GetId();

            su.sendMessage(msg);

            fileIO FileW = new fileIO();
            FileW.wrTransactionData(user.GetFullName() + " has created a new account called " + newAccount.GetAccountName());

            BankAccountListFactory.CreateAndDisplay(user);
            DonutChartFactory.CreateAndDisplay(user);


            resultLabel.setTextFill(Color.LIGHTGREEN);
            resultLabel.setText("Success!");
            resultLabel.setWrapText(true);
        } else {
            resultLabel.setTextFill(Color.RED);
            resultLabel.setText("Error: Unable to Connect!");
            resultLabel.setWrapText(true);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("--- Network Communications Error ---");
            alert.setHeaderText("Unable to talk to Socket Server!");

            alert.showAndWait();
        }
    }
}
