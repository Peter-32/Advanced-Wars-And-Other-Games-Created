import javax.swing.*;

class GameOver {
    GameBoard gameBoard;

    GameOver(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        JOptionPane.showMessageDialog(gameBoard, "Game Over! Turns: " + gameBoard.getTurnNumber());
    }
}