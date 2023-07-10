package com.solvd.naviapp.controller.services;

import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;

public interface INavService {
    // returns list of randomly generated Nodes
    Graph getGraph();
    // returns optimal Path based on client input and Graph
    Path getPath(Node source, Node target, Graph graph);
}
