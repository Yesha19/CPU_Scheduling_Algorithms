package CPU_Scheduler;

// THREADS CLASS - making 3 threads to run concurrently and they call 3 functions present in MainThreads class - makeProcess, longTermScheduler and shortTermScheduler
public class Threads
{	
	// Instance of MainThreads class created
	final MainThreads pc1;
	
	// Assigning different algorithm instances
	public Threads(Scheduler rr, Scheduler fcfs, Scheduler drr) 
	{
		pc1 = new MainThreads(rr, fcfs, drr);
	}
	
	// Call Threads method - make 3 processes of Runnable class
	public void callThreads() 
	{
		// THREAD 1 - making process
		Thread t1 = new Thread(new Runnable() 
	    { 
	        public void run() 
	        { 
	        	try 
	        	{
					pc1.makeProcess();
				} 
	        	catch (InterruptedException e) 
	        	{
					e.printStackTrace();
				}
	        } 
	    }); 
		
		// THREAD 2 - long term scheduler
		Thread t2 = new Thread(new Runnable() 
	    { 
	        public void run() 
	        { 
	        	
				try 
				{
					pc1.longTermScheduler();
					
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } 
	    }); 
		
		// THREAD 3 - Short term scheduler
		Thread t3 = new Thread(new Runnable() 
	    { 
	        public void run() 
	        { 
	        	//calling consumer function
				try 
				{
					pc1.shorttermScheduler();			
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
	        } 
	    }); 
		
		// Starting all the 3 threads
		t1.start(); 
		t2.start(); 
		t3.start();
	}
	
}
