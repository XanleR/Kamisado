package GameLogic;

import java.io.*;

public class Game implements Serializable {

    private static int player_1Points=0;
    private static int player_2Points=0;
    private static int pointsToWin = 3;
    private static boolean ended = false;

    private static int maxSumoLevel = 3;

    public static void setPointsToWin(int pointsToWin){
        Game.pointsToWin = pointsToWin;
    }

    public static int getPointsToWin(){return pointsToWin;}

    //returns the Piece that got into the enemy's lines,
    //so it returns much more information
    public static Piece winningPiece(Board board){
        return winningSquare(board).getPiece();
    }

    public static Square winningSquare(Board board){
        for(int i=0;i<8;i++){
            if(board.at(0, i).hasPiece() && board.at(0, i).getPiece().getSide() == PlayerSide.PLAYER_1){

                return board.at(0, i);
            }
            if(board.at(7, i).hasPiece() && board.at(7, i).getPiece().getSide() == PlayerSide.PLAYER_2){
                return board.at(7, i);
            }
        }
        return null;
    }
    public static boolean checkRoundEnd(Board board){
        for(int i=0;i<8;i++){
            if(board.at(0, i).hasPiece() && board.at(0, i).getPiece().getSide() == PlayerSide.PLAYER_1){
                return true;
            }
            if(board.at(7, i).hasPiece() && board.at(7, i).getPiece().getSide() == PlayerSide.PLAYER_2){
                return true;
            }
        }
        return false;
    }
    public static void incrementPoints(Board board){
        Piece wp = winningPiece(board);
        if(wp.getSide() == PlayerSide.PLAYER_1){player_1Points += wp.getPointWorth();}
        if(wp.getSide() == PlayerSide.PLAYER_2){player_2Points += wp.getPointWorth();}
    }

    static public int getPoints(PlayerSide ps){
        if(ps == PlayerSide.PLAYER_1){
            return player_1Points;
        }
        return player_2Points;
    }

    static public int getMaxSumoLevel(){
        return maxSumoLevel;
    }

    public static void setMaxSumoLevel(int n){
        maxSumoLevel = n;
    }

    public static boolean checkGameEnd(){
        return player_1Points >= pointsToWin || player_2Points >= pointsToWin;
    }

    public static void endGame(){
        ended = true;
    }

    public static boolean getEnded(){
        return ended;
    }

    public static PlayerSide winner(){
        if(player_1Points >= pointsToWin){
            return PlayerSide.PLAYER_1;
        }
        if(player_2Points >= pointsToWin){
            return PlayerSide.PLAYER_2;
        }
        return null;
    }

    public static void setUpNewGame(){
        ended = false;
        player_1Points = 0;
        player_2Points = 0;
    }

    public static void save() throws IOException, FileNotFoundException {
        File f = new File("Assets/TextFiles/GameSave1.txt");
        if(!f.exists()){
            f.createNewFile();
        }
        if(f.exists()){
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(player_1Points);
            out.close();
        }
        File f2 = new File("Assets/TextFiles/GameSave2.txt");
        if(!f2.exists()){
            f2.createNewFile();
        }
        if(f2.exists()){
            FileOutputStream fs = new FileOutputStream(f2);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(player_2Points);
            out.close();
        }
        File f3 = new File("Assets/TextFiles/GameSave3.txt");
        if(!f3.exists()){
            f3.createNewFile();
        }
        if(f3.exists()){
            FileOutputStream fs = new FileOutputStream(f3);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(pointsToWin);
            out.close();
        }
        File f4 = new File("Assets/TextFiles/GameSave4.txt");
        if(!f4.exists()){
            f4.createNewFile();
        }
        if(f4.exists()){
            FileOutputStream fs = new FileOutputStream(f4);
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(maxSumoLevel);
            out.close();
        }
    }

    public static void load() throws IOException, ClassNotFoundException {

        File f = new File("Assets/TextFiles/GameSave1.txt");
        if(f.exists()){
            FileInputStream fs = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fs);
            player_1Points = (Integer) in.readObject();
            in.close();
        }
        File f2 = new File("Assets/TextFiles/GameSave2.txt");
        if(f2.exists()){
            FileInputStream fs = new FileInputStream(f2);
            ObjectInputStream in = new ObjectInputStream(fs);
            player_2Points = (Integer) in.readObject();
            in.close();
        }
        File f3 = new File("Assets/TextFiles/GameSave3.txt");
        if(f3.exists()){
            FileInputStream fs = new FileInputStream(f3);
            ObjectInputStream in = new ObjectInputStream(fs);
            pointsToWin = (Integer) in.readObject();
            in.close();
        }
        File f4 = new File("Assets/TextFiles/GameSave4.txt");
        if(f4.exists()){
            FileInputStream fs = new FileInputStream(f4);
            ObjectInputStream in = new ObjectInputStream(fs);
            maxSumoLevel = (Integer) in.readObject();
            in.close();
        }


    }
}
