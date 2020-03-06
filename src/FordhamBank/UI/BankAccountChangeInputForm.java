package FordhamBank.UI;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Events.BankAccountChangeEvents.WithdrawEvent;
import FordhamBank.Events.IBankAccountChangeEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BankAccountChangeInputForm extends GridPane {
    public BankAccountChangeInputForm(User user, BankAccount bankAccount, IBankAccountChangeEvent submitEvent) {
        this.setPadding(new Insets(15, 15, 15, 15));
        this.setVgap(20);

        Label resultLabel = new Label();

        Label amountLabel = new Label("Amount: ");
        amountLabel.getStyleClass().add("pr-10");

        TextField amountTextField = new NumericTextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                OperationResult result = submitEvent.fireEvent(user, bankAccount, amountTextField.getText());

                if (result == OperationResult.FAIL) {
                    resultLabel.setTextFill(Color.RED);
                    resultLabel.setText("ERROR: This transaction puts balance of " + bankAccount.toString() + " below zero.");
                    resultLabel.setWrapText(true);
                } else {
                    resultLabel.setTextFill(Color.LIGHTGREEN);
                    resultLabel.setText("SUCCESS! " + bankAccount.GetAccountName() + " Balance: $" + bankAccount.GetBalance());
                }
            } catch(NumberFormatException err) {
                resultLabel.setTextFill(Color.RED);
                resultLabel.setText("ERROR: Please enter an amount.");
                resultLabel.setWrapText(true);
            }
        });

        this.add(amountLabel, 0, 0);
        this.add(amountTextField, 0, 1);
        this.add(submitButton, 0, 2);
        this.add(resultLabel, 0, 3);
    }
}
