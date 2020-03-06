package FordhamBank.Factories;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Main;
import FordhamBank.UI.BankAccountListItem;

public class BankAccountListFactory {
    public static void CreateAndDisplay(User user) {
        Main.bankAccountListContent.getChildren().clear();

        for (BankAccount bankAccount : user.GetBankAccounts()) {
            Main.bankAccountListContent.getChildren().add(BankAccountListItem.Create(user, bankAccount));
        }
    }
}
