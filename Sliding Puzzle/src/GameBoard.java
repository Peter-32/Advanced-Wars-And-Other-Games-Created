import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by peterjmyers on 8/11/16.
 */
public class GameBoard extends JFrame {


    //// FIELDS

    private int width = 800;
    private int height = 600;
    private int titleBarHeight;
    private int leftFrameBorderWidth;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private int tileSideMargin = 5;
    private int boxStartX = 225;
    private int boxStartY = 125;
    private int tileSideLength = 75;
    private int boxSideLength = (4 * tileSideLength) + (5 * tileSideMargin);

    // sound files

    private String backgroundMusic = "file:./resources/Puzzle Theme1.wav";
    private Clip clip = null;

    // use this to lock for write operations like add/remove

    private final Lock readLock;

    // use this to lock for read operations like get/iterator/contains..

    private final Lock writeLock;

    //// CONSTRUCTOR

    GameBoard() {
        // initialize JFrame

        this.setSize(width, height);
        this.setTitle("Sliding Puzzle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleBarHeight = 30;
        leftFrameBorderWidth = 8;

        // locks

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();

        // Start the background music

        playMusic(backgroundMusic, true);

        // Create all 15 tiles in random locations
        populateTilesArray();

        // Start the key/mouse listener class
        // his prompts the main game loop each time there is user input

        ListenForButton lForButton = new ListenForButton();
        ListenForMouse lForMouse = new ListenForMouse();
        addKeyListener(lForButton);
        addMouseListener(lForMouse);

        // start the GameDrawingPanel

        GameDrawingPanel gameDrawingPanel = new GameDrawingPanel(this);
        this.add(gameDrawingPanel, BorderLayout.CENTER);

        // Add multi-threading for the game loop
        ScheduledThreadPoolExecutor executor = new  ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(new MainGameLoop(this), 0L, 20L, TimeUnit.MILLISECONDS);


        // final changes to JFrame

        setResizable(false);

        this.setVisible(true);

    }

    // GETTERS AND SETTERS

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLeftFrameBorderWidth() {
        return leftFrameBorderWidth;
    }
    public int getTitleBarHeight() {
        return titleBarHeight;
    }
    public int getTileSideMargin() {
        return tileSideMargin;
    }
    public int getBoxStartX() {
        return boxStartX;
    }

    public int getBoxStartY() {
        return boxStartY;
    }
    public int getTileSideLength() {
        return tileSideLength;
    }
    public int getBoxSideLength() {
        return boxSideLength;
    }
    public Object[] cloneTiles() {
        return tiles.toArray();
    }
    public Iterator<Tile> tileIterator() {
        readLock.lock();
        try {
            return new ArrayList<Tile>(tiles).iterator();
            // we iterate over a snapshot of our list
        } finally {
            readLock.unlock();
        }
    }

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

    /*
    Places a randomized x, y location for the tiles numbered 1 to 15.
    x can be any number between 0 and 3 and y can be any number between 0 and 3.
    No tile can take the same location twice.
    Loops through the 15 locations and creates a tile with a random number from 1 to 15.
     */
    private void populateTilesArray() {

        // shuffle the ordering of the numbers that will be on the tiles

        int[] randNumberArray = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Collections.shuffle(Arrays.asList(randNumberArray));

        // create the tiles and use the random number array

        for(int i = 0; i < 15; i++) {
            tiles.add(new Tile(this, i%4, i/4, randNumberArray[i]));
        }
    }

    private class ListenForButton implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class ListenForMouse implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(e.getButton());
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
    private GameBoard gameBoard;

    private Iterator<Tile> tempTileIterator;
    private Tile currentTile;



    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;


    }

    // when repainting add animation so that the tiles slide rather than swap locations

    public void paint(Graphics g) {

        // draw the border

        Graphics2D graphicSettings = (Graphics2D) g;

        graphicSettings.setColor(Color.CYAN);
        graphicSettings.fillRect(0, 0, gameBoard.getWidth(), gameBoard.getHeight());

        graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw border

        graphicSettings.setStroke(new BasicStroke(5));
        graphicSettings.setColor(Color.BLUE);
        graphicSettings.drawRect(gameBoard.getBoxStartX(), gameBoard.getBoxStartY(), gameBoard.getBoxSideLength(),
                gameBoard.getBoxSideLength());

        // Get iterator

        tempTileIterator = gameBoard.tileIterator();

        // start iterating

        while (tempTileIterator.hasNext()) {

            currentTile = tempTileIterator.next();

            // draw green tile each iteration

            graphicSettings.setColor(Color.GREEN);
            graphicSettings.fillRect(currentTile.getTopLeftXPos(), currentTile.getTopLeftYPos(), gameBoard.getTileSideLength(),
                    gameBoard.getTileSideLength());

            // draw white text

            graphicSettings.setColor(Color.WHITE);
            // ???

        } // END OF WHILE LOOP

    } // END OF Paint() METHOD

} // END OF GameDrawingPanel

// This loop only is run when the InputListener tells it to run.

class MainGameLoop implements Runnable {

    //// FIELDS
    private GameBoard gameBoard;

    //// CONSTRUCTOR

    MainGameLoop(GameBoard gameBoard) {

        this.gameBoard = gameBoard;

    }

    @Override
    public void run() {
        // move based on user input


        // repaint using animation
        gameBoard.repaint();
    }
}


class Tile {

    //// FIELDS
    private GameBoard gameBoard;
    private int row;
    private int col;
    private int topLeftXPos;
    private int topLeftYPos;
    private int numberOnTile;


    //// CONSTRUCTOR
    Tile(GameBoard gameBoard, int row, int col, int numberOnTile) {
        this.row = row;
        this.col = col;
        this.numberOnTile = numberOnTile;

        // Initialize the X, Y position on the screen at start up

        this.topLeftXPos = gameBoard.getBoxStartX() + ((col + 1) * gameBoard.getTileSideMargin()) +
                (col * gameBoard.getTileSideLength());
        this.topLeftYPos = gameBoard.getBoxStartY() + ((row + 1) * gameBoard.getTileSideMargin()) +
                (row * gameBoard.getTileSideLength());
    }

    //// GETTERS AND SETTERS

    public int getNumberOnTile() {
        return numberOnTile;
    }

    public int getTopLeftYPos() {
        return topLeftYPos;
    }

    public int getTopLeftXPos() {
        return topLeftXPos;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    ////METHODS

    // constrained movement changes X and Y location

    void move() {
        // If colliding with wall correct; USE tile margin for any corrections

        // move; if clicked on move at a certain speed in the right direction until colliding
        // if keyboard is used move in the right direction until colliding


        // If colliding with tile correct;  USE tile Margin for any corrections



    }


}