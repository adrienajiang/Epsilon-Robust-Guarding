public class VisibilityChecker {

    public static boolean canSee(Polygon polygon, int i, int j) {
        if (i == j) return true;

        Point a = polygon.get(i);
        Point b = polygon.get(j);
        int n = polygon.size();

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

        return true;
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