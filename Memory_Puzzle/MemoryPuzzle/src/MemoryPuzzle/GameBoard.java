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
    private int titleBarHeight;
    private int leftFrameBorderWidth;
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
    private CardStateChanges cardStateChanges;

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
        titleBarHeight = 30;
        leftFrameBorderWidth = 8;

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

/*        System.out.println(this.numberOfPairsOfPatterns);*/

        // Set up the north south margin based on input.  The 22 stands for the number of patterns in two rows.
        // Expecting 11 patterns per row.  If there are less rows, we want more margin to center the cards.

        northAndSouthMargin = 30 + ( 3 - (numberOfPatterns - 12) / 22) * (cardHeight + cardsVerticalMargin);

        //northAndSouthMargin = 30;

        // populate the arraylists

        populateArrayLists();

        // Create object for use in MainGameLoop

        cardStateChanges =  new CardStateChanges(this);

        // click listener added

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // Only register clicks when in this state

                if (cardStateChanges.getIterationsUntilReset() == -1) {
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
        });


        // threads added for things such as MainGameLoop
        ScheduledThreadPoolExecutor executor = new  ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(new MainGameLoop(this, cardStateChanges), 0L, 20L, TimeUnit.MILLISECONDS);

        setResizable(false);

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
    public int getTitleBarHeight(int titleBarHeight) { return this.titleBarHeight = titleBarHeight; }
    public int getLeftFrameBorderWidth(int leftFrameBorderWidth) { return this.leftFrameBorderWidth = leftFrameBorderWidth; }

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
            return combinations.iterator();
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
            return cardsGuessedCorrect.iterator();
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

class CardStateChanges {

    //// FIELDS
    GameBoard gameBoard;
    private Object[] combinations;
    private int cardWidth;
    private int cardHeight;
    private int idx = 0;
    private int xLoc = 0;
    private int yLoc = 0;
    private int xClicked;
    private int yClicked;
    private int xCutoffPoint;
    private Iterator<Boolean> tempCardsGuessedCorrectIterator;
    private boolean isGuessedCorrectAlready;
    private int iterationsUntilReset = -1;

    //// CONSTRUCTOR

    CardStateChanges(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.xLoc = gameBoard.getEastAndWestMargin();
        this.yLoc = gameBoard.getNorthAndSouthMargin();
        cardWidth = this.gameBoard.getCardWidth();
        cardHeight = this.gameBoard.getCardsHeight();
        this.xCutoffPoint = gameBoard.getWidth() - gameBoard.getEastAndWestMargin();

    }
    public int getIterationsUntilReset() {
        return iterationsUntilReset;
    }



    void update() {


        if (iterationsUntilReset != -1) {

            iterationsUntilReset--;

            if (iterationsUntilReset == 0) {
                // Reset the choices

                gameBoard.setFirstChoiceIdx(-1);
                gameBoard.setSecondChoiceIdx(-1);
                iterationsUntilReset = -1;
            }


        }

        // Cloning each frame because the program is using threading.  Probably could do without threading.

        combinations = gameBoard.cloneCombinations();

        // get the most recent click location

        xClicked = gameBoard.getXClicked();
        yClicked = gameBoard.getYClicked();

        tempCardsGuessedCorrectIterator = gameBoard.cardsGuessedCorrectIterator();

        while(tempCardsGuessedCorrectIterator.hasNext()) {

            isGuessedCorrectAlready = tempCardsGuessedCorrectIterator.next();

            checkForNewlyClickedOnCards();

            updatexLocyLocForNextDrawing();

            idx++;

        } // END OF WHILE LOOP INSIDE paint() METHOD

        idx = 0;
        this.xLoc = gameBoard.getEastAndWestMargin();
        this.yLoc = gameBoard.getNorthAndSouthMargin();
    } // END OF paint() METHOD


