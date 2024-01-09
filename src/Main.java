import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        boolean humanFirst = true;
        char h = humanFirst ? 'x' : 'o';
        char m = humanFirst ? 'o' : 'x';


        Scanner sc = new Scanner(System.in);

        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
        TicTacToeGameState gameState = new TicTacToeGameState(h, m);

        System.out.println("Initial game state:");
        gameState.printBoard();

        while (!gameState.isTerminal()) {
            if (humanFirst) {
                // for human, we are performing random action
                System.out.print("Enter move: ");
                String[] tokens = sc.nextLine().split(" ");
                int row = Integer.parseInt(tokens[0]);
                int col = Integer.parseInt(tokens[1]);

                System.out.println("After human [" + gameState.getCURRENT_PLAYER() + "] move:");

                gameState.performAction(new Move(row, col));
                humanFirst = false;
            } else {
                System.out.println("After MCTS [" + gameState.getCURRENT_PLAYER() + "] move:");
                gameState = (TicTacToeGameState) mcts.search(gameState, 100);

                // at lest one move by MCTS has to be made before calling this function
                System.out.println("MCTS depth: " + mcts.getDepth());
                humanFirst = true;
            }
            gameState.printBoard();
        }

        System.out.println("MCTS reward: " + gameState.getSimulationOutcome());


    }
}