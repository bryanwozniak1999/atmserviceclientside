package FordhamBank.UI;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Events.BankAccountChangeEvents.DepositEvent;
import FordhamBank.Events.BankAccountChangeEvents.WithdrawEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BankAccountListItem {

    public static VBox Create(User user, BankAccount bankAccount, VBox donutChartContainer) {
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
        Label balanceAmount = new Label("$" + bankAccount.GetBalance());
        balanceAmount.getStyleClass().add("pb-10");

        container.getChildren().addAll(infoContainer, balanceLabel, balanceAmount);

        HBox buttonsList = new HBox();
        buttonsList.setSpacing(5);

        Button withdraw = new Button("Withdraw");
        withdraw.getStyleClass().add("button");
        withdraw.setOnAction(e -> {
            fireWithdrawButtonClickEvent(user, bankAccount, balanceAmount, donutChartContainer);
        });
        Button deposit = new Button("Deposit");
        deposit.getStyleClass().add("button");
        deposit.setOnAction(e -> {
            fireDepositButtonClickEvent(user, bankAccount, balanceAmount, donutChartContainer);
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

    private static void fireWithdrawButtonClickEvent(User user, BankAccount bankAccount, Label balanceAmount, VBox donutChartContainer) {
        modal.setTitle("Withdraw");
        BankAccountChangeInputForm content = new BankAccountChangeInputForm(user, bankAccount, balanceAmount, donutChartContainer, new WithdrawEvent());

        initScene(content);
    }

    private static void fireTransferButtonClickEvent() {
        modal.setTitle("Transfer");
        modal.show();
    }

    private static void fireDepositButtonClickEvent(User user, BankAccount bankAccount, Label balanceAmount, VBox donutChartContainer) {
        modal.setTitle("Deposit");
        BankAccountChangeInputForm content = new BankAccountChangeInputForm(user, bankAccount, balanceAmount, donutChartContainer, new DepositEvent());

        initScene(content);
    }

    private static void fireHistoryButtonClickEvent() {
        modal.setTitle("History");
        modal.show();
    }

    private static void initScene(Pane pane) {
        Scene scene = new Scene(pane, 500, 400);
        modal.setScene(scene);
        modal.show();
    }
}
