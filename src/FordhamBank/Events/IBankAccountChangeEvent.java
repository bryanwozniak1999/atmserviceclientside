package FordhamBank.Events;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Factories.BankAccountListFactory;
import FordhamBank.Factories.DonutChartFactory;
import FordhamBank.Main;

import java.util.UUID;

public interface IBankAccountChangeEvent {
    OperationResult fireEvent(User user, BankAccount bankAccount, String amount);

    static void updateChart(User user) {
        DonutChartFactory.CreateAndDisplay(user);
    }

    static void updateBankAccountList(User user) {
        BankAccountListFactory.CreateAndDisplay(user);
    }

    static void updateServer(UUID accountId, double balance) {
        String idString = accountId.toString();
        String balanceString = String.valueOf(balance);

        String msg = "UpdateBankAccount>" + idString + "," + balanceString;

        Main.su.sendMessage(msg);
    }
}
