import java.util.*;

// Simple class to represent a polygon as a list of vertices/points.
public class Polygon {

    // Store all vertices of the polygon in boundary order.
    private List<Point> vertices;

    // Constructor to initialize the polygon with a list of vertices.
    public Polygon(List<Point> vertices) {
        this.vertices = vertices;
    }

    // Get the list of vertices of the polygon.
    public List<Point> getVertices() {
        return vertices;
    }

    // Return the number of vertices/points in the polygon.
    public int size() {
        return vertices.size();
    }

    // Get the vertex/point at a specific index in the polygon.
    public Point get(int index) {
        return vertices.get(index);
    }
}