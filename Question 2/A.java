public class A {
    public static int minMovesToEqualize(int[] machines) {
        int totalDresses = 0;
        int numMachines = machines.length;

        for (int dresses : machines) {
            totalDresses += dresses;
        }

        if (totalDresses % numMachines != 0) {
            return -1; // Cannot equalize dresses
        }

        int targetDresses = totalDresses / numMachines;
        int moves = 0;
        int dressesSum = 0;

        for (int i = 0; i < numMachines - 1; i++) {
            dressesSum += machines[i];
            if (dressesSum != targetDresses * (i + 1)) {
                moves = Math.max(moves, Math.abs((i + 1) * targetDresses - dressesSum));
            }
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] machines = {1, 0, 5};
        int minMoves = minMovesToEqualize(machines);
        System.out.println("Minimum moves to equalize: " + minMoves);
    }
}
