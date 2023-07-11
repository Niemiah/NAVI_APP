package com.solvd.naviapp.bin;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private List<Graph> graphList;

    public Client() {
        this.graphList = new ArrayList<>();
    }

    public Client(String name) {
        this.name = name;
        this.graphList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Graph> getGraphList() {
        return graphList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGraphList(List<Graph> graphList) {
        this.graphList = graphList;
    }

    public void addGraph(Graph graph){
        this.graphList.add(graph);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", graphList=" + graphList +
                '}';
    }
}
