import Images.BufferedImageLoader;
import Images.SpriteSheet;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


import static javafx.scene.input.KeyCode.G;

/**
 * Created by Peter on 8/14/2016.
 */
public class GameBoard extends JFrame {

    //// GETTERS AND SETTERS

    synchronized TerrainTile getTerrainAtXYTile(int xTile, int yTile) {
        return terrainTilesGrid[yTile][xTile];
    }
    synchronized BuildingTile getBuildingAtXYTile(int xTile, int yTile) {
        return buildingTilesGrid[yTile][xTile];
    }
    public TerrainTile[][] cloneTerrainTilesGrid() {

        TerrainTile[][] clonedTerrainTilesGrid = new TerrainTile[10][16];
        for(int i = 0; i < terrainTilesGrid.length; i++) {
            clonedTerrainTilesGrid[i] = terrainTilesGrid[i].clone();
        }
        return clonedTerrainTilesGrid;
    }
    public BuildingTile[][] cloneBuildingTilesGrid() {

        BuildingTile[][] clonedBuildingTilesGrid = new BuildingTile[10][16];
        for(int i = 0; i < buildingTilesGrid.length; i++) {
            clonedBuildingTilesGrid[i] = buildingTilesGrid[i].clone();
        }
        return clonedBuildingTilesGrid;
    }
    public boolean[][] cloneCurrentMoveableChoicesGrid() {

        boolean[][] clonedCurrentMoveableChoicesGrid = new boolean[10][16];
        for(int i = 0; i < currentMoveableChoicesGrid.length; i++) {
            clonedCurrentMoveableChoicesGrid[i] = currentMoveableChoicesGrid[i].clone();
        }
        return clonedCurrentMoveableChoicesGrid;
    }
    synchronized public void updateCurrentMoveableChoicesGrid(int x, int y, boolean newValue) {
        currentMoveableChoicesGrid[y][x] = newValue;
    }
    synchronized public void updateBuildingTilesGrid(int x, int y, BuildingTile newValue) {
        buildingTilesGrid[y][x] = newValue;
    }
    synchronized public void resetCurrentMoveableChoicesGrid() {
        for (int i = 0; i < currentMoveableChoicesGrid.length; i++) {
            for (int j = 0; j < currentMoveableChoicesGrid[0].length; j++) {
                currentMoveableChoicesGrid[i][j] = false;
            }
        }

    }
    public boolean[][] cloneCurrentAttackChoicesGrid() {

        boolean[][] clonedCurrentAttackChoicesGrid = new boolean[10][16];
        for(int i = 0; i < currentAttackChoicesGrid.length; i++) {
            clonedCurrentAttackChoicesGrid[i] = currentAttackChoicesGrid[i].clone();
        }
        return clonedCurrentAttackChoicesGrid;
    }
    synchronized public void updateCurrentAttackChoicesGrid(int x, int y, boolean newValue) {
        currentAttackChoicesGrid[y][x] = newValue;
    }
    synchronized public void resetCurrentAttackChoicesGrid() {
        for (int i = 0; i < currentAttackChoicesGrid.length; i++) {
            for (int j = 0; j < currentAttackChoicesGrid[0].length; j++) {
                currentAttackChoicesGrid[i][j] = false;
            }
        }

    }

    public int getRedHQXTile() {
        return redHQXTile;
    }

    public int getRedHQYTile() {
        return redHQYTile;
    }

    public int getBlueHQXTile() {
        return blueHQXTile;
    }

