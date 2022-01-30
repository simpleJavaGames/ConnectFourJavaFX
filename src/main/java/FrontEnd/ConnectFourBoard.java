package FrontEnd;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;


public class ConnectFourBoard {
    private final GridPane connectFourBoard = new GridPane();//final since we're not changing its pointer.
    private final Node[][] connectFourBoardNodes = new Node[NUMROW][NUMCOLUMN]; //this is for easily modifying all the nodes in the gridpane.
    private static final int NUMROW = 6;
    private static final int NUMCOLUMN = 7;


    /**
     * Default constructor that initializes all the cells in the connect-four board, and then sets its alignment to centered.
     */
    ConnectFourBoard(Scene scene){
        for(int i=0;i<NUMROW;i++){
            for(int j=0;j<NUMCOLUMN;j++){
                StackPane cell = createCell(scene); //todo test
                connectFourBoard.add(cell,j,i);
                connectFourBoardNodes[i][j] = cell;

                if(i==0){ //initialize the first row to have mouse hover handlers.
                    connectFourBoardNodes[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.isPrimaryButtonDown()){
                                //todo add drop piece.
                            }
                        }
                    });
                }

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
        cell.setStyle("-fx-background-color: Blue");

        piece.setFill(Color.DARKGREEN); //default this to the same color as background.
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
