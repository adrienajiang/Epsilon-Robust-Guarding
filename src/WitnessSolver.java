import java.util.*;

public class WitnessSolver {

    public static Set<Integer> greedyWitnesses(Polygon polygon, double epsilon) {
        int n = polygon.size();
        Set<Integer> witnesses = new LinkedHashSet<>();

        for (int candidate = 0; candidate < n; candidate++) {
            boolean independent = true;

            for (int existingWitness : witnesses) {
                if (canBeSeenBySameGuard(polygon, candidate, existingWitness, epsilon)) {
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

    private static boolean canBeSeenBySameGuard(
            Polygon polygon,
            int witnessA,
            int witnessB,
            double epsilon
    ) {
        int n = polygon.size();

        for (int guard = 0; guard < n; guard++) {
            boolean seesA = VisibilityChecker.epsilonRobustCanSee(
                    polygon, guard, witnessA, epsilon
            );

            boolean seesB = VisibilityChecker.epsilonRobustCanSee(
                    polygon, guard, witnessB, epsilon
            );

            if (seesA && seesB) {
                return true;
            }
        }

        return false;
    }
}