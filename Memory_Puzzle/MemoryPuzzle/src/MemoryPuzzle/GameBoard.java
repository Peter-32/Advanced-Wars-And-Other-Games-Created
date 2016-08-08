package MemoryPuzzle;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
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
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Peter on 8/7/2016.
 */
public class GameBoard extends JFrame {

    //// FIELDS

    private int width = 1200;
    private int height = 900;
    private int numCardsInRow;
    private int numCardsInCol;
    private int northAndSouthMargin = 200;
    private int eastAndWestMargin = 200;
    private int cardWidth = 30;
    private int cardHeight = 30;
    private int cardsHorizontalMargin = 5;
    private int cardsVerticalMargin = 5;
    private int turnNumber = 0;
    private int numberOfPairsOfPatterns;
    private int numberOfPatterns;
    private ArrayList<String> possibleColors = new ArrayList<String> (Arrays.asList("red","blue","green","dark_gray","pink","yellow","orange"));
    private ArrayList<String> possibleShapes = new ArrayList<String> (Arrays.asList("donut","lines","circle","square","triangle","pentagon","star"));
    private ArrayList<String> combinations = new ArrayList<String> ();

    // The indices of the arrayList "combinations" where the cards are revealed to the player

    private ArrayList<Boolean> cardsRevealed;

    // A player chooses two cards to see if they match, this is the idx of the first choice

    private int firstChoiceIdx = -1;

    // This is the idx of the second choice

    private int secondChoiceIdx = -1;

    // If one box is clicked on this should be true

    private boolean madeFirstChoice = false;

    // sound files

    private String backgroundMusic = "file:./resources/game_music.wav";
    private Clip clip = null;

    // use this to lock for write operations like add/remove

    private final Lock readLock;

    // use this to lock for read operations like get/iterator/contains..

    private final Lock writeLock;


    //// CONSTRUCTOR

    GameBoard(int numberOfPairsOfPatterns) {

        // initialize the JFrame

        this.setSize(width, height);
        this.setTitle("Memory Puzzle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // locks

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();

        // Start the background music

        playMusic(backgroundMusic, true);

        // Create the main drawing panel and place it in the center of the gameBoard JFrame

        GameDrawingPanel gameDrawingPanel = new GameDrawingPanel(this);
        this.add(gameDrawingPanel, BorderLayout.CENTER);

        // Set up some field values

        this.numberOfPairsOfPatterns = (numberOfPairsOfPatterns > 49) ? 49 : numberOfPairsOfPatterns;

        this.numberOfPairsOfPatterns = (this.numberOfPairsOfPatterns < 1) ? 1 : this.numberOfPairsOfPatterns;

        this.numberOfPatterns = numberOfPairsOfPatterns * 2;

        // populate the arraylists

        populateArrayLists();

        // click listener added

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

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
        });

        // threads added for things such as MainGameLoop
        ScheduledThreadPoolExecutor executor = new  ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(new MainGameLoop(this), 0L, 20L, TimeUnit.MILLISECONDS);

        // show the JFrame

