package ConnectFourService;

import Exceptions.ColumnFullException;

public class ConnectFour {
    private char[][] connectFourBoard = new char[6][7];
    private boolean yellowTurn;
    private boolean isGameRunning;

    public ConnectFour(){
        yellowTurn = true;
        isGameRunning = true;
    }

    /**
     * Increment the turn and make it the next turn.
     * Will be called internally from within the ConnectFour Class.
     */
    private void nextTurn(){
        if(yellowTurn){
            yellowTurn = false;
        }else{
            yellowTurn = true;
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
        nextTurn();
    }

    /**
     * This method will find the next row position that is available when the piece is dropped.
     */
    public int nextAvailablePosition(int colPos) throws ColumnFullException{
        //This will start going through all the rows as length of 2D array is height, [1].length would be width.
        for(int i= connectFourBoard.length-1;i>=0;i--){
            if(connectFourBoard[i][colPos] == '\u0000') return i;
        }
        //All the cells in the column is full!
        throw new ColumnFullException("Column at colPos "+colPos+" is completely full.");
    }

    public boolean dropPiece(int colPos){
        try{
            int nextAvailableRow = nextAvailablePosition(colPos);
            putPiece(nextAvailableRow,colPos);
            if(isWinner(nextAvailableRow,colPos)) return true; // if we have a winner, return true, else continue on.
            isGameRunning = !isCompletelyFull(); //if the board is full, end the game, else continue. //todo make this so it actually counts num pieces out.
            return true; //if we made it here, the piece drop was valid.
        }catch (ColumnFullException e){
            //we messed up, and we tried to put a piece in a column that was full!
            System.out.println(e.getMessage()); //todo REMOVE THIS DUG MESSAGE later.
            return false;
        }
    }

    /**
     * Returns true if it is yellow's turn, else returns false.
     */
    public boolean isYellowTurn(){
        return yellowTurn;
    }

    /**
     * This method returns true if the board is completely full.
     */
    public boolean isCompletelyFull(){
        for(int i=0;i<connectFourBoard.length;i++){
            for(int j=0;j< connectFourBoard[1].length;j++){
                if(connectFourBoard[i][j] == '\u0000') return false; //if the slot is null, return false.
            }
        }
        return true;
    }

    /**
     * This method calls all the other smaller methods to check to see if there is a winner.
     * Returns true is there is a winner, else false.
     */
    //todo make this multithreaded.
    public boolean isWinner(int rowPos,int colPos){
        return (isWinnerHorizontal(rowPos) || isWinnerVertical(colPos) || isWinnerDiagonal(rowPos,colPos));
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
            if(numInARow == 4) return true;
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

}
