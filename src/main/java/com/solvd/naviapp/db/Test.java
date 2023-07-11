package com.solvd.naviapp.db;

import java.util.Scanner;
import user.impl.UserInterface;
import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.db.implMyBatis.services.DbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test {
    private static final Logger LOGGER = LogManager.getLogger(Test.class);

    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        INavService navService = new NavService();
        IDbService dbService = new DbService();

        //displays the welcome message
        userInterface.displayWelcomeMessage();
        // generates new Graph object that has Nodes with Edges
        Graph graph = navService.getGraph();
        // prints Nodes of the graph
        graph.getNodes().forEach((node -> {
            LOGGER.info("Node: name " + node.getName() +
                    ", x, y coordinates: " +
                    node.getX() + " " +
                    node.getY());

            node.getEdges().forEach((edge) -> {
                LOGGER.info("Edge: from " +
                        edge.getSource().getName() +
                        " to "
                        + edge.getDestination().getName());
            });
        }));
        LOGGER.info("-------------------------------------------------------------");

        // Get source and target node from the user
        Scanner scanner = new Scanner(System.in);
        LOGGER.info("Enter the source node: ");
        int sourceIndex = scanner.nextInt();
        LOGGER.info("Enter the target node: ");
        int targetIndex = scanner.nextInt();

        // Check if the indices are valid
        if (sourceIndex >= 0 && sourceIndex < graph.getNodes().size() &&
                targetIndex >= 0 && targetIndex < graph.getNodes().size()) {

            // Assign Source and Target Nodes
            Node source = graph.getNodes().get(sourceIndex);
            Node target = graph.getNodes().get(targetIndex);

            // calculates the shortest path
            Path path = navService.getPath(source, target, graph);
            LOGGER.info("Shortest path from " +
                    path.getSource().getName() +
                    " to " + path.getTarget().getName() +
                    " has distance: " + path.getDistance());
            LOGGER.info("Route:");
            path.getNodeList().forEach(node -> {
                LOGGER.info(node.getName());
            });

            LOGGER.info("-------------------------------------------------------------");

            graph.setPath(path);
            Client client = new Client("Sasha");
            client.addGraph(graph);

            // saves Client to DB
            int clientId = dbService.saveClient(client);
            // reads Client to DB
            Client clientRetrieved = dbService.getClientById(clientId);
            LOGGER.info("Client name: " + clientRetrieved.getName() + " id: " + clientRetrieved.getId());
            clientRetrieved.getGraphList().get(0).getNodes().forEach(node -> {
                LOGGER.info("Node: name " + node.getName() +
                        ", x, y coordinates: " +
                        node.getX() + " " +
                        node.getY());
            });

            // creates new Graph
            Graph graph2 = navService.getGraph();
            // Get source2 and target2 nodes from the user
            LOGGER.info("Enter the source2 node: ");
            int source2Index = scanner.nextInt();
            LOGGER.info("Enter the target2 node: ");
            int target2Index = scanner.nextInt();

            // Check if the indices are valid
            if (source2Index >= 0 && source2Index < graph2.getNodes().size() &&
                    target2Index >= 0 && target2Index < graph2.getNodes().size()) {

                Node source2 = graph2.getNodes().get(source2Index);
                Node target2 = graph2.getNodes().get(target2Index);
                Path path2 = navService.getPath(source2, target2, graph2);
                graph2.setPath(path2);
                // adds new Graph to existing Client in DB
                dbService.addGraphToClient(graph2, clientId);

                Client clientRetrieved2 = dbService.getClientById(clientId);
                LOGGER.info("Client name: " + clientRetrieved.getName() + " id: " + clientRetrieved.getId());
                clientRetrieved2.getGraphList().forEach(graph1 -> {
                    LOGGER.info("graph id: " + graph1.getId());
                });

                // removes Client from DB
                dbService.removeClient(clientId);
            } else {
                LOGGER.info("Invalid source or target node index.");
            }
            userInterface.displayGoodbyeMessage();
        }
    }
}