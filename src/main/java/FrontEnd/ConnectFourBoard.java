package FrontEnd;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


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
                Rectangle cell = createCell(scene);
                connectFourBoard.add(cell,i,j);
            }
        }
        connectFourBoard.setAlignment(Pos.CENTER);
    }

    /**
     * Creates a "cell" for each connect four board spot and returns it.
     */
    private Rectangle createCell(Scene scene){
        Rectangle cell = new Rectangle();
        cell.setFill(Color.BLUE);
        cell.setWidth(50);
        cell.setHeight(50);
        cell.widthProperty().bind(scene.heightProperty().divide(8)); //These 2 methods make it so that the boxes scale with window size.
        cell.heightProperty().bind(scene.heightProperty().divide(8));
        return cell;
    }

    /**
     * Get a pointer towards the connect-four board object.
     */
    public GridPane getConnectFourBoard() {
        return connectFourBoard;
    }
}
