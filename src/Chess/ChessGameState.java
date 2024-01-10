package Chess;

import MCTS.Action;
import MCTS.State;

import java.util.List;

public class ChessGameState implements State {

    public static final int EMPTY = 0;
    public static final int PAWN_WHITE = 1;
    public static final int ROOK_WHITE = 2;
    public static final int KNIGHT_WHITE = 3;
    public static final int BISHOP_WHITE = 4;
    public static final int QUEEN_WHITE = 5;
    public static final int KING_WHITE = 6;
    public static final int PAWN_BLACK = -1;
    public static final int ROOK_BLACK = -2;
    public static final int KNIGHT_BLACK = -3;
    public static final int BISHOP_BLACK = -4;
    public static final int QUEEN_BLACK = -5;
    public static final int KING_BLACK = -6;

    private int[][] board;


    public ChessGameState() {
        this.board = initBoard();
    }


    private int[][] initBoard() {
        return new int[][] {
                {ROOK_BLACK, KNIGHT_BLACK, BISHOP_BLACK, QUEEN_BLACK, KING_BLACK, BISHOP_BLACK, KNIGHT_BLACK, ROOK_BLACK},
                {PAWN_BLACK, PAWN_BLACK, PAWN_BLACK, PAWN_BLACK, PAWN_BLACK, PAWN_BLACK, PAWN_BLACK, PAWN_BLACK},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {PAWN_WHITE, PAWN_WHITE, PAWN_WHITE, PAWN_WHITE, PAWN_WHITE, PAWN_WHITE, PAWN_WHITE, PAWN_WHITE},
                {ROOK_WHITE, KNIGHT_WHITE, BISHOP_WHITE, QUEEN_WHITE, KING_WHITE, BISHOP_WHITE, KNIGHT_WHITE, ROOK_WHITE}
        };
    }


    @Override
    public List<Action> getAvailableActions() {
        return null;
    }

    @Override
    public void performAction(Action action) {

    }

    @Override
    public void performRandomAction() {

    }

    @Override
    public State deepCopy() {
        return null;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public double getSimulationOutcome() {
        return 0;
    }

    @Override
    public boolean isMCTSAgentState() {
        return false;
    }
}
