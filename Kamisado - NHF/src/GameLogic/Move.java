package GameLogic;

import Graphics.PieceAssets;

import java.io.*;

public class Move {

    static private boolean swapEnabled;

    static private GameColor lastGameColor = GameColor.DEFAULT;
    static private PlayerSide lastPlayer = PlayerSide.PLAYER_1;
    static private PlayerSide startingPlayer = PlayerSide.PLAYER_1;
    static private boolean roundStarted = false;

    public static GameColor getLastGameColor(){
        return lastGameColor;
    }
    public static PlayerSide getLastPlayer(){
        return lastPlayer;
    }
    public static void setLastPlayer(PlayerSide ps){lastPlayer = ps;}
    public static boolean isMovePossible(Square startingPoint, Square targetPoint){
        //The legendary code
        /*if(1==1){
            return true;
        }*/

        if(targetPoint.hasPiece()){
            return false;
        }
        if(!startingPoint.hasPiece()){
            return false;
        }

        if(startingPoint.getPiece().getColor() != lastGameColor){
            if(lastGameColor != GameColor.DEFAULT){
                return false;
            }
        }

        if(!roundStarted && startingPoint.getPiece().getSide() != startingPlayer){
            return false;
        }
        if(startingPoint.getPiece().getSide() != lastPlayer && roundStarted){
            return false;
        }

        // ha egy vonalban vannak
        if(Math.abs(startingPoint.getY() - targetPoint.getY()) <= startingPoint.getPiece().getMovingDistance()
                && startingPoint.getX() == targetPoint.getX()){
            return true;
        }

        //ha átlóban vannak
        return Math.abs(startingPoint.getY() - targetPoint.getY()) == Math.abs(startingPoint.getX() - targetPoint.getX());
        //szintén fancy exception, hogyha nem lehet oda tenni a bábut
    }
    public static boolean move(Square startingPoint, Square targetPoint){
        if(isSwapPossible(startingPoint, targetPoint)){
            swap(startingPoint, targetPoint);
            return true;
        }

        if(!isMovePossible(startingPoint, targetPoint)){
            return false;
        }
        if(startingPoint.getPiece().getSide() == PlayerSide.PLAYER_1){lastPlayer = PlayerSide.PLAYER_2;}
        else{lastPlayer = PlayerSide.PLAYER_1;}

        targetPoint.addPiece(startingPoint.popPiece());
        lastGameColor = targetPoint.getColor();
        swapEnabled = false;
        roundStarted = true;
        return true;
    }

    public static boolean isSwapPossible(Square startingPoint, Square targetPoint){
        return swapEnabled && startingPoint.getY() == targetPoint.getY() && (startingPoint.getY() == 0 || startingPoint.getY() == 7)
                && startingPoint.hasPiece() && targetPoint.hasPiece() &&
                startingPoint.getPiece().getSide() == targetPoint.getPiece().getSide();
    }

    public static boolean swap(Square startingPoint, Square targetPoint){
        if(!isSwapPossible(startingPoint, targetPoint)){
            return false;
        }
        Piece tmp = startingPoint.getPiece();
        startingPoint.setPiece(targetPoint.getPiece());
        targetPoint.setPiece(tmp);
        return true;
    }

    public static boolean isPushPossible(Board board, Square sumoSquare){
        if(!sumoSquare.hasPiece() || sumoSquare.getPiece().getClass() != Sumo.class){
            return false;
        }
        int frontOf=-1, sX=sumoSquare.getX(), sY = sumoSquare.getY();

        if(sumoSquare.getPiece().getSide() == PlayerSide.PLAYER_2){
            frontOf = 1;
        }


        for(int i=0;i<((Sumo)(sumoSquare.getPiece())).getSumoLevel()+1;i++){
            if(i==0 && !board.at(sY+frontOf, sX).hasPiece()){
                return false;
            }
            if(i==0 && board.at(sY+frontOf, sX).getPiece().getClass() == Sumo.class){
                if( ((Sumo)sumoSquare.getPiece()).getSumoLevel() <= ((Sumo)board.at(sY+frontOf, sX).getPiece()).getSumoLevel() ){
                    return false;
                }
            }
            if(sY+frontOf*(i+1) > 7 || sY+frontOf*(i+1) < 0){
                return false;
            }
            if(i > 1 && board.at(sY+frontOf*i, sX).hasPiece()){
                return false;
            }
        }
        return true;
    }

