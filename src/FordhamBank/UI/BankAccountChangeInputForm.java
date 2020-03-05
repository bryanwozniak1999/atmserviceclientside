package FordhamBank.UI;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Events.BankAccountChangeEvents.WithdrawEvent;
import FordhamBank.Events.IBankAccountChangeEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class BankAccountChangeInputForm extends GridPane {
    public BankAccountChangeInputForm(User user, BankAccount bankAccount, Label balanceAmount, VBox donutChartContainer, IBankAccountChangeEvent submitEvent) {
        this.setPadding(new Insets(15, 15, 15, 15));
        this.setVgap(20);

        Label amountLabel = new Label("Amount: ");
        amountLabel.getStyleClass().add("pr-10");

        TextField amountTextField = new NumericTextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            submitEvent.fireEvent(user, bankAccount, balanceAmount, donutChartContainer, amountTextField.getText());
        });

        this.add(amountLabel, 0, 0);
        this.add(amountTextField, 1, 0);
        this.add(submitButton, 0, 1);
    }
}
