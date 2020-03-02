package FordhamBank.Utilities;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

        VBox content = new VBox();
        content.setPadding(new Insets(15, 15, 15, 15));

        Label accountNameLabel = new Label("Account Name: ");
        content.getChildren().add(accountNameLabel);

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
