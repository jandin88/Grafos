package util.graph;

import java.util.List;

public interface GraphRepresentation {
    void addEdge(int vertex1, int vertex2);
    void quantityVertices(int quantityVertices);
    int countVertices();
    int countEdge();
    int minDegree();
    int maxDegree();
    int avgDegree();
    List<List<Integer>> connectedComponents();

}
