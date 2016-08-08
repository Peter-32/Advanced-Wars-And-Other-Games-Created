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

    private int width = 800;
    private int height = 600;
    private int numCardsInRow;
    private int numCardsInCol;
    private int northAndSouthMargin;
    private int eastAndWestMargin = 50;
    private int cardWidth = 50;
    private int cardHeight = 50;
    private int cardsHorizontalMargin = 5;
    private int cardsVerticalMargin = 5;
    private int yClicked;
    private int xClicked;
    private int turnNumber = 0;
    private int numberOfPairsOfPatterns;
    private int numberOfPairsOfPatternsGuessedCorrectly = 0;
    private int numberOfPatterns;
    private ArrayList<String> possibleColors = new ArrayList<String> (Arrays.asList("red","blue","green","gray","pink","yellow","orange"));
    private ArrayList<String> possibleShapes = new ArrayList<String> (Arrays.asList("donut","vlines","circle","square","triangle","diamond","hlines"));
    private ArrayList<String> combinations = new ArrayList<String> ();

    // The indices of the arrayList "combinations" where the cards are fully revealed to the player.
    // True means that index was guessed correctly already.

    private ArrayList<Boolean> cardsGuessedCorrect;

    // A player chooses two cards to see if they match, this is the idx of the first choice

    private int firstChoiceIdx = -1;

    // This is the idx of the second choice

    private int secondChoiceIdx = -1;

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

        this.numberOfPairsOfPatterns = (this.numberOfPairsOfPatterns < 5) ? 5 : this.numberOfPairsOfPatterns;

        this.numberOfPatterns = this.numberOfPairsOfPatterns * 2;

        System.out.println(this.numberOfPairsOfPatterns);

        // Set up the north south margin based on input.  The 22 stands for the number of patterns in two rows.
        // Expecting 11 patterns per row.  If there are less rows, we want more margin to center the cards.

        northAndSouthMargin = 30 + ( 3 - (numberOfPatterns - 12) / 22) * (cardHeight + cardsVerticalMargin);

        //northAndSouthMargin = 30;

        // populate the arraylists

        populateArrayLists();

        // click listener added

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                xClicked = getX();
                yClicked = getY();
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
        executor.scheduleAtFixedRate(new MainGameLoop(this), 0L, 1000L, TimeUnit.MILLISECONDS);

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
    public int getTurnNumber() {
        return turnNumber;
    }
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
    public int getNumberOfPairsOfPatterns() {
        return numberOfPairsOfPatterns;
    }
    public int getNumberOfPairsOfPatternsGuessedCorrectly() {
        return numberOfPairsOfPatternsGuessedCorrectly;
    }
    public void setNumberOfPairsOfPatternsGuessedCorrectly(int numberOfPairsOfPatternsGuessedCorrectly) {
        this.numberOfPairsOfPatternsGuessedCorrectly = numberOfPairsOfPatternsGuessedCorrectly;
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
    public Iterator<Boolean> cardsGuessedCorrectIterator() {
        readLock.lock();
        try {
            return new ArrayList<Boolean>(cardsGuessedCorrect).iterator();
            // we iterate over a snapshot of our list
        } finally {
            readLock.unlock();
        }
    }
    public void updateCardsGuessedCorrect(int index, boolean isRevealed) {
        writeLock.lock();
        try {
            cardsGuessedCorrect.set(index, isRevealed);
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

        ArrayList<String> tempCombinations = new ArrayList<String> ();
        ArrayList<String> tempCombinations2 = new ArrayList<String> ();

        // populate the combinations
        // we need two of each combination that will be used

        for (String color : possibleColors) {
            for (String shape : possibleShapes) {
                tempCombinations.add(color + "-" + shape);
            }
        }
        Collections.shuffle(tempCombinations);

        // only keep what is needed for the number of patterns used
        for (int i = 0; i < numberOfPairsOfPatterns; i++) {
            tempCombinations2.add(tempCombinations.get(i));
        }

        // double the number of records
        combinations.addAll(tempCombinations2);
        combinations.addAll(tempCombinations2);

        // do shuffling on combinations

        Collections.shuffle(combinations);

        // All cards are unrevealed at the start

        cardsGuessedCorrect = new ArrayList<Boolean> (Collections.nCopies(numberOfPatterns, false));

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
    int xClicked;
    int yClicked;
    private int xCutoffPoint;
    private Iterator<Boolean> tempCardsGuessedCorrectIterator;
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
        this.xCutoffPoint = gameBoard.getWidth() - gameBoard.getEastAndWestMargin();

    } // END OF GameDrawingPanel CONSTRUCTOR

    public void paint(Graphics g) {

        // Cloning each frame because the program is using threading.  Probably could do without threading.

        combinations = gameBoard.cloneCombinations();

        graphicSettings = (Graphics2D)g;

        graphicSettings.setColor(Color.LIGHT_GRAY);
        graphicSettings.fillRect(0,0, gameBoard.getWidth(), gameBoard.getHeight());
        graphicSettings.setStroke(new BasicStroke(3));

        // get the most recent click location
        xClicked = gameBoard.getXClicked();
        yClicked = gameBoard.getYClicked();
        gameBoard.setXClicked(0);
        gameBoard.setYClicked(0);

        tempCardsGuessedCorrectIterator = gameBoard.cardsGuessedCorrectIterator();

        while(tempCardsGuessedCorrectIterator.hasNext()) {

            checkForNewlyClickedOnCards();

            // Check if it is already gusesed correctly and show be revealed

            if (tempCardsGuessedCorrectIterator.next()) {

                // show the pattern to the user, these have already been guessed correctly

                processShowingPattern();

            } else {

                // draw card

                graphicSettings.setColor(Color.WHITE);
                graphicSettings.fill(new Rectangle2D.Double(xPos, yPos,cardWidth, cardHeight));

                // this method finds the next xPos and yPos

                updateXPosYPosForNextDrawing();

            }

            idx++;

        } // END OF WHILE LOOP INSIDE paint() METHOD

        idx = 0;
        this.xPos = gameBoard.getEastAndWestMargin();
        this.yPos = gameBoard.getNorthAndSouthMargin();
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
            case "gray":
                graphicSettings.setColor(Color.GRAY);
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
                graphicSettings.setColor(Color.LIGHT_GRAY);
                graphicSettings.fill(new Ellipse2D.Double(xPos + cardWidth/3, yPos + cardHeight/3,cardWidth - cardWidth*2/3, cardHeight - cardHeight*2/3));
                break;
            case "vlines":
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
            case "diamond":
                int[] xArray2 = {xPos + cardWidth/2, xPos + cardWidth/6 , xPos + cardWidth/2, xPos + cardWidth*5/6};
                int[] yArray2 = {yPos + cardHeight/6, yPos + cardHeight/2, yPos + cardHeight*5/6, yPos + cardHeight/2};
                Polygon p2 = new Polygon(xArray2,yArray2,4);
                graphicSettings.fillPolygon(p2);
                break;
            case "hlines":
                graphicSettings.draw(new Line2D.Double(xPos + cardWidth/6, yPos + cardHeight*.25, xPos + cardWidth*5/6, yPos + cardHeight*.25));
                graphicSettings.draw(new Line2D.Double(xPos + cardWidth/6, yPos + cardHeight*.50, xPos + cardWidth*5/6, yPos + cardHeight*.50));
                graphicSettings.draw(new Line2D.Double(xPos + cardWidth/6, yPos + cardHeight*.75, xPos + cardWidth*5/6, yPos + cardHeight*.75));
                break;
        }

    } // END OF drawColoredShape() METHOD

    // Updates X and Y positions based on side margins.

    void updateXPosYPosForNextDrawing() {

        xPos += cardWidth + gameBoard.getCardsHorizontalMargin();

        // if we can fit another spot on this line, then we don't need a new line

        if (xPos + cardWidth + gameBoard.getCardsHorizontalMargin() < xCutoffPoint) {
            return;
        } else {
            xPos = gameBoard.getEastAndWestMargin();
            yPos += cardHeight + gameBoard.getCardsVerticalMargin();
        }
    }

    // check if the click occurred inside a card

    boolean cardContainsClick(int mouseX, int mouseY, int rectMinX, int rectMinY, int rectWidth, int rectHeight) {
        return (mouseX >= rectMinX && mouseX <= rectMinX + rectWidth) && (mouseY >= rectMinY && mouseY <= rectMinY + rectHeight);
    }

    // This method deals with the event in which two choices are made.  Either it is correct or incorrect

    void processGuessChoices(int guessIdx1, int guessIdx2) {
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // increment turns by 1 because a guess has been made

        gameBoard.setTurnNumber(gameBoard.getTurnNumber()+1);

        // sleep for 1 second

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // set both guesses in gameBoard to -1.  By setting to -1 threads will continue to redraw again.

        gameBoard.setFirstChoiceIdx(-1);
        gameBoard.setSecondChoiceIdx(-1);

        // if correct, give these true values in cardsGuessedCorrect.  Increment correct guesses variable

        //////////if ()   FINISH IF STATEMENT HERE//////////////////
        gameBoard.setNumberOfPairsOfPatternsGuessedCorrectly(gameBoard.getNumberOfPairsOfPatternsGuessedCorrectly() + 1);
        gameBoard.updateCardsGuessedCorrect(guessIdx1, true);
        gameBoard.updateCardsGuessedCorrect(guessIdx2, true);

        // otherwise it is an incorrect guess and things go back to normal after the sleeping for 1 second
        //////////ELSE PLACED HERE/////////

    }

    void processShowingPattern() {

        // find the shape and color
        combinationsElement = (String) combinations[idx];
        color = combinationsElement.split("-")[0];
        shape = combinationsElement.split("-")[1];

        // draw pattern

        drawColoredShape();

        // this method finds the next xPos and yPos

        updateXPosYPosForNextDrawing();

    }

    void checkForNewlyClickedOnCards() {///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // if it was just clicked on and hasn't been guessed correctly yet and wasn't the first choice index
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (cardContainsClick(xClicked, yClicked, xPos, yPos, cardWidth, cardHeight) && !isGuessedCorrectAlready
                && gameBoard.getFirstChoiceIdx() != idx) {

            // first show the pattern to the user

            processShowingPattern();

            // Check if it is the first or second guess

            if (gameBoard.getFirstChoiceIdx() == -1) {
                gameBoard.setFirstChoiceIdx(idx);
            } else {
                gameBoard.setSecondChoiceIdx(idx);  // Note: Redrawing takes no more threads until this is set back to -1
                processGuessChoices(gameBoard.getFirstChoiceIdx(),gameBoard.getSecondChoiceIdx());  //this sets it to -1
            }
        } // END OF IF STATEMENT
    }

} // END OF GameDrawingPanel CLASS



class MainGameLoop implements Runnable {

    private GameBoard gameBoard;

    MainGameLoop(GameBoard gameBoard) {

        this.gameBoard = gameBoard;

    }

    @Override
    public void run() {

        // check input/output

        // Only repaint if the second guess isn't chosen yet.  This gets updated to a non-negative and back to a
        // negative while inside the paint method.

        if (gameBoard.getSecondChoiceIdx() == -1) {

            // repaint

            gameBoard.repaint();
        }

    }

    /*
      ALL Input commands should be checked here
     */



}