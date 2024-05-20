package Graphics;

import GameLogic.Square;

import javax.swing.*;

public class myJButton extends JButton {
    private Square square;

    public myJButton(Square sq){
        square = sq;
    }

    public void setSquare(Square sq){
        square = sq;
    }

    public Square getSquare() {
        return square;
    }
}
