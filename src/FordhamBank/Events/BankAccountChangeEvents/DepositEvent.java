package FordhamBank.Events.BankAccountChangeEvents;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Events.IBankAccountChangeEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DepositEvent implements IBankAccountChangeEvent {
    @Override
    public OperationResult fireEvent(User user, BankAccount bankAccount, String amount) {
        bankAccount.Deposit(Double.parseDouble(amount));

        IBankAccountChangeEvent.updateBankAccountList(user);
        IBankAccountChangeEvent.updateChart(user);

        return OperationResult.SUCCESS;
    }
}
