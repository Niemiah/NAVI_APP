package user.impl;
import com.solvd.naviapp.db.IDbService;
import user.IUserInterface;
import user.IUserOutputService;
import user.IUserInputService;
import com.solvd.naviapp.db.IClientService;
import com.solvd.naviapp.db.implMyBatis.services.ClientService;
import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.db.implMyBatis.services.DbService;
import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Scanner;

public class UserInterface implements IUserInterface {
    private static final Logger LOGGER = LogManager.getLogger(UserInterface.class);
    private final INavService navService;
    private final IClientService clientService;
    private final IDbService dbService;
    private Graph graph;
    private Scanner scanner;
    private Client client;

    //welcome and goodbye messages
    private final String welcomeMessage = "Welcome to the shortest path finder!";
    private final String goodbyeMessage = "Thank you for using the shortest path finder!";

    public UserInterface() {
        LOGGER.info("Initializing UserInterface.");
        this.navService = new NavService();
        this.clientService = new ClientService();
        this.dbService = new DbService();
        this.scanner = new Scanner(System.in);
    }

    //Starting the UserInterface
    @Override
    public void start() {
        LOGGER.info("Starting the UserInterface.");
        displayWelcomeMessage();

        while(true) { // Add loop for user input validity check
            System.out.println("Are you a new or existing user? press 1 for New User or 2 for Existing User");
            int userType = scanner.nextInt();
            scanner.nextLine();

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
                    continue; // Continue the loop if invalid input
            }
            break; // Break the loop if valid input
        }
    }
//        boolean isNewExecution = promptForExecutionType();

//        if (isNewExecution) {
//            graph = navService.getGraph();
//            client.addGraph(graph);
//            dbService.saveClient(client);
//            displayGraph();
//        } else {
//            graph = client.getGraphList().get(client.getGraphList().size() - 1);
//            displayGraph();
//        }

//        Path path = navService.getPath(sourceNode, destinationNode, graph);
//        displayRoute(path);
//
//        displayGoodbyeMessage();
//    }


    private void handleExistingUser() {
        int existingClientId = getClientId();
        client = dbService.getClientById(existingClientId);
        if (client == null) {
            LOGGER.error("Client with id {} not found.", existingClientId);
            return;
        }

        System.out.println("Welcome back " + client.getName() + "!");

//        graph = client.getGraphList().get(client.getGraphList().size() - 1);
//        displayGraph();

        this.graph = navService.getGraph();

        graph.getNodes().forEach((node -> {
            LOGGER.info("Node: name "+node.getName()+
                    ", x, y coordinates: " +
                    node.getX()+" " +
                    node.getY());

            node.getEdges().forEach((edge) ->{
                LOGGER.info("Edge: from "+
                        edge.getSource().getName()+
                        " to "
                        +edge.getDestination().getName());
            });
        }));

        Node source = promptNode("source"); // Use getNodeFromUserInput via promptNode
        Node target = promptNode("target"); // Use getNodeFromUserInput via promptNode
        Path path = navService.getPath(source, target, graph);
        LOGGER.info("Shortest path from "+
                path.getSource().getName()+
                " to "+path.getTarget().getName()+
                " has distance: "+path.getDistance());
        LOGGER.info("Route:");
        path.getNodeList().forEach(node -> {
            LOGGER.info(node.getName());
        });

        graph.setPath(path);
        client.addGraph(graph);

        int savedClientId = dbService.saveClient(client);
        client = dbService.getClientById(savedClientId);

        displayRoute(path);
        displayGoodbyeMessage();
    }

    private void handleNewUser() {
        String name = getUserName();
        client = new Client(name);
        this.graph = navService.getGraph();

        graph.getNodes().forEach((node -> {
            LOGGER.info("Node: name "+node.getName()+
                    ", x, y coordinates: " +
                    node.getX()+" " +
                    node.getY());

            node.getEdges().forEach((edge) ->{
                LOGGER.info("Edge: from "+
                        edge.getSource().getName()+
                        " to "
                        +edge.getDestination().getName());
            });
        }));

        Node source = promptNode("source"); // Use getNodeFromUserInput via promptNode
        Node target = promptNode("target"); // Use getNodeFromUserInput via promptNode
        Path path = navService.getPath(source, target, graph);
        LOGGER.info("Shortest path from "+
                path.getSource().getName()+
                " to "+path.getTarget().getName()+
                " has distance: "+path.getDistance());
        LOGGER.info("Route:");
        path.getNodeList().forEach(node -> {
            LOGGER.info(node.getName());
        });

        graph.setPath(path);
        client.addGraph(graph);

        int clientId = dbService.saveClient(client);
        client = dbService.getClientById(clientId);
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
