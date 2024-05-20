package GameLogic;

import java.io.Serializable;

public class Square implements Serializable {
    private int x;
    private int y;
    private Piece piece;
    private boolean hasPiece;
    private GameColor gameColor;

    public Square(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        if(piece == null) hasPiece = false;
        else hasPiece = true;
        this.piece = piece;
    }
    public Square(int x, int y, Piece piece, GameColor gameColor){
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.gameColor = gameColor;
        if(piece == null) hasPiece = false;
        else hasPiece = true;
    }

    public Square(GameColor gameColor){
        this.gameColor = gameColor;
    }

    public int getX(){return x;}
    public int getY(){return y;}
    public boolean hasPiece(){return hasPiece;}
    public Piece getPiece(){return piece;}
    public void setPiece(Piece piece){this.piece = piece;this.hasPiece = true;}
    public void setColor(GameColor gameColor){this.gameColor = gameColor;}
    public GameColor getColor(){return gameColor;}
    public void addPiece(Piece piece){
        this.piece = piece;
        this.hasPiece = true;
    }
    public Piece popPiece(){
        Piece tmp = new Piece();
        tmp = this.piece;
        this.piece = null;
        this.hasPiece = false;
        return tmp;
    }


}
