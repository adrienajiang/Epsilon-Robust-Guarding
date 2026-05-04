// Collection of geometric helper functions.
public class GeometryUtils {

    // Compute the orientation of three points a, b, c.
    // Returns positive if clockwise/right, negative if counterclockwise/left and zero if collinear.
    public static double orientation(Point a, Point b, Point c) {
        // Cross product.
        return (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
    }

    // Check if point b lies on the line segment ac.
    public static boolean onSegment(Point a, Point b, Point c) {
        // Check if b's x is between a and c.
        // Check if b's y is between a and c.
        // Both checks must be true.
        return b.x <= Math.max(a.x, c.x) && b.x >= Math.min(a.x, c.x) && b.y <= Math.max(a.y, c.y) && b.y >= Math.min(a.y, c.y);
    }

    // Check if two line segments p1q1 and p2q2 intersect.
    public static boolean segmentsIntersect(Point p1, Point q1, Point p2, Point q2) {
        // Compute orientations for the four combinations of the points.
        double orientation1 = orientation(p1, q1, p2);
        double orientation2 = orientation(p1, q1, q2);
        double orientation3 = orientation(p2, q2, p1);
        double orientation4 = orientation(p2, q2, q1);

        // General case: segments intersect if orientations are different/opposite.
        if (orientation1 * orientation2 < 0 && orientation3 * orientation4 < 0) {
            return true;
        }

        // Special Cases: Check for collinearity and if the points lies on the segments.

        // Check if p2 lies on segment p1q1.
        if (Math.abs(orientation1) < 1e-9 && onSegment(p1, p2, q1)) {
            return true;
        }

        // Check if q2 lies on segment p1q1.
        if (Math.abs(orientation2) < 1e-9 && onSegment(p1, q2, q1)) {
            return true;
        }

        // Check if p1 lies on segment p2q2.
        if (Math.abs(orientation3) < 1e-9 && onSegment(p2, p1, q2)) {
            return true;
        }

        // Check if q1 lies on segment p2q2.
        if (Math.abs(orientation4) < 1e-9 && onSegment(p2, q1, q2)) {
            return true;
        }

        // If none of the cases, segments do not intersect.
        return false;
    }

    // Compute shortest distance from point p to line segment ab.
    public static double distancePointToSegment(Point p, Point a, Point b) {
        // Direction vector from a to b.
        double dx = b.x - a.x;
        double dy = b.y - a.y;

        // If a and b are the same point, return distance from p to a.
        if (dx == 0 && dy == 0) {
            return p.euclideanDistance(a);
        }

        // Project point p onto the line defined by a and b, then clamp to segment.
        double t = ((p.x - a.x) * dx + (p.y - a.y) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));

        // Compute projection point.
        Point projection = new Point(a.x + t * dx, a.y + t * dy);

        // Return the distance from p to that projection point.
        return p.euclideanDistance(projection);
    }
}