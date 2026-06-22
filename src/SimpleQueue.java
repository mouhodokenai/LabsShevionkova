class SimpleQueue {
    private final int[] arr;
    private int front; // индекс начала очереди
    private int rear; // индекс конца очереди
    private int size; // текущее количество элементов
    private final int capacity;

    public SimpleQueue(int capacity) {
        this.capacity = capacity;
        arr = new int[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }
    public void enqueue(int x) {
        if (size == capacity) {
            System.out.println("Очередь переполнена");
            return;
        }
        rear = (rear + 1) % capacity; // кольцевой сдвиг
        arr[rear] = x;
        size++;
    }
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Очередь пуста");
            return -1;
        }
        int value = arr[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }
    public int peek() {
        if (isEmpty()) return -1;
        return arr[front];
    }
    public boolean isEmpty() {
        return size == 0;
    }

    static void main(String[] args) {
        SimpleQueue queue = new SimpleQueue(5);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Извлечено:");
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}