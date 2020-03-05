package FordhamBank.Events;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Factories.DonutChartFactory;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public interface IBankAccountChangeEvent {
    void fireEvent(User user, BankAccount bankAccount, Label balanceLabel, VBox donutChartContainer, String amount);

    static void updateBalanceLabelAndChart(User user, BankAccount bankAccount, Label balanceLabel, VBox donutChartContainer) {
        balanceLabel.setText("$" + new DecimalFormat("#.##").format(bankAccount.GetBalance()));

        DonutChartFactory.CreateAndDisplay(user, donutChartContainer);
    }
}
