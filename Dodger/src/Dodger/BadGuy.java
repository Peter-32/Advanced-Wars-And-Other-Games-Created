package Dodger;

import java.awt.*;

/**
 * Created by Peter on 8/1/2016.
 */
public class BadGuy {

    // FIELDS

    private int topLeftXPos, topLeftYPos, xVelocity, yVelocity, size;

    // GameDrawingPanel is used to remove BadGuys from the Bad Guy array when they fall down off the screen.

    GameBoard gameBoard;

    //// CONSTRUCTOR

    BadGuy(GameBoard gameBoard, int topLeftXPos, int topLeftYPos, int xVelocity, int yVelocity, int size) {
        this.gameBoard = gameBoard;
        this.topLeftXPos = topLeftXPos;
        this.topLeftYPos = topLeftYPos;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.size = size;

    } // END OF BadGuy CONSTRUCTOR

    //// GETTERS AND SETTERS

    public int getTopLeftXPos() {
        return topLeftXPos;
    }
    public void setTopLeftXPos(int topLeftXPos) {
        this.topLeftXPos = topLeftXPos;
    }
    public int getTopLeftYPos() {
        return topLeftYPos;
    }
    public void setTopLeftYPos(int topLeftYPos) {
        this.topLeftYPos = topLeftYPos;
    }
    public int getXVelocity() {
        return xVelocity;
    }
    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }
    public int getYVelocity() {
        return yVelocity;
    }
    public void setYVelocity (int yVelocity) {
        this.yVelocity = yVelocity;
    }
    public int getSize() {
        return size;
    }
    public void setSize (int size) {
        this.size = size;
    }
    int getXCenter() {
        return topLeftXPos + (int)(size/2);
    }
    int getYCenter() {
        return topLeftYPos + (int)(size/2);
    }

    ////METHODS

    // used for collison detection

    Rectangle getBounds() {
        return new Rectangle(topLeftXPos,topLeftYPos,size,size);
    }

    void move() {

        // The bad guy's bounds for collison detection
        Rectangle badGuyBounds = getBounds();

        if(getTopLeftYPos() > gameBoard.getHeight() + 10) {

            // first check for collison

            gameBoard.removeBadGuys(this);
            return;
        }

        topLeftXPos += xVelocity;
        topLeftYPos += yVelocity;

        // now check for collison
        Rectangle playerBox = gameBoard.getPlayer().getBounds();

        if (badGuyBounds.intersects(playerBox)) {
            gameBoard.setGameOver(true);
            gameBoard.GameOver();
        }
    }

}
