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
    public Client getClientById(int id) {
        Client client = null;
        if (id >= 1) {
            // getting basic client object
            client = clientService.readFromDb(id);
            // getting graphs created by the client
            List<Graph> graphList = graphService.readFromDbByClientId(id);
            graphList.forEach(graph -> {
                // get nodes that belong to the graph
                List<Node> nodeList = nodeService.readFromDbByGraphId(graph.getId());
                graph.setNodes(nodeList);
                // get path that belongs to the graph
                Path path = pathService.readFromDbByGraphId(graph.getId());
                Node sourceNode = nodeService.readFromDb(pathService.readFromDbSourceId(path.getId()));
                Node targetNode = nodeService.readFromDb(pathService.readFromDbTargetId(path.getId()));
                List <Edge> sourceNodeEdges = edgeService.readFromDbBySourceNodeId(sourceNode.getId());
                sourceNodeEdges.forEach(edge -> {
                    edge.setSource(sourceNode);
                    edge.setDestination(nodeService.readFromDb(edgeService.readFromDbDestinationId(edge.getId())));
                });

                sourceNode.setEdges(sourceNodeEdges);

                List <Edge> targetNodeEdges = edgeService.readFromDbBySourceNodeId(targetNode.getId());
                targetNodeEdges.forEach(edge -> {
                    edge.setSource(sourceNode);
                    edge.setDestination(nodeService.readFromDb(edgeService.readFromDbDestinationId(edge.getId())));
                });

                targetNode.setEdges(targetNodeEdges);

                path.setSource(sourceNode);
                path.setTarget(targetNode);
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
        return client;
    }

    @Override
    public int saveClient(Client client) {
        int clientId = -1;
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
}
