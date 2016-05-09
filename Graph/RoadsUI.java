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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author Jonathan Oler, Candace Speers, Yerenia Adame
 */
public class RoadsUI extends Application {

    private Scene scene;
    private final ObservableList<String> origins = FXCollections.observableArrayList();

    private Stage window;
    private Graph graph = new Graph();

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

       //This will create a listener that will attempt to close the program whenever the exit button is pressed
        exit.setOnAction(event -> {
            event.consume();
            closeProgram();
        });

        //This will create a listener that will switch the displayed screen to the menu whenever the menu button is pressed
        menu.setOnAction(event -> {
            event.consume();
            menuScreen();
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
        Label message = new Label();
        Tooltip hidden = new Tooltip();
        //secret
        hidden.setGraphic(new ImageView(new Image("Hackerman.gif")));

        begin.setText("Begin");
        exit.setText("Exit");

        message.setText("Hello Everybody!");//Space to be used for program description
        message.setFont(new Font(15));
        message.setTooltip(hidden);

        stack.getChildren().addAll(message, exit, begin);
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

        Text message = new Text("Please enter the source file path:");

        Button browse = new Button("Browse");

        HBox hb = new HBox();

        VBox vb = new VBox();

        Text error = new Text();

        StackPane stack = new StackPane();

        tField.setPromptText("C:\\documents\\jane\\cityRecord"); //Displays Prompt text as example file path
        tField.setPrefColumnCount(15);
        //makes use of css to make the prompt display initially
        tField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); }");

        message.getStyleClass().add("text_label");
        error.getStyleClass().add("text_label");

