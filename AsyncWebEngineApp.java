
public class AsyncWebEngineApp {

    public static void main(String[] args) {

        // Display the default JVM main thread name
        System.out.println(
            "Main thread name: " + Thread.currentThread().getName()
        );

        // Create the telemetry thread
        TelemetryThread telemetry = new TelemetryThread();
        telemetry.setName("Telemetry-Worker");

        // Create the log-processing thread using Runnable
        LogProcessorTask logTask = new LogProcessorTask();
        Thread logWorker = new Thread(logTask);
        logWorker.setName("Log-Worker");

        // Start both worker threads
        telemetry.start();
        logWorker.start();

        // Display their current execution states
        System.out.println(
            "Telemetry-Worker state: " + telemetry.getState()
        );
        System.out.println(
            "Log-Worker state: " + logWorker.getState()
        );

        // Wait until both worker threads finish
        try {
            telemetry.join();
            logWorker.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
            return;
        }

        // Display final execution states
        System.out.println(
            "Telemetry-Worker final state: " + telemetry.getState()
        );
        System.out.println(
            "Log-Worker final state: " + logWorker.getState()
        );

        System.out.println("All background tasks completed.");
    }
}
