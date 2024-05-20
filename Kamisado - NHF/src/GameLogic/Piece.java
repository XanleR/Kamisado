package GameLogic;

import java.io.Serializable;

public class /*One*/ Piece /*is real*/ implements Serializable {
    protected GameColor gameColor;
    protected int movingDistance;
    protected PlayerSide side;

    protected int pointWorth = 1;

    public Piece(){}

    public Piece(GameColor gameColor, int movingDistance, PlayerSide side){
        this.gameColor = gameColor;
        this.movingDistance = movingDistance;
        this.side = side;
    }

    public int getMovingDistance(){return movingDistance;}

    public PlayerSide getSide(){
        if(side ==  null){
            return PlayerSide.DEFAULT;
        }
        return side;
    }
    public void setSide(PlayerSide side){
        this.side = side;
    }

    public GameColor getColor(){
        return gameColor;
    }

    public int getPointWorth() {
        return pointWorth;
    }
}
