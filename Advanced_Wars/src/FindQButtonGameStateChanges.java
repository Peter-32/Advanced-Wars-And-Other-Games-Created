/**
 * Created by Peter on 8/21/2016.
 */
public class FindQButtonGameStateChanges {

    GameBoard gameBoard;

    FindQButtonGameStateChanges(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        // update state of tiles to show red tiles

        gameBoard.updateDisplayQAttackSquares();

    } // END OF CONSTRUCTOR

    //// METHODS

    /*
    The attack squares are painted with red tiles.  They are where a unit can attack while standing at a given tile.
     */


}
