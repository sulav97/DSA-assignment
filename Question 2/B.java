import java.util.ArrayList;
import java.util.List;

public class B {
    public static List<Integer> knowSecret(int n, int[][] intervals, int firstPerson) {
        // Create an array to track whether each person knows the secret
        boolean[] knowsSecret = new boolean[n];
        knowsSecret[firstPerson] = true; // Person 0 initially knows the secret

        // Iterate through the intervals and mark individuals who receive the secret
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            for (int i = start; i <= end; i++) {
                knowsSecret[i] = true;
            }
        }

        // Collect the indices of individuals who know the secret
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (knowsSecret[i]) {
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;
        List<Integer> knownIndividuals = knowSecret(n, intervals, firstPerson);
        System.out.println("Individuals who eventually know the secret: " + knownIndividuals);
    }
}
