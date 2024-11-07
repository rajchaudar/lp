import java.util.*;

class Process {
    int pid;
    int bt;
    int priority;

    public Process(int pid, int bt, int priority) {
        this.pid = pid;
        this.bt = bt;
        this.priority = priority;
    }

    public Process(int pid, int bt) {
        this(pid, bt, 0);
    }
}

public class Scheduling {

    static void findWaitingTimeFCFS(Process proc[], int n, int wt[]) {
        wt[0] = 0;

        for (int i = 1; i < n; i++) {
            wt[i] = proc[i - 1].bt + wt[i - 1];
        }
    }

    static void findWaitingTimePriority(Process proc[], int n, int wt[]) {
        wt[0] = 0;

        for (int i = 1; i < n; i++) {
            wt[i] = proc[i - 1].bt + wt[i - 1];
        }
    }

    static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = proc[i].bt + wt[i]; // Turnaround time = Burst Time + Waiting Time
        }
    }

    static void findAvgTime(Process proc[], int n) {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0;
        float total_tat = 0;

        findWaitingTimePriority(proc, n, wt);
        findTurnAroundTime(proc, n, wt, tat);

        System.out.printf("------------------------------------------------------------\n");
        System.out.printf("| Processes | Burst time | Waiting time | Turn around time |\n");
        System.out.printf("------------------------------------------------------------\n");

        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.printf("|    %d\t    |     %d\t |     %d\t|      %d\t   |\n",
                    proc[i].pid, proc[i].bt, wt[i], tat[i]);
        }
        System.out.printf("------------------------------------------------------------\n");
        System.out.printf("Average waiting time = %.5f\n", (float) total_wt / n);
        System.out.printf("Average turn around time = %f\n", total_tat / n);
        System.out.printf("------------------------------------------------------------\n");
    }

    public static void priorityScheduling(Process proc[], int n) {
        Arrays.sort(proc, new Comparator<Process>() {
            @Override
            public int compare(Process a, Process b) {
                return b.priority - a.priority; // Descending order
            }
        });

        System.out.printf("Order in which priority processes get executed: ");
        for (int i = 0; i < n; i++) {
            System.out.print(proc[i].pid + " ");
        }
        System.out.println("\n");

        findAvgTime(proc, n);
    }

    public static void fcfsScheduling(Process proc[], int n) {
        findAvgTime(proc, n);
    }

    public static void main(String[] args) {
        Process fcfsProc[] = {
                new Process(1, 4),
                new Process(2, 7),
                new Process(3, 3),
                new Process(4, 3),
                new Process(5, 5)
        };

        Process priorityProc[] = {
                new Process(1, 10, 2),
                new Process(2, 5, 0),
                new Process(3, 8, 1),
        };

        fcfsScheduling(fcfsProc, fcfsProc.length);

        priorityScheduling(priorityProc, priorityProc.length);
    }
}
