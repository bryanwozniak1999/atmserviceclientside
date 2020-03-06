package FordhamBank.UI;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.Transaction;
import FordhamBank.Enums.TransactionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransactionWindow {
	
	private Stage window;
	
	public TransactionWindow(BankAccount account) {
		
		initialize(account);
	}
	
	public void show() {
		window.show();
	}
	
	@SuppressWarnings("unchecked")
	private void initialize(BankAccount account) {
		window = new Stage(); // second stage that includes transactions
		window.setTitle("Transactions");
		
		// creates the gui components
		
		VBox root = new VBox();
		root.setSpacing(5);
		root.setPadding(new Insets(10));
				
		Label name = new Label(account.GetAccountName());
		Label balanceL = new Label("Account Balance");
		Label balanceNum = new Label(Double.toString(account.GetBalance()));
		
		root.getChildren().addAll(name, balanceL, balanceNum);
		
		
		TableView<Transaction> table = new TableView<>();
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		// the following line is used to create an observableArrayList from the account list of transactions
		// this line can be removed if the transaction list variable in bank account is converted to ObservableList.
		// would only have to call table.setItems(account.GetTransactions);
		Collections.reverse(account.GetTransactions());
		ObservableList<Transaction> data = FXCollections.observableArrayList(account.GetTransactions());
		
		
		TableColumn<Transaction, Date> date = new TableColumn<>("Date");
		TableColumn<Transaction, Double> amount = new TableColumn<>("Amount");
		TableColumn<Transaction, Double> balance = new TableColumn<>("Balance");
		TableColumn<Transaction, TransactionType> type = new TableColumn<>("Type");
		
		// binds each column to an attribute of a Transaction object
		date.setCellValueFactory(new PropertyValueFactory<Transaction, Date>("TransactionDate"));
		amount.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("Amount"));
		balance.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("Balance"));
		type.setCellValueFactory(new PropertyValueFactory<Transaction, TransactionType>("TransactionType"));
		
		table.getColumns().addAll(date, amount, balance, type);
		table.getSortOrder().add(date);
		table.setItems(data);
		
		
		root.getChildren().add(table);
		
		Scene scene = new Scene(root, 600, 400);
		window.setScene(scene);
		
		
	}
}