        hb.getChildren().addAll(message, tField, browse);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(5.0);

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
                                  " shortest distance to all of the other cities.", "This option will ask for the "+
                                  "name of a starting city and will then create a path to traverse the graph..","This option will ask for the" +
                                  " name of two cities and will\n then determine the shortest path length between them."};

        Text message = new Text("Please choose one of the following options:");
        Text hint = new Text("*Place Cursor on Option for a description*");
        Text error = new Text();

        Tooltip tips = new Tooltip();

        ComboBox cb = new ComboBox();

        message.getStyleClass().add("text_label");
        error.getStyleClass().add("text_label");

        hint.setFont(new Font(10.0));
        hint.setFill(Color.GREY);

        cb.setItems(FXCollections.observableArrayList("Determine Cost of Minimum Spanning Tree", "Determine a Path", "Determine the Shortest Path Cost"));
        //sets the title for the options to select from
        cb.setPromptText("Options");
        tips.setText("HURRY UP AN CHOOSE AN OPTION \nYA DINGUS!");
        tips.setTextAlignment(TextAlignment.CENTER);
        cb.setTooltip(tips);

        //This makes use of an anonymous class declaration inorder to attach a listener to the ComboBox object.
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

        for(String x : origins)
        {
            graph.get(x).visited = false;
        }
        next.setOnAction(event -> {
            switch(cb.getSelectionModel().getSelectedIndex())
            {
                case 0 :
                   minSpanInputScreen(true); break;
                case 1 :
                    minSpanInputScreen(false); break;
                case 2 :
                    findRouteScreen(); break;
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

    public void minSpanInputScreen(boolean minSpanOrTraverse)
    {
        StackPane stack = new StackPane();

        ComboBox<String> cb = new ComboBox();
        Text message = new Text("Please choose one of the following origins: ");
        Text error = new Text("");
        VBox vb = new VBox();

        message.getStyleClass().add("text_label");
        error.getStyleClass().add("text_label");
        System.out.println(origins.size());
        cb.setItems(origins);
        cb.setPromptText("Origins");
        cb.setVisibleRowCount(2);

        vb.getChildren().addAll(message, cb, error);
        vb.setAlignment(Pos.CENTER);

        stack.getChildren().addAll(vb, back, next);
        StackPane.setAlignment(back, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(next, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        next.setOnAction(event -> {
            event.consume();
            if(cb.getSelectionModel().getSelectedItem() == null)
            {
                //user did not select an origin
                error.setText("Please select an origin city to continue!");
                error.setFill(Color.RED);
                error.setEffect(createFlashEffect());
            }
            else
            {
                if(minSpanOrTraverse)
                    minSpanResultScreen(cb.getSelectionModel().getSelectedItem());
                else
                    traversalResultScreen(cb.getSelectionModel().getSelectedItem());
            }
        });

        back.setOnAction(event -> {
            event.consume();
            menuScreen();
        });
    }


    private void findRouteScreen(){
        final ObservableList<String> destinations = FXCollections.observableArrayList();
        StackPane stack = new StackPane();

        ComboBox<String> comboStart = new ComboBox();
        ComboBox<String> comboEnd = new ComboBox();

        Text start = new Text("Origins:");
        Text end = new Text("Destination:");
        Text error = new Text();

        HBox hb1 = new HBox(40);

        VBox vbStart = new VBox(8);
        VBox vbEnd = new VBox(8);
        VBox vb = new VBox(8);

        comboStart.setItems(origins);
        comboStart.setPromptText("Origin");
        comboEnd.setItems(destinations);
        comboEnd.setPromptText("Destination");

        comboStart.setVisibleRowCount(2);
        comboEnd.setVisibleRowCount(2);

        start.getStyleClass().add("text_label");

        end.getStyleClass().add("text_label");

        error.getStyleClass().add("text_label");

        //Layout
        vbStart.getChildren().addAll(start, comboStart);
        vbEnd.getChildren().addAll(end, comboEnd);

        hb1.getChildren().addAll(vbStart, vbEnd);
        hb1.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(hb1, error);
        vb.setAlignment(Pos.CENTER);

        stack.getChildren().addAll(vb, back, next);
        StackPane.setAlignment(back, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(next, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        comboStart.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number newValue)
            {
                //resets the destination selection when the origin is changed
                comboEnd.getSelectionModel().select(null);
                destinations.setAll(origins);
                //removes the origin as a possible destination
                destinations.remove(comboStart.getSelectionModel().getSelectedItem());

            }
        });

        next.setOnAction(event -> {
            event.consume();
            if(comboStart.getSelectionModel().getSelectedItem() == null || comboEnd.getSelectionModel().getSelectedItem() == null)
            {
                // user did not select an origin or a destination
                error.setText("Please be sure to choose an origin and destination!");
                error.setFill(Color.RED);
                error.setEffect(createFlashEffect());
            }
            else
            {
                    displayShortPathScreen(comboStart.getSelectionModel().getSelectedItem(), comboEnd.getSelectionModel().getSelectedItem());
            }
        });
        back.setOnAction(event -> {
            event.consume();
            menuScreen();
        });

    }



    public void minSpanResultScreen(String city)
    {
        StackPane stack = new StackPane();

        Label results = new Label();

        Tooltip mystery = new Tooltip();

        results.setWrapText(true);

        results.setText("The weight cost of the minimum spanning tree is " + Roads.minimumSpanningTree(graph, city) + " miles");
        results.setTextAlignment(TextAlignment.CENTER);
        results.getStyleClass().add("text_comical");

        mystery.setGraphic(new ImageView(new Image("ThumbsUp.gif")));
        results.setTooltip(mystery);

        stack.getChildren().addAll(results, exit, menu);

        StackPane.setAlignment(results, Pos.CENTER);
        StackPane.setAlignment(exit, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(menu, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        //If exit or menu is pressed the listeners in start will handle it
    }

    public void displayShortPathScreen(String origin, String destination)
    {
        StackPane stack = new StackPane();

        Text results = new Text();

        Roads path = new Roads(graph, destination);
        path.shortestPath(graph.get(origin), 1000);

        results.setText("The total cost for the shortest path is " + path.distance);
        results.getStyleClass().add("text_comical");
        results.setTextAlignment(TextAlignment.CENTER);

        stack.getChildren().addAll(results, exit, menu);

        StackPane.setAlignment(results, Pos.CENTER);
        StackPane.setAlignment(exit, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(menu, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

        //If exit or menu is pressed the listeners in start will handle it
    }

    public void traversalResultScreen(String origin)
    {
        StackPane stack = new StackPane();

        String result = "The path will start at ";

        Label results = new Label();

        ArrayList<Node> path = new ArrayList<>();

        Roads.traversal(graph, graph.get(origin), path);

        for(int i = 0; i < path.size(); i++)
        {
            result += (i != path.size() - 1)? path.get(i) + ", which then connects to " : path.get(i) + ", which is where your journey ends.";
        }

        results.setText(result);
        results.setFont(new Font(15));
        results.setWrapText(true);
        results.setTextAlignment(TextAlignment.CENTER);

        stack.getChildren().addAll(results, exit, menu);
        StackPane.setAlignment(results, Pos.CENTER);
        StackPane.setAlignment(exit, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(menu, Pos.BOTTOM_RIGHT);

        scene.setRoot(stack);

    }

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
        origins.clear();

        Scanner in = new Scanner(new File(fileName));
        String [] tokens;

        String origin;
        String destination;
        String distance;

        while(in.hasNextLine())
        {
            tokens = in.nextLine().split(",");

            if(tokens.length == 3)
            {
                distance = tokens[2].trim();
                //if the last token contains a numeric value
                if(distance.matches("\\d+(\\.\\d+)?"))
                {
                    origin = tokens[0].trim();
                    destination = tokens[1].trim();

                    if(!origins.contains(origin))
                        origins.add(origin);
                    if(!origins.contains(destination))
                        origins.add(destination);

                    graph.addNode(origin, destination, Double.parseDouble(distance));
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
        timeLine.setCycleCount(5);//Timeline.INDEFINITE); //use this if you like seizures
        timeLine.setAutoReverse(true);
        timeLine.getKeyFrames().addAll(keyF1, keyF2);
        timeLine.play();

        return bloom;
    }


}
