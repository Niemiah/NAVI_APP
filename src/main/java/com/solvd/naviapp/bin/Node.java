package com.solvd.naviapp.bin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private int id;
    private int x;
    private int y;
    private String name;
    private List<Edge> edges;

    public Node(int id, int x, int y, String name) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public Node(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<>();
    }

    public Node() {
    }

    public int getId() {
        return id;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id && Objects.equals(edges, node.edges);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                ", edges=" + edges +
                '}';
    }
}
