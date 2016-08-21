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

        updateAttackSquares();

    } // END OF CONSTRUCTOR

    //// METHODS

    /*
    The attack squares are painted with red tiles.  They are where a unit can attack while standing at a given tile.
     */
    void updateAttackSquares() {

        if (gameBoard.isAMeleeMilitaryUnitSelected()) {
            updateMeleeAttackSquares();
        } else if (gameBoard.isARangedMilitaryUnitSelected()) {
            updateRangedAttackSquares();
        }
    } // END OF updateAttackSquares METHOD

    void updateMeleeAttackSquares() {

        // variables

        int xTile, yTile;

        // reset

        gameBoard.resetCurrentAttackChoicesGrid();

        // if no unit is selected then return.  Probably unnecessary, but shouldn't hurt to include this.

        if (!gameBoard.isAMilitaryUnitSelected()) {
            return;
        }

        // find the current x, y position

        xTile = gameBoard.getSelectedMilitaryUnit().getXTile();
        yTile = gameBoard.getSelectedMilitaryUnit().getYTile();

        if (xTile != 0) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 1, yTile, true);
        }

        if (xTile != gameBoard.getMapTileWidth() - 1) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 1, yTile, true);
        }

        if (yTile != 0) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile, yTile - 1, true);
        }

        if (yTile != gameBoard.getMapTileHeight() - 1) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile, yTile + 1, true);
        }

    } // END OF updateMeleeAttackSquares METHOD

    void updateRangedAttackSquares() {

        // variables

        int xTile, yTile;

        // reset

        gameBoard.resetCurrentAttackChoicesGrid();

        // if no unit is selected then return.  Probably unnecessary, but shouldn't hurt to include this.

        if (!gameBoard.isAMilitaryUnitSelected()) {
            return;
        }

        // find the current x, y position

        xTile = gameBoard.getSelectedMilitaryUnit().getXTile();
        yTile = gameBoard.getSelectedMilitaryUnit().getYTile();

        // furthest left

        if (xTile >= 3) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 3, yTile, true);
        }

        // furthest up

        if (yTile >= 3) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile, yTile - 3, true);
        }

        // furthest right

        if (xTile <= gameBoard.getMapTileWidth() - 4) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile, yTile + 3, true);
        }

        // furthest down

        if (yTile <= gameBoard.getMapTileHeight() - 4) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 3, yTile, true);
        }

        // left 2

        if (xTile >= 2) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 2, yTile, true);
        }

        // up 2

        if (yTile >= 2) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile, yTile - 2, true);
        }

        // right 2

        if (xTile <= gameBoard.getMapTileWidth() - 3) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile, yTile + 2, true);
        }

        // down 2

        if (yTile <= gameBoard.getMapTileHeight() - 3) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 2, yTile, true);
        }

        // diagonal up 1 / left 1

        if (xTile >= 1 && yTile >= 1) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 1, yTile - 1, true);
        }

        // diagonal up 1 / right 1

        if (xTile <= gameBoard.getMapTileWidth() - 2 && yTile >= 1) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 1, yTile - 1, true);
        }

        // diagonal down 1 / left 1

        if (xTile >= 1 && yTile <= gameBoard.getMapTileWidth() - 2) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 1, yTile + 1, true);
        }

        // diagonal down 1 / right 1

        if (xTile <= gameBoard.getMapTileWidth() - 2 && yTile <= gameBoard.getMapTileWidth() - 2) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 1, yTile + 1, true);
        }
        
        // diagonal up 2 / left 1

        if (xTile >= 1 && yTile >= 2) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 1, yTile - 2, true);
        }

        // diagonal up 1 / left 2

        if (xTile >= 2 && yTile >= 1) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 2, yTile - 1, true);
        }

        // diagonal up 2 / right 1

        if (xTile <= gameBoard.getMapTileWidth() - 2 && yTile >= 2) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 1, yTile - 2, true);
        }

        // diagonal up 1 / right 2

        if (xTile <= gameBoard.getMapTileWidth() - 3 && yTile >= 1) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 2, yTile - 1, true);
        }

        // diagonal down 2 / left 1

        if (xTile >= 1 && yTile <= gameBoard.getMapTileHeight() - 3) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 1, yTile + 2, true);
        }

        // diagonal down 1 / left 2

        if (xTile >= 2 && yTile <= gameBoard.getMapTileHeight() - 2) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile - 2, yTile + 1, true);
        }

        // diagonal down 2 / right 1

        if (xTile <= gameBoard.getMapTileWidth() - 2 && yTile <= gameBoard.getMapTileHeight() - 3) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 1, yTile + 2, true);
        }

        // diagonal down 1 / right 2

        if (xTile <= gameBoard.getMapTileWidth() - 3 && yTile <= gameBoard.getMapTileHeight() - 2) {
            gameBoard.updateCurrentAttackChoicesGrid(xTile + 2, yTile + 1, true);
        }





    }

}
