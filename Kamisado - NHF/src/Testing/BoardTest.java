package Testing;

import GameLogic.Board;
import GameLogic.GameColor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class BoardTest {

    Board board;

    @Before
    public void setUpBoard(){
        board = new Board();
        board.generateBoard("Testinput/BoardColors1.txt");
    }

    @Test
    public void at(){

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Assert.assertNotEquals(board.at(i,j), null );
                Assert.assertEquals(board.at(i, j).getX(), j);
                Assert.assertEquals(board.at(i, j).getY(), i);
            }
        }
    }

    @Test
    public void boardGenerate(){

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Assert.assertEquals(board.at(i, j).getColor(), GameColor.ORANGE);
            }
        }
    }

    @Test
    public void save() throws IOException, ClassNotFoundException {
        board.save();
        board.load();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Assert.assertEquals(board.at(i, j).getColor(), GameColor.ORANGE);
            }
        }
    }

    @Test
    public void generatePieces(){
        board.generatePieces();

        for(int i=0;i<8;i++){
            Assert.assertTrue(board.at(0, i).hasPiece());
            Assert.assertEquals(board.at(0, i).getPiece().getColor(), GameColor.ORANGE);

            Assert.assertTrue(board.at(7, i).hasPiece());
            Assert.assertEquals(board.at(7, i).getPiece().getColor(), GameColor.ORANGE);
        }

    }

}
