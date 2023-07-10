package com.solvd.naviapp.db.implMyBatis.services;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
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
        // getting basic client object
        Client client = clientService.readFromDb(id);
        // getting graphs created by the client
        List<Graph> graphList = graphService.readFromDbByClientId(id);
        graphList.forEach(graph -> {
            // get nodes that belong to the graph
            List <Node> nodeList = nodeService.readFromDbByGraphId(graph.getId());
            graph.setNodes(nodeList);

            // get path that belongs to the graph
            Path path = pathService.readFromDbByGraphId(graph.getId());
            path.setSource(nodeService.readFromDb(pathService.readFromDbSourceId(path.getId())));
            path.setTarget(nodeService.readFromDb(pathService.readFromDbTargetId(path.getId())));
            List<Integer> routeIdList = nodeService.readFromDbByPathId(path.getId());
            List<Node> routeNodeList = new ArrayList<>();
            routeIdList.forEach((nodeId)->{
                routeNodeList.add(nodeService.readFromDb(nodeId));
            });
            path.setNodeList(routeNodeList);
            graph.setPath(path);
        });
        client.setGraphList(graphList);
        LOGGER.info("client retrieved");
        return client;
    }

    @Override
    public int saveClient(Client client) {
        Graph graph = client.getGraphList().get(0);
        // inserting client
        int clientId = clientService.writeToDb(client);
        // inserting graph
        int graphId = graphService.writeToDb(clientId);
        // inserting nodes
        graph.getNodes().forEach(node->{
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
        return clientId;
    }
}
