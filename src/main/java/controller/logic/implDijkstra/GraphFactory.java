package controller.logic.implDijkstra;

import bin.Graph;
import controller.logic.IGraphFactory;
import controller.logic.IDistanceFinder;
import bin.Edge;
import bin.Node;

import java.util.*;

public class GraphFactory implements IGraphFactory {
    public static final List<Integer> NODE_IDS = List.of(0, 1, 2, 3);
    public static final int COORDINATE_UPPER_BOUND = 50;
    public static final int COORDINATE_LOWER_BOUND = 10;
    public static final IDistanceFinder distanceFinder = new DistanceFinder();

    @Override
    public Graph generateGraph() {
        // generate nodes w/o edges
        List<Node> nodeList = new ArrayList<>();
        Random random = new Random();
        for (int id : NODE_IDS) {
            int x = random.nextInt(COORDINATE_UPPER_BOUND - COORDINATE_LOWER_BOUND) + COORDINATE_LOWER_BOUND;
            int y = random.nextInt(COORDINATE_UPPER_BOUND - COORDINATE_LOWER_BOUND) + COORDINATE_LOWER_BOUND;
            Node node = new Node(id, x, y);
            nodeList.add(node);
        }

        // generate guaranteed route edges
        for (int i : NODE_IDS) {
            Node node = nodeList.get(i);
            Edge edge = new Edge();
            edge.setSource(node);
            if (i == nodeList.size() - 1) {
                edge.setDestination(nodeList.get(0));
                edge.setDistance(distanceFinder.getDistance(node, nodeList.get(0)));
            } else {
                edge.setDestination(nodeList.get(i + 1));
                edge.setDistance(distanceFinder.getDistance(node, nodeList.get(i + 1)));
            }
            node.addEdge(edge);
        }

        // generate additional route edges
        for (int i : NODE_IDS) {
            Node node = nodeList.get(i);
            Edge edge = new Edge();
            List<Integer> destinationOptions = new ArrayList<>(NODE_IDS);
            destinationOptions.remove(Integer.valueOf(i));
            if (i == NODE_IDS.size() - 1) {
                destinationOptions.remove(Integer.valueOf(0));
            } else {
                destinationOptions.remove(Integer.valueOf(i+1));
            }
            int upperIndexBound = destinationOptions.size() - 1;
            int destinationIndex = random.nextInt(upperIndexBound);
            edge.setSource(node);
            edge.setDestination(nodeList.get(destinationIndex));
            edge.setDistance(distanceFinder.getDistance(node, nodeList.get(destinationIndex)));
            node.addEdge(edge);
        }
        return new Graph(nodeList);
    }
}
