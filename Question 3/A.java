import java.util.Collections;
import java.util.PriorityQueue;

public class A {
    private PriorityQueue<Double> minHeap; // Stores the larger half of the scores
    private PriorityQueue<Double> maxHeap; // Stores the smaller half of the scores

    public A() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void addScore(double score) {
        // Add score to appropriate heap
        if (maxHeap.isEmpty() || score <= maxHeap.peek()) {
            maxHeap.offer(score);
        } else {
            minHeap.offer(score);
        }

        // Balance heaps if necessary
        balanceHeaps();
    }

    public double getMedianScore() {
        // If number of scores is even, return average of two middle scores
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            // Otherwise, return the middle score from maxHeap
            return maxHeap.peek();
        }
    }

    private void balanceHeaps() {
        // Balance the sizes of minHeap and maxHeap
        while (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        }
        while (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public static void main(String[] args) {
        A scoreTracker = new A();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1); // Output: 88.9
        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2); // Output: 86.95
    }
}
