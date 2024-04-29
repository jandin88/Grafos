package util.graph;


public class Vertex {
    private int value;
    private int parent;
    private int nivel;

    public Vertex(int value, int parent, int nivel) {
        this.value = value;
        this.parent = parent;
        this.nivel = nivel;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

}
