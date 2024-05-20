package GameLogic;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Board implements Serializable {
    private Square[][] board;

    public Board(){
        board = new Square[8][8];
    }

    //generating board, from my File
    public void generateBoard(String filename){
        if(board == null){
            board = new Square[8][8];
        }
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);
            for(int i=0;i<8;i++){
                String line = scanner.nextLine();
                for(int j=0;j<8;j++){
                    board[i][j] = new Square(j, i, null, GameColor.DEFAULT);
                    int n = Integer.parseInt(String.valueOf(line.charAt(j)));
                    switch (n) {
                        case 1 -> board[i][j].setColor(GameColor.ORANGE);
                        case 2 -> board[i][j].setColor(GameColor.BLUE);
                        case 3 -> board[i][j].setColor(GameColor.PURPLE);
                        case 4 -> board[i][j].setColor(GameColor.PINK);
                        case 5 -> board[i][j].setColor(GameColor.YELLOW);
                        case 6 -> board[i][j].setColor(GameColor.RED);
                        case 7 -> board[i][j].setColor(GameColor.GREEN);
                        case 8 -> board[i][j].setColor(GameColor.BROWN);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            generateBasicBoard();
        }
    }

    //hard-coded board
    public void generateBasicBoard(){
        if(board == null){
            board = new Square[8][8];
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                board[i][j] = new Square(j, i, null, GameColor.DEFAULT);

                if(i==j){
                    board[i][j].setColor(GameColor.ORANGE);
                } else if (7-i == j) {
                    board[i][j].setColor(GameColor.BROWN);
                } else if (i == j-4 || i-4 == j) {
                    board[i][j].setColor(GameColor.YELLOW);
                } else if (7-i == j+4 || 7-i == j-4) {
                    board[i][j].setColor(GameColor.PINK);
                } else if ((i*3+1)%8 == j) {
                    board[i][j].setColor(GameColor.BLUE);
                } else if ((i*5 + 2) % 8 == j) {
                    board[i][j].setColor(GameColor.PURPLE);
                } else if ((i*3 + 5) % 8 ==j){
                    board[i][j].setColor(GameColor.RED);
                } else if ((5*i + 6) % 8 == j) {
                    board[i][j].setColor(GameColor.GREEN);
                }


                if(board[i][j] == null){
                    board[i][j] = new Square(GameColor.DEFAULT);
                }
            }
        }
    }

    public void generatePieces(){
        for(int i=0;i<8;i++){
            board[0][i].addPiece(new DragonTower(board[0][i].getColor(), PlayerSide.PLAYER_2) );
            board[7][i].addPiece(new DragonTower(board[7][i].getColor(), PlayerSide.PLAYER_1));
        }
    }

    public void regenerateBoard(){
        Board b2 = new Board();
        b2.generateBoard("BoardColors.txt");

        for(int k=0;k<8;k++){
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(this.at(i, j).hasPiece() && this.at(i, j).getPiece().getSide() == PlayerSide.PLAYER_1 &&
                    this.at(i, j).getPiece().getColor() == this.at(7, k).getColor()){
                        b2.at(7, k).addPiece(this.at(i, j).getPiece());
                    }
                    if(this.at(i, j).hasPiece() && this.at(i, j).getPiece().getSide() == PlayerSide.PLAYER_2 &&
                            this.at(i, j).getPiece().getColor() == this.at(0, k).getColor()){
                        b2.at(0, k).addPiece(this.at(i, j).getPiece());
                    }
                }
            }
        }
        this.board = b2.board;
        Move.setSwapEnabled(true);
    }

    public Square at(int a, int b){
        return board[a][b];
    }

    public void save() throws IOException {

        if(board != null){
            File f = new File("Assets/TextFiles/BoardSave.txt");
            if(!f.exists()){
                f.createNewFile();
            }
            if(f.exists()){
                FileOutputStream fs = new FileOutputStream(f);
                ObjectOutputStream out = new ObjectOutputStream(fs);
                out.writeObject(board);
                out.close();
            }
        }
    }

    public void load() throws IOException, ClassNotFoundException {

        File f = new File("Assets/TextFiles/BoardSave.txt");
        if(f.exists()){
            FileInputStream fs = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fs);
            Square[][] b2 = (Square[][]) in.readObject();
            in.close();
            board = b2;
        }
    }
}
