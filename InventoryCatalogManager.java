
import java.util.*;
import java.util.concurrent.*;
public class InventoryCatalogManager {
    private static final int WORKERS = 10;
    private static final int OPERATIONS = 50000;
    private static final int PRODUCTS = 1000;
    private Map<String, Integer> createCatalog() {
        Map<String, Integer> catalog = new HashMap<>();
        for (int i = 0; i < PRODUCTS; i++) {
            catalog.put("P" + i, 100);
        }
        return catalog;
    }
    public void benchmarkSynchronizedMap() throws Exception {
        Map<String, Integer> catalog = Collections.synchronizedMap(createCatalog());
        runBenchmark(catalog, false);
    }
    public void benchmarkConcurrentMap() throws Exception {
        Map<String, Integer> catalog = new ConcurrentHashMap<>(createCatalog());
        runBenchmark(catalog, true);
    }
    private void runBenchmark(Map<String, Integer> catalog,boolean concurrent) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(WORKERS);

        int operationsPerWorker = OPERATIONS / WORKERS;
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int t = 0; t < WORKERS; t++) {
            tasks.add(() -> {
                for (int i = 0; i < operationsPerWorker; i++) {
                    String productId = "P" + (i % PRODUCTS);
                    if (i % 2 == 0) {
                        // Stock lookup
                        catalog.get(productId);
                    } else {
                        // Stock update
                        if (concurrent) {
                            ((ConcurrentHashMap<String, Integer>) catalog).compute(productId,(key, stock) -> stock + 1);
                        } else {
                            synchronized (catalog) {
                                int stock = catalog.get(productId);
                                catalog.put(productId, stock + 1);
                            }
                        }
                    }
                }
                return null;
            });
        }
        long startTime = System.nanoTime();
        executor.invokeAll(tasks);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;
        double seconds = (endTime - startTime) / 1_000_000_000.0;
        double throughput = OPERATIONS / seconds;
        System.out.println("--------------------------------");
        System.out.println(concurrent ? "ConcurrentHashMap Benchmark" : "SynchronizedMap Benchmark");
        System.out.println("Worker Threads: " + WORKERS);
        System.out.println("Total Operations: " + OPERATIONS);
        System.out.printf("Execution Time: %.3f ms%n",executionTime);
        System.out.printf("Throughput: %.2f operations/second%n",throughput);
    }

    public static void main(String[] args) throws Exception {
        InventoryCatalogManager manager = new InventoryCatalogManager();
        System.out.println("INVENTORY CATALOG BENCHMARK");
        manager.benchmarkSynchronizedMap();
        manager.benchmarkConcurrentMap();
        System.out.println("--------------------------------");
        System.out.println("Benchmark completed.");
    }
}
