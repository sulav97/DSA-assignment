public class A{
    public static int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        int n = costs.length;
        int k = costs[0].length;

        int[][] dp = new int[n][k];
        dp[0] = costs[0];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                dp[i][j] = costs[i][j] + min(dp[i - 1], j);
            }
        }

        return min(dp[n - 1]);
    }

    private static int min(int[] arr, int excludeIndex) {
        int minVal = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (i != excludeIndex) {
                minVal = Math.min(minVal, arr[i]);
            }
        }
        return minVal;
    }

    private static int min(int[] arr) {
        int minVal = Integer.MAX_VALUE;
        for (int val : arr) {
            minVal = Math.min(minVal, val);
        }
        return minVal;
    }

    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        System.out.println(minCost(costs)); // Output: 7
    }
}