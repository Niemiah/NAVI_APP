package com.solvd.naviapp.controller.logic.implFactory;

import com.solvd.naviapp.bin.Edge;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.controller.logic.IDistanceFinder;
import com.solvd.naviapp.controller.logic.IGraphFactory;
import com.solvd.naviapp.controller.logic.implDistanceFinder.DistanceFinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GraphFactory implements IGraphFactory {
    public static final List<Integer> NODE_Indexes
            = List.of(0, 1, 2, 3, 4, 5);
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
       NODE_Indexes.forEach((index)-> {
            int x = random.nextInt(XY_UPPER_BOUND - XY_LOWER_BOUND) + XY_LOWER_BOUND;
            int y = random.nextInt(XY_UPPER_BOUND - XY_LOWER_BOUND) + XY_LOWER_BOUND;
            Node node = new Node(x, y, Integer.toString(index));
            nodeList.add(node);
        });
        return nodeList;
    }

    // connects all nodes together
    private void addEdges(List<Node> nodeList) {
        for (int index = 0; index < nodeList.size(); index++) {
            Edge edge = new Edge();
            Node currentNode = nodeList.get(index);
            // creates edge with source at current node
            edge.setSource(currentNode);
            // connects edge with the first node, if current node index is the last in the list,
            if (index == nodeList.size() - 1) {
                edge.setDestination(nodeList.get(0));
                edge.setDistance(distanceFinder.getDistance(currentNode, nodeList.get(0)));

                // connects edge with the next node from current
            } else {
                edge.setDestination(nodeList.get(index + 1));
                edge.setDistance(distanceFinder.getDistance(currentNode, nodeList.get(index + 1)));
            }
            currentNode.addEdge(edge);
        }
        ;
    }

    // adds 2 additional edges to nodes on top of existing
    private void addMoreEdges(List<Node> nodeList) {
        for (int index = 0; index < nodeList.size(); index++) {
            Node currentNode = nodeList.get(index);
            // stores new unique edge
            HashSet<Edge> edgeSet = new HashSet<>();
            // contains indexes of potential destination for new edges
            List<Integer> destinationOptions = new ArrayList<>(NODE_Indexes);
            // removes destination indexes to the next node to avoid duplicate edges
            if (index == destinationOptions.size() - 1) {
                destinationOptions.remove(Integer.valueOf(0));
            } else {
                destinationOptions.remove(Integer.valueOf(index + 1));
            }
            // removes destination indexes to the node itself to avoid self bound edges
            destinationOptions.remove(Integer.valueOf(index));
            int upperIndexBound = destinationOptions.size();

            while (edgeSet.size() < 2) {
                //  randomly picks index for edge destination from options list
                int destinationIndex = random.nextInt(upperIndexBound);
                Node destinationNode = nodeList.get(destinationOptions.get(destinationIndex));
                int distance = distanceFinder.getDistance(currentNode, destinationNode);
                edgeSet.add(new Edge(currentNode, destinationNode, distance));
            }
            // adds generated edges from set to the current node
            edgeSet.forEach(edge ->
                    currentNode.addEdge(edge)
            );
        }
    }
}

