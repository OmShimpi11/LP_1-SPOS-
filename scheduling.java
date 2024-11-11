import java.util.Scanner;
public class FCFS 
{
    static class Process 
    {
        int id = 0;
        int arrivalTime = 0;
        int burstTime = 0;
        int completionTime = 0;
        int turnaroundTime = 0;
        int waitingTime = 0;
        int priority = 0;

        Process(int id, int arrivalTime, int burstTime,int priority) 
        {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        Process(int id,int burstTime,int priority) 
        {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
        }
    }

    private static void calculateFCFS(Process[] processes) 
    {
        int currentTime = 0;

        for (Process p : processes) 
        {
            // Ensure the current time is at least the arrival time of the process
            if (currentTime < p.arrivalTime) 
                currentTime = p.arrivalTime;
            

            p.completionTime = currentTime + p.burstTime;

            p.turnaroundTime = p.completionTime - p.arrivalTime;

            p.waitingTime = p.turnaroundTime - p.burstTime;

            currentTime = p.completionTime;
        }
    }

    private static void calculatePriority(Process[] processes) 
    {
        int n = processes.length;
        int currentTime = 0;
        int completed = 0;

        boolean[] isCompleted = new boolean[n];

        while (completed < n) 
        {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;
            int earliestArrival = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) 
            {
                if (processes[i].arrivalTime <= currentTime && !isCompleted[i]) 
                {
                    if (processes[i].priority < highestPriority) 
                    {
                        highestPriority = processes[i].priority;
                        earliestArrival = processes[i].arrivalTime;
                        idx = i;
                    }
                    else if (processes[i].priority == highestPriority) 
                    {
                        if (processes[i].arrivalTime < earliestArrival) 
                        {
                            earliestArrival = processes[i].arrivalTime;
                            idx = i;
                        }
                        else if (processes[i].arrivalTime == earliestArrival) 
                            if (processes[i].id < processes[idx].id) 
                                idx = i;
                    }
                }
            }
            if (idx != -1) 
            {
                Process p = processes[idx];

                if (currentTime < p.arrivalTime) 
                {
                    currentTime = p.arrivalTime;
                }

                p.completionTime = currentTime + p.burstTime;

                p.turnaroundTime = p.completionTime - p.arrivalTime;

                p.waitingTime = p.turnaroundTime - p.burstTime;

                currentTime = p.completionTime;

                isCompleted[idx] = true;
                completed++;
            } 
            else 
                currentTime++;
            
        }
    }
    private static void calculateSJF(Process[] processes) 
    {
        int n = processes.length, completed = 0;
        int currentTime = 0; // Start at time 0
        boolean[] comp = new boolean[n];

        while (completed < n) 
        {
            int min = 9999;
            int md = -1; 

            for (int i = 0; i < n; i++) 
            {
                if (!comp[i] && processes[i].arrivalTime <= currentTime && processes[i].burstTime < min) 
                {
                    min = processes[i].burstTime;
                    md = i;
                }
            }

            if (md == -1) {
                currentTime++;
                continue;
            }

            processes[md].completionTime = currentTime + processes[md].burstTime;
            processes[md].turnaroundTime = processes[md].completionTime - processes[md].arrivalTime;
            processes[md].waitingTime = processes[md].turnaroundTime - processes[md].burstTime;

            currentTime = processes[md].completionTime;

            comp[md] = true;
            completed++;
        }
    }
    private static void calculateRR(Process[] processes) 
    {
        int n = processes.length;
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Time Quantum: ");
        int tq = sc.nextInt();
        
        int[] rbt = new int[n];
        for (int i = 0; i < n; i++) 
        {
            rbt[i] = processes[i].burstTime;  // Copy burst time
        }
        
        int currentTime = 0;
        int completed = 0; 
        boolean[] comp = new boolean[n];

        while (completed < n) 
        {
            boolean didProcessRun = false; 

            for (int i = 0; i < n; i++) 
            {
                if (!comp[i]) 
                {
                    didProcessRun = true;                    
                    if (rbt[i] > tq) 
                    {
                        currentTime += tq;
                        rbt[i] -= tq; 
                    }
                    else 
                    {
                        currentTime += rbt[i];
                        rbt[i] = 0;  

                        comp[i] = true;
                        completed++;
                        
                        processes[i].completionTime = currentTime;
                        processes[i].turnaroundTime = currentTime - processes[i].arrivalTime;
                        processes[i].waitingTime = processes[i].turnaroundTime - processes[i].burstTime;
                    }
                }
            }
            
            if (!didProcessRun) 
            {
                currentTime++;
            }
        }
    }



