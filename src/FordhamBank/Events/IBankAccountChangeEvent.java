package FordhamBank.Events;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Factories.BankAccountListFactory;
import FordhamBank.Factories.DonutChartFactory;

public interface IBankAccountChangeEvent {
    OperationResult fireEvent(User user, BankAccount bankAccount, String amount);

    static void updateChart(User user) {
        DonutChartFactory.CreateAndDisplay(user);
    }

    static void updateBankAccountList(User user) {
        BankAccountListFactory.CreateAndDisplay(user);
    }
}