    void updatexLocyLocForNextDrawing() {

        xLoc += cardWidth + gameBoard.getCardsHorizontalMargin();

        // if we can fit another spot on this line, then we don't need a new line

        if (xLoc + cardWidth + gameBoard.getCardsHorizontalMargin() < xCutoffPoint) {
            return;
        } else {
            xLoc = gameBoard.getEastAndWestMargin();
            yLoc += cardHeight + gameBoard.getCardsVerticalMargin();
        }
    }


    void processGuessChoices(int guessIdx1, int guessIdx2) {

        // increment turns by 1 because a guess has been made

        gameBoard.setTurnNumber(gameBoard.getTurnNumber()+1);

        // if correct, give these true values in cardsGuessedCorrect.  Increment correct guesses variable

        if (((String)combinations[guessIdx1]).equals((String)combinations[guessIdx2])) {

            // We know that it was a correct guess, so we apply updates to gameBoard fields
            // The drawing will view these new fields and should draw correctly

            gameBoard.setNumberOfPairsOfPatternsGuessedCorrectly(gameBoard.getNumberOfPairsOfPatternsGuessedCorrectly() + 1);
            gameBoard.updateCardsGuessedCorrect(guessIdx1, true);
            gameBoard.updateCardsGuessedCorrect(guessIdx2, true);
            gameBoard.setFirstChoiceIdx(-1);
            gameBoard.setSecondChoiceIdx(-1);

        } else {

            // create some time to see what is under the second guess before reseting

            iterationsUntilReset = 50;

            // Perhaps add a sound file here later.

        }

    } // END OF processGuessChoices METHOD


    // check if the click occurred inside a card

    boolean cardContainsClick(int mouseX, int mouseY, int rectMinX, int rectMinY, int rectWidth, int rectHeight) {
        return (mouseX >= rectMinX && mouseX <= rectMinX + rectWidth) && (mouseY >= rectMinY && mouseY <= rectMinY + rectHeight);
    }


    void checkForNewlyClickedOnCards() {
        // if all of these happen: 1) just clicked on. 2) hasn't been guessed correctly yet. 3) wasn't the first choice index

        //System.out.println(gameBoard.getFirstChoiceIdx());
        //System.out.println(xClicked);

        /* if (xClicked != 0) {
            System.out.println("xClick = " + xClicked + " yClick = " + yClicked);
            System.out.println("idx = " + idx + " first guess = " + gameBoard.getFirstChoiceIdx() + " second guess = " +
                    gameBoard.getSecondChoiceIdx());
            System.out.println("xLoc = " + xLoc + " yLoc = " + yLoc);
            System.out.println("cardWidth = " + cardWidth + " cardHeight = "  + cardHeight);

            System.out.println("was this contained?");
            System.out.println(cardContainsClick(xClicked, yClicked, xLoc, yLoc, cardWidth, cardHeight) && !isGuessedCorrectAlready
                    && gameBoard.getFirstChoiceIdx() != idx);
            System.out.println(cardContainsClick(xClicked, yClicked, xLoc, yLoc, cardWidth, cardHeight));
            System.out.println(!isGuessedCorrectAlready);
            System.out.println(gameBoard.getFirstChoiceIdx() != idx);
        } */
        if (cardContainsClick(xClicked, yClicked, xLoc, yLoc, cardWidth, cardHeight) && !isGuessedCorrectAlready
                && gameBoard.getFirstChoiceIdx() != idx) {

            // Check if it is the first or second guess

            if (gameBoard.getFirstChoiceIdx() == -1) {
                gameBoard.setFirstChoiceIdx(idx);
/*                System.out.println("first choice = " + gameBoard.getFirstChoiceIdx() +
                        " second choice = " + gameBoard.getSecondChoiceIdx());*/
            } else {
                gameBoard.setSecondChoiceIdx(idx);
/*                System.out.println("first choice = " + gameBoard.getFirstChoiceIdx() +
                        " second choice = " + gameBoard.getSecondChoiceIdx());*/
                processGuessChoices(gameBoard.getFirstChoiceIdx(),gameBoard.getSecondChoiceIdx());
            }
        } // END OF IF STATEMENT
    }

}




