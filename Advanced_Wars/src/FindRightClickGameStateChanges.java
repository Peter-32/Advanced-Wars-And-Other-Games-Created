import java.util.Iterator;

public class FindRightClickGameStateChanges {

    GameBoard gameBoard;
    int xClicked;
    int yClicked;
    int cursorXTile;
    int cursorYTile;
    int clickedXTile;
    int clickedYTile;
    boolean isAMeleeMilitaryUnitSelected;
    boolean isARangedMilitaryUnitSelected;
    MilitaryUnit selectedMilitaryUnit = null;
    MilitaryUnit clickedMilitaryUnit = null;


    FindRightClickGameStateChanges(GameBoard gameBoard, int xClicked, int yClicked) {

        this.gameBoard = gameBoard;

        // pull snapshots of variables

        this.xClicked = xClicked;
        this.yClicked = yClicked;
        clickedXTile = gameBoard.findXTileClickedOn();
        clickedYTile = gameBoard.findYTileClickedOn();
        cursorXTile = gameBoard.getCursorMapTileX();
        cursorYTile = gameBoard.getCursorMapTileY();
        this.isAMeleeMilitaryUnitSelected = gameBoard.isAMeleeMilitaryUnitSelected();
        this.isARangedMilitaryUnitSelected = gameBoard.isARangedMilitaryUnitSelected();
        this.selectedMilitaryUnit = gameBoard.getSelectedMilitaryUnit();  // this can be null
        this.clickedMilitaryUnit = gameBoard.getMilitaryUnitAtXYTile(clickedXTile, clickedYTile);  // This can be null

        // leave early if no units are selected

        if (selectedMilitaryUnit == null) { return; }

        // if the unit can still move this turn:
        // check if the player clicked on a tile that is true in the movable grid while a unit is selected (black tiles)
        // Make sure A is not pressed.

        if (!selectedMilitaryUnit.isMovedThisTurn() &&
                !gameBoard.isPressedTheAKeyWhileUnitSelected()) {
            militaryUnitMovementOrMergeCommand();
        }

        // if the unit can still attack this turn:
        // check if the player clicked on an enemy unit inside the attack grid and make sure a unti is currently under the cursor
        // Make sure A is pressed.

        if (!selectedMilitaryUnit.isAttackedThisTurn() &&
                gameBoard.isPressedTheAKeyWhileUnitSelected()) {
            militaryUnitAttackCommand();
        }

    } // END OF CONSTRUCTOR

    /*
    check if the player clicked on a tile that is true in the movable grid while a unit is selected (black tiles)
     */

