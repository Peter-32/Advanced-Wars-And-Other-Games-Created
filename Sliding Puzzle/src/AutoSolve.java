import javax.swing.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;

/**
 * Created by Peter on 8/13/2016.
 */
public class AutoSolve {
    GameBoard gameBoard;
    int computerSpeed;
    int sleepInterval;
    private Iterator<Tile> tempTileIterator;

    AutoSolve(GameBoard gameBoard, int computerSpeed) {
        this.gameBoard = gameBoard;
        this.computerSpeed = computerSpeed;

        sleepInterval = 1000 / computerSpeed;

        // repaint using animation
        gameBoard.repaint();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Instant starts = Instant.now();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingRight();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingDown();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingLeft();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // repaint using animation
        gameBoard.repaint();
        tryMovingUp();
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameBoard.repaint();

        Instant ends = Instant.now();

        JOptionPane.showMessageDialog(gameBoard, "Time taken: " + Duration.between(starts, ends));

    } // END OF CONSTRUCTOR

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

} // END OF AutoSolve CLASS
