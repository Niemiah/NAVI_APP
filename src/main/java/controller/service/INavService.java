package controller.service;

import bin.Graph;
import bin.Node;
import bin.Path;
import java.util.List;

public interface INavService {
    // returns list of randomly generated Nodes
    Graph getGraph();
    // returns optimal Path based on client input and Graph
    Path getPath(Node source, Node target, Graph graph);
}
