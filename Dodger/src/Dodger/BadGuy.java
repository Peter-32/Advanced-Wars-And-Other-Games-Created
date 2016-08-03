package Dodger;

/**
 * Created by Peter on 8/1/2016.
 */
public class BadGuy {

    // FIELDS

    private int topLeftXPos, topLeftYPos, xVelocity, yVelocity, size;

    // CONSTRUCTOR

    public BadGuy(int topLeftXPos, int topLeftYPos, int xVelocity, int yVelocity, int size) {
        this.topLeftXPos = topLeftXPos;
        this.topLeftYPos = topLeftYPos;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.size = size;

    } // END OF BadGuy CONSTRUCTOR

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



}
