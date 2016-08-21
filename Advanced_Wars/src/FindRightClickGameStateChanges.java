public class FindRightClickGameStateChanges {

    GameBoard gameBoard;
    int xClicked;
    int yClicked;
    int cursorXTile;
    int cursorYTile;
    int selectedXTile;
    int selectedYTile;
    boolean isAMilitaryUnitSelected;
    boolean isAMeleeMilitaryUnitSelected;
    boolean isARangedMilitaryUnitSelected;


    FindRightClickGameStateChanges(GameBoard gameBoard, int xClicked, int yClicked) {

        this.gameBoard = gameBoard;

        // pull snapshots of variables

        this.xClicked = xClicked;
        this.yClicked = yClicked;
        selectedXTile = gameBoard.findXTileClickedOn();
        selectedYTile = gameBoard.findYTileClickedOn();
        cursorXTile = gameBoard.getCursorMapTileX();
        cursorYTile = gameBoard.getCursorMapTileY();
        this.isAMilitaryUnitSelected = gameBoard.isAMilitaryUnitSelected();
        this.isAMeleeMilitaryUnitSelected = gameBoard.isAMeleeMilitaryUnitSelected();
        this.isARangedMilitaryUnitSelected = gameBoard.isARangedMilitaryUnitSelected();




        // check if the player clicked on a tile that is true in the movable grid while a unit is selected (black tiles)

        militaryUnitMovementCommand();

        // check if the player clicked on an enemy unit inside the attack grid and make sure a unti is currently under the cursor

        militaryUnitAttackCommand();







    } // END OF CONSTRUCTOR

    /*
    check if the player clicked on a tile that is true in the movable grid while a unit is selected (black tiles)
     */

    void militaryUnitMovementCommand() {

        if (!isAMeleeMilitaryUnitSelected) { return; } // return early if no unit is selected

        boolean[][] tempCurrentMoveableChoices = gameBoard.cloneCurrentMoveableChoicesGrid();

        // if the selected tile is currently movable

        if (tempCurrentMoveableChoices[selectedYTile][selectedXTile]) {



        }




        for (boolean[] row : tempCurrentMoveableChoices) {
            for (int j = 0; j < row.length; j++) {
                if (row[j]) {



                }

            }

        } // END OF FOR LOOP

    }

    /*
    check if the player clicked on an enemy unit inside the attack grid and make sure a unti is currently under the cursor
     */

    void militaryUnitAttackCommand() {



    }


} // END OF FindRightClickGameStateChanges CLASS