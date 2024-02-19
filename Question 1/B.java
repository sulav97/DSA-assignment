import java.util.PriorityQueue;

public class B {

    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        // Initially, there is only one engineer
        pq.offer(0);

        int totalTime = 0;
        for (int engineTime : engines) {
            int currentEngineTime = pq.poll();
            
            // Check if splitting is beneficial
            if (currentEngineTime <= splitCost) {
                pq.offer(currentEngineTime + engineTime);
            } else {
                // Split engineer and add their workload
                pq.offer(splitCost + engineTime);
                pq.offer(currentEngineTime - splitCost + engineTime);
            }
            totalTime = Math.max(totalTime, pq.peek());
        }

        return totalTime;
    }

    public static void main(String[] args) {
        int[] engines = {1, 2, 3};
        int splitCost = 1;
        int minTime = minTimeToBuildEngines(engines, splitCost);
        System.out.println("Minimum time to build all engines: " + minTime);
    }
}
