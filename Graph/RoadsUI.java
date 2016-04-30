<<<<<<< HEAD
package Graph;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Jonathan Oler, Candace Speers, Yerenia Adame
 */
public class RoadsUI extends Application {

    private Scene scene;
    Stage window;
   // private Graph graph;

    final private Button exit = new Button("Exit");
    final private Button next = new Button("Next");
    final private Button back = new Button("Back");
    final private Button menu = new Button("Menu");
    ComboBox<String> comboStart = new ComboBox<>();
    ComboBox<String> comboEnd = new ComboBox<>();


    public static void main(String [] args)
    {
        launch(args);
    }

    /**
     * This will be the main starting point for the program, it will
     * set the main scene and make a call to beginScreen. If at any point
     * a screen with an exit button has the button pushed, it will be heard
     * here and then it will close the program.
     * @param primaryStage The primary window used to display the scenes
     */
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        scene = new Scene(new StackPane(), 450, 250);
        beginScreen();

        //This will make use of a lambda expression to simplify the anonymous class declaration
        //needed to make use of a listener for the exit button This syntax will be used for every
        //button's listener.
        exit.setOnAction(event -> {
            event.consume();
            closeProgram();
        });

        //Close Request
        window.setOnCloseRequest(e -> {
            e.consume(); //Tells system "We're gonna take care of it"
            closeProgram();
        });

        window.setTitle("Graphs!");
        window.setScene(scene);
        window.show();

