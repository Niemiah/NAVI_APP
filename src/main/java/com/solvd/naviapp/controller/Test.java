package com.solvd.naviapp.controller;

import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.logic.IGraphFactory;
import com.solvd.naviapp.controller.logic.IPathAlgorithm;
import com.solvd.naviapp.controller.logic.implFactoryNoId.GraphFactory;
import com.solvd.naviapp.controller.logic.implDijkstra.PathAlgorithm;

public class Test {
    public static void main(String[] args) {
        IGraphFactory graphFactory = new GraphFactory();
        Graph graph = graphFactory.generateGraph();
        IPathAlgorithm pathAlgorithm = new PathAlgorithm();
        System.out.println(graph);
        Path path = pathAlgorithm.getPath(graph, graph.getNodes().get(0), graph.getNodes().get(3));
        System.out.println(path);
        }
    }