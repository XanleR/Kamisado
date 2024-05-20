package Graphics;

import GameLogic.Game;
import GameLogic.Move;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MenuFrame extends JFrame implements ActionListener {
    private static JFrame frame;
    private final JComboBox<String> comboBox;

    GuiFrame guiFrame;
    Options optionsFrame;


    public MenuFrame(){
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton action = new JButton("ACTION");
        action.addActionListener(this);

        String[] test = {"New Game", "Load Game", "Options", "Exit"};
        comboBox = new JComboBox<>(test);

        frame.add(comboBox);
        frame.add(action);
        frame.setSize(350, 100);
        frame.setLayout(new GridLayout(1, 2));

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            String selected = (String) comboBox.getSelectedItem();
            switch (Objects.requireNonNull(selected)){
                case "New Game":
                    frame.setEnabled(false);
                    Game.setUpNewGame();
                    Move.setUpNewGame();
                    guiFrame = new GuiFrame();
                    try {
                        guiFrame.paintBoard();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Load Game":
                    try {
                        Move.load();
                        Game.load();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    guiFrame = new GuiFrame();
                    frame.setEnabled(false);
                    guiFrame.loadBoard();
                    guiFrame.generateButtons();
                    try {
                        guiFrame.paintBoard();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Options":
                    Options opt = new Options();
                    frame.setEnabled(false);
                    break;
                case "Exit":
                    System.exit(0);
                default:
                    throw new IllegalStateException("Unexpected value: " + Objects.requireNonNull(selected));
            }
        //}
    }

    public static void setEnable(boolean bool){
        if(frame !=  null)
            frame.setEnabled(bool);
    }

    public static void main(String[] args){
        MenuFrame menu = new MenuFrame();
        frame.setVisible(true);
    }

}
