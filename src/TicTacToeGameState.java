import java.util.*;

public class TicTacToeGameState implements State {


    private final char EMPTY_CELL = '-';
    private final int BOARD_SIZE = 3;
    private char[][] board;
    private final char MCTS_AGENT;
    private final char HUMAN_PLAYER;
    private char CURRENT_PLAYER;
    private char LAST_TO_PLAY;

    public TicTacToeGameState(char HUMAN_PLAYER, char MCTS_AGENT) {
        this.HUMAN_PLAYER = HUMAN_PLAYER;
        this.MCTS_AGENT = MCTS_AGENT;
        this.board = new char[BOARD_SIZE][BOARD_SIZE];
        this.CURRENT_PLAYER = 'x';
        Arrays.stream(board).forEach(row -> Arrays.fill(row, EMPTY_CELL));
    }




    public void printBoard() {
        Arrays.stream(board)
                .map(String::new)
                .map(rowString -> rowString
                .replace("", " ")
                .trim()).forEach(System.out::println);
        System.out.println();
    }

    public boolean isWinner() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    public boolean isDraw() {
        return Arrays.stream(board).flatMapToInt(row -> new String(row).chars()).noneMatch(cell -> cell == EMPTY_CELL);
    }


    private boolean checkRows() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != EMPTY_CELL && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[0][j] != EMPTY_CELL && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        if (board[0][0] != EMPTY_CELL && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true;
        } else return board[0][2] != EMPTY_CELL && board[0][2] == board[1][1] && board[1][1] == board[2][0];
    }

    private void switchPlayer() {
        CURRENT_PLAYER = (CURRENT_PLAYER == 'x') ? 'o' : 'x';
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && board[row][col] == EMPTY_CELL;
    }


    public void setBoard(char[][] board) {
        this.board = board;
    }

    public void setCURRENT_PLAYER(char CURRENT_PLAYER) {
        this.CURRENT_PLAYER = CURRENT_PLAYER;
    }

    public char getCURRENT_PLAYER() {
        return CURRENT_PLAYER;
    }

    @Override
    public List<Action> getAvailableActions() {
        if (isTerminal()) return null; // if game draw, win or lose
        List<Action> availableActions = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    availableActions.add(new Move(i, j));
                }
            }
        }
        return availableActions;
    }

    @Override
    public void performAction(Action action) {
        Move move = (Move) action;
        board[move.getRow()][move.getCol()] = CURRENT_PLAYER;
        this.LAST_TO_PLAY = CURRENT_PLAYER;
        switchPlayer();
    }

    @Override
    public void performRandomAction() {

        List<Action> availableActions = this.getAvailableActions();
        Collections.shuffle(availableActions);
        this.performAction(availableActions.getFirst());
    }

    @Override
    public State deepCopy() {
        TicTacToeGameState clonedState = new TicTacToeGameState(HUMAN_PLAYER, MCTS_AGENT);
        clonedState.setCURRENT_PLAYER(this.CURRENT_PLAYER);
        char[][] copy = Arrays.stream(board).map(row -> Arrays.copyOf(row, row.length)).toArray(char[][]::new);
        clonedState.setBoard(copy);
        return clonedState;
    }

    @Override
    public boolean isTerminal() {
        return isWinner() || isDraw();
    }

    @Override
    public boolean equals(State state) {
        TicTacToeGameState gameState = (TicTacToeGameState) state;
        return Arrays.deepEquals(board, gameState.board);
    }

    @Override
    public char getLastToPlay() {
        return LAST_TO_PLAY;
    }

    @Override
    public char getCurrentAgent() {
        return CURRENT_PLAYER;
    }

    @Override
    public char getMCTSAgent() {
        return MCTS_AGENT;
    }

    @Override
    public double getSimulationOutcome() {

        // TODO: Play with this to get it right.

        double reward;

        boolean isMCTSLastPlayer = (this.LAST_TO_PLAY == this.MCTS_AGENT);

        // Set the reward based on the conditions
        if (isWinner()) {
            reward = isMCTSLastPlayer ? 1 : -1;
        } else if (isDraw()) {
            reward = 0;
        } else {
            reward = isMCTSLastPlayer ? -1 : 1;
        }

        return reward;
    }



}

