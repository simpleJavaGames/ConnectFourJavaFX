package FrontEnd;

import ConnectFourService.ConnectFourService;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class ConnectFourBoard {

    private final ConnectFourService connectFourService = new ConnectFourService();


    private final GridPane connectFourBoard = new GridPane();//final since we're not changing its pointer.
    private final Node[][] connectFourBoardNodes = new Node[NUMROW][NUMCOLUMN]; //this is for easily modifying all the nodes in the gridpane.
    private final Circle[][] connectFourBoardCircles = new Circle[NUMROW][NUMCOLUMN]; // THis is for ease of access of the circle nodes which are inside the Stackpane inside of the GP.
    private static final int NUMROW = 6;
    private static final int NUMCOLUMN = 7;


    /**
     * Default constructor that initializes the back-end board. Afterwards it works on the front-end,
     * initializing all the cells in the connect-four board, and then setting its alignment to centered.
     */
    ConnectFourBoard(Scene scene){
        for(int i=0;i<NUMROW;i++){
            for(int j=0;j<NUMCOLUMN;j++){
                StackPane cell = createCell(scene,i,j); //todo test
                connectFourBoard.add(cell,j,i);
                connectFourBoardNodes[i][j] = cell;


                //initialize the first row to have mouse hover handlers and mouse click handlers as to play the game.
                if(i==0){
                    //temp variable to set the colPos
                    int colPos = j;
                    int rowPos = i; //we could set this to 0 to save time, but why bother, could lead to problems.
                    connectFourBoardNodes[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.getButton() == MouseButton.PRIMARY){
                                connectFourBoardCircles[rowPos][colPos].setFill(Color.DARKGREEN); //little trick to clear the hovered piece.
                                connectFourService.dropPiece(colPos);
                            }
                        }
                    });

                    connectFourBoardNodes[rowPos][colPos].setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            //todo check if the spot is empty and if it is, hover a piece there to indicate where it's being dropped.
                            if(!connectFourService.isColumnCompletelyFull(colPos)){ //if the column is not completely full, then show that piece.
                                boolean yellowTurn = connectFourService.isYellowTurn();
                                if(yellowTurn){
                                    connectFourBoardCircles[rowPos][colPos].setFill(Color.YELLOW);
                                }else{
                                    connectFourBoardCircles[rowPos][colPos].setFill(Color.RED);
                                }
                            }
                        }
                    });

                    connectFourBoardNodes[rowPos][colPos].setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(!connectFourService.isColumnCompletelyFull(colPos)){
                                connectFourBoardCircles[rowPos][colPos].setFill(Color.DARKGREEN);
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
    private StackPane createCell(Scene scene,int rowPos,int colPos){
        StackPane cell = new StackPane();
        Circle piece = new Circle();

        connectFourBoardCircles[rowPos][colPos] = piece; //put a pointer to each circle object for easy modification to their color.

        cell.getChildren().add(piece);
        cell.setAlignment(Pos.CENTER);
        cell.setStyle("-fx-background-color: DODGERBLUE"); //MAKE SURE THAT THIS ALIGNS WITH THE STROKE WE SET THE PIECE AS.

        piece.setFill(Color.DARKGREEN); //default this to the same color as background.
        piece.setRadius(50);
        piece.setStroke(Color.DODGERBLUE);
        piece.setStrokeWidth(7);
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
