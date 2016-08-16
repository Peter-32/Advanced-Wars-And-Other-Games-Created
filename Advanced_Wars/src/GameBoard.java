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

    public ImageIcon getResizedGrayBaseIcon() {
        return resizedGrayBaseIcon;
    }

    public ImageIcon getResizedGrayCityIcon() {
        return resizedGrayCityIcon;
    }

    public ImageIcon getResizedRedHQIcon() {
        return resizedRedHQIcon;
    }

    public ImageIcon getResizedRedBaseIcon() {
        return resizedRedBaseIcon;
    }

    public ImageIcon getResizedRedCityIcon() {
        return resizedRedCityIcon;
    }

    public ImageIcon getResizedBlueHQIcon() {
        return resizedBlueHQIcon;
    }

    public ImageIcon getResizedBlueBaseIcon() {
        return resizedBlueBaseIcon;
    }

    public ImageIcon getResizedBlueCityIcon() {
        return resizedBlueCityIcon;
    }

    public int getjFrameHeight() {
        return jFrameHeight;
    }

    public int getjFrameWidth() {
        return jFrameWidth;
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

    public ImageIcon getResizedRedInfantry() {
        return resizedRedInfantry;
    }

    public ImageIcon getResizedRedMech() {
        return resizedRedMech;
    }

    public ImageIcon getResizedRedTank() {
        return resizedRedTank;
    }

    public ImageIcon getResizedRedArtillery() {
        return resizedRedArtillery;
    }

    public ImageIcon getResizedBlueInfantry() {
        return resizedBlueInfantry;
    }

    public ImageIcon getResizedBlueMech() {
        return resizedBlueMech;
    }

    public ImageIcon getResizedBlueTank() {
        return resizedBlueTank;
    }

    public ImageIcon getResizedBlueArtillery() {
        return resizedBlueArtillery;
    }
    public int getRedPlayerBank() {
        return redPlayerBank;
    }

    public void setRedPlayerBank(int redPlayerBank) {
        this.redPlayerBank = redPlayerBank;
    }

    public int getBluePlayerBank() {
        return bluePlayerBank;
    }

    public void setBluePlayerBank(int bluePlayerBank) {
        this.bluePlayerBank = bluePlayerBank;
    }
    public char getTurnColor() {
        return turnColor;
    }

    public void setTurnColor(char turnColor) {
        this.turnColor = turnColor;
    }

    //// FIELDS
    private final int jFrameWidth = 1500;
    private final int jFrameHeight = 780;
    private final int leftJFrameBorderLength = 8;
    private final int topJFrameBorderLength = 30;
    private final int menuStartX = 125;
    private final int menuStartY = 50;
    private final int menuWidth = 250;
    private final int menuHeight = 150;
    private final int mapStartX = 300;
    private final int mapStartY = 0;
    private final int mapWidth = 1200;
    private final int mapHeight = 750;
    private final int tileLength = mapHeight / 10;

    private int redPlayerBank = 5000;
    private int bluePlayerBank = 5000;
    private char turnColor = 'r'; // also 'b' is possible


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
        INFANTRY, MECH, ARTILLERY, TANK
    }
    private TerrainTile[][] terrainTilesGrid = new TerrainTile[10][16];
    private BuildingTile[][] buildingTilesGrid = new BuildingTile[10][16];
    private UnitTile[][] unitTilesGrid = new UnitTile[10][16];

    // Terrain

    private ImageIcon resizedMountainIcon;
    private ImageIcon resizedRoadTurnULIcon;
    private ImageIcon resizedRoadTurnURIcon;
    private ImageIcon resizedRoadTurnDLIcon;
    private ImageIcon resizedRoadTurnDRIcon;
    private ImageIcon resizedRoadVerticalIcon;
    private ImageIcon resizedRoadHorizontalIcon;
    private ImageIcon resizedGrassIcon;

    // Buildings

    private ImageIcon resizedGrayBaseIcon;
    private ImageIcon resizedGrayCityIcon;
    private ImageIcon resizedRedHQIcon;
    private ImageIcon resizedRedBaseIcon;
    private ImageIcon resizedRedCityIcon;
    private ImageIcon resizedBlueHQIcon;
    private ImageIcon resizedBlueBaseIcon;
    private ImageIcon resizedBlueCityIcon;

    // Units

    private ImageIcon resizedRedInfantry;
    private ImageIcon resizedRedMech;
    private ImageIcon resizedRedTank;
    private ImageIcon resizedRedArtillery;
    private ImageIcon resizedBlueInfantry;
    private ImageIcon resizedBlueMech;
    private ImageIcon resizedBlueTank;
    private ImageIcon resizedBlueArtillery;

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

        this.setSize(jFrameWidth, jFrameHeight);
        this.setTitle("Advance Wars");
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


        // load terrain sprite icons

        resizedMountainIcon = getResizedImageFromFile("resources/mountain.png");
        resizedRoadTurnULIcon = getResizedImageFromFile("resources/road_turn_ul.png");
        resizedRoadTurnURIcon = getResizedImageFromFile("resources/road_turn_ur.png");
        resizedRoadTurnDLIcon = getResizedImageFromFile("resources/road_turn_dl.png");
        resizedRoadTurnDRIcon = getResizedImageFromFile("resources/road_turn_dr.png");
        resizedRoadVerticalIcon = getResizedImageFromFile("resources/road_vertical.png");
        resizedRoadHorizontalIcon = getResizedImageFromFile("resources/road_horizontal.png");
        resizedGrassIcon = getResizedImageFromFile("resources/grass.png");

        // load buildings sprite icons

        resizedGrayBaseIcon = getResizedImageFromSpriteSheet(ssBuildings,372,28,19,18);
        resizedGrayCityIcon = getResizedImageFromSpriteSheet(ssBuildings,373,5,18,22);
        resizedRedHQIcon = getResizedImageFromSpriteSheet(ssBuildings,24,3,18,32);
        resizedRedBaseIcon = getResizedImageFromSpriteSheet(ssBuildings,24,196,18,18);
        resizedRedCityIcon = getResizedImageFromSpriteSheet(ssBuildings,25,173,18,21);
        resizedBlueHQIcon = getResizedImageFromSpriteSheet(ssBuildings,67,3,18,32);
        resizedBlueBaseIcon = getResizedImageFromSpriteSheet(ssBuildings,68,196,18,18);
        resizedBlueCityIcon = getResizedImageFromSpriteSheet(ssBuildings,68,173,18,21);

        // load unit sprite icons

        resizedRedInfantry = getResizedImageFromSpriteSheet(ssUnits,0,0,15,17);
        resizedRedMech = getResizedImageFromSpriteSheet(ssUnits,15,0,17,17);
        resizedRedTank = getResizedImageFromSpriteSheet(ssUnits,78,0,17,18);
        resizedRedArtillery = getResizedImageFromSpriteSheet(ssUnits,143,0,17,16);
        resizedBlueInfantry = getResizedImageFromSpriteSheet(ssUnits,0,16,15,16);
        resizedBlueMech = getResizedImageFromSpriteSheet(ssUnits,15,16,17,16);
        resizedBlueTank = getResizedImageFromSpriteSheet(ssUnits,80,17,16,15);
        resizedBlueArtillery = getResizedImageFromSpriteSheet(ssUnits,144,16,16,16);


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

                // This loops through each column.  If the text input is too short it skips that iteration.

                for (int j = 0; j < terrainTilesGrid[0].length && j < line.length(); j++) {
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

                // This loops through each column.  If the text input is too short it skips that iteration.

                for (int j = 0; j < buildingTilesGrid[0].length && j < line.length(); j++) {
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

    ImageIcon getResizedImageFromSpriteSheet(SpriteSheet ss, int topLeftX, int topLeftY, int width, int height) {
        BufferedImage sprite = ss.grabSprite(topLeftX, topLeftY, width, height);
        ImageIcon originalIcon = new ImageIcon(sprite);
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    } // END OF getResizedImageFromFile METHOD

} // END OF GameBoard CLASS

class GameDrawingPanel extends JComponent {

    //// FIELDS

    GameBoard gameBoard;
    int drawingTopLeftXPos;
    int drawingTopLeftYPos;
    JLabel menuTitleLabel, redPlayerBankLabel, player2BankLabel;
    JButton redInfantryBtn, redMechBtn, redTankBtn, redArtilleryBtn, blueInfantryBtn, blueMechBtn, blueTankBtn,
            blueArtilleryBtn;
    Boolean buttonsUpdatedAlready = false;


    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        drawingTopLeftXPos = gameBoard.getMapStartX();
        drawingTopLeftYPos = gameBoard.getMapStartY();

        menuTitleLabel = new JLabel("Advance Wars");
        menuTitleLabel.setLocation(0,0);
        this.add(menuTitleLabel);
        redPlayerBankLabel = new JLabel(Integer.toString(gameBoard.getRedPlayerBank()));
        redPlayerBankLabel.setLocation(0,675);
        this.add(redPlayerBankLabel);
        player2BankLabel = new JLabel(Integer.toString(gameBoard.getBluePlayerBank()));
        player2BankLabel.setLocation(75,750);
        this.add(player2BankLabel);

        redInfantryBtn = new JButton("1000 G");
        redMechBtn = new JButton("3000 G");
        redArtilleryBtn = new JButton("6000 G");
        redTankBtn = new JButton("7000 G");

        blueInfantryBtn = new JButton("1000 G");
        blueMechBtn = new JButton("3000 G");
        blueArtilleryBtn = new JButton("6000 G");
        blueTankBtn = new JButton("7000 G");

    }

    // METHODS

    public void paint(Graphics g) {

        Graphics2D graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.WHITE);
        graphicSettings.fillRect(0,0, gameBoard.getjFrameWidth(), gameBoard.getjFrameHeight());

        graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /// draw terrain, then buildings, then units.  See if they can stack!

        // draw terrain

        drawTerrain(g);

        // draw buildings

        drawBuildings(g);

        /// draw menu

        drawMenu(g);

    } // END OF paint METHOD

    void drawTerrain(Graphics g) {
        GameBoard.TerrainTile[][] tempTerrainTilesGrid = gameBoard.cloneTerrainTilesGrid();


        for (GameBoard.TerrainTile[] row : tempTerrainTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                switch(row[j]) {
                    case GRASS:
                        gameBoard.getResizedGrassIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case MOUNTAIN:
                        gameBoard.getResizedMountainIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case ROAD_HORIZONTAL:
                        gameBoard.getResizedRoadHorizontalIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case ROAD_VERTICAL:
                        gameBoard.getResizedRoadVerticalIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case ROAD_TURN_DL:
                        gameBoard.getResizedRoadTurnDLIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case ROAD_TURN_DR:
                        gameBoard.getResizedRoadTurnDRIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case ROAD_TURN_UR:
                        gameBoard.getResizedRoadTurnURIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case ROAD_TURN_UL:
                        gameBoard.getResizedRoadTurnULIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
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
                        break;
                    case RED_HQ:
                        gameBoard.getResizedRedHQIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case RED_BASE:
                        gameBoard.getResizedRedBaseIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case RED_CITY:
                        gameBoard.getResizedRedCityIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case BLUE_HQ:
                        gameBoard.getResizedBlueHQIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case BLUE_BASE:
                        gameBoard.getResizedBlueBaseIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case BLUE_CITY:
                        gameBoard.getResizedBlueCityIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case GRAY_BASE:
                        gameBoard.getResizedGrayBaseIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                    case GRAY_CITY:
                        gameBoard.getResizedGrayCityIcon().paintIcon(this, g, drawingTopLeftXPos, drawingTopLeftYPos);
                        break;
                }
                updateXYPositionForMapDrawing();
            }

        }

    } // END OF drawBuildings METHOD
