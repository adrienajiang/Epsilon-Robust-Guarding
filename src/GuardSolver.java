import java.util.*;

// Compute the set of guards using a greedy algorithm.
public class GuardSolver {

    // Greedy algorithm to compute a set of guards.
    public static Set<Integer> greedyGuards(Polygon polygon, double epsilon) {
        // Number of vertices in the polygon.
        int n = polygon.size();

        // Set of vertices that are not covered by any guard.
        Set<Integer> uncovered = new HashSet<>();
        // Initially, all vertices are uncovered.
        for (int i = 0; i < n; i++) {
            uncovered.add(i);
        }

        // Stores the chosen guards.
        Set<Integer> guards = new LinkedHashSet<>();

        // Continue until all vertices are covered.
        while (!uncovered.isEmpty()) {
            // Track the best guard candidate.
            int bestGuard = -1;
            // Track how many vertices the guard can see.
            int bestCoverage = -1;

            // Check each candidate guard vertex.
            for (int candidate = 0; candidate < n; candidate++) {
                // Count how many uncovered vertices this candidate can see.
                int coverage = 0;

                // Check visibility to each uncovered vertex.
                for (int vertex : uncovered) {
                    // If candidate can epsilon robustly see this vertex.
                    if (VisibilityChecker.epsilonRobustVisibility(polygon, candidate, vertex, epsilon)) {
                        // Increase count.
                        coverage++;
                    }
                }

                // See if this candidate is better than the best so far.
                if (coverage > bestCoverage) {
                    // Update best coverage and best guard if this candidate is better.
                    bestCoverage = coverage;
                    bestGuard = candidate;
                }
            }

            // If no guard can see any uncovered vertex, break out of the loop.
            if (bestGuard == -1) {
                break;
            }

            // Add the chosen guard to the set of guards. 
            guards.add(bestGuard);

            // Remove all vertices that this guard can see from the uncovered set.
            Iterator<Integer> iterator = uncovered.iterator();
            // Check each uncovered vertex.
            while (iterator.hasNext()) {
                // Get the next uncovered vertex.
                int vertex = iterator.next();

                // If guard can see vertex, its covered.
                if (VisibilityChecker.epsilonRobustVisibility(polygon, bestGuard, vertex, epsilon)) {
                    // Remove from uncovered set.
                    iterator.remove();
                }
            }
        }

        // Return final set of guards.
        return guards;
    }
}