package com.solvd.naviapp.db.implMyBatis.services;
import com.solvd.naviapp.bin.*;
import com.solvd.naviapp.db.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
public class DbService implements IDbService {
    private final IClientService clientService = new ClientService();
    private final IGraphService graphService = new GraphService();
    private final INodeService nodeService = new NodeService();
    private final IPathService pathService = new PathService();
    private final IEdgeService edgeService = new EdgeService();
    private static final Logger LOGGER = LogManager.getLogger(DbService.class);

    @Override
    public int saveClient(Client client) {
        int clientId;
        if(client!=null) {
            Graph graph = client.getGraphList().get(0);
            // inserting client
            clientId = clientService.writeToDb(client);
            // inserting graph
            int graphId = graphService.writeToDb(clientId);
            // inserting nodes
            graph.getNodes().forEach(node -> {
                int nodeId = nodeService.writeToDb(node, graphId);
                node.setId(nodeId);
            });
            // inserting edges
            graph.getNodes().forEach(node -> {
                edgeService.writeToDb(node.getEdges());
            });
            // inserting path
            int pathId = pathService.writeToDb(graph.getPath(), graphId);
            graph.getPath().setId(pathId);
            // updating nodes with path and index
            nodeService.updateWithPath(graph.getPath());
            // returns client id, so it can be queried upon next executions
            LOGGER.info("client saved");
        } else {
            LOGGER.error("invalid client argument");
            throw new IllegalArgumentException("client must be initialized");
        }
        return clientId;
    }

    @Override
    public int removeClient(int id) {
        if (id >= 1) {
            clientService.removeFromDb(id);
            LOGGER.info("client removed");
        } else {
            LOGGER.error("invalid id argument");
            throw new IllegalArgumentException("Id must be int >=1");
         }
        return 1;
    }

    public Client getClientById(int id) {
        Client client;
        if (id >= 1) {
            // getting basic client object
            client = clientService.readFromDb(id);
            // getting graphs created by the client
            List<Graph> graphList = graphService.readFromDbByClientId(id);
            // populating graphs
            graphList.forEach(graph -> {
                // setting nodes with reference type fields
                List<Node> nodeList = nodeService.readFromDbByGraphId(graph.getId());
                graph.setNodes(nodeList);
                // setting path with reference type fields
                Path path = pathService.readFromDbByGraphId(graph.getId());
                // setting nodes
                path.setSource(nodeService.readFromDb(pathService.readFromDbSourceId(path.getId())));
                path.setTarget(nodeService.readFromDb(pathService.readFromDbTargetId(path.getId())));

                // setting route list
                List<Integer> routeIdList = nodeService.readFromDbByPathId(path.getId());
                List<Node> routeNodeList = new ArrayList<>();
                routeIdList.forEach((nodeId) -> {
                    routeNodeList.add(nodeService.readFromDb(nodeId));
                });
                path.setNodeList(routeNodeList);
                graph.setPath(path);
            });
            client.setGraphList(graphList);
            LOGGER.info("client retrieved");
        } else {
            LOGGER.error("invalid id argument");
            throw new IllegalArgumentException("Id must be int >=1");
        }
        if (client == null) {
            LOGGER.error("no such client");
        }
        return client;
    }

    @Override
    public int addGraphToClient(Graph graph, int clientId) {
        if (graph != null && clientId >= 1) {
            // inserting graph
            int graphId = graphService.writeToDb(clientId);
            // inserting nodes
            graph.getNodes().forEach(node -> {
                int nodeId = nodeService.writeToDb(node, graphId);
                node.setId(nodeId);
            });
            // inserting edges
            graph.getNodes().forEach(node -> {
                edgeService.writeToDb(node.getEdges());
            });
            // inserting path
            int pathId = pathService.writeToDb(graph.getPath(), graphId);
            graph.getPath().setId(pathId);
            // updating nodes with path and index
            nodeService.updateWithPath(graph.getPath());
            // returns client id, so it can be queried upon next executions
            LOGGER.info("graph saved");
        } else {
            LOGGER.error("invalid client argument");
            throw new IllegalArgumentException("graph must be initialized and Id must be int >=1");
        }
        return 1;
    }
}
