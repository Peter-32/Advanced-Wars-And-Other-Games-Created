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
        this.selectedMilitaryUnit = gameBoard.getSelectedMilitaryUnit();
        this.clickedMilitaryUnit = gameBoard.getMilitaryUnitAtXYTile(clickedXTile, clickedYTile);  // This can be null

        // assign a value to clickedMilitaryUnit:


        //gameBoard.unit



        //this.clickedMilitaryUnit =






        // leave early if no units are selected

        if (selectedMilitaryUnit == null) { return; }

        // if the unit can still move this turn:
        // check if the player clicked on a tile that is true in the movable grid while a unit is selected (black tiles)

        if (!selectedMilitaryUnit.isMovedThisTurn()) {
            militaryUnitMovementCommand();
        }

        // if the unit can still attack this turn:
        // check if the player clicked on an enemy unit inside the attack grid and make sure a unti is currently under the cursor

        if (!selectedMilitaryUnit.isAttackedThisTurn()) {
            militaryUnitAttackCommand();
        }

    } // END OF CONSTRUCTOR

    /*
    check if the player clicked on a tile that is true in the movable grid while a unit is selected (black tiles)
     */

    void militaryUnitMovementCommand() {

        boolean[][] tempCurrentMoveableChoices = gameBoard.cloneCurrentMoveableChoicesGrid();

        // if the selected tile is currently movable

        if (tempCurrentMoveableChoices[selectedYTile][selectedXTile]) {

            selectedMilitaryUnit.setXTile(selectedXTile);
            selectedMilitaryUnit.setYTile(selectedYTile);
            selectedMilitaryUnit.setMovedThisTurn(true);
            gameBoard.setCursorMapTileX(selectedXTile);
            gameBoard.setCursorMapTileY(selectedYTile);
            gameBoard.setBuyingFromBasePossible(false);

            // If artillery moves then it can't attack the same turn

            if (selectedMilitaryUnit.getMilitaryUnitType() == GameBoard.MilitaryUnitType.ARTILLERY) {
                selectedMilitaryUnit.setAttackedThisTurn(true);
            }

            // These don't need to be updated because paint is turned off and move is turned off.  The attack squares
            // already seem updated
              //gameBoard.updateCurrentMoveableChoicesGrid();
              //gameBoard.updateAttackSquares();

        }

    } // END OF militaryUnitMovementCommand METHOD

    /*
    check if the player clicked on an enemy unit inside the attack grid and make sure a unti is currently under the cursor
     */

    void militaryUnitAttackCommand() {

        boolean[][] tempCurrentAttackChoices = gameBoard.cloneCurrentAttackChoicesGrid();

        if (tempCurrentAttackChoices[selectedYTile][selectedXTile]) {



            selectedMilitaryUnit.setMovedThisTurn(true);
            //selectedMilitaryUnit.attack
            //////////////////////// CAN ONLY ATTACK THE OTHER COLOR!!!!!!!!!!!!!!!!
        }



    }


} // END OF FindRightClickGameStateChanges CLASS