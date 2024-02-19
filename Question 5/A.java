import java.util.*;

public class A {
    static class Graph {
        int[][] distances;

        Graph(int[][] distances) {
            this.distances = distances;
        }

        int getDistance(int city1, int city2) {
            return distances[city1][city2];
        }
    }

    static class Ant {
        int numCities;
        boolean[] visited;
        int[] tour;
        int tourLength;
        int startCity;

        Ant(int numCities, int startCity) {
            this.numCities = numCities;
            this.startCity = startCity;
            visited = new boolean[numCities];
            tour = new int[numCities];
            tourLength = 0;
            tour[0] = startCity;
            visited[startCity] = true;
        }

        void tourConstruction(Graph graph, double[][] pheromones, double alpha, double beta) {
            for (int i = 1; i < numCities; i++) {
                int currentCity = tour[i - 1];
                int nextCity = selectNextCity(currentCity, graph, pheromones, alpha, beta);
                tour[i] = nextCity;
                visited[nextCity] = true;
                tourLength += graph.getDistance(currentCity, nextCity);
            }
            tourLength += graph.getDistance(tour[numCities - 1], startCity);
        }

        int selectNextCity(int currentCity, Graph graph, double[][] pheromones, double alpha, double beta) {
            double[] probabilities = new double[numCities];
            double totalProbability = 0.0;
            for (int i = 0; i < numCities; i++) {
                if (!visited[i]) {
                    probabilities[i] = Math.pow(pheromones[currentCity][i], alpha) *
                            Math.pow(1.0 / graph.getDistance(currentCity, i), beta);
                    totalProbability += probabilities[i];
                }
            }
            double random = Math.random() * totalProbability;
            double cumulativeProbability = 0.0;
            for (int i = 0; i < numCities; i++) {
                if (!visited[i]) {
                    cumulativeProbability += probabilities[i];
                    if (cumulativeProbability >= random) {
                        return i;
                    }
                }
            }
            return -1; // Should not reach here
        }
    }

    static class ACO {
        Graph graph;
        int numCities;
        int numAnts;
        int numIterations;
        double alpha;
        double beta;
        double evaporationRate;
        double[][] pheromones;
        int[] bestTour;
        int bestTourLength;

        ACO(Graph graph, int numAnts, int numIterations, double alpha, double beta, double evaporationRate) {
            this.graph = graph;
            this.numCities = graph.distances.length;
            this.numAnts = numAnts;
            this.numIterations = numIterations;
            this.alpha = alpha;
            this.beta = beta;
            this.evaporationRate = evaporationRate;
            this.pheromones = new double[numCities][numCities];
            initializePheromones();
            this.bestTour = new int[numCities];
            this.bestTourLength = Integer.MAX_VALUE;
        }

        void initializePheromones() {
            double initialPheromone = 1.0 / numCities;
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    pheromones[i][j] = initialPheromone;
                }
            }
        }

        void solveTSP() {
            for (int iteration = 0; iteration < numIterations; iteration++) {
                Ant[] ants = new Ant[numAnts];
                for (int i = 0; i < numAnts; i++) {
                    ants[i] = new Ant(numCities, (int) (Math.random() * numCities));
                    ants[i].tourConstruction(graph, pheromones, alpha, beta);
                    if (ants[i].tourLength < bestTourLength) {
                        bestTourLength = ants[i].tourLength;
                        System.arraycopy(ants[i].tour, 0, bestTour, 0, numCities);
                    }
                }
                updatePheromones(ants);
                evaporatePheromones();
            }
        }

        void updatePheromones(Ant[] ants) {
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    if (i != j) {
                        pheromones[i][j] *= (1.0 - evaporationRate);
                    }
                }
            }
            for (Ant ant : ants) {
                for (int i = 0; i < numCities - 1; i++) {
                    int city1 = ant.tour[i];
                    int city2 = ant.tour[i + 1];
                    pheromones[city1][city2] += 1.0 / ant.tourLength;
                    pheromones[city2][city1] += 1.0 / ant.tourLength;
                }
            }
        }

        void evaporatePheromones() {
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    pheromones[i][j] *= (1.0 - evaporationRate);
                }
            }
        }

        void printBestTour() {
            System.out.println("Best Tour: " + Arrays.toString(bestTour));
            System.out.println("Best Tour Length: " + bestTourLength);
        }
    }

    public static void main(String[] args) {
        int[][] distances = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };
        Graph graph = new Graph(distances);
        int numAnts = 5;
        int numIterations = 100;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;

        ACO aco = new ACO(graph, numAnts, numIterations, alpha, beta, evaporationRate);
        aco.solveTSP();
        aco.printBestTour();
    }
}
