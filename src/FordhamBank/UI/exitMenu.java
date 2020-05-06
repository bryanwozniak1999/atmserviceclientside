package FordhamBank.UI;

import FordhamBank.Main;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class exitMenu {
    private Stage window;
    

    public exitMenu(Stage primaryStage) {
        initialize(primaryStage);
    }

    public void show() {
        window.show();
    }

    private void initialize(Stage primaryStage) {
        Label secondLabel = new Label("Are you sure you want to exit?");

        GridPane secondaryLayout = new GridPane();
        secondaryLayout.setVgap(5);
        secondaryLayout.setAlignment(Pos.CENTER);
        GridPane.setHalignment(secondLabel, HPos.CENTER);
        //GridPane.setValignment(secondLabel, VPos.TOP);
        secondaryLayout.add(secondLabel,0,1);
        
        Button exitButton = new Button("Exit");
        
        
       
        
        GridPane.setHalignment(exitButton, HPos.RIGHT);
        //GridPane.setValignment(exitButton, VPos.BOTTOM);
        secondaryLayout.add(exitButton,0,9);

        exitButton.setOnAction(e -> {
        	
            
            
            
            Main.su.sendMessage("QUIT");
            primaryStage.close();
            
            
        });

        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        
        GridPane.setHalignment(cancelButton, HPos.LEFT);
        //GridPane.setValignment(cancelButton, VPos.BOTTOM);
        secondaryLayout.add(cancelButton,0,9);
        

        cancelButton.setOnAction(e -> {
        	
        	window.close();
            
        });


        Scene secondScene = new Scene(secondaryLayout, 275, 200);

        // New window (Stage)
        window = new Stage();
        window.setTitle("Exit Confirmation");
        window.setScene(secondScene);

        // Specifies the modality for new window.
        window.initModality(Modality.WINDOW_MODAL);

        //parent window
        window.initOwner(primaryStage);

        // where the second window will be
        window.setX(primaryStage.getX() + 275);
        window.setY(primaryStage.getY() + 200);
    }
}
