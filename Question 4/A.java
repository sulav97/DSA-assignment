import java.util.*;

public class A {
    
    static class Point {
        int x, y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    
    public static int minStepsToCollectAllKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int totalKeys = 0;
        Queue<Point> queue = new LinkedList<>();
        Set<Character> keys = new HashSet<>();
        Set<Character> doors = new HashSet<>();
        
        // Find starting position and count total number of keys
        Point start = null;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    start = new Point(i, j);
                } else if (Character.isLowerCase(grid[i][j])) {
                    totalKeys++;
                } else if (Character.isUpperCase(grid[i][j])) {
                    doors.add(grid[i][j]);
                }
            }
        }
        
        if (totalKeys == 0) return 0; // No keys to collect
        
        int steps = 0;
        queue.offer(start);
        Set<String> visited = new HashSet<>();
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Point curr = queue.poll();
                for (int k = 0; k < 4; k++) {
                    int nx = curr.x + dx[k];
                    int ny = curr.y + dy[k];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 'W') {
                        char cell = grid[nx][ny];
                        if (Character.isLowerCase(cell)) {
                            keys.add(cell);
                            grid[nx][ny] = 'P'; // Mark the cell as visited
                        } else if (Character.isUpperCase(cell) && keys.contains(Character.toLowerCase(cell))) {
                            doors.remove(cell);
                            grid[nx][ny] = 'P'; // Mark the cell as visited
                        } else if (cell == 'E' && keys.size() == totalKeys) {
                            return steps + 1;
                        }
                        String key = nx + "," + ny + "," + keys.toString();
                        if (!visited.contains(key)) {
                            visited.add(key);
                            queue.offer(new Point(nx, ny));
                        }
                    }
                }
            }
            steps++;
        }
        
        return -1; // Cannot collect all keys
    }

    public static void main(String[] args) {
        char[][] grid = {
            {'S', 'P', 'q', 'P', 'P'},
            {'W', 'W', 'W', 'P', 'W'},
            {'r', 'P', 'Q', 'P', 'R'}
        };
        int minSteps = minStepsToCollectAllKeys(grid);
        System.out.println("Minimum steps to collect all keys: " + minSteps);
    }
}