    void militaryUnitMovementOrMergeCommand() {

        MilitaryUnit clickedOnMilitaryUnit = selectedMilitaryUnit = gameBoard.getMilitaryUnitAtXYTile(clickedXTile, clickedYTile);
        boolean[][] tempCurrentMoveableChoices = gameBoard.cloneCurrentMoveableChoicesGrid();
        int defenseStars = 0;

        // move if 1) The tile is a movable choice based on distance
        //         2) it is the unit's turn
        //         3) the new space is not occupied

        if (tempCurrentMoveableChoices[clickedYTile][clickedXTile] &&
                selectedMilitaryUnit.getColor() == gameBoard.getTurnColor() &&
                clickedOnMilitaryUnit == null) {

            selectedMilitaryUnit.setXTile(clickedXTile);
            selectedMilitaryUnit.setYTile(clickedYTile);
            defenseStars = gameBoard.defenseStarsAtXYTile(clickedXTile, clickedYTile);
            selectedMilitaryUnit.setDefenseStars(defenseStars);
            selectedMilitaryUnit.setMovedThisTurn(true);
            gameBoard.setCursorMapTileX(clickedXTile);
            gameBoard.setCursorMapTileY(clickedYTile);
            gameBoard.setBuyingFromBasePossible(false); // this can be reset next time a left click happens

            // If artillery moves then it can't attack the same turn

            if (selectedMilitaryUnit.getMilitaryUnitType() == GameBoard.MilitaryUnitType.ARTILLERY) {
                selectedMilitaryUnit.setAttackedThisTurn(true);
            }

            // Now check if an infantry or mech have just moved onto a gray city, then begin capturing it.
            tryCapturingCity();

            // These don't need to be updated because paint is turned off and move is turned off.  The attack squares
            // already seem updated
              //gameBoard.updateCurrentMoveableChoicesGrid();
              //gameBoard.updateAttackSquares();

        }

        // merge military units if 1) The tile is a movable choice based on distance
        //                         2) it is the unit's turn
        //                         3) the new space has a unit
        //                         4) the new space isn't the initial position of the selected unit
        //                         5) the new space or this current unit is damaged
        //                         6) both units are the same type of units



        if (tempCurrentMoveableChoices[clickedYTile][clickedXTile] && // #1
                selectedMilitaryUnit.getColor() == gameBoard.getTurnColor() && // #2
                clickedOnMilitaryUnit != null && // #3
                (clickedXTile != cursorXTile || clickedYTile != cursorYTile)) {  // #4
            if ((clickedOnMilitaryUnit.getHealth() != 100 || selectedMilitaryUnit.getHealth() != 100) && // #5
                    clickedOnMilitaryUnit.getMilitaryUnitType() == selectedMilitaryUnit.getMilitaryUnitType()) { // #6
                selectedMilitaryUnit.setXTile(clickedXTile);
                selectedMilitaryUnit.setYTile(clickedYTile);
                defenseStars = gameBoard.defenseStarsAtXYTile(clickedXTile, clickedYTile);
                selectedMilitaryUnit.setDefenseStars(defenseStars);
                selectedMilitaryUnit.setMovedThisTurn(true);
                selectedMilitaryUnit.setAttackedThisTurn(true); // also don't allow attacks this turn
                gameBoard.setCursorMapTileX(clickedXTile);
                gameBoard.setCursorMapTileY(clickedYTile);
                gameBoard.setBuyingFromBasePossible(false); // this can be reset next time a left click happens

                // merge health and delete the old one
                selectedMilitaryUnit.setHealth(selectedMilitaryUnit.getHealth() + clickedOnMilitaryUnit.getHealth());
                gameBoard.removeMilitaryUnits(clickedOnMilitaryUnit);

                // These don't need to be updated because paint is turned off and move is turned off.  The attack squares
                // already seem updated
                //gameBoard.updateCurrentMoveableChoicesGrid();
                //gameBoard.updateAttackSquares();
            } //END OF INNER IF

        } //END OF IF

    } // END OF militaryUnitMovementOrMergeCommand METHOD

    /*
    check if the player clicked on an enemy unit inside the attack grid and make sure a unti is currently under the cursor
     */

    void militaryUnitAttackCommand() {

        if (clickedMilitaryUnit == null) { return; } // we return if no unit was clicked on
        if (clickedMilitaryUnit.getColor() == gameBoard.getTurnColor()) { return; } // we return if the unit isn't another team color

        boolean[][] tempCurrentAttackChoices = gameBoard.cloneCurrentAttackChoicesGrid();

        // attack if 1) possible attack option
        //           2) it is the unit's turn
        //           3) the other unit is not their own color

        if (tempCurrentAttackChoices[clickedYTile][clickedXTile] &&
                selectedMilitaryUnit.getColor() == gameBoard.getTurnColor() &&
                clickedMilitaryUnit.getColor() != gameBoard.getTurnColor()) {

            selectedMilitaryUnit.setMovedThisTurn(true);
            selectedMilitaryUnit.setAttackedThisTurn(true);
            selectedMilitaryUnit.initiateAttack(clickedMilitaryUnit);

            checkForDestroyedUnits();

        }

    }

    /*
    Since units can't access GameBoard methods, the health of all units is checked here so they can be removed from the gameboard
     */

    void checkForDestroyedUnits() {

        MilitaryUnit currentMilitaryUnit = null;

        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = gameBoard.militaryUnitsIterator();
        while (tempMilitaryUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempMilitaryUnitsIterator.next();
            if (currentMilitaryUnit.getHealth() < 0) {
                gameBoard.removeMilitaryUnits(currentMilitaryUnit);
            }

        }

    } // END OF checkForDestroyedUnits METHOD

    void tryCapturingCity() {

    }

} // END OF FindRightClickGameStateChanges CLASS