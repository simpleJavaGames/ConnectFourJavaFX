package FrontEnd;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
        AnchorPane background = new AnchorPane();
        background.setStyle("-fx-background-color: #006400");
        Scene mainScene = new Scene(background,640,480);


        GridPane board = new ConnectFourBoard(mainScene).getConnectFourBoard();
        AnchorPane.setBottomAnchor(board, .0);
        AnchorPane.setLeftAnchor(board, .0);
        AnchorPane.setRightAnchor(board, .0);
        AnchorPane.setTopAnchor(board, .0);
        background.getChildren().add(board);


        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}