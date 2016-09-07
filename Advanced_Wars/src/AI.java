/**
 * Created by Peter on 9/5/2016.
 */
public class AI {

    GameBoard gameBoard;
    int xTileOfInterest = 0;
    int yTileOfInterest = 0;


    public AI(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void executeEntireTurn() {

        gameBoard.setToldAIToExecuteTurn(true);


        moveEachUnit();
        buildFromEachBase();

        clickEndTurn();

    }




    // Rules:
    /*
    Never move units onto bases
    Build units after all units moved
    Use point system to decide which moves to make.  Move one unit at a time based on the highest point from moving that unit.
    Get highest points from building units;  You get good points for infintry at first, then diminishing returns
    based on how many gray are left, and how the enemy units vs your units total spend compares.  If the computer is behind and it
    is after a few turns, they will want non-infantry units to fight back.

    Higher points for taking out enemy
    Higher points for starting and ending capturing
    Higher points for moving closer to the enemy
    Lower points for moving into artillery strike range


    notes:
    get a total tank, infantry, mech, artillery counter based on when they are destroyed and created (RED and BLUE)
    Get a Matchup chart to calculate the $$ Overall gain over the opponent.  IE. a fight one loses 400$ worth and other loses 200$ worth.
        One of them is 200$ ahead; the computer can use these for deciding who to attack who; and avoiding bad attacks.



     */







    private void moveEachUnit() {

    }


    private void buildFromEachBase() {

    }

    private void calculateDeltaPointsMovement() {

    }

    private void calculateDeltaPointsAttack() {

    }


    private void decideWhatWillBeDoneThisTurn() {

        // if first turn

        if (gameBoard.getTurnNumber() == 1) {
            UpdateTileInterestLocationOfClosestActiveBaseToGrayBuilding();
            purchaseUnit(GameBoard.MilitaryUnitType.INFANTRY, xTileOfInterest, yTileOfInterest);
        }


        // number of blue infantry vs number of gray buildings









    }

    private void purchaseUnit(GameBoard.MilitaryUnitType militaryUnitType, int xTile, int yTile) {

    }

    private void clickEndTurn() {
        gameBoard.setXClicked(144);
        gameBoard.setYClicked(119);
        gameBoard.setClickType(1);
    }

    private void UpdateTileInterestLocationOfClosestActiveBaseToGrayBuilding() {
        int xTile = 0;
        int yTile = 0;






        int xTileOfInterest = 0;
        int yTileOfInterest = 0;
    }




}
