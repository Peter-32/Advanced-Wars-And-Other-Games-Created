package MemoryPuzzle;

import java.util.Scanner;

/**
 * Created by Peter on 8/7/2016.
 */
public class MemoryPuzzleMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many matches do you want to play with?");
        System.out.println("Your choices range from 5 to 49.");
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int input = scanner.nextInt();



        new GameBoard(input);
    }
}
