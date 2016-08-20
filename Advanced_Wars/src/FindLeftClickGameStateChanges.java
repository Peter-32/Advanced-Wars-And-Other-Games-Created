import java.util.Iterator;

/*
 Movement: left click to select, right click to move.  Don't move if A is held.
 Attacking melee: hold A and right click on the movement tile, then still while holding A right click on the attack tile, if valid do the movement and attack
 ATtacking range: hold A and right click on the enemy unit, don't preform a movement if A is held no matter what.
 */








public class FindLeftClickGameStateChanges {

    GameBoard gameBoard;
    int xClicked;
    int yClicked;
    int selectedXTile;
    int selectedYTile;

    FindLeftClickGameStateChanges(GameBoard gameBoard, int xClicked, int yClicked) {

        this.gameBoard = gameBoard;

        // Pull in the snapshot click location (x, y)

        this.xClicked = xClicked;
        this.yClicked = yClicked;
        selectedXTile = gameBoard.findXTileClickedOn();
        selectedYTile = gameBoard.findYTileClickedOn();

        // check if end turn was clicked on

        checkIfEndTurnWasClickedOn();

        // check if the map is clicked on, then the game will update the cursor location, otherwise no update is wanted.

        if (selectedXTile != -1 && selectedYTile != -1) {
            gameBoard.setCursorMapTileX(selectedXTile);    // SETTER USED
            gameBoard.setCursorMapTileY(selectedYTile);    // SETTER USED
        }

        // check if the player clicked on a military unit, regardless of if it is friendly or an enemy

        updateMilitaryUnitSelectionState();

        // check if a cursor is over a base and no military unit (friendly or enemy)
        //   is on that base, then update the boolean saying buying is possible

        checkIfBuyingFromBaseIsPossible();

        // If buying is possible, check to see if the player clicked on one while the boolean above is true.

        if (gameBoard.isBuyingFromBasePossible()) {
            purchaseUnitIfClickedOn();
        }

        // change the state of currentMoveableChoices[][].  Won't change unless a unit is selected.

        updateCurrentMoveableChoices();


    }

    void updateMilitaryUnitSelectionState() {
        MilitaryUnit currentMilitaryUnit = null;
        boolean aMilitaryUnitIsSelected = false;
        // loop through allunits; update selected to true or false.
        Iterator<MilitaryUnit> tempUnitsIterator = gameBoard.militaryUnitsIterator();
        while (tempUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempUnitsIterator.next();
            if (gameBoard.getCursorMapTileX() == currentMilitaryUnit.getXTile() &&
                    gameBoard.getCursorMapTileY() == currentMilitaryUnit.getYTile()) {
                currentMilitaryUnit.setSelected(true);    // SETTER USED
                aMilitaryUnitIsSelected = true;
            } else {
                currentMilitaryUnit.setSelected(false);    // SETTER USED
            }
        }

        // if at least one unit is selected then this will be set to true, otherwise it will be set to false.
        gameBoard.setAMilitaryUnitSelected(aMilitaryUnitIsSelected);    // SETTER USED

    } // END OF updateWhichUnitIsSelected METHOD

    void checkIfBuyingFromBaseIsPossible() {

        // check which player turn it is
        // check if a building is selected, otherwise return from method early

        GameBoard.BuildingTile[][] tempBuildingTiles = gameBoard.cloneBuildingTilesGrid();

        switch (gameBoard.getTurnColor()) {
            case 'r':
                if (tempBuildingTiles[gameBoard.getCursorMapTileY()][gameBoard.getCursorMapTileX()]
                        != GameBoard.BuildingTile.RED_BASE) {
                    gameBoard.setBuyingFromBasePossible(false);    // SETTER USED
                    return;
                }
                break;
            case 'b':
                if (tempBuildingTiles[gameBoard.getCursorMapTileY()][gameBoard.getCursorMapTileX()]
                        != GameBoard.BuildingTile.BLUE_BASE) {
                    gameBoard.setBuyingFromBasePossible(false);    // SETTER USED
                    return;
                }
                break;
            default: // otherwise return as well.
                gameBoard.setBuyingFromBasePossible(false);    // SETTER USED
                return;
        }

        // If still inside this method:
        // Check if there are no military units on that square, then buying is possible

        if (gameBoard.isAMilitaryUnitSelected()) {
            gameBoard.setBuyingFromBasePossible(false);    // SETTER USED
        } else {
            gameBoard.setBuyingFromBasePossible(true);    // SETTER USED
        }
    } // END OF checkIfBuyingFromBaseIsPossible METHOD

