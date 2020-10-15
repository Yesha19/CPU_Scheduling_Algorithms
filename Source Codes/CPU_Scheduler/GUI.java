package CPU_Scheduler;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.*;

// CustomPanel Class 
class CustomPanel extends JPanel
{   
    private List<ProcessState> list;
    int xrange, yrange;
    
    // Constructor
    public CustomPanel() {
    	
    }
    
    public void setTimeline(List<ProcessState> timeline) {
        this.list = timeline;
        repaint();
    }
    
    // Overriding the painComponent function to display the scheduler's timeline
    @Override
    protected void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        
        boolean check = (list != null);
        if (check)
        { 
        	int i;
            for (i = 0; i < list.size(); i++)
            {
                xrange = 30 * (i + 1);
                yrange = 20;
                ProcessState e1 = list.get(i);
                
                // Making box and assigning the end time and start time of the process in a chain for display
                graphic.drawRect(xrange, yrange, 30, 30);
                graphic.drawString(e1.getprocessId(), xrange + 10, yrange + 20);             
                graphic.drawString(Integer.toString(e1.getInitialTime()), xrange - 5, yrange + 45);
                
                boolean check1 = (i == list.size()-1);
                if (check1)
                {
                	String f = Integer.toString(e1.getFinishTime());
                    graphic.drawString(f, xrange + 27, yrange + 45);
                }
            }
        }
    }
}


// --------------- GUI WINDOW FOR DISPLAYING ALL THE SCHEDULING ALGORTIHMS -------------------------------------
public class GUI
{
	// Declaring variables
	private JLabel waitLabel, waitValueLabel, tatLabel, tatValueLabel, contLabel, contValueLabel, respLabel, respValueLabel;
    private JFrame finalFrame;
    private String choice;
    private JPanel mainWindow;
    private CustomPanel chartWindow;
    private JScrollPane tablePane, sPane;
    private JTable tablularForm;
    private JButton displayButton;
    private JComboBox selectScheduler;
    private DefaultTableModel guiDisplay;
    
    // GUI constructor
    public GUI(Scheduler rr, Scheduler fcfs, Scheduler drr)
    {
    	// Table Model header
        guiDisplay = new DefaultTableModel(new String[]{"Process", "AT", "BT", "WT", "TAT","RT"}, 0);
        
        // TABLE CONTENTS
        tablularForm = new JTable(guiDisplay);
        tablularForm.setFillsViewportHeight(true);
        tablePane = new JScrollPane(tablularForm);
        tablePane.setBounds(25, 25, 450, 250);
        
        chartWindow = new CustomPanel();
        chartWindow.setBackground(Color.WHITE);
        
        sPane = new JScrollPane(chartWindow);
        sPane.setBounds(25, 310, 450, 100);
        
        // ------------------- Label definitions -------------------------
        // WAIT LABEL NAME
        waitLabel = new JLabel("Avg Waiting Time:");
        waitLabel.setBounds(25, 425, 180, 25);
        
        // TAT LABEL NAME
        tatLabel = new JLabel("Avg TurnAround Time:");
        tatLabel.setBounds(25, 450, 180, 25);
        
        // RESPONSE TIME LABEL
        respLabel = new JLabel("Average Response Time:");
        respLabel.setBounds(25, 475, 180, 25);
        
        // CONTEXT SWITCHES LABEL NAME
        contLabel = new JLabel("Number of Context Switches:");
        contLabel.setBounds(25, 500, 180, 25);
        
        // WAIT LABEL VALUE
        waitValueLabel = new JLabel();
        waitValueLabel.setBounds(215, 425, 180, 25);
        
        // TAT LABEL VALUE
        tatValueLabel = new JLabel();
        tatValueLabel.setBounds(215, 450, 180, 25);
        
        // RESPONSE LABEL VALUE
        respValueLabel = new JLabel();
        respValueLabel.setBounds(215, 475, 180, 25);
        
        // CONTEXT SWITCHES LABEL VALUE
        contValueLabel = new JLabel();
        contValueLabel.setBounds(215, 500, 180, 25);
        
        // DEFINING THE COMBO BOX WITH THE SCHEDULERS IMPLEMENTED
        selectScheduler = new JComboBox(new String[]{"FCFS", "RR 10", "DRR"});
        selectScheduler.setBounds(390, 420, 85, 20);
        
        // Display Button to display the contents of the scheduler selected
        displayButton = new JButton("Display");
        displayButton.setBounds(390, 450, 85, 25);
      
        displayButton.addActionListener(new ActionListener() 
        {
        	// Action listener of the display button
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	// Inputting the type of scheduler from user (default is FCFS)
                choice = (String) selectScheduler.getSelectedItem();
                Scheduler displayScheduler;
                
                // clearing the table after choosing one of the parameters
                guiDisplay.setRowCount(0);
                
                // Switch case to assign the scheduler from the choice taken by user
                switch (choice) 
                {
                    case "FCFS":
                    	displayScheduler = fcfs;
                        break;
                        
                    case "RR":
                    	displayScheduler = rr;
                        break;
                        
                    case "DRR":
                    	displayScheduler = drr; 
                        break;
                        
                    default:
                        return;
                }
                
                // Displaying the values of arrival time, burst time, turnaround time, waitingtime, and response time of all the proceses in the table
                for (int i = 0; i < displayScheduler.row_btat.size(); i++)
                {
                	Process row = displayScheduler.row_btat.get(i);
                	guiDisplay.addRow(new String[]{row.getProcessId(), Integer.toString(row.getArrivalTime()), Integer.toString(row.getBurstTime()), Integer.toString(displayScheduler.waitmap.get(row.getProcessId())), Integer.toString(displayScheduler.tatmap.get(row.getProcessId())), Integer.toString(displayScheduler.respmap.get(row.getProcessId()))});
                }
                                
                waitValueLabel.setText(Double.toString(displayScheduler.getAverageWaitingTime()));
                tatValueLabel.setText(Double.toString(displayScheduler.getAverageTurnAroundTime()));
                respValueLabel.setText(Double.toString(displayScheduler.getAverageResponseTime()));
                contValueLabel.setText(Double.toString(displayScheduler.getContextSwicth()-1));
                
                chartWindow.setTimeline(displayScheduler.getTimeline());
                
            }
        });
        
        // Adding the contents in the mainWindow variable
        mainWindow = new JPanel(null);
        mainWindow.setPreferredSize(new Dimension(500, 540));
        mainWindow.add(tablePane);
        mainWindow.add(sPane);
        
        // Adding labels to the mainWindow
        
        // Adding wait label
        mainWindow.add(waitLabel);
        
        // Adding tat label
        mainWindow.add(tatLabel);
        
        // adding context switch label
        mainWindow.add(contLabel);
        
        // adding response time label
        mainWindow.add(respLabel);
        
        // Adding wait value label
        mainWindow.add(waitValueLabel);
        
        // Adding tat value label
        mainWindow.add(tatValueLabel);
        
        // Adding context switch value label
        mainWindow.add(contValueLabel);
        
        // Adding response time value label
        mainWindow.add(respValueLabel);
        
        // Adding scheduler and display buttton
        mainWindow.add(selectScheduler);
        mainWindow.add(displayButton);
        
        // Adding the mainWindow to the finalFrame and assigning label to the finalFrame
        finalFrame = new JFrame("Scheduling Algorithms");
        
        // exit on close operation
        finalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        finalFrame.setVisible(true);
        finalFrame.setResizable(false);
        finalFrame.add(mainWindow);
        finalFrame.pack();
    }
}

