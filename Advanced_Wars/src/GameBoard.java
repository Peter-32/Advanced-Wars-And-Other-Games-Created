import Images.BufferedImageLoader;
import Images.SpriteSheet;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
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
    public BuildingTile[][] cloneBuildingTilesGrid() {

        BuildingTile[][] clonedBuildingTilesGrid = new BuildingTile[10][16];
        for(int i = 0; i < terrainTilesGrid.length; i++) {
            clonedBuildingTilesGrid[i] = buildingTilesGrid[i].clone();
        }
        return clonedBuildingTilesGrid;
    }
    public UnitTile[][] cloneUnitTilesGrid() {

        UnitTile[][] clonedUnitTilesGrid = new UnitTile[10][16];
        for(int i = 0; i < terrainTilesGrid.length; i++) {
            clonedUnitTilesGrid[i] = unitTilesGrid[i].clone();
        }
        return clonedUnitTilesGrid;
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

    public ImageIcon getResizedRoadVerticalIcon() {
        return resizedRoadVerticalIcon;
    }

    public ImageIcon getResizedRoadHorizontalIcon() {
        return resizedRoadHorizontalIcon;
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
    public int getTopJFrameBorderLength() {
        return topJFrameBorderLength;
    }
    public int getLeftJFrameBorderLength() {
        return leftJFrameBorderLength;
    }
    public int getMapWidth() {
        return mapWidth;
    }
    public int getMapHeight() {
        return mapHeight;
    }

    //// FIELDS

    private final int leftJFrameBorderLength = 8;
    private final int topJFrameBorderLength = 30;
    private final int tileLength = 75;
    private final int menuStartX = 125;// not too important, just where it shows up on screen you can move it
    private final int menuStartY = 50;// not too important, just where it shows up on screen you can move it
    private final int menuWidth = 250;
    private final int menuHeight = 150;
    private final int mapStartX = 450; // not too important, just where it shows up on screen you can move it
    private final int mapStartY = 225;// not too important, just where it shows up on screen you can move it
    //private final int mapWidth = 1200 + (2 * leftJFrameBorderLength);
    //private final int mapHeight = 750 + topJFrameBorderLength + leftJFrameBorderLength;
    private final int mapWidth = 1200;
    private final int mapHeight = 750;

    SpriteSheet ssBuildings;
    SpriteSheet ssUnits;


    private boolean lockInput = false; // not sure if this still be used
    private int yClicked = -1;
    private int xClicked = -1;
    private boolean GameOver = false;



    // Sprites and enums

    public enum TerrainTile {
        MOUNTAIN, GRASS,
        ROAD_VERTICAL, ROAD_HORIZONTAL, ROAD_TURN_UL, ROAD_TURN_UR, ROAD_TURN_DL, ROAD_TURN_DR
    }
    public enum BuildingTile {
        NONE,
        GRAY_BASE, GRAY_CITY,
        RED_HQ, RED_BASE, RED_CITY,
        BLUE_HQ, BLUE_BASE, BLUE_CITY
    }
    public enum UnitTile {
        INFANTRY, MECH, TANK, ARTILLERY
    }
    private TerrainTile[][] terrainTilesGrid = new TerrainTile[10][16];
    private BuildingTile[][] buildingTilesGrid = new BuildingTile[10][16];
    private UnitTile[][] unitTilesGrid = new UnitTile[10][16];

    // Terrain

    private ImageIcon resizedMountainIcon = getResizedImageFromFile("resources/mountain.png");
    private ImageIcon resizedRoadTurnULIcon = getResizedImageFromFile("resources/road_turn_ul.png");
    private ImageIcon resizedRoadTurnURIcon = getResizedImageFromFile("resources/road_turn_ur.png");
    private ImageIcon resizedRoadTurnDLIcon = getResizedImageFromFile("resources/road_turn_dl.png");
    private ImageIcon resizedRoadTurnDRIcon = getResizedImageFromFile("resources/road_turn_dr.png");
    private ImageIcon resizedRoadVerticalIcon = getResizedImageFromFile("resources/road_vertical.png");
    private ImageIcon resizedRoadHorizontalIcon = getResizedImageFromFile("resources/road_horizontal.png");
    private ImageIcon resizedGrassIcon = getResizedImageFromFile("resources/grass.png");

    // Buildings
////////////////////////////////// FIX THIS IT ISN"T RIGHT.  NEED TO LOAD IN THE SPRITE SHEET and the right information
    /* private ImageIcon resizedMountainIcon = getResizedImageFromSpriteSheet("resources/mountain.png",,);
    private ImageIcon resizedRoadTurnULIcon = getResizedImageFromSpriteSheet("resources/road_turn_ul.png",,);
    private ImageIcon resizedRoadTurnURIcon = getResizedImageFromSpriteSheet("resources/road_turn_ur.png",,);
    private ImageIcon resizedRoadTurnDLIcon = getResizedImageFromSpriteSheet("resources/road_turn_dl.png",,);
    private ImageIcon resizedRoadTurnDRIcon = getResizedImageFromSpriteSheet("resources/road_turn_dr.png",,);
    private ImageIcon resizedRoadVerticalIcon = getResizedImageFromSpriteSheet("resources/road_vertical.png",,);
    private ImageIcon resizedRoadHorizontalIcon = getResizedImageFromSpriteSheet("resources/road_horizontal.png",,);
    private ImageIcon resizedGrassIcon = getResizedImageFromSpriteSheet("resources/grass.png",,); */











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

        // load map #1

        loadMap(1);

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

                xClicked = e.getX() - leftJFrameBorderLength;

                // Account for the size of the top border

                yClicked = e.getY() - topJFrameBorderLength;
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

    void loadMap(int mapNumber) {

        // load map terrain

        loadMapTerrain(mapNumber);

        // load map buildings

        loadMapBuildings(mapNumber);


    } // END OF loadMap METHOD


    void loadMapTerrain(int mapNumber) {

        // Default to grass in case the input file doesn't have enough rows of data

        for (TerrainTile[] row : terrainTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                row[j] = TerrainTile.GRASS;
            }
        }

        //File mapTerrainFile = new File("file:./resources/Map" + 1 + "_Terrain.txt");        Why doesn't this work???
        File mapTerrainFile = new File("C:/Users/Peter/Java Projects/Game_Clones/Advanced_Wars/resources/Map" + mapNumber + "_Terrain.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new FileReader(mapTerrainFile));

            // read one line at a time

            int lineNumber = 0;
            String line = br.readLine();

            while(line != null) {

                System.out.println(line);
                // This loops through each column.  If the text input is too short it skips that iteration.

                for (int j = 0; j < terrainTilesGrid[0].length && j < line.length(); j++) {
                    System.out.println(line.substring(j, j + 1));
                    switch(line.substring(j,j + 1)) {
                        case "g":
                            terrainTilesGrid[lineNumber][j] = TerrainTile.GRASS;
                            break;
                        case "m":
                            terrainTilesGrid[lineNumber][j] = TerrainTile.MOUNTAIN;
                            break;
                        case "-":
                            terrainTilesGrid[lineNumber][j] = TerrainTile.ROAD_HORIZONTAL;
                            break;
                        case "|":
                            terrainTilesGrid[lineNumber][j] = TerrainTile.ROAD_VERTICAL;
                            break;
                        case "z":
                            terrainTilesGrid[lineNumber][j] = TerrainTile.ROAD_TURN_DL;
                            break;
                        case "x":
                            terrainTilesGrid[lineNumber][j] = TerrainTile.ROAD_TURN_DR;
                            break;
                        case "c":
                            terrainTilesGrid[lineNumber][j] = TerrainTile.ROAD_TURN_UR;
                            break;
                        case "v":
                            terrainTilesGrid[lineNumber][j] = TerrainTile.ROAD_TURN_UL;
                            break;
                    }

                } // END OF column FOR LOOP

                // read the next line

                lineNumber++;
                line = br.readLine();

            } // END OF reading WHILE LOOP


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // END OF LoadMapTerrain METHOD

    void loadMapBuildings(int mapNumber) {

        // Default to none in case the input file doesn't have enough rows of data

        for (BuildingTile[] row : buildingTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                row[j] = BuildingTile.NONE;
            }
        }

        //File mapTerrainFile = new File("file:./resources/Map" + 1 + "_Terrain.txt");        Why doesn't this work???
        File mapTerrainFile = new File("C:/Users/Peter/Java Projects/Game_Clones/Advanced_Wars/resources/Map" + mapNumber + "_Buildings.txt");
        BufferedReader br = null;

        try {
            br = new BufferedReader(
                    new FileReader(mapTerrainFile));

            // read one line at a time

            int lineNumber = 0;
            String line = br.readLine();

            while(line != null) {

                System.out.println(line);
                // This loops through each column.  If the text input is too short it skips that iteration.

                for (int j = 0; j < buildingTilesGrid[0].length && j < line.length(); j++) {
                    System.out.println(line.substring(j, j + 1));
                    switch(line.substring(j,j + 1)) {
                        case "H":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.RED_HQ;
                            break;
                        case "B":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.RED_BASE;
                            break;
                        case "C":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.RED_CITY;
                            break;
                        case "h":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.BLUE_HQ;
                            break;
                        case "b":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.BLUE_BASE;
                            break;
                        case "c":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.BLUE_CITY;
                            break;
                        case "z":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.GRAY_CITY;
                            break;
                        case "x":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.GRAY_BASE;
                            break;
                        case "0":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.NONE;
                            break;
                    }

                } // END OF column FOR LOOP

                // read the next line

                lineNumber++;
                line = br.readLine();

            } // END OF reading WHILE LOOP


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // END OF LoadMapBuildings METHOD

    ImageIcon getResizedImageFromFile(String fileLocation) {

    ImageIcon originalIcon = new ImageIcon(fileLocation);
    Image image = originalIcon.getImage();
    Image resizedImage = image.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
    ImageIcon resizedIcon = new ImageIcon(resizedImage);
    return resizedIcon;
    } // END OF getResizedImageFromFile METHOD

    ImageIcon getResizedImageFromSpriteSheet(String fileLocation, int topLeftX, int topLeftY, int width, int height) {
///////////////////////////////////////////////////////////////////// NOT RIGHT!!!!!!!!!!!!!!!!!!
        ImageIcon originalIcon = new ImageIcon(fileLocation);
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    } // END OF getResizedImageFromFile METHOD

} // END OF GameBoard CLASS

class GameDrawingPanel extends JComponent {

    //// FIELDS

    GameBoard gameBoard;
    int terrainDrawingTopLeftXPos = 0;
    int terrainDrawingTopLeftYPos = 0;

/*
    ACCOUNT FOR MAP START LOCATION!!!!!!!!!!!!!!
    ACCOUNT FOR BORDERS OF JFRAME when clicking!!!!!!!!!
     */
    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        terrainDrawingTopLeftXPos += 0;
        terrainDrawingTopLeftYPos += 0;
    }

    // METHODS

    public void paint(Graphics g) {

        Graphics2D graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.WHITE);
        graphicSettings.fillRect(0,0, gameBoard.getMapWidth(), gameBoard.getMapHeight());

        graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /// draw terrain, then buildings, then units.  See if they can stack!

        // draw terrain

        drawTerrain(g);

        // draw buildings

        /* drawBuildings(g);
        ///////////////////////////////////////////////////// NOT DRAWING THIS FOR NOW.  Need to pull images from sprites and put the image in gameBoard
         */



    } // END OF paint METHOD

    void drawTerrain(Graphics g) {
        GameBoard.TerrainTile[][] tempTerrainTilesGrid = gameBoard.cloneTerrainTilesGrid();


        for (GameBoard.TerrainTile[] row : tempTerrainTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                switch(row[j]) {
                    case GRASS:
                        gameBoard.getResizedGrassIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case MOUNTAIN:
                        gameBoard.getResizedMountainIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case ROAD_HORIZONTAL:
                        gameBoard.getResizedRoadHorizontalIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case ROAD_VERTICAL:
                        gameBoard.getResizedRoadVerticalIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case ROAD_TURN_DL:
                        gameBoard.getResizedRoadTurnDLIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case ROAD_TURN_DR:
                        gameBoard.getResizedRoadTurnDRIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case ROAD_TURN_UR:
                        gameBoard.getResizedRoadTurnURIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case ROAD_TURN_UL:
                        gameBoard.getResizedRoadTurnULIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                }
                updateXYPositionForMapDrawing();
            }
        }
    } // END OF drawTerrain METHOD

    void drawBuildings(Graphics g) {
        GameBoard.BuildingTile[][] tempBuildingTilesGrid = gameBoard.cloneBuildingTilesGrid();

/*
                public enum BuildingTile {
                    NONE,
                    GRAY_BASE, GRAY_CITY,
                    RED_HQ, RED_BASE, RED_CITY,
                    BLUE_HQ, BLUE_BASE, BLUE_CITY
                }
                */
        for (GameBoard.BuildingTile[] row : tempBuildingTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                switch(row[j]) {
                    case NONE:
                        gameBoard.getResizedGrassIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case RED_HQ:
                        gameBoard.getResizedMountainIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case RED_BASE:
                        gameBoard.getResizedRoadHorizontalIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case RED_CITY:
                        gameBoard.getResizedRoadVerticalIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case BLUE_HQ:
                        gameBoard.getResizedRoadTurnDLIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case BLUE_BASE:
                        gameBoard.getResizedRoadTurnDRIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case BLUE_CITY:
                        gameBoard.getResizedRoadTurnURIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case GRAY_BASE:
                        gameBoard.getResizedRoadTurnULIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                    case GRAY_CITY:
                        gameBoard.getResizedRoadTurnULIcon().paintIcon(this, g, terrainDrawingTopLeftXPos, terrainDrawingTopLeftYPos);
                        break;
                }
                updateXYPositionForMapDrawing();
            }

        }

    } // END OF drawBuildings METHOD


    /*
    This updates the XY position.
    The first if statement will look for when the tile has gone to the edge of the screen, the second checks if it reaches
    the bottom of the screen.
     */
    public void updateXYPositionForMapDrawing() {
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