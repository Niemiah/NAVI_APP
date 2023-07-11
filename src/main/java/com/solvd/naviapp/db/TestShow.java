package com.solvd.naviapp.db;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.db.implMyBatis.services.DbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestShow {
    private static final Logger LOGGER = LogManager.getLogger(TestShow.class);

    public static void main(String[] args) {
        INavService navService = new NavService();
        IDbService dbService = new DbService();

        // generates new Graph object that has Nodes with Edges
        Graph graph = navService.getGraph();
        // prints Nodes of the graph
        graph.getNodes().forEach((node -> {
            LOGGER.info("Node: name "+node.getName()+
                    ", x, y coordinates: " +
                    node.getX()+" " +
                    node.getY());

            node.getEdges().forEach((edge) ->{
                LOGGER.info("Edge: from "+
                        edge.getSource().getName()+
                        " to "
                        +edge.getDestination().getName()+
                        " Distance: "+edge.getDistance());
            });
        }));
        LOGGER.info("-------------------------------------------------------------");

        // assign Source and Target Nodes
        Node source = graph.getNodes().get(0);
        Node target = graph.getNodes().get(4);

        // calculates the shortest path
        Path path = navService.getPath(source, target,graph);
        LOGGER.info("Shortest path from "+
                path.getSource().getName()+
                " to "+path.getTarget().getName()+
                " has distance: "+path.getDistance());
        LOGGER.info("Route:");
        path.getNodeList().forEach(node -> {
            LOGGER.info(" Node: "+node.getName());
        });

        LOGGER.info("-------------------------------------------------------------");

        graph.setPath(path);
        Client client = new Client("Sasha");
        client.addGraph(graph);

        // saves Client to DB
        int clientId = dbService.saveClient(client);
        // reads Client to DB
        Client clientRetrieved = dbService.getClientById(clientId);
        LOGGER.info("Client name: "+clientRetrieved.getName()+" id: "+clientRetrieved.getId());
        clientRetrieved.getGraphList().get(0).getNodes().forEach(node ->{
            LOGGER.info("Node: name "+node.getName()+
                    ", x, y coordinates: " +
                    node.getX()+" " +
                    node.getY());
        });

        // creates new Graph
        Graph graph2 = navService.getGraph();
        Node source2 = graph2.getNodes().get(1);
        Node target2 = graph2.getNodes().get(5);
        Path path2 = navService.getPath(source2, target2,graph2);
        graph2.setPath(path2);
        // adds new Graph to existing Client in DB
        dbService.addGraphToClient(graph2, clientId);

        Client clientRetrieved2 = dbService.getClientById(clientId);
        LOGGER.info("Client name: "+clientRetrieved.getName()+" id: "+clientRetrieved.getId());
        clientRetrieved2.getGraphList().forEach(graph1 -> {
            LOGGER.info("graph id: "+graph1.getId());
            });

        // removes Client from DB
        dbService.removeClient(clientId);
    }
}
