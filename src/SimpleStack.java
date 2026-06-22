public class SimpleStack {
    private int[] arr;
    private int top;

    public SimpleStack(int size) {
        arr = new int[size];
        top = -1; // Empty stack
    }

    public void push(int x) {
        if (top == arr.length - 1) {
            throw new RuntimeException("Стек переполнен");
        }
        arr[++top] = x;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Стек пуст");
        }
        return arr[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Стек пуст");
        }
        return arr[top];
    }

    public int size() {
        return top + 1;
    }

    public int capacity() {
        return arr.length;
    }

    public void printStack() {
        if (isEmpty()) {
            System.out.println("Стек пуст");
            return;
        }
        System.out.print("Стек: ");
        for (int i = top; i >= 0; i--) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void main(String[] args) {
        // Создаем стек вместимостью 5 элементов
        SimpleStack stack = new SimpleStack(5);
        System.out.println("Стек пуст? " + stack.isEmpty());

        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.printStack();
        System.out.println("Стек пуст? " + stack.isEmpty());
        System.out.println("Размер стека: " + stack.size());
        System.out.println("Верхний элемент (peek): " + stack.peek());

        while (!stack.isEmpty()) {
            int popped = stack.pop();
            System.out.println("Извлечено: " + popped);
        }

        System.out.println("\nПосле извлечения всех элементов:");
        System.out.println("Стек пуст? " + stack.isEmpty());
        stack.printStack();

        System.out.println("\n=== Проверка обработки ошибок ===");

        SimpleStack smallStack = new SimpleStack(3);
        System.out.println("Добавляем 1, 2, 3 в стек размера 3...");
        smallStack.push(1);
        smallStack.push(2);
        smallStack.push(3);

        try {
            System.out.println("Пытаемся добавить 4...");
            smallStack.push(4);
        } catch (RuntimeException e) {
            System.out.println("Исключение: " + e.getMessage());
        }

        SimpleStack emptyStack = new SimpleStack(3);
        try {
            System.out.println("\nПытаемся извлечь из пустого стека...");
            emptyStack.pop();
        } catch (RuntimeException e) {
            System.out.println("Исключение: " + e.getMessage());
        }
    }
}

