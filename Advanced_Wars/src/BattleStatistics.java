import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

/**
 * Created by Peter on 8/22/2016.
 */
public class BattleStatistics {
    public static void main(String[] args) {

        new BattleStatistics();
    }

    BattleStatistics() {
        MilitaryUnit rInfantry = new Infantry('r', 3, 4, true, true, true, 0);
        MilitaryUnit rMech = new Mech('r', 3, 4, true, true, true, 0);
        MilitaryUnit rArtillery = new Artillery('r', 3, 4, true, true, true, 0);
        MilitaryUnit rTank = new Tank('r', 3, 4, true, true, true, 0);
        MilitaryUnit bInfantry = new Infantry('b', 3, 4, true, true, true, 0);
        MilitaryUnit bMech = new Mech('b', 3, 4, true, true, true, 0);
        MilitaryUnit bArtillery = new Artillery('b', 3, 4, true, true, true, 0);
        MilitaryUnit bTank = new Tank('b', 3, 4, true, true, true, 0);
        int DR;
        int randIdx1;
        int randIdx2;
        MilitaryUnit unit1;
        MilitaryUnit unit2;
        String info = "";
        double resultCost;

        // get list of red and blue

        ArrayList<MilitaryUnit> RU = new ArrayList<MilitaryUnit>(Arrays.asList(rInfantry, rMech, rArtillery, rTank));
        ArrayList<MilitaryUnit> BU = new ArrayList<MilitaryUnit>(Arrays.asList(bInfantry, bMech, bArtillery, bTank));

        // stores all stats like defense rating and units into variables
        // C stands for cost, AC stands for average cost, N stands for number
        // I stands for infantry, M stands for mech, A stands for artillery, T stands for tank

        // run iteratively and keep repairing
        // store data

        File fileOutput = new File("C:/Users/Peter/Desktop/output.txt");

        PrintWriter bw = null;
        try {
            bw = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(fileOutput, true)));
        for (int i = 0; i < 5000; i++) {

            // randomize the defense rating

            do {
                DR = (int) (Math.random() * 5);
            } while (DR == 2);

            // randomize indices

            randIdx1 = (int) (Math.random() * 4);
            randIdx2 = (int) (Math.random() * 4);

            // pull from array

            unit1 = RU.get(randIdx1);
            unit2 = BU.get(randIdx2);

            // battle

            resultCost = getCostDifference(unit1, unit2);
            fullRepair(unit1, unit2);

                switch (unit1.getMilitaryUnitType()) {

                    case INFANTRY:
                        info += "I";
                        break;
                    case MECH:
                        info += "M";
                        break;
                    case ARTILLERY:
                        info += "A";
                        break;
                    case TANK:
                        info += "T";
                        break;
                }
                switch (unit2.getMilitaryUnitType()) {

                    case INFANTRY:
                        info += "I";
                        break;
                    case MECH:
                        info += "M";
                        break;
                    case ARTILLERY:
                        info += "A";
                        break;
                    case TANK:
                        info += "T";
                        break;
                }
                info += Integer.toString(DR);
                info += "\t" + resultCost;

                bw.println(info);

        }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bw.close();
        }
        System.out.print("Done");
    } // END OF CONSTRUCTOR

    /*
    Find the enemy unit cost * % enemy unit health lost minus (your unit cost * % of your health lost)
     */

    double getCostDifference(MilitaryUnit yourUnit, MilitaryUnit enemyUnit) {

        yourUnit.initiateAttack(enemyUnit);
        double EH = enemyUnit.getHealth();
        int EC = enemyUnit.getCost();
        double YH = yourUnit.getHealth();
        int YC = yourUnit.getCost();

        return (YC * YH)/100 - (EC * EH)/100;

    }

    void fullRepair(MilitaryUnit militaryUnit1, MilitaryUnit militaryUnit2) {
        militaryUnit1.setHealth(100);
        militaryUnit2.setHealth(100);
    }



}