        this.setVisible(true);

    } // END OF GameBoard CONSTRUCTOR

    //// GETTERS AND SETTERS

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getNumCardsInRow() {
        return numCardsInRow;
    }
    public int getNumCardsInCol() {
        return numCardsInCol;
    }
    public int getNorthAndSouthMargin() {
        return northAndSouthMargin;
    }
    public int getEastAndWestMargin() {
        return eastAndWestMargin;
    }
    public int getCardWidth() {
        return cardWidth;
    }
    public int getCardsHeight() {
        return cardHeight;
    }
    public int getCardsHorizontalMargin() {
        return cardsHorizontalMargin;
    }
    public int getCardsVerticalMargin() {
        return cardsVerticalMargin;
    }
    public int getTurnNumber() {
        return turnNumber;
    }
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
    public int getNumberOfPairsOfPatterns() {
        return numberOfPairsOfPatterns;
    }
    public int getNumberOfPatterns() {
        return numberOfPatterns;
    }
    public Iterator<String> combinationsIterator() {
        readLock.lock();
        try {
            return new ArrayList<String>(combinations).iterator();
            // we iterate over a snapshot of our list
        } finally {
            readLock.unlock();
        }
    }
    public Object[] cloneCombinations() {
        return combinations.toArray();
    }
    public Iterator<Boolean> cardsRevealedIterator() {
        readLock.lock();
        try {
            return new ArrayList<Boolean>(cardsRevealed).iterator();
            // we iterate over a snapshot of our list
        } finally {
            readLock.unlock();
        }
    }
    public void updateCardsRevealed(int index, boolean isRevealed) {
        writeLock.lock();
        try {
            cardsRevealed.set(index, isRevealed);
        } finally{
            writeLock.unlock();
        }
    }
    public int getFirstChoiceIdx() {
        return firstChoiceIdx;
    }
    public void setFirstChoiceIdx(int firstChoiceIdx) {
        this.firstChoiceIdx = firstChoiceIdx;
    }
    public int getSecondChoiceIdx() {
        return secondChoiceIdx;
    }
    public void setSecondChoiceIdx(int secondChoiceIdx) {
        this.secondChoiceIdx = secondChoiceIdx;
    }
    public boolean getMadeFirstChoice() {
        return madeFirstChoice;
    }
    public void setMadeFirstChoice(boolean madeFirstChoice) {
        this.madeFirstChoice = madeFirstChoice;
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
    }

    void populateArrayLists() {

        // populate the combinations

        for (String color : possibleColors) {
            for (String shape : possibleShapes) {
                combinations.add(color + "-" + shape);
            }
        }

        // do shuffling on combinations

        Collections.shuffle(combinations);

        // All cards are unrevealed at the start

        cardsRevealed = new ArrayList<Boolean> (Collections.nCopies(numberOfPatterns, false));

    }

}

class GameOver {
    GameOver() {
        //clapping sound
        //message displayed
        //create new GameBoard
    }
}

class GameDrawingPanel extends JComponent {

    //// FIELDS

    private GameBoard gameBoard;
    private Object[] combinations;
    private int cardWidth;
    private int cardHeight;
    private int idx = 0;
    private int xPos = 0;
    private int yPos = 0;
    private int xCutoffPoint = gameBoard.getWidth() - gameBoard.getEastAndWestMargin();
    private Iterator<Boolean> tempCardsRevealedIterator;
    String combinationsElement;
    String shape;
    String color;
    Graphics2D graphicSettings;

    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {

        this.gameBoard = gameBoard;
        this.xPos = gameBoard.getEastAndWestMargin();
        this.yPos = gameBoard.getNorthAndSouthMargin();
        cardWidth = this.gameBoard.getCardWidth();
        cardHeight = this.gameBoard.getCardsHeight();

    } // END OF GameDrawingPanel CONSTRUCTOR

    public void paint(Graphics g) {

        // Cloning each frame because the program is using threading.  Probably could do without threading.

        combinations = gameBoard.cloneCombinations();

        graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.GRAY);
        graphicSettings.fillRect(0,0, gameBoard.getWidth(), gameBoard.getHeight());


        tempCardsRevealedIterator = gameBoard.cardsRevealedIterator();
        while(tempCardsRevealedIterator.hasNext()) {
            if (tempCardsRevealedIterator.next()) {

                // find the shape and color
                combinationsElement = (String) combinations[idx];
                color = combinationsElement.split("-")[0];
                shape = combinationsElement.split("-")[1];

                // draw pattern

                drawColoredShape();

                // this method finds the next xPos and yPos

                updateXPosYPosForNextDrawing();

            } else {

                // draw card

                graphicSettings.setColor(Color.BLACK);
                graphicSettings.draw(new Rectangle2D.Double(xPos, yPos,cardWidth, cardHeight));
                //graphicSettings.fillRect(xPos,yPos, cardWidth, cardHeight);

                // this method finds the next xPos and yPos

                updateXPosYPosForNextDrawing();
            }

            idx++;
        } // END OF WHILE LOOP INSIDE paint() METHOD

