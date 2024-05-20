package Testing;

import GameLogic.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {

    Square square1;
    Square square2;

    DragonTower dragonTower1;


    @Before
    public void before(){
        square1 = new Square(0, 3, null, GameColor.GREEN);

        dragonTower1 = new DragonTower(GameColor.BROWN, PlayerSide.PLAYER_1);
        square2 = new Square(0, 7, dragonTower1, GameColor.BROWN);

        Move.setUpNewGame();
    }



    @Test
    public void isMovePossible(){
        Assert.assertTrue(Move.isMovePossible(square2, square1));
    }

    @Test
    public void move(){
        Assert.assertTrue(Move.isMovePossible(square2, square1));

        Move.move(square2, square1);

        Assert.assertFalse(square2.hasPiece());
        Assert.assertTrue(square1.hasPiece());
        Assert.assertSame(square1.getPiece(), dragonTower1);

    }

    @Test
    public void lastVariables(){
        Move.move(square2, square1);

        Assert.assertEquals(Move.getLastPlayer(), PlayerSide.PLAYER_2);
        Assert.assertEquals(Move.getLastGameColor(), GameColor.GREEN);


    }

    @Test
    public void swap(){
        DragonTower dragonTower2 = new DragonTower(GameColor.RED, PlayerSide.PLAYER_1);
        Square square3 = new Square(2, 7, dragonTower2, GameColor.RED);

        Move.setSwapEnabled(true);
        Assert.assertTrue(Move.isSwapPossible(square2, square3));

        Move.swap(square2, square3);
        Assert.assertSame(square2.getPiece(), dragonTower2);
        Assert.assertSame(square3.getPiece(), dragonTower1);
    }

    @Test
    public void push(){
        DragonTower pushed =  new DragonTower(GameColor.GREEN, PlayerSide.PLAYER_2);
        DragonTower tmp = new DragonTower(GameColor.PINK, PlayerSide.PLAYER_1);
        Sumo sumo = new Sumo(tmp);

        Board board = new Board();
        board.generateBoard("Testinput/BoardColors1.txt");
        board.at(3, 0).setPiece(dragonTower1);
        board.at(4, 0).setPiece(sumo);

        Assert.assertTrue(Move.isPushPossible(board, board.at(4, 0)));

        Move.push(board, board.at(4, 0));

        Assert.assertFalse(board.at(4, 0).hasPiece());
        Assert.assertSame(board.at(3, 0).getPiece(), sumo);
        Assert.assertTrue(board.at(2, 0).hasPiece());
    }




}
