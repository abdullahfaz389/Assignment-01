
import java.util.concurrent.*;
public class SensorGridMonitorApp {
    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        int size = 50000;
        double[] sensorData = new double[size];
        // Generate sensor readings
        for (int i = 0; i < size; i++) {
            sensorData[i] = 10 + (i * 37 % 9000) / 100.0;
        }
        int workers = 4;
        int chunkSize = size / workers;

        ExecutorService executor = Executors.newFixedThreadPool(workers);
        Future<Double>[] minResults = new Future[workers];
        Future<Double>[] maxResults = new Future[workers];
        long startTime = System.nanoTime();
        // Divide the sensor array into four segments
        for (int i = 0; i < workers; i++) {
            int start = i * chunkSize;
            int end = (i == workers - 1) ? size : start + chunkSize;
            SensorSearchEngine minTask = new ParallelMinSearch(sensorData, start, end);

            SensorSearchEngine maxTask = new ParallelMaxSearch(sensorData, start, end);
            minResults[i] = executor.submit(minTask::processGrid);
            maxResults[i] = executor.submit(maxTask::processGrid);
        }
        double globalMin = Double.MAX_VALUE;
        double globalMax = -Double.MAX_VALUE;
        // Combine results from all segments
        for (int i = 0; i < workers; i++) {
            globalMin = Math.min(globalMin, minResults[i].get());
            globalMax = Math.max(globalMax, maxResults[i].get());
        }
        long endTime = System.nanoTime();
        executor.shutdown();
        double executionTime = (endTime - startTime) / 1_000_000.0;
        System.out.println("===== Sensor Grid Monitor =====");
        System.out.println("Total Sensor Readings: " + size);
        System.out.println("Worker Threads: " + workers);
        System.out.printf("Global Minimum Temperature: %.2f%n", globalMin);
        System.out.printf("Global Maximum Vibration: %.2f%n", globalMax);
        System.out.printf("Execution Time: %.3f ms%n", executionTime);
        System.out.println("Parallel sensor analysis completed.");
    }
}
