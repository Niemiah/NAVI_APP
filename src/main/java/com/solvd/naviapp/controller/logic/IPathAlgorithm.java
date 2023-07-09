package com.solvd.naviapp.controller.logic;

import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;

public interface IPathAlgorithm {
    Path getPath(Graph graph, Node source, Node target);
}
