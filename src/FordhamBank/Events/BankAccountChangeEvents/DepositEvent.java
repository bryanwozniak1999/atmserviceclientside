package FordhamBank.Events.BankAccountChangeEvents;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.Enums.OperationResult;
import FordhamBank.Events.IBankAccountChangeEvent;
import FordhamBank.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DepositEvent implements IBankAccountChangeEvent {
    @Override
    public OperationResult fireEvent(User user, BankAccount bankAccount, String amount) {
    	if (Main.connected == true) {
	        bankAccount.Deposit(Double.parseDouble(amount));
	        
	        String msg = "BankTransaction>" + "Deposit," + bankAccount.GetAccountName() + "," + amount + "," + bankAccount.GetId();
	        
	        Main.su.sendMessage(msg);
	
	        IBankAccountChangeEvent.updateBankAccountList(user);
	        IBankAccountChangeEvent.updateChart(user);
	
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
