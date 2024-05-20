package GameLogic;

import java.io.Serializable;

public enum GameColor implements Serializable {

    ORANGE,
    BLUE,
    PURPLE,
    PINK,
    YELLOW,
    RED,
    GREEN,
    BROWN,
    DEFAULT;

    public String toString(){
        switch (this){
            case ORANGE -> {return "ORANGE";}
            case BLUE -> {return "BLUE";}
            case PURPLE -> {return "PURPLE";}
            case PINK -> {return "PINK";}
            case YELLOW -> {return "YELLOW";}
            case RED -> {return "RED";}
            case GREEN -> {return "GREEN";}
            case BROWN -> {return "BROWN";}
        }
        return "DEFAULT";
    }
}
