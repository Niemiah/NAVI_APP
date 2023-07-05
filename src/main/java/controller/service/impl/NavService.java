package controller.service.impl;

import bin.Graph;
import bin.Node;
import bin.Path;
import controller.logic.IGraphFactory;

import controller.logic.IPathAlgorithm;
import controller.logic.implDijkstra.PathAlgorithm;
import controller.logic.implFacotry.GraphFactory;
import controller.service.INavService;

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
