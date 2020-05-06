package FordhamBank.Events.BankAccountChangeEvents;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Events.IBankAccountChangeEvent;
import FordhamBank.Main;
import javafx.scene.control.Alert;

import java.util.Date;
import java.util.TimeZone;

public class WithdrawEvent implements IBankAccountChangeEvent {
    @Override
    public OperationResult fireEvent(User user, BankAccount bankAccount, String amount) {
        if (Main.connected) {
            TimeZone.setDefault(TimeZone.getTimeZone("EST"));

            var date = new Date();
            var result = bankAccount.Withdraw(Double.parseDouble(amount), date);

            if (result == OperationResult.FAIL) {
                return OperationResult.FAIL;
            }

            Main.su.sendMessage("BankTransaction>" + "WITHDRAWAL," + bankAccount.GetAccountName() + "," + bankAccount.GetBalance() + "," + amount + "," + date + "," + bankAccount.GetId());
            IBankAccountChangeEvent.updateChart(user);
            IBankAccountChangeEvent.updateBankAccountList(user);

            IBankAccountChangeEvent.updateServer(bankAccount.GetId(), bankAccount.GetBalance());

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
