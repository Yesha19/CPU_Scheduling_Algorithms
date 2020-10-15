package CPU_Scheduler;

// PROCESS CLASS - has attributes - ID, WT, TAT, RT, AT, BT
public class Process
{
	// Data members of Process class
    private String processId;
    private int waitingTime;
    private int turnaroundTime;
    private int responseTime;
    private int arrivalTime;
    private int burstTime;
    private boolean finish=false;
    
    // Default Constructor
    public Process() {
    	
    }
    
    // Parameterized constructor - assigns process id Burst time and arrival time
    public Process(String processId, int arrivalTime, int burstTime)
    {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
    
    // GETTERS AND SETTERS FOR EACH OF THE VALUES MENTIONED ABOVE 
    
    // setting Burst Time
    public void setBurstTime(int burstTime)
    {
        this.burstTime = burstTime;
    }
    
    // Get waiting time
    public int getWaitingTime()
    {
        return this.waitingTime;
    }
    
    // get response time
    public int getResponseTime()
    {
        return this.responseTime;
    }
    
    // get turnaround time
    public int getTurnaroundTime()
    {
        return this.turnaroundTime;
    }
    
    // set waiting time
    public void setWaitingTime(int waitingTime)
    {
        this.waitingTime = waitingTime;
    }
    
    // set response time
    public void setResponseTime(int responseTime)
    {
        this.responseTime = responseTime;
    }
    
    // set turnaround time
    public void setTurnaroundTime(int turnaroundTime)
    {
        this.turnaroundTime = turnaroundTime;
    }
    
    // get Process id
    public String getProcessId()
    {
        return this.processId;
    }
    
    // get arrival time
    public int getArrivalTime()
    {
        return this.arrivalTime;
    }
    
    // get burst time
    public int getBurstTime()
    {
        return this.burstTime;
    }
    
    // setting the process finish state
    void setFinish(boolean finish) 
    {
      this.finish = finish;
    }

    // checking if the process is finished or not
    boolean isFinish() 
    {
      return (finish==true) ? true : false;
    }
}
