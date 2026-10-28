
public class CacheLocalityBenchmark {
    static final int N = 2000;
    static final int ITERATIONS = 10;
    static double[][] matrix = new double[N][N];
    static volatile double result;
    public static void initializeMatrix() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = i + j * 0.5;
            }
        }
    }
    public static double traverseRowMajor() {
        double sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
    public static double traverseColumnMajor() {
        double sum = 0;
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        initializeMatrix();
        long rowTotalTime = 0;
        long columnTotalTime = 0;
        System.out.println("Cache Locality Benchmark");
        System.out.println("Matrix Size: " + N + " x " + N);
        System.out.println("Iterations: " + ITERATIONS);
        // Row-Major Benchmark
        for (int i = 0; i < ITERATIONS; i++) {
            long start = System.nanoTime();
            result = traverseRowMajor();
            long end = System.nanoTime();
            rowTotalTime += end - start;
        }
        // Column-Major Benchmark
        for (int i = 0; i < ITERATIONS; i++) {
            long start = System.nanoTime();
            result = traverseColumnMajor();
            long end = System.nanoTime();
            columnTotalTime += end - start;
        }
        double rowAverageMs = rowTotalTime / (double) ITERATIONS / 1_000_000.0;
        double columnAverageMs = columnTotalTime / (double) ITERATIONS / 1_000_000.0;
        double speedup = columnTotalTime / (double) rowTotalTime;
        System.out.println("--------------------------------");
        System.out.printf("Row-Major Average Time: %.3f ms%n",rowAverageMs);
        System.out.printf("Column-Major Average Time: %.3f ms%n",columnAverageMs);
        System.out.printf("Speedup Ratio (Column / Row): %.2fx%n",speedup);
        System.out.printf("Matrix Sum: %.2f%n", result);
        System.out.println("Benchmark completed.");
    }
}
