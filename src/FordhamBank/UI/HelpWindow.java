package FordhamBank.UI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpWindow {
    private Stage window;

    public HelpWindow(Stage primaryStage) {
        initialize(primaryStage);
    }

    public void show() {
        window.show();
    }

    private void initialize(Stage primaryStage) {
        Label secondLabel = new Label("Hello! Welcome to FordhamBank the number one bank of Fordham students. \nFrom this page you can make transactions, make a withdrawal, or make a depostit.\nYour money is displayed on the right hand side and each account has an in depth account transaction list!");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 750, 200);

        // New window (Stage)
        window = new Stage();
        window.setTitle("Welcome to FordhamBank!");
        window.setScene(secondScene);

        // Specifies the modality for new window.
        window.initModality(Modality.WINDOW_MODAL);

        //parent window
        window.initOwner(primaryStage);

        // where the second window will be
        window.setX(primaryStage.getX() + 25);
        window.setY(primaryStage.getY() + 150);
    }
}
