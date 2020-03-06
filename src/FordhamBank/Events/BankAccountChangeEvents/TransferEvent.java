package FordhamBank.Events.BankAccountChangeEvents;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Events.IBankAccountChangeEvent;

public class TransferEvent {
    public OperationResult fireEvent(User user, BankAccount transferFrom, BankAccount transferTo, String amount) {
        OperationResult result = new WithdrawEvent().fireEvent(user, transferFrom, amount);

        if (result == OperationResult.FAIL) {
            return OperationResult.FAIL;
        }

        result = new DepositEvent().fireEvent(user, transferTo, amount);

        IBankAccountChangeEvent.updateBankAccountList(user);
        IBankAccountChangeEvent.updateChart(user);

        return result;
    }
}
