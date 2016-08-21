import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 Movement: left click to select, right click to move.  Don't move if A is held.
 Attacking melee: hold A and right click on the movement tile, then still while holding A right click on the attack tile, if valid do the movement and attack
 ATtacking range: hold A and right click on the enemy unit, don't preform a movement if A is held no matter what.
 */








public class FindLeftClickGameStateChanges {

    GameBoard gameBoard;
    int xClicked;
    int yClicked;
    int clickedXTile;
    int clickedYTile;

    FindLeftClickGameStateChanges(GameBoard gameBoard, int xClicked, int yClicked) {

        this.gameBoard = gameBoard;

        // Pull in the snapshot click location (x, y)

        this.xClicked = xClicked;
        this.yClicked = yClicked;
        clickedXTile = gameBoard.findXTileClickedOn();
        clickedYTile = gameBoard.findYTileClickedOn();

        // check if end turn was clicked on

        checkIfEndTurnWasClickedOn();

        // check if the map is clicked on, then the game will update the cursor location, otherwise no update is wanted.

        if (clickedXTile != -1 && clickedYTile != -1) {
            gameBoard.setCursorMapTileX(clickedXTile);    // SETTER USED
            gameBoard.setCursorMapTileY(clickedYTile);    // SETTER USED
        }

        // check if the player clicked on a military unit, regardless of if it is friendly or an enemy.
        // also check if the unit is melee or ranged and update the gameboard state

        updateMilitaryUnitSelectionState();

        // check if a cursor is over a base and no military unit (friendly or enemy)
        //   is on that base, then update the boolean saying buying is possible

        checkIfBuyingFromBaseIsPossible();

        // If buying is possible, check to see if the player clicked on one while the boolean above is true.

        if (gameBoard.isBuyingFromBasePossible()) {
            purchaseUnitIfClickedOn();
        }

        // change the state of currentMoveableChoices[][].  Won't change unless a unit is selected.

        updateCurrentMovableChoices();


    } // END OF CONSTRUCTOR

