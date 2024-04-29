package util;

import util.graph.GraphRepresentation;
import util.graph.Vertex;

import java.io.*;
import java.util.List;

public class ReadAndWriteFile {
    private GraphRepresentation typeGraph;

    public void readGraph(String path, GraphRepresentation typeGraph){
        this.typeGraph=typeGraph;
        try(BufferedReader reader=new BufferedReader(new FileReader(path))){
            String line;
            typeGraph.quantityVertices(Integer.parseInt(reader.readLine())+1);
            while((line= reader.readLine())!=null){
                String[] lineArray =line.split(" ");
                typeGraph.addEdge(Integer.parseInt(lineArray[0]),Integer.parseInt(lineArray[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeInfoGrafo(String outputPath,List<Vertex> vertexList){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write("Número de Vértices: " + typeGraph.countVertices() + "\n");
            writer.write("Número de Arestas: " + typeGraph.countEdge() + "\n");
            writer.write("Grau Mínimo: " + typeGraph.minDegree() + "\n");
            writer.write("Grau Máximo: " + typeGraph.maxDegree() + "\n");
            writer.write("Grau Médio: " + typeGraph.avgDegree() + "\n\n");

            List<List<Integer>> connectedComponents = typeGraph.connectedComponents();
            writer.write("Número de Componentes Conexas: " + connectedComponents.size() + "\n");
            writer.write("Componentes Conexas:\n");
            for (List<Integer> component : connectedComponents) {
                writer.write("Tamanho: " + component.size() + ", Vértices: " + component + "\n");
            }
            writer.newLine();

            for(Vertex v: vertexList){
                String parent = v.getParent()==-1?"Raiz": String.valueOf(v.getParent());
                writer.write("Vertice: " + v.getValue() + ", Pai: " + parent + ", Nivel: " + v.getNivel());
                writer.newLine();
            }
            System.out.println("TXT Criado");

            // Componentes Conexas


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
