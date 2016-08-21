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

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {

        // 100 is the highest health for all military units

        this.health = Math.min(health, 100);

        // If health is less than 0 another area of the program removes it from the game

        // update the display and capture integer health amount.  Add one to the health and divide by 10, and round down.
        // the value is at most 10.

        int tempDisplayAndCaptureHealth = (int) ((health + 10) / 10);
        int newDisplayAndCaptureHealth = Math.min(10, tempDisplayAndCaptureHealth);
        setDisplayAndCaptureHealth(newDisplayAndCaptureHealth);
    }

    void setCaptureProgress(int captureProgress) {
        this.captureProgress = captureProgress;
    }

    int getCaptureProgress() {
        return captureProgress;
    }

    public int getDefenseStars() {
        return defenseStars;
    }

    public void setDefenseStars(int defenseStars) {
        this.defenseStars = defenseStars;
    }

    public int getCost() {
        return cost;
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
    private double health = 100;
    private int displayAndCaptureHealth = 10;
    private boolean selected;
    private boolean movedThisTurn = false;
    private boolean attackedThisTurn = false;
    private int defenseStars;
    protected boolean isRanged;
    protected int cost;
    protected int captureProgress; // getting 20 capture points gains a new building for your team

    MilitaryUnit(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
                 int defenseStars) {
        this.color = color;
        this.xTile = xTile;
        this.yTile = yTile;
        this.selected = selected;
        this.movedThisTurn = movedThisTurn;
        this.attackedThisTurn = attackedThisTurn;
        this.defenseStars = defenseStars;
    }

    abstract void attack(MilitaryUnit enemyUnit);

    public void initiateAttack(MilitaryUnit enemyUnit) {

        // attack enemy.

        attack(enemyUnit);

        // enemy returns fire after damage is done to them.
        // never counter attack if one of the units is ranged

        if (!enemyUnit.isRanged && !this.isRanged) {
            enemyUnit.attack(this);
        }
    }

}

class Infantry extends MilitaryUnit {

    Infantry(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
             int defenseStars) {
        super(color, xTile, yTile, selected, movedThisTurn, attackedThisTurn, defenseStars);
        isRanged = false;
        this.cost = 1000;
    }

    void attack(MilitaryUnit enemyUnit) {
        GameBoard.MilitaryUnitType enemyUnitType = enemyUnit.getMilitaryUnitType();
        int randomNumber = (int) (Math.random() * 10);
        double baseDamage;
        double attackerDamageOutput;

        // get health / 10, which is similar to display health, but can be fractional
        // multiply this by the stars ie. 2 stars 100 health gives you 20 defense
        // divide this by 100 to get .2;  Then subtract from 1 to get 0.8 multiplier

        double defenseStarsMultiplier = 1 - (((enemyUnit.getHealth() / 10) * enemyUnit.getDefenseStars()) / 100);

        switch (enemyUnitType) {

            case INFANTRY:
                baseDamage = 55;
                break;
            case MECH:
                baseDamage = 45;
                break;
            case ARTILLERY:
                baseDamage = 15;
                break;
            case TANK:
                baseDamage = 5;
                break;
            default:
                baseDamage = 0;
                break;
        }

        attackerDamageOutput = (baseDamage + randomNumber) *   // base damage plus random 0 to 9 extra damage
                (this.getHealth() / 100) *  // pct health
                defenseStarsMultiplier;  // if 4 defenseStars and 10 health

        enemyUnit.setHealth(enemyUnit.getHealth() - attackerDamageOutput);

    }

}

class Mech extends MilitaryUnit {

    Mech(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
         int defenseStars) {
        super(color, xTile, yTile, selected, movedThisTurn, attackedThisTurn, defenseStars);
        isRanged = false;
        this.cost = 1000;
    }

    void attack(MilitaryUnit enemyUnit) {
        GameBoard.MilitaryUnitType enemyUnitType = enemyUnit.getMilitaryUnitType();
        int randomNumber = (int) (Math.random() * 10);
        double baseDamage;
        double attackerDamageOutput;

        // get health / 10, which is similar to display health, but can be fractional
        // multiply this by the stars ie. 2 stars 100 health gives you 20 defense
        // divide this by 100 to get .2;  Then subtract from 1 to get 0.8 multiplier

        double defenseStarsMultiplier = 1 - (((enemyUnit.getHealth() / 10) * enemyUnit.getDefenseStars()) / 100);

        switch (enemyUnitType) {

            case INFANTRY:
                baseDamage = 65;
                break;
            case MECH:
                baseDamage = 55;
                break;
            case ARTILLERY:
                baseDamage = 70;
                break;
            case TANK:
                baseDamage = 55;
                break;
            default:
                baseDamage = 0;
                break;
        }

        attackerDamageOutput = (baseDamage + randomNumber) *   // base damage plus random 0 to 9 extra damage
                (this.getHealth() / 100) *  // pct health
                defenseStarsMultiplier;  // if 4 defenseStars and 10 health

        enemyUnit.setHealth(enemyUnit.getHealth() - attackerDamageOutput);

    }

}

