package TicTacToe;

import MCTS.Action;
import MCTS.State;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TTTGameState implements State {


    private final char EMPTY_CELL = '-';
    private final int BOARD_SIZE = 3;
    private char[][] board;
    private final char MCTS_AGENT;
    private final char HUMAN_PLAYER;
    private char CURRENT_PLAYER;


    public TTTGameState(char HUMAN_PLAYER, char MCTS_AGENT) {
        this.HUMAN_PLAYER = HUMAN_PLAYER;
        this.MCTS_AGENT = MCTS_AGENT;
        this.board = new char[BOARD_SIZE][BOARD_SIZE];
        this.CURRENT_PLAYER = 'x';
        Arrays.stream(board).forEach(row -> Arrays.fill(row, EMPTY_CELL));
    }


    public void printBoard() {
        Arrays.stream(board).map(String::new).map(r -> r.replace("", " ").trim()).forEach(System.out::println);
        System.out.println();
    }

    public boolean isMCTSWinner() {
        return checkRows(MCTS_AGENT) || checkColumns(MCTS_AGENT) || checkDiagonals(MCTS_AGENT);
    }

    public boolean isHumanWinner() {
        return checkRows(HUMAN_PLAYER) || checkColumns(HUMAN_PLAYER) || checkDiagonals(HUMAN_PLAYER);
    }

    public boolean isDraw() {
        return Arrays.stream(board).flatMapToInt(row -> new String(row).chars()).noneMatch(cell -> cell == EMPTY_CELL);
    }


    private boolean checkRows(char player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != EMPTY_CELL && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0] == player;
            }
        }
        return false;
    }

    private boolean checkColumns(char player) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[0][j] != EMPTY_CELL && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j] == player;
            }
        }
        return false;
    }

    private boolean checkDiagonals(char player) {
        if (board[0][0] != EMPTY_CELL && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0] == player;
        } else if (board[0][2] != EMPTY_CELL && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2] == player;
        }
        return false;
    }

    private void switchPlayer() {
        CURRENT_PLAYER = (CURRENT_PLAYER == HUMAN_PLAYER) ? MCTS_AGENT : HUMAN_PLAYER;
    }

    public boolean isValidMove(int row, int col) {
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
        return IntStream.range(0, BOARD_SIZE).boxed()
                .flatMap(i -> IntStream.range(0, BOARD_SIZE)
                .filter(j -> board[i][j] == EMPTY_CELL)
                .mapToObj(j -> new Move(i, j))).collect(Collectors.toList());
    }

    @Override
    public void performAction(Action action) {
        Move move = (Move) action;
        board[move.getRow()][move.getCol()] = CURRENT_PLAYER;
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
        TTTGameState clonedState = new TTTGameState(HUMAN_PLAYER, MCTS_AGENT);
        clonedState.setCURRENT_PLAYER(this.CURRENT_PLAYER);
        char[][] copy = Arrays.stream(board).map(row -> Arrays.copyOf(row, row.length)).toArray(char[][]::new);
        clonedState.setBoard(copy);
        return clonedState;
    }

    @Override
    public boolean isTerminal() {
        return isDraw() || isMCTSWinner() || isHumanWinner();
    }

    @Override
    public double getSimulationOutcome() {
        return isDraw() ? 0 : (isMCTSWinner() ? 1 : -1);
    }

    @Override
    public boolean isMCTSAgentState() {
        return CURRENT_PLAYER != MCTS_AGENT;
    }
}

