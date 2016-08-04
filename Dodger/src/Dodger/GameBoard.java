package Dodger;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private int pressedKeyCode = 32;  // space
    private LinkedList<BadGuy> badGuys = new LinkedList<BadGuy>();

    // Player starts towards bottom vertically and toward the middle horizontally

    private Player player = new Player(this,width / 2 - 12, 3 * height / 2, 25);

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

        // Keep track of the score

        Runnable runScoreAccumulator = new runScoreAccumulatorThread(this);
        new Thread(runScoreAccumulator).start();

        // Create the main drawing panel and place it in the center of the gameBoard JFrame

        GameDrawingPanel gamePanel = new GameDrawingPanel(this);
        this.add(gamePanel, BorderLayout.CENTER);

        // Set up user input

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeyCode = e.getKeyCode (); // store the most recent key code used this frame
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeyCode = 32;  // space
            }
        });

        // Used to execute code after a specified delay
        // Parameter 5 is the number of threads to keep int he pool, even if they are idle
        // Also handles repainting

        ScheduledThreadPoolExecutor executor = new  ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(new MainGameLoop(this), 0L, 20L, TimeUnit.MILLISECONDS);

        // Show the JFrame

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
    public int getPressedKeyCode() { return pressedKeyCode;}
    public LinkedList<BadGuy> getBadGuys() {
        return badGuys;
    }
    public void addBadGuys(BadGuy badGuy) {
        this.badGuys.add(badGuy);
    }
    public void removeBadGuys(BadGuy badGuy) {
        this.badGuys.remove(badGuy);
    }
    public Player getPlayer() {
        return player;
    }
    //// METHODS

} // END OF GameBoard CLASS

class GameOver {
    // play music

    // make a display screen

    // restart game if option is chosen

}

class MainGameLoop implements Runnable {
    private GameBoard gameBoard;

    MainGameLoop(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        badGuys = gameBoard.getBadGuys();

    } //END OF MainGameLoop CONSTRUCTOR

    // run() is the main game loop

    @Override
    public void run() {

        // move the player
        movePlayer();
        moveBadGuys();

        // Redraws the game board

        gameBoard.repaint();

    }

    // Move the player 5 pixels if a key is down

    private void movePlayer() {
        if (gameBoard.getPressedKeyCode() == 87) {
            gameBoard.getPlayer().move(0,-5);
        } else if (gameBoard.getPressedKeyCode() == 68) {
            gameBoard.getPlayer().move(5,0);
        } else if (gameBoard.getPressedKeyCode() == 83) {
            gameBoard.getPlayer().move(0,5);
        } else if (gameBoard.getPressedKeyCode() == 65) {
            gameBoard.getPlayer().move(-5,0);
        }
    }

    private void moveBadGuys() {
        for (BadGuy badGuy : gameBoard.getBadGuys()) {
            badGuy.move();
        }
    }

} // END OF MainGameLoop CLASS

class GameDrawingPanel extends JComponent {

    //// FIELDS

    private GameBoard gameBoard;

    // Bad Guy fields.  All bad guys created in a collection, icons, and images.

    ImageIcon badGuyIcon = null;
    Image badGuyImg = null;
    Image resizedBadGuyImg = null;
    ImageIcon resizedBadGuyIcon = null;

    // The player icon

    ImageIcon playerIcon = null;

    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {

        this.gameBoard = gameBoard;
        badGuyIcon = new ImageIcon("resources/baddie.png");
        badGuyImg = badGuyIcon.getImage();
        playerIcon = new ImageIcon("resources/player.png");

        // Start a thread to create Bad Guys at the top of the screen

        Runnable runBadGuyCreation = new runBadGuyCreationThread(this, gameBoard);
        new Thread(runBadGuyCreation).start();

    } // END OF GameDrawingPanel CONSTRUCTOR

    // METHODS

    public void paint(Graphics g) {

        Graphics2D graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.BLACK);
        graphicSettings.fillRect(0,0, gameBoard.getWidth(), gameBoard.getHeight());

        graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (BadGuy badGuy : gameBoard.getBadGuys()) {
            resizedBadGuyImg = badGuyImg.getScaledInstance(badGuy.getSize(), badGuy.getSize(), java.awt.Image.SCALE_SMOOTH);
            resizedBadGuyIcon = new ImageIcon(resizedBadGuyImg);
            resizedBadGuyIcon.paintIcon(this, g, badGuy.getTopLeftXPos(), badGuy.getTopLeftYPos());
        }

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

        // generates Bad Guys that fall from the top of the screen at random positions, X velocity, and Y velocity.
        // The starting X position should be slightly wider than the screen width.

        while(!gameBoard.getGameOver()) {
            try {
                Thread.sleep(400);
                this.topLeftXPos = (int) (Math.random() * (gameBoard.getWidth() + 141)) - 70; // from -70 to gameBoard.getWidth() + 70
                this.topLeftYPos = -50; // always the same
                this.xVelocity = (int) (Math.random() * 4); // magnitude of the x velocity is 0 to 3
                this.yVelocity = (int) (Math.random() * 3) + 1; // between 1 and 3
                this.size = (int) (Math.random() * 41) + 10; // between 10 and 50

                // Choose if moving left or right based on initial position.
                // If on right side move left, and vise versa.

                this.xVelocity = xVelocity * ((topLeftXPos > gameBoard.getWidth()/2) ? -1 : 1);

                // Add the Bad Guy to the Bad Guy array

                gameBoard.addBadGuys(new BadGuy(gameBoard, topLeftXPos, topLeftYPos, xVelocity, yVelocity, size));
            }
            catch(InterruptedException e)
            {}
        }
    }

} // END OF runScoreAccumulatorThread CLASS
