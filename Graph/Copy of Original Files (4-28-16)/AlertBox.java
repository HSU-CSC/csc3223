package Graph; /**
 * Created by Candace on 4/23/2016.
 */

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();

        //Keeps you from messing with other things before addressing the window already opened.
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Close");

        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        scene.getStylesheets().add("Graph/Styles.css");
        window.setScene(scene);

        window.showAndWait(); //Needs to be closed before showing next window
    }

}
