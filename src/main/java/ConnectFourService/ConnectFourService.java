package ConnectFourService;

import Exceptions.ColumnFullException;

public class ConnectFourService {
    //todo clean up the back-end.
    //todo make it so that the winner check actually works.
    private char[][] connectFourBoard = new char[6][7];
    private boolean yellowTurn;
    private boolean isGameRunning;
    private int numPiecesOut = 0;

    /**
     * Default constructor that sets the initial player turn to yellow.
     */
    public ConnectFourService(){
        yellowTurn = true;
        isGameRunning = true;
    }

    /**
     * Overloaded constructor that sets which player plays first.
     */
    public ConnectFourService(boolean yellowTurn){
        this.yellowTurn = yellowTurn;
        isGameRunning = true;
    }

    /**
     * Increment the turn and make it the next turn.
     * DO NOT CALL THIS METHOD
     * Will be called internally from within the ConnectFour Class.
     */
    private void nextTurn(){
        if(yellowTurn){
            yellowTurn = false;
        }else{
            yellowTurn = true;
        }
    }

    public boolean isColumnCompletelyFull(int colPos){
        if(connectFourBoard[0][colPos] == '\u0000'){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Puts the specified piece in the spot and then changes the turn to the next one.
     * DO NOT CALL THIS METHOD.
     * This will be called by a method that checks to make sure that the move is valid.
     */
    private void putPiece(int rowPos,int colPos){
        if(yellowTurn){
            connectFourBoard[rowPos][colPos] = 'Y';
        }else{
            connectFourBoard[rowPos][colPos] = 'R';
        }
        numPiecesOut++; //Increment the number of pieces out.
        nextTurn(); //Make it the next player's turn.
    }

    /**
     * This method will find the next row position that is available when the piece is dropped.
     * This method is also the one that checks to make sure that the move is actually legal.
     */
    public int nextAvailableRow(int colPos) throws ColumnFullException{
        //This will start going through all the rows as length of 2D array is height, [1].length would be width.
        for(int i= connectFourBoard.length-1;i>=0;i--){
            if(connectFourBoard[i][colPos] == '\u0000') return i;
        }
        //All the cells in the column is full!
        throw new ColumnFullException("Column at colPos "+colPos+" is completely full.");
    }

    /**
     * This method will drop a piece at the selected column, will return true if successful.
     * Will return false if unsuccessful.
     */
    public int[] dropPiece(int colPos) throws ColumnFullException{

        int nextAvailableRow = nextAvailableRow(colPos);
        putPiece(nextAvailableRow,colPos); //This method can throw a columnFullException.
        if(isWinnerMultiThread(nextAvailableRow,colPos)){// if we have a winner, return true, else continue on.
            isGameRunning = false;
        }else if(isCompletelyFull()){
            //if the board is full, end the game, else continue.
            isGameRunning = false;
        }
        return new int[]{nextAvailableRow,colPos}; //return where the new piece was placed so that we can update it in the GUI.

    }

    /**
     * Debug method that prints the current board to the console.
     */
    @Override
    public String toString() {
        String to_return="  0   1   2   3   4   5   6";
        for(int i=0;i<6;i++) {
            to_return+="\n-----------------------------\n ";
            to_return+="| ";
            for(int j=0;j<7;j++) {
                to_return+=connectFourBoard[i][j]+" | ";
            }
        }
        to_return+="\n-----------------------------\n";
        return to_return;
    }

    /**
     * Getter to ensure that the game is not over.
     */
    public boolean getIsGameRunning() {
        return isGameRunning;
    }

    /**
     * Returns true if it is yellow's turn, else returns false.
     */
    public boolean isYellowTurn(){
        return yellowTurn;
    }

    /**
     * This method returns true if the board is completely full(if there are 42 pieces out, we know the board is full.)
     */
    public boolean isCompletelyFull(){
        return (numPiecesOut >= 42);
    }

    /**
     * This multithreaded method calls all the other smaller methods to check to see if there is a winner.
     * Returns true is there is a winner, else false.
     */
    //todo test this to make sure that it functions properly.
    public boolean isWinnerMultiThread(int rowPos, int colPos){
        final boolean[] isWinnerHorizontal = new boolean[1];
        Thread horizontal = new Thread(() -> isWinnerHorizontal[0] = isWinnerHorizontal(rowPos));
        horizontal.start();

        final boolean[] isWinnerVertical = new boolean[1];
        Thread vertical = new Thread(()-> isWinnerVertical[0] = isWinnerVertical(colPos));
        vertical.start();

        final boolean[] isWinnerPositiveDiagonal = new boolean[1];
        Thread positiveDiagonal = new Thread(()-> isWinnerPositiveDiagonal[0] =isWinnerPositiveDiagonal(rowPos, colPos));
        positiveDiagonal.start();

        final boolean[] isWinnerNegativeDiagonal = new boolean[1];
        Thread negativeDiagonal = new Thread(()-> isWinnerNegativeDiagonal[0] = isWinnerNegativeDiagonal(rowPos, colPos));
        negativeDiagonal.start();

        //thread joins to ensure that all the checks are done before we say anything.
        try{
            horizontal.join();
        }catch (InterruptedException e){
            System.out.println("Horizontal join in isWinnerMultiThread was interrupted.");
        }

        try{
            vertical.join();
        }catch (InterruptedException e){
            System.out.println("vertical join in isWinnerMultiThread was interrupted.");
        }

        try{
            negativeDiagonal.join();
        }catch (InterruptedException e){
            System.out.println("negDiagonal join in isWinnerMultiThread was interrupted.");
        }

        try{
            positiveDiagonal.join();
        }catch (InterruptedException e){
            System.out.println("posDiagonal join in isWinnerMultiThread was interrupted.");
        }

        return (isWinnerHorizontal[0] || isWinnerVertical[0] || isWinnerPositiveDiagonal[0] || isWinnerNegativeDiagonal[0]);
    }

    public boolean isWinnerNotMultiThread(int rowPos, int colPos){
        return (isWinnerHorizontal(rowPos) || isWinnerVertical(colPos) || isWinnerDiagonal(rowPos, colPos));
    }

    /**
     * This method checks if there is a winner across horizontally.
     * Returns true is there is a winner, else false.
     */
    private boolean isWinnerHorizontal(int rowPos){
        int numInARow = 1;
        for(int i=0;i<connectFourBoard[1].length-1;i++){
            if(connectFourBoard[rowPos][i] != '\u0000' && (connectFourBoard[rowPos][i] == connectFourBoard[rowPos][i+1])) numInARow++;
            else numInARow =1;
            if(numInARow == 4){//todo get the current piece and get the positions of all the winning pieces.
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if there is a winner across vertically.
     * Returns true is there is a winner, else false.
     */
    private boolean isWinnerVertical(int colPos){
        int numInARow = 1;
        for(int j=0;j< connectFourBoard.length-1;j++){
            if(connectFourBoard[j][colPos] != '\u0000' && (connectFourBoard[j][colPos] == connectFourBoard[j+1][colPos])) numInARow++;
            else numInARow =1;
            if(numInARow == 4) return true;
        }
        return false;
    }

    /**
     * This method calls the 2 diagonal checks.
     */
    private boolean isWinnerDiagonal(int rowPos,int colPos){
        return (isWinnerPositiveDiagonal(rowPos,colPos) || isWinnerNegativeDiagonal(rowPos,colPos));
    }

    /**
     * This method checks all the diagonals in a positive sloping fashion.
     * Returns true is there is a winner, else false.
     */
    private boolean isWinnerPositiveDiagonal(int rowPos,int colPos){
        int currentRowPos = rowPos;
        int currentColPos = colPos;
        int numInARow =1;

        //up and to the right.
        while(currentColPos<(connectFourBoard[1].length-1) && currentRowPos > 0){
            if(connectFourBoard[currentRowPos][currentColPos] != '\u0000' && (connectFourBoard[currentRowPos][currentColPos] == connectFourBoard[currentRowPos-1][currentColPos+1])) numInARow++;
            else numInARow =1;
            if(numInARow == 4) return true;
            currentColPos++;
            currentRowPos--;
        }

        currentRowPos = rowPos;
        currentColPos = colPos;
        numInARow =1;
        //down and to the left.
        while(currentColPos > 0 && currentRowPos < connectFourBoard.length-1){
            if(connectFourBoard[currentRowPos][currentColPos] != '\u0000' && (connectFourBoard[currentRowPos][currentColPos] == connectFourBoard[currentRowPos+1][currentColPos-1])) numInARow++;
            else numInARow =1;
            if(numInARow == 4) return true;
            currentColPos--;
            currentRowPos++;
        }

        return false;
    }

    /**
     * This method checks all diagonals in a negative sloping fashion.
     * Returns true is there is a winner, else false.
     */
    private boolean isWinnerNegativeDiagonal(int rowPos, int colPos){
        int currentRowPos = rowPos;
        int currentColPos = colPos;
        int numInARow =1;

        //up and to the left.
        while(currentRowPos > 0 && currentColPos > 0){
            if(connectFourBoard[currentRowPos][currentColPos] != '\u0000' && (connectFourBoard[currentRowPos][currentColPos] == connectFourBoard[currentRowPos-1][currentColPos-1])) numInARow++;
            else numInARow =1;
            if(numInARow == 4) return true;
            currentColPos--;
            currentRowPos--;
        }

        currentRowPos = rowPos;
        currentColPos = colPos;
        numInARow =1;

        //down and to the right.
        while(currentRowPos < connectFourBoard.length-1 && currentColPos < connectFourBoard[1].length-1){
            if(connectFourBoard[currentRowPos][currentColPos] != '\u0000' && (connectFourBoard[currentRowPos][currentColPos] == connectFourBoard[currentRowPos+1][currentColPos+1])) numInARow++;
            else numInARow =1;
            if(numInARow == 4) return true;
            currentColPos++;
            currentRowPos++;
        }

        return false;
    }

    /**
     * Get the connectFourBoard object.
     */
    public char[][] getConnectFourBoard() {
        return connectFourBoard;
    }

}
