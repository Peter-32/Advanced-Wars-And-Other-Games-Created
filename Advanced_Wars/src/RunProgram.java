import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Peter on 8/14/2016.
 */
public class RunProgram {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> mapsStored = new ArrayList<Integer>(Arrays.asList(1));

        System.out.println("Artillery has been nerfed to do 40% less damage.");

        System.out.println("How many players? (1 or 2)");

        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int players = scanner.nextInt();

        if (players != 2) {
            players = 1;
        }

        System.out.println("Which map should be loaded?");
        System.out.println("1: Mountain Pass");

        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int mapNumber = scanner.nextInt();

        if (!mapsStored.contains(mapNumber)) {
            mapNumber = 1;
        }

        new GameBoard(mapNumber, players);
    }
}
