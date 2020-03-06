package FordhamBank.Events.BankAccountChangeEvents;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Events.IBankAccountChangeEvent;

public class WithdrawEvent implements IBankAccountChangeEvent {
    @Override
    public OperationResult fireEvent(User user, BankAccount bankAccount, String amount) {
        var result = bankAccount.Withdraw(Double.parseDouble(amount));

        if (result == OperationResult.FAIL) {
            return OperationResult.FAIL;
        }

        IBankAccountChangeEvent.updateChart(user);
        IBankAccountChangeEvent.updateBankAccountList(user);

        return OperationResult.SUCCESS;
    }
}
