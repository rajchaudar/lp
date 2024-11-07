class Process {
    int pid, bt, art;

    public Process(int pid, int bt, int art) {
        this.pid = pid;
        this.bt = bt;
        this.art = art;
    }

    public Process(int pid, int bt) {
        this(pid, bt, 0);
    }
}

public class CombinedScheduling1 {

    static void findWaitingTimeFCFS(Process proc[], int n, int wt[]) {
        wt[0] = 0;

        for (int i = 1; i < n; i++) {
            wt[i] = proc[i - 1].bt + wt[i - 1];
        }
    }

    static void findWaitingTimeSJF(Process proc[], int n, int wt[]) {
        int rt[] = new int[n];
        for (int i = 0; i < n; i++) {
            rt[i] = proc[i].bt;
        }

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        while (complete != n) {
            for (int j = 0; j < n; j++) {
                if ((proc[j].art <= t) && (rt[j] < minm) && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            rt[shortest]--;

            minm = rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            if (rt[shortest] == 0) {
                complete++;
                check = false;

                finish_time = t + 1;
                wt[shortest] = finish_time - proc[shortest].bt - proc[shortest].art;

                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }
            t++;
        }
    }

    static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = proc[i].bt + wt[i];
        }
    }

    static void findAvgTime(Process proc[], int n, String algorithm) {
        int wt[] = new int[n], tat[] = new int[n];
        float total_wt = 0, total_tat = 0;

        if (algorithm.equals("FCFS")) {
            findWaitingTimeFCFS(proc, n, wt);
        } else if (algorithm.equals("SJF")) {
            findWaitingTimeSJF(proc, n, wt);
        }

        findTurnAroundTime(proc, n, wt, tat);

        System.out.printf("\n%s Scheduling:\n", algorithm);
        System.out.printf("------------------------------------------------------------\n");
        System.out.printf("| Processes | Burst time | Waiting time | Turn around time |\n");
        System.out.printf("------------------------------------------------------------\n");

        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.printf("|    %d\t    |     %d\t |     %d\t|      %d\t   |\n", proc[i].pid, proc[i].bt, wt[i],
                    tat[i]);
        }

        System.out.printf("------------------------------------------------------------\n");
        System.out.printf("Average waiting time (%s) = %.2f\n", algorithm, total_wt / n);
        System.out.printf("Average turn around time (%s) = %.2f \n", algorithm, total_tat / n);
        System.out.printf("------------------------------------------------------------\n");
    }

    public static void main(String[] args) {
        Process procFCFS[] = {
                new Process(1, 4),
                new Process(2, 7),
                new Process(3, 3),
                new Process(4, 3),
                new Process(5, 5)
        };

        Process procSJF[] = {
                new Process(1, 6, 2),
                new Process(2, 2, 5),
                new Process(3, 8, 1),
                new Process(4, 3, 0),
                new Process(5, 4, 4)
        };

        findAvgTime(procFCFS, procFCFS.length, "FCFS");

        findAvgTime(procSJF, procSJF.length, "SJF");
    }
}
