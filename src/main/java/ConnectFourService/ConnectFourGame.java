package ConnectFourService;

public class ConnectFourGame {
    private Player yellow = new Player(true);
    private Player red = new Player(false);
    private static ConnectFourService service;

    /**
     * Default constructor creates a connect-four game where yellow goes first.
     */
    public ConnectFourGame(){
        service = new ConnectFourService();
    }

    /**
     * Overloaded constructor that creates a connect-four game which specified player goes first.
     */
    public ConnectFourGame(boolean yellowTurn){
        service = new ConnectFourService(yellowTurn);
    }
}
