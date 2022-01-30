package FrontEnd;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ConnectFourBoard {
    private GridPane connectFourBoard = new GridPane();
    private static final int NUMROW = 6;
    private static final int NUMCOLUMN = 7;


    ConnectFourBoard(Scene s){
        for(int i=0;i<NUMCOLUMN;i++){
            for(int j=0;j<NUMROW;j++){
                Rectangle cell = new Rectangle();
                cell.setFill(Color.BLUE);
                cell.setWidth(50);
                cell.setHeight(50);
                cell.widthProperty().bind(s.heightProperty().divide(8));
                cell.heightProperty().bind(s.heightProperty().divide(8));
                connectFourBoard.add(cell,i,j);
            }
        }
        connectFourBoard.setAlignment(Pos.CENTER);
    }

    public GridPane getConnectFourBoard() {
        return connectFourBoard;
    }
}
