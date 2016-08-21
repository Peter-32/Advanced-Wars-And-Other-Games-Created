public class FindRightClickGameStateChanges {

    GameBoard gameBoard;
    int xClicked;
    int yClicked;
    int cursorTileX; ////???????
    int cursorTileY; ////???????
    int selectedXTile; ////???????
    int selectedYTile; ////???????

    /*   ACCOUNT FOR THIS!!!!!!!!!!!!!
        private final int leftJFrameBorderLength = 8;
private final int topJFrameBorderLength = 30;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */
    FindRightClickGameStateChanges(GameBoard gameBoard, int xClicked, int yClicked) {

        this.gameBoard = gameBoard;

        // Pull in the snapshot click location (x, y)

        this.xClicked = xClicked;
        this.yClicked = yClicked;

        // check if right click location is a free space with no units;
        // note if a unit moved and/or attacked this turn it counts as a move
        // if so, move and update the unit to have the state moved.*** a military unit property
        // only allow movement from units when it is their turn.***



        // check if the player clicked on a free map location within moving range while their unit was selected



        // check if the player clicked on an enemy unit while their unit was selected






    } // END OF CONSTRUCTOR




} // END OF FindRightClickGameStateChanges CLASS