import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.Random;
import javax.swing.text.DefaultCaret;

public class ProcessControlBlock extends JPanel {
	
	private JLabel label;
	private JPanel processPanel;
	private JPanel[] panel;
	private JTextArea[] table;
	private JScrollPane[] scrollPane;
	private JScrollBar bar;
	
	// private String[] string = {"<html><div style = 'text-align: center;'>PID</div></html>", 
	// 						   "<html><div style = 'text-align: center;'>Arrival<br/>Time</div></html>",
	// 						   "<html><div style = 'text-align: center;'>CPU<br/>Burst<br/>Time</div></html>",
	// 						   "<html><div style = 'text-align: center;'>Priority</div></html>",
	// 						   "<html><div style = 'text-align: center;'>Execution<br/>History<br/>Information</div></html>", ""};
	private String[] string = {"<html><div style = 'text-align: center;'>PID</div></html>", 
							   "<html><div style = 'text-align: center;'>Arrival<br/>Time</div></html>",
							   "<html><div style = 'text-align: center;'>CPU<br/>Burst<br/>Time</div></html>",
							   "<html><div style = 'text-align: center;'>Priority</div></html>", ""};
	private int count = 0;
	private int[] sizes = {91, 123, 103, 123, 20};
	// private int[] sizes = {60, 92, 72, 92, 124, 20};
	
	public AdjustmentListener listener = new MyAdjustmentListener();
	
	private Font font = new Font("Verdana", Font.BOLD, 28);
	private Font font1 = new Font("Verdana", Font.BOLD, 15);
	private Font font2 = new Font("Verdana", Font.PLAIN, 15);

	private int[] fcfs_test1_arrival = {20, 15, 23, 34, 28, 26, 39, 33, 49, 47};
	private int[] fcfs_test1_burst = {48, 11, 10, 26, 42, 37, 33, 29, 25, 1};
	private int[] fcfs_test2_arrival = {2, 25, 28, 6, 31, 11, 11, 12, 31, 5, 49, 37, 47, 23, 24};
	private int[] fcfs_test2_burst = {38, 32, 6, 34, 42, 24, 45, 30, 9, 5, 21, 41, 37, 31, 38};
	private int[] fcfs_test3_arrival = {21, 37, 26, 25, 39, 27, 16, 33, 6, 43, 23, 42, 23, 48, 33, 9, 29, 12, 45, 23};
	private int[] fcfs_test3_burst = {32, 39, 12, 17, 32, 37, 48, 11, 32, 27, 30, 2, 23, 7, 33, 27, 32, 28, 42, 31};

	private int[] sjf_test1_arrival = {27, 45, 2, 47, 4, 46, 8, 3, 21, 45};
	private int[] sjf_test1_burst = {22, 22, 48, 33, 14, 41, 19, 26, 47, 36};
	private int[] sjf_test2_arrival = {0, 16, 42, 13, 12, 23, 28, 8, 34, 9, 0 , 4, 1, 33, 29};
	private int[] sjf_test2_burst = {27, 3, 4, 18, 41, 37, 33, 15, 32, 38, 36, 5, 8, 13, 14};
	private int[] sjf_test3_arrival = {39, 11, 45, 9, 35, 21, 32, 1, 38, 5, 23, 33, 29, 2, 7, 5, 47, 22, 7, 5};
	private int[] sjf_test3_burst = {39, 36, 25, 4, 14, 23, 28, 19, 28, 34, 36, 3, 18, 23, 44, 33, 39, 50, 10, 33};

	private int[] srtf_test1_arrival = {38, 6, 1, 33, 23, 45, 7, 2, 49, 14};
	private int[] srtf_test1_burst = {20, 8, 20, 15, 23, 29, 5, 6, 28, 25};
	private int[] srtf_test2_arrival = {6, 16, 25, 23, 24, 6, 26, 28, 3, 9, 7, 9, 1, 44, 15};
	private int[] srtf_test2_burst = {50, 36, 14, 2, 45, 37, 5, 25, 45, 12, 48, 45, 30, 13, 23};
	private int[] srtf_test3_arrival = {43, 0, 23, 28, 10, 37, 16, 33, 48, 29, 28, 1, 46, 42, 38, 14, 33, 10, 36, 11};
	private int[] srtf_test3_burst = {42, 39, 33, 30, 7, 4, 27, 15, 42, 25, 23, 27, 34, 26, 4, 15, 48, 17, 20, 40};

