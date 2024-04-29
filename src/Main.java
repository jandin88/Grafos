
import util.graph.GraphsListAdjacency;
import util.graph.GraphsMatrizAdjacency;
import util.graph.Vertex;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Digite (l) para Lista e (M) para usar matriz de adjacency ");
        String response = sc.nextLine();
        processingResponse(response);
        sc.close();
    }

    private static void processingResponse(String response) {
        while (true) {
            if (response.equalsIgnoreCase("l")) {
                GraphsListAdjacency gl=new GraphsListAdjacency();
                gl.readGraph("src/util/file/grafosFile", gl);
                List<Vertex> vertexList=gl.DFS(1);

                gl.writeInfoGrafo("BFSLista.txt",gl.BFS(1));
                gl.writeInfoGrafo("DFSLIsta.txt",vertexList);

                System.out.println(gl.distanceBetweenVertices(150,750));
                System.out.println(gl.diameterOfGraph(150,750));

                break;
            } else if (response.equalsIgnoreCase("m")) {
                // Implemente a lógica para lidar com o uso de uma matriz de adjacência aqui
                GraphsMatrizAdjacency gm=new GraphsMatrizAdjacency();
                gm.readGraph("src/util/file/grafosFile",gm);

                List<Vertex> vertexListDFS=gm.DFS(1678);
                List<Vertex> vertexListBFS=gm.BFS(1678);

                gm.writeInfoGrafo("DFSMatriz.txt",vertexListDFS);
                gm.writeInfoGrafo("BFSMatriz.txt",vertexListBFS);
                System.out.println();

                System.out.println(gm.diameterOfGraph(1678,6007));
                System.out.println(gm.distanceBetweenVertices(1678,6007));
                break;
            } else {
                System.out.println("Digite M ou L");
                response = sc.nextLine();
            }
        }
    }
}
