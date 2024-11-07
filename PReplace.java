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
                    memory[memoryCount] = pages[i]; // Add the page to memory
                    memoryCount++; // Increase memory count
                } else {
                    for (int j = 1; j < capacity; j++) {
                        memory[j - 1] = memory[j];
                    }
                    memory[capacity - 1] = pages[i]; // Add the new page to memory
                }
                pageFaults++; // Increase the page fault count
            }
        }

        return pageFaults;
    }

    static boolean search(int key, int[] fr) {
        for (int i = 0; i < fr.length; i++) {
            if (fr[i] == key) {
                return true;
            }
        }
        return false;
    }

    static int predict(int pg[], int[] fr, int pn, int index) {
        int res = -1, farthest = index;
        for (int i = 0; i < fr.length; i++) {
            int j;
            for (j = index; j < pn; j++) {
                if (fr[i] == pg[j]) {
                    if (j > farthest) {
                        farthest = j;
                        res = i;
                    }
                    break;
                }
            }

            if (j == pn) {
                return i; // If page is not found in future references
            }
        }

        return (res == -1) ? 0 : res;
    }

    static void optimalPage(int pg[], int pn, int fn) {
        int[] fr = new int[fn]; // Frame to store pages in memory

        int hit = 0;
        int index = 0;
        for (int i = 0; i < pn; i++) {
            if (search(pg[i], fr)) {
                hit++;
                continue;
            }

            if (index < fn) {
                fr[index++] = pg[i]; // Add page to memory
            } else {
                int j = predict(pg, fr, pn, i + 1);
                fr[j] = pg[i]; // Replace it with the current page
            }
        }

        System.out.println("Optimal Page Fault = " + (pn - hit));
    }

    public static void main(String[] args) {
        int pages[] = { 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 4, 5, 6, 7 };
        int pn = pages.length;
        int capacity = 3;

        System.out.println("FIFO Page Faults: " + fifoPageFaults(pages, pn, capacity));

        optimalPage(pages, pn, capacity);
    }
}
