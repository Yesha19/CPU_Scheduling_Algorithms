package CPU_Scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

// SCHEDULER CLASS - parent of all the algorithms implemented
// Contains common attributes of all the algorithms

public abstract class Scheduler
{
    public List<Process> rows;
    public List<Process> row_btat;

    // HASHMAPs for waiting time, turn around time and response time of each process
    protected Map<String, Integer> waitmap = new HashMap<>();
    protected Map<String, Integer> tatmap = new HashMap<>();
    protected Map<String, Integer> respmap = new HashMap<>();
    
    private final List<ProcessState> timeline;
    private int timeQuant;
    protected int contextSwitch;
    
    // Constructor - initializing data members of this class
    public Scheduler()
    {
        rows = new ArrayList();
        timeline = new ArrayList();
        timeQuant = 1;
        row_btat = new ArrayList();
        contextSwitch = 0;
    }
    
    // Abstract function - need to be implemented by the child classes
    public abstract void execute(int f);
    
    // Add row funtion
    public boolean add(Process row)
    {
        return rows.add(row);
    }
    
    // Set time quantum function
    public void setTimeQuantum(int timeQuant)
    {
        this.timeQuant = timeQuant;
    }
    
    // Get time quantum
    public int getTimeQuantum()
    {
        return timeQuant;
    }
    
    // get average waiting time
    public double getAverageWaitingTime()
    {
        double average = 0.0;
        
        for (Process row : row_btat)
        {
            average = average + waitmap.get(row.getProcessId());
        }
        
        return average / row_btat.size();
    }
    
    // get average turn around time
    public double getAverageTurnAroundTime() 
    {
        double average = 0.0;
        
        for (Process row : row_btat)
        {
            average = average + tatmap.get(row.getProcessId());
        }
        
        return average / row_btat.size();
    }
    
    // get average response time
    public double getAverageResponseTime() 
    {
        double average = 0.0;
        
        for (Process row : row_btat)
        {
            average = average + respmap.get(row.getProcessId());
        }
        
        return average / row_btat.size();
    }
    
    // getting the the process's finish time and start time for each quanta it runs
    public ProcessState getEvent(Process row) {
        for (ProcessState event : timeline)
        {
            if (row.getProcessId().equals(event.getprocessId()))
            {
                return event;
            }
        }
        
        return null;
    }
    
    public List<ProcessState> getTimeline() {
        return timeline;
    }
    
    // get row function - returns Ready queue
    public List<Process> getRows()
    {
        return rows;
    }
       
    // get Context switch function
    public int getContextSwicth() 
    {
    	return contextSwitch;
    }
}
