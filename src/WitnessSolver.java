import java.util.*;

public class WitnessSolver {

    public static Set<Integer> greedyWitnesses(Polygon polygon, double epsilon) {
        int n = polygon.size();
        Set<Integer> witnesses = new LinkedHashSet<>();

        for (int candidate = 0; candidate < n; candidate++) {
            boolean independent = true;

            for (int existingWitness : witnesses) {

                // If the two witness vertices can see each other,
                // then one of them can guard both, so they are NOT independent.
                if (VisibilityChecker.epsilonRobustVisibility(polygon, candidate, existingWitness, epsilon) ||
                    VisibilityChecker.epsilonRobustVisibility(polygon, existingWitness, candidate, epsilon)) {
                    independent = false;
                    break;
                }

                // More general rule:
                // If ANY vertex guard can see both witnesses,
                // then they are NOT independent.
                if (canSameGuardSeeBoth(polygon, candidate, existingWitness, epsilon)) {
                    independent = false;
                    break;
                }
            }

            if (independent) {
                witnesses.add(candidate);
            }
        }

        return witnesses;
    }

    private static boolean canSameGuardSeeBoth(
            Polygon polygon,
            int witnessA,
            int witnessB,
            double epsilon
    ) {
        int n = polygon.size();

        for (int guard = 0; guard < n; guard++) {
            boolean seesA = VisibilityChecker.epsilonRobustVisibility(polygon, guard, witnessA, epsilon);
            boolean seesB = VisibilityChecker.epsilonRobustVisibility(polygon, guard, witnessB, epsilon);

            if (seesA && seesB) {
                return true;
            }
        }

        return false;
    }
}