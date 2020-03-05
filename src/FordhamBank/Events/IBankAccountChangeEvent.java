package FordhamBank.Events;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public interface IBankAccountChangeEvent {
    void fireEvent(User user, BankAccount bankAccount, Label balanceLabel, VBox donutChartContainer, String amount);
}
