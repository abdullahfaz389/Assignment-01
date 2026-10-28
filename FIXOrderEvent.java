
public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp,
                         double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double fee = basePrice * 0.0015;
        double totalCost = basePrice + fee;

        System.out.println("FIX Stock Purchase Order");
        System.out.println("Sender ID: " + senderId);
        System.out.println("Timestamp: " + timestamp +" ms" +" (" + new java.util.Date(timestamp) + ")");
        System.out.println("Client Order ID: " + clOrdId);
        System.out.printf("Stock Price: $%.2f%n", basePrice);
        System.out.printf("Exchange Fee (0.15%%): $%.2f%n", fee);
        System.out.printf("Total Cost: $%.2f%n", totalCost);
    }
}
