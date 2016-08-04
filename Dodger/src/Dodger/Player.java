package Dodger;

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

    void move(int xDirection, int yDirection) {

        // allows you to move and move off screen

        topLeftXPos -= xDirection;
        topLeftYPos -= yDirection;

        // places you back on the screen if moving off the screen

        if (topLeftXPos < 0) topLeftXPos = 0;
        if (topLeftYPos < 0) topLeftYPos = 0;
        if (topLeftXPos + size > gameBoard.getWidth()) topLeftXPos = gameBoard.getWidth() - size;
        if (topLeftYPos + size > gameBoard.getHeight()) topLeftYPos = gameBoard.getWidth() - size;

    }


}
