public class Point {
    // x-coordinate of the point.
    public double x;
    // y-coordinate of the point.
    public double y;

    // Constructor to initialize the point with give x and y coordinates.
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Compute the Euclidean distance from this point to another point.
    public double euclideanDistance(Point other) {
        // Difference in x and y coordinates.
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        // Distance formula: sqrt(dx^2 + dy^2).
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Overrides default toString method to provide a string representation of the point.
    @Override
    public String toString() {
        // Format the point as (x, y).
        return "(" + x + ", " + y + ")";
    }
}