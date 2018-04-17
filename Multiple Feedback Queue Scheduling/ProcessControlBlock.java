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
	
	private String[] string = {"<html><div style = 'text-align: center;'>PID</div></html>", 
							   "<html><div style = 'text-align: center;'>Arrival<br/>Time</div></html>",
							   "<html><div style = 'text-align: center;'>CPU<br/>Burst<br/>Time</div></html>",
							   "<html><div style = 'text-align: center;'>Priority</div></html>",
							   "<html><div style = 'text-align: center;'>Execution<br/>History<br/>Information</div></html>", ""};
	private int count = 0;
	private int[] sizes = {60, 92, 72, 92, 124, 20};
	
	public AdjustmentListener listener = new MyAdjustmentListener();
	
	private Font font = new Font("Verdana", Font.BOLD, 28);
	private Font font1 = new Font("Verdana", Font.BOLD, 15);
	private Font font2 = new Font("Verdana", Font.PLAIN, 15);

	private Random random = new Random();

	private Process[] processList;
	
	public ProcessControlBlock() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		label = new JLabel("PROCESS CONTROL BLOCK", JLabel.CENTER);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		label.setFont(font);
		label.setBounds(20, 20, 460, 70);
		// label.setSize(460, 70);
		// label.setLocation(20, 20);
		
		processPanel = new JPanel();
		processPanel.setLayout(null);
		// processPanel.setBackground(Color.RED);
		processPanel.setBounds(20, 110, 460, 540);
		// processPanel.setSize(460, 540);
		// processPanel.setLocation(20, 110);
		processPane();
		
		add(label);
		add(processPanel);
	}
	
	public void processPane() {
		
		panel = new JPanel[6];
		table = new JTextArea[6];
		scrollPane = new JScrollPane[6];
		
		for(int i = 0; i < 6; i++) {
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
			
			if(i != 5) {
				scrollPane[i] = new JScrollPane(table[i], JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				// panel[i].add(scrollPane[i], BorderLayout.CENTER);
			} else {
				scrollPane[i] = new JScrollPane(table[i]);
				// panel[i].add(scrollPane[i], BorderLayout.CENTER);
				bar = scrollPane[i].getVerticalScrollBar();
				bar.addAdjustmentListener(listener);

			}
			DefaultCaret caret = (DefaultCaret)table[i].getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

			scrollPane[i].setBounds(0, 70, sizes[i], 470);
			// scrollPane[i].setSize(sizes[i], 470);
			// scrollPane[i].setLocation(0, 70);
			scrollPane[i].setBorder(BorderFactory.createEmptyBorder());
			panel[i].add(scrollPane[i]);
			panel[i].setBackground(Color.WHITE);
			if(i > 0 && i < 5) {
				panel[i].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
				scrollPane[i].setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			} else if(i == 5) {
				panel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
				scrollPane[i].setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
			} else if (i == 0) {
				panel[i].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
				scrollPane[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			}
			panel[i].setBounds(count, 0, sizes[i], 540);
			// panel[i].setSize(sizes[i], 540);
			// panel[i].setLocation(count, 0);
			processPanel.add(panel[i]);
			count += sizes[i];
		}
		
	}
	
	private class MyAdjustmentListener implements AdjustmentListener {
		
		public void adjustmentValueChanged(AdjustmentEvent e) {
			
			if(e.getSource() == bar && e.getAdjustmentType() == AdjustmentEvent.TRACK) {
				for(int i = 0; i < 5; i++) {
					scrollPane[i].getVerticalScrollBar().setValue(e.getValue());
				}
			}
		}
	}

	public void generateProcesses(int maxProcesses, String checker, String s1, String s2, String s3) {

		if(checker.equals("randomized")) {
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
				table[0].append("   " + processList[i].getProcessID() + "\n");
				table[1].append("      " + processList[i].getArrivalTime() + "\n");
				table[2].append("    " + processList[i].getBurstTime() + "\n");
				table[3].append("      " + processList[i].getPriority() + "\n");
				table[4].append("          " + processList[i].getHistoryInfo() + "\n");
			}
		} else if(checker.equals("user-defined")) {
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
				table[0].append("   " + processList[i].getProcessID() + "\n");
				table[1].append("      " + processList[i].getArrivalTime() + "\n");
				table[2].append("    " + processList[i].getBurstTime() + "\n");
				table[3].append("      " + processList[i].getPriority() + "\n");
				table[4].append("          " + processList[i].getHistoryInfo() + "\n");
			}
		}
	}

	public Process[] getProcessList() {

		return processList;
	}

	public void clearTables() {

		for(int i = 0; i < 5; i++) {
			table[i].setText("");
		}
	}
}