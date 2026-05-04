public class VisibilityChecker {

    public static boolean canSee(Polygon polygon, int i, int j) {
        if (i == j) return true;

        Point a = polygon.get(i);
        Point b = polygon.get(j);
        int n = polygon.size();

        // 1. Check edge intersections (already correct)
        for (int k = 0; k < n; k++) {
            int next = (k + 1) % n;

            if (k == i || k == j || next == i || next == j) {
                continue;
            }

            Point c = polygon.get(k);
            Point d = polygon.get(next);

            if (GeometryUtils.segmentsIntersect(a, b, c, d)) {
                return false;
            }
        }

        // 2. NEW: Check midpoint is inside polygon
        Point mid = new Point((a.x + b.x) / 2.0, (a.y + b.y) / 2.0);

        if (!isPointInsidePolygon(polygon, mid)) {
            return false;
        }

        return true;
    }

    private static boolean isPointInsidePolygon(Polygon polygon, Point p) {
        int count = 0;
        int n = polygon.size();

        Point extreme = new Point(1e9, p.y);

        for (int i = 0; i < n; i++) {
            int next = (i + 1) % n;

            Point a = polygon.get(i);
            Point b = polygon.get(next);

            if (GeometryUtils.segmentsIntersect(p, extreme, a, b)) {
                count++;
            }
        }

        return (count % 2 == 1);
    }

    public static boolean epsilonRobustCanSee(Polygon polygon, int i, int j, double epsilon) {
        if (!canSee(polygon, i, j)) {
            return false;
        }

        Point a = polygon.get(i);
        Point b = polygon.get(j);

        for (int k = 0; k < polygon.size(); k++) {
            if (k == i || k == j) {
                continue;
            }

            Point other = polygon.get(k);
            double distance = GeometryUtils.distancePointToSegment(other, a, b);

            if (distance < epsilon) {
                return false;
            }
        }

        return true;
    }
}