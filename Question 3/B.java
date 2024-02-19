import java.util.*;

public class B {
    // Edge class to represent an edge in the graph
    static class Edge {
        int source, destination, weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    // UnionFind class to implement disjoint set data structure for Kruskal's algorithm
    static class UnionFind {
        int[] parent;

        UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        int find(int vertex) {
            if (vertex == parent[vertex]) {
                return vertex;
            }
            return parent[vertex] = find(parent[vertex]);
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            parent[rootX] = rootY;
        }
    }

    // Kruskal's algorithm to find Minimum Spanning Tree (MST)
    static List<Edge> kruskalMST(List<Edge> edges, int vertices) {
        List<Edge> result = new ArrayList<>();
        // Sort edges by weight
        edges.sort(Comparator.comparingInt(e -> e.weight));
        UnionFind uf = new UnionFind(vertices);

        for (Edge edge : edges) {
            int sourceRoot = uf.find(edge.source);
            int destRoot = uf.find(edge.destination);
            if (sourceRoot != destRoot) {
                result.add(edge);
                uf.union(sourceRoot, destRoot);
            }
        }
        return result;
    }

    // Priority queue based on minimum heap
    static class MinHeap {
        private List<Edge> heap;

        MinHeap() {
            heap = new ArrayList<>();
        }

        int parent(int i) {
            return (i - 1) / 2;
        }

        int leftChild(int i) {
            return 2 * i + 1;
        }

        int rightChild(int i) {
            return 2 * i + 2;
        }

        void insert(Edge edge) {
            heap.add(edge);
            int current = heap.size() - 1;
            while (current > 0 && heap.get(current).weight < heap.get(parent(current)).weight) {
                Collections.swap(heap, current, parent(current));
                current = parent(current);
            }
        }

        Edge extractMin() {
            Edge minEdge = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            minHeapify(0);
            return minEdge;
        }

        void minHeapify(int i) {
            int left = leftChild(i);
            int right = rightChild(i);
            int smallest = i;

            if (left < heap.size() && heap.get(left).weight < heap.get(smallest).weight) {
                smallest = left;
            }

            if (right < heap.size() && heap.get(right).weight < heap.get(smallest).weight) {
                smallest = right;
            }

            if (smallest != i) {
                Collections.swap(heap, i, smallest);
                minHeapify(smallest);
            }
        }

        boolean isEmpty() {
            return heap.isEmpty();
        }
    }

    public static void main(String[] args) {
        // Example graph represented by edge list
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        int vertices = 4; // Number of vertices in the graph

        // Find MST using Kruskal's algorithm
        List<Edge> mst = kruskalMST(edges, vertices);
        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println(edge.source + " - " + edge.destination + ": " + edge.weight);
        }

        // Priority queue based on minimum heap
        MinHeap minHeap = new MinHeap();
        minHeap.insert(new Edge(0, 1, 10));
        minHeap.insert(new Edge(0, 2, 6));
        minHeap.insert(new Edge(0, 3, 5));
        minHeap.insert(new Edge(1, 3, 15));
        minHeap.insert(new Edge(2, 3, 4));

        System.out.println("Minimum weight edge extracted from min heap: " + minHeap.extractMin().weight);
    }
}
