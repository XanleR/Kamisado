package Graphics;

import GameLogic.Game;
import GameLogic.Move;
import GameLogic.PlayerSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Options extends JFrame implements ActionListener {

    JFrame frame;
    JComboBox<String> comboBox;
    JButton button;
    JTextField textField;
    JLabel startingSide;
    JLabel pointsToWin;
    JLabel maxSumo;

    public Options(){
        frame = new JFrame("Options");
        frame.setSize(600, 150);

        button = new JButton("Set");
        button.addActionListener(this);

        textField = new JTextField();

        String[] test = {"Starting side (Black or White)", "Points to Win (1-15)", "Max Sumo Level (0-3)"};
        comboBox = new JComboBox<>(test);

        String tmp = "Starting Side:";
        if(Move.getLastPlayer() == PlayerSide.PLAYER_1){
            tmp += " White";
        }
        else{
            tmp += " Black";
        }
        startingSide = new JLabel(tmp);



        pointsToWin = new JLabel("Points To Win: " + Game.getPointsToWin());
        maxSumo = new JLabel("Max Sumo Level: " + Game.getMaxSumoLevel());

        frame.add(comboBox);
        frame.add(textField);
        frame.add(button);

        frame.add(startingSide);
        frame.add(pointsToWin);
        frame.add(maxSumo);

        frame.setLayout(new GridLayout(2, 3));
        frame.addWindowListener(new CustomWindowListener());

        frame.setVisible(true);
    }

    public static void main(String[] args){
        Options options = new Options();

    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selected = (String) comboBox.getSelectedItem();

        switch (selected){
            case "Starting side (Black or White)":
                if(textField.getText().equalsIgnoreCase("black")){
                    Move.setLastPlayer(PlayerSide.PLAYER_2);
                }
                else if(textField.getText().equalsIgnoreCase("white")){
                    Move.setLastPlayer(PlayerSide.PLAYER_1);
                }
                String tmp = "Starting Side: ";
                if(Move.getLastPlayer() == PlayerSide.PLAYER_1){
                    tmp += "White";
                }
                else{
                    tmp += "Black";
                }
                startingSide.setText(tmp);
                break;
            case "Points to Win (1-15)":
                if(isNumeric(textField.getText())){
                    int n = Integer.parseInt(textField.getText());
                    if(n >= 1 && n <= 15){
                        Game.setPointsToWin(n);
                        pointsToWin.setText("Points To Win: " + Game.getPointsToWin());
                    }
                }
                break;
            case "Max Sumo Level (0-3)":
                if(isNumeric(textField.getText())){
                    int n = Integer.parseInt(textField.getText());
                    if(n >= 0 && n <= 3){
                        Game.setMaxSumoLevel(n);
                        maxSumo.setText("Max Sumo Level: " + Game.getMaxSumoLevel());
                    }
                }
        }
    }
    static class CustomWindowListener implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            MenuFrame.setEnable(true);
        }

        @Override
        public void windowClosed(WindowEvent e) {

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