/*
    public enum UnitTile {
        INFANTRY, MECH, TANK, ARTILLERY
    }
 */
    void drawMenu(Graphics g) {

        gameBoard.getResizedRedInfantry().paintIcon(this, g, 0, 75);
        gameBoard.getResizedRedMech().paintIcon(this, g, 0, 150);
        gameBoard.getResizedRedArtillery().paintIcon(this, g, 0, 225);
        gameBoard.getResizedRedTank().paintIcon(this, g, 0, 300);
        gameBoard.getResizedBlueInfantry().paintIcon(this, g, 0, 375);
        gameBoard.getResizedBlueMech().paintIcon(this, g, 0, 450);
        gameBoard.getResizedBlueArtillery().paintIcon(this, g, 0, 525);
        gameBoard.getResizedBlueTank().paintIcon(this, g, 0, 600);

        // update bank numbers

        redPlayerBankLabel = new JLabel(Integer.toString(gameBoard.getRedPlayerBank()));
        player2BankLabel = new JLabel(Integer.toString(gameBoard.getBluePlayerBank()));

        this.add(menuTitleLabel);
        this.add(redPlayerBankLabel);
        this.add(player2BankLabel);

        /*
                menuTitleLabel = new JLabel("Advance Wars");
        menuTitleLabel.setLocation(0,0);
        this.add(menuTitleLabel);
        redPlayerBankLabel = new JLabel(Integer.toString(gameBoard.getRedPlayerBank()));
        redPlayerBankLabel.setLocation(0,675);
        this.add(redPlayerBankLabel);
        player2BankLabel = new JLabel(Integer.toString(gameBoard.getBluePlayerBank()));
        player2BankLabel.setLocation(75,750);
        this.add(player2BankLabel);
         */

        // update buttons to enable / disable based on player's turn.  Update once per turn

        if (!buttonsUpdatedAlready) {
            updateButtonAccess(gameBoard.getTurnColor());
        }


        // draw labels



/*
        menuTitleLabel = new JLabel("Advance Wars");
        redPlayerBankLabel = new JLabel(Integer.toString(gameBoard.getRedPlayerBank()));
        player2BankLabel = new JLabel(Integer.toString(gameBoard.getBluePlayerBank()));

        redInfantryBtn = new JButton("1000 G");
        redMechBtn = new JButton("3000 G");
        redArtilleryBtn = new JButton("6000 G");
        redTankBtn = new JButton("7000 G");

        blueInfantryBtn = new JButton("1000 G");
        blueMechBtn = new JButton("3000 G");
        blueArtilleryBtn = new JButton("6000 G");
        blueTankBtn = new JButton("7000 G");
 */
    }

    void updateButtonAccess(char color) {
        if (color == 'r') {
            redInfantryBtn.setEnabled(true);
            redMechBtn.setEnabled(true);
            redArtilleryBtn.setEnabled(true);
            redTankBtn.setEnabled(true);
            blueInfantryBtn.setEnabled(false);
            blueMechBtn.setEnabled(false);
            blueArtilleryBtn.setEnabled(false);
            blueTankBtn.setEnabled(false);
        } else if (color == 'b') {
            redInfantryBtn.setEnabled(false);
            redMechBtn.setEnabled(false);
            redArtilleryBtn.setEnabled(false);
            redTankBtn.setEnabled(false);
            blueInfantryBtn.setEnabled(true);
            blueMechBtn.setEnabled(true);
            blueArtilleryBtn.setEnabled(true);
            blueTankBtn.setEnabled(true);
        }
        buttonsUpdatedAlready = true;
    }

    /*
    This updates the XY position.
    The first if statement will look for when the tile has gone to the edge of the screen, the second checks if it reaches
    the bottom of the screen.
     */
    public void updateXYPositionForMapDrawing() {
        if (drawingTopLeftXPos < gameBoard.getMapWidth() + gameBoard.getMapStartX() - gameBoard.getTileLength()) {
            drawingTopLeftXPos += gameBoard.getTileLength();
        } else if (drawingTopLeftYPos < gameBoard.getMapHeight() + gameBoard.getMapStartY() - gameBoard.getTileLength()) {
            drawingTopLeftXPos = gameBoard.getMapStartX();
            drawingTopLeftYPos += gameBoard.getTileLength();
        } else {
            drawingTopLeftXPos = gameBoard.getMapStartX();
            drawingTopLeftYPos = gameBoard.getMapStartY();
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