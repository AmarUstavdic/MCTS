import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean humanFirst = true;
        char h = humanFirst ? 'x' : 'o';
        char m = humanFirst ? 'o' : 'x';

        Scanner sc = new Scanner(System.in);

        MCTS mcts = new MCTS();
        TTTGameState gameState = new TTTGameState(h, m);

        System.out.println("Initial game state:");
        gameState.printBoard();

        while (!gameState.isTerminal()) {
            if (humanFirst) {

                int row, col;
                while (true) {
                    System.out.print("Enter move: ");
                    String[] tokens = sc.nextLine().split(" ");
                    row = Integer.parseInt(tokens[0]);
                    col = Integer.parseInt(tokens[1]);
                    if (gameState.isValidMove(row, col)) break;
                    System.out.println("Invalid move. Try again: ");
                }

                System.out.println("After human [" + gameState.getCURRENT_PLAYER() + "] move:");

                gameState.performAction(new Move(row, col));
                humanFirst = !humanFirst;
            } else {
                System.out.println("After MCTS [" + gameState.getCURRENT_PLAYER() + "] move:");
                gameState = (TTTGameState) mcts.search(gameState, 500);

                // at lest one move by MCTS has to be made before calling this function
                System.out.println("MCTS depth: " + mcts.getDepth());
                humanFirst = !humanFirst;
            }
            gameState.printBoard();
        }

        System.out.println("MCTS reward: " + gameState.getSimulationOutcome());

    }
}