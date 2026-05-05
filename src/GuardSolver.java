import java.util.*;

public class GuardSolver {

    public static Set<Integer> greedyGuards(Polygon polygon, double epsilon) {
        int n = polygon.size();

        Set<Integer> uncovered = new HashSet<>();
        for (int i = 0; i < n; i++) {
            uncovered.add(i);
        }

        Set<Integer> guards = new LinkedHashSet<>();

        while (!uncovered.isEmpty()) {
            int bestGuard = -1;
            int bestCoverage = -1;

            for (int candidate = 0; candidate < n; candidate++) {
                int coverage = 0;

                for (int vertex : uncovered) {
                    if (VisibilityChecker.epsilonRobustVisibility(polygon, candidate, vertex, epsilon)) {
                        coverage++;
                    }
                }

                if (coverage > bestCoverage) {
                    bestCoverage = coverage;
                    bestGuard = candidate;
                }
            }

            if (bestGuard == -1) {
                break;
            }

            guards.add(bestGuard);

            Iterator<Integer> iterator = uncovered.iterator();
            while (iterator.hasNext()) {
                int vertex = iterator.next();

                if (VisibilityChecker.epsilonRobustVisibility(polygon, bestGuard, vertex, epsilon)) {
                    iterator.remove();
                }
            }
        }

        return guards;
    }
}