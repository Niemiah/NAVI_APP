package controller;

import bin.Graph;
import bin.Node;
import bin.Path;
import controller.service.INavService;
import controller.service.impl.NavService;


// TODO Check main algo for correctness, review Factory algo, refactor all with SOLID and patterns

public class Test {
    public static void main(String[] args) {
        INavService navService = new NavService();
        Graph graph = navService.getGraph();
        System.out.println(graph);
        Node source = graph.getNodes().get(0);
        Node target = graph.getNodes().get(3);
        Path path = navService.getPath(source, target, graph);
        System.out.println(
                "source " + path.getSource().getId() +
                        " target " + path.getTarget().getId()+
                        " distance " +path.getDistance()
        );

    }
}
