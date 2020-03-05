package FordhamBank.Events.BankAccountChangeEvents;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Events.IBankAccountChangeEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DepositEvent implements IBankAccountChangeEvent {
    @Override
    public void fireEvent(User user, BankAccount bankAccount, Label balanceLabel, VBox donutChartContainer, String amount) {
        bankAccount.Deposit(Double.parseDouble(amount));

        IBankAccountChangeEvent.updateBalanceLabelAndChart(user, bankAccount, balanceLabel, donutChartContainer);
    }
}
