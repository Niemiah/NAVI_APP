package controller;

import bin.Edge;
import bin.Graph;
import bin.Node;
import bin.Path;
import controller.service.INavService;
import controller.service.impl.NavService;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// TODO
//  Check main algo for correctness +++
//  improve main algo to search for Source-Target Only (WIP)
//  review Factory algo +++
//  refactor all with SOLID  +++
//  patters (WIP)


public class Test {
    public static void main(String[] args) {
        INavService navService = new NavService();
        Graph graph = navService.getGraph();
        System.out.println(graph);
    }
}