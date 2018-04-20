import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class GUI extends JFrame implements ActionListener {
	
	private JMenuBar menu_bar;
	private JMenu file, help;
	private JMenuItem exit, help1, about, generateProcess, clear, imp;
	// private JPanel pcb, queues, infos;
	
	private QueuesPanel queuesPanel = null;
	private ProcessControlBlock processControlBlock = null;
	private InfoPanel infoPanel = null;
	private CPUSched sched = null;
	
	public GUI() {
		
		super("Multiple Feedback Queue Scheduling");
		setLayout(null);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) { }
		getContentPane().setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		
		createGUI();
	}
	
	public void createGUI() {
		
		addMenuBarComponents();
		infoPanels();
		
		generateProcess.addActionListener(this);
		clear.addActionListener(this);
		imp.addActionListener(this);
		exit.addActionListener(this);
	}
	
	public void addMenuBarComponents() {
		
		menu_bar = new JMenuBar();
		setJMenuBar(menu_bar);
		
		file = new JMenu("App");
		file.setMnemonic(KeyEvent.VK_A);
		menu_bar.add(file);
		
		generateProcess = new JMenuItem("Generate Processes");
		generateProcess.setMnemonic(KeyEvent.VK_G);

		clear = new JMenuItem("Clear All");
		clear.setMnemonic(KeyEvent.VK_C);

		imp = new JMenuItem("Implement MLFQ");
		imp.setMnemonic(KeyEvent.VK_I);
		imp.setEnabled(false);

		exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		
		file.add(generateProcess);
		file.add(imp);
		file.addSeparator();
		file.add(clear);
		file.addSeparator();
		file.add(exit);
		
		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		menu_bar.add(help);
		
		help1 = new JMenuItem("Help...");
		help1.setMnemonic(KeyEvent.VK_E);
		about = new JMenuItem("About...");
		about.setMnemonic(KeyEvent.VK_B);
		
		help.add(help1);
		help.add(about);
	}
	
	public void infoPanels() {
		
		// pcb = new JPanel();
		// pcb.setSize(500, 680);
		// pcb.setLocation(20, 20);
		processControlBlock = null;
		processControlBlock = new ProcessControlBlock();
		processControlBlock.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		processControlBlock.setBounds(20, 20, 500, 680);
		// processControlBlock.setSize(500, 680);
		// processControlBlock.setLocation(20, 20);
		
		// queues = new JPanel();
		// queues.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		// queues.setSize(740, 430);
		// queues.setLocation(540, 20);
		queuesPanel = null;
		queuesPanel = new QueuesPanel();
		queuesPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		queuesPanel.setBounds(540, 20, 740, 430);
		// queuesPanel.setSize(740, 680);
		// queuesPanel.setLocation(540, 20);
		
		// infos = new JPanel();
		// infos.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		// infos.setSize(740, 230);
		// infos.setLocation(540, 470);
		infoPanel = null;
		infoPanel = new InfoPanel();
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		infoPanel.setBounds(540, 470, 740, 230);
		// infoPanel.setSize(740, 230);
		// infoPanel.setLocation(540, 470);
		
		add(processControlBlock);
		add(queuesPanel);
		add(infoPanel);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == generateProcess) {
			String[] inputs = JOptionPaneExample.displayGUI();
			if(inputs[0] != null && inputs[1] != null) {
				// System.out.println(inputs[0] + " " + inputs[1]);
				int maxProcesses = Integer.parseInt(inputs[0]);
				processControlBlock.generateProcesses(maxProcesses, inputs[1], inputs[2], inputs[3], inputs[4]);
				imp.setEnabled(true);
				// sched = new CPUSched(processControlBlock.getProcessList());
				// System.out.println("FCFS");
				// sched.FCFS(queuesPanel);
			} else { }
		} else if(event.getSource() == clear) {
			processControlBlock.clearTables();
			queuesPanel.clearQueuesPanel();
			infoPanel.clearInfoPanel();
			imp.setEnabled(false);
		} else if(event.getSource() == imp) {
			// queuesPanel.addToQueue();
			String[] inputs1 = JOptionPaneExample.displayGUI1();
			queuesPanel.queuesHolder(inputs1[0]);
			infoPanel.additionalInfo(inputs1[2], inputs1[1]);
			// System.out.println(inputs1[0]);
			// System.out.println(inputs1[1]);
			// System.out.println(inputs1[2]);
			sched = new CPUSched(processControlBlock.getProcessList());
			// System.out.println("SJF");
			String algorithm = "";
			for(String temp : inputs1[2].split("\n")) {
				algorithm = temp;
			}
			// System.out.println(algorithm);
			Process[] temp = null;
			switch(algorithm) {
				case "First Come First Serve":
					temp = sched.FCFS(); // works
					break;
				case "Shortest Job First":
					temp = sched.SJF(); // works
					break;
				case "Shortest Remaining Time First":
					temp = sched.SRTF();
					break;
				case "Preemptive Priority Scheduling":
					temp = sched.Prio();
					break;
				case "Non-preemptive Priority Scheduling":
					temp = sched.NPrio(); // works
					break;
				case "Round Robin":
					sched.RR(3); // sample quantum time
					break;
				default:
					break;
			}
			String[] string = new String[temp.length];
			for(int i = 0; i < temp.length; i++) {
				string[i] = String.valueOf(temp[i].getProcessID());
			}

			queuesPanel.addToQueue(temp);
			infoPanel.addToField(string, temp.length);
		} else if(event.getSource() == exit) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		Runnable runner = new Runnable() {
			public void run() {
				new GUI();
			}
		};
		EventQueue.invokeLater(runner);
	}
}