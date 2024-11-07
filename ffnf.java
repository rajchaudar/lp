import java.util.Arrays;
import java.util.Scanner;

public class ffnf {

    // First Fit memory allocation algorithm
    static void firstFit(int blockSize[], int m, int processSize[], int n) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    break;
                }
            }
        }

        System.out.println("\nFirst Fit Allocation:");
        System.out.println("Process No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }

    // Next Fit memory allocation algorithm
    static void nextFit(int blockSize[], int m, int processSize[], int n) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);
        int j = 0;

        for (int i = 0; i < n; i++) {
            int start = j;
            while (true) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    break;
                }
                j = (j + 1) % m;
                if (j == start)
                    break; // Loop back to start if no fit
            }
        }

        System.out.println("\nNext Fit Allocation:");
        System.out.println("Process No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] blockSize = { 100, 500, 200, 300, 600 };
        int[] processSize = { 212, 417, 112, 426 };

        System.out.println("Choose Allocation Method:");
        System.out.println("1. First Fit");
        System.out.println("2. Next Fit");
        int choice = sc.nextInt();

        // Duplicate block sizes for each method run, to keep original values
        int[] blockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);

        switch (choice) {
            case 1:
                firstFit(blockSizeCopy, blockSize.length, processSize, processSize.length);
                break;
            case 2:
                nextFit(blockSizeCopy, blockSize.length, processSize, processSize.length);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        sc.close();
    }
}
