public class MainGameLoop implements Runnable {

    //// FIELDS

    int xClicked;
    int yClicked;
    GameBoard gameBoard;
    GameDrawingPanel gameDrawingPanel;

    //// CONSTRUCTOR

    MainGameLoop(GameBoard gameBoard, GameDrawingPanel gameDrawingPanel) {
        this.gameBoard = gameBoard;
        this.gameDrawingPanel = gameDrawingPanel;
    }

    //// METHODS

    @Override
    public void run() {

        // set the xClicked and yClicked for this frame
        xClicked = gameBoard.getXClicked();
        yClicked = gameBoard.getYClicked();

        // if the click type is 1, it is a left click.  If click type is greater than 1 it is an alternative click
        // all alternative clicks are treated the same.  They are essentially all treated as right clicks.

        if (gameBoard.getClickType() == 1 &&
                !gameBoard.isPressedTheAKeyWhileUnitSelected()) {
            new FindLeftClickGameStateChanges(gameBoard, xClicked, yClicked);
        } else if (gameBoard.getClickType() > 1 &&
                !gameBoard.isPressedTheAKeyWhileUnitSelected()) {
            new FindRightClickGameStateChanges(gameBoard, xClicked, yClicked);
        } else if (gameBoard.isPressedTheAKeyWhileUnitSelected()) {
            new FindAButtonGameStateChanges(gameBoard);
        }


        /////////////////////////if (gameBoard.isPressedTheAKeyWhileUnitSelected())??????????????? What is this again


        // repaint
        gameDrawingPanel.repaint();

        // Reset click location to -1 after frame is complete.  We don't want to reuse that click after the frame is done.

        gameBoard.setXClicked(-1);
        gameBoard.setYClicked(-1);
        gameBoard.setClickType(-1);

    } // END OF run METHOD

} // END OF MainGameLoop CLASS