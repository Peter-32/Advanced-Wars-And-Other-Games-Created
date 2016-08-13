import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by peterjmyers on 8/11/16.
 */
public class GameBoard extends JFrame {


    //// FIELDS

    private int width = 800;
    private int height = 600;
    private int titleBarHeight;
    private int leftFrameBorderWidth;





    // sound files

    private String backgroundMusic = "file:./resources/Puzzle Theme1.wav";
    private Clip clip = null;




    //// CONSTRUCTOR

    GameBoard() {
        // initialize JFrame

        this.setSize(width, height);
        this.setTitle("Sliding Puzzle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleBarHeight = 30;
        leftFrameBorderWidth = 8;


        // Start the background music

        playMusic(backgroundMusic, true);

        // Create all 15 tiles in random locations
        createTiles();

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
        executor.scheduleAtFixedRate(new MainGameLoop(this, MainGameLoop), 0L, 20L, TimeUnit.MILLISECONDS);


        // final changes to JFrame

        setResizable(false);

        this.setVisible(true);

    }

    // GETTERS AND SETTERS

    private void createTiles() {
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


class InputListener {

    //// FIELDS

    //// CONSTRUCTOR

    InputListener() {
        // keyboard input

        // click input

        // change the state of things.

    }

}


class GameDrawingPanel extends JComponent {

    //// FIELDS
    GameBoard gameBoard;
    Tile[] tileArray;


    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

    }

    // when repainting add animation so that the tiles slide rather than swap locations

    public void paint(Graphics g) {
        // draw the border

        // draw all the tiles based on their X, Y location

    }
}

// This loop only is run when the InputListener tells it to run.

class MainGameLoop {

    //// FIELDS

    //// CONSTRUCTOR

    MainGameLoop() {
        // update states without threads

        // repaint using animation without threads
    }
}


class Tile {

    //// FIELDS
    int xPos;
    int yPos;
    int numberOnTile;

    //// CONSTRUCTOR
    Tile(int xPos, int yPos, int numberOnTile) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.numberOnTile = numberOnTile;

    }

    ////METHODS

    // constrained movement changes X and Y location

    void move() {
        // If colliding with wall correct

        // move; if clicked on move at a certain speed in the right direction until colliding
        // if keyboard is used move in the right direction until colliding


        // If colliding with tile correct


    }
}