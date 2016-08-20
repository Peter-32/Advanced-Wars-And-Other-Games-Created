import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Peter on 8/19/2016.
 */
public class WeightedGraph {

    private CopyOnWriteArrayList<DirectedEdge> edges;
    private CopyOnWriteArrayList<Node> nodes;
    private Map<Node, CopyOnWriteArrayList<DirectedEdge>> nodeEdges;

    WeightedGraph() {
        edges = new CopyOnWriteArrayList<DirectedEdge>();
        nodes = new CopyOnWriteArrayList<Node>();
        nodeEdges = new ConcurrentHashMap<Node, CopyOnWriteArrayList<DirectedEdge>>();

    }

    Node getNodeAtLocation(int xTile, int yTile) {
        for (Node node : nodes) {
            if (node.getXTile() == xTile &&
                    node.getYTile() == yTile) {
                return node;
            }
        }
        return null;
    }

    void addNode(GameBoard.TerrainTile terrainTile, int xTile, int yTile) {
        new Node(terrainTile, xTile, yTile);
    }

    void addEdges(Node node1, Node node2) {
        new DirectedEdge(node1, node2);
        new DirectedEdge(node2, node1);
    }


    /*
    Returns the unique nodes that are accessible from this initial x, y location
     */
    CopyOnWriteArrayList<Node> nodesAccessibleFromLocationWithSteps(int steps, int xTile, int yTile, GameBoard.MilitaryUnitType militaryUnitType) {
        return recursiveNodesAccessibleFromLocationWithSteps(
                new CopyOnWriteArrayList<Node>(), steps, xTile, yTile, militaryUnitType);
    }



    /*
    Recursive call from the nodesAccessibleFromLocationWithSteps method
     */

    CopyOnWriteArrayList<Node> recursiveNodesAccessibleFromLocationWithSteps(
            CopyOnWriteArrayList<Node> uniqueNodeList, int steps, int xTile, int yTile, GameBoard.MilitaryUnitType militaryUnitType) {

        // moves required to go from node 1 to node 2 for the edge

        int terrainMovementRequirement;

        // the edges at a given node

        CopyOnWriteArrayList<DirectedEdge> currentEdges = nodeEdges.get(getNodeAtLocation(xTile, yTile));

        for (DirectedEdge edge : currentEdges) {

            terrainMovementRequirement = edge.getMovementRequired(militaryUnitType);

            // Check if the number of steps is enough to move over the terrain
            // Check if the new node is not already in the list being compiled

            if (steps >= terrainMovementRequirement &&
                    !uniqueNodeList.contains(edge.getNode2())) {

                // subtract the number of steps possible left

                steps -= terrainMovementRequirement;

                // add the node2 to the tempNodeList, which represents the new locations the military unit can travel to.

                uniqueNodeList.add(edge.getNode2());

            }

        }

        // remove duplicates and return the list


        return null;
    }



    class Node {

        private GameBoard.TerrainTile terrain;
        private int xTile, yTile;

        Node(GameBoard.TerrainTile terrain, int xTile, int yTile) {
            this.terrain = terrain;
            this.xTile = xTile;
            this.yTile = yTile;

            // add to nodes and nodeEdges

            nodes.add(this);
            nodeEdges.put(this, new CopyOnWriteArrayList<DirectedEdge>());

        }


        public GameBoard.TerrainTile getTerrain() {
            return terrain;
        }

        public int getXTile() {
            return xTile;
        }

        public int getYTile() {
            return yTile;
        }
    } // END OF Node INNER CLASS

    class DirectedEdge {

        private Node node1, node2;
        private int infantryMovementRequired, vehicleMovementRequired, mechMovementRequired;


        DirectedEdge(Node node1, Node node2) {

            this.node1 = node1;
            this.node2 = node2;

            // find four weights: movement from node1 to node2 for both infantry and vehicle

            switch (node2.getTerrain()) {
                case MOUNTAIN:
                    infantryMovementRequired = 2;
                    mechMovementRequired = 1;
                    vehicleMovementRequired = 100; // arbitrarily high number
                    break;
                default:
                    infantryMovementRequired = 1;
                    mechMovementRequired = 1;
                    vehicleMovementRequired = 1;
            }

            // Add this edge to edges and nodeEdges

            edges.add(this);
            nodeEdges.get(node1).add(this);


        } // END OF CONSTRUCTOR

        public int getMovementRequired(GameBoard.MilitaryUnitType militaryUnitType) {
            switch (militaryUnitType) {

                case INFANTRY:
                    return infantryMovementRequired;
                case MECH:
                    return mechMovementRequired;
                case ARTILLERY:
                    return vehicleMovementRequired;
                case TANK:
                    return vehicleMovementRequired;
                default:
                    return 100; // arbitrarily high number
            }
        }

        public Node getNode1() {
            return node1;
        }

        public Node getNode2() {
            return node2;
        }
    } // END OF DirectedEdge INNER CLASS

} // END OF WeightedGraph CLASS