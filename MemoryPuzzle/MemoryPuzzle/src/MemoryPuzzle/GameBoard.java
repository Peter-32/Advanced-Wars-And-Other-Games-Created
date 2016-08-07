package MemoryPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Peter on 8/7/2016.
 */
public class GameBoard extends JFrame {

    //// FIELDS

    private int width;
    private int height;
    private int cardsInRow;
    private int cardsInCol;
    private int numberOfPairsOfPatterns;
    private ArrayList<String> possibleColors = new ArrayList<String> (Arrays.asList("red","blue","green","indigo","purple","yellow","orange"));
    private ArrayList<String> possibleShapes = new ArrayList<String> (Arrays.asList("donut","line","circle","square","triangle","pentagon","star"));
    private ArrayList<String> combinations = new ArrayList<String> ();

    // The indices of the arrayList "combinations" where the cards are revealed to the player

    private ArrayList<Integer> cardsRevealedIndices = new ArrayList<Integer> ();

    // A player chooses two cards to see if they match, this is the idx of the first choice

    private int firstChoiceIdx;

    // A player chooses two cards to see if they match, if one is chosen this will be true

    private boolean madeFirstChoice = false;

    //// CONSTRUCTOR

    GameBoard(int numberOfPairsOfPatterns) {

        // initialize the JFrame

        this.numberOfPairsOfPatterns = (numberOfPairsOfPatterns > 49) ? 49 : numberOfPairsOfPatterns;

        this.numberOfPairsOfPatterns = (this.numberOfPairsOfPatterns < 1) ? 1 : this.numberOfPairsOfPatterns;


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

    //// METHODS
    void populateArrayLists() {
        // populate the combinations



        // do shuffling on combinations



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