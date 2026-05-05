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
            if (bestGuard == -1 || bestCoverage == 0) {
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
        
        // Remove guards that are not needed after greedy solution.
        removeRedundantGuards(polygon, epsilon, guards);

        // Return final set of guards.
        return guards;
    }

    // Post processing step.
    // Remove a guard if all vertices are still covered without it.
    private static void removeRedundantGuards(Polygon polygon, double epsilon, Set<Integer> guards) {
        // Copy to safely iterate while modifying guards.
        List<Integer> guardList = new ArrayList<>(guards);

        // Removing each guard one at a time.
        for (int guard : guardList) {
            guards.remove(guard);

            // If removing guard makes vertex uncovered, add it back.
            if (!allVerticesCovered(polygon, epsilon, guards)) {
                guards.add(guard);
            }
        }
    }

    // Check every vertex is covered by at least one guard.
    private static boolean allVerticesCovered(Polygon polygon, double epsilon, Set<Integer> guards) {
        int n = polygon.size();

        // Check every vertex in the polygon.
        for (int vertex = 0; vertex < n; vertex++) {
            boolean covered = false;

            // A vertex is covered if any guard can epsilon robustly see it.
            for (int guard : guards) {
                if (VisibilityChecker.epsilonRobustVisibility(polygon, guard, vertex, epsilon)) {
                    covered = true;
                    break;
                }
            }

            // If no guard covers this vertex, the guard set is invalid.
            if (!covered) {
                return false;
            }
        }

        // Every vertex was covered.
        return true;
    }
}