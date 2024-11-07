class PageReplacement {

    static int fifoPageFaults(int pages[], int n, int capacity) {
        int[] memory = new int[capacity];
        int pageFaults = 0;
        int memoryCount = 0;

        for (int i = 0; i < n; i++) {
            boolean pageInMemory = false;

            for (int j = 0; j < memoryCount; j++) {
                if (memory[j] == pages[i]) {
                    pageInMemory = true;
                    break;
                }
            }

            if (!pageInMemory) {
                if (memoryCount < capacity) {
                    memory[memoryCount] = pages[i];
                    memoryCount++;
                } else {
                    for (int j = 1; j < capacity; j++) {
                        memory[j - 1] = memory[j];
                    }
                    memory[capacity - 1] = pages[i];
                }
                pageFaults++;
            }
        }

        return pageFaults;
    }

    static int lruPageFaults(int pages[], int n, int capacity) {
        int[] memory = new int[capacity];
        int[] lastUsed = new int[capacity];
        int pageFaults = 0;
        int memoryCount = 0;

        for (int i = 0; i < n; i++) {
            boolean pageInMemory = false;
            int lruIndex = -1;
            int lruTime = Integer.MAX_VALUE;

            for (int j = 0; j < memoryCount; j++) {
                if (memory[j] == pages[i]) {
                    pageInMemory = true;
                    lastUsed[j] = i;
                    break;
                }
            }

            if (!pageInMemory) {
                if (memoryCount < capacity) {
                    memory[memoryCount] = pages[i];
                    lastUsed[memoryCount] = i;
                    memoryCount++;
                } else {
                    for (int j = 0; j < capacity; j++) {
                        if (lastUsed[j] < lruTime) {
                            lruTime = lastUsed[j];
                            lruIndex = j;
                        }
                    }
                    memory[lruIndex] = pages[i];
                    lastUsed[lruIndex] = i;
                }
                pageFaults++;
            }
        }

        return pageFaults;
    }

    public static void main(String args[]) {
        int pages[] = { 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 4, 5, 6, 7 };
        int capacity = 3;

        System.out.println("FIFO Page Faults : " + fifoPageFaults(pages, pages.length, capacity));
        System.out.println("LRU Page Faults : " + lruPageFaults(pages, pages.length, capacity));
    }
}
