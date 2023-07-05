package controller.logic.implFacotry;

import bin.Graph;
import controller.logic.IGraphFactory;
import controller.logic.IDistanceFinder;
import bin.Edge;
import bin.Node;

import java.util.*;

public class GraphFactory implements IGraphFactory {
    public static final List<Integer> NODE_IDS = List.of(0, 1, 2, 3, 4, 5);
    public static final int XY_UPPER_BOUND = 51; // exclusive
    public static final int XY_LOWER_BOUND = 10; // inclusive
    public static final IDistanceFinder distanceFinder = new DistanceFinder();
    public static final Random random = new Random();

    @Override
    public Graph generateGraph() {
        List<Node> nodeList = generateNodes();
        addEdges(nodeList);
        addMoreEdges(nodeList);
        return new Graph(nodeList);
    }
    // generates nodes w/o edges
    private List<Node> generateNodes() {
        List<Node> nodeList = new ArrayList<>();
        for (int id : NODE_IDS) {
            int x = random.nextInt(XY_UPPER_BOUND - XY_LOWER_BOUND) + XY_LOWER_BOUND;
            int y = random.nextInt(XY_UPPER_BOUND - XY_LOWER_BOUND) + XY_LOWER_BOUND;
            Node node = new Node(id, x, y);
            nodeList.add(node);
        }
    return nodeList;
    }

    // connects all nodes together
    private void addEdges(List<Node> nodeList) {
        nodeList.forEach((currentNode -> {
            Edge edge = new Edge();
            // creates edge with source at current node
            edge.setSource(currentNode);
            // connects edge with the first node, if current node index is the last in the list,
            if (currentNode.getId()==nodeList.size()-1){
                edge.setDestination(nodeList.get(0));
                edge.setDistance(distanceFinder.getDistance(currentNode, nodeList.get(0)));

            // connects edge with the next node from current
            } else {
                edge.setDestination(nodeList.get(currentNode.getId() + 1));
                edge.setDistance(distanceFinder.getDistance(currentNode, nodeList.get(currentNode.getId() + 1)));
            }
            currentNode.addEdge(edge);
        }));

        }

    // adds 2 additional edges to nodes on top of existing
    private void addMoreEdges(List <Node> nodeList){
        nodeList.forEach((currentNode -> {
            // stores new unique edge
            System.out.println("working on "+ currentNode);
            HashSet<Edge> edgeSet = new HashSet<>();
            // contains indexes of potential destination for new edges
            List<Integer> destinationOptions = new ArrayList<>(NODE_IDS);
            System.out.println("original option list"+ destinationOptions);
            // removes destination indexes to current node and the next node
            destinationOptions.remove(Integer.valueOf(currentNode.getEdges().get(0).getSource().getId()));
            destinationOptions.remove(Integer.valueOf(currentNode.getEdges().get(0).getDestination().getId()));
            System.out.println("option list after removing " + destinationOptions);
            int upperIndexBound = destinationOptions.size();

            while(edgeSet.size()<2){
                //  randomly picks index for edge destination from options list
                int destinationIndex = random.nextInt(upperIndexBound);
                System.out.println("generated destination index " + destinationIndex);
                Node destinationNode = nodeList.get(destinationOptions.get(destinationIndex));
                int distance = distanceFinder.getDistance(currentNode, destinationNode);
                edgeSet.add(new Edge(currentNode, destinationNode, distance));
                System.out.println("generated edge set " + edgeSet);
            }
            // adds generated edges from set to the current node
            edgeSet.forEach(edge ->
                    currentNode.addEdge(edge)
                    );
        }));

        }
    }

