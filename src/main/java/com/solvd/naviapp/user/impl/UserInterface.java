package com.solvd.naviapp.user.impl;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.user.IUserInterface;


import java.util.List;
import java.util.Scanner;

public class UserInterface implements IUserInterface {
    private final INavService navService;
    private Graph graph;
    private Scanner scanner;

    public UserInterface() {
        navService = new NavService();
        scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        displayWelcomeMessage();
        graph = navService.getGraph();
        displayGraph();
        promptSourceNode();
        Node sourceNode = getNodeFromUserInput();
        promptDestinationNode();
        Node destinationNode = getNodeFromUserInput();
        Path path = navService.getPath(sourceNode, destinationNode, graph);
        displayRoute(path);
        displayGoodbyeMessage();
    }

    @Override
    public void displayWelcomeMessage() {
        System.out.println("Welcome to the shortest path finder!");
    }

    @Override
    public void promptSourceNode() {
        System.out.println("Please select a starting node from the list above by entering the node's number:");
    }

    @Override
    public void promptDestinationNode() {
        System.out.println("Please select a destination node from the list above by entering the node's number:");
    }

    @Override
    public void displayRoute(Path path) {
        System.out.println("The shortest path from node " + path.getSource().getId() + " to node " + path.getTarget().getId() + " is " + path.getDistance() + " units long.");
    }

    @Override
    public void displayGoodbyeMessage() {
        System.out.println("Thank you for using the shortest path finder!");
    }

    public void displayGraph() {
        List<Node> nodes = graph.getNodes();
        for (Node node : nodes) {
            System.out.println("Node number " + node.getId() + " at coordinates (" + node.getX() + ", " + node.getY() + ")");
        }
    }

    private Node getNodeFromUserInput() {
        int nodeNumber = scanner.nextInt();
        return graph.getNodes().stream()
                .filter(node -> node.getId() == nodeNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Node with id " + nodeNumber));
    }

    @Override
    public Node getSourceNode() {
        promptSourceNode(); //Prompts the com.solvd.naviapp.user to select a source node
        return getNodeFromUserInput();
    }

    @Override
    public Node getDestinationNode() {
        promptDestinationNode(); //Prompts the com.solvd.naviapp.user to select a source node
        return getNodeFromUserInput();
    }
}
