package Graphics;

import GameLogic.Game;
import GameLogic.GameColor;
import GameLogic.PlayerSide;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;

public class PieceAssets {
    static private HashMap<GameColor, BufferedImage> bassets = new HashMap<GameColor, BufferedImage>();
    static private HashMap<GameColor, BufferedImage> wassets = new HashMap<GameColor, BufferedImage>();
    static private HashMap<Integer, BufferedImage> sumos = new HashMap<Integer, BufferedImage>();

    static private ImageIcon goku;

    static private boolean generated = false;

    static public ImageIcon get(GameColor gc, PlayerSide ps){
        if(ps == PlayerSide.PLAYER_1){
            return new ImageIcon( wassets.get(gc));
        }
        return new ImageIcon(bassets.get(gc));
    }

    static public ImageIcon getSumo(GameColor gc, PlayerSide ps, int sumoLevel){
        BufferedImage image;
        if(ps == PlayerSide.PLAYER_1){
            image = wassets.get(gc);
        }
        else{
            image = bassets.get(gc);
        }
        BufferedImage overlay = sumos.get(sumoLevel);
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

// paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(overlay, 0, 0, null);

        g.dispose();

        return new ImageIcon(combined);
    }

    static public ImageIcon getGoku(){
        return goku;
    }

    static public boolean isGenerated(){
        return generated;
    }

    static public void generate(){
        try {
            bassets.put(GameColor.BLUE, (ImageIO.read(new File("Assets/BBlue.png"))));
            bassets.put(GameColor.BROWN, ImageIO.read(new File("Assets/BBrown.png")));
            bassets.put(GameColor.GREEN, ImageIO.read(new File("Assets/BGreen.png")));
            bassets.put(GameColor.ORANGE, ImageIO.read(new File("Assets/BOrange.png")));
            bassets.put(GameColor.PINK, ImageIO.read(new File("Assets/BPink.png")));
            bassets.put(GameColor.PURPLE, ImageIO.read(new File("Assets/BPurple.png")));
            bassets.put(GameColor.RED, ImageIO.read(new File("Assets/BRed.png")));
            bassets.put(GameColor.YELLOW, ImageIO.read(new File("Assets/BYellow.png")));

            wassets.put(GameColor.BLUE, ImageIO.read(new File("Assets/WBlue.png")));
            wassets.put(GameColor.BROWN, ImageIO.read(new File("Assets/WBrown.png")));
            wassets.put(GameColor.GREEN, ImageIO.read(new File("Assets/WGreen.png")));
            wassets.put(GameColor.ORANGE, ImageIO.read(new File("Assets/WOrange.png")));
            wassets.put(GameColor.PINK, ImageIO.read(new File("Assets/WPink.png")));
            wassets.put(GameColor.PURPLE, ImageIO.read(new File("Assets/WPurple.png")));
            wassets.put(GameColor.RED, ImageIO.read(new File("Assets/WRed.png")));
            wassets.put(GameColor.YELLOW, ImageIO.read(new File("Assets/wYellow.png")));

            sumos.put(1, ImageIO.read(new File("Assets/SumoLevel1.png")));
            sumos.put(2, ImageIO.read(new File("Assets/SumoLevel2.png")));
            sumos.put(3, ImageIO.read(new File("Assets/SumoLevel3.png")));

            BufferedImage bi = ImageIO.read(new File("Assets/Goku.png"));
            goku = new ImageIcon(bi);

            generated = true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}