package Chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ChessBoard extends JPanel {


    private Vector<BufferedImage> peaces;
    private ChessGameState gameState;
    private JButton[][] buttons;



    public ChessBoard(ChessGameState gameState) {
        this.gameState = gameState;
        this.setBackground(Color.red);
        this.setLayout(new GridLayout(8, 8));

        this.peaces = loadPeaces("/home/lilwizzz/Desktop/MCTS.MCTS/assets/peaces");

        this.buttons = new JButton[8][8];
        boolean light = false;
        Color c1 = new Color(8, 163, 121);
        Color c2 = new Color(151, 245, 218);
        EventHandler eh = new EventHandler();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton(getAppropriatePeace(i, j));
                button.addActionListener(eh);
                button.setPreferredSize(new Dimension(70, 70));
                button.setBackground(light ? c1 : c2);
                buttons[i][j] = button;
                this.add(button);
                light = !light;
            }
            light = !light;
        }



    }


    private class EventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            JButton button = (JButton) e.getSource();

            int x = 0;
            int y = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (button == buttons[i][j]) {
                        x = i;
                        y = j;
                        break;
                    }
                }
            }

            System.out.println("i: " + x + "  j: " + y);
            
        }
    }



    private Vector<BufferedImage> loadPeaces(String dirPath) {
        Vector<BufferedImage> imageVector = new Vector<>();
        File directory = new File(dirPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                    try {
                        BufferedImage image = ImageIO.read(file);
                        imageVector.add(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return imageVector;
    }


    private ImageIcon getAppropriatePeace(int i, int j) {
        ImageIcon icon = null;
        if (i == 1) icon = new ImageIcon(peaces.get(2));
        else if (i == 6) icon = new ImageIcon(peaces.get(8));
        else if (i == 0 && j == 2 || i == 0 && j == 5) icon = new ImageIcon(peaces.get(5));
        else if (i == 0 && j == 1 || i == 0 && j == 6) icon = new ImageIcon(peaces.get(6));
        else if (i == 7 && j == 2 || i == 7 && j == 5) icon = new ImageIcon(peaces.get(11));
        else if (i == 0 && j == 0 || i == 0 && j == 7) icon = new ImageIcon(peaces.get(0));
        else if (i == 7 && j == 0 || i == 7 && j == 7) icon = new ImageIcon(peaces.get(7));
        else if (i == 7 && j == 1 || i == 7 && j == 6) icon = new ImageIcon(peaces.get(1));
        else if (i == 0 && j == 4) icon = new ImageIcon(peaces.get(4));
        else if (i == 0 && j == 3) icon = new ImageIcon(peaces.get(3));
        else if (i == 7 && j == 4) icon = new ImageIcon(peaces.get(10));
        else if (i == 7 && j == 3) icon = new ImageIcon(peaces.get(9));
        return icon;
    }




}
