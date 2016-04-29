/**
 * Created by Candace on 4/23/2016.
 */

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class AlertBox {

    public static void display(String title, String message, Stage parent){
        Stage window = new Stage();

        //Keeps you from messing with other things before addressing the window already opened.
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setHeight(100);
        window.setWidth(250);
        //ensures that the new window pops up in the center of the parent window
        window.setX(parent.getX() + parent.getWidth() / 2 - window.getWidth() /2);
        window.setY(parent.getY() + parent.getHeight() / 2 - window.getHeight() / 2);
        window.setResizable(false);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Close");

        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        scene.getStylesheets().add("Styles.css");
        window.setScene(scene);

        window.showAndWait(); //Needs to be closed before showing next window
    }

}
