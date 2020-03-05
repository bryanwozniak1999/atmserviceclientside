package FordhamBank.UI;

import FordhamBank.Aggregates.BankAccount;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BankAccountListItem {

    public static VBox Create(BankAccount bankAccount) {
        modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);

        VBox container = new VBox();
        container.getStyleClass().add("bank-account-container");
        container.setPadding(new Insets(15));

        HBox infoContainer = new HBox(); // aligns the account name and account type labels.

        Label accountName = new Label(bankAccount.GetAccountName());
        Label accountType = new Label(bankAccount.GetAccountType().toString() + " - ");

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
        withdraw.setOnAction(e -> {
            fireWithdrawButtonClickEvent();
        });
        Button deposit = new Button("Deposit");
        deposit.getStyleClass().add("button");
        deposit.setOnAction(e -> {
            fireDepositButtonClickEvent();
        });
        Button transfer = new Button("Transfer");
        transfer.getStyleClass().add("button");
        transfer.setOnAction(e -> {
            fireTransferButtonClickEvent();
        });
        Button history = new Button("History");
        history.getStyleClass().add("button");
        history.setOnAction(e -> {
            fireHistoryButtonClickEvent();
        });
        buttonsList.getChildren().addAll(withdraw, deposit, transfer, history);
        buttonsList.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().add(buttonsList);

        return container;
    }

    private static Stage modal;

    private static void fireWithdrawButtonClickEvent() {
        modal.setTitle("Withdraw");
        modal.show();
    }

    private static void fireTransferButtonClickEvent() {
        modal.setTitle("Transfer");
        modal.show();
    }

    private static void fireDepositButtonClickEvent() {
        modal.setTitle("Deposit");
        modal.show();
    }

    private static void fireHistoryButtonClickEvent() {
        modal.setTitle("History");
        modal.show();
    }
}
