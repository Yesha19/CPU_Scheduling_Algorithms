package CPU_Scheduler;

// PROCESS STATE CLASS - has attributes - ID, initial time and final time

public class ProcessState
{
	// Event noting start time and end time for the sequence of processes executing in each quanta
    private final String processId;
    private final int initialTime;
    private int finishTime;
    
    // Paramterized Constructor
    public ProcessState(String processId, int initialTime, int finishTime)
    {
        this.processId = processId;
        this.initialTime = initialTime;
        this.finishTime = finishTime;
    }
    
    // --------------------- GETTERS & SETTERS -------------------------------
    // Get process Id
    public String getprocessId()
    {
        return processId;
    }
    
    // Get initial time
    public int getInitialTime()
    {
        return initialTime;
    }
    
    // Get finish time
    public int getFinishTime()
    {
        return finishTime;
    }
    
    // Setting finish time
    public void setFinishTime(int finishTime)
    {
        this.finishTime = finishTime;
    }
}
