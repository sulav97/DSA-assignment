import java.util.*;

public class B {
    
    public static List<Integer> findImpactedDevices(int[][] edges, int targetDevice) {
        List<Integer> impactedDevices = new ArrayList<>();
        
        // Create adjacency list representation of the graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        // Get the neighbors of the target device
        List<Integer> neighbors = graph.getOrDefault(targetDevice, Collections.emptyList());
        impactedDevices.addAll(neighbors);
        
        return impactedDevices;
    }
    
    public static void main(String[] args) {
        int[][] edges = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
        int targetDevice = 4;

        List<Integer> impactedDevices = findImpactedDevices(edges, targetDevice);
        
        // Filter out the target device itself
        impactedDevices.removeIf(device -> device == targetDevice);
        
        System.out.println("Impacted Device List: " + impactedDevices);
    }
}
