package com.solvd.naviapp;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.controller.logic.IGraphFactory;
import com.solvd.naviapp.controller.logic.IPathAlgorithm;
import com.solvd.naviapp.controller.logic.implDijkstra.PathAlgorithm;
import com.solvd.naviapp.controller.logic.implFactoryNoId.GraphFactory;
import com.solvd.naviapp.db.IDbService;
import com.solvd.naviapp.db.implMyBatis.services.DbService;

public class main {
    public static void main(String[] args) {
        IDbService dbService = new DbService();
        IGraphFactory graphFactory = new GraphFactory();
        IPathAlgorithm pathAlgorithm = new PathAlgorithm();
        Client client = new Client();

        System.out.println(graphFactory.generateGraph());

//         creating graph, path, client
//        Graph graph =graphFactory.generateGraph();
//        Node source = graph.getNodes().get(0);
//        Node target = graph.getNodes().get(4);
//        graph.setPath(pathAlgorithm.getPath(graph,source,target));
//        client.setName("Art");
//        client.addGraph(graph);
//
//        System.out.println(client);
//        System.out.println(dbService.saveClient(client));
//        System.out.println(dbService.getClientById(1));
    }
}