class GameOver {
    GameOver(GameBoard gameBoard) {
        //clapping sound

        //message displayed

        JOptionPane.showMessageDialog(gameBoard, "Game Over!");

        //create new GameBoard

        //new GameBoard(gameBoard.getNumberOfPairsOfPatterns());

    }
}

class GameDrawingPanel extends JComponent {




    //// FIELDS

    private GameBoard gameBoard;
    private Object[] combinations;
    private int cardWidth;
    private int cardHeight;
    private int idx = 0;
    private int xLoc = 0;
    private int yLoc = 0;
    private int xCutoffPoint;
    private Iterator<Boolean> tempCardsGuessedCorrectIterator;
    boolean isGuessedCorrectAlready;
    String combinationsElement;
    String shape;
    String color;
    Graphics2D graphicSettings;

    //// CONSTRUCTOR

    GameDrawingPanel(GameBoard gameBoard) {

        this.gameBoard = gameBoard;
        this.xLoc = gameBoard.getEastAndWestMargin();
        this.yLoc = gameBoard.getNorthAndSouthMargin();
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

        tempCardsGuessedCorrectIterator = gameBoard.cardsGuessedCorrectIterator();

        while(tempCardsGuessedCorrectIterator.hasNext()) {

            isGuessedCorrectAlready = tempCardsGuessedCorrectIterator.next();


            //checkForNewlyClickedOnCards();

            //System.out.println(gameBoard.getSecondChoiceIdx());
            // Check if it is already guessed correctly, or if it is a current guess.

            if (isGuessedCorrectAlready ||
                    gameBoard.getFirstChoiceIdx() == idx || gameBoard.getSecondChoiceIdx() == idx) {

                // show the pattern to the user, these have already been guessed correctly

                processShowingPattern();

            } else {

                // draw card

                graphicSettings.setColor(Color.WHITE);
                graphicSettings.fill(new Rectangle2D.Double(xLoc, yLoc,cardWidth, cardHeight));

                // this method finds the next xLoc and yLoc

                updatexLocyLocForNextDrawing();

            }

            idx++;

        } // END OF WHILE LOOP INSIDE paint() METHOD

        idx = 0;
        this.xLoc = gameBoard.getEastAndWestMargin();
        this.yLoc = gameBoard.getNorthAndSouthMargin();
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
                graphicSettings.fill(new Ellipse2D.Double(xLoc + cardWidth/6, yLoc + cardHeight/6,cardWidth - cardWidth/3, cardHeight - cardHeight/3));
                graphicSettings.setColor(Color.LIGHT_GRAY);
                graphicSettings.fill(new Ellipse2D.Double(xLoc + cardWidth/3, yLoc + cardHeight/3,cardWidth - cardWidth*2/3, cardHeight - cardHeight*2/3));
                break;
            case "vlines":
                graphicSettings.draw(new Line2D.Double(xLoc + cardWidth*.25, yLoc + cardHeight/6,xLoc + cardWidth*.25, yLoc + cardHeight*5/6));
                graphicSettings.draw(new Line2D.Double(xLoc + cardWidth*.5, yLoc + cardHeight/6,xLoc + cardWidth*.50, yLoc + cardHeight*5/6));
                graphicSettings.draw(new Line2D.Double(xLoc + cardWidth*.75, yLoc + cardHeight/6,xLoc + cardWidth*.75, yLoc + cardHeight*5/6));
                break;
            case "circle":
                graphicSettings.fill(new Ellipse2D.Double(xLoc + cardWidth/6, yLoc + cardHeight/6,cardWidth - cardWidth/3, cardHeight - cardHeight/3));
                break;
            case "square":
                graphicSettings.fill(new Rectangle2D.Double(xLoc + cardWidth/6, yLoc + cardHeight/6,cardWidth - cardWidth/3, cardHeight - cardHeight/3));
                break;
            case "triangle":
                int[] xArray = {xLoc + cardWidth/2, xLoc + cardWidth/6, xLoc + cardWidth*5/6};
                int[] yArray = {yLoc + cardHeight/6, yLoc + cardHeight*5/6, yLoc + cardHeight*5/6};
                Polygon p = new Polygon(xArray,yArray,3);
                graphicSettings.fillPolygon(p);
                break;
            case "diamond":
                int[] xArray2 = {xLoc + cardWidth/2, xLoc + cardWidth/6 , xLoc + cardWidth/2, xLoc + cardWidth*5/6};
                int[] yArray2 = {yLoc + cardHeight/6, yLoc + cardHeight/2, yLoc + cardHeight*5/6, yLoc + cardHeight/2};
                Polygon p2 = new Polygon(xArray2,yArray2,4);
                graphicSettings.fillPolygon(p2);
                break;
            case "hlines":
                graphicSettings.draw(new Line2D.Double(xLoc + cardWidth/6, yLoc + cardHeight*.25, xLoc + cardWidth*5/6, yLoc + cardHeight*.25));
                graphicSettings.draw(new Line2D.Double(xLoc + cardWidth/6, yLoc + cardHeight*.50, xLoc + cardWidth*5/6, yLoc + cardHeight*.50));
                graphicSettings.draw(new Line2D.Double(xLoc + cardWidth/6, yLoc + cardHeight*.75, xLoc + cardWidth*5/6, yLoc + cardHeight*.75));
                break;
        }

    } // END OF drawColoredShape() METHOD

    // Updates X and Y positions based on side margins.

    void updatexLocyLocForNextDrawing() {

        xLoc += cardWidth + gameBoard.getCardsHorizontalMargin();

        // if we can fit another spot on this line, then we don't need a new line

        if (xLoc + cardWidth + gameBoard.getCardsHorizontalMargin() < xCutoffPoint) {
            return;
        } else {
            xLoc = gameBoard.getEastAndWestMargin();
            yLoc += cardHeight + gameBoard.getCardsVerticalMargin();
        }
    }

    // This method deals with the event in which two choices are made.  Either it is correct or incorrect



    void processShowingPattern() {

        // find the shape and color
        combinationsElement = (String) combinations[idx];
        color = combinationsElement.split("-")[0];
        shape = combinationsElement.split("-")[1];

        // draw pattern

        drawColoredShape();

        // this method finds the next xLoc and yLoc

        updatexLocyLocForNextDrawing();

    }






} // END OF GameDrawingPanel CLASS



