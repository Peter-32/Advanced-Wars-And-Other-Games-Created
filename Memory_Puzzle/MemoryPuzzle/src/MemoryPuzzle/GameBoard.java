package MemoryPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    private int width;
    private int height;
    private int numCardsInRow;
    private int numCardsInCol;
    private int cardWidth = 30;
    private int cardHeight = 30;
    private int cardsHorizontalMargin = 5;
    private int cardsVerticalMargin = 5;
    private int numberOfPairsOfPatterns;
    private int numberOfPatterns;
    private ArrayList<String> possibleColors = new ArrayList<String> (Arrays.asList("red","blue","green","indigo","purple","yellow","orange"));
    private ArrayList<String> possibleShapes = new ArrayList<String> (Arrays.asList("donut","line","circle","square","triangle","pentagon","star"));
    private ArrayList<String> combinations = new ArrayList<String> ();

    // The indices of the arrayList "combinations" where the cards are revealed to the player

    private ArrayList<Boolean> cardsRevealed;

    // A player chooses two cards to see if they match, this is the idx of the first choice

    private int firstChoiceIdx;

    // A player chooses two cards to see if they match, if one is chosen this will be true

    private boolean madeFirstChoice = false;

    // use this to lock for write operations like add/remove

    private final Lock readLock;

    // use this to lock for read operations like get/iterator/contains..

    private final Lock writeLock;

    //// CONSTRUCTOR

    GameBoard(int numberOfPairsOfPatterns) {

        // initialize the JFrame

        this.numberOfPairsOfPatterns = (numberOfPairsOfPatterns > 49) ? 49 : numberOfPairsOfPatterns;

        this.numberOfPairsOfPatterns = (this.numberOfPairsOfPatterns < 1) ? 1 : this.numberOfPairsOfPatterns;

        this.numberOfPatterns = numberOfPairsOfPatterns * 2;

        // locks

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();


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
    public boolean getMadeFirstChoice() {
        return madeFirstChoice;
    }
    public void setMadeFirstChoice(boolean madeFirstChoice) {
        this.madeFirstChoice = madeFirstChoice;
    }

    //// METHODS
    void populateArrayLists() {
        // populate the combinations



        // do shuffling on combinations



        // All cards are unrevealed at the start

        cardsRevealed = new ArrayList<Boolean> (Collections.nCopies(10, false));


        // populate the cardsRevealed



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


    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {

    } // END OF GameDrawingPanel CONSTRUCTOR





    public void paint(Graphics g) {

        Graphics2D graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.GRAY);
        graphicSettings.fillRect(0,0, gameBoard.getWidth(), gameBoard.getHeight());
    }

    void drawShapeWithColor(String shape, String color) {

        // change color

        // switch statement for shape

    }


}



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