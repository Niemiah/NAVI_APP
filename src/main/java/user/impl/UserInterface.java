package user.impl;
import user.IUserInterface;
import user.IUserInputService;
import user.IUserOutputService;
import com.solvd.naviapp.db.IClientService;
import com.solvd.naviapp.db.implMyBatis.services.ClientService;
import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;

import java.util.List;
import java.util.Scanner;

public class UserInterface implements IUserInterface {
    private final INavService navService;
    private final IClientService clientService;
    private Graph graph;
    private Scanner scanner;
    private Client client;

    //welcome and goodbye messages
    private final String welcomeMessage = "Welcome to the shortest path finder!";
    private final String goodbyeMessage = "Thank you for using the shortest path finder!";

    public UserInterface() {
        navService = new NavService();
        clientService = new ClientService();
        scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        displayWelcomeMessage();
        String userName = getUserName();
        client = clientService.readFromDb(userName.hashCode());
        if (client == null) {
            client = new Client();
            client.setName(userName);
            client = clientService.writeToDb(client);
        }
        boolean isNewExecution = promptForExecutionType();

        if (isNewExecution) {
            graph = navService.getGraph();
            client.addGraph(graph);
            displayGraph();
        } else {
            graph = client.getGraphList().get(client.getGraphList().size() - 1);
            displayGraph();
        }
        Node sourceNode = promptNode("source");
        Node destinationNode = promptNode("destination");
        Path path = navService.getPath(sourceNode, destinationNode, graph);
        displayRoute(path);
        displayGoodbyeMessage();
    }

    private String getUserName() {
        System.out.println("Please enter your name:");
        return scanner.nextLine();
    }

    private boolean promptForExecutionType() {
        System.out.println("Would you like to start a new execution? (Y/N)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("Y")) {
            return true;
        } else if (input.equalsIgnoreCase("N")) {
            return false;
        } else {
            System.out.println("Invalid input. Please try again.");
            return promptForExecutionType();
        }
    }
    private void promptNodeMessage(String type) {
        System.out.println("Please select a " + type + " node from the list above by entering the node's number:");
    }

    private Node getNodeFromUserInput() {
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Exiting the application...");
            System.exit(0);
        }
        try {
        int nodeNumber = scanner.nextInt();
        return graph.getNodes().stream()
                .filter(node -> node.getId() == nodeNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Node with id " + nodeNumber));
    } catch (NumberFormatException e) {
        System.out.println("Invalid node number entered. Please try again.");
        return getNodeFromUserInput();
        }
    }

    @Override
    public Node promptNode(String type) {
        promptNodeMessage(type);
        return getNodeFromUserInput();
    }

    @Override
    public void displayWelcomeMessage() {
        System.out.println(welcomeMessage);
    }

    @Override
    public void displayRoute(Path path) {
        System.out.println("The shortest path from node " + path.getSource().getId() + " to node " + path.getTarget().getId() + " is " + path.getDistance() + " units long.");
    }

    @Override
    public void displayGoodbyeMessage() {
        System.out.println(goodbyeMessage);
    }

    public void displayGraph() {
        List<Node> nodes = graph.getNodes();
        for (Node node : nodes) {
            System.out.println("Node number " + node.getId() + " at coordinates (" + node.getX() + ", " + node.getY() + ")");
        }
    }
}