	private int[] prio_test1_arrival = {42, 49, 43, 29, 13, 3, 26, 21, 6, 48};
	private int[] prio_test1_burst = {33, 44, 33, 25, 42, 31, 42, 6, 11, 18};
	private int[] prio_test1_priority = {17, 27, 19, 37, 16, 27, 31, 12, 11, 27};
	private int[] prio_test2_arrival = {35, 20, 36, 43, 22, 37, 32, 21, 46, 36, 47, 47, 33, 2, 47};
	private int[] prio_test2_burst = {29, 8, 12, 47, 5, 5, 1, 7, 40, 3, 30, 26, 20, 27, 3};
	private int[] prio_test2_priority = {22, 40, 29, 7, 8, 33, 37, 1, 10, 1, 6, 23, 37, 35, 6};
	private int[] prio_test3_arrival = {40, 34, 12, 29, 11, 33, 17, 40, 30, 13, 28, 11, 31, 37, 3, 46, 46, 17, 12, 2};
	private int[] prio_test3_burst = {33, 15, 6, 29, 3, 9, 35, 24, 39, 49, 14, 29, 15, 34, 14, 48, 27, 23, 43, 21};
	private int[] prio_test3_priority = {31, 4, 6, 23, 11, 31, 33, 36, 16, 8, 4, 25, 27, 25, 15, 37, 29, 9, 23, 23};

	private int[] nprio_test1_arrival = {6, 18, 7, 33, 24, 13, 19, 15, 46, 3};
	private int[] nprio_test1_burst = {18, 32, 48, 49, 1, 16, 7, 6, 5, 22};
	private int[] nprio_test1_priority = {26, 29, 33, 27, 40, 11, 30, 33, 30, 23};
	private int[] nprio_test2_arrival = {1, 12, 17, 34, 44, 30, 32, 40, 34, 41, 9, 35, 38, 45, 16};
	private int[] nprio_test2_burst = {6, 10, 24, 36, 50, 38, 43, 34, 21, 8, 27, 14, 21, 17, 31};
	private int[] nprio_test2_priority = {37, 16, 4, 19, 30, 5, 24, 35, 10, 14, 36, 34, 12, 18, 13};
	private int[] nprio_test3_arrival = {36, 31, 48, 17, 21, 28, 40, 10, 16, 12, 33, 32, 39, 0, 47, 11, 33, 38, 21, 49};
	private int[] nprio_test3_burst = {15, 21, 15, 9, 30, 43, 12, 42, 49, 47, 44, 10, 22, 37, 13, 3, 2, 50, 1, 15};
	private int[] nprio_test3_priority = {22, 29, 36, 36, 9, 18, 13, 24, 8, 31, 4, 13, 5, 32, 29, 21, 19, 10, 26, 10};

	private int[] rr_test1_arrival = {38, 47, 45, 21, 35, 23, 24, 16, 36, 8};
	private int[] rr_test1_burst = {47, 14, 23, 42, 49, 17, 23, 11, 12, 25};
	private int[] rr_test2_arrival = {49, 13, 13, 2, 15, 16, 34, 11, 19, 3, 26, 19, 43, 14, 45};
	private int[] rr_test2_burst = {18, 9, 15, 27, 40, 8, 17, 2, 31, 3, 8, 11, 34, 16, 6};
	private int[] rr_test3_arrival = {8, 43, 24, 35, 28, 39, 17, 10, 44, 0, 36, 43, 3, 42, 0, 27, 48, 9, 4, 45};
	private int[] rr_test3_burst = {30, 6, 43, 46, 44, 17, 9, 26, 38, 41, 12, 22, 41, 41, 27, 46, 33, 26, 13, 48};

	private Random random = new Random();

	private Process[] processList;
	
	public ProcessControlBlock() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		label = new JLabel("PROCESS CONTROL BLOCK", JLabel.CENTER);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		label.setFont(font);
		label.setBounds(20, 15, 460, 50);
		// label.setSize(460, 70);
		// label.setLocation(20, 20);
		
		processPanel = new JPanel();
		processPanel.setLayout(null);
		// processPanel.setBackground(Color.RED);
		processPanel.setBounds(20, 80, 460, 280);
		// processPanel.setSize(460, 540);
		// processPanel.setLocation(20, 110);
		processPane();
		
		add(label);
		add(processPanel);
	}
	
	public void processPane() {
		
		panel = new JPanel[5];
		table = new JTextArea[5];
		scrollPane = new JScrollPane[5];
		
		for(int i = 0; i < 5; i++) {
			JLabel label1 = new JLabel(string[i], SwingConstants.CENTER);
			label1.setFont(font1);
			label1.setBounds(0, 0, sizes[i], 70);
			// label1.setSize(sizes[i], 70);
			// label1.setLocation(0, 0);
			// label1.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
			// label1.setOpaque(true);
			// label1.setBackground(Color.RED);
			
			panel[i] = new JPanel(null);
			panel[i].add(label1);
			// panel[i].add(label1, BorderLayout.NORTH);
			
			table[i] = new JTextArea(20, 2);
			table[i].setFont(font2);
			table[i].setEditable(false);
			
			if(i != 4) {
				scrollPane[i] = new JScrollPane(table[i], JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				// panel[i].add(scrollPane[i], BorderLayout.CENTER);
			} else {
				scrollPane[i] = new JScrollPane(table[i], JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				// panel[i].add(scrollPane[i], BorderLayout.CENTER);
				bar = scrollPane[i].getVerticalScrollBar();
				bar.addAdjustmentListener(listener);

			}
			// DefaultCaret caret = (DefaultCaret)table[i].getCaret();
			// caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

			scrollPane[i].setBounds(0, 70, sizes[i], 210);
			// scrollPane[i].setSize(sizes[i], 470);
			// scrollPane[i].setLocation(0, 70);
			scrollPane[i].setBorder(BorderFactory.createEmptyBorder());
			panel[i].add(scrollPane[i]);
			panel[i].setBackground(Color.WHITE);
			if(i > 0 && i < 4) {
				panel[i].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
				scrollPane[i].setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			} else if(i == 4) {
				panel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
				scrollPane[i].setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
			} else if (i == 0) {
				panel[i].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
				scrollPane[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			}
			panel[i].setBounds(count, 0, sizes[i], 280);
			// panel[i].setSize(sizes[i], 540);
			// panel[i].setLocation(count, 0);
			processPanel.add(panel[i]);
			count += sizes[i];
		}
		
	}
	
	private class MyAdjustmentListener implements AdjustmentListener {
		
		public void adjustmentValueChanged(AdjustmentEvent e) {
			
			if(e.getSource() == bar && e.getAdjustmentType() == AdjustmentEvent.TRACK) {
				for(int i = 0; i < 4; i++) {
					scrollPane[i].getVerticalScrollBar().setValue(e.getValue());
				}
			}
		}
	}

	public void generateProcesses(int maxProcesses, String checker, String s1, String s2, String s3) {

		/*if(checker.equals("randomized")) {
			processList = new Process[maxProcesses];
			int maxBurst = 50;

			table[0].setText("");
			table[1].setText("");
			table[2].setText("");
			table[3].setText("");
			table[4].setText("");

			for(int i = 0; i < maxProcesses; i++) {
				processList[i] = new Process((i + 1), random.nextInt(50), (random.nextInt(maxBurst) + 1), (random.nextInt(40) + 1));
			}

			for(int i = 0; i < maxProcesses; i++) {
				// System.out.println(processList[i].getProcessID() + "\t" + processList[i].getArrivalTime() + "\t" +  processList[i].getBurstTime() + "\t" +  processList[i].getPriority() + "\t" +  processList[i].getHistoryInfo());
				table[0].append("   P" + processList[i].getProcessID() + "\n");
				table[1].append("      " + processList[i].getArrivalTime() + "\n");
				table[2].append("    " + processList[i].getBurstTime() + "\n");
				table[3].append("      " + processList[i].getPriority() + "\n");
				table[4].append("          " + processList[i].getHistoryInfo() + "\n");
			}
		} else if(checker.equals("user-defined")) {*/
		int[] t1 = new int[maxProcesses];
		int[] t2 = new int[maxProcesses];
		int[] t3 = new int[maxProcesses];
		int index = 0;

		processList = new Process[maxProcesses];

		for(String ye : s1.split("\\s")) {
			t1[index] = Integer.parseInt(ye);
			index++;
		}
		index = 0;
		for(String ye : s2.split("\\s")) {
			t2[index] = Integer.parseInt(ye);
			index++;
		}
		index = 0;
		for(String ye : s3.split("\\s")) {
			t3[index] = Integer.parseInt(ye);
			index++;
		}

		for(int i = 0; i < maxProcesses; i++) {
			processList[i] = new Process((i + 1), t1[i], t2[i], t3[i]);
		}

		for(int i = 0; i < maxProcesses; i++) {
			// System.out.println(processList[i].getProcessID() + "\t" + processList[i].getArrivalTime() + "\t" +  processList[i].getBurstTime() + "\t" +  processList[i].getPriority() + "\t" +  processList[i].getHistoryInfo());
			table[0].append("      " + processList[i].getProcessID() + "\n");
			table[1].append("         " + processList[i].getArrivalTime() + "\n");
			table[2].append("       " + processList[i].getBurstTime() + "\n");
			table[3].append("         " + processList[i].getPriority() + "\n");
			// table[4].append("          " + processList[i].getHistoryInfo() + "\n");
		}
		// }
	}

	public int generateTests(String[] inputs) {
		
		if(inputs[0] == "First Come First Serve") {
			if(inputs[1] == "Test #1") {
				processList = new Process[10];
				for(int i = 0; i < 10; i++) {
					processList[i] = new Process((i + 1), fcfs_test1_arrival[i], fcfs_test1_burst[i], (random.nextInt(40) + 1));
				}
			} else if(inputs[1] == "Test #2") {
				processList = new Process[15];
				for(int i = 0; i < 15; i++) {
					processList[i] = new Process((i + 1), fcfs_test2_arrival[i], fcfs_test2_burst[i], (random.nextInt(40) + 1));
				}
			} else if(inputs[1] == "Test #3") {
				processList = new Process[20];
				for(int i = 0; i < 20; i++) {
					processList[i] = new Process((i + 1), fcfs_test3_arrival[i], fcfs_test3_burst[i], (random.nextInt(40) + 1));
				}
			}
		} else if(inputs[0] == "Shortest Job First") {
			if(inputs[1] == "Test #1") {
				processList = new Process[10];
				for(int i = 0; i < 10; i++) {
					processList[i] = new Process((i + 1), sjf_test1_arrival[i], sjf_test1_burst[i], (random.nextInt(40) + 1));
				}
			} else if(inputs[1] == "Test #2") {
				processList = new Process[15];
				for(int i = 0; i < 15; i++) {
					processList[i] = new Process((i + 1), sjf_test2_arrival[i], sjf_test2_burst[i], (random.nextInt(40) + 1));
				}
			} else if(inputs[1] == "Test #3") {
				processList = new Process[20];
				for(int i = 0; i < 20; i++) {
					processList[i] = new Process((i + 1), sjf_test3_arrival[i], sjf_test3_burst[i], (random.nextInt(40) + 1));
				}
			}
		} else if(inputs[0] == "Shortest Remaining Time First") {
			if(inputs[1] == "Test #1") {
				processList = new Process[10];
				for(int i = 0; i < 10; i++) {
					processList[i] = new Process((i + 1), srtf_test1_arrival[i], srtf_test1_burst[i], (random.nextInt(40) + 1));
				}
			} else if(inputs[1] == "Test #2") {
				processList = new Process[15];
				for(int i = 0; i < 15; i++) {
					processList[i] = new Process((i + 1), srtf_test2_arrival[i], srtf_test2_burst[i], (random.nextInt(40) + 1));
				}
			} else if(inputs[1] == "Test #3") {
				processList = new Process[20];
				for(int i = 0; i < 20; i++) {
					processList[i] = new Process((i + 1), srtf_test3_arrival[i], srtf_test3_burst[i], (random.nextInt(40) + 1));
				}
			}
		} else if(inputs[0] == "Preemptive Priority Scheduling") {
			if(inputs[1] == "Test #1") {
				processList = new Process[10];
				for(int i = 0; i < 10; i++) {
					processList[i] = new Process((i + 1), prio_test1_arrival[i], prio_test1_burst[i], prio_test1_priority[i]);
				}
			} else if(inputs[1] == "Test #2") {
				processList = new Process[15];
				for(int i = 0; i < 15; i++) {
					processList[i] = new Process((i + 1), prio_test2_arrival[i], prio_test2_burst[i], prio_test2_priority[i]);
				}
			} else if(inputs[1] == "Test #3") {
				processList = new Process[20];
				for(int i = 0; i < 20; i++) {
					processList[i] = new Process((i + 1), prio_test3_arrival[i], prio_test3_burst[i], prio_test3_priority[i]);
				}
			}
		} else if(inputs[0] == "Non-preemptive Priority Scheduling") {
			if(inputs[1] == "Test #1") {
				processList = new Process[10];
				for(int i = 0; i < 10; i++) {
					processList[i] = new Process((i + 1), nprio_test1_arrival[i], nprio_test1_burst[i], nprio_test1_priority[i]);
				}
			} else if(inputs[1] == "Test #2") {
				processList = new Process[15];
				for(int i = 0; i < 15; i++) {
					processList[i] = new Process((i + 1), nprio_test2_arrival[i], nprio_test2_burst[i], nprio_test2_priority[i]);
				}
			} else if(inputs[1] == "Test #3") {
				processList = new Process[20];
				for(int i = 0; i < 20; i++) {
					processList[i] = new Process((i + 1), nprio_test3_arrival[i], nprio_test3_burst[i], nprio_test3_priority[i]);
				}
			}
		} else if(inputs[0] == "Round Robin") {
			if(inputs[1] == "Test #1") {
				processList = new Process[10];
				for(int i = 0; i < 10; i++) {
					processList[i] = new Process((i + 1), rr_test1_arrival[i], rr_test1_burst[i], (random.nextInt(40) + 1));
				}
			} else if(inputs[1] == "Test #2") {
				processList = new Process[15];
				for(int i = 0; i < 15; i++) {
					processList[i] = new Process((i + 1), rr_test2_arrival[i], rr_test2_burst[i], (random.nextInt(40) + 1));
				}
			} else if(inputs[1] == "Test #3") {
				processList = new Process[20];
				for(int i = 0; i < 20; i++) {
					processList[i] = new Process((i + 1), rr_test3_arrival[i], rr_test3_burst[i], (random.nextInt(40) + 1));
				}
			}
		}

		for(int i = 0; i < processList.length; i++) {
			table[0].append("   " + processList[i].getProcessID() + "\n");
			table[1].append("      " + processList[i].getArrivalTime() + "\n");
			table[2].append("    " + processList[i].getBurstTime() + "\n");
			table[3].append("      " + processList[i].getPriority() + "\n");
			// table[4].append("          " + processList[i].getHistoryInfo() + "\n");
		}

		return processList.length;
	}

	public Process[] getProcessList() {

		return processList;
	}

	public void clearTables() {

		for(int i = 0; i < 4; i++) {
			table[i].setText("");
		}
	}
}