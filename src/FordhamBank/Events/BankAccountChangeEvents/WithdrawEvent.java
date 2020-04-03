package FordhamBank.Events.BankAccountChangeEvents;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Events.IBankAccountChangeEvent;
import FordhamBank.Main;

public class WithdrawEvent implements IBankAccountChangeEvent {
    @Override
    public OperationResult fireEvent(User user, BankAccount bankAccount, String amount) {
        var result = bankAccount.Withdraw(Double.parseDouble(amount));

        if (result == OperationResult.FAIL) {
            return OperationResult.FAIL;
        }
    
        Main.su.sendMessage("Withdraw>" + bankAccount.GetId() + "," + amount);
        IBankAccountChangeEvent.updateChart(user);
        IBankAccountChangeEvent.updateBankAccountList(user);

        return OperationResult.SUCCESS;
    }
}
