
public class TransactionStack {
    private char[] stack;
    private int top;
    private int capacity;

    public TransactionStack(int capacity) {
        this.capacity = capacity;
        stack = new char[capacity];
        top = -1;
    }

    public void push(char c) {
        if (top == capacity - 1) {
            throw new IllegalStateException("Stack Overflow!");
        }

        stack[++top] = c;
    }

    public char pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }

        return stack[top--];
    }

    public char peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }

        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
