package Dodger;
import java.awt.BorderLayout;

import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import javax.swing.*;

/**
 * Created by Peter on 8/1/2016.
 */
public class GameBoard extends JFrame {

    //// FIELDS

    private int width = 1200;
    private int height = 900;
    private int score = 0;
    private int topScore = 0;
    private boolean gameOver = false;

    //// MAIN

    public static void main(String[] args) {
        new GameBoard();
    }

    // CONSTRUCTOR
    private GameBoard() {

        // JFrame defaults

        this.setSize(width, height);
        this.setTitle("Dodger");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Start a thread to keep track of the score

        Runnable runScoreAccumulator = new runScoreAccumulatorThread(this);
        new Thread(runScoreAccumulator).start();

        // Main drawing panel

        GameDrawingPanel gamePanel = new GameDrawingPanel();

        // Make drawing area take up center of frame

        this.add(gamePanel, BorderLayout.CENTER);

        // Used to execute code after a specified delay
        // Parameter 5 is the number of threads to keep int he pool, even if they are idle
        // Creates the method RepaintTheBoard

        ScheduledThreadPoolExecutor executor = new  ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0L, 20L, TimeUnit.MILLISECONDS);

        // Show the frame

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
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getTopScore() {
        return topScore;
    }
    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }
    public boolean getGameOver() {
        return gameOver;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    //// METHODS

    // RUN ONCE PER GAME

    // Shows a pop-up window message when the game is over.  Called by the player object.

    private void displayGameOverScreen() {

    }

} // END OF GameBoard CLASS


class RepaintTheBoard implements Runnable {
    private GameBoard gameBoard;

    RepaintTheBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void run() {

        // Redraws the game board

        gameBoard.repaint();

    }
} // END OF RepaintTheBoard CLASS

class GameDrawingPanel extends JComponent {

    //// FIELDS

    // stores a list of all BadGuys created

    private GameBoard gameBoard;
    private LinkedList<BadGuy> badGuys = new LinkedList<BadGuy>();

    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {

        this.gameBoard = gameBoard;

        // Start a thread to create Bad Guys at the top of the screen

        Runnable runBadGuyCreation = new runBadGuyCreationThread(this, gameBoard);
        new Thread(runBadGuyCreation).start();

    } // END OF GameDrawingPanel CONSTRUCTOR

    //// GETTERS AND SETTERS
    public LinkedList<BadGuy> getBadGuys() {
        return badGuys;
    }
    public void addBadGuys(BadGuy badGuy) {
        this.badGuys.add(badGuy);
    }
    public void removeBadGuys(BadGuy badGuy) {
        this.badGuys.remove(badGuy);
    }

    // METHODS

    // generates Bad Guys that fall from the top of the screen at random positions, X velocity, and Y velocity.
    // The starting X position should be slightly wider than the screen width.

    public void paint() {

        // THIS NEEDS TO BE WORKED ON /////////////////////////////////////////////////////////////////////////
        // loop through BadGuy array.  Draw different things based on X Y position
        // youtube video has more information.  This is possibly the most confusing part of the rest of the project.
    }
} // END OF GameDrawingPanel CLASS


// Accumulates the score as long as the player survives.

class runScoreAccumulatorThread implements Runnable{

    // FIELDS

    private GameBoard gameBoard;

    // CONSTRUCTOR

    runScoreAccumulatorThread(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

    } // END OF runScoreAccumulatorThread CONSTRUCTOR

    @Override
    public void run() {

        while (!gameBoard.getGameOver()) {

            try {
                Thread.sleep(250);
                gameBoard.setScore(gameBoard.getScore() + 1);
            }
            catch(InterruptedException e)
            {}
        }
    }

} // END OF runScoreAccumulatorThread CLASS

// Creates Bad Guys from the top of the screen

class runBadGuyCreationThread implements Runnable{

    // FIELDS

    private GameDrawingPanel gameDrawingPanel;
    private GameBoard gameBoard;
    int topLeftXPos, topLeftYPos, xVelocity, yVelocity, size;

    // CONSTRUCTOR

    runBadGuyCreationThread(GameDrawingPanel gameDrawingPanel, GameBoard gameBoard) {
        this.gameDrawingPanel = gameDrawingPanel;
        this.gameBoard = gameBoard;

    } // END OF runScoreAccumulatorThread CONSTRUCTOR

    // synchronized is just one way of locking this method down so one thread can access it at a time.

    @Override
    synchronized public void run() {

        while(!gameBoard.getGameOver()) {
            try {
                Thread.sleep(100);
                this.topLeftXPos = (int) (Math.random() * (gameBoard.getWidth() + 141)) - 70; // from -70 to gameBoard.getWidth() + 70
                this.topLeftYPos = -50; // always the same
                this.xVelocity = (int) (Math.random() * 4); // magnitude of the x velocity is 0 to 3
                this.yVelocity = (int) (Math.random() * 3) + 1; // between 1 and 3
                this.size = (int) (Math.random() * 41) + 10; // between 10 and 50

                // Choose if moving left or right based on initial position.
                // If on right side move left, and vise versa.

                this.xVelocity = xVelocity * ((topLeftXPos > gameBoard.getWidth()/2) ? -1 : 1);

                // Add the Bad Guy to the Bad Guy array

                gameDrawingPanel.addBadGuys(new BadGuy(topLeftXPos, topLeftYPos, xVelocity, yVelocity, size));
            }
            catch(InterruptedException e)
            {}
        }
    }

} // END OF runScoreAccumulatorThread CLASS
