package com.solvd.naviapp.controller.services.impl;

import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.logic.IGraphFactory;

import com.solvd.naviapp.controller.logic.IPathAlgorithm;
import com.solvd.naviapp.controller.logic.implDijkstra.PathAlgorithm;
import com.solvd.naviapp.controller.logic.implFactoryNoId.GraphFactory;
import com.solvd.naviapp.controller.services.INavService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavService implements INavService {
    private static final Logger LOGGER = LogManager.getLogger(NavService.class);

    @Override
    public Graph getGraph() {
        IGraphFactory nodeFactory = new GraphFactory();
        Graph graph = nodeFactory.generateGraph();
        LOGGER.info("graph generated");
        return graph;
    }

    @Override
    public Path getPath(Node source, Node target, Graph graph) {
        Path path;
        if(source == null|| target == null || graph == null) {
            LOGGER.error("invalid argument");
            throw new IllegalArgumentException("object must be initialized");
    } else {

            IPathAlgorithm iPathAlgorithm = new PathAlgorithm();
            path = iPathAlgorithm.getPath(graph, source, target);
            LOGGER.info("path generated");
        }
        return path;
    }
}