    public int getBlueHQYTile() {
        return blueHQYTile;
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

    public int getXClicked() {
        return xClicked;
    }

    public void setXClicked(int xClicked) {
        this.xClicked = xClicked;
    }

    public int getYClicked() {
        return yClicked;
    }

    public void setYClicked(int yClicked) {
        this.yClicked = yClicked;
    }

    public boolean isPressedTheAKeyWhileUnitSelected() {
        return pressedTheAKeyWhileUnitSelected;
    }
    public boolean isWaitForTheAKeyRelease() {
        return waitForTheAKeyRelease;
    }

    public void setWaitForTheAKeyRelease(boolean waitForTheAKeyRelease) {
        this.waitForTheAKeyRelease = waitForTheAKeyRelease;
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

    public int getMapTileHeight() {
        return mapTileHeight;
    }

    public int getMapTileWidth() {
        return mapTileWidth;
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
    public ImageIcon getResizedCursor() {
        return resizedCursor;
    }
    public int getCursorMapTileY() {
        return cursorMapTileY;
    }

    public void setCursorMapTileY(int cursorMapTileY) {
        this.cursorMapTileY = cursorMapTileY;
    }

    public int getCursorMapTileX() {
        return cursorMapTileX;
    }

    public void setCursorMapTileX(int cursorMapTileX) {
        this.cursorMapTileX = cursorMapTileX;
    }

    public void setAMilitaryUnitSelected(boolean aMilitaryUnitSelected) {
        this.aMilitaryUnitSelected = aMilitaryUnitSelected;
    }
    public boolean isARangedMilitaryUnitSelected() {
        return aRangedMilitaryUnitSelected;
    }

    public void setARangedMilitaryUnitSelected(boolean aRangedMilitaryUnitSelected) {
        this.aRangedMilitaryUnitSelected = aRangedMilitaryUnitSelected;
    }

    public boolean isAMeleeMilitaryUnitSelected() {
        return aMeleeMilitaryUnitSelected;
    }

    public void setAMeleeMilitaryUnitSelected(boolean aMeleeMilitaryUnitSelected) {
        this.aMeleeMilitaryUnitSelected = aMeleeMilitaryUnitSelected;
    }
    public int getClickType() {
        return clickType;
    }

    public void setClickType(int clickType) {
        this.clickType = clickType;
    }
    public int getEndTurnBtnHeight() {
        return endTurnBtnHeight;
    }

    public int getEndTurnBtnWidth() {
        return endTurnBtnWidth;
    }

    public int getEndTurnBtnStartY() {
        return endTurnBtnStartY;
    }

    public int getEndTurnBtnStartX() {
        return endTurnBtnStartX;
    }
    public ImageIcon getResizedRedButton() {
        return resizedRedButton;
    }

    public ImageIcon getResizedBlueButton() {
        return resizedBlueButton;
    }

    public ImageIcon getResizedWhiteButton() {
        return resizedWhiteButton;
    }

    public ImageIcon getResizedGreenEndTurnButton() {
        return resizedGreenEndTurnButton;
    }
    public int getPurchaseBtnsStartX() {
        return purchaseBtnsStartX;
    }

    public int getPurchaseBtnsStartY() {
        return purchaseBtnsStartY;
    }

    public int getPurchaseBtnsWidth() {
        return purchaseBtnsWidth;
    }

    public int getPurchaseBtnsHeight() {
        return purchaseBtnsHeight;
    }
    public int getPurchaseBtnsTopMargin() {
        return purchaseBtnsTopMargin;
    }
    public boolean isBuyingFromBasePossible() {
        return buyingFromBasePossible;
    }

    public void setBuyingFromBasePossible(boolean buyingFromBasePossible) {
        this.buyingFromBasePossible = buyingFromBasePossible;
    }
    public MilitaryUnit getSelectedMilitaryUnit() {
        return selectedMilitaryUnit;
    }

    public void setSelectedMilitaryUnit() {
        MilitaryUnit currentMilitaryUnit = null;
        MilitaryUnit selectedMilitaryUnit = null;

        // loop through all units

        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = militaryUnitsIterator();
        while (tempMilitaryUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempMilitaryUnitsIterator.next();
            if (currentMilitaryUnit.isSelected()) {
                selectedMilitaryUnit = currentMilitaryUnit;
            }
        }

        // this value can be null.

        this.selectedMilitaryUnit = selectedMilitaryUnit;
    }
    public Iterator<MilitaryUnit> militaryUnitsIterator() {
        readLock.lock();
        try {
            return new ArrayList<MilitaryUnit>(militaryUnits).iterator();
            // we iterate over a snapshot of our list
        } finally {
            readLock.unlock();
        }
    }
    public void addMilitaryUnits(MilitaryUnit e) {
        writeLock.lock();
        try {
            militaryUnits.add(e);
        } finally{
            writeLock.unlock();
        }
    }
    public void removeMilitaryUnits(MilitaryUnit e) {
        writeLock.lock();
        try {
            militaryUnits.remove(e);

            // Sound can possibly go here

        } finally{
            writeLock.unlock();
        }
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
    private final int mapHeight = 750;
    private final int mapWidth = (int) (mapHeight * 16 / 10);
    private final int tileLength = mapHeight / 10;
    private final int mapTileHeight = (int) (mapHeight / tileLength);
    private final int mapTileWidth = (int) (mapWidth / tileLength);
    private final int endTurnBtnStartX = 40;
    private final int endTurnBtnStartY = (int) (1.46 * tileLength);
    private final int endTurnBtnWidth = (int) (2.93 * tileLength);
    private final int endTurnBtnHeight = (int) (0.5 * tileLength);
    private final int purchaseBtnsStartX = tileLength;
    private final int purchaseBtnsStartY = (int) (2.15 * tileLength);
    private final int purchaseBtnsWidth = 2 * tileLength;
    private final int purchaseBtnsHeight = (int) (0.8 * tileLength);
    private final int purchaseBtnsTopMargin = (int) (0.2 * tileLength);

    // Nodes and edges and put in this graph

    WeightedGraph graph;

    // the units list, Infantry, mech, artillery, tank

    private final java.util.List<MilitaryUnit> militaryUnits = new ArrayList();

    // Bank

    private int redPlayerBank = 5000;
    private int bluePlayerBank = 2000;

    // this stores the tile that should hold the cursor.  0,0  is default because it is always on the board.

    private int cursorMapTileX = 0;
    private int cursorMapTileY = 0;

    // sprite sheets

    SpriteSheet ssBuildings;
    SpriteSheet ssMilitaryUnits;
    SpriteSheet ssBtns;

    // Other board states

    private boolean lockInput = false; // not sure if this still be used
    private int yClicked = -1;
    private int xClicked = -1;
    private int clickType = -1;
    private boolean pressedTheAKeyWhileUnitSelected = false;
    private boolean waitForTheAKeyRelease = false;
    private boolean GameOver = false;
    private boolean aMilitaryUnitSelected = false;
    private boolean aRangedMilitaryUnitSelected = false;
    private boolean aMeleeMilitaryUnitSelected = false;
    private MilitaryUnit selectedMilitaryUnit = null;
    private int redHQXTile;
    private int redHQYTile;
    private int blueHQXTile;
    private int blueHQYTile;


    // Turn, base clicked on (a base allows you to buy units)

    private char turnColor = 'r'; // also 'b' is possible
    private boolean buyingFromBasePossible = false;

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
    public enum MilitaryUnitType {
        INFANTRY, MECH, ARTILLERY, TANK
    }

    private TerrainTile[][] terrainTilesGrid = new TerrainTile[10][16];
    private BuildingTile[][] buildingTilesGrid = new BuildingTile[10][16];
    private boolean[][] currentMoveableChoicesGrid = new boolean[10][16];
    private boolean[][] currentAttackChoicesGrid = new boolean[10][16];

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

    // Military Units

    private ImageIcon resizedRedInfantry;
    private ImageIcon resizedRedMech;
    private ImageIcon resizedRedTank;
    private ImageIcon resizedRedArtillery;
    private ImageIcon resizedBlueInfantry;
    private ImageIcon resizedBlueMech;
    private ImageIcon resizedBlueTank;
    private ImageIcon resizedBlueArtillery;

    // cursor and movement

    private ImageIcon resizedCursor;

    // buttons

    private ImageIcon resizedRedButton;
    private ImageIcon resizedBlueButton;
    private ImageIcon resizedWhiteButton;
    private ImageIcon resizedGreenEndTurnButton;


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
        this.setLocationRelativeTo(null);
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
        BufferedImage spriteSheetMilitaryUnits = null;
        BufferedImage spriteSheetBtns = null;
        try {
            spriteSheetBuildings = loader.loadImage("file:./resources/Game Boy Advance - Advance Wars 2 - Buildings.png");
            spriteSheetMilitaryUnits = loader.loadImage("file:./resources/Map_units.png");
            spriteSheetBtns = loader.loadImage("file:./resources/game_button_spritesheet.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ssBuildings = new SpriteSheet(spriteSheetBuildings);
        ssMilitaryUnits = new SpriteSheet(spriteSheetMilitaryUnits);
        ssBtns = new SpriteSheet(spriteSheetBtns);


        // load terrain sprite icons

        resizedMountainIcon = getResizedTilesizeImageFromFile("resources/mountain.png");
        resizedRoadTurnULIcon = getResizedTilesizeImageFromFile("resources/road_turn_ul.png");
        resizedRoadTurnURIcon = getResizedTilesizeImageFromFile("resources/road_turn_ur.png");
        resizedRoadTurnDLIcon = getResizedTilesizeImageFromFile("resources/road_turn_dl.png");
        resizedRoadTurnDRIcon = getResizedTilesizeImageFromFile("resources/road_turn_dr.png");
        resizedRoadVerticalIcon = getResizedTilesizeImageFromFile("resources/road_vertical.png");
        resizedRoadHorizontalIcon = getResizedTilesizeImageFromFile("resources/road_horizontal.png");
        resizedGrassIcon = getResizedTilesizeImageFromFile("resources/grass.png");

        // load buildings sprite icons

        resizedGrayBaseIcon = getResizedTilesizeImageFromSpriteSheet(ssBuildings,372,28,19,18);
        resizedGrayCityIcon = getResizedTilesizeImageFromSpriteSheet(ssBuildings,373,5,18,22);
        resizedRedHQIcon = getResizedTilesizeImageFromSpriteSheet(ssBuildings,24,3,18,32);
        resizedRedBaseIcon = getResizedTilesizeImageFromSpriteSheet(ssBuildings,24,196,18,18);
        resizedRedCityIcon = getResizedTilesizeImageFromSpriteSheet(ssBuildings,25,173,18,21);
        resizedBlueHQIcon = getResizedTilesizeImageFromSpriteSheet(ssBuildings,67,3,18,32);
        resizedBlueBaseIcon = getResizedTilesizeImageFromSpriteSheet(ssBuildings,68,196,18,18);
        resizedBlueCityIcon = getResizedTilesizeImageFromSpriteSheet(ssBuildings,68,173,18,21);

        // load unit sprite icons

        resizedRedInfantry = getResizedTilesizeImageFromSpriteSheet(ssMilitaryUnits,0,0,15,17);
        resizedRedMech = getResizedTilesizeImageFromSpriteSheet(ssMilitaryUnits,15,0,17,17);
        resizedRedTank = getResizedTilesizeImageFromSpriteSheet(ssMilitaryUnits,78,0,17,18);
        resizedRedArtillery = getResizedTilesizeImageFromSpriteSheet(ssMilitaryUnits,143,0,17,16);
        resizedBlueInfantry = getResizedTilesizeImageFromSpriteSheet(ssMilitaryUnits,0,16,15,16);
        resizedBlueMech = getResizedTilesizeImageFromSpriteSheet(ssMilitaryUnits,15,16,17,16);
        resizedBlueTank = getResizedTilesizeImageFromSpriteSheet(ssMilitaryUnits,80,17,16,15);
        resizedBlueArtillery = getResizedTilesizeImageFromSpriteSheet(ssMilitaryUnits,144,16,16,16);

        // load button sprite icons
        resizedRedButton = getResizedImageFromSpriteSheet(ssBtns,111,97,99,25, purchaseBtnsWidth, purchaseBtnsHeight);
        resizedBlueButton = getResizedImageFromSpriteSheet(ssBtns,112,127,98,24, purchaseBtnsWidth, purchaseBtnsHeight);
        resizedWhiteButton = getResizedImageFromSpriteSheet(ssBtns,110,8,100,25, purchaseBtnsWidth, purchaseBtnsHeight);
        resizedGreenEndTurnButton = getResizedImageFromSpriteSheet(ssBtns,112,37,98,26, endTurnBtnWidth, endTurnBtnHeight);

        // cursor and movement

        resizedCursor = getResizedTilesizeImageFromFile("resources/cursor.png");

        // load map #1

        loadMap(1);

        // Start the mouse listener class
        // his prompts the main game loop each time there is user input

        ListenForMouse lForMouse = new ListenForMouse();
        addMouseListener(lForMouse);
        KeyListener lForKey = new ListenForKey();
        addKeyListener(lForKey);

        // create the GameDrawingPanel

        GameDrawingPanel gameDrawingPanel = new GameDrawingPanel(this);
        this.add(gameDrawingPanel, BorderLayout.CENTER);

        // create testing units

        addMilitaryUnits(new Tank('r', 6, 8, true, false, false, 0));
        addMilitaryUnits(new Tank('b', 7, 8, true, false, false, 0));

        // final changes to JFrame

        setResizable(false);
        this.setVisible(true);

        // Add multi-threading for the game loop

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(new MainGameLoop(this, gameDrawingPanel), 0L, 20L, TimeUnit.MILLISECONDS);
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
            if (!lockInput) {// lock is off

                // Account for the size of the left border

                xClicked = e.getX() - leftJFrameBorderLength;

                // Account for the size of the top border

                yClicked = e.getY() - topJFrameBorderLength;
            }

            clickType = e.getButton();
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

    private class ListenForKey implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == 65 &&
                    getSelectedMilitaryUnit() != null && // a military unit is selected
                    !waitForTheAKeyRelease) {
                pressedTheAKeyWhileUnitSelected = true;
            } else if (e.getKeyCode() == 65) {
                waitForTheAKeyRelease = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 65) {
                waitForTheAKeyRelease = false;
                pressedTheAKeyWhileUnitSelected = false;
            }
        }
    }


    public int findXTileClickedOn() {

        // if xPosition is out of bounds then -1, otherwise get the right grid number

        if (xClicked < mapStartX || xClicked > mapStartX + mapWidth) {
            return -1;
        } else {
            return (xClicked - mapStartX) / tileLength;
        }
    }

    public int findYTileClickedOn() {

        // if xPosition is out of bounds then -1, otherwise get the right grid number

        if (yClicked < mapStartY || yClicked > mapStartY + mapHeight) {
            return -1;
        } else {
            return (yClicked - mapStartY) / tileLength;
        }
    }

    public MilitaryUnit getMilitaryUnitAtXYTile(int xTile, int yTile) {
        MilitaryUnit currentMilitaryUnit = null;
        MilitaryUnit selectedMilitaryUnit = null;

        // loop through all units

        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = militaryUnitsIterator();
        while (tempMilitaryUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempMilitaryUnitsIterator.next();
            if (currentMilitaryUnit.getXTile() == xTile &&
                    currentMilitaryUnit.getYTile() == yTile) {
                selectedMilitaryUnit = currentMilitaryUnit;
            }
        }

        // this value can be null.

        return selectedMilitaryUnit;
    }

    void loadMap(int mapNumber) {

        // load map terrain

        loadMapTerrain(mapNumber);

        // load the graph nodes/edges based on the terrain

        graph = loadTerrainGraph();

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
        // USE THIS ON MAC File mapTerrainFile = new File("/Users/peterjmyers/IdeaProjects/GameClones/Game-Clones/Advanced_Wars/resources/Map" + mapNumber + "_Terrain.txt");
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

    /*
    The purpose of a graph is to be able to use graph search to find which spaces can be moved to by military units.
     */

    WeightedGraph loadTerrainGraph() {

        int countNodes = 0;
        int countEdges = 0;

        // use terrainTilesGrid to build the nodes and edges

        // add all nodes

        WeightedGraph g = new WeightedGraph(this);
        for (int i = 0; i < terrainTilesGrid.length; i++) {
            for (int j = 0; j < terrainTilesGrid[0].length; j++) {
                g.addNode(terrainTilesGrid[i][j], j, i);
                countNodes++;
            }
        }

        // add all edges.  It is inefficient, but this only has to be run once when the map is loaded.

        WeightedGraph.Node currentNode = null;
        WeightedGraph.Node otherNode = null;

        for (int i = 0; i < terrainTilesGrid.length; i++) {
            for (int j = 0; j < terrainTilesGrid[0].length; j++) {

                // find the current node

                currentNode = g.getNodeAtLocation(j, i);

                // add the edges connected directly up from the current node

                if (i != 0) {
                    otherNode = g.getNodeAtLocation(j, i - 1);
                    g.addEdge(currentNode, otherNode);
                    countEdges+=2;
                }

                // add the edges connected directly right from the current node

                if (j != terrainTilesGrid[0].length - 1) {
                    otherNode = g.getNodeAtLocation(j + 1, i);
                    g.addEdge(currentNode, otherNode);
                    countEdges+=2;
                }

                // add the edges connected directly down from the current node

                if (i != terrainTilesGrid.length - 1) {
                    otherNode = g.getNodeAtLocation(j, i + 1);
                    g.addEdge(currentNode, otherNode);
                    countEdges+=2;
                }

                // add the edges connected directly right from the current node

                if (j != 0) {
                    otherNode = g.getNodeAtLocation(j - 1, i);
                    g.addEdge(currentNode, otherNode);
                    countEdges+=2;
                }

            } // END OF j FOR LOOP

        } // END OF i FOR LOOP

        return g;

    } // END OF loadTerrainGraph METHOD

    void loadMapBuildings(int mapNumber) {

        // Default to none in case the input file doesn't have enough rows of data

        for (BuildingTile[] row : buildingTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                row[j] = BuildingTile.NONE;
            }
        }

        //File mapTerrainFile = new File("file:./resources/Map" + 1 + "_Terrain.txt");        Why doesn't this work???
        File mapTerrainFile = new File("C:/Users/Peter/Java Projects/Game_Clones/Advanced_Wars/resources/Map" + mapNumber + "_Buildings.txt");
        // USE THIS ON MAC File mapTerrainFile = new File("/Users/peterjmyers/IdeaProjects/GameClones/Game-Clones/Advanced_Wars/resources/Map" + mapNumber + "_Buildings.txt");
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
                            redHQXTile = j;
                            redHQYTile = lineNumber;
                            break;
                        case "B":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.RED_BASE;
                            break;
                        case "C":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.RED_CITY;
                            break;
                        case "h":
                            buildingTilesGrid[lineNumber][j] = BuildingTile.BLUE_HQ;
                            blueHQXTile = j;
                            blueHQYTile = lineNumber;
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

    ImageIcon getResizedTilesizeImageFromFile(String fileLocation) {

        ImageIcon originalIcon = new ImageIcon(fileLocation);
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(tileLength, tileLength, java.awt.Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    } // END OF getResizedImageFromFile METHOD

    ImageIcon getResizedTilesizeImageFromSpriteSheet(SpriteSheet ss, int topLeftX, int topLeftY, int width, int height) {
        return getResizedImageFromSpriteSheet(ss, topLeftX, topLeftY, width, height, tileLength, tileLength);
    } // END OF getResizedImageFromFile METHOD

    ImageIcon getResizedImageFromSpriteSheet(SpriteSheet ss, int topLeftX, int topLeftY, int width, int height,
                                             int newWidth, int newHeight) {
        BufferedImage sprite = ss.grabSprite(topLeftX, topLeftY, width, height);
        ImageIcon originalIcon = new ImageIcon(sprite);
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    } // END OF getResizedImageFromFile METHOD

    /*
    This method allows you to call this method against the graph stored in this GameBoard
     */

    public CopyOnWriteArrayList<WeightedGraph.Node> runNodesAccessibleFromLocationByUnitType(WeightedGraph.Node startingNode, MilitaryUnitType militaryUnitType) {
        return graph.nodesAccessibleFromLocationByUnitType(startingNode, militaryUnitType);
    }

    /*
    This method allows you to call this method against the graph stored in this GameBoard
     */

    public WeightedGraph.Node getNodeAtLocation(int xTile, int yTile) {
        return graph.getNodeAtLocation(xTile, yTile);
    }

    public void resetMilitaryUnitSelected() {
        System.out.println("test3");
        MilitaryUnit currentMilitaryUnit = null;
        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = militaryUnitsIterator();

        while (tempMilitaryUnitsIterator.hasNext()) {

            currentMilitaryUnit = tempMilitaryUnitsIterator.next();
            currentMilitaryUnit.setSelected(false);

        }
        updateGameBoardSpecificSelectedMilitaryUnitVariables(false, false);

    } // END OF resetMilitaryUnitSelected METHOD

    public void updateAttackSquares() {

        if (isAMeleeMilitaryUnitSelected()) {
            updateMeleeAttackSquares();
        } else if (isARangedMilitaryUnitSelected()) {
            updateRangedAttackSquares();
        }
    } // END OF updateAttackSquares METHOD

    void updateMeleeAttackSquares() {

        // variables

        int xTile, yTile;

        // reset

        resetCurrentAttackChoicesGrid();

        // if no unit is selected then return.  Probably unnecessary, but shouldn't hurt to include this.

        if (getSelectedMilitaryUnit() == null) {
            return;
        }

        // find the current x, y position

        xTile = getSelectedMilitaryUnit().getXTile();
        yTile = getSelectedMilitaryUnit().getYTile();

        if (xTile != 0) {
            updateCurrentAttackChoicesGrid(xTile - 1, yTile, true);
        }

        if (xTile != getMapTileWidth() - 1) {
            updateCurrentAttackChoicesGrid(xTile + 1, yTile, true);
        }

        if (yTile != 0) {
            updateCurrentAttackChoicesGrid(xTile, yTile - 1, true);
        }

        if (yTile != getMapTileHeight() - 1) {
            updateCurrentAttackChoicesGrid(xTile, yTile + 1, true);
        }

    } // END OF updateMeleeAttackSquares METHOD

    void updateRangedAttackSquares() {

        // variables

        int xTile, yTile;

        // reset

        resetCurrentAttackChoicesGrid();

        // if no unit is selected then return.  Probably unnecessary, but shouldn't hurt to include this.

        if (getSelectedMilitaryUnit() == null) {
            return;
        }

        // find the current x, y position

        xTile = getSelectedMilitaryUnit().getXTile();
        yTile = getSelectedMilitaryUnit().getYTile();

        // furthest left

        if (xTile >= 3) {
            updateCurrentAttackChoicesGrid(xTile - 3, yTile, true);
        }

        // furthest up

        if (yTile >= 3) {
            updateCurrentAttackChoicesGrid(xTile, yTile - 3, true);
        }

        // furthest right

        if (xTile <= getMapTileWidth() - 4) {
            updateCurrentAttackChoicesGrid(xTile + 3, yTile, true);
        }

        // furthest down

        if (yTile <= getMapTileHeight() - 4) {
            updateCurrentAttackChoicesGrid(xTile, yTile + 3, true);
        }

        // left 2

        if (xTile >= 2) {
            updateCurrentAttackChoicesGrid(xTile - 2, yTile, true);
        }

        // up 2

        if (yTile >= 2) {
            updateCurrentAttackChoicesGrid(xTile, yTile - 2, true);
        }

        // right 2

        if (xTile <= getMapTileWidth() - 3) {
            updateCurrentAttackChoicesGrid(xTile + 2, yTile, true);
        }

        // down 2

        if (yTile <= getMapTileHeight() - 3) {
            updateCurrentAttackChoicesGrid(xTile, yTile + 2, true);
        }

        // diagonal up 1 / left 1

        if (xTile >= 1 && yTile >= 1) {
            updateCurrentAttackChoicesGrid(xTile - 1, yTile - 1, true);
        }

        // diagonal up 1 / right 1

        if (xTile <= getMapTileWidth() - 2 && yTile >= 1) {
            updateCurrentAttackChoicesGrid(xTile + 1, yTile - 1, true);
        }

        // diagonal down 1 / left 1

        if (xTile >= 1 && yTile <= getMapTileHeight() - 2) {
            updateCurrentAttackChoicesGrid(xTile - 1, yTile + 1, true);
        }

        // diagonal down 1 / right 1

        if (xTile <= getMapTileWidth() - 2 && yTile <= getMapTileHeight() - 2) {
            updateCurrentAttackChoicesGrid(xTile + 1, yTile + 1, true);
        }

        // diagonal up 2 / left 1

        if (xTile >= 1 && yTile >= 2) {
            updateCurrentAttackChoicesGrid(xTile - 1, yTile - 2, true);
        }

        // diagonal up 1 / left 2

        if (xTile >= 2 && yTile >= 1) {
            updateCurrentAttackChoicesGrid(xTile - 2, yTile - 1, true);
        }

        // diagonal up 2 / right 1

        if (xTile <= getMapTileWidth() - 2 && yTile >= 2) {
            updateCurrentAttackChoicesGrid(xTile + 1, yTile - 2, true);
        }

        // diagonal up 1 / right 2

        if (xTile <= getMapTileWidth() - 3 && yTile >= 1) {
            updateCurrentAttackChoicesGrid(xTile + 2, yTile - 1, true);
        }
        // diagonal down 2 / left 1

        if (xTile >= 1 && yTile <= getMapTileHeight() - 3) {
            updateCurrentAttackChoicesGrid(xTile - 1, yTile + 2, true);
        }

        // diagonal down 1 / left 2

        if (xTile >= 2 && yTile <= getMapTileHeight() - 2) {
            updateCurrentAttackChoicesGrid(xTile - 2, yTile + 1, true);
        }

        // diagonal down 2 / right 1

        if (xTile <= getMapTileWidth() - 2 && yTile <= getMapTileHeight() - 3) {
            updateCurrentAttackChoicesGrid(xTile + 1, yTile + 2, true);
        }

        // diagonal down 1 / right 2

        if (xTile <= getMapTileWidth() - 3 && yTile <= getMapTileHeight() - 2) {
            updateCurrentAttackChoicesGrid(xTile + 2, yTile + 1, true);
        }

    } // END OF updateRangedAttackSquares METHOD

    void updateGameBoardSpecificSelectedMilitaryUnitVariables(boolean isMelee, boolean isRanged) {
        setAMeleeMilitaryUnitSelected(isMelee);    // SETTER USED
        setARangedMilitaryUnitSelected(isRanged);    // SETTER USED
        setSelectedMilitaryUnit();    // SETTER USED
    }

    /*
    Find out how defendable a location is.  This will be assigned to a military unit.
     */

    int defenseStarsAtXYTile(int xTile, int yTile) {

        TerrainTile terrain = getTerrainAtXYTile(xTile, yTile);
        BuildingTile building = getBuildingAtXYTile(xTile, yTile);
        int highestDefenseStars = 0;
        int terrainDefenseStars = 0;
        int buildingDefenseStars = 0;

        switch (terrain) {

            case MOUNTAIN:
                terrainDefenseStars = 4;
                break;
            case GRASS:
                terrainDefenseStars = 1;
                break;
            case ROAD_VERTICAL:
                terrainDefenseStars = 0;
                break;
            case ROAD_HORIZONTAL:
                terrainDefenseStars = 0;
                break;
            case ROAD_TURN_UL:
                terrainDefenseStars = 0;
                break;
            case ROAD_TURN_UR:
                terrainDefenseStars = 0;
                break;
            case ROAD_TURN_DL:
                terrainDefenseStars = 0;
                break;
            case ROAD_TURN_DR:
                terrainDefenseStars = 0;
                break;
            default:
                terrainDefenseStars = 0;
                break;
        }

        switch (building) {

            case NONE:
                buildingDefenseStars = 0;
                break;
            case GRAY_BASE:
                buildingDefenseStars = 3;
                break;
            case GRAY_CITY:
                buildingDefenseStars = 3;
                break;
            case RED_HQ:
                buildingDefenseStars = 4;
                break;
            case RED_BASE:
                buildingDefenseStars = 3;
                break;
            case RED_CITY:
                buildingDefenseStars = 3;
                break;
            case BLUE_HQ:
                buildingDefenseStars = 4;
                break;
            case BLUE_BASE:
                buildingDefenseStars = 3;
                break;
            case BLUE_CITY:
                buildingDefenseStars = 3;
                break;
        }

        highestDefenseStars = Math.max(terrainDefenseStars, buildingDefenseStars);

        return highestDefenseStars;
    }

    /*
    Went for counting buildings each turn rather than keeping a running total which would be more efficient
     */

    void giveNewTurnIncome() {
        int redDailyIncome = 0;
        int blueDailyIncome = 0;
        int newBankAmount = 0;

        BuildingTile[][] tempBuildingTilesGrid = cloneBuildingTilesGrid();

        for (BuildingTile[] row : tempBuildingTilesGrid) {
            for (int j = 0; j < row.length; j++) {
                switch(row[j]) {

                    case NONE:
                        break;
                    case GRAY_BASE:
                        break;
                    case GRAY_CITY:
                        break;
                    case RED_HQ:
                        redDailyIncome += 1000;
                        break;
                    case RED_BASE:
                        redDailyIncome += 1000;
                        break;
                    case RED_CITY:
                        redDailyIncome += 1000;
                        break;
                    case BLUE_HQ:
                        blueDailyIncome += 1000;
                        break;
                    case BLUE_BASE:
                        blueDailyIncome += 1000;
                        break;
                    case BLUE_CITY:
                        blueDailyIncome += 1000;
                        break;

                } // END OF SWITCH

            } // END OF INNER FOR LOOP

        } // END OF FOR LOOP

        switch (getTurnColor()) {
            case 'r':
                newBankAmount = Math.min(99900, getRedPlayerBank() + redDailyIncome);
                setRedPlayerBank(newBankAmount);
                break;
            case 'b':
                newBankAmount = Math.min(99900, getBluePlayerBank() + blueDailyIncome);
                setBluePlayerBank(newBankAmount);
                break;

        } // END OF SWITCH

    } // END OF giveNewTurnIncome METHOD

    void tryRepairingAllUnits() {


        MilitaryUnit currentMilitaryUnit = null;
        BuildingTile theBuildingTile;
        char buildingColor;

        // loop through all units; update selected to true or false.

        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = militaryUnitsIterator();
        while (tempMilitaryUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempMilitaryUnitsIterator.next();

            // continue to the next military unit if this one already has full health.  There is no point in repairing it.

            if (currentMilitaryUnit.getHealth() == 100) { continue; }

            // get the building tile at the location of this military unit

            theBuildingTile = getBuildingAtXYTile(currentMilitaryUnit.getXTile(), currentMilitaryUnit.getYTile());

            // get the color of this bulding tile

            buildingColor = getBuildingColor(theBuildingTile);

            // if the building tile is the right color; then try to repair it automatically if the funds allow.

            tryRepairingMilitaryUnit(buildingColor, currentMilitaryUnit); // two chances to repair it for 1/10 of the unit cost.
            tryRepairingMilitaryUnit(buildingColor, currentMilitaryUnit); // two chances to repair it for 1/10 of the unit cost.

        } // END OF WHILE LOOP

    }  // END OF initiateRepairs METHOD

    void tryRepairingMilitaryUnit(char buildingColor, MilitaryUnit currentMilitaryUnit) {

        // Check if 1) The building is the right color
        //          2) The unit is the right color
        //          3) The bank is high enough

        if (buildingColor == getTurnColor() &&
                currentMilitaryUnit.getColor() == getTurnColor() &&
                getRedPlayerBank() >= currentMilitaryUnit.getCost() / 10) {
            setRedPlayerBank(getRedPlayerBank() - (currentMilitaryUnit.getCost() / 10));
            currentMilitaryUnit.setHealth(currentMilitaryUnit.getHealth() + 10);
        }
    }


    char getBuildingColor(BuildingTile theBuildingtile) {

        switch(theBuildingtile) {

            case NONE:
                return '0';
            case GRAY_BASE:
                return 'g';
            case GRAY_CITY:
                return 'g';
            case RED_HQ:
                return 'r';
            case RED_BASE:
                return 'r';
            case RED_CITY:
                return 'r';
            case BLUE_HQ:
                return 'b';
            case BLUE_BASE:
                return 'b';
            case BLUE_CITY:
                return 'b';
            default:
                return '0';
        }

    } // END OF getBuildingColor METHOD

    void continueBuildingCapturesAndCheckForCompletion(char playerColor) {

        BuildingTile newBuilding = null;
        MilitaryUnit currentMilitaryUnit = null;

        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = militaryUnitsIterator();
        while (tempMilitaryUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempMilitaryUnitsIterator.next();

            BuildingTile buildingTile =  getBuildingAtXYTile(currentMilitaryUnit.getXTile(), currentMilitaryUnit.getYTile());

            // continue to next iteration if there is no building at this location

            if (buildingTile == BuildingTile.NONE) { continue; }

            // continue to next iteration if it is the own player's building

            if ((buildingTile == buildingTile.RED_BASE || buildingTile == buildingTile.RED_CITY || buildingTile == buildingTile.RED_HQ) &&
                    playerColor == 'r') {
                continue;
            }
            if ((buildingTile == buildingTile.BLUE_BASE || buildingTile == buildingTile.BLUE_CITY || buildingTile == buildingTile.BLUE_HQ) &&
                    playerColor == 'b') {
                continue;
            }

            // Check if 1) The unit is the right color that is supposed to be capturing
            //          2) The unit is either an INFANTRY or a MECH
            // otherwise skip this unit

            if (currentMilitaryUnit.getColor() == playerColor && // #1
                    (currentMilitaryUnit.getMilitaryUnitType() == MilitaryUnitType.INFANTRY || currentMilitaryUnit.getMilitaryUnitType() == MilitaryUnitType.MECH)) { // #2
                currentMilitaryUnit.setCaptureProgress(currentMilitaryUnit.getCaptureProgress() + currentMilitaryUnit.getDisplayAndCaptureHealth());

                // check if the unit has completed the capture.  If so do state updates.

                if (currentMilitaryUnit.captureProgress > 20) {

                    // find what the new building should be

                    newBuilding = findCapturedBuildingNewBuildingType(buildingTile, playerColor);

                    updateBuildingTilesGrid(currentMilitaryUnit.getXTile(), currentMilitaryUnit.getYTile(), newBuilding);

                    currentMilitaryUnit.setCaptureProgress(0);

                    // check if the HQ was captured, if so end the game

                    if (newBuilding == BuildingTile.BLUE_HQ || newBuilding == BuildingTile.RED_HQ) {
                        new GameOver();
                    }

                } // END OF INNER IF

            } // END OF IF

        } // END OF WHILE

    } // END OF continueBuildingCapturesAndCheckForCompletion METHOD

    BuildingTile findCapturedBuildingNewBuildingType(BuildingTile buildingTile, char playerColor) {

        BuildingTile newBuildingTile = null;

        if (playerColor == 'r') {

            switch (buildingTile) {

                case GRAY_BASE:
                    newBuildingTile = BuildingTile.RED_BASE;
                    break;
                case GRAY_CITY:
                    newBuildingTile = BuildingTile.RED_CITY;
                    break;
                case BLUE_HQ:
                    newBuildingTile = BuildingTile.RED_HQ;
                    break;
                case BLUE_BASE:
                    newBuildingTile = BuildingTile.RED_BASE;
                    break;
                case BLUE_CITY:
                    newBuildingTile = BuildingTile.RED_CITY;
                    break;
            }

        } else if (playerColor == 'b') {

            switch (buildingTile) {

                case GRAY_BASE:
                    newBuildingTile = BuildingTile.BLUE_BASE;
                    break;
                case GRAY_CITY:
                    newBuildingTile = BuildingTile.BLUE_CITY;
                    break;
                case RED_HQ:
                    newBuildingTile = BuildingTile.BLUE_HQ;
                    break;
                case RED_BASE:
                    newBuildingTile = BuildingTile.BLUE_BASE;
                    break;
                case RED_CITY:
                    newBuildingTile = BuildingTile.BLUE_CITY;
                    break;
            }

        }

        return newBuildingTile;

    } // END OF findCapturedBuildingNewBuildingType METHOD

} // END OF GameBoard CLASS

class GameDrawingPanel extends JPanel {

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

    }

    // METHODS

    public void paint(Graphics g) {

        Graphics2D graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.WHITE);
        graphicSettings.fillRect(0,0, gameBoard.getjFrameWidth(), gameBoard.getjFrameHeight());

        graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /// draw terrain, then buildings, then military units.  See if they can stack!

        // draw terrain

        drawTerrain(g);

        // draw buildings

        drawBuildings(g);

        // draw menu

        drawMenu(g);

        // draw units

        drawMilitaryUnits(g);

        // draw cursor

        drawCursor(g);

        // draw currently movable locations or attack locations.  Decided to remove the red color possibility if an attack happened this turn.

        if (gameBoard.getSelectedMilitaryUnit() != null &&
                !gameBoard.isPressedTheAKeyWhileUnitSelected()) {
            if (!gameBoard.getSelectedMilitaryUnit().isMovedThisTurn()) {  // if military unit has not moved this turn (null checked above)
                drawMoveableGrid(graphicSettings);
            }
        } else if (gameBoard.getSelectedMilitaryUnit() != null &&
                gameBoard.isPressedTheAKeyWhileUnitSelected()) {
            if (!gameBoard.getSelectedMilitaryUnit().isAttackedThisTurn()) {  // if military unit has not attacked this turn (null checked above)
                drawAttackGrid(graphicSettings);
            }
        }



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

    void drawMenu(Graphics g) {

        gameBoard.getResizedRedInfantry().paintIcon(this, g, 0, 150);
        gameBoard.getResizedRedMech().paintIcon(this, g, 0, 225);
        gameBoard.getResizedRedArtillery().paintIcon(this, g, 0, 300);
        gameBoard.getResizedRedTank().paintIcon(this, g, 0, 375);
        gameBoard.getResizedBlueInfantry().paintIcon(this, g, 0, 450);
        gameBoard.getResizedBlueMech().paintIcon(this, g, 0, 525);
        gameBoard.getResizedBlueArtillery().paintIcon(this, g, 0, 600);
        gameBoard.getResizedBlueTank().paintIcon(this, g, 0, 675);

        // redraw the title

        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.setColor(Color.BLACK);
        g.drawString("Advance Wars",70,40);

        // create a rectangle based on the player turn

        if (gameBoard.getTurnColor() == 'r') {
            g.setColor(Color.RED);
            g.fillRect(20, 85, 100, 20);
        }

        if (gameBoard.getTurnColor() == 'b') {
            g.setColor(Color.BLUE);
            g.fillRect(170, 85, 100, 20);
        }

        // update bank numbers

        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(gameBoard.getRedPlayerBank()), 40, 105);
        g.drawString(Integer.toString(gameBoard.getBluePlayerBank()), 190, 105);

        // bank numbers title

        g.setColor(Color.BLACK);
        g.drawString("Red Bank:", 20, 75);
        g.drawString("Blue Bank:", 170, 75);

        // draw the end turn button

        gameBoard.getResizedGreenEndTurnButton().paintIcon(this, g, gameBoard.getEndTurnBtnStartX(), gameBoard.getEndTurnBtnStartY());
        g.drawString("End Turn",gameBoard.getEndTurnBtnStartX() + (gameBoard.getEndTurnBtnWidth() / 2) - (int) (0.75 * gameBoard.getTileLength()),
                gameBoard.getEndTurnBtnStartY() + (int) (0.35 * gameBoard.getTileLength()));

        // update the military unit labels
        gameBoard.getResizedRedButton().paintIcon(this, g, gameBoard.getPurchaseBtnsStartX(),
                gameBoard.getPurchaseBtnsStartY());
        g.drawString("1000 G", 110, 200);
        gameBoard.getResizedRedButton().paintIcon(this, g, gameBoard.getPurchaseBtnsStartX(),
                gameBoard.getPurchaseBtnsStartY() + 1 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()));
        g.drawString("3000 G", 110, 275);
        gameBoard.getResizedRedButton().paintIcon(this, g, gameBoard.getPurchaseBtnsStartX(),
                gameBoard.getPurchaseBtnsStartY() + 2 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()));
        g.drawString("6000 G", 110, 350);
        gameBoard.getResizedRedButton().paintIcon(this, g, gameBoard.getPurchaseBtnsStartX(),
                gameBoard.getPurchaseBtnsStartY() + 3 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()));
        g.drawString("7000 G", 110, 425);
        gameBoard.getResizedBlueButton().paintIcon(this, g, gameBoard.getPurchaseBtnsStartX(),
                gameBoard.getPurchaseBtnsStartY() + 4 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()));
        g.drawString("1000 G", 110, 500);
        gameBoard.getResizedBlueButton().paintIcon(this, g, gameBoard.getPurchaseBtnsStartX(),
                gameBoard.getPurchaseBtnsStartY() + 5 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()));
        g.drawString("3000 G", 110, 575);
        gameBoard.getResizedBlueButton().paintIcon(this, g, gameBoard.getPurchaseBtnsStartX(),
                gameBoard.getPurchaseBtnsStartY() + 6 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()));
        g.drawString("6000 G", 110, 650);
        gameBoard.getResizedBlueButton().paintIcon(this, g, gameBoard.getPurchaseBtnsStartX(),
                gameBoard.getPurchaseBtnsStartY() + 7 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()));
        g.drawString("7000 G", 110, 725);
    }

    /*
      Draw the military units to the screen
     */

    void drawMilitaryUnits(Graphics g) {
        MilitaryUnit currentMilitaryUnit = null;
        int drawXPosition = 0;
        int drawYPosition = 0;
        final int drawStringAdjustmentX = (int) (0.80 * gameBoard.getTileLength());
        final int drawStringAdjustmentY = gameBoard.getTileLength();
        int currentDisplayAndCaptureHealth;

        // loop through all units; update selected to true or false.

        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = gameBoard.militaryUnitsIterator();
        while (tempMilitaryUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempMilitaryUnitsIterator.next();

            // update drawXPosition, drawYPosition, and currentDisplayAndCaptureHealth for use further below

            drawXPosition = gameBoard.getMapStartX() + (gameBoard.getTileLength() * currentMilitaryUnit.getXTile());
            drawYPosition = gameBoard.getMapStartY() + (gameBoard.getTileLength() * currentMilitaryUnit.getYTile());
            currentDisplayAndCaptureHealth = currentMilitaryUnit.getDisplayAndCaptureHealth();

            // if a red unit, make a red unit

            if (currentMilitaryUnit.getColor() == 'r') {
                switch (currentMilitaryUnit.getMilitaryUnitType()) {

                    case INFANTRY:
                        gameBoard.getResizedRedInfantry().paintIcon(this, g, drawXPosition, drawYPosition);
                        break;
                    case MECH:
                        gameBoard.getResizedRedMech().paintIcon(this, g, drawXPosition, drawYPosition);
                        break;
                    case ARTILLERY:
                        gameBoard.getResizedRedArtillery().paintIcon(this, g, drawXPosition, drawYPosition);
                        break;
                    case TANK:
                        gameBoard.getResizedRedTank().paintIcon(this, g, drawXPosition, drawYPosition);
                        break;

                } // END OF SWITCH

            } else {

                // if blue unit, make a blue unit
                switch (currentMilitaryUnit.getMilitaryUnitType()) {

                    case INFANTRY:
                        gameBoard.getResizedBlueInfantry().paintIcon(this, g, drawXPosition, drawYPosition);
                        break;
                    case MECH:
                        gameBoard.getResizedBlueMech().paintIcon(this, g, drawXPosition, drawYPosition);
                        break;
                    case ARTILLERY:
                        gameBoard.getResizedBlueArtillery().paintIcon(this, g, drawXPosition, drawYPosition);
                        break;
                    case TANK:
                        gameBoard.getResizedBlueTank().paintIcon(this, g, drawXPosition, drawYPosition);
                        break;

                } // END OF SWITCH

            } // END OF ELSE

            // Draw health if under 10 displayAndCaptureHealth
            if (currentDisplayAndCaptureHealth < 10) {
                g.setColor(Color.BLACK);
                g.fillRect(drawXPosition + gameBoard.getTileLength() - 20, drawYPosition + gameBoard.getTileLength() - 20, 20, 20);
                g.setColor(Color.RED);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                g.drawString(Integer.toString(currentDisplayAndCaptureHealth),
                        drawXPosition + drawStringAdjustmentX,
                        drawYPosition + drawStringAdjustmentY);
            }

        } // END OF WHLIE LOOP

    }

    /*
    Draw the cursor in its currently map location.  The cursor never leaves the map
     */

    void drawCursor(Graphics g) {
        int xTile = gameBoard.getCursorMapTileX();
        int yTile = gameBoard.getCursorMapTileY();

        gameBoard.getResizedCursor().paintIcon(this, g,
                gameBoard.getMapStartX() + gameBoard.getTileLength() * xTile,
                gameBoard.getMapStartY() + gameBoard.getTileLength() * yTile);
    }


    /*
    Draw the moveable grid;  There is a condition prior that only draws if a unit is selected
     */

    void drawMoveableGrid(Graphics2D graphicSettings) {
        boolean[][] tempCurrentMoveableChoices = gameBoard.cloneCurrentMoveableChoicesGrid();

        graphicSettings.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f));
        graphicSettings.setPaint(Color.BLACK);

        for (boolean[] row : tempCurrentMoveableChoices) {
            for (int j = 0; j < row.length; j++) {
                if (row[j]) {

                    // draw a transparent rectangle

                    graphicSettings.fill(new Rectangle(drawingTopLeftXPos, drawingTopLeftYPos, gameBoard.getTileLength(),
                            gameBoard.getTileLength()));
                }

                updateXYPositionForMapDrawing();
            }

        } // END OF FOR LOOP

    } // END OF drawMoveableGrid METHOD

    /*
    Draw the moveable grid;  There is a condition prior that only draws if a unit is selected
     */

    void drawAttackGrid(Graphics2D graphicSettings) {
        boolean[][] tempCurrentAttackChoices = gameBoard.cloneCurrentAttackChoicesGrid();

        graphicSettings.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f));
        graphicSettings.setPaint(Color.RED);

        for (boolean[] row : tempCurrentAttackChoices) {
            for (int j = 0; j < row.length; j++) {
                if (row[j]) {

                    // draw a transparent rectangle

                    graphicSettings.fill(new Rectangle(drawingTopLeftXPos, drawingTopLeftYPos, gameBoard.getTileLength(),
                            gameBoard.getTileLength()));
                }

                updateXYPositionForMapDrawing();
            }

        } // END OF FOR LOOP

    } // END OF drawAttackGrid METHOD


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

