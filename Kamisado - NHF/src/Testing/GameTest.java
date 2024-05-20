package Testing;

import GameLogic.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class GameTest {

    Board board;
    DragonTower dragonTower;

    @Before
    public void before(){
        board = new Board();
        board.generateBoard("BoardColors.txt");

        dragonTower = new DragonTower(GameColor.PINK, PlayerSide.PLAYER_1);
        board.at(0, 0).setPiece(dragonTower);
    }

    @Test
    public void checkRoundEnd(){
        Assert.assertTrue(Game.checkRoundEnd(board));
    }

    @Test
    public void winningSquare(){
        Assert.assertSame(Game.winningPiece(board), dragonTower);
    }

    @Test
    public void save(){
        int player1Pints = Game.getPoints(PlayerSide.PLAYER_1);
        int player2Pints = Game.getPoints(PlayerSide.PLAYER_2);
        int pointsToWIn = Game.getPointsToWin();
        int maxSumoLevel = Game.getMaxSumoLevel();

        try {
            Game.save();
            Game.load();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Assert.assertEquals(player1Pints, Game.getPoints(PlayerSide.PLAYER_1));
        Assert.assertEquals(player2Pints, Game.getPoints(PlayerSide.PLAYER_2));
        Assert.assertEquals(pointsToWIn, Game.getPointsToWin());
        Assert.assertEquals(maxSumoLevel, Game.getMaxSumoLevel());


    }
}
