package com.solvd.naviapp.controller.services.impl;

import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.logic.IGraphFactory;

import com.solvd.naviapp.controller.logic.IPathAlgorithm;
import com.solvd.naviapp.controller.logic.implDijkstra.PathAlgorithm;
import com.solvd.naviapp.controller.logic.implFacotry.GraphFactory;
import com.solvd.naviapp.controller.services.INavService;

public class NavService implements INavService {

    @Override
    public Graph getGraph() {
        IGraphFactory nodeFactory = new GraphFactory();
        return nodeFactory.generateGraph();
    }

    @Override
    public Path getPath(Node source, Node target, Graph graph) {
        IPathAlgorithm iPathAlgorithm = new PathAlgorithm();
        Path path = iPathAlgorithm.getPath(graph, source,target);

        return path;
    }
}
