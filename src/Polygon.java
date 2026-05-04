import java.util.*;

public class Polygon {
    private List<Point> vertices;

    public Polygon(List<Point> vertices) {
        this.vertices = vertices;
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public int size() {
        return vertices.size();
    }

    public Point get(int index) {
        return vertices.get(index);
    }
}