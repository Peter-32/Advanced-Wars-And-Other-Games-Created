import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Peter on 8/19/2016.
 */
public class WeightedGraph {

    private CopyOnWriteArrayList<DirectedEdge> edges;
    private CopyOnWriteArrayList<Node> nodes;
    private Map<Node, CopyOnWriteArrayList<DirectedEdge>> nodeEdges;
    private GameBoard gameBoard;

    WeightedGraph(GameBoard gameBoard) {
        edges = new CopyOnWriteArrayList<DirectedEdge>();
        nodes = new CopyOnWriteArrayList<Node>();
        nodeEdges = new ConcurrentHashMap<Node, CopyOnWriteArrayList<DirectedEdge>>();
        this.gameBoard = gameBoard;

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
    void loggingNumberOfNodesAndEdges() {
        System.out.println("edges.size() = " + edges.size());
        System.out.println("nodes.size() = " + nodes.size());
        System.out.println("nodeEdges.size() = " + nodeEdges.size());
        CopyOnWriteArrayList<DirectedEdge> loggingEdges = null;
        int countEdgesInDictionary = 0;
        for (Node node : nodes) {
            loggingEdges = nodeEdges.get(node);
            countEdgesInDictionary += loggingEdges.size();
        }
        System.out.println("nodeEdges number of edges = " + countEdgesInDictionary);
    }


    /*
    Returns the unique nodes that are accessible from this initial x, y location
     */
    Set<Node> nodesAccessibleFromLocationWithSteps(Node startingNode, GameBoard.MilitaryUnitType militaryUnitType
                                                   ) {
        int steps = 0;
        switch (militaryUnitType) {

            case INFANTRY:
                steps = 3;
                break;
            case MECH:
                steps = 2;
                break;
            case ARTILLERY:
                steps = 4;
                break;
            case TANK:
                steps = 6;
                break;
        }


        // run the recursive method with the new steps field

        CopyOnWriteArrayList<Node> nodeList = NodesAccessibleFromLocationWithStepsRecursion(
                new CopyOnWriteArrayList<Node>(), steps, startingNode, militaryUnitType);

        // this conversion removes duplicates

        Set<Node> uniqueNodeList = new HashSet<Node>(nodeList);
        return uniqueNodeList;
    }



    /*
    Recursive call from the nodesAccessibleFromLocationWithSteps method
     */

    CopyOnWriteArrayList<Node> NodesAccessibleFromLocationWithStepsRecursion(
            CopyOnWriteArrayList<Node> nodeList, int steps, Node startingNode, GameBoard.MilitaryUnitType militaryUnitType) {

        boolean useContinueOnce = false;

        // get the xTile and yTile of the startingNode

        int startingXTile = startingNode.getXTile();
        int startingYTile = startingNode.getYTile();

        // moves required to go from node 1 to node 2 for the edge

        int terrainMovementRequirement;
        int newSteps = steps;

        // this stores the nodes that will be moved to

        Node nodeToMoveTo = null;

        // the edges at a given node

        CopyOnWriteArrayList<DirectedEdge> currentEdges = nodeEdges.get(getNodeAtLocation(startingXTile, startingYTile));

        // prepare to skip movement through tiles with enemy units in them.  You cannot move through the other team's color units.

        MilitaryUnit currentMilitaryUnit = null;
        Iterator<MilitaryUnit> tempUnitsIterator = gameBoard.militaryUnitsIterator();

        // loops through all edges that start from this starting node, and go to a new node called "node 2".

        for (DirectedEdge edge : currentEdges) {

            useContinueOnce = false;

            // if this edge has the same node2 X position and Y position of an enemy unit then we want to skip this edge.

            while (tempUnitsIterator.hasNext()) {

                currentMilitaryUnit = tempUnitsIterator.next();
                if (edge.getNode2().getXTile() == currentMilitaryUnit.getXTile() &&
                        edge.getNode2().getYTile() == currentMilitaryUnit.getYTile() &&
                        gameBoard.getTurnColor() != currentMilitaryUnit.getColor()) {
                    useContinueOnce = true;
                }

            } // ?ND OF WHILE LOOP

            // based on the while loop above, either use continue or don't use it.  This boolean resets to false
            // for each for loop iteration.

            if (useContinueOnce) {
                continue;
            }

            terrainMovementRequirement = edge.getMovementRequired(militaryUnitType);
            nodeToMoveTo = edge.getNode2();

            // Check if the number of steps is enough to move over the terrain
            // Check if the new node is not already in the list being compiled

            if (steps >= terrainMovementRequirement &&
                    !nodeList.contains(nodeToMoveTo)) {

                // subtract the number of steps possible left

                newSteps = steps - terrainMovementRequirement;

                // add the node2 to the tempNodeList, which represents the new locations the military unit can travel to.

                nodeList.add(nodeToMoveTo);

                // return recursively

                nodeList.addAll(NodesAccessibleFromLocationWithStepsRecursion(nodeList, newSteps, nodeToMoveTo, militaryUnitType));

            }

        } // END OF FOR LOOP

        // return the list.

        return nodeList;

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