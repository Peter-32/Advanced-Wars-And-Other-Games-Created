import java.util.Scanner;

/**
 * Created by Peter on 8/13/2016.
 */
public class PlayGame {
    public static void main(String[] args) {
        Boolean autoSolve = false;
        int computerLevel = 0;

        Scanner scanner = new Scanner(System.in);

        // Ask for user input

        System.out.print("Would you like to auto solve the puzzle? (Y/N): ");
        String answer = scanner.next();
        if (answer.equals("Y")) {
            autoSolve = true;
            System.out.print("What level computer should solve the puzzle? (1 to " + Integer.MAX_VALUE + ") ");
            while (!scanner.hasNextInt()) {
                scanner.next();
            }
            computerLevel = scanner.nextInt();
        }

        new GameBoard(autoSolve, computerLevel);
    }
}
