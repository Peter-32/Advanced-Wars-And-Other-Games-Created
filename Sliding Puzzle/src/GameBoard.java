import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
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
    private boolean lockInput = false; // this isn't a feature that is used.  It can be used to animate the sliding.
    private int yClicked = -1;
    private int xClicked = -1;
    private boolean keyUpPressed = false;
    private boolean keyRightPressed = false;
    private boolean keyDownPressed = false;
    private boolean keyLeftPressed = false;
    private int rowOfBlankTile = 3;
    private int colOfBlankTile = 3;
    private boolean GameOver = false;
    private boolean autuSolve = false;
    private int computerLevel;

    // sound files

    private String backgroundMusic = "file:./resources/Puzzle Theme1.wav";
    private Clip clip = null;

    // use this to lock for write operations like add/remove

    private final Lock readLock;

    // use this to lock for read operations like get/iterator/contains..

    private final Lock writeLock;

    //// CONSTRUCTOR

    GameBoard(Boolean autoSolve, int computerLevel) {
        // initialize JFrame

        this.autuSolve = autoSolve;
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

        // final changes to JFrame

        setResizable(false);

        this.setVisible(true);

        // Add multi-threading for the game loop
        if (!autoSolve) {
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
            executor.scheduleAtFixedRate(new MainGameLoop(this), 0L, 20L, TimeUnit.MILLISECONDS);
        } else {

            new AutoSolve(this, computerLevel);

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

        // create the random number array.  In this case it is always the same order
        String[] randNumberArray = {"05","08","15","09","01","14","06","03","12","11","02","13","04","10","07"};

        /* BEWARE Shuffling will create invalid puzzles.  Not sure of how to scramble it easily at the moment
        //String[] randNumberArray = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15"};
        //Collections.shuffle(Arrays.asList(randNumberArray));
        */

        // create the tiles and use the random number array

        for(int i = 0; i < 15; i++) {
            tiles.add(new Tile(this, i/4, i%4, randNumberArray[i]));
        }
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
    public int getXClicked() {
        return xClicked;
    }
    public int getYClicked() {
        return yClicked;
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


    public int getRowOfBlankTile() {
        return rowOfBlankTile;
    }
    public void setRowOfBlankTile(int rowOfBlankTile) {
        this.rowOfBlankTile = rowOfBlankTile;
    }

    public int getColOfBlankTile() {
        return colOfBlankTile;
    }
    public void setColOfBlankTile(int colOfBlankTile) {
        this.colOfBlankTile = colOfBlankTile;
    }

    public boolean isKeyUpPressed() {
        return keyUpPressed;
    }

    public void setKeyUpPressed(boolean keyUpPressed) {
        this.keyUpPressed = keyUpPressed;
    }

    public boolean isKeyRightPressed() {
        return keyRightPressed;
    }

    public void setKeyRightPressed(boolean keyRightPressed) {
        this.keyRightPressed = keyRightPressed;
    }

    public boolean isKeyDownPressed() {
        return keyDownPressed;
    }

    public void setKeyDownPressed(boolean keyDownPressed) {
        this.keyDownPressed = keyDownPressed;
    }

    public boolean isKeyLeftPressed() {
        return keyLeftPressed;
    }

    public void setKeyLeftPressed(boolean keyLeftPressed) {
        this.keyLeftPressed = keyLeftPressed;
    }

    public boolean isGameOver() {
        return GameOver;
    }

    public void setGameOver(boolean gameOver) {
        GameOver = gameOver;
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
            if (e.getKeyCode() == 87 || e.getKeyCode() == 38) {
                keyUpPressed = true;
            } else if (e.getKeyCode() == 68 || e.getKeyCode() == 39) {
                keyRightPressed = true;
            } else if (e.getKeyCode() == 83 || e.getKeyCode() == 40) {
                keyDownPressed = true;
            } else if (e.getKeyCode() == 65 || e.getKeyCode() == 37) {
                keyLeftPressed = true;
            }
        }
    }

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

        graphicSettings.setStroke(new BasicStroke(gameBoard.getTileSideMargin()));
        graphicSettings.setColor(Color.BLUE);
        graphicSettings.drawRect(gameBoard.getBoxStartX(), gameBoard.getBoxStartY(), gameBoard.getBoxSideLength(),
                gameBoard.getBoxSideLength());

        // Prepare for iterating on the tile array

        tempTileIterator = gameBoard.tileIterator();
        graphicSettings.setStroke(new BasicStroke(gameBoard.getTileSideMargin() - 1));

        // start iterating

        while (tempTileIterator.hasNext()) {

            currentTile = tempTileIterator.next();

            // get the recontangle

            Shape newRectangle = new Rectangle2D.Float(currentTile.getTopLeftXPos(), currentTile.getTopLeftYPos(), gameBoard.getTileSideLength(),
                    gameBoard.getTileSideLength());

            // draw the border

            graphicSettings.setPaint(Color.BLACK);
            graphicSettings.draw(newRectangle);

            // fill in the rectangle

            graphicSettings.setPaint(Color.GREEN);
            graphicSettings.fill(newRectangle);

            /*
            graphicSettings.fillRect(currentTile.getTopLeftXPos(), currentTile.getTopLeftYPos(), gameBoard.getTileSideLength(),
                    gameBoard.getTileSideLength());
            */
            // draw white text

            graphicSettings.setFont(new Font("TimesRoman", Font.PLAIN, 25));
            graphicSettings.setColor(Color.WHITE);
            graphicSettings.drawString((String) currentTile.getNumberOnTile(),
                    (int) (currentTile.getTopLeftXPos() + gameBoard.getTileSideLength() * .30),
                    (int) (currentTile.getTopLeftYPos() + gameBoard.getTileSideLength() * .60));
            // ???

        } // END OF WHILE LOOP

    } // END OF Paint() METHOD

} // END OF GameDrawingPanel

// This loop only is run when the InputListener tells it to run.

class MainGameLoop implements Runnable {

    //// FIELDS
    private GameBoard gameBoard;
    private Iterator<Tile> tempTileIterator;
    private boolean boolResult;

    //// CONSTRUCTOR

    MainGameLoop(GameBoard gameBoard) {

        this.gameBoard = gameBoard;

    }

    @Override
    public void run() {
        // Check to see if the game is won
        if (didPlayerWin()) {

            // Check if we've already found it to be gave over

            if (!gameBoard.isGameOver()) {
                new GameOver(gameBoard);
            }
        }

        // move based on user input
        moveTiles();

        // repaint using animation
        gameBoard.repaint();
    }

    /*
    This method will move the tiles if the user has used inputs (asdw keys, arrow keys, or mouse)
    After checking and responding with a possible movement, the inputs are reset to false, xClick = -1, and yClick = -1
     */
    private void moveTiles() {

        // check for any user input; if two keys are pressed at once it will prioritize certain keys over others

        if (gameBoard.isKeyDownPressed() || gameBoard.isKeyLeftPressed() || gameBoard.isKeyRightPressed() ||
                gameBoard.isKeyUpPressed() || gameBoard.getXClicked() != -1 || gameBoard.getYClicked() != -1) {

            // find the row / column we're looking for based on the input used and the missing tile location
            if (gameBoard.isKeyDownPressed()) {
                gameBoard.setKeyDownPressed(false);
                tryMovingDown();
            } else if (gameBoard.isKeyUpPressed()) {
                gameBoard.setKeyUpPressed(false);
                tryMovingUp();
            } else if (gameBoard.isKeyLeftPressed()) {
                gameBoard.setKeyLeftPressed(false);
                tryMovingLeft();
            } else if (gameBoard.isKeyRightPressed()) {
                gameBoard.setKeyRightPressed(false);
                tryMovingRight();
            }

        }

    } // END OF moveTiles METHOD

    public void tryMovingRight() {
        int searchTileWithRow = gameBoard.getRowOfBlankTile();
        int searchTileWithCol = gameBoard.getColOfBlankTile() - 1;

        if (searchTileWithCol < 0) { return; } // right isn't possible

        searchForTileAndMoveIt(searchTileWithRow, searchTileWithCol);

    } // END OF tryMovingRight Method

    public void tryMovingLeft() {

        int searchTileWithRow = gameBoard.getRowOfBlankTile();
        int searchTileWithCol = gameBoard.getColOfBlankTile() + 1;

        if (searchTileWithCol > 3) { return; } // right isn't possible

        searchForTileAndMoveIt(searchTileWithRow, searchTileWithCol);

    } // END OF tryMovingLeft Method

    public void tryMovingDown() {
        int searchTileWithRow = gameBoard.getRowOfBlankTile() - 1;
        int searchTileWithCol = gameBoard.getColOfBlankTile();

        if (searchTileWithRow < 0) { return; } // right isn't possible

        searchForTileAndMoveIt(searchTileWithRow, searchTileWithCol);

    } // END OF tryMovingDown Method

    public void tryMovingUp() {
        int searchTileWithRow = gameBoard.getRowOfBlankTile() + 1;
        int searchTileWithCol = gameBoard.getColOfBlankTile();

        if (searchTileWithRow > 3) { return; } // right isn't possible

        searchForTileAndMoveIt(searchTileWithRow, searchTileWithCol);

    } // END OF tryMovingUp Method

    /*
    This method will find the tile in a specific row and column and call the Tile.move() method
     */

    private void searchForTileAndMoveIt(int searchTileWithRow, int searchTileWithCol) {
        Tile currentTile;
        tempTileIterator = gameBoard.tileIterator();

        // start iterating

        while (tempTileIterator.hasNext()) {

            currentTile = tempTileIterator.next();

            // Search for the tile that can move into the blank tile area.  Once found call move.

            if (currentTile.getRow() == searchTileWithRow && currentTile.getCol() == searchTileWithCol) {
                currentTile.move();
            }
        }

    } // END OF searchForTileAndMoveIt METHOD

    private boolean didPlayerWin() {
        Tile currentTile;

        tempTileIterator = gameBoard.tileIterator();

        // start iterating

        while (tempTileIterator.hasNext()) {

            currentTile = tempTileIterator.next();
            if (currentTile.getRow() == 0) {
                switch (currentTile.getCol()) {
                    case 0:
                        boolResult = currentTile.getNumberOnTile().equals("01");
                        break;
                    case 1:
                        boolResult = currentTile.getNumberOnTile().equals("02");
                        break;
                    case 2:
                        boolResult = currentTile.getNumberOnTile().equals("03");
                        break;
                    default:
                        boolResult = currentTile.getNumberOnTile().equals("04");
                        break;
                }
            } else if (currentTile.getRow() == 1) {
                switch (currentTile.getCol()) {
                    case 0:
                        boolResult = currentTile.getNumberOnTile().equals("05");
                        break;
                    case 1:
                        boolResult = currentTile.getNumberOnTile().equals("06");
                        break;
                    case 2:
                        boolResult = currentTile.getNumberOnTile().equals("07");
                        break;
                    default:
                        boolResult = currentTile.getNumberOnTile().equals("08");
                        break;
                }
            } else if (currentTile.getRow() == 2) {
                switch (currentTile.getCol()) {
                    case 0:
                        boolResult = currentTile.getNumberOnTile().equals("09");
                        break;
                    case 1:
                        boolResult = currentTile.getNumberOnTile().equals("10");
                        break;
                    case 2:
                        boolResult = currentTile.getNumberOnTile().equals("11");
                        break;
                    default:
                        boolResult = currentTile.getNumberOnTile().equals("12");
                        break;
                }
            } else {
                switch (currentTile.getCol()) {
                    case 0:
                        boolResult = currentTile.getNumberOnTile().equals("13");
                        break;
                    case 1:
                        boolResult = currentTile.getNumberOnTile().equals("14");
                        break;
                    case 2:
                        boolResult = currentTile.getNumberOnTile().equals("15");
                        break;
                    default:
                        boolResult = false;
                        break;
                }
            }


            if (!boolResult) {
                return false;
            }

        }
        // no contradiction so it's a winning puzzle state

        return true;
    } // END OF didPlayerWin METHOD

} // END OF MainGameLoop CLASS


class Tile {

    //// FIELDS
    private GameBoard gameBoard;
    private int row;
    private int col;
    private int topLeftXPos;
    private int topLeftYPos;
    private String numberOnTile;


    //// CONSTRUCTOR
    Tile(GameBoard gameBoard, int row, int col, String numberOnTile) {
        this.gameBoard = gameBoard;
        this.row = row;
        this.col = col;
        this.numberOnTile = numberOnTile;

        // Initialize the X, Y position on the screen at start up

        updateXYPos(row, col);

    }

    //// GETTERS AND SETTERS

    public String getNumberOnTile() {
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

    /*
     constrained movement changes X and Y location
    */

    void move() {
        // OLD: // move slowly in the direction towards the blank tile

        // swap
        // save temps

        int tempRow = gameBoard.getRowOfBlankTile();
        int tempCol = gameBoard.getColOfBlankTile();

        // update gameBoard

        gameBoard.setRowOfBlankTile(row);
        gameBoard.setColOfBlankTile(col);

        // swap temps into this row and col

        row = tempRow;
        col = tempCol;

        //update the X and Y similar to the constructor above

        updateXYPos(row, col);

    }

    void updateXYPos(int row, int col) {
        this.topLeftXPos = gameBoard.getBoxStartX() + ((col + 1) * gameBoard.getTileSideMargin()) +
                (col * gameBoard.getTileSideLength());
        this.topLeftYPos = gameBoard.getBoxStartY() + ((row + 1) * gameBoard.getTileSideMargin()) +
                (row * gameBoard.getTileSideLength());
    }

}

class GameOver {
    GameOver(GameBoard gameBoard) {
        //clapping sound

        //message displayed
        gameBoard.setGameOver(true);
        JOptionPane.showMessageDialog(gameBoard, "Game Over!");

        //create new GameBoard

        //new GameBoard(gameBoard.getNumberOfPairsOfPatterns());

    }
}

