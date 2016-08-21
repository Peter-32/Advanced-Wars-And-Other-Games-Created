/**
 * Created by Peter on 8/18/2016.
 */
///// Add to the gameboard a list of the redUnits and blueUnits

abstract class MilitaryUnit {
    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public int getYTile() {
        return yTile;
    }

    public void setYTile(int yTile) {
        this.yTile = yTile;
    }

    public int getXTile() {
        return xTile;
    }

    public void setXTile(int xTile) {
        this.xTile = xTile;
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isMovedThisTurn() {
        return movedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    public boolean isAttackedThisTurn() {
        return attackedThisTurn;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.attackedThisTurn = attackedThisTurn;
    }

    public int getDisplayAndCaptureHealth() {
        return displayAndCaptureHealth;
    }

    public void setDisplayAndCaptureHealth(int displayAndCaptureHealth) {
        this.displayAndCaptureHealth = displayAndCaptureHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public GameBoard.TerrainTile getCurrentTerrain() {
        return currentTerrain;
    }

    public void setCurrentTerrain(GameBoard.TerrainTile currentTerrain) {
        this.currentTerrain = currentTerrain;
    }

    public GameBoard.MilitaryUnitType getMilitaryUnitType() {

        switch(this.getClass().getName()) {
            case "Infantry":
                return GameBoard.MilitaryUnitType.INFANTRY;
            case "Mech":
                return GameBoard.MilitaryUnitType.MECH;
            case "Artillery":
                return GameBoard.MilitaryUnitType.ARTILLERY;
            case "Tank":
                return GameBoard.MilitaryUnitType.TANK;
            default:
                return null;

        }

    } // END OF getMilitaryUnitType GETTER

    private char color;
    private int xTile;
    private int yTile;
    private int health = 100;
    private int displayAndCaptureHealth = 10;
    private boolean selected;
    private boolean movedThisTurn = false;
    private boolean attackedThisTurn = false;
    private GameBoard.TerrainTile currentTerrain;

    MilitaryUnit(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
                 GameBoard.TerrainTile currentTerrain) {
        this.color = color;
        this.xTile = xTile;
        this.yTile = yTile;
        this.selected = selected;
        this.movedThisTurn = movedThisTurn;
        this.attackedThisTurn = attackedThisTurn;
        this.currentTerrain = currentTerrain;
    }

    abstract void attack(MilitaryUnit enemyUnit);

}

class Infantry extends MilitaryUnit {

    Infantry(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
             GameBoard.TerrainTile currentTerrain) {
        super(color, xTile, yTile, selected, movedThisTurn, attackedThisTurn, currentTerrain);
    }

    void attack(MilitaryUnit enemyUnit) {
        GameBoard.MilitaryUnitType enemyUnitType = enemyUnit.getMilitaryUnitType();
        //
        int randomNumber = (int) (Math.random() * 10);
        double baseDamage;

        switch (enemyUnitType) {

            case INFANTRY:
                baseDamage = 0.55;
                break;
            case MECH:
                baseDamage = 0.45;
                break;
            case ARTILLERY:
                baseDamage = 0.15;
                break;
            case TANK:
                baseDamage = 0.05;
                break;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

}

class Mech extends MilitaryUnit {

    Mech(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
         GameBoard.TerrainTile currentTerrain) {
        super(color, xTile, yTile, selected, movedThisTurn, attackedThisTurn, currentTerrain);
    }

    void attack(MilitaryUnit enemyUnit) {
        GameBoard.MilitaryUnitType enemyUnitType = enemyUnit.getMilitaryUnitType();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

}

class Artillery extends MilitaryUnit {

    Artillery(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
              GameBoard.TerrainTile currentTerrain) {
        super(color, xTile, yTile, selected, movedThisTurn, attackedThisTurn, currentTerrain);
    }

    void attack(MilitaryUnit enemyUnit) {
        GameBoard.MilitaryUnitType enemyUnitType = enemyUnit.getMilitaryUnitType();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

}

class Tank extends MilitaryUnit {
    private char color;

    Tank(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
         GameBoard.TerrainTile currentTerrain) {
        super(color, xTile, yTile, selected, movedThisTurn, attackedThisTurn, currentTerrain);
    }

    void attack(MilitaryUnit enemyUnit) {
        GameBoard.MilitaryUnitType enemyUnitType = enemyUnit.getMilitaryUnitType();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

}