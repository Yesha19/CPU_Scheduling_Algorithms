package CPU_Scheduler;

// ------------------- OS PROJECT - CPU SCHEDULING ALGORITHMS --------------------
// Team Members : Muskan Matwani  AU1741027
//				  Yesha Shastri   AU1741035
// -----------------------------------------------------------------------------------

import java.util.List;
import java.util.Random;

// ------------------------------- MAIN CLASS --------------------------------------------------------------------
public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
    	System.out.println("--------------- OS PROJECT -----------------");
    	System.out.println("             CPU SCHEDULING ALGORITHMS           ");
    	System.out.println("---------------------------------------------");
    	
    	// Making instances of all the schedulers 
    	
    	// Round Robin
    	Scheduler rr = new RR1();
        rr.setTimeQuantum(10);
        
        // first come first serve
        Scheduler fcfs = new FCFS();
        
        // dynamic round robin
        Scheduler drr = new DRR();
       
        Threads th = new Threads(rr, fcfs, drr);
        th.callThreads();
        
        Thread.sleep(20000);
        
        System.out.println("\n                 Round Robin - 5");
        showDetails(rr);
    
        System.out.println("\n                First Come First Serve");
        showDetails(fcfs);
        
        System.out.println("\n\n             Dynamic Round Robin ");
        showDetails(drr);
        
        // Calling GUI to display the corresponding contents
        new CPU_Scheduler.GUI(rr, fcfs, drr);
    }
    
    // showDetails function for all the schedulers
    public static void showDetails(Scheduler scheduler1)
    {
        System.out.println("Process\tAT\tBT\tWT\tTAT\tRT");
        
        // Showing the arrival time, burst time, waiting time, turn around time and response time of all the processes of all the algorithms implmeneted
        for (Process row : scheduler1.row_btat)
        {
            System.out.println(row.getProcessId() + "\t" + row.getArrivalTime() + "\t" + row.getBurstTime() + "\t" + scheduler1.waitmap.get(row.getProcessId()) + "\t" + scheduler1.tatmap.get(row.getProcessId()) + "\t" + scheduler1.respmap.get(row.getProcessId()));
        }
        
        // Showing the average WT and TAT and RT corresponding to each algorithm implmeneted
        System.out.println("\n\nAverage WT: " + scheduler1.getAverageWaitingTime() + "\nAverage TAT: " + scheduler1.getAverageTurnAroundTime() + "\nContext Swicthes: " + (scheduler1.getContextSwicth()-1));
    }
}