    public static boolean push(Board board, Square sumoSquare){
        if(!isPushPossible(board, sumoSquare)){
            return false;
        }
        int pushingDir;
        if(sumoSquare.getPiece().getSide() == PlayerSide.PLAYER_1){
            pushingDir = -1;
        }
        else{
            pushingDir = 1;
        }
        pushingDir *= ((Sumo)sumoSquare.getPiece()).getSumoLevel();

        int sX = sumoSquare.getX(), sY= sumoSquare.getY();
        int pX = sumoSquare.getX(), pY = sumoSquare.getY()+1;
        if(pushingDir < 0){
            pY -= 2;
        }

        board.at(pY+pushingDir, pX).setPiece(board.at(pY, pX).popPiece());
        board.at(sY+pushingDir, sX).setPiece(sumoSquare.popPiece());

        return true;
    }

    public static void setSwapEnabled(boolean bool){
        swapEnabled = bool;
    }

    public static boolean isRoundStarted(Board board){
        for(int i=0;i<8;i++){
            if(!board.at(0, i).hasPiece() || !board.at(7, i).hasPiece()){
                return false;
            }
            if(board.at(0, i).getPiece().getSide() != PlayerSide.PLAYER_2 ||
                    board.at(7, i).getPiece().getSide() != PlayerSide.PLAYER_1){
                return  false;
            }
        }

        return true;
    }
    public static void setUpNewRound(){
        if(startingPlayer == PlayerSide.PLAYER_1) {
            startingPlayer = PlayerSide.PLAYER_2;
        }
        else{
            startingPlayer = PlayerSide.PLAYER_1;
        }
        lastPlayer = startingPlayer;
        lastGameColor = GameColor.DEFAULT;
        roundStarted = false;

    }
    public static void setUpNewGame(){
        lastPlayer = PlayerSide.PLAYER_1;
        lastGameColor = GameColor.DEFAULT;
        startingPlayer = PlayerSide.PLAYER_1;
    }

    public static void save() throws IOException {
        File f = new File("Assets/TextFiles/MoveSave1.txt");
        if(!f.exists()){
            f.createNewFile();
        }
        if(f.exists()){
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(swapEnabled);
            out.close();
        }
        File f2 = new File("Assets/TextFiles/MoveSave2.txt");
        if(!f2.exists()){
            f2.createNewFile();
        }
        if(f2.exists()){
            FileOutputStream fs = new FileOutputStream(f2);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(lastGameColor);
            out.close();
        }
        File f3 = new File("Assets/TextFiles/MoveSave3.txt");
        if(!f3.exists()){
            f3.createNewFile();
        }
        if(f3.exists()){
            FileOutputStream fs = new FileOutputStream(f3);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(lastPlayer);
            out.close();
        }
        File f4 = new File("Assets/TextFiles/MoveSave4.txt");
        if(!f4.exists()){
            f4.createNewFile();
        }
        if(f4.exists()){
            FileOutputStream fs = new FileOutputStream(f4);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(startingPlayer);
            out.close();
        }
        File f5 = new File("Assets/TextFiles/MoveSave5.txt");
        if(!f5.exists()){
            f5.createNewFile();
        }
        if(f5.exists()){
            FileOutputStream fs = new FileOutputStream(f5);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(roundStarted);
            out.close();
        }
    }

    public static void load() throws IOException, ClassNotFoundException {

        File f = new File("Assets/TextFiles/MoveSave1.txt");
        if(f.exists()){
            FileInputStream fs = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fs);
            swapEnabled = (Boolean) in.readObject();
            in.close();
        }
        File f2 = new File("Assets/TextFiles/MoveSave2.txt");
        if(f2.exists()){
            FileInputStream fs = new FileInputStream(f2);
            ObjectInputStream in = new ObjectInputStream(fs);
            lastGameColor = (GameColor) in.readObject();
            in.close();
        }
        File f3 = new File("Assets/TextFiles/MoveSave3.txt");
        if(f3.exists()){
            FileInputStream fs = new FileInputStream(f3);
            ObjectInputStream in = new ObjectInputStream(fs);
            lastPlayer = (PlayerSide) in.readObject();
            in.close();
        }
        File f4 = new File("Assets/TextFiles/MoveSave4.txt");
        if(f4.exists()){
            FileInputStream fs = new FileInputStream(f4);
            ObjectInputStream in = new ObjectInputStream(fs);
            startingPlayer = (PlayerSide) in.readObject();
            in.close();
        }
        File f5 = new File("Assets/TextFiles/MoveSave5.txt");
        if(f5.exists()){
            FileInputStream fs = new FileInputStream(f5);
            ObjectInputStream in = new ObjectInputStream(fs);
            roundStarted = (Boolean) in.readObject();
            in.close();
        }
    }
}