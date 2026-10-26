
public class ReutersQuoteEvent extends TradeEvent {
    private double bidPrice;
    private double askPrice;

    public ReutersQuoteEvent(String senderId, long timestamp,
                             double basePrice, double bidPrice,
                             double askPrice) {
        super(senderId, timestamp, basePrice);
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    @Override
    public void dispatch() {
        double spread = askPrice - bidPrice;

        System.out.println("Reuters Market Quote");
        System.out.println("Sender ID: " + senderId);
        System.out.println("Timestamp: " + timestamp +" ms" +" (" + new java.util.Date(timestamp) + ")");
        System.out.printf("Base Price: $%.2f%n", basePrice);
        System.out.printf("Bid Price: $%.2f%n", bidPrice);
        System.out.printf("Ask Price: $%.2f%n", askPrice);
        System.out.printf("Bid-Ask Spread: $%.2f%n", spread);
    }
}