        scene.getStylesheets().add("Graph/Styles.css");
    }

    /**
     * This will display the first screen to be displayed upon running the
     * program. This screen will display a brief description about the
     * program and have two buttons, one to exit the program and one to
     * continue to the next screen.
     */
    public void beginScreen()
    {
        back.setCancelButton(false);
        Button begin = new Button("Begin");
        StackPane stack = new StackPane();
        Text message = new Text();

        begin.setText("Begin");
        begin.setDefaultButton(true);
        exit.setText("Exit");
        exit.setCancelButton(true);
        message.setText("Hello Everybody!");//Space to be used for program description

        stack.getChildren().addAll(begin, exit, message);
        StackPane.setAlignment(exit, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(begin, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(message, Pos.CENTER);

        scene.setRoot(stack);

        begin.setOnAction(event -> {
            event.consume();
            fileChoiceScreen();
        });
    }

    /**
     * This method will alter the the current scene so
     * that it will display field for the user to add
     * a source file path name, if the file is contained in the
     * same directory as the program a simple file name and
     * extension will suffice. If the user does not wish to
     * type the path name of the file, a browse button is
     * available to search the program files for the desired file.
     * Once selected two other buttons are available, one to go back to
     * the beginScreen and one to advance to the menuScreen.
     */
    public void fileChoiceScreen()
    {
        final TextField tField = new TextField();

        Label label = new Label("Please enter the source file path:");

        Button browse = new Button("Browse");

        HBox hb = new HBox();

        StackPane stack = new StackPane();

        next.setDefaultButton(true);
        back.setCancelButton(true);

        tField.setPromptText("C:\\documents\\jane\\cityName"); //Displays Prompt text as example file path
        tField.setPrefColumnCount(15);
        //makes use of css to make the prompt display initially
        tField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); }");

        hb.getChildren().addAll(label, tField, browse);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10.0);

        stack.getChildren().addAll(hb, back, next);
        StackPane.setAlignment(hb, Pos.CENTER);
        StackPane.setAlignment(back, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(next, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        browse.setOnAction(event -> {
            FileChooser fChoice = new FileChooser();
            //initializes a file with the location of the program's directory
            File input = new File(System.getProperty("user.dir"));

            fChoice.setTitle("Choose a File");
            if(input.canRead())
               fChoice.setInitialDirectory(input); //sets the FileChooser to start at the program's directory
            else
                fChoice.setInitialDirectory(new File("c:/")); //if the location cannot be accessed, starts at hard drive

            input = fChoice.showOpenDialog(new Stage());

            if(input != null)
                tField.setText(input.getAbsolutePath()); //if a file was selected, stores the path name in the text field


        });

        next.setOnAction(event -> {
            event.consume();

            try
            {
                populateGraph(tField.getText());
                menuScreen();
            }
            catch(IOException ex)
            {
                //if the given file cannot be read, will display an error message
                AlertBox.display("ERROR", "File cannot be found! Please Try Again.");
            }
        });

        back.setOnAction(event -> {
            event.consume();
            back.setCancelButton(false);
            beginScreen();
        });
    }

    /**
     * This will convert the current screen to that of the main menu for
     * the program. This menu will have two options for the user to select from,
     * to determine the minimum spanning tree or the shortest path. Once chosen,
     * if the next button is pressed, the program will continue to process the request.
     * If the back button is pressed the program will return to the fileChoiceScreen.
     */
    public void menuScreen()
    {
        StackPane stack = new StackPane();

        VBox vb = new VBox();

        String [] descriptions = {"This option will ask for the name of your starting city\n and determine the" +
                                  " shortest distance to all of the other cities.", "This option will ask for the" +
                                  " name of two cites and will\n then determine the shortest path between them."};

        Text message = new Text("Please choose one of the following options:");
        Text hint = new Text("*Place Cursor on Option for description*");

        Tooltip tips = new Tooltip(descriptions[0]);

        ChoiceBox cb = new ChoiceBox();

        hint.setFont(new Font(10.0));
        hint.setFill(Color.GREY);
        //sets the title for the options to select from
        cb.setItems(FXCollections.observableArrayList("Determine Minimum Spanning Tree", "Determine Shortest Path"));
        cb.getSelectionModel().select(0); //sets the default option to the first choice.

        //This makes use of an anonymous class declaration inorder to attach a listener to the ChoiceBox object.
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number newValue)
            {
                //changes the mouse over option description value.
                tips.setText(descriptions[newValue.intValue()]);
                cb.setTooltip(tips);
            }
        });

        vb.getChildren().addAll(message, cb);
        vb.setAlignment(Pos.CENTER);

        stack.getChildren().addAll(vb, hint, back, next);
        StackPane.setAlignment(vb, Pos.CENTER);
        StackPane.setAlignment(hint, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(back, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(next, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        //To do: Make a next button listener and transfer control to the next appropriate screen

        next.setOnAction(event -> {
            event.consume();
            findRouteScreen();
        });

        back.setOnAction(event -> {
            event.consume();
            fileChoiceScreen();
        });

    }

    private void findRouteScreen(){
        //-----------------------------------
        //ERROR: Problem with ComboBoxes
        //-----------------------------------

        StackPane stack = new StackPane();
        //ComboBox<String> comboStart = new ComboBox<>();
        //ComboBox<String> comboEnd = new ComboBox<>();
        String startPoint = "";
        String endPoint = "";

        Text start = new Text("Starting Point: ");
            start.getStyleClass().add("text_label");
        Text end = new Text("Ending Point: ");
            end.getStyleClass().add("text_label");
        HBox hb1 = new HBox(40);
        VBox vbStart = new VBox(8);
        VBox vbEnd = new VBox(8);

        System.out.println("");

        comboStart.setPromptText("[Start City]");
        comboEnd.setPromptText("[End City]");

        //Populate ComboBoxes
        comboStart.getItems().addAll(
                "Hot Springs",
                "Malvern",
                "Arkadelphia"
        );
        comboEnd.getItems().addAll(
                "Hot Springs",
                "Malvern",
                "Arkadelphia"
        );

        //Get values from ComboBoxes
        startPoint = comboStart.getValue();
        System.out.println(startPoint);
        endPoint = comboEnd.getValue();
        System.out.println(endPoint);

        //Layout
        vbStart.getChildren().addAll(start, comboStart);
        vbEnd.getChildren().addAll(end, comboEnd);

        hb1.getChildren().addAll(vbStart, vbEnd);
        hb1.setAlignment(Pos.CENTER);

        stack.getChildren().addAll(hb1, back, next);
        stack.setAlignment(back, Pos.BOTTOM_LEFT);
        stack.setAlignment(next, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        //To do: Make a next button listener and transfer control to the next appropriate screen

        back.setOnAction(event -> {
            event.consume();
            menuScreen();
        });

    }


    //SCREENS TO BE MADE: {

    //public void minSpanInputScreen()

    //public void shortPathInputScreen()

    //public void minSpanResultScreen(String city)

    //public void shortPathResultScreen(String origin, String dest) }

    /**
     * This will convert a passed string value to a File object and
     * attach a Scanner to it. If successful, every line from the file
     * will be read and if the format of the content is correct, the
     * information will be added to the Graph.
     *
     * @param fileName      The string value to be converted to a File
     * @throws IOException  if the file cannot be read
     */
    private void populateGraph(String fileName)throws IOException
    {
        Scanner in = new Scanner(new File(fileName));
        String [] tokens;

        while(in.hasNextLine())
        {
            tokens = in.nextLine().split(",");
            //To do: check formatting of line and add contents to the Graph

            //To Do: populate comboBoxes with locations
        }
    }

    private void closeProgram(){
        boolean answer = ConfirmBox.display("Exit Confirmation", "Are you sure you want to exit?");
        if(answer)
            Platform.exit();
    }


}
=======
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * @author Jonathan Oler, Candace Speers, Yerenia Adame
 */
public class RoadsUI extends Application {

    private Scene scene;
    private final ObservableList<String> origins = FXCollections.observableArrayList();

    Stage window;
    private final Graph graph = new Graph();

    final private Button exit = new Button("Exit");
    final private Button next = new Button("Next");
    final private Button back = new Button("Back");
    final private Button menu = new Button("Menu");


    public static void main(String [] args)
    {
        launch(args);
    }

    /**
     * This will be the main starting point for the program, it will
     * set the main scene and make a call to beginScreen. If at any point
     * a screen with an exit button has the button pushed, it will be heard
     * here and then it will close the program.
     * @param primaryStage The primary window used to display the scenes
     */
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        scene = new Scene(new StackPane(), 450, 250);
        beginScreen();

        //This will make use of a lambda expression to simplify the anonymous class declaration
        //needed to make use of a listener for the exit button This syntax will be used for every
        //button's listener.
        exit.setOnAction(event -> {
            event.consume();
            closeProgram();
        });

        //Close Request
        window.setOnCloseRequest(e -> {
            e.consume(); //Tells system "We're gonna take care of it"
            closeProgram();
        });
        scene.getStylesheets().add("Styles.css");
        primaryStage.setTitle("Graphs!!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This will display the first screen to be displayed upon running the
     * program. This screen will display a brief description about the
     * program and have two buttons, one to exit the program and one to
     * continue to the next screen.
     */
    public void beginScreen()
    {

        Button begin = new Button("Begin");
        StackPane stack = new StackPane();
        Text message = new Text();

        back.setCancelButton(false);

        begin.setText("Begin");
        begin.setDefaultButton(true);
        exit.setText("Exit");
        exit.setCancelButton(true);
        message.setText("Hello Everybody!");//Space to be used for program description

        stack.getChildren().addAll(begin, exit, message);
        StackPane.setAlignment(exit, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(begin, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(message, Pos.CENTER);

        scene.setRoot(stack);

        begin.setOnAction(event -> {
            event.consume();
            fileChoiceScreen();
        });
    }

    /**
     * This method will alter the the current scene so
     * that it will display field for the user to add
     * a source file path name, if the file is contained in the
     * same directory as the program a simple file name and
     * extension will suffice. If the user does not wish to
     * type the path name of the file, a browse button is
     * available to search the program files for the desired file.
     * Once selected two other buttons are available, one to go back to
     * the beginScreen and one to advance to the menuScreen.
     */
    public void fileChoiceScreen()
    {
        final TextField tField = new TextField();

        Label label = new Label("Please enter the source file path:");

        Button browse = new Button("Browse");

        HBox hb = new HBox();

        VBox vb = new VBox();

        Text error = new Text();

        StackPane stack = new StackPane();

        next.setDefaultButton(true);
        back.setCancelButton(true);

        tField.setPromptText("C:\\documents\\jane\\cityRecord"); //Displays Prompt text as example file path
        tField.setPrefColumnCount(15);
        //makes use of css to make the prompt display initially
        tField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); }");

        hb.getChildren().addAll(label, tField, browse);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10.0);

        vb.getChildren().addAll(hb, error);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(5.0);

        stack.getChildren().addAll(vb, back, next);
        StackPane.setAlignment(back, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(next, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        browse.setOnAction(event -> {
            FileChooser fChoice = new FileChooser();
            //initializes a file with the location of the program's directory
            File input = new File(System.getProperty("user.dir"));

            fChoice.setTitle("Choose a File");
            if(input.canRead())
               fChoice.setInitialDirectory(input); //sets the FileChooser to start at the program's directory
            else
                fChoice.setInitialDirectory(new File("c:/")); //if the location cannot be accessed, starts at hard drive

            input = fChoice.showOpenDialog(window);

            if(input != null)
                tField.setText(input.getAbsolutePath()); //if a file was selected, stores the path name in the text field


        });

        next.setOnAction(event -> {
            event.consume();
            if(tField.getText().trim().isEmpty())
            {
                error.setText("Please enter a path name to continue!");
                error.setFill(Color.RED);
                error.setTextAlignment(TextAlignment.RIGHT);
                error.setEffect(createFlashEffect());
            }
            else
            {
                try {
                    error.setText("");
                    populateGraph(tField.getText());
                    menuScreen();
                } catch (IOException ex) {
                    //if the given file cannot be read, will display an error message
                    AlertBox.display("ERROR", "File cannot be found! Please Try Again.", window);
                }
            }
        });

        back.setOnAction(event -> {
            event.consume();
            back.setCancelButton(false);
            beginScreen();
        });
    }

    /**
     * This will convert the current screen to that of the main menu for
     * the program. This menu will have two options for the user to select from,
     * to determine the minimum spanning tree or the shortest path. Once chosen,
     * if the next button is pressed, the program will continue to process the request.
     * If the back button is pressed the program will return to the fileChoiceScreen.
     */
    public void menuScreen()
    {
        StackPane stack = new StackPane();

        VBox vb = new VBox();

        String [] descriptions = {"This option will ask for the name of your starting city\n and determine the" +
                                  " shortest distance to all of the other cities.", "This option will ask for the" +
                                  " name of two cites and will\n then determine the shortest path between them."};

        Text message = new Text("Please choose one of the following options:");
        Text hint = new Text("*Place Cursor on Option for a description*");
        Text error = new Text();

        Tooltip tips = new Tooltip();

        ComboBox cb = new ComboBox();

        hint.setFont(new Font(10.0));
        hint.setFill(Color.GREY);

        cb.setItems(FXCollections.observableArrayList("Determine Minimum Spanning Tree", "Determine Shortest Path"));
        //sets the title for the options to select from
        cb.setPromptText("Options");
        tips.setText("HURRY UP AN CHOOSE A OPTION \nYA DINGUS!");
        tips.setTextAlignment(TextAlignment.CENTER);
        cb.setTooltip(tips);

        //This makes use of an anonymous class declaration inorder to attach a listener to the ChoiceBox object.
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number newValue)
            {
                //changes the mouse over option description value.
                tips.setText(descriptions[newValue.intValue()]);
                cb.setTooltip(tips);
            }
        });

        vb.getChildren().addAll(message, cb, error);
        vb.setAlignment(Pos.CENTER);

        stack.getChildren().addAll(vb, hint, back, next);
        StackPane.setAlignment(hint, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(back, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(next, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);


        next.setOnAction(event -> {
            switch(cb.getSelectionModel().getSelectedIndex())
            {
                case 0 :
                   minSpanInputScreen(); break;
                case 1 :
                    //shortPathInputScreen()
                    break;
                default : {
                    error.setText("Please choose an option to continue!");
                    error.setFill(Color.RED);
                    error.setEffect(createFlashEffect());

                    break;
                }
            }
        });

        back.setOnAction(event -> {
            event.consume();
            fileChoiceScreen();
        });
    }

    public void minSpanInputScreen()
    {
        StackPane stack = new StackPane();

        ComboBox<ListView> cb = new ComboBox(origins);
        Text message = new Text("Please choose one of the following origins: ");
        VBox vb = new VBox();

        cb.setPromptText("Origins");
        cb.setVisibleRowCount(4);

        vb.getChildren().addAll(message, cb);
        vb.setAlignment(Pos.CENTER);

        stack.getChildren().addAll(vb, back, next);
        StackPane.setAlignment(back, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(next, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        next.setOnAction(event -> {
            //event.consume();
           // minSpanResultScreen(listBox.getSelectionModel().getSelectedItem());
        });

        back.setOnAction(event -> {
            event.consume();
            menuScreen();
        });
    }

    //SCREENS TO BE MADE: {
    //public void shortPathInputScreen()

    //public void minSpanResultScreen(String city)

    //public void shortPathResultScreen(String origin, String dest) }

    /**
     * This will convert a passed string value to a File object and
     * attach a Scanner to it. If successful, every line from the file
     * will be read and if the format of the content is correct, the
     * information will be added to the Graph.
     *
     * @param fileName      The string value to be converted to a File
     * @throws IOException  if the file cannot be read
     */
    private void populateGraph(String fileName)throws IOException
    {
        Scanner in = new Scanner(new File(fileName));
        String [] tokens;

        while(in.hasNextLine())
        {
            tokens = in.nextLine().split(",");

            if(tokens.length == 3)
            {
                //if the last token contains a numeric value
                if(tokens[2].trim().matches("\\d+") || tokens[2].trim().matches("\\d+" + "." + "\\d+"))
                {
                    if(!graph.contains(tokens[0].trim()))
                        origins.add(tokens[0].trim());
                    if(!graph.contains(tokens[1].trim()))
                        origins.add(tokens[1].trim());

                    graph.addNode(tokens[0].trim(), tokens[1].trim(), Double.parseDouble(tokens[2].trim()));
                }
            }
        }
        FXCollections.sort(origins);
    }

    private void closeProgram(){
        boolean answer = ConfirmBox.display("Exit Confirmation", "Are you sure you want to exit?", window);
        if(answer)
            Platform.exit();
    }

    /**
     * This will generate a new javafx bloom effect that will
     * use a timeline to create a flashing light animation.
     *
     * @return The Bloom effect object with the timeline attached to it
     */
    private Bloom createFlashEffect()
    {
        //creates a timeline for an lighting animation
        Timeline timeLine = new Timeline();

        Bloom bloom = new Bloom();
        //alternates the lighting from none to half of the max
        KeyValue keyV1 = new KeyValue(bloom.thresholdProperty(), 0.0, Interpolator.EASE_OUT);
        KeyValue keyV2 = new KeyValue(bloom.thresholdProperty(), 0.5, Interpolator.EASE_OUT);
        KeyFrame keyF1 = new KeyFrame(Duration.millis(0.0), keyV1);
        KeyFrame keyF2 = new KeyFrame(Duration.millis(150), keyV2);

        //sets the timeline to cycle through an off/on lighting animation (even cycles = remains on, odd cycles = remains off)
        timeLine.setCycleCount(5);//Timeline.INDEFINITE);
        timeLine.setAutoReverse(true);
        timeLine.getKeyFrames().addAll(keyF1, keyF2);
        timeLine.play();

        return bloom;
    }


}
>>>>>>> refs/remotes/origin/master
