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

    //// GETTERS AND SETTERS

    public TerrainTile[][] cloneTerrainTilesGrid() {

        TerrainTile[][] clonedTerrainTilesGrid = new TerrainTile[10][16];
        for(int i = 0; i < terrainTilesGrid.length; i++) {
            clonedTerrainTilesGrid[i] = terrainTilesGrid[i].clone();
        }
        return clonedTerrainTilesGrid;
    }
    public ImageIcon getResizedMountainIcon() {
        return resizedMountainIcon;
    }

    public ImageIcon getResizedRoadTurnULIcon() {
        return resizedRoadTurnULIcon;
    }

    public ImageIcon getResizedRoadTurnURIcon() {
        return resizedRoadTurnURIcon;
    }

    public ImageIcon getResizedRoadTurnDLIcon() {
        return resizedRoadTurnDLIcon;
    }

    public ImageIcon getResizedRoadTurnDRIcon() {
        return resizedRoadTurnDRIcon;
    }

    public ImageIcon getResizedRoadUpIcon() {
        return resizedRoadUpIcon;
    }

    public ImageIcon getResizedRoadRightIcon() {
        return resizedRoadRightIcon;
    }

    public ImageIcon getResizedGrassIcon() {
        return resizedGrassIcon;
    }

    public boolean isGameOver() {
        return GameOver;
    }

    public void setGameOver(boolean gameOver) {
        GameOver = gameOver;
    }

    public int getxClicked() {
        return xClicked;
    }

    public void setxClicked(int xClicked) {
        this.xClicked = xClicked;
    }

    public int getyClicked() {
        return yClicked;
    }

    public void setyClicked(int yClicked) {
        this.yClicked = yClicked;
    }

    public int getMenuStartX() {
        return menuStartX;
    }

    public int getMenuStartY() {
        return menuStartY;
    }

    public int getMenuWidth() {
        return menuWidth;
    }

    public int getMenuHeight() {
        return menuHeight;
    }

    public int getMapStartX() {
        return mapStartX;
    }

    public int getMapStartY() {
        return mapStartY;
    }
    public int getTileLength() {
        return tileLength;
    }
    public int getLeftFrameBorderWidth() {
        return leftFrameBorderWidth;
    }
    public int getTitleBarHeight() {
        return titleBarHeight;
    }


    //// FIELDS

    private final int titleBarHeight = 30;
    private final int leftFrameBorderWidth = 8;
    private final int tileLength = 75;
    private final int menuStartX = 125;
    private final int menuStartY = 50;
    private final int menuWidth = 250;
    private final int menuHeight = 150;
    private final int mapStartX = 450;
    private final int mapStartY = 225;
    private final int mapWidth = 1200;
    private final int mapHeight = 750;

    SpriteSheet ssBuildings;
    SpriteSheet ssUnits;


    private boolean lockInput = false; // not sure if this still be used
    private int yClicked = -1;
    private int xClicked = -1;
    private boolean GameOver = false;

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    // Sprites and enums

    public enum TerrainTile {
        MOUNTAIN, GRASS,
        ROAD_VERTICAL, ROAD_HORIZONTAL, ROAD_TURN_UL, ROAD_TURN_UR, ROAD_TURN_DL, ROAD_TURN_DR
    }
    public enum BuildingTile {
        GRAY_HQ, GRAY_BASE, GRAY_CITY,
        RED_HQ, RED_BASE, RED_CITY,
        BLUE_HQ, BLUE_BASE, BLUE_CITY
    }
    private TerrainTile[][] terrainTilesGrid = new TerrainTile[10][16];
    private BuildingTile[][] buildingTilesGrid = new BuildingTile[10][16];

    private ImageIcon mountainIcon = new ImageIcon("resources/mountain.png");
    private ImageIcon roadTurnULIcon = new ImageIcon("resources/road_turn_ul.png");
    private ImageIcon roadTurnURIcon = new ImageIcon("resources/road_turn_ur.png");
    private ImageIcon roadTurnDLIcon = new ImageIcon("resources/road_turn_dl.png");
    private ImageIcon roadTurnDRIcon = new ImageIcon("resources/road_turn_dr.png");
    private ImageIcon roadUpIcon = new ImageIcon("resources/road_up.png");
    private ImageIcon roadRightIcon = new ImageIcon("resources/road_right.png");
    private ImageIcon grassIcon = new ImageIcon("resources/grass.png");

    private Image mountainImage = mountainIcon.getImage();
    private Image roadTurnULImage = roadTurnULIcon.getImage();
    private Image roadTurnURImage = roadTurnURIcon.getImage();
    private Image roadTurnDLImage = roadTurnDLIcon.getImage();
    private Image roadTurnDRImage = roadTurnDRIcon.getImage();
    private Image roadUpImage = roadUpIcon.getImage();
    private Image roadRightImage = roadRightIcon.getImage();
    private Image grassImage = grassIcon.getImage();

    private Image resizedMountainImage = mountainImage.getScaledInstance(tileLength, (int) (tileLength * 1.2), java.awt.Image.SCALE_SMOOTH);
    private Image resizedRoadTurnULImage = roadTurnULImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    private Image resizedRoadTurnURImage = roadTurnURImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    private Image resizedRoadTurnDLImage = roadTurnDLImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    private Image resizedRoadTurnDRImage = roadTurnDRImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    private Image resizedRoadUpImage = roadUpImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    private Image resizedRoadRightImage = roadRightImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    private Image resizedGrassImage = grassImage.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);

    private ImageIcon resizedMountainIcon = new ImageIcon(resizedMountainImage);
    private ImageIcon resizedRoadTurnULIcon = new ImageIcon(resizedRoadTurnULImage);
    private ImageIcon resizedRoadTurnURIcon = new ImageIcon(resizedRoadTurnURImage);
    private ImageIcon resizedRoadTurnDLIcon = new ImageIcon(resizedRoadTurnDLImage);
    private ImageIcon resizedRoadTurnDRIcon = new ImageIcon(resizedRoadTurnDRImage);
    private ImageIcon resizedRoadUpIcon = new ImageIcon(resizedRoadUpImage);
    private ImageIcon resizedRoadRightIcon = new ImageIcon(resizedRoadRightImage);
    private ImageIcon resizedGrassIcon = new ImageIcon(resizedGrassImage);

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

        this.setSize(mapWidth, mapHeight);
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

        // load map1

        loadMap1();

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
        executor.scheduleAtFixedRate(new MainGameLoop(this), 0L, 100000L, TimeUnit.MILLISECONDS);
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
    } // END OF MouseListener INNER CLASS

    void loadMap1() {

        /*private enum TerrainTile {
            MOUNTAIN, GRASS,
            ROAD_VERTICAL, ROAD_HORIZONTAL, ROAD_TURN_UL, ROAD_TURN_UR, ROAD_TURN_DL, ROAD_TURN_DR
        }
        */

        // default to all grass to start

        for (TerrainTile[] row : terrainTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                row[j] = TerrainTile.GRASS;
            }
        }

    }



} // END OF GameBoard CLASS

