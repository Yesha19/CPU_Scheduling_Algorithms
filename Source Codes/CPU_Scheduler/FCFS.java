package CPU_Scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ------------------------------------------------- FIRST COME FIRST SERVE -----------------------------------------------------------
public class FCFS extends Scheduler 
{	
	Map<String, Integer> endtimeprev = new HashMap();
	static int time;
	static int cr = 0;
	int flag=0, waitTime, finish=0;
	
	public void execute(int flag1) 
	{
		// Sorting process in the READY queue according to their burst times
		 if(flag1==0) 
		 {
			 Collections.sort(this.getRows(), (Object firstProcess, Object nextProcess) -> {
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
		
		// Taking the first process arrival time only for once
		if(cr==0) 
		{
			time = rows.get(0).getArrivalTime();
		}
		
		 cr = 1;
		 
	     // while loop till the rows is empty
	     while (!rows.isEmpty())
	     {
		     Process row = rows.get(0);
		      
		     // if the process is not in the hash map called wait map
		     if(!waitmap.containsKey(row.getProcessId()))
		     {
		    	 // set the initial wait time of the new process to currnt time - arrival time. 
		    	 waitTime = (time-row.getArrivalTime());
		    	 
		    	 // if wait time is less than zero, means CPU instantly takes the process
		    	 if(waitTime<0) 
		    	 {
		    		 // setting wait time to 0 and assigning it in waitmap
		    		 waitTime = 0;
		    		 flag=1;
		    		 waitmap.put(row.getProcessId(), 0);
		    		 
		    		 // Adding each process finish time and start time for each quantum it is executing
		    		 this.getTimeline().add(new ProcessState(row.getProcessId(), row.getArrivalTime(), row.getArrivalTime() + row.getBurstTime()));
		             
		    		 // incrementing the context switch
		    		 contextSwitch++;
		    		 
		    	 } 
		    	 else 
		    	 {
		    		// setting wait time to wait time value and assigning it in waitmap
		    		 waitmap.put(row.getProcessId(), waitTime);
		    		 
		    		 this.getTimeline().add(new ProcessState(row.getProcessId(), time, time + row.getBurstTime()));
		    		 
		    		 // incrementing the context switch
		             contextSwitch++;   
		    	 }
		    	 
		    	 respmap.put(row.getProcessId(), waitTime);
		    	 tatmap.put(row.getProcessId(), (row.getBurstTime() + waitTime));
		    	  
		     } 
		     
		     // flag is 1 when wait time is 0 and this happens when a process's arrival time is larger than time at present 
		     if(flag==1) 
		     {
		    	 // setting the time to BT + AT because AT > time at present
		    	 time =  row.getBurstTime() + row.getArrivalTime(); 
		    	 flag=0;
		     } 
		     else 
		     {  
		    	 // setting time to BT + time as time at present > AT of next process
		    	 time = time + row.getBurstTime();
		     }
		    
		     // removing the finished row from the ready queue
		     rows.remove(0);
		 	 return;
		     
	     }    
	}
}