class MainGameLoop implements Runnable {

    private GameBoard gameBoard;
    private CardStateChanges cardStateChanges;
    private boolean gameOver = false;

    MainGameLoop(GameBoard gameBoard, CardStateChanges cardStateChanges) {

        this.gameBoard = gameBoard;
        this.cardStateChanges = cardStateChanges;

    }

    @Override
    public void run() {

        // logging
        /*
        if (gameBoard.getXClicked() != 0) {
            System.out.println("xClick = " + gameBoard.getXClicked() + " yClick = " + gameBoard.getYClicked());
            System.out.println("first choice = " + gameBoard.getFirstChoiceIdx() +
                    " second choice = " + gameBoard.getSecondChoiceIdx());
        }
*/
        //System.out.println(gameBoard.getSecondChoiceIdx());

        // check input/output

        cardStateChanges.update();

        // repaint

        gameBoard.repaint();

        // reset the clicks after paint is done

        gameBoard.setXClicked(0);
        gameBoard.setYClicked(0);

        // check if game is over

        if (!gameOver && gameBoard.getNumberOfPairsOfPatterns() == gameBoard.getNumberOfPairsOfPatternsGuessedCorrectly()) {
            gameOver = true;
            new GameOver(gameBoard);
        }

    }

    /*
      ALL Input commands should be checked here
     */



}