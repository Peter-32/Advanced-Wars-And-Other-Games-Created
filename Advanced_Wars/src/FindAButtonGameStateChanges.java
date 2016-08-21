/**
 * Created by Peter on 8/21/2016.
 */
public class FindAButtonGameStateChanges {

    GameBoard gameBoard;
    MilitaryUnit selectedUnit;

    FindAButtonGameStateChanges(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        selectedUnit = gameBoard.getSelectedMilitaryUnit();

        // update state of tiles to show red tiles

        gameBoard.updateAttackSquares();

    } // END OF CONSTRUCTOR

    //// METHODS

    /*
    The attack squares are painted with red tiles.  They are where a unit can attack while standing at a given tile.
     */


}
