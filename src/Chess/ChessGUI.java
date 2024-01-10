package Chess;

import javax.swing.*;

public class ChessGUI extends JFrame {

    private ChessGameState gameState;
    private ChessBoard chessBoard;


    public ChessGUI(ChessGameState gameState) {
        this.gameState = gameState;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);


        this.chessBoard = new ChessBoard(gameState);
        this.add(chessBoard);


        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }




    public static void main(String[] args) {

        ChessGameState gameState = new ChessGameState();

        SwingUtilities.invokeLater(() -> new ChessGUI(gameState));


    }

}
