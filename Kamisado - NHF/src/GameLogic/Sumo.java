package GameLogic;

import java.io.Serializable;

public class Sumo extends Piece implements Serializable {
    private int sumoLevel;

    public Sumo(DragonTower dragonTower){
        this.gameColor = dragonTower.gameColor;
        this.side = dragonTower.side;
        this.movingDistance = 5;
        sumoLevel = 1;
        pointWorth = 3;
    }

    public void upgrade(){
        if(sumoLevel != 3){
            sumoLevel++;
            if(sumoLevel == 2){
                movingDistance = 3;
                pointWorth = 5;
            }
            if(sumoLevel == 3){
                movingDistance = 1;
                pointWorth = 15;
            }
        }
    }
    public int getSumoLevel(){
        return sumoLevel;
    }


}
