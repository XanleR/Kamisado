package GameLogic;

import java.io.Serializable;

public enum PlayerSide implements Serializable {
    PLAYER_1,
    PLAYER_2,
    DEFAULT;

    public String toString(){
        switch (this){
            case PLAYER_1 -> {
                return "PLAYER_1";
            }
            case PLAYER_2 -> {
                return "PLAYER_2";
            }
        }
        return "DEFAULT";
    }
}
