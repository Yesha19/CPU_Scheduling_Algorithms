package CPU_Scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

// ------------------------------------------------- ROUND ROBIN -------------------------------------------------------------------------------
public class RR1 extends Scheduler 
{	
	Map<String, Integer> endtimeprev = new HashMap();
	static int time, cr=0;
	int flag=0, x, finish=0;
	
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
		
		// Assigning ready queue to the variable rows (NOTE: they point to the same address)
		List<Process> rows = this.getRows();
		
		// Taking the first process arrival time only for once
		if(cr==0) 
		{
			time = rows.get(0).getArrivalTime();
		}
		
		 cr = 1;
	     int timeQuant = this.getTimeQuantum();
	     
	     // run till the ready queue is empty
	     while (!rows.isEmpty())
	     {
		     Process row = rows.get(0);
		     int bt = (row.getBurstTime() < timeQuant ? row.getBurstTime() : timeQuant);
		    
		     if(!waitmap.containsKey(row.getProcessId()))
		     { 
		    	 x = (time-row.getArrivalTime());
		    	 if(x<0) 
		    	 {
		    		 x = 0;
		    		 flag=1;
		    		 waitmap.put(row.getProcessId(), 0);
		    		 
		    		 // Adding each process finish time and start time for each quantum it is executing
		    		 this.getTimeline().add(new ProcessState(row.getProcessId(), row.getArrivalTime(), row.getArrivalTime() + bt)); 
		    		 
		    		 // Assigning the response time of the current process
		    		 respmap.put(row.getProcessId(), x);
		    		 
		    		 // incrementing the context switch
		    		 contextSwitch++;
		    		 
		    	 } else {
		    		 waitmap.put(row.getProcessId(), x);
		    		 
		    		 // Adding each process finish time and start time for each quantum it is executing
		    		 this.getTimeline().add(new ProcessState(row.getProcessId(), time, time + bt));
		    		 respmap.put(row.getProcessId(), x);
		    		 
		    		 // incrementing the context switch
		    		 contextSwitch++;
		    	 }
		    	 
		        tatmap.put(row.getProcessId(), (row.getBurstTime() + x));
		    	 
		     } else {
		    	 
		    	 // Taking subtraction of prev end time and current time
		    	 int w = time - endtimeprev.get(row.getProcessId());
		    	 x = waitmap.get(row.getProcessId());
	             waitmap.put(row.getProcessId(), ( x + w ));
	             
	          // Adding each process finish time and start time for each quantum it is executing
	             this.getTimeline().add(new ProcessState(row.getProcessId(), time, time + bt));
	             contextSwitch++;
	       
	             // Adding previous tat with current waiting time calculated
	             int tat = tatmap.get(row.getProcessId());
	             tatmap.put(row.getProcessId(), (tat + w));
		     }
		     
		     // If process arrival time was greater than the current time then, then assign end time previous to (AT+ BT) else assign to (TIME+BT)
		     if(flag==1) 
		     {
		    	 endtimeprev.put(row.getProcessId(), (row.getArrivalTime() + bt));
		     } else {
		    	 endtimeprev.put(row.getProcessId(), (time + bt));
		    	 
		     }
		     
		     // flag is 1 when wait time is 0 and this happens when a process's arrival time is larger than time at present 
		    if(flag==1) 
		    {
		    	 // setting the time to BT + AT because AT > time at present
		    	 time =  bt + row.getArrivalTime(); 
		    	 flag=0;
		     } 
		    else 
		    {
		    	// setting time to BT + time as time at present > AT of next process
		    	 time = time + bt;
		     }
		    
		     rows.remove(0);
		     boolean checkBt = (row.getBurstTime() > timeQuant);
		     
		     int i;
		     if (checkBt)
	         {
		    	 int setBT = (row.getBurstTime() - timeQuant);
	             row.setBurstTime(setBT);
	             
	             // Adding the unfinished process back to the ready queue 
	             for (i = 0; i < rows.size(); i++)
	             {
	            	 // Adding this row before the process whose arrival time is larger than the current time
	                 if (rows.get(i).getArrivalTime() > time)
	                 {  
	                     rows.add(i, row);
	                     break;
	                 }
	                 else if (i == rows.size()-1)
	                 {
	                     rows.add(row);
	                     break;
	                 }
	             }
	         } 
		     else 
		     {	 
	        	 return;
	         }
	     }
	}
}
