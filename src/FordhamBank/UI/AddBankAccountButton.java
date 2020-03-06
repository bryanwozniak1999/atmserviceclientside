package FordhamBank.UI;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import FordhamBank.Factories.BankAccountListFactory;
import FordhamBank.Factories.DonutChartFactory;
import FordhamBank.Main;
import FordhamBank.fileIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String accountType = (String) accountTypeDropdown.getValue();
            addAccount(user, accountNameTextField.getText(), AccountType.valueOf(accountType));
        });

        content.add(accountNameLabel, 0, 0);
        content.add(accountNameTextField, 1, 0);

        content.add(accountTypeLabel, 0, 1);
        content.add(accountTypeDropdown, 1, 1);

        content.add(submitButton, 0, 2);

        initScene(content, modal);
    }

    private static void initScene(Pane pane, Stage modal) {
        Scene scene = new Scene(pane, 300, 200);
        modal.setScene(scene);
        modal.show();
    }

    private static void addAccount(User user, String accountName, AccountType accountType) {
        BankAccount newAccount = new BankAccount(user.GetId(), accountType, accountName);

        user.AddBankAccount(newAccount);
        
        fileIO FileW = new fileIO();
        FileW.wrTransactionData(user.GetFullName() + " has created a new account called " + newAccount.GetAccountName());

        BankAccountListFactory.CreateAndDisplay(user);
        DonutChartFactory.CreateAndDisplay(user);
    }
}
