package GameLogic;

import java.io.Serializable;

public class DragonTower extends Piece implements Serializable {
    public DragonTower(GameColor gameColor, PlayerSide side){
        super(gameColor, 8, side);
    }
}