class Artillery extends MilitaryUnit {

    Artillery(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
              int defenseStars) {
        super(color, xTile, yTile, selected, movedThisTurn, attackedThisTurn, defenseStars);
        isRanged = true;
        this.cost = 1000;
    }

    void attack(MilitaryUnit enemyUnit) {
        GameBoard.MilitaryUnitType enemyUnitType = enemyUnit.getMilitaryUnitType();
        int randomNumber = (int) (Math.random() * 10);
        double baseDamage;
        double attackerDamageOutput;

        // get health / 10, which is similar to display health, but can be fractional
        // multiply this by the stars ie. 2 stars 100 health gives you 20 defense
        // divide this by 100 to get .2;  Then subtract from 1 to get 0.8 multiplier

        double defenseStarsMultiplier = 1 - (((enemyUnit.getHealth() / 10) * enemyUnit.getDefenseStars()) / 100);

        switch (enemyUnitType) {

            case INFANTRY:
                baseDamage = 90;
                break;
            case MECH:
                baseDamage = 85;
                break;
            case ARTILLERY:
                baseDamage = 75;
                break;
            case TANK:
                baseDamage = 70;
                break;
            default:
                baseDamage = 0;
                break;
        }

        attackerDamageOutput = (baseDamage + randomNumber) *   // base damage plus random 0 to 9 extra damage
                (this.getHealth() / 100) *  // pct health
                defenseStarsMultiplier;  // if 4 defenseStars and 10 health

        enemyUnit.setHealth(enemyUnit.getHealth() - attackerDamageOutput);

    }

}

class Tank extends MilitaryUnit {
    private char color;

    Tank(char color, int xTile, int yTile, boolean selected, boolean movedThisTurn, boolean attackedThisTurn,
         int defenseStars) {
        super(color, xTile, yTile, selected, movedThisTurn, attackedThisTurn, defenseStars);
        isRanged = false;
        this.cost = 1000;
    }

    void attack(MilitaryUnit enemyUnit) {
        GameBoard.MilitaryUnitType enemyUnitType = enemyUnit.getMilitaryUnitType();
        int randomNumber = (int) (Math.random() * 10);
        double baseDamage;
        double attackerDamageOutput;

        // get health / 10, which is similar to display health, but can be fractional
        // multiply this by the stars ie. 2 stars 100 health gives you 20 defense
        // divide this by 100 to get .2;  Then subtract from 1 to get 0.8 multiplier


        double defenseStarsMultiplier = 1 - (((enemyUnit.getHealth() / 10) * enemyUnit.getDefenseStars()) / 100);

        switch (enemyUnitType) {

            case INFANTRY:
                baseDamage = 75;
                break;
            case MECH:
                baseDamage = 70;
                break;
            case ARTILLERY:
                baseDamage = 70;
                break;
            case TANK:
                baseDamage = 55;
                break;
            default:
                baseDamage = 0;
                break;
        }

        attackerDamageOutput = (baseDamage + randomNumber) *   // base damage plus random 0 to 9 extra damage
                (this.getHealth() / 100) *  // pct health
                defenseStarsMultiplier;  // if 4 defenseStars and 10 health

        /*       LOGGING FOR ATTACK
        System.out.println("randomNumber = " + randomNumber);
        System.out.println("baseDamage = " + baseDamage);
        System.out.println("this.getHealth() = " + this.getHealth());
        System.out.println("(this.getHealth() / 100) = " + (this.getHealth() / 100));
        System.out.println("defenseStarsMultiplier = " + defenseStarsMultiplier);
        System.out.println("attackerDamageOutput = " + attackerDamageOutput);

        System.out.println("BEFORE: enemyUnit.getHealth() = " + enemyUnit.getHealth());

        */
        enemyUnit.setHealth(enemyUnit.getHealth() - attackerDamageOutput);


    }

}