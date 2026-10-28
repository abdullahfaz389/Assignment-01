
import java.util.concurrent.ForkJoinPool;
public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int size = 10000;
        double[] assets = new double[size];
        // Generate synthetic financial asset values
        for (int i = 0; i < size; i++) {
            assets[i] = 500 + (i % 1500);
        }
        double riskCap = 1500.0;
        ForkJoinPool pool = new ForkJoinPool();
        // Calculate initial risk using Fork/Join
        long parallelStart = System.nanoTime();
        double initialRisk = pool.invoke(new ParallelRiskCalculator(assets, 0, assets.length) );
        long parallelEnd = System.nanoTime();
        // Apply the 5% stress-test penalty
        pool.invoke( new PortfolioThresholdTask(assets, 0, assets.length, riskCap));
        // Calculate risk after the adjustment
        double adjustedRisk = pool.invoke(new ParallelRiskCalculator(assets, 0, assets.length));
        // Calculate risk sequentially for comparison
        long sequentialStart = System.nanoTime();
        double sequentialRisk = 0;
        for (double value : assets) {
            sequentialRisk += value;
        }
        long sequentialEnd = System.nanoTime();
        double parallelTimeMs =(parallelEnd - parallelStart) / 1_000_000.0;
        double sequentialTimeMs =(sequentialEnd - sequentialStart) / 1_000_000.0;
        System.out.println("===== Fork/Join Portfolio Assessment =====");
        System.out.println("Total Assets: " + size);
        System.out.printf("Initial Total Risk: %.2f%n", initialRisk);
        System.out.printf("Risk Cap: %.2f%n", riskCap);
        System.out.printf("Adjusted Total Risk: %.2f%n", adjustedRisk);
        System.out.printf("Sequential Verification Risk: %.2f%n",sequentialRisk);
        System.out.printf("Parallel Calculation Time: %.3f ms%n",parallelTimeMs);
        System.out.printf("Sequential Calculation Time: %.3f ms%n",sequentialTimeMs);
        pool.shutdown();
    }
}
