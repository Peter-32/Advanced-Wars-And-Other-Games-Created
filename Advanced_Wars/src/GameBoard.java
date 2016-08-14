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
import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Peter on 8/14/2016.
 */
public class GameBoard extends JFrame {

    //// FIELDS

    private enum TerrainTiles {

    }

    private final int width = 1200;
    private final int height = 800;
    private final int titleBarHeight = 30;
    private final int leftFrameBorderWidth = 8;

    SpriteSheet ssBuildings;
    SpriteSheet ssUnits;
    private int menuStartX = 125;
    private int menuStartY = 50;
    private int menuWidth = 250;
    private int menuHeight = 150;
    private int mapStartX = 450;
    private int mapStartY = 225;
    private int tileLength = 75;
    private boolean lockInput = false; // not sure if this still be used
    private int yClicked = -1;
    private int xClicked = -1;
    private boolean GameOver = false;

    // Sprites and enums

    private enum terrainTile {
        MOUNTAIN, GRASS,
        ROAD_VERTICAL, ROAD_HORIZONTAL, ROAD_TURN_UL, ROAD_TURN_UR, ROAD_TURN_DL, ROAD_TURN_DR
    }
    private enum buildingTile {
        GRAY_HQ, GRAY_BASE, GRAY_CITY,
        RED_HQ, RED_BASE, RED_CITY,
        BLUE_HQ, BLUE_BASE, BLUE_CITY
    }
    private terrainTile[][] terrainTiles = new terrainTile[16][10];
    private buildingTile[][] buildingTiles = new buildingTile[16][10];

    ImageIcon mountainIcon = new ImageIcon("resources/mountain.png");
    ImageIcon roadTurnULIcon = new ImageIcon("resources/road_turn_ul.png");
    ImageIcon roadTurnURIcon = new ImageIcon("resources/road_turn_ur.png");
    ImageIcon roadTurnDLIcon = new ImageIcon("resources/road_turn_dl.png");
    ImageIcon roadTurnDRIcon = new ImageIcon("resources/road_turn_dr.png");
    ImageIcon roadUpIcon = new ImageIcon("resources/road_up.png");
    ImageIcon roadRightIcon = new ImageIcon("resources/road_right.png");
    ImageIcon grassIcon = new ImageIcon("resources/grass.png");

    Image mountainImage = mountainIcon.getImage();
    Image roadTurnULImage = roadTurnULIcon.getImage();
    Image roadTurnURImage = roadTurnURIcon.getImage();
    Image roadTurnDLImage = roadTurnDLIcon.getImage();
    Image roadTurnDRImage = roadTurnDRIcon.getImage();
    Image roadUpImage = roadUpIcon.getImage();
    Image roadRightImage = roadRightIcon.getImage();
    Image grassImage = grassIcon.getImage();

    Image resizedMountainImage = mountainImage.getScaledInstance(tileLength, (int) (tileLength * 1.2), java.awt.Image.SCALE_SMOOTH);
    Image resizedRoadTurnULImage = roadTurnULImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    Image resizedRoadTurnURImage = roadTurnURImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    Image resizedRoadTurnDLImage = roadTurnDLImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    Image resizedRoadTurnDRImage = roadTurnDRImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    Image resizedRoadUpImage = roadUpImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    Image resizedRoadRightImage = roadRightImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    Image resizedGrassImage = grassImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);




    // sound files

    private String backgroundMusic = "file:./resources/David_Szesztay_-_Morning_Four.wav";
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

        playMusic(backgroundMusic, true);

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

    // METHODS

    public void paint(Graphics g) {

        Graphics2D graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.WHITE);
        graphicSettings.fillRect(0,0, gameBoard.getWidth(), gameBoard.getHeight());

        graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw terrain, then buildings, then units.  See if they can stack!



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

class BuildingTile {

}