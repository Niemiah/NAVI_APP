package com.solvd.naviapp;

import java.util.List;
import java.util.Scanner;

import com.solvd.naviapp.controller.logic.IGraphFactory;
import com.solvd.naviapp.controller.logic.implFactory.GraphFactory;
import com.solvd.naviapp.user.impl.UserInterface;
import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.db.implMyBatis.services.DbService;
import com.solvd.naviapp.utilities.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class main {

    public static void main(String[] args) {
        IGraphFactory graphFactory = new GraphFactory();
        Graph graphList = graphFactory.generateGraph();
        List<Node> nodeList = graphList.getNodes();
        nodeList.forEach(node -> {
            System.out.println(node.getName());;
        });
        System.out.println("-----------------------------------");
        Utilities.nodeSort(nodeList);
        nodeList.forEach(node -> {
            System.out.println(node.getName());;
        });



    }
}
