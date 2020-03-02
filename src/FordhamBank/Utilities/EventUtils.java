package FordhamBank.Utilities;

import FordhamBank.Enums.AccountType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventUtils {
    private Stage Modal;

    public EventUtils() {
        Modal = new Stage();
        Modal.initModality(Modality.APPLICATION_MODAL);
    }

    public void FireAddAccountButtonClickEvent() {
        Modal.setTitle("Add Account");

        GridPane content = new GridPane();
        content.setPadding(new Insets(15, 15, 15, 15));
        content.setVgap(20);

        Label accountNameLabel = new Label("Account Name: ");
        accountNameLabel.getStyleClass().add("pr-10");

        TextField accountNameTextField = new TextField();

        Label accountTypeLabel = new Label("Account Type: ");
        accountTypeLabel.getStyleClass().add("pr-10");

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        AccountType.SAVINGS.toString(),
                        AccountType.CHECKING.toString(),
                        AccountType.CD.toString()
                );

        ComboBox accountTypeDropdown = new ComboBox(options);

        Button submitButton = new Button("Submit");

        content.add(accountNameLabel, 0, 0);
        content.add(accountNameTextField, 1, 0);

        content.add(accountTypeLabel, 0, 1);
        content.add(accountTypeDropdown, 1, 1);

        content.add(submitButton, 0, 2);

        initScene(content);
    }

    public void FireWithdrawButtonClickEvent() {
        Modal.setTitle("Withdraw");
        Modal.show();
    }

    public void FireTransferButtonClickEvent() {
        Modal.setTitle("Transfer");
        Modal.show();
    }

    public void FireDepositButtonClickEvent() {
        Modal.setTitle("Deposit");
        Modal.show();
    }

    public void FireHistoryButtonClickEvent() {
        Modal.setTitle("History");
        Modal.show();
    }

    private void initScene(Pane pane) {
        Scene scene = new Scene(pane, 500, 400);
        Modal.setScene(scene);
        Modal.show();
    }
}
