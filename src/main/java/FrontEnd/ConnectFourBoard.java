package FrontEnd;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class ConnectFourBoard {
    private final GridPane connectFourBoard = new GridPane();//final since we're not changing its pointer.
    private static final int NUMROW = 6;
    private static final int NUMCOLUMN = 7;


    /**
     * Default constructor that initializes all the cells in the connect-four board, and then sets its alignment to centered.
     */
    ConnectFourBoard(Scene scene){
        for(int i=0;i<NUMCOLUMN;i++){
            for(int j=0;j<NUMROW;j++){
                 StackPane piece = createCell(scene);
                connectFourBoard.add(piece,i,j);
            }
        }
        connectFourBoard.setAlignment(Pos.CENTER);
    }

    /**
     * Creates a StackPane "cell" for each connect four board spot and returns it.
     */
    private StackPane createCell(Scene scene){
        StackPane cell = new StackPane();
        Circle piece = new Circle();
        cell.getChildren().add(piece);
        cell.setAlignment(Pos.CENTER);
        cell.setStyle("-fx-background-color: Black");

        piece.setFill(Color.BLUE);
        piece.setRadius(50);
        piece.radiusProperty().bind(scene.heightProperty().divide(16)); //This method make it so that the boxes scale with window size.

        return cell;
    }

    /**
     * Get a pointer towards the connect-four board object.
     */
    public GridPane getConnectFourBoard() {
        return connectFourBoard;
    }
}