class GameDrawingPanel extends JComponent {

    //// FIELDS

    GameBoard gameBoard;
    int terrainDrawingTopLeftXPos = 0;
    int terrainDrawingTopLeftYPos = 0;

    /*
        private final int menuStartX = 125;
    private final int menuStartY = 50;
    private final int menuWidth = 250;
    private final int menuHeight = 150;
    private final int mapStartX = 450;
    private final int mapStartY = 225;
    private final int mapWidth = 1200;
    private final int mapHeight = 750;




    ACCOUNT FOR MAP START LOCATION!!!!!!!!!!!!!!
    ACCOUNT FOR BORDERS OF JFRAME!!!!!!!!!
     */
    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

    }

    // METHODS

    public void paint(Graphics g) {

        Graphics2D graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.WHITE);
        graphicSettings.fillRect(0,0, gameBoard.getMapWidth(), gameBoard.getMapHeight());

        graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /// draw terrain, then buildings, then units.  See if they can stack!

        // draw terrain

        GameBoard.TerrainTile[][] tempTerrainTilesGrid = gameBoard.cloneTerrainTilesGrid();


        for (GameBoard.TerrainTile[] row : tempTerrainTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                /* switch(row[j]) {
                    case GRASS:
                        gameBoard.getResizedGrassIcon().paintIcon(this, g, topLeftXPos, topLeftYPos);
                        break;
                } */
                gameBoard.getResizedGrassIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                updateXYPositionForMapDrawing();
            }
        }
        //cloneTerrainTilesGrid

    } // END OF paint METHOD

    public void updateXYPositionForMapDrawing() {
        System.out.println("xPos = " + terrainDrawingTopLeftXPos);
        System.out.println("yPos = " + terrainDrawingTopLeftYPos);
        if (terrainDrawingTopLeftXPos < gameBoard.getMapWidth() - gameBoard.getTileLength()) {
            terrainDrawingTopLeftXPos += gameBoard.getTileLength();
        } else if (terrainDrawingTopLeftYPos < gameBoard.getMapHeight() - gameBoard.getTileLength()) {
            terrainDrawingTopLeftXPos = 0;
            terrainDrawingTopLeftYPos += gameBoard.getTileLength();
        } else {
            terrainDrawingTopLeftXPos = 0;
            terrainDrawingTopLeftYPos = 0;
        }
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