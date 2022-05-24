// min heap
public class Heap {
    int[] heap;
    int cap = 1000;
    int size = 0;

    public Heap() {
        heap = new int[cap];
    }

    private void swap(int idx1, int idx2) {
        int tmp = heap[idx1];
        heap[idx1] = heap[idx2];
        heap[idx2] = tmp;
    }

    private void heapifyUp(int idx) {
        while (idx > 0) {
            int parentIdx = (idx - 1) / 2;
            if (heap[parentIdx] <= heap[idx]) {
                break;
            }
            swap(parentIdx, idx);
            idx = parentIdx;
        }
    }

    private void heapifyDown(int idx) {
        while (idx < size) {
            int leftIdx = idx * 2 + 1;
            int rightIdx = idx * 2 + 2;

            int minIdx = idx;
            if (leftIdx < size && heap[leftIdx] < heap[minIdx]) {
                minIdx = leftIdx;
            }
            if (rightIdx < size && heap[rightIdx] < heap[minIdx]) {
                minIdx = rightIdx;
            }

            if (minIdx == idx) {
                break;
            }

            swap(idx, minIdx);
            idx = minIdx;
        }
    }

    private void insert(int val) {
        if (size == cap) {
            return;
        }

        heap[size++] = val;
        heapifyUp(size - 1);
    }

    private int pop() {
        if (size == 0) {
            return -1;
        }

        int rst = heap[0];
        swap(0, --size);
        heapifyDown(0);

        return rst;
    }

    private int peek() {
        if (size == 0) {
            return -1;
        }
        return heap[0];
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        Heap heap = new Heap();
        heap.insert(10);
        heap.insert(9);
        heap.insert(8);
        heap.insert(7);
        heap.insert(11);
        heap.insert(3);
        heap.insert(6);

        while (!heap.isEmpty()) {
            System.out.println(heap.pop());
        }
    }
}
