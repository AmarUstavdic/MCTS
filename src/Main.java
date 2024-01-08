public class Main {
    public static void main(String[] args) {





        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();


        TicTacToeGameState gameState = new TicTacToeGameState('x', 'o');

        System.out.println("Initial game state:");
        gameState.printBoard();

        boolean human = true;
        char lastMove = ' ';
        while (!gameState.isTerminal()) {
            if (human) {
                // for human, we are performing random action
                lastMove = gameState.getCURRENT_PLAYER();
                System.out.println("After human [" + gameState.getCURRENT_PLAYER() + "] move:");
                gameState.performRandomAction();
                human = false;
            } else {
                lastMove = gameState.getCURRENT_PLAYER();
                System.out.println("After MCTS [" + gameState.getCURRENT_PLAYER() + "] move:");
                gameState = (TicTacToeGameState) mcts.search(gameState, 300);
                human = true;
            }
            gameState.printBoard();
        }

        System.out.println("MCTS [" + lastMove +"] reward: " + gameState.getValue());








    }
}