package com.solvd.naviapp.controller.logic.implDijkstra;

import com.solvd.naviapp.bin.Edge;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;

import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.logic.IPathAlgorithm;

import java.util.*;

public class PathAlgorithm implements IPathAlgorithm {
    public Path getPath(Graph graph, Node source, Node target) {
        // Initialize data structures
        // stores the minimum distance from the source node to each node in the graph.
        Map<Node, Integer> distanceMap = new HashMap<>();
        // stores the parent node of each node in the shortest path from the source node.
        Map<Node, Node> parentMap = new HashMap<>();
        //  keeps track of the nodes that have been visited.
        Set<Node> visitedNodes = new HashSet<>();
        Path pathObject = null;

        // Step 1: Initialize distances to infinity except for the source node
       graph.getNodes().forEach(node->{
           if (node == source) {
               distanceMap.put(node, 0);
           } else {
               distanceMap.put(node, Integer.MAX_VALUE);
           }
       });

        // Step 2: Process nodes
        while (visitedNodes.size() < graph.getNodes().size()) {
            // get unvisited with min distance from the source (current)
            Node current = getMinDistanceNode(distanceMap, visitedNodes);

            if (current == null) {
                // The graph is not connected, and there is no path from the source node
                break;
            }
            // mark current as visited
            visitedNodes.add(current);

            // Step 3: Update distances for neighboring nodes

            // iterate over list of edges of current node
            current.getEdges().forEach((edge -> {
                // get neighbor node from the edge object
                Node neighbor = edge.getDestination();
                int weight = edge.getDistance();
                int newDistance = distanceMap.get(current) + weight;

                if (newDistance < distanceMap.get(neighbor)) {
                    distanceMap.put(neighbor, newDistance);
                    parentMap.put(neighbor, current);
                }
            }));
        }

        // Step 4: Print shortest paths and distances
        for (Node node : graph.getNodes()) {
            // iterate over nodes in and skip source node
            if (node == target) {
                // for non source initialize a list that will hold path from source to current node
                List<Node> path = new ArrayList<>();
                Node current = node;

                // Build the shortest path by traversing parent nodes
                while (current != null) {
                    path.add(current);
                    current = parentMap.get(current);
                }

                // Reverse the path to get the correct order
                Collections.reverse(path);

                // initializing Path Object
                pathObject = new Path(source, target, path, distanceMap.get(target));
            }
        }
        return pathObject;
    }

    // purpose of this method is to find the node with the minimum distance among the unvisited nodes
    private static Node getMinDistanceNode(Map<Node, Integer> distanceMap, Set<Node> visitedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;

        // Alternative way to iterate over map. entrySet() returns a set view of the hash map.
        // Entry objects has both value and keys (entry.getValue, entry.getKey)

        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();

            if (!visitedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

}

