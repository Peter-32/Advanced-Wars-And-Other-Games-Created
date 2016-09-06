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

        decideWhatWillBeDoneThisTurn();

        clickEndTurn();

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
