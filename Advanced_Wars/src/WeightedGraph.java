import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 8/19/2016.
 */
public class WeightedGraph {

    private ArrayList<DirectedEdge> edges;
    private ArrayList<Node> nodes;
    private Map<Node, ArrayList<DirectedEdge>> nodeEdges;

    WeightedGraph() {
        edges = new ArrayList<DirectedEdge>();
        nodes = new ArrayList<Node>();
        nodeEdges = new HashMap<Node, ArrayList<DirectedEdge>>();

    }

    void addNode(GameBoard.TerrainTile terrainTile) {
        new Node(terrainTile);
    }

    void addEdge(Node node1, Node node2) {
        new DirectedEdge(node1, node2);
    }


    class Node {

        private GameBoard.TerrainTile terrain;

        Node(GameBoard.TerrainTile terrain) {
            this.terrain = terrain;

            // add to nodes and nodeEdges

            nodes.add(this);
            nodeEdges.put(this, new ArrayList<DirectedEdge>());

        }


        public GameBoard.TerrainTile getTerrain() {
            return terrain;
        }
    } // END OF Node INNER CLASS

    class DirectedEdge {

        private Node node1, node2;
        private int infantryMovementRequired, vehicleMovementRequired;


        DirectedEdge(Node node1, Node node2) {

            this.node1 = node1;
            this.node2 = node2;

            // find four weights: movement from node1 to node2 for both infantry and vehicle

            switch (node2.getTerrain()) {
                case MOUNTAIN:
                    infantryMovementRequired = 2;
                    vehicleMovementRequired = 100; // arbitrarily high number
                    break;
                default:
                    infantryMovementRequired = 1;
                    vehicleMovementRequired = 1;
            }

            // Add this edge to edges and nodeEdges

            edges.add(this);
            nodeEdges.get(node1).add(this);


        } // END OF CONSTRUCTOR

        public int getInfantryMovementRequired() {
            return infantryMovementRequired;
        }

        public int getVehicleMovementRequired() {
            return vehicleMovementRequired;
        }

        public Node getNode1() {
            return node1;
        }

        public Node getNode2() {
            return node2;
        }
    } // END OF DirectedEdge INNER CLASS

} // END OF WeightedGraph CLASS