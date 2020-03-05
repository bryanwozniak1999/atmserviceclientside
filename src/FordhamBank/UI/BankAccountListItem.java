package FordhamBank.UI;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.AccountType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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

    private static void fireWithdrawButtonClickEvent(User user, BankAccount bankAccount, Label balanceAmount, VBox donutChartContainer) {
        modal.setTitle("Withdraw");
        GridPane content = new GridPane();
        content.setPadding(new Insets(15, 15, 15, 15));
        content.setVgap(20);

        Label amountLabel = new Label("Amount: ");
        amountLabel.getStyleClass().add("pr-10");

        TextField amountTextField = new TextField();

        // Numeric only TextField
        // https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        amountTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    amountTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            withdrawFromAccount(user, bankAccount, balanceAmount, donutChartContainer, amountTextField.getText());
        });

        content.add(amountLabel, 0, 0);
        content.add(amountTextField, 1, 0);
        content.add(submitButton, 0, 1);

        initScene(content);
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

    private static void initScene(Pane pane) {
        Scene scene = new Scene(pane, 500, 400);
        modal.setScene(scene);
        modal.show();
    }

    private static void withdrawFromAccount(User user, BankAccount bankAccount, Label balanceLabel, VBox donutChartContainer, String amount) {
        bankAccount.Withdraw(Double.parseDouble(amount));
        balanceLabel.setText("$" + bankAccount.GetBalance());

        ObservableList<PieChart.Data> newPieChartData = DonutChart.createData(user);
        DonutChart newDonut = new DonutChart(newPieChartData);
        newDonut.setTitle("Total Balance: $" + user.getTotalBalance());

        donutChartContainer.getChildren().clear();
        donutChartContainer.getChildren().add(newDonut);
    }
}
