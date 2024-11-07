public class CPU_Scheduling {

    static void roundRobin(int processes[], int n, int bt[], int quantum) {
        int wt[] = new int[n];
        int tat[] = new int[n];
        int rem_bt[] = new int[n];

        for (int i = 0; i < n; i++)
            rem_bt[i] = bt[i];

        int t = 0;

        while (true) {
            boolean done = true;
            for (int i = 0; i < n; i++) {
                if (rem_bt[i] > 0) {
                    done = false;
                    if (rem_bt[i] > quantum) {
                        t += quantum;
                        rem_bt[i] -= quantum;
                    } else {
                        t += rem_bt[i];
                        wt[i] = t - bt[i];
                        rem_bt[i] = 0;
                    }
                }
            }
            if (done)
                break;
        }

        findTurnAroundTime(processes, n, bt, wt, tat);
        System.out.println("Round Robin Scheduling:");
        System.out.printf("------------------------------------------------------------\n");
        System.out.printf("| Processes | Burst time | Waiting time | Turn around time |\n");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < n; i++) {
            System.out.printf("|    %d\t    |     %d\t |     %d\t|      %d\t   |\n", processes[i], bt[i], wt[i],
                    tat[i]);
        }
        System.out.printf("------------------------------------------------------------\n");

        float total_wt = 0, total_tat = 0;
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
        }

        System.out.println("Average waiting time (RR) = " + (total_wt / n));
        System.out.println("Average turn around time (RR) = " + (total_tat / n));
        System.out.println();
    }

    static void fcfs(int processes[], int n, int bt[]) {
        int wt[] = new int[n];
        int tat[] = new int[n];
        findWaitingTimeFCFS(bt, n, wt);
        findTurnAroundTime(processes, n, bt, wt, tat);

        System.out.println("FCFS Scheduling:");
        System.out.printf("------------------------------------------------------------\n");
        System.out.printf("| Processes | Burst time | Waiting time | Turn around time |\n");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < n; i++) {
            System.out.printf("|    %d\t    |     %d\t |     %d\t|      %d\t   |\n", processes[i], bt[i], wt[i],
                    tat[i]);
        }
        System.out.printf("------------------------------------------------------------\n");
        float total_wt = 0, total_tat = 0;
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
        }

        System.out.println("Average waiting time (FCFS) = " + (total_wt / n));
        System.out.println("Average turn around time (FCFS) = " + (total_tat / n));
        System.out.printf("------------------------------------------------------------\n");
    }

    static void findWaitingTimeFCFS(int bt[], int n, int wt[]) {
        wt[0] = 0;
        for (int i = 1; i < n; i++) {
            wt[i] = bt[i - 1] + wt[i - 1];
        }
    }

    static void findTurnAroundTime(int processes[], int n, int bt[], int wt[], int tat[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = bt[i] + wt[i];
        }
    }

    public static void main(String[] args) {
        int processes[] = { 1, 2, 3 };
        int n = processes.length;
        int burst_time_fcfs[] = { 10, 5, 8 };
        int burst_time_rr[] = { 10, 5, 8 };
        int quantum = 2;

        fcfs(processes, n, burst_time_fcfs);

        roundRobin(processes, n, burst_time_rr, quantum);
    }
}
