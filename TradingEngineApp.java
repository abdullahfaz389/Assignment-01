
public class TradingEngineApp {
    public static void main(String[] args) {

        TradeEvent event1 = new FIXOrderEvent(
            "FIX-Gateway-01",
            System.currentTimeMillis(),
            1000.00,
            "ORD-101"
        );

        TradeEvent event2 = new ReutersQuoteEvent(
            "Reuters-Gateway-01",
            System.currentTimeMillis(),
            150.00,
            149.50,
            150.25
        );

        System.out.println("===== Financial Trade Event Dispatcher =====");

        System.out.println("\n--- Event 1 ---");
        event1.dispatch();

        System.out.println("\n--- Event 2 ---");
        event2.dispatch();

        System.out.println("\n===== Dispatch Completed =====");
    }
}
