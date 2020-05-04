package FordhamBank.Events.BankAccountChangeEvents;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Events.IBankAccountChangeEvent;
import FordhamBank.Main;
import javafx.scene.control.Alert;

public class WithdrawEvent implements IBankAccountChangeEvent {
    @Override
    public OperationResult fireEvent(User user, BankAccount bankAccount, String amount) {
        if (Main.connected) {
            var result = bankAccount.Withdraw(Double.parseDouble(amount));

            if (result == OperationResult.FAIL) {
                return OperationResult.FAIL;
            }

            Main.su.sendMessage("Withdraw>" + bankAccount.GetAccountName() + "," + amount + "," + bankAccount.GetId());
            IBankAccountChangeEvent.updateChart(user);
            IBankAccountChangeEvent.updateBankAccountList(user);

            return OperationResult.SUCCESS;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("--- Network Communications Error ---");
            alert.setHeaderText("Unable to talk to Socket Server!");

            alert.showAndWait();

            return OperationResult.FAIL;
        }
    }
}