        idx = 0;
    } // END OF paint() METHOD

    void drawColoredShape() {

        // change color

        switch(color)
        {
            case "red":
                graphicSettings.setColor(Color.RED);
                break;
            case "blue":
                graphicSettings.setColor(Color.BLUE);
                break;
            case "green":
                graphicSettings.setColor(Color.GREEN);
                break;
            case "dark_gray":
                graphicSettings.setColor(Color.DARK_GRAY);
                break;
            case "pink":
                graphicSettings.setColor(Color.PINK);
                break;
            case "yellow":
                graphicSettings.setColor(Color.YELLOW);
                break;
            case "orange":
                graphicSettings.setColor(Color.ORANGE);
                break;
        }

        // draw the shape

        switch(shape)
        {
            case "donut":
                graphicSettings.fill(new Ellipse2D.Double(xPos + cardWidth/6, yPos + cardHeight/6,cardWidth - cardWidth/3, cardHeight - cardHeight/3));
                graphicSettings.setColor(Color.GRAY);
                graphicSettings.fill(new Ellipse2D.Double(xPos + cardWidth/3, yPos + cardHeight/3,cardWidth - cardWidth*2/3, cardHeight - cardHeight*2/3));
                break;
            case "lines":
                graphicSettings.draw(new Line2D.Double(xPos + cardWidth*.25, yPos + cardHeight/6,xPos + cardWidth*.25, yPos + cardHeight*5/6));
                graphicSettings.draw(new Line2D.Double(xPos + cardWidth*.5, yPos + cardHeight/6,xPos + cardWidth*.50, yPos + cardHeight*5/6));
                graphicSettings.draw(new Line2D.Double(xPos + cardWidth*.75, yPos + cardHeight/6,xPos + cardWidth*.75, yPos + cardHeight*5/6));
                break;
            case "circle":
                graphicSettings.fill(new Ellipse2D.Double(xPos + cardWidth/6, yPos + cardHeight/6,cardWidth - cardWidth/3, cardHeight - cardHeight/3));
                break;
            case "square":
                graphicSettings.fill(new Rectangle2D.Double(xPos + cardWidth/6, yPos + cardHeight/6,cardWidth - cardWidth/3, cardHeight - cardHeight/3));
                break;
            case "triangle":
                int[] xArray = {xPos + cardWidth/2, xPos + cardWidth/6, xPos + cardWidth*5/6};
                int[] yArray = {yPos + cardHeight/6, yPos + cardHeight*5/6, yPos + cardHeight*5/6};
                Polygon p = new Polygon(xArray,yArray,3);
                graphicSettings.fillPolygon(p);
                break;
            case "pentagon":
                int[] xArray2 = {};/////////////////////////UNIMPLEMENTED
                int[] yArray2 = {};/////////////////////////UNIMPLEMENTED
                Polygon p2 = new Polygon(xArray2,yArray2,5);
                graphicSettings.fillPolygon(p2);
                break;
            case "star":
                int[] xArray3 = {};/////////////////////////UNIMPLEMENTED
                int[] yArray3 = {};/////////////////////////UNIMPLEMENTED
                Polygon p3 = new Polygon(xArray3,yArray3,3);
                graphicSettings.fillPolygon(p3);
                break;
        }

                /*
            private ArrayList<String> possibleColors = new ArrayList<String> (Arrays.asList("red","blue","green","dark_gray","purple","yellow","orange"));
            private ArrayList<String> possibleShapes = new ArrayList<String> (Arrays.asList("donut","line","circle","square","triangle","pentagon","star"));
         */
    } // END OF drawColoredShape() METHOD

    // Updates X and Y positions based on side margins.

    void updateXPosYPosForNextDrawing() {

        xPos += cardWidth + gameBoard.getCardsHorizontalMargin();

        // if we can fit another spot on this line, then we don't need a new line

        if (xPos + cardWidth + gameBoard.getCardsHorizontalMargin() < xCutoffPoint) {
            return;
        } else {
            xPos = gameBoard.getEastAndWestMargin();
            yPos += yPos + cardHeight + gameBoard.getCardsVerticalMargin();
        }
    }


} // END OF GameDrawingPanel CLASS



class MainGameLoop implements Runnable {

    GameBoard gameBoard;

    MainGameLoop(GameBoard gameBoard) {

        this.gameBoard = gameBoard;

    }

    @Override
    public void run() {

        // check input/output

        // repaint
        gameBoard.repaint();
    }

    /*
      ALL Input commands should be checked here
     */



}