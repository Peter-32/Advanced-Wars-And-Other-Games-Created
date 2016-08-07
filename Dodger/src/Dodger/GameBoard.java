package Dodger;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// import sound libraries

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.*;

import javax.swing.*;

//import static javafx.scene.input.KeyCode.T;

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
    private boolean keyUpPressed = false;
    private boolean keyRightPressed = false;
    private boolean keyDownPressed = false;
    private boolean keyLeftPressed = false;


    // sound files

    private String backgroundMusic = "file:./resources/background.mid";
    private String gameOverMusic = "file:./resources/gameover.wav";
    private Clip clip = null;

    // use this to lock for write operations like add/remove

    private final Lock readLock;

    // use this to lock for read operations like get/iterator/contains..

    private final Lock writeLock;

    // the underlying list

    private final List<BadGuy> badGuys = new ArrayList();

    // Player starts towards bottom vertically and toward the middle horizontally

    private Player player = new Player(this,(width / 2) - 12, 3 * height / 4, 25);


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

        // locks

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();

        // Start the background music

        playMusic(backgroundMusic, true);

        // Create the main drawing panel and place it in the center of the gameBoard JFrame

        GameDrawingPanel gameDrawingPanel = new GameDrawingPanel(this);
        this.add(gameDrawingPanel, BorderLayout.CENTER);

        // Keep track of the score

        Runnable runScoreAccumulator = new runScoreAccumulatorThread(this, gameDrawingPanel);
        new Thread(runScoreAccumulator).start();

        // Set up user input

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
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

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 87 || e.getKeyCode() == 38) {
                    keyUpPressed = false;
                } else if (e.getKeyCode() == 68 || e.getKeyCode() == 39) {
                    keyRightPressed = false;
                } else if (e.getKeyCode() == 83 || e.getKeyCode() == 40) {
                    keyDownPressed = false;
                } else if (e.getKeyCode() == 65 || e.getKeyCode() == 37) {
                    keyLeftPressed = false;
                }
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
    public boolean getKeyUpPressed() { return keyUpPressed;}
    public boolean getKeyRightPressed() { return keyRightPressed;}
    public boolean getKeyDownPressed() { return keyDownPressed;}
    public boolean getKeyLeftPressed() { return keyLeftPressed;}
    public Iterator<BadGuy> badGuyIterator() {
        readLock.lock();
        try {
            return new ArrayList<BadGuy>(badGuys).iterator();
            // we iterator over a snapshot of our list
        } finally {
            readLock.unlock();
        }
    }
    public void addBadGuys(BadGuy e) {
        writeLock.lock();
        try {
            badGuys.add(e);
        } finally{
            writeLock.unlock();
        }
    }
    public void removeBadGuys(BadGuy e) {
        writeLock.lock();
        try {
            badGuys.remove(e);
        } finally{
            writeLock.unlock();
        }
    }
    public Player getPlayer() {
        return player;
    }
    //// METHODS

    // plays music files

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

    // used when the game ends

    void GameOver() {
        // play music
        playMusic(gameOverMusic, false);
        // make a display screen
        //setVisible(false);
        JOptionPane.showMessageDialog(this, "Game Over!");
        // restart game if option is chosen
        //dispose();
        new GameBoard();
    }

} // END OF GameBoard CLASS

class MainGameLoop implements Runnable {
    private GameBoard gameBoard;
    Iterator<BadGuy> tempIterator;

    MainGameLoop(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

    } //END OF MainGameLoop CONSTRUCTOR

    // run() is the main game loop

    @Override
    public void run() {
        if (gameBoard.getGameOver()) return;
        // move the player and Bad Guys

        movePlayer();
        moveBadGuys();

        /*
        // check for collision

        gameBoard.getPlayer().getBounds();

        tempIterator = gameBoard.badGuyIterator();
        while(tempIterator.hasNext()) {
            tempIterator.next().getBounds();
        } */

        // Redraws the game board

        gameBoard.repaint();


    }

    // Move the player 5 pixels if a key is down

    private void movePlayer() {

        if (gameBoard.getKeyUpPressed()) {
            gameBoard.getPlayer().move(0,5);
        }
        if (gameBoard.getKeyRightPressed()) {
            gameBoard.getPlayer().move(-5,0);
        }
        if (gameBoard.getKeyDownPressed()) {
            gameBoard.getPlayer().move(0,-5);
        }
        if (gameBoard.getKeyLeftPressed()) {
            gameBoard.getPlayer().move(5,0);
        }

    }

    private void moveBadGuys() {
        tempIterator = gameBoard.badGuyIterator();
        while(tempIterator.hasNext()) {
            tempIterator.next().move();
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
    Image playerImg = null;
    Image resizedPlayerImg = null;
    ImageIcon resizedPlayerIcon = null;

    // The player icon

    ImageIcon playerIcon = null;

    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {

        this.gameBoard = gameBoard;
        badGuyIcon = new ImageIcon("resources/baddie.png");
        badGuyImg = badGuyIcon.getImage();
        playerIcon = new ImageIcon("resources/player.png");
        playerImg = playerIcon.getImage();

        // resize the player icon

        resizedPlayerImg = playerImg.getScaledInstance(gameBoard.getPlayer().getSize(), gameBoard.getPlayer().getSize(), java.awt.Image.SCALE_SMOOTH);
        resizedPlayerIcon = new ImageIcon(resizedPlayerImg);

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

        Iterator<BadGuy> tempIterator = gameBoard.badGuyIterator();

        while(tempIterator.hasNext()) {
            BadGuy badGuy = tempIterator.next();
            resizedBadGuyImg = badGuyImg.getScaledInstance(badGuy.getSize(), badGuy.getSize(), java.awt.Image.SCALE_SMOOTH);
            resizedBadGuyIcon = new ImageIcon(resizedBadGuyImg);
            resizedBadGuyIcon.paintIcon(this, g, badGuy.getTopLeftXPos(), badGuy.getTopLeftYPos());

        }

        // draw the player at their location

        resizedPlayerIcon.paintIcon(this, g, gameBoard.getPlayer().getTopLeftXPos(), gameBoard.getPlayer().getTopLeftYPos());

    }
} // END OF GameDrawingPanel CLASS


// Accumulates the score as long as the player survives.

class runScoreAccumulatorThread implements Runnable{

    // FIELDS

    private GameBoard gameBoard;
    private GameDrawingPanel gameDrawingPanel;

    // CONSTRUCTOR

    runScoreAccumulatorThread(GameBoard gameBoard, GameDrawingPanel gameDrawingPanel) {
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
                Thread.sleep(200);
                this.topLeftXPos = (int) (Math.random() * (gameBoard.getWidth() + 141)) - 70; // from -70 to gameBoard.getWidth() + 70
                this.topLeftYPos = -50; // always the same  -50
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
