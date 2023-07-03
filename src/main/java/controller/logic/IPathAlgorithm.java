package controller.logic;

import bin.Graph;
import bin.Node;
import bin.Path;

public interface IPathAlgorithm {
    Path getPath(Graph graph, Node source, Node target);
}
