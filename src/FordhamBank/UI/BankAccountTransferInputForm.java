package FordhamBank.UI;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import FordhamBank.Events.BankAccountChangeEvents.TransferEvent;
import FordhamBank.Events.BankAccountChangeEvents.WithdrawEvent;
import FordhamBank.Events.IBankAccountChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class BankAccountTransferInputForm extends GridPane {
    public BankAccountTransferInputForm(User user, BankAccount bankAccount, TransferEvent submitEvent) {
        this.setPadding(new Insets(15, 15, 15, 15));
        this.setVgap(20);

        Label amountLabel = new Label("Amount: ");
        amountLabel.getStyleClass().add("pr-10");

        TextField amountTextField = new NumericTextField();

        Label accountLabel = new Label("Transfer To: ");

        ObservableList<BankAccount> options = FXCollections.observableArrayList(user.GetBankAccounts());
        options.remove(bankAccount);

        ComboBox accountDropDown = new ComboBox();

        accountDropDown.setItems(options);

        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            submitEvent.fireEvent(user, bankAccount,(BankAccount) accountDropDown.getValue(), amountTextField.getText());
        });

        this.add(amountLabel, 0, 0);
        this.add(amountTextField, 1, 0);
        this.add(accountLabel, 0, 1);
        this.add(accountDropDown, 1, 1);
        this.add(submitButton, 0, 2);
    }
}