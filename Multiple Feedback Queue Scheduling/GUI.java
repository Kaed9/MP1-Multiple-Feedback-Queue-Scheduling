import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class GUI extends JFrame implements ActionListener {
	
	private JMenuBar menu_bar;
	private JMenu file, tests/*, help*/;
	private JMenuItem exit/*, help1, about*/, generateProcess, clear, imp, use;
	// private JPanel pcb, queues, infos;
	
	private QueuesPanel queuesPanel = null;
	private ProcessControlBlock processControlBlock = null;
	private InfoPanel infoPanel = null;
	private SummaryPanel summaryPanel = null;
	private CPUSched sched = null;

	private int globalMaxProcesses = 0;
	
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
		use.addActionListener(this);
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
		
		tests = new JMenu("Tests");
		tests.setMnemonic(KeyEvent.VK_T);
		menu_bar.add(tests);

		use = new JMenuItem("Use Test Cases");
		use.setMnemonic(KeyEvent.VK_U);

		tests.add(use);

		/*help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		menu_bar.add(help);
		
		help1 = new JMenuItem("Help...");
		help1.setMnemonic(KeyEvent.VK_E);
		about = new JMenuItem("About...");
		about.setMnemonic(KeyEvent.VK_B);
		
		help.add(help1);
		help.add(about);*/
	}
	
	public void infoPanels() {
		
		// pcb = new JPanel();
		// pcb.setSize(500, 680);
		// pcb.setLocation(20, 20);
		processControlBlock = null;
		processControlBlock = new ProcessControlBlock();
		processControlBlock.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		processControlBlock.setBounds(20, 20, 500, 380);
		// processControlBlock.setSize(500, 680);
		// processControlBlock.setLocation(20, 20);
		
		// queues = new JPanel();
		// queues.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		// queues.setSize(740, 430);
		// queues.setLocation(540, 20);
		queuesPanel = null;
		queuesPanel = new QueuesPanel();
		queuesPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		queuesPanel.setBounds(540, 20, 740, 380);
		// queuesPanel.setSize(740, 680);
		// queuesPanel.setLocation(540, 20);
		
		// infos = new JPanel();
		// infos.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		// infos.setSize(740, 230);
		// infos.setLocation(540, 470);
		infoPanel = null;
		infoPanel = new InfoPanel();
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		infoPanel.setBounds(630, 420, 650, 280);
		// infoPanel.setSize(740, 230);
		// infoPanel.setLocation(540, 470);

		summaryPanel = null;
		summaryPanel = new SummaryPanel();
		summaryPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
		summaryPanel.setBounds(20, 420, 590, 280);
		
		add(processControlBlock);
		add(queuesPanel);
		add(infoPanel);
		add(summaryPanel);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == generateProcess) {
			String[] inputs = JOptionPaneExample.displayGUI();
			if(inputs[0] != null && inputs[1] != null) {
				// System.out.println(inputs[0] + " " + inputs[1]);
				int maxProcesses = Integer.parseInt(inputs[0]);
				globalMaxProcesses = maxProcesses;
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
			summaryPanel.clearSummaryPanel();
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
			int quantumTime = 0;
			for(String temp : inputs1[2].split("\n")) {
				algorithm = temp;
			}
			for(String temp : inputs1[3].split("\n")) {
				quantumTime = Integer.parseInt(temp);
			}
			// System.out.println(algorithm);
			Queue printable = null;
			switch(algorithm) {
				case "First Come First Serve":
					printable = sched.FCFS();
					break;
				case "Shortest Job First":
					printable = sched.SJF();
					break;
				case "Shortest Remaining Time First":
					printable = sched.SRTF();
					break;
				case "Preemptive Priority Scheduling":
					printable = sched.Prio();
					break;
				case "Non-preemptive Priority Scheduling":
					printable = sched.NPrio();
					break;
				case "Round Robin":
					printable = sched.RR(quantumTime);
					break;
				default:
					break;
			}
			// System.out.println("|" + algorithm + "|");
			// String[] string = new String[temp.length];
			// for(int i = 0; i < temp.length; i++) {
			// 	string[i] = String.valueOf(temp[i].getProcessID());
			// }

			Queue infoQueue = new Queue()/*, summaryTime = new Queue()*/;
			Process adder = null;
			int printableLength = printable.getIndex();

			while(printableLength > 0) {
				adder = printable.dequeue();
				if(infoQueue.getIndex() == 0) {
					infoQueue.initialProcess(adder);
					// summaryTime.initialProcess(adder);
				} else {
					infoQueue.enqueue(adder);
					// summaryTime.enqueue(adder);
				}

				printable.enqueue(adder);
				printableLength--;
			}

			queuesPanel.addToQueue(printable, globalMaxProcesses, summaryPanel, algorithm);
			infoPanel.addToField(infoQueue, infoQueue.getIndex());
			// summaryPanel.addToTime(summaryTime, globalMaxProcesses);
		} else if(event.getSource() == use) {
			String[] inputs2 = JOptionPaneExample.chooseTests();
			int qT = Integer.parseInt(inputs2[2]);
			// System.out.println(inputs2[1]);
			queuesPanel.queuesHolder("1");
			infoPanel.additionalInfo(inputs2[0], "none");
			globalMaxProcesses = processControlBlock.generateTests(inputs2);

			sched = new CPUSched(processControlBlock.getProcessList());
			// Process[] temp = null;
			Queue printable = null;
			switch(inputs2[0]) {
				case "First Come First Serve":
					printable = sched.FCFS();
					break;
				case "Shortest Job First":
					printable = sched.SJF();
					break;
				case "Shortest Remaining Time First":
					printable = sched.SRTF();
					break;
				case "Preemptive Priority Scheduling":
					printable = sched.Prio();
					break;
				case "Non-preemptive Priority Scheduling":
					printable = sched.NPrio();
					break;
				case "Round Robin":
					printable = sched.RR(qT);
					break;
				default:
					break;
			}
			// String[] string = new String[temp.length];
			// for(int i = 0; i < temp.length; i++) {
			// 	string[i] = String.valueOf(temp[i].getProcessID());
			// }

			Queue infoQueue = new Queue();
			Process adder = null;
			int printableLength = printable.getIndex();

			while(printableLength > 0) {
				adder = printable.dequeue();
				if(infoQueue.getIndex() == 0)
					infoQueue.initialProcess(adder);
				else
					infoQueue.enqueue(adder);

				printable.enqueue(adder);
				printableLength--;
			}

			queuesPanel.addToQueue(printable, globalMaxProcesses, summaryPanel, inputs2[0]);
			infoPanel.addToField(infoQueue, infoQueue.getIndex());
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