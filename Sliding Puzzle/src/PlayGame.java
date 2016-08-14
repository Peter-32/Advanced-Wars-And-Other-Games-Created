import java.util.Scanner;

/**
 * Created by Peter on 8/13/2016.
 */
public class PlayGame {
    public static void main(String[] args) {
        Boolean autoSolve = false;
        int computerSpeed = 0;

        Scanner scanner = new Scanner(System.in);

        // Ask for user input

        System.out.print("Would you like to auto solve the pre-made puzzle? (Y/N): ");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("Y")) {
            autoSolve = true;
            System.out.print("Approximately how many moves should the computer make each second? (1 to 1000) ");
            while (!scanner.hasNextInt()) {
                scanner.next();
            }
            computerSpeed = scanner.nextInt();
        }

        new GameBoard(autoSolve, computerSpeed);
    }
}
