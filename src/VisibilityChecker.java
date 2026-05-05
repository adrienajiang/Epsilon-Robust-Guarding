public class VisibilityChecker {

    // Check if vertex i can see vertex j using standard (non-epsilon) visibility.
    public static boolean standardVisibility(Polygon polygon, int i, int j) {

        // A vertex can always see itself.
        if (i == j) {
            return true;
        }

        // Get the two points corresponding to the vertices.
        Point a = polygon.get(i);
        Point b = polygon.get(j);

        // Get the number of vertices in the polygon.
        int n = polygon.size();

        // Check if the segment ab intersect any polygon edge.
        for (int k = 0; k < n; k++) {
            // Get the next vertex index in a boundary order.
            int next = (k + 1) % n;

            // Skip edges that share endpoints with segment ab.
            // Important or else falsely detect intersection at the endpoints.
            if (k == i || k == j || next == i || next == j) {
                continue;
            }

            // Get edge endpoints.
            Point c = polygon.get(k);
            Point d = polygon.get(next);

            // If segment ab intersects edge cd, then i and j cannot see each other.
            if (GeometryUtils.segmentsIntersect(a, b, c, d)) {
                return false;
            }
        }

        // Check the segment of visibility is inside the polygon.
        // Compute the midpoint of the segment ab.
        Point mid = new Point((a.x + b.x) / 2.0, (a.y + b.y) / 2.0);

        // If midpoint is not inside the polygon, then i and j cannot see each other.
        if (!isPointInsidePolygon(polygon, mid)) {
            return false;
        }

        // No intersections and midpoint is inside, then true!
        return true;
    }

    // Check if a point p is inside the polygon.
    private static boolean isPointInsidePolygon(Polygon polygon, Point p) {
        // Number of intersections.
        int count = 0;
        // Get the number of vertices in the polygon.
        int n = polygon.size();
        // Create a point far to the right of p.
        Point extreme = new Point(1e9, p.y);

        // Count how many times the ray from p to extreme intersects the polygon edges.
        for (int i = 0; i < n; i++) {
            // Get the next vertex index in boundary order.
            int next = (i + 1) % n;
            // Get edge endpoints.
            Point a = polygon.get(i);
            Point b = polygon.get(next);
            // Increment count if the ray intersects edge ab.
            if (GeometryUtils.segmentsIntersect(p, extreme, a, b)) {
                count++;
            }
        }

        // If count is odd, point is inside.
        // If count is even, point is outside.
        return (count % 2 == 1);
    }

    // Check epsilon robust visibility!
    public static boolean epsilonRobustVisibility(Polygon polygon, int i, int j, double epsilon) {
        
        // Check standard visibility.
        // If not visible, then definitely not epsilon robust visible.
        if (!standardVisibility(polygon, i, j)) {
            return false;
        }

        // Get the two points corresponding to the vertices.
        Point a = polygon.get(i);
        Point b = polygon.get(j);

        // Check every other vertex.
        for (int k = 0; k < polygon.size(); k++) {
            // Skip endpoints.
            if (k == i || k == j) {
                continue;
            }

            // Get the other vertex and compute the distance from it to segment ab.
            Point other = polygon.get(k);
            double distance = GeometryUtils.distancePointToSegment(other, a, b);

            // If any vertex is too close, blocks visibility.
            if (distance < epsilon) {
                return false;
            }
        }

        // If no vertex blocks within epsilon, then i and j are epsilon robust visible!
        return true;
    }
}