package Graphics;

import GameLogic.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GuiFrame extends JFrame implements ActionListener {

    private Board board;
    private JFrame frame;
    private JLabel p1Score;
    private JLabel p2Score;
    private JLabel log;
    private JLabel pointToWin;
    private myJButton lastPressed;
    private JButton saveButton;

    private myJButton[][] buttons;
    private JButton goku;
    private JButton onePiece;


    public GuiFrame() {

        board = new Board();

        frame = new JFrame();
        frame.setName("Kamisado");
        frame.setResizable(true);
        frame.setSize(600, 600);

        buttons = new myJButton[8][8];

        board.generateBoard("Assets/TextFiles/BoardColors.txt");
        board.generatePieces();


        //setting the buttons background to paint the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                myJButton jButton = new myJButton(board.at(i, j));
                switch(board.at(i, j).getColor()){
                    case ORANGE -> jButton.setBackground(new Color(255, 165, 0));
                    case BROWN -> jButton.setBackground(new Color(139, 69, 19));
                    case YELLOW -> jButton.setBackground(new Color(255, 255, 0));
                    case PINK -> jButton.setBackground(new Color(255, 20, 147));
                    case BLUE -> jButton.setBackground(new Color(0, 0, 255));
                    case PURPLE -> jButton.setBackground(new Color(128, 0, 128));
                    case RED -> jButton.setBackground(new Color(255, 0, 0));
                    case GREEN -> jButton.setBackground(new Color(0, 255, 0));
                }
                jButton.setBorder(null);
                jButton.addActionListener(this);

                buttons[i][j] = jButton;
                frame.add(buttons[i][j]);
            }
        }

        //last row labels
        p1Score = new JLabel("Player1: 0");
        p2Score = new JLabel("Player2: 0");
        log = new JLabel("Kamisado");
        pointToWin = new JLabel("To win: " + Game.getPointsToWin());
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (!Game.getEnded()) {
                try {
                    board.save();
                    Move.save();
                    Game.save();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.add(p1Score);
        frame.add(p2Score);
        frame.add(pointToWin);
        frame.add(log);
        frame.add(saveButton);

        //shh...
        goku = new JButton();
        goku.setBackground(new Color(238, 238, 238));
        goku.setBorder(null);
        goku.addActionListener(e -> {

            File f = new File("Assets/Goku.wav");
            AudioInputStream audioIn = null;
            try {
                audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            } catch (UnsupportedAudioFileException | IOException ex) {
                throw new RuntimeException(ex);
            }
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            try {
                clip.open(audioIn);
            } catch (LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            clip.start();
            goku.setIcon(PieceAssets.getGoku());
        });

        onePiece = new JButton();
        onePiece.setBackground(new Color(238, 238, 238));
        onePiece.setBorder(null);
        onePiece.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URL("https://youtu.be/gWo12TtN9Kk").toURI());
            } catch (Exception ignored) {}
        });

        frame.add(goku);
        frame.add(onePiece);



        frame.setLayout(new GridLayout(9, 9));
        frame.setVisible(true);
        frame.addWindowListener(new CustomWindowListener());


    }


    public void paintBoard() throws IOException {
        if (!PieceAssets.isGenerated()) {
            PieceAssets.generate();
        }

        //setting/updating the buttons background
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.at(i, j).hasPiece()) {
                    if (board.at(i, j).getPiece().getClass() == Sumo.class) {
                        buttons[i][j].setIcon(PieceAssets.getSumo(board.at(i, j).getPiece().getColor(),
                                board.at(i, j).getPiece().getSide(),
                                ((Sumo) board.at(i, j).getPiece()).getSumoLevel()));
                    } else {
                        buttons[i][j].setIcon(PieceAssets.get(board.at(i, j).getPiece().getColor(), board.at(i, j).getPiece().getSide()));
                    }

                } else {
                    buttons[i][j].setIcon(null);
                }
            }
        }

        //checking if the Game was ended
        if (!Game.getEnded()) {
            String tmp;
            if (Move.getLastPlayer() == PlayerSide.PLAYER_1) {
                tmp = "White: ";
            } else {
                tmp = "Black: ";
            }
            if (Move.getLastGameColor() != GameColor.DEFAULT) {
                tmp += Move.getLastGameColor().toString();
            }
            log.setText(tmp);
        } else {
            if (Game.winner() == PlayerSide.PLAYER_1) {
                log.setText("Player 1 Won!!");
            }
            if (Game.winner() == PlayerSide.PLAYER_2) {
                log.setText("Player 2 Won!!");
            }
        }
        p1Score.setText("P1: " + Game.getPoints(PlayerSide.PLAYER_1));
        p2Score.setText("P2: " + Game.getPoints(PlayerSide.PLAYER_2));

    }


    //if the main board buttons were pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Game.getEnded()) {
            if (lastPressed == null) {
                lastPressed = (myJButton) e.getSource();
            } else {
                if (!Game.checkRoundEnd(board)) {
                    myJButton tmp = (myJButton) e.getSource();
                    if(tmp == lastPressed){
                        Move.push(board, tmp.getSquare());
                    }
                    else{
                        Move.move(lastPressed.getSquare(), tmp.getSquare());
                    }

                    lastPressed = null;
                }
                if (Game.checkRoundEnd(board)) {
                    Game.incrementPoints(board);
                    Move.setUpNewRound();

                    if (Game.winningPiece(board).getClass() == DragonTower.class){
                        if(Game.getMaxSumoLevel() > 0) {
                            Sumo sumotmp = new Sumo((DragonTower) Game.winningPiece(board));
                            Square squaretmp = Game.winningSquare(board);
                            board.at(squaretmp.getY(), squaretmp.getX()).setPiece(sumotmp);
                        }
                    }
                    else {
                        if(((Sumo) Game.winningPiece(board)).getSumoLevel() < Game.getMaxSumoLevel()){
                            ((Sumo) Game.winningPiece(board)).upgrade();
                        }
                    }
                    if (Game.checkGameEnd()) {
                        Game.endGame();
                    } else {
                        board.regenerateBoard();
                        generateButtons();
                    }
                    lastPressed = null;
                }
                try {
                    paintBoard();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void loadBoard() {
        try {
            board.load();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateButtons() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                buttons[i][j].setSquare(board.at(i, j));
    }

    public static void main(String[] args) throws IOException {

        GuiFrame guiFrame = new GuiFrame();
        guiFrame.frame.setVisible(true);

        guiFrame.paintBoard();

    }

    static class CustomWindowListener implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            MenuFrame.setEnable(true);
        }

        @Override
        public void windowClosed(WindowEvent e) {
            MenuFrame.setEnable(true);
        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }


}