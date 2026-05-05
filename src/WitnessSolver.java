import java.util.*;

// Compute a set of witness vertices.
public class WitnessSolver {

    // Greedy algorithm to compute a set of witnesses.
    public static Set<Integer> greedyWitnesses(Polygon polygon, double epsilon) {
        // Number of vertices in the polygon.
        int n = polygon.size();

        // Stores chosen witnesses!
        Set<Integer> witnesses = new LinkedHashSet<>();

        // Try adding each vertex as a potential witness.
        for (int candidate = 0; candidate < n; candidate++) {
            // Assume candidate is independent/valid witness.
            boolean independent = true;

            // Try adding each vertex as a potential witness.
            for (int existingWitness : witnesses) {

                // If two vertices can see each other, then one guard could cover both, so they cannot both be witnesses.
                if (VisibilityChecker.epsilonRobustVisibility(polygon, candidate, existingWitness, epsilon) || VisibilityChecker.epsilonRobustVisibility(polygon, existingWitness, candidate, epsilon)) {
                    independent = false;
                    break;
                }

                // If any vertex can see both witnesses, they cannot both be witnesses.
                if (vertexSeeBothWitnesses(polygon, candidate, existingWitness, epsilon)) {
                    independent = false;
                    break;
                }
            }

            // If candidate passed checks, add as witness.
            if (independent) {
                witnesses.add(candidate);
            }
        }

        // Return final set of witnesses.
        return witnesses;
    }

    // Check if a vertex that can see both witnesses.
    private static boolean vertexSeeBothWitnesses(Polygon polygon, int witnessA, int witnessB, double epsilon) {
        // Number of vertices in the polygon.
        int n = polygon.size();

        // Iterate through all vertices.
        for (int vertex = 0; vertex < n; vertex++) {

            // Check if this vertex can see witnessA and witnessB.
            boolean seesA = VisibilityChecker.epsilonRobustVisibility(polygon, vertex, witnessA, epsilon);
            boolean seesB = VisibilityChecker.epsilonRobustVisibility(polygon, vertex, witnessB, epsilon);

            // If one vertex see both witness, then they cannot both be witnesses.
            if (seesA && seesB) {
                return true;
            }
        }

        // No vertex can see both witness, independent.
        return false;
    }
}