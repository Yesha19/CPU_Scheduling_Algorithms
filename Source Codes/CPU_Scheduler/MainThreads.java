package CPU_Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// MAIN THREADS -INCLUDING makeProcess, long term scheduler and short term scheduler functions

public class MainThreads 
{
	List<Process> newqueue = new ArrayList<>();
	List<Process> readySuspendQueue = new ArrayList<>();
	
	Scheduler ss, fcfs, drr;
	static int noOfProc=0, y, firsttime=0;
	int flag1=0;  // sees whether the total number of processes arrived or not
	
	// Constructor
	public MainThreads() {
	}
	
	// Parameterized constructor 
	public MainThreads(Scheduler ss, Scheduler fcfs, Scheduler drr) {
		this.ss = ss;
		this.fcfs = fcfs;
		this.drr = drr;
	}
	
	// This method is called by Thread 1. It randomly assigns process Burst time san arrival times and add it in NEW QUEUE
	public void makeProcess() throws InterruptedException 
	{	
		Random random = new Random();
		int i=0, id, prev_bt=-1;
		
		while(true)
		{
			// Synchronized so that no two threads can axis New queue at the same time
			synchronized(this)
			{	
				// Wait after all number of processes are created (here assigned 8)
				while(noOfProc==8) {
					flag1 = 1;
					wait();
				}
				
				// Assigning process ID
				i++;
				id = i;
				String str = "P";
				str = str + id;
				
				System.out.println("\nProcess " + str + ": ADDED IN NEW QUEUE " + id);
				
				// Assigning Arrival time randomly
				int a1 = random.nextInt(((5+y) - y) + 1) + y;
				y = a1;
				
				// Assigning Burst Time randomly
				//int b1 = random.nextInt(10);
				
				int b1;
				
				// Generating both short burst and long burst processes in order to compare various scheduling algorithms
				if(prev_bt == -1) {
					b1 = random.nextInt(100 - 60) + 60;
					prev_bt=b1;
				} 
				else {
					if(prev_bt > 10) 
					{
						b1 = random.nextInt(5);
						prev_bt = b1;
					}
					else 
					{
						b1 = random.nextInt(100 - 60) + 0;
						prev_bt = b1;
					}
				}
				
				
				// Adding the process in the NEW QUEUE
				newqueue.add(new Process(str, a1, b1));
				
				noOfProc++;
				
				// notify when there is at least one process in new queue
				notify();
				
				// Random sleep between creation of processes
				Thread.sleep((int)(Math.random() * 100));
			}
		}
	}
	
	// Long Term Scheduler
	public void longTermScheduler() throws InterruptedException 
	{
		while(true) 
		{
			// Synchronized so that no two threads can axis New queue at the same time
			synchronized(this)
			{
				// wait till the list_items is empty. the while loop breaks when there is at least an item in the buffer to consume
				while(newqueue.size()==0 && (flag1==0)) 
				{
						wait();
				}
				
		
				Process row1 = new Process();
				
				// If ready queue is full and new queue is not zero, add the new process in the ready suspend queue 
				if(ss.getRows().size() == 5 && newqueue.size()!=0) 
				{
					row1 = newqueue.remove(0);
					readySuspendQueue.add(row1);
					System.out.println("\n" +  row1.getProcessId() + " : ADDED IN BLOCKED QUEUE");
					
					// adding into row_btat of each algo to display contents
					Process newRow = new Process(row1.getProcessId(), row1.getArrivalTime(), row1.getBurstTime());
					ss.row_btat.add(newRow);
					
					Process newRow3 = new Process(row1.getProcessId(), row1.getArrivalTime(), row1.getBurstTime());
					fcfs.row_btat.add(newRow3);
					
					Process newRow4 = new Process(row1.getProcessId(), row1.getArrivalTime(), row1.getBurstTime());
					drr.row_btat.add(newRow4);
					
				} 
				else if(ss.getRows().size()<5 && readySuspendQueue.size() == 0 && newqueue.size()!=0)
				{
					// If the ready queue is not full and ready suspend queue is empty, enter the new process from NEW queue to READY queue
					row1 = newqueue.remove(0);
					ss.add(row1);
					
					Process row2 = new Process(row1.getProcessId(), row1.getArrivalTime(), row1.getBurstTime());
					fcfs.add(row2);
					
					Process row3 = new Process(row1.getProcessId(), row1.getArrivalTime(), row1.getBurstTime());
					drr.add(row3);
					
					System.out.println("\n" + row1.getProcessId() + " : ADDED IN READY QUEUE");
					
					
					// Adding processes in row_btat for displaying contents of all processes
					Process temp_row = new Process(row1.getProcessId(), row1.getArrivalTime(), row1.getBurstTime());
					ss.row_btat.add(temp_row);
					
					Process temp_row1 = new Process(row1.getProcessId(), row1.getArrivalTime(), row1.getBurstTime());
					fcfs.row_btat.add(temp_row1);
					
					Process temp_row2 = new Process(row1.getProcessId(), row1.getArrivalTime(), row1.getBurstTime());
					drr.row_btat.add(temp_row2);
					
					
				} else if(ss.getRows().size()<5 && readySuspendQueue.size()>0)
				{
					// If the ready queue is not full and ready suspend queue has processes, add the process from ready suspend to ready queue
					Process blockedrow = readySuspendQueue.remove(0);
					ss.add(blockedrow);
									
					Process blocked_row1 = new Process(blockedrow.getProcessId(), blockedrow.getArrivalTime(), blockedrow.getBurstTime());
					fcfs.add(blocked_row1);
					
					Process blocked_row2 = new Process(blockedrow.getProcessId(), blockedrow.getArrivalTime(), blockedrow.getBurstTime());
					drr.add(blocked_row2);
					
					System.out.println("\n" + blockedrow.getProcessId() + " : ADDED IN READY from BLOCKED QUEUE");
					notify();
				}
				
				// Notify when there is at least one process in the READY queue
				notify();
				
				// Sleeping the consumer thread for random amount of time
				Thread.sleep((int)(Math.random() * 100));
			}
		}
	}
	
	// Short Term Scheduler
	public void shorttermScheduler() throws InterruptedException 
	{
		while(true) 
		{
			// Synchronized so that no two threads can axis New queue at the same time
			synchronized(this)
			{
				// Proceed execution when ready queue size is full OR if not full, blocked queue size is zero
				while(!( flag1 == 1 &&  (ss.getRows().size()==5 || (ss.getRows().size()<5 && readySuspendQueue.size()==0 && newqueue.size()==0))  )) 
				{
					wait();
				}
			
				System.out.println("\nPROCESSING READY QUEUE (short term)");
				if(ss.getRows().size()==0) 
				{
					System.out.println("\nOVER!");
					break;
				}
				
				// Running all the algorithms 
				ss.execute(firsttime);
				fcfs.execute(firsttime);
				drr.execute(firsttime);
					
				// Assigning a variable first time to keep track of running of the process functions for the first time
				if(firsttime==0)
				{
					firsttime=1;
				}
				
				// Notify when there is at least one space for process in READY queue
				notify();
				
				// Random sleep
				Thread.sleep((int)(Math.random() * 100));
				
		    }
		}
	}
	
}
