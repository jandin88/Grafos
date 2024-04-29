package util.graph;

import util.ReadAndWriteFile;

import java.util.*;

public class GraphsListAdjacency extends ReadAndWriteFile implements GraphRepresentation {

    private LinkedList<Integer>[]adjList;
    private int vertices;


    @Override
    public void addEdge(int source, int target) {
        adjList[source].add(target);
        adjList[target].add(source);
    }

    @Override
    public void quantityVertices(int quantityVertice) {
        this.vertices = quantityVertice;
        adjList=new LinkedList[quantityVertice];
        for (int i = 0; i <quantityVertice; i++) {
            adjList[i]=new LinkedList<>();
        }
    }

    public List<Vertex>BFS(int startVertex) {
        boolean[] visited = new boolean[vertices];
        List<Vertex> vertexList = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] level = new int[vertices];
        int[] parent=new int[vertices];

        visited[startVertex] = true;
        queue.add(startVertex);
        level[startVertex] = 0;
        parent[startVertex]=-1;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            vertexList.add(new Vertex(currentVertex,parent[currentVertex],level[currentVertex]));
            for (int vertex : adjList[currentVertex]) {
                if (!visited[vertex]) {
                    visited[vertex] = true;
                    parent[vertex]=currentVertex;
                    level[vertex]=level[currentVertex]+1;
                    queue.add(vertex);
                }
            }
        }
        return vertexList;
    }

    public List<Vertex> DFS(int startV) {
        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<>();
        List<Vertex>vertexList=new ArrayList<>();
        int[] level = new int[vertices];
        int[] parent=new int[vertices];

        stack.push(startV);
        level[startV] = 0;
        parent[startV]=-1;

        while (!stack.empty()) {
            int currentVertex =  stack.pop();
            if (!visited[currentVertex]) {
                visited[currentVertex] = true;
                vertexList.add(new Vertex(currentVertex, parent[currentVertex], level[currentVertex]));
                for (int vertex : adjList[currentVertex]) {
                    if (!visited[vertex]) {
                        parent[vertex] = currentVertex;
                        level[vertex] = level[currentVertex] + 1;
                        stack.push(vertex);
                    }
                }
            }
        }
        return vertexList;
    }

    public int distanceBetweenVertices(int startVertex, int endVertex) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new ArrayDeque<>();
        int[] distance = new int[vertices];

        visited[startVertex] = true;
        queue.add(startVertex);
        distance[startVertex] = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            for (int neighbor : adjList[currentVertex]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distance[neighbor] = distance[currentVertex] + 1;
                    queue.add(neighbor);
                }
            }
        }
        return distance[endVertex];
    }

    public int diameterOfGraph(int startVertex, int endVertex) {
        int maxDistance = 0;
        int[] distanceStart = distanceVertex(startVertex);
        int[] distanceEnd = distanceVertex(endVertex);

        for (int i = 0; i < vertices; i++) {
            int distance = distanceStart[i] + distanceEnd[i];
            maxDistance = Math.max(maxDistance, distance);
        }
        return maxDistance;
    }

    private int[] distanceVertex(int startVertex) {
        boolean[] visited = new boolean[vertices];
        int[] distance = new int[vertices];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startVertex);
        visited[startVertex] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            for (int neighbor : adjList[currentVertex]) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                    distance[neighbor] = distance[currentVertex] + 1;
                }
            }
        }
        return distance;
    }

    @Override
    public int countVertices() {
        return vertices-1;
    }

    @Override
    public int countEdge() {
        int count=0;
        for(int i=0;i<vertices;i++){
            count+=adjList[i].size();
        }
        return count/2;
    }

    @Override
    public int minDegree() {
        int min= Integer.MAX_VALUE;
        for(int i =1;i<vertices;i++){
            min=Math.min(min,adjList[i].size());
        }
        return min;
    }

    @Override
    public int maxDegree() {
        int max=0;
        for(int i=0;i<vertices;i++){
            max=Math.max(max,adjList[i].size());
        }
        return max;
    }

    @Override
    public int avgDegree() {
        return (2*countEdge())/vertices;
    }

    @Override
    public List<List<Integer>> connectedComponents() {

        boolean[] visited = new boolean[vertices];
        List<List<Integer>> components = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                Stack<Integer> stack = new Stack<>();
                stack.push(i);
                visited[i] = true;

                while (!stack.isEmpty()) {
                    int vertex = stack.pop();
                    component.add(vertex);
                    for (int neighbor : adjList[vertex]) {
                        if (!visited[neighbor]) {
                            stack.push(neighbor);
                            visited[neighbor] = true;
                        }
                    }
                }
                components.add(component);
            }
        }

        return components;
    }
}
