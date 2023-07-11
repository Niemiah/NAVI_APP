package com.solvd.naviapp.db;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.db.implMyBatis.services.DbService;
import com.solvd.naviapp.db.implMyBatis.services.NodeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestService {

    public static void main(String[] args) {
        IDbService dbService = new DbService();
        INavService navService = new NavService();
        Graph graph = navService.getGraph();
        Path path = navService.getPath(graph.getNodes().get(0), graph.getNodes().get(4), graph);
        graph.setPath(path);
        Client client = new Client("Art");
        client.addGraph(graph);
        System.out.println(client);
        int id = dbService.saveClient(client);
        Client clientRetrieved = dbService.getClientById(id);
        System.out.println(clientRetrieved);
    }
}