    void updateMilitaryUnitSelectionState() {
        MilitaryUnit currentMilitaryUnit = null;
        boolean isAMilitaryUnitIsSelected = false;
        boolean isAMeleeMilitaryUnitSelected = false;
        boolean isARangedMilitaryUnitSelected = false;
        // loop through allunits; update selected to true or false.
        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = gameBoard.militaryUnitsIterator();
        while (tempMilitaryUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempMilitaryUnitsIterator.next();
            if (gameBoard.getCursorMapTileX() == currentMilitaryUnit.getXTile() &&
                    gameBoard.getCursorMapTileY() == currentMilitaryUnit.getYTile()) {
                currentMilitaryUnit.setSelected(true);    // SETTER USED
                isAMilitaryUnitIsSelected = true;
                switch (currentMilitaryUnit.getMilitaryUnitType()) {

                    case INFANTRY:
                        isAMeleeMilitaryUnitSelected = true;
                        isARangedMilitaryUnitSelected = false;
                        break;
                    case MECH:
                        isAMeleeMilitaryUnitSelected = true;
                        isARangedMilitaryUnitSelected = false;
                        break;
                    case ARTILLERY:
                        isAMeleeMilitaryUnitSelected = false;
                        isARangedMilitaryUnitSelected = true;
                        break;
                    case TANK:
                        isAMeleeMilitaryUnitSelected = true;
                        isARangedMilitaryUnitSelected = false;
                        break;
                }
            } else {
                currentMilitaryUnit.setSelected(false);    // SETTER USED
            }
        }

        // if at least one unit is selected then this will be set to true, otherwise it will be set to false.
        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(
                isAMeleeMilitaryUnitSelected, isARangedMilitaryUnitSelected);

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

        if (gameBoard.getSelectedMilitaryUnit() == null) {
            gameBoard.setBuyingFromBasePossible(true);    // SETTER USED
        } else {
            gameBoard.setBuyingFromBasePossible(false);    // SETTER USED
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
                    purchaseMilitaryUnitIfEnoughFunds('r', GameBoard.MilitaryUnitType.INFANTRY);
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 1 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('r', GameBoard.MilitaryUnitType.MECH);
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 2 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('r', GameBoard.MilitaryUnitType.ARTILLERY);
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 3 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('r', GameBoard.MilitaryUnitType.TANK);
                }
                break;
            case 'b':
                if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 4 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('b', GameBoard.MilitaryUnitType.INFANTRY);
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 5 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('b', GameBoard.MilitaryUnitType.MECH);
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 6 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('b', GameBoard.MilitaryUnitType.ARTILLERY);
                } else if (checkIfBtnPressed(gameBoard.getPurchaseBtnsStartX(),
                        gameBoard.getPurchaseBtnsStartY() + 7 * (gameBoard.getPurchaseBtnsHeight() + gameBoard.getPurchaseBtnsTopMargin()),
                        gameBoard.getPurchaseBtnsWidth(), gameBoard.getPurchaseBtnsHeight())) {
                    purchaseMilitaryUnitIfEnoughFunds('b', GameBoard.MilitaryUnitType.TANK);
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

    void purchaseMilitaryUnitIfEnoughFunds(char playerColor, GameBoard.MilitaryUnitType militaryUnitType) {

        int defenseStars = gameBoard.defenseStarsAtXYTile(gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY());

        if (playerColor == 'r') {
            switch (militaryUnitType) {
                case INFANTRY:
                    if (gameBoard.getRedPlayerBank() >= 1000) {
                        gameBoard.setRedPlayerBank(gameBoard.getRedPlayerBank() - 1000);
                        gameBoard.resetMilitaryUnitSelected();
                        gameBoard.addMilitaryUnits(new Infantry('r', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true, true, true,
                                defenseStars));
                        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(true, false);
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.updateAttackSquares();
                    }
                    break;
                case MECH:
                    if (gameBoard.getRedPlayerBank() >= 3000) {
                        gameBoard.setRedPlayerBank(gameBoard.getRedPlayerBank() - 3000);
                        gameBoard.resetMilitaryUnitSelected();
                        gameBoard.addMilitaryUnits(new Mech('r', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true, true, true,
                                defenseStars));
                        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(true, false);
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.updateAttackSquares();
                    }
                    break;
                case ARTILLERY:
                    if (gameBoard.getRedPlayerBank() >= 6000) {
                        gameBoard.setRedPlayerBank(gameBoard.getRedPlayerBank() - 6000);
                        gameBoard.resetMilitaryUnitSelected();
                        gameBoard.addMilitaryUnits(new Artillery('r', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true, true, true,
                                defenseStars));
                        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(false, true);
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.updateAttackSquares();
                    }
                    break;
                case TANK:
                    if (gameBoard.getRedPlayerBank() >= 7000) {
                        gameBoard.setRedPlayerBank(gameBoard.getRedPlayerBank() - 7000);
                        gameBoard.resetMilitaryUnitSelected();
                        gameBoard.addMilitaryUnits(new Tank('r', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true, true, true,
                                defenseStars));;
                        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(true, false);
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.updateAttackSquares();
                    }
                    break;
            }
        } else if (playerColor == 'b') {
            switch (militaryUnitType) {
                case INFANTRY:
                    if (gameBoard.getBluePlayerBank() >= 1000) {
                        gameBoard.setBluePlayerBank(gameBoard.getBluePlayerBank() - 1000);
                        gameBoard.resetMilitaryUnitSelected();
                        gameBoard.addMilitaryUnits(new Infantry('b', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true, true, true,
                                defenseStars));
                        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(true, false);
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.updateAttackSquares();
                    }
                    break;
                case MECH:
                    if (gameBoard.getBluePlayerBank() >= 3000) {
                        gameBoard.setBluePlayerBank(gameBoard.getBluePlayerBank() - 3000);
                        gameBoard.resetMilitaryUnitSelected();
                        gameBoard.addMilitaryUnits(new Mech('b', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true, true, true,
                                defenseStars));
                        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(true, false);
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.updateAttackSquares();
                    }
                    break;
                case ARTILLERY:
                    if (gameBoard.getBluePlayerBank() >= 6000) {
                        gameBoard.setBluePlayerBank(gameBoard.getBluePlayerBank() - 6000);
                        gameBoard.resetMilitaryUnitSelected();
                        gameBoard.addMilitaryUnits(new Artillery('b', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true, true, true,
                                defenseStars));
                        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(false, true);
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.updateAttackSquares();
                    }
                    break;
                case TANK:
                    if (gameBoard.getBluePlayerBank() >= 7000) {
                        gameBoard.setBluePlayerBank(gameBoard.getBluePlayerBank() - 7000);
                        gameBoard.resetMilitaryUnitSelected();
                        gameBoard.addMilitaryUnits(new Tank('b', gameBoard.getCursorMapTileX(), gameBoard.getCursorMapTileY(), true, true, true,
                                defenseStars));
                        gameBoard.updateGameBoardSpecificSelectedMilitaryUnitVariables(true, false);
                        gameBoard.setBuyingFromBasePossible(false);
                        gameBoard.updateAttackSquares();
                    }
                    break;
            }
            System.out.println("click on purchase button happened and player is blue");
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

            resetAllMilitaryUnitsHasMoved();

            if (gameBoard.getTurnColor() == 'r') {

                gameBoard.setTurnColor('b');
                gameBoard.giveNewTurnIncome('b');
                System.out.println("It is blue's turn");

            } else {

                gameBoard.setTurnColor('r');
                gameBoard.giveNewTurnIncome('r');
                System.out.println("It is red's turn");

            }

        }

    } // END OF checkIfEndTurnWasClickedOn METHOD

    /*
    Updates all military units.  The property has moved is set to false, so they can move on a new turn.
     */

    void resetAllMilitaryUnitsHasMoved() {

        MilitaryUnit currentMilitaryUnit = null;
        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = gameBoard.militaryUnitsIterator();

        while (tempMilitaryUnitsIterator.hasNext()) {

            currentMilitaryUnit = tempMilitaryUnitsIterator.next();
            currentMilitaryUnit.setMovedThisTurn(false);
            currentMilitaryUnit.setAttackedThisTurn(false);
        }
    }

    /*
     update the grid of which tiles are currently movable.
     */

    void updateCurrentMovableChoices() {

        // reset the current movable choices to all false, whenever this method is called.
        // this method is called at some point soon after the player uses a left click
        // (all of the methods in this class occur after left clicks)
        gameBoard.resetCurrentMoveableChoicesGrid();

        // if no unit is selected or if the unit has moved already this turn, then return early.

        if (gameBoard.getSelectedMilitaryUnit() == null || gameBoard.getSelectedMilitaryUnit().isMovedThisTurn()) {
            return;
        }

        // initialize variables

        MilitaryUnit selectedMilitaryUnit = null;
        GameBoard.MilitaryUnitType militaryUnitType;
        int currentNodeX;
        int currentNodeY;

        // get the unit type of the unit selected and the node at the current location of the unit

        selectedMilitaryUnit = gameBoard.getSelectedMilitaryUnit();
        militaryUnitType = selectedMilitaryUnit.getMilitaryUnitType();
        int selectedUnitXTile = selectedMilitaryUnit.getXTile();
        int selectedUnitYTile = selectedMilitaryUnit.getYTile();
        WeightedGraph.Node selectedNode = gameBoard.getNodeAtLocation(selectedUnitXTile, selectedUnitYTile);

        // call the graph search function via a GameBoard method.  The graph is on the gameBoard

        CopyOnWriteArrayList<WeightedGraph.Node> resultNodes = gameBoard.runNodesAccessibleFromLocationByUnitType(selectedNode, militaryUnitType);
        // now update the movable choices based on the X, Y locations of the resultNodes

        for (WeightedGraph.Node node : resultNodes) {
            currentNodeX = node.getXTile();
            currentNodeY = node.getYTile();
            gameBoard.updateCurrentMoveableChoicesGrid(currentNodeX, currentNodeY, true);   // not sure if this will work, but looks reasonable.
        }

    } // END OF updateCurrentMovableChoices METHOD

} // END OF FindLeftClickGameStateChanges  CLASS