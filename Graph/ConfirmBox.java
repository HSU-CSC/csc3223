import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

/**
 * Created by Candace on 4/23/2016.
 */
public class ConfirmBox {

    static boolean answer;

        public static boolean display(String title, String message, Stage parent){
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL); //Forces to address this box
            window.setTitle(title);
            window.setHeight(100);
            window.setWidth(280);
            //ensures that the new window pops up in the center of the parent window
            window.setX(parent.getX() + parent.getWidth() / 2 - window.getWidth() /2);
            window.setY(parent.getY() + parent.getHeight() /2 - window.getHeight() /2);
            window.setResizable(false);

            Label label = new Label();
            label.setText(message);

            Button btnYes = new Button("Yes");
            Button btnNo = new Button("No");

            btnYes.setOnAction(e -> {
                //System.out.print("Yes button"));
                answer = true;
                window.close();
            });
            btnNo.setOnAction(e -> {
                //System.out.print("No button"));
                answer = false;
                window.close();
            });

            HBox btnLayout = new HBox(5);
            btnLayout.getChildren().addAll(btnYes, btnNo);
            btnLayout.setAlignment(Pos.CENTER);
            btnLayout.setSpacing(10);

            VBox layout = new VBox(8);
            layout.getChildren().addAll(label, btnLayout);
            layout.setAlignment(Pos.CENTER);
            layout.setSpacing(10);
            layout.setPadding(new Insets(10,10,10,10));


            Scene scene = new Scene(layout);
            scene.getStylesheets().add("Styles.css");
            window.setScene(scene);

            window.showAndWait(); //Needs to be closed before showing next window

            return answer;
        }


}