    /*
    Checks if the player clicks on a new unit button with their same color in the menu, in order to buy the unit.
     */
    void purchaseUnitIfClickedOn() {
        if (!gameBoard.isBuyingFromBasePossible()) { return;}

        switch (gameBoard.getTurnColor()) {
            case 'r':
                if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY(),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('r', "Infantry");
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 1 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('r', "Mech");
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 2 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('r', "Artillery");
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 3 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('r', "Tank");
                }
                break;
            case 'b':
                if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 4 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('b', "Infantry");
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 5 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('b', "Mech");
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 6 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('b', "Artillery");
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 7 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('b', "Tank");
                }
                break;

        } // END OF SWITCH

    } // END OF purchaseUnitIfClickedOn METHOD

    /*
     given the location of a button, returns true or false if it was clicked on.
     Uses the class fields xClicked and yClicked
     */

    boolean checkIfBtnPressed(int topLeftX, int topLeftY, int width, int height) {

        return (xClicked >= topLeftX &&
                xClicked <= topLeftX + width &&
                yClicked >= topLeftY &&
                yClicked <= topLeftY + height);

    } // END OF checkIfBtnPressed METHOD

    /*
    This unit was clicked on in the game menu for a purchase.  If there is enough money in the player's bank then
    the unit should go onto the board.  Add the unit to the gamebord unit list, update "buying possible" to false,
    update unit selected to true.
     */

    void purchaseMilitaryUnitIfEnoughFunds(char playerColor, String militaryUnit) {

        if (playerColor == 'r') {
            switch (militaryUnit) {
                case "Infantry":
                    if (gameBoard.getRedPlayerBank() >= 1000) {
                        gameBoard.setRedPlayerBank(gameBoard.getRedPlayerBank() - 1000);
                        gameBoard.addMilitaryUnits(new Infantry('r', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true));
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.setAMilitaryUnitSelected(true);
                    }
                    break;
                case "Mech":
                    if (gameBoard.getRedPlayerBank() >= 3000) {
                        gameBoard.setRedPlayerBank(gameBoard.getRedPlayerBank() - 3000);
                        gameBoard.addMilitaryUnits(new Mech('r', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true));
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.setAMilitaryUnitSelected(true);
                    }
                    break;
                case "Artillery":
                    if (gameBoard.getRedPlayerBank() >= 6000) {
                        gameBoard.setRedPlayerBank(gameBoard.getRedPlayerBank() - 6000);
                        gameBoard.addMilitaryUnits(new Artillery('r', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true));
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.setAMilitaryUnitSelected(true);
                    }
                    break;
                case "Tank":
                    if (gameBoard.getRedPlayerBank() >= 7000) {
                        gameBoard.setRedPlayerBank(gameBoard.getRedPlayerBank() - 7000);
                        gameBoard.addMilitaryUnits(new Tank('r', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true));
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.setAMilitaryUnitSelected(true);
                    }
                    break;
            }
        } else if (playerColor == 'b') {
            switch (militaryUnit) {
                case "Infantry":
                    if (gameBoard.getBluePlayerBank() >= 1000) {
                        gameBoard.setBluePlayerBank(gameBoard.getBluePlayerBank() - 1000);
                        gameBoard.addMilitaryUnits(new Infantry('b', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true));
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.setAMilitaryUnitSelected(true);
                    }
                    break;
                case "Mech":
                    if (gameBoard.getBluePlayerBank() >= 3000) {
                        gameBoard.setBluePlayerBank(gameBoard.getBluePlayerBank() - 3000);
                        gameBoard.addMilitaryUnits(new Mech('b', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true));
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.setAMilitaryUnitSelected(true);
                    }
                    break;
                case "Artillery":
                    if (gameBoard.getBluePlayerBank() >= 6000) {
                        gameBoard.setBluePlayerBank(gameBoard.getBluePlayerBank() - 6000);
                        gameBoard.addMilitaryUnits(new Artillery('b', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true));
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.setAMilitaryUnitSelected(true);
                    }
                    break;
                case "Tank":
                    if (gameBoard.getBluePlayerBank() >= 7000) {
                        gameBoard.setBluePlayerBank(gameBoard.getBluePlayerBank() - 7000);
                        gameBoard.addMilitaryUnits(new Tank('b', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true));
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.setAMilitaryUnitSelected(true);
                    }
                    break;
            }
        }

    } // END OF purchaseMilitaryUnitIfEnoughFunds METHOD

    /*
    This method will check if the button for ending the turn has been clicked.  If so, it will change a game
    state that tells other classes which player has their turn.
     */

    void checkIfEndTurnWasClickedOn() {

        // checking end turn button

        if(checkIfBtnPressed(gameBoard.getEndTurnBtnStartX(), gameBoard.getEndTurnBtnStartY(),
                gameBoard.getEndTurnBtnWidth(), gameBoard.getEndTurnBtnHeight())) {

            if (gameBoard.getTurnColor() == 'r') {

                gameBoard.setTurnColor('b');
                System.out.println("It is blue's turn");

            } else {

                gameBoard.setTurnColor('r');
                System.out.println("It is red's turn");

            }

        }

    } // END OF checkIfEndTurnWasClickedOn METHOD

    /*
     update the grid of which tiles are currently movable.
     */

    void updateCurrentMoveableChoices() {

        MilitaryUnit selectedMilitaryUnit = null;
        GameBoard.MilitaryUnitType militaryUnitType;

        // if no unit is selected then return

        if (!gameBoard.isAMilitaryUnitSelected()) {
            return;
        }

        // get the unit type of the unit selected

        selectedMilitaryUnit = gameBoard.getSelectedMilitaryUnit();
        militaryUnitType = selectedMilitaryUnit.getMilitaryUnitType();

        // call the graph search function via a GameBoard method.  The graph is on the gameBoard

        gameBoard.runNodesAccessibleFromLocationWithSteps()

        //////gameBoard.runNodesAccessibleFromLocationWithSteps()



        //nodesAccessibleFromLocationWithSteps()

    }

} // END OF FindLeftClickGameStateChanges  CLASS