    private static void display(Process[] processes)
    {
        System.out.println("\nProcess ID | Arrival Time | Priority | Burst Time | Completion Time | Turnaround Time | Waiting Time");        
        for (Process p : processes) 
            System.out.printf("%9d | %12d | %9d | %9d | %15d | %14d | %11d\n",
                p.id, p.arrivalTime,p.priority, p.burstTime, p.completionTime, p.turnaroundTime, p.waitingTime);
        
    }
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];
        int ch = 0; // for priority scheduling
        
        do
        {
            System.out.println("\n-------MENU------");
            System.out.println("1.FCFS");
            System.out.println("2.SJF");
            System.out.println("3.Priority");
            System.out.println("4.RR");
            System.out.println("5.Exit");
            System.out.print("Enter your choice : ");
            ch = scanner.nextInt();
            switch(ch)
            {
                case 1:
                    for(int i = 0; i < n; i++) 
                    {
                        System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                        int arrivalTime = scanner.nextInt();
                        System.out.print("Enter burst time for process " + (i + 1) + ": ");
                        int burstTime = scanner.nextInt();
                        processes[i] = new Process(i + 1, arrivalTime, burstTime,0);
                    }
                    for(int i = 0; i < n - 1; i++)
                    {
                        for(int j = i + 1; j < n; j++)
                        {
                            if(processes[i].arrivalTime > processes[j].arrivalTime)
                            {
                                Process temp = processes[i];
                                processes[i] = processes[j];
                                processes[j] = temp;
                            }
                        }

                    }
                    calculateFCFS(processes);
                    display(processes);
                    break;

                case 2:
                    for(int i = 0; i < n; i++) 
                    {
                        System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                        int arrivalTime = scanner.nextInt();
                        System.out.print("Enter burst time for process " + (i + 1) + ": ");
                        int burstTime = scanner.nextInt();
                        processes[i] = new Process(i + 1, arrivalTime, burstTime,0);
                    }
                    for(int i = 0; i < n - 1; i++)
                    {
                        for(int j = i + 1; j < n; j++)
                        {
                            if(processes[i].arrivalTime > processes[j].arrivalTime)
                            {
                                Process temp = processes[i];
                                processes[i] = processes[j];
                                processes[j] = temp;
                            }
                        }

                    }
                    calculateSJF(processes);
                    display(processes);
                    break;
                case 3:
                    for(int i = 0; i < n; i++) 
                    {
                        System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                        int arrivalTime = scanner.nextInt();
                        System.out.print("Enter burst time for process " + (i + 1) + ": ");
                        int burstTime = scanner.nextInt();
                        System.out.print("Enter priority for process " + (i + 1) + ": ");
                        int priority = scanner.nextInt();
                        processes[i] = new Process(i + 1, arrivalTime, burstTime,priority);
                    }   
                    for(int i = 0; i < n - 1; i++)
                    {
                        for(int j = i + 1; j < n; j++)
                        {
                            if(processes[i].arrivalTime > processes[j].arrivalTime)
                            {
                                Process temp = processes[i];
                                processes[i] = processes[j];
                                processes[j] = temp;
                            }
                        }

                    }
                    calculatePriority(processes);
                    display(processes);
                    break;

                case 4:
                    for(int i = 0; i < n; i++) 
                    {
                        System.out.print("Enter burst time for process " + (i + 1) + ": ");
                        int burstTime = scanner.nextInt();
                        processes[i] = new Process(i + 1, burstTime,0);
                    }
                    for(int i = 0; i < n - 1; i++)
                    {
                        for(int j = i + 1; j < n; j++)
                        {
                            if(processes[i].arrivalTime > processes[j].arrivalTime)
                            {
                                Process temp = processes[i];
                                processes[i] = processes[j];
                                processes[j] = temp;
                            }
                        }

                    }
                    calculateRR(processes);
                    display(processes);
                    break;
            }

        }while(ch != 5);

        scanner.close();
    }
}

