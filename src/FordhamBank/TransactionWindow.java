package FordhamBank;

import java.util.Date;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.Transaction;
import FordhamBank.Enums.TransactionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransactionWindow {
	
	private Stage window;
	
	public TransactionWindow() {
		
		initialize();
	}
	
	public void show() {
		window.show();
	}
	
	@SuppressWarnings("unchecked")
	private void initialize() {
		window = new Stage(); // second stage that includes transactions
		window.setTitle("Transactions");
		
		// creates the gui components
		
		TableView<Transaction> table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		// data (for now it is generated manually).
		ObservableList<Transaction> data = FXCollections.observableArrayList(
			new Transaction(new Date(), "Test", 500, 3000.00, TransactionType.DEPOSIT)
		);
		
		TableColumn<Transaction, Date> date = new TableColumn<>("Date");
		TableColumn<Transaction, String> desc = new TableColumn<>("Description");
		TableColumn<Transaction, Double> amount = new TableColumn<>("Amount");
		TableColumn<Transaction, Double> balance = new TableColumn<>("Balance");
		TableColumn<Transaction, TransactionType> type = new TableColumn<>("Type");
		
		// binds each column to an attribute of a Transaction object
		date.setCellValueFactory(new PropertyValueFactory<Transaction, Date>("TransactionDate"));
		desc.setCellValueFactory(new PropertyValueFactory<Transaction, String>("TransactionName"));
		amount.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("Amount"));
		balance.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("Balance"));
		type.setCellValueFactory(new PropertyValueFactory<Transaction, TransactionType>("TransactionType"));
		
		table.getColumns().addAll(date, desc, amount, balance, type);
		table.setItems(data);
		
		VBox root = new VBox();
		root.getChildren().add(table);
		
		Scene scene = new Scene(root, 600, 400);
		window.setScene(scene);
		
		
	}
}