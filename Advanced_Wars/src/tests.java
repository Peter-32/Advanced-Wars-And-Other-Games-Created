import java.util.Iterator;

/**
 * Created by Peter on 8/14/2016.
 */
public class tests {

    private enum TerrainTile {
        MOUNTAIN, GRASS,
        ROAD_VERTICAL, ROAD_HORIZONTAL, ROAD_TURN_UL, ROAD_TURN_UR, ROAD_TURN_DL, ROAD_TURN_DR
    }
    private enum BuildingTile {
        GRAY_HQ, GRAY_BASE, GRAY_CITY,
        RED_HQ, RED_BASE, RED_CITY,
        BLUE_HQ, BLUE_BASE, BLUE_CITY
    }
    private TerrainTile[][] terrainTilesGrid = new TerrainTile[10][16];


    public static void main(String[] args) {
        new tests();
    }

    tests() {
        System.out.println(terrainTilesGrid.length);
        System.out.println(terrainTilesGrid[0].length);
        /*
        for (int i = 0; i < terrainTilesGrid.length; i++) {
            for (int j = 0; j < terrainTilesGrid[0].length; j++) {
                System.out.println(i);
            }
        }
        */
    }
}
