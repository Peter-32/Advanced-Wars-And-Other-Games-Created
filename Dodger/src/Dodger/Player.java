package Dodger;

import java.awt.*;

/**
 * Created by Peter on 8/3/2016.
 */
public class Player {

    //// FIELDS

    private int topLeftXPos, topLeftYPos, size;
    private GameBoard gameBoard;

    //// CONSTRUCTOR

    Player(GameBoard gameboard, int topLeftXPos, int topLeftYPos, int size) {
        this.gameBoard = gameboard;
        this.topLeftXPos = topLeftXPos;
        this.topLeftYPos = topLeftYPos;
        this.size = size;

    } // END OF Player CONSTRUCTOR

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

    //// METHODS

    // used for collison detection

    Rectangle getBounds() {
        return new Rectangle(topLeftXPos,topLeftYPos,size,size);
    }

    void move(int xDirection, int yDirection) {

        // allows you to move and move off screen

        topLeftXPos -= xDirection;
        topLeftYPos -= yDirection;

        // places you back on the screen if moving off the screen

        if (topLeftXPos < 10) topLeftXPos = 10;
        if (topLeftYPos < 10) topLeftYPos = 10;
        if (topLeftXPos + (2 * size) > gameBoard.getWidth()) topLeftXPos = gameBoard.getWidth() - (2 * size);
        if (topLeftYPos + (3 * size) > gameBoard.getHeight()) topLeftYPos = gameBoard.getHeight() - (3 * size);

    }


}
