package util.graph;


import util.ReadAndWriteFile;

import java.util.*;

public class GraphsMatrizAdjacency extends ReadAndWriteFile implements GraphRepresentation {

    private int[][] adjMatriz;
    private int vertices;

    @Override
    public void addEdge(int source, int target) {
        adjMatriz[source][target] = 1;
        adjMatriz[target][source] = 1;
    }

    @Override
    public void quantityVertices(int quantityVertices) {
        this.vertices = quantityVertices;
        adjMatriz = new int[quantityVertices][quantityVertices];
    }

    public List<Vertex> BFS(int startVertex) {
        List<Vertex> vertexList = new ArrayList<>();
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new ArrayDeque<>();
        int[] level = new int[vertices];
        int[] parent = new int[vertices];

        queue.add(startVertex);
        visited[startVertex] = true;
        level[startVertex] = 0;
        parent[startVertex] = -1;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            System.out.print(currentVertex + " ");
            vertexList.add(new Vertex(currentVertex, parent[currentVertex], level[currentVertex]));
            for (int vertex = 0; vertex < vertices; vertex++) {
                if (adjMatriz[currentVertex][vertex] == 1 && !visited[vertex]) {
                    queue.add(vertex);
                    visited[vertex] = true;
                    parent[vertex] = currentVertex;
                    level[vertex] = level[currentVertex] + 1;
                }
            }
        }
        return vertexList;
    }

    public List<Vertex> DFS(int startVertex) {
        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<>();
        List<Vertex> vertexList = new ArrayList<>();
        int[] level = new int[vertices];
        int[] parent = new int[vertices];

        vertexList.add(new Vertex(startVertex, -1, 0));
        stack.push(startVertex);
        level[startVertex] = 0;
        parent[startVertex] = -1;

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();
            if (!visited[currentVertex]) {
                visited[currentVertex] = true;
                vertexList.add(new Vertex(currentVertex, parent[currentVertex], level[currentVertex]));
                for (int vertex = 0; vertex < adjMatriz[currentVertex].length; vertex++) {
                    if (adjMatriz[currentVertex][vertex] == 1 && !visited[vertex]) {
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
        int[] distance = new int[vertices];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startVertex);
        visited[startVertex] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            if (currentVertex == endVertex) {
                return distance[currentVertex];
            }
            for (int vertex = 0; vertex < vertices; vertex++) {
                if (adjMatriz[currentVertex][vertex] == 1 && !visited[vertex]) {
                    queue.add(vertex);
                    visited[vertex] = true;
                    distance[vertex] = distance[currentVertex] + 1;
                }
            }
        }
        return -1;
    }

    public int diameterOfGraph(int startVertex, int endVertex) {
        int maxDistance = 0;
        int[] distanceStart = distanceVertice(startVertex);
        int[] distanceEnd = distanceVertice(endVertex);

        for (int i = 0; i < vertices; i++) {
            if (distanceStart[i] != -1 && distanceEnd[i] != -1) {
                int distance = distanceStart[i] + distanceEnd[i];
                maxDistance = Math.max(maxDistance, distance);
            }
        }
        return maxDistance;
    }

    private int[] distanceVertice(int startVertex) {
        boolean[] visited = new boolean[vertices];
        int[] distance = new int[vertices];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startVertex);
        visited[startVertex] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            for (int vertex = 0; vertex < vertices; vertex++) {
                if (adjMatriz[currentVertex][vertex] == 1 && !visited[vertex]) {
                    queue.add(vertex);
                    visited[vertex] = true;
                    distance[vertex] = distance[currentVertex] + 1;
                }
            }
        }
        return distance;
    }


    @Override
    public int countVertices() {
        return vertices;
    }

    @Override
    public int countEdge() {
        return (int) Arrays.stream(adjMatriz).count();
    }

    @Override
    public int minDegree() {
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < vertices; i++) {
            min = Math.min(min, adjMatriz[i].length);
        }
        return min;
    }

    @Override
    public int maxDegree() {
        int max = 0;
        for (int i = 0; i < vertices; i++) {
            max = Math.max(max, adjMatriz[i].length);
        }
        return max;
    }

    @Override
    public int avgDegree() {
        return (2 * countEdge()) / vertices;
    }

    @Override
    public List<List<Integer>> connectedComponents() {
        boolean[] visited = new boolean[vertices];
        List<List<Integer>> connectedComponents = new ArrayList<>();
        for (int v = 0; v < vertices; v++) {
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                DFS(v, visited, component);
                connectedComponents.add(component);
            }
        }
        connectedComponents.sort((a, b) -> b.size() - a.size()); // Ordena por tamanho decrescente
        return connectedComponents;
    }

    private void DFS(int v, boolean visited[], List<Integer> component) {
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        visited[v] = true; // Marcamos o vértice atual como visitado ao colocá-lo na pilha
        while (!stack.isEmpty()) { // Corrigido: verificar se a pilha não está vazia
            int current = stack.pop();
            component.add(current);
            for (Integer neighbor : adjMatriz[current]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true; // Marcamos os vizinhos não visitados antes de adicioná-los à pilha
                    stack.push(neighbor);
                }
            }
        }
    }
}