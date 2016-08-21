import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Peter on 8/19/2016.
 */
public class WeightedGraph {

    private CopyOnWriteArrayList<DirectedEdge> edges;
    private CopyOnWriteArrayList<Node> nodes;
    public Map<Node, CopyOnWriteArrayList<DirectedEdge>> nodeEdges;
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

    void addEdge(Node node1, Node node2) {
        new DirectedEdge(node1, node2);
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

        int i = 1;
        for (Node node : nodes) {
            System.out.println("");
            System.out.println("node number " + i + ": (" + node.getXTile() + ", " + node.getYTile() + ")");
            loggingEdges = nodeEdges.get(node);
            for (DirectedEdge edge_ : loggingEdges) {
                System.out.println("Edge's node1 X position = " + edge_.getNode1().getXTile() + " -- Edge's node1 Y Position = " + edge_.getNode1().getYTile() +
                        " Edge's node2 X position = " + edge_.getNode2().getXTile() + " -- Edge's node2 Y Position = " + edge_.getNode2().getYTile());
            }
            i++;
        }



    }


    /*
    Returns the unique nodes that are accessible from this initial x, y location.
     */
    CopyOnWriteArrayList<Node> nodesAccessibleFromLocationByUnitType(Node startingNode, GameBoard.MilitaryUnitType militaryUnitType) {
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

        CopyOnWriteArrayList<Node> nodeList = nodesAccessibleFromLocationInNSteps(steps, startingNode, militaryUnitType);

        /*CopyOnWriteArrayList<Node> uniqueNodeList = new HashSet<Node>(nodeList); //This can be used to remove duplicates*/

        return nodeList;
    }

    /*
    Uses a breadth like search and stops when too many steps have been made.
     */

    CopyOnWriteArrayList<Node> nodesAccessibleFromLocationInNSteps(int steps, Node startingNode,
                                                                   GameBoard.MilitaryUnitType militaryUnitType) {

        // variables

        CopyOnWriteArrayList<Node> nodesVisited = new CopyOnWriteArrayList<Node>();
        CopyOnWriteArrayList<Node> frontierNodes = new CopyOnWriteArrayList<Node>();
        Node nextNode;
        CopyOnWriteArrayList<Integer> movementCostToVisitedNode = new CopyOnWriteArrayList<Integer>();
        CopyOnWriteArrayList<Integer> movementCostToFrontierNode = new CopyOnWriteArrayList<Integer>();

        // create the frontier
        // 1) The movements can't be too much
        // 2) The square cannot be controlled by an enemy unit

        CopyOnWriteArrayList<DirectedEdge> tempEdges = nodeEdges.get(startingNode);
        for (DirectedEdge edge_ : tempEdges) {
            if (steps >= edge_.getMovementRequired(militaryUnitType) &&
                    !node2ControlledByEnemyUnit(edge_)) {
                frontierNodes.add(edge_.getNode2());
                movementCostToFrontierNode.add(edge_.getMovementRequired(militaryUnitType));
            }
        }

        // visit a random frontier until you run out of moves or run out of spaces to visit

        while(frontierNodes.size() > 0) {

            // get the first frontier node

            nextNode = frontierNodes.get(0);

            // add this as a new visited node

            nodesVisited.add(nextNode);
            movementCostToVisitedNode.add(movementCostToFrontierNode.get(0));

            // generate new frontier nodes after visiting the node above
            // Only continue if 1) the movements are not too much in total
            //                  2) the existing arrays of nodes do not contain the new node (node2) (frontier or visited)
            //                  3) The square cannot be controlled by an enemy unit

            tempEdges = nodeEdges.get(nextNode);
            for (DirectedEdge edge_ : tempEdges) {
                if (steps >= edge_.getMovementRequired(militaryUnitType) + movementCostToFrontierNode.get(0) &&
                        !nodesVisited.contains(edge_.getNode2()) &&
                        !frontierNodes.contains(edge_.getNode2()) &&
                        !node2ControlledByEnemyUnit(edge_)) {
                    frontierNodes.add(edge_.getNode2());
                    movementCostToFrontierNode.add(movementCostToFrontierNode.get(0) + edge_.getMovementRequired(militaryUnitType));
                }
            }

            // remove the node from the frontier node list

            frontierNodes.remove(0);
            movementCostToFrontierNode.remove(0);

        } // END OF WHILE LOOP

        return nodesVisited;
    } // END OF nodesAccessibleFromLocationInNSteps METHOD

    boolean node2ControlledByEnemyUnit(DirectedEdge edge_) {

        // prepare to skip movement through tiles with enemy units in them.  You cannot move through the other team's color units.

        MilitaryUnit currentMilitaryUnit = null;
        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = gameBoard.militaryUnitsIterator();

        while (tempMilitaryUnitsIterator.hasNext()) {

            // Search for when the X, Y are the same, and
            // the currently selected military unit color differs from this other military unit.

            currentMilitaryUnit = tempMilitaryUnitsIterator.next();
            if (edge_.getNode2().getXTile() == currentMilitaryUnit.getXTile() &&
                    edge_.getNode2().getYTile() == currentMilitaryUnit.getYTile() &&
                    gameBoard.getSelectedMilitaryUnit().getColor() != currentMilitaryUnit.getColor()) {
                return true;
            }

        } // AND OF WHILE LOOP

        return false;
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









   /*
    Recursive call from the nodesAccessibleFromLocationWithSteps method
     */

/*           --------PREVIOUS ATTEMPT AT GRAPH SEARCH------------
    CopyOnWriteArrayList<Node> NodesAccessibleFromLocationWithStepsRecursion(
            CopyOnWriteArrayList<Node> nodeList, int steps, Node startingNode, GameBoard.MilitaryUnitType militaryUnitType) {

        // The continue variable is used to skip instances where the path runs into an enemy unit.

        System.out.println("New recursive call");
        boolean useContinueOnce = false;

*//*      // get the xTile and yTile of the startingNode

        int startingXTile = startingNode.getXTile();
        int startingYTile = startingNode.getYTile();
        getNodeAtLocation(startingXTile, startingYTile) *//*

        // moves required to go from node 1 to node 2 for the edge

        int terrainMovementRequirement;
        int newSteps = steps;

        // this stores the nodes that will be moved to

        Node nodeToMoveTo = null;
        Node nodeToMoveFrom = null;

        // the edges at a given node

        CopyOnWriteArrayList<DirectedEdge> currentEdges = nodeEdges.get(startingNode);

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

            if (steps >= terrainMovementRequirement *//* && !nodeList.contains(nodeToMoveTo)*//*) {

                // subtract the number of steps possible left

                newSteps = steps - terrainMovementRequirement;

                // add the node2 to the tempNodeList, which represents the new locations the military unit can travel to.

                nodeList.add(nodeToMoveTo);

                // Update this new variable so it is easier to read by a programmer.

                nodeToMoveFrom = nodeToMoveTo;

                // return recursively

                nodeList.addAll(NodesAccessibleFromLocationWithStepsRecursion(nodeList, newSteps, nodeToMoveFrom, militaryUnitType));

            }

        } // END OF FOR LOOP

        // return the list.

        System.out.println("Leaving recursive call");
        return nodeList;

    }*/