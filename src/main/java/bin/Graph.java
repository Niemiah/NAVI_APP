package bin;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Graph {
    private int id;
    private List<Node> nodes;

    public Graph() {
        nodes = new ArrayList<>();
    }

    public Graph(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return Objects.equals(nodes, graph.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "id=" + id +
                ", nodes=" + nodes +
                '}';
    }
}