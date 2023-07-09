package com.solvd.naviapp.bin;

import java.util.List;

public class Path {
    private int id;
    private Node source;
    private Node target;
    private List<Node> nodeList;
    private int distance;

    public Path(Node source, Node target, List<Node> nodeList, int distance) {
        this.source = source;
        this.target = target;
        this.nodeList = nodeList;
        this.distance = distance;
    }

    public Path() {

    }

    public int getId() {
        return id;
    }

    public Node getSource() {
        return source;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public int getDistance() {
        return distance;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setSource(Node source) {
        this.source = source;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                ", source=" + source +
                ", target=" + target +
                ", nodeList=" + nodeList +
                ", distance=" + distance +
                '}';
    }
}
