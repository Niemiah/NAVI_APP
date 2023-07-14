package com.solvd.naviapp.user.impl;
import com.solvd.naviapp.bin.*;
import com.solvd.naviapp.db.IDbService;
import com.solvd.naviapp.user.IUserInterface;
import com.solvd.naviapp.db.IClientService;
import com.solvd.naviapp.db.implMyBatis.services.ClientService;
import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.db.implMyBatis.services.DbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class View implements IUserInterface {
    private static final Logger LOGGER = LogManager.getLogger(View.class);
    private final INavService navService;
    private final IClientService clientService;
    private final IDbService dbService;
    private Graph graph;
    private Scanner scanner;
    private Client client;

    //welcome and goodbye messages
    private final String welcomeMessage = "WELCOME TO THE SHORTEST PATH FINDER!";
    private final String goodbyeMessage = "THANK YOU FOR USING THE SHORTEST PATH FINDER!";

    public View() {
        this.navService = new NavService();
        this.clientService = new ClientService();
        this.dbService = new DbService();
        this.scanner = new Scanner(System.in);
    }

    //Starting the UserInterface
    public void start() {
        displayWelcomeMessage();

        while(true) {
            System.out.println("Are you a new or existing user? press 1 for New User or 2 for Existing User");
            String userInput = scanner.nextLine(); // read input as string

            try {
                int userType = Integer.parseInt(userInput); // try to parse string to integer

                switch(userType) {
                    case 1:
                        //new user
                        handleNewUser();
                        break;
                    case 2:
                        //existing user
                        handleExistingUser();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again."); // handle non-integer input
            }
        }
    }


    private void handleExistingUser() {
        int existingClientId = getClientId();
        client = dbService.getClientById(existingClientId);
        if (client == null) {
            LOGGER.error("Client with id {} not found.", existingClientId);
            return;
        }

        System.out.println("Welcome back " + client.getName() + "!");

        while (true) {
            this.graph = navService.getGraph();

            graph.getNodes().forEach((node -> {
                System.out.println("NODE:  "+node.getName()+" "+"("+ node.getX()+","+node.getY()+")");
                List<Edge> el = node.getEdges();
                Edge e1 = el.get(0);
                Edge e2 = el.get(1);
                Edge e3 = el.get(2);
                System.out.println("EDGES: "+e1.getSource().getName()+"-"+e1.getDestination().getName()+" length="+e2.getDistance()+
                        " |"+e2.getSource().getName()+"-"+e2.getDestination().getName()+" length="+e2.getDistance()+
                        " |"+e3.getSource().getName()+"-"+e3.getDestination().getName()+" length="+e3.getDistance()
                );
                System.out.println("");  // This is the new line you're adding
            }));

            Node source = promptNode("source"); // Use getNodeFromUserInput via promptNode
            Node target = promptNode("target"); // Use getNodeFromUserInput via promptNode
            Path path = navService.getPath(source, target, graph);
            System.out.println("Shortest path from "+
                    path.getSource().getName()+
                    " to "+path.getTarget().getName()+
                    " has distance: "+path.getDistance());

            String route = path.getNodeList().stream()
                    .map(node -> node.getName() + " --> ")
                    .collect(Collectors.joining());

            // Removing the last arrow and space
            if (!route.isEmpty()) {
                route = route.substring(0, route.length() - 5);
            }

            System.out.println("Route: " + route);

            graph.setPath(path);
            client.addGraph(graph);

            while (true) {
                System.out.println("Enter 1 to save the graph, 2 to delete and restart, or 9 to exit:");
                String userInput = scanner.nextLine();

                try {
                    int userOption = Integer.parseInt(userInput);

                    switch(userOption) {
                        case 1:
                            int savedClientId = dbService.saveClient(client);
                            displayGoodbyeMessage();
                            break;
                        case 2:
                            client.getGraphList().clear();
                            break; // Break the inner loop to re-generate a graph.
                        case 9:
                            displayGoodbyeMessage();
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
            if (client.getGraphList().isEmpty()) {
                continue; // Continue the outer loop to re-generate a graph if the graph list is empty.
            }
            break;
        }
    }

    private void handleNewUser() {
        String name = getUserName();
        client = new Client(name);

        while (true) {
            this.graph = navService.getGraph();

            graph.getNodes().forEach((node -> {
                System.out.println("NODE:  "+node.getName()+" "+"("+ node.getX()+","+node.getY()+")");
                List<Edge> el = node.getEdges();
                Edge e1 = el.get(0);
                Edge e2 = el.get(1);
                Edge e3 = el.get(2);
                System.out.println("EDGES: "+e1.getSource().getName()+"-"+e1.getDestination().getName()+" length="+e2.getDistance()+
                        " |"+e2.getSource().getName()+"-"+e2.getDestination().getName()+" length="+e2.getDistance()+
                        " |"+e3.getSource().getName()+"-"+e3.getDestination().getName()+" length="+e3.getDistance()
                );
                System.out.println("");  // This is the new line you're adding
            }));

            Node source = promptNode("source"); // Use getNodeFromUserInput via promptNode
            Node target = promptNode("target"); // Use getNodeFromUserInput via promptNode
            Path path = navService.getPath(source, target, graph);
            System.out.println("Shortest path from "+
                    path.getSource().getName()+
                    " to "+path.getTarget().getName()+
                    " has a distance of: "+path.getDistance());

            String route = path.getNodeList().stream()
                    .map(node -> node.getName() + " --> ")
                    .collect(Collectors.joining());

            // Removing the last arrow and space
            if (!route.isEmpty()) {
                route = route.substring(0, route.length() - 5);
            }

            System.out.println("Route: " + route);

            graph.setPath(path);
            client.addGraph(graph);

            while (true) {
                System.out.println("Enter 1 to save the graph, 2 to delete and restart, or 9 to exit:");
                String userInput = scanner.nextLine();

                try {
                    int userOption = Integer.parseInt(userInput);

                    switch(userOption) {
                        case 1:
                            int clientId = dbService.saveClient(client);
                            break;
                        case 2:
                            client.getGraphList().clear();
                            break; // Break the inner loop to re-generate a graph.
                        case 9:
                            displayGoodbyeMessage();
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
            if (client.getGraphList().isEmpty()) {
                continue; // Continue the outer loop to re-generate a graph if the graph list is empty.
            }
            break;
        }
    }

    private int getClientId() {
        System.out.println("Please enter your Client ID:");
        return Integer.parseInt(scanner.nextLine());
    }
    //prompting user for their name
    private String getUserName() {
        System.out.println("Please enter your name:");
        return scanner.nextLine();
    }
    //checks if user is already in the database and allows them to start a new execution
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

    //displays the Welcome Message
    @Override
    public void displayWelcomeMessage() {
        System.out.println(welcomeMessage);
    }
    //
    private void promptNodeMessage(String type) {
        System.out.println("Please select a " + type + " node from the list above by entering the node's number:");
    }

    private Node getNodeFromUserInput() {
        String nodeName = scanner.nextLine().trim();

        // If the user wants to exit
        if (nodeName.equalsIgnoreCase("exit")) {
            LOGGER.info("User requested exit. Exiting application");
            System.out.println("Exiting the application...");
            System.exit(0);
        }

        // Find the node with the given name
        try {
            return graph.getNodes().stream()
                    .filter(node -> node.getName().equalsIgnoreCase(nodeName))
                    .findFirst()
                    .orElseThrow(() -> {
                        LOGGER.error("Node with name {} not found.", nodeName);
                        return new IllegalArgumentException("Node with name " + nodeName + " not found.");
                    });
        } catch (Exception e) {
            LOGGER.error("Invalid node name entered. Error: {}", e.getMessage());
            System.out.println("Invalid node name entered. Please try again.");
            return getNodeFromUserInput();  // If the node name is invalid, prompt again
        }
    }
    @Override
    public Node promptNode(String type) {
        promptNodeMessage(type);
        return getNodeFromUserInput();
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
    private void displayConnections() {
        System.out.println("Connections:");
        graph.getNodes().forEach(node -> {
            node.getEdges().forEach(edge -> {
                System.out.println("Node " + node.getId() + " is connected to node " + edge.getDestination().getId() + "with a distance of " + edge.getDistance());
            });
        });
    }
}

