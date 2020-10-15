package CPU_Scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

//------------------------------------------------- DYNAMIC ROUND ROBIN -------------------------------------------------------------------------------

public class DRR extends Scheduler 
{
	static int timeNow = 0, burstCount;
	static ArrayList<Process> mainQueue = new ArrayList<Process>(0);      
	static ArrayList<String> checkProcess = new ArrayList<>();
	 
	// process function - called by short term scheduler to process the READY QUEUE
	public void execute(int flag1) 
	{
	    ArrayList<Process> burstQueue;
	    
	   // flag used to check the cases and change the timeQuantum accordingly
	    boolean flag = true;   
	    int maxBurstTime;
	    float timeQuantum = 0;
	    
	    // if the process function is executing first time, then sort the processes according to the arrival time
	    if(flag1==0) 
	    {
	    	// sorting the processes on the basis of increasing order of arrival time
			Collections.sort(this.getRows(), (Object firstProcess, Object secondProcess) -> {
	            if (((Process) firstProcess).getArrivalTime() == ((Process) secondProcess).getArrivalTime()) {
	                return 0;
	            }
	            else if (((Process) firstProcess).getArrivalTime() < ((Process) secondProcess).getArrivalTime()) {
	                return -1;
	            }
	            else  {
	                return 1;
	            }
	        });
	    }
		
	    // Sorting the row_btat list according to arrival times for displaying the contents of all the processes
		Collections.sort(this.row_btat, (Object firstProcess, Object nextProcess) -> {
            if (((Process) firstProcess).getArrivalTime() == ((Process) nextProcess).getArrivalTime())
            {
                return 0;
            }
            else if (((Process) firstProcess).getArrivalTime() < ((Process) nextProcess).getArrivalTime())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        });
	    
	    
		List<Process> rows = this.getRows();
       
        for( ; ; ) 
        {
           addProcess(timeNow);  // check for the process that entered the system until the current time
           burstQueue = (ArrayList<Process>) mainQueue.clone();
            
            // sorting process based on their burst times
            Collections.sort(burstQueue, (Object firstProcess, Object secondProcess) -> {
                if (((Process) firstProcess).getBurstTime() == ((Process) secondProcess).getBurstTime()) {
                    return 0;
                }
                else if (((Process) firstProcess).getBurstTime() < ((Process) secondProcess).getBurstTime())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            });

            // If flag is true and there are processes in burstQueue, then assign the time quantum to 0.8 part of the maximum burst time out of all processes
            if( flag && (burstQueue.size()>0) ) 
            {
              maxBurstTime = burstQueue.get(burstQueue.size()-1).getBurstTime();
              timeQuantum = (0.8f * maxBurstTime);
            }
         
            // Traversing the main queue
            for(int i=0; i<mainQueue.size(); i++)
            {
              // if the process's burst time less that current timeQuantum and not yet finished
            	// assigning CPU to all processes whose burst time is less than time quantum
              if(mainQueue.get(i).getBurstTime() <= timeQuantum && !mainQueue.get(i).isFinish()) 
              {
                int ans;
                if( (burstCount-mainQueue.get(i).getArrivalTime()) < 0 )
                	ans = 0;
                else
                	ans = burstCount-mainQueue.get(i).getArrivalTime();
                
                // Adding each process finish time and start time for each quantum it is executing
                this.getTimeline().add(new CPU_Scheduler.ProcessState(mainQueue.get(i).getProcessId(), burstCount, burstCount + mainQueue.get(i).getBurstTime()));
                
                if(!checkProcess.contains(mainQueue.get(i).getProcessId())) {
             	   respmap.put(mainQueue.get(i).getProcessId(), ans);
             	   checkProcess.add(mainQueue.get(i).getProcessId());
                }
                
                // incrementing the context switch
                contextSwitch++;
                
                // setting the waiting time and turn around time
                mainQueue.get(i).setWaitingTime(ans);
                waitmap.put(mainQueue.get(i).getProcessId(), ans);
                timeNow = timeNow + mainQueue.get(i).getBurstTime();
                burstCount = burstCount + mainQueue.get(i).getBurstTime();
                
                // setting the finish status of the process
                mainQueue.get(i).setFinish(true);
                mainQueue.get(i).setTurnaroundTime(ans + mainQueue.get(i).getBurstTime());
                tatmap.put(mainQueue.get(i).getProcessId(), ans + mainQueue.get(i).getBurstTime());
                
                // removing the finished process from the ready queue
                for(int jj=0; jj<rows.size(); jj++) 
                {
                	if(mainQueue.get(i).getProcessId().equals(rows.get(jj).getProcessId()))
                	{	
                		rows.remove(jj);
                	}
                }
                
                if(mainQueue.size() != 8) 
                	return;
              }
            }
            
            // if the size of the main queue of this DRR algorithm is full, then set the time quantum to the max burst time out of all the left processes
            // in the ready queue and finish them
            if(mainQueue.size() == 8) 
            {
                timeQuantum = burstQueue.get(burstQueue.size()-1).getBurstTime();
                flag = false;
            }

            // if all the processes are finished, break the function
            if(rows.isEmpty()) 
            {
                break;
            }
        }
		
	}
	
	// ADD process function adds the process from ready queue to p1 list according to the current time
	public void addProcess(int time)
	{
	    // this method used to add the processes that arrive till current time to mainQueue list
	    for(int j=0; j<this.getRows().size(); j++)
	    {
	      if(this.getRows().get(j).getArrivalTime() <= time)
	      { 
	    	  if(!mainQueue.contains(this.getRows().get(j)))
	    		  mainQueue.add(this.getRows().get(j)); 
	      }
	    }
	     
	    timeNow++;
	    return;
	}

}
