
import java.util.Scanner;

public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter packet payload: ");
        String payload = scanner.nextLine();

        int length = payload.length();

        TransactionStack stack = new TransactionStack(length);
        RequestQueue queue = new RequestQueue(length);

        // Push and enqueue each character
        for (int i = 0; i < length; i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        boolean isPalindrome = true;

        // Compare characters in reverse and forward order
        while (!stack.isEmpty() && !queue.isEmpty()) {
            char stackChar = stack.pop();
            char queueChar = queue.dequeue();

            if (stackChar != queueChar) {
                isPalindrome = false;
                break;
            }
        }

        System.out.println("\n===== Packet Symmetry Audit =====");
        System.out.println("Original Payload: " + payload);

        if (isPalindrome) {
            System.out.println("Result: SYMMETRIC");
            System.out.println("Status: Packet is a palindrome.");
        } else {
            System.out.println("Result: NOT SYMMETRIC");
            System.out.println("Status: Packet is not a palindrome.");
        }

        System.out.println("Audit completed.");

        scanner.close();
    }
}
