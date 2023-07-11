package user.impl;
import com.solvd.naviapp.db.IDbService;
import user.IUserInterface;
import user.IUserInputService;
import user.IUserOutputService;
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
        navService = new NavService();
        clientService = new ClientService();
        dbService = new DbService();
        scanner = new Scanner(System.in);
    }

    //initializing the UserInterface
    @Override
    public void start() {
        LOGGER.info("Starting the UserInterface.");
        displayWelcomeMessage();
        String userName = getUserName();
        client = dbService.getClientById(userName.hashCode());
        if (client == null) {
            LOGGER.info("Creating new client: {}", userName);
            client = new Client();
            client.setName(userName);
            int clientId = dbService.saveClient(client);
            client = dbService.getClientById(clientId);
        }
        boolean isNewExecution = promptForExecutionType();

        if (isNewExecution) {
            graph = navService.getGraph();
            client.addGraph(graph);
            dbService.saveClient(client);
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
    //
    private void promptNodeMessage(String type) {
        System.out.println("Please select a " + type + " node from the list above by entering the node's number:");
    }

    private Node getNodeFromUserInput() {
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            LOGGER.info("User requested exit. Exiting application");
            System.out.println("Exiting the application...");
            System.exit(0);
        }
        try {
        int nodeNumber = scanner.nextInt();
        return graph.getNodes().stream()
                .filter(node -> node.getId() == nodeNumber)
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.error("Node with id {} not found.", nodeNumber);
                    return new IllegalArgumentException("Node with id " + nodeNumber + " not found.");
                });
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid node number entered. Error: {}", e.getMessage());
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
    private void displayConnections() {
        System.out.println("Connections:");
        graph.getNodes().forEach(node -> {
            node.getEdges().forEach(edge -> {
                System.out.println("Node " + node.getId() + " is connected to node " + edge.getDestination().getId() + "with a distance of " + edge.getDistance());
            });
        });
    }
}
