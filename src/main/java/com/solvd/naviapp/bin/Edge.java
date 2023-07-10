package com.solvd.naviapp.bin;


import java.util.Objects;

public class Edge {
    private int id;
    private Node source;
    private Node destination;
    private int distance;

    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.distance = weight;
    }

    public Edge() {
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setSource(Node source) {
        this.source = source;
    }


    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return distance == edge.distance && Objects.equals(source, edge.source) && Objects.equals(destination, edge.destination);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, distance);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", sourceId=" + source.getId() +
                ", sourceName=" + source.getName() +
                ", destinationId=" + destination.getId() +
                ", destinationName=" + destination.getName() +
                ", weight=" + distance +
                '}';
    }
}