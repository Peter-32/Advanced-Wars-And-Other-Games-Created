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

        // AI Executes turn

        if (gameBoard.players == 1 &&
                gameBoard.getTurnColor() == 'b' &&
                !gameBoard.isToldAIToExecuteTurn()) {
            System.out.println("TEST");
            gameBoard.getAi().executeEntireTurn();
        }

        // set the xClicked and yClicked for this frame
        xClicked = gameBoard.getXClicked();
        yClicked = gameBoard.getYClicked();

        // Check if A button is pressed; shows a selected units attack grids

        if (gameBoard.isPressedTheAKeyWhileUnitSelected()) {
            new FindAButtonGameStateChanges(gameBoard);
        }

        // Check if Q button is pressed; shows all display attack grids
        if (gameBoard.isPressedTheQKey()) {
            new FindQButtonGameStateChanges(gameBoard);
        }

        // if the click type is 1, it is a left click.  If click type is greater than 1 it is an alternative click
        // all alternative clicks are treated the same.  They are essentially all treated as right clicks.

        if (gameBoard.getClickType() == 1) {
            new FindLeftClickGameStateChanges(gameBoard, xClicked, yClicked);
        } else if (gameBoard.getClickType() > 1) {
            new FindRightClickGameStateChanges(gameBoard, xClicked, yClicked);
        }

        // Search for game over

        // repaint
        gameDrawingPanel.repaint();

        // Reset click location to -1 after frame is complete.  We don't want to reuse that click after the frame is done.

        gameBoard.setXClicked(-1);
        gameBoard.setYClicked(-1);
        gameBoard.setClickType(-1);

    } // END OF run METHOD

} // END OF MainGameLoop CLASS