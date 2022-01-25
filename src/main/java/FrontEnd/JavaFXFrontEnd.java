package FrontEnd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class JavaFXFrontEnd extends Application {

    //todo implement the front end.
    @Override
    public void start(Stage stage) {
        GridPane boardGP = new GridPane();
        boardGP.setMinSize(6,7);
        boardGP.add(new Label("Testing"),0,0);
        Scene mainScene = new Scene(boardGP,640,480);
        //var is basically a local variable in java.
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}