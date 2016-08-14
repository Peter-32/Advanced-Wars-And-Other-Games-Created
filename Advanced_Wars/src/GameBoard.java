import Images.BufferedImageLoader;
import Images.SpriteSheet;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Peter on 8/14/2016.
 */
public class GameBoard extends JFrame {

    //// FIELDS

    private final int width = 1200;
    private final int height = 800;
    private final int titleBarHeight = 30;
    private final int leftFrameBorderWidth = 8;
    private TerrainTile[][] tiles = new TerrainTile[16][10];
    SpriteSheet ssBuildings;
    SpriteSheet ssUnits;
    private int menuStartX = 125;
    private int menuStartY = 50;
    private int menuWidth = 250;
    private int menuHeight = 150;
    private int mapStartX = 450;
    private int mapStartY = 225;
    private int tileSideLength = 75;
    private boolean lockInput = false; // not sure if this still be used
    private int yClicked = -1;
    private int xClicked = -1;
    private boolean GameOver = false;
// sound files

    private String backgroundMusic = "file:./resources/Puzzle Theme1.wav";
    private Clip clip = null;

    // use this to lock for write operations like add/remove

    private final Lock readLock;

    // use this to lock for read operations like get/iterator/contains..

    private final Lock writeLock;



    // enum?

    //// CONSTRUCTOR
    GameBoard() {

        this.setSize(width, height);
        this.setTitle("Sliding Puzzle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // locks

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();

        // Start the background music

        //////////////////////////////////////////playMusic(backgroundMusic, true);

        // load sprite sheets

        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage spriteSheetBuildings = null;
        BufferedImage spriteSheetUnits = null;
        try {
            spriteSheetBuildings = loader.loadImage("file:./resources/Game Boy Advance - Advance Wars 2 - Buildings.png");
            spriteSheetUnits = loader.loadImage("file:./resources/Map_units.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ssBuildings = new SpriteSheet(spriteSheetBuildings);
        ssUnits = new SpriteSheet(spriteSheetUnits);

        // Start the mouse listener class
        // his prompts the main game loop each time there is user input

        ListenForMouse lForMouse = new ListenForMouse();
        addMouseListener(lForMouse);

        // start the GameDrawingPanel

        GameDrawingPanel gameDrawingPanel = new GameDrawingPanel(this);
        this.add(gameDrawingPanel, BorderLayout.CENTER);


        // final changes to JFrame

        setResizable(false);
        this.setVisible(true);

        // Add multi-threading for the game loop

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(new MainGameLoop(this), 0L, 20L, TimeUnit.MILLISECONDS);
    } //// END OF GameBoard CONSTRUCTOR

    //// METHODS

    public void playMusic(String soundToPlay, boolean loop) {

        int numLoops = (loop) ? Clip.LOOP_CONTINUOUSLY : 0;
        URL soundLocation;

        try {
            soundLocation = new URL(soundToPlay);

            // end any existing clips

            if (clip != null) {
                if (clip.isActive()) {
                    clip.stop();
                }
            }


            // start a new clip

            clip = AudioSystem.getClip();

            AudioInputStream inputStream;

            inputStream = AudioSystem.getAudioInputStream(soundLocation);

            clip.open(inputStream);
            clip.loop(numLoops);
            clip.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    } // END OF PlayMusic METHOD

    private class ListenForMouse implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!lockInput && e.getButton() == 1) {// lock is off & left click

                // Account for the size of the left border

                xClicked = e.getX() - leftFrameBorderWidth;

                // Account for the size of the top border

                yClicked = e.getY() - titleBarHeight;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

class GameDrawingPanel extends JComponent {

    //// FIELDS

    GameBoard gameBoard;

    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

}

class GameOver {

}

class MainGameLoop implements Runnable {

    //// FIELDS

    GameBoard gameBoard;

    //// CONSTRUCTOR

    MainGameLoop(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void run() {

        // ???

        // movement

        // repaint
        gameBoard.repaint();


    }
}

class TerrainTile {

}
