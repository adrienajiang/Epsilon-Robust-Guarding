public class GeometryUtils {

    public static double orientation(Point a, Point b, Point c) {
        return (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
    }

    public static boolean onSegment(Point a, Point b, Point c) {
        return b.x <= Math.max(a.x, c.x) && b.x >= Math.min(a.x, c.x) && b.y <= Math.max(a.y, c.y) && b.y >= Math.min(a.y, c.y);
    }

    public static boolean segmentsIntersect(Point p1, Point q1, Point p2, Point q2) {
        double o1 = orientation(p1, q1, p2);
        double o2 = orientation(p1, q1, q2);
        double o3 = orientation(p2, q2, p1);
        double o4 = orientation(p2, q2, q1);

        if (o1 * o2 < 0 && o3 * o4 < 0) {
            return true;
        }

        if (Math.abs(o1) < 1e-9 && onSegment(p1, p2, q1)) {
            return true;
        }
        if (Math.abs(o2) < 1e-9 && onSegment(p1, q2, q1)) {
            return true;
        }
        if (Math.abs(o3) < 1e-9 && onSegment(p2, p1, q2)) {
            return true;
        }
        if (Math.abs(o4) < 1e-9 && onSegment(p2, q1, q2)) {
            return true;
        }

        return false;
    }

    public static double distancePointToSegment(Point p, Point a, Point b) {
        double dx = b.x - a.x;
        double dy = b.y - a.y;

        if (dx == 0 && dy == 0) {
            return p.euclideanDistance(a);
        }

        double t = ((p.x - a.x) * dx + (p.y - a.y) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));

        Point projection = new Point(a.x + t * dx, a.y + t * dy);
        return p.euclideanDistance(projection);
    }
}