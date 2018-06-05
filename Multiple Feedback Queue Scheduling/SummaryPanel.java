import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class SummaryPanel extends JPanel {

	private JLabel title;
	private JPanel summaryPanel;
	private JPanel[] panel, avePanel;
	private JTextArea[] area;
	private JTextField[] aveField;
	private JScrollPane[] pane, avePane;
	private JScrollBar bar;

	private AdjustmentListener listener = new MyAdjustmentListener();

	private String[] string = {"<html><div style = 'text-align: center;'>PID</div></html>", 
							   "<html><div style = 'text-align: center;'>Response Time</div></html>",
							   "<html><div style = 'text-align: center;'>Turnaround Time</div></html>",
							   "<html><div style = 'text-align: center;'>Waiting Time</div></html>", ""};
	private String average = "<html><div style = 'text-align: center;'>AVE</div></html>";
	private int[] sizes = {60, 160, 160, 150, 20};
	private int[] aveSizes = {60, 160, 160, 151};

	private Font font = new Font("Verdana", Font.BOLD, 28);
	private Font font1 = new Font("Verdana", Font.BOLD, 15);
	private Font font2 = new Font("Verdana", Font.PLAIN, 15);

	public SummaryPanel() {

		setLayout(null);
		setBackground(Color.WHITE);

		title = new JLabel("TITLE", JLabel.CENTER);
		title.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		title.setFont(font);
		title.setBounds(20, 10, 550, 50);

		add(title);

		setSummaryGUI();
	}

	public void setSummaryGUI() {

		summaryPanel = new JPanel();
		summaryPanel.setLayout(null);
		summaryPanel.setBounds(20, 70, 550, 190);
		summaryPanel.setBackground(Color.WHITE);

		panel = new JPanel[5];
		area = new JTextArea[5];
		pane = new JScrollPane[5];

		int count = 0;
		for(int i = 0; i < 5; i++) {
			JLabel label = new JLabel(string[i], SwingConstants.CENTER);
			label.setFont(font1);
			label.setBounds(0, 0, sizes[i], 30);
			if(i != 4)
				label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.BLACK));
			else
				label.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));

			panel[i] = new JPanel(null);
			panel[i].add(label);

			area[i] = new JTextArea(20, 2);
			area[i].setFont(font2);
			area[i].setEditable(false);
			area[i].setBorder(BorderFactory.createEmptyBorder());

			if(i != 4) {
				pane[i] = new JScrollPane(area[i], JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane. HORIZONTAL_SCROLLBAR_NEVER);
				pane[i].setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));
			} else {
				pane[i] = new JScrollPane(area[i], JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane. HORIZONTAL_SCROLLBAR_NEVER);
				pane[i].setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));
				bar = pane[i].getVerticalScrollBar();
				bar.addAdjustmentListener(listener);
			}

			pane[i].setBounds(0, 30, sizes[i], 135);

			panel[i].add(pane[i]);
			// panel[i].setBackground(Color.WHITE);
			panel[i].setBounds(count, 0, sizes[i], 165);
			panel[i].setBackground(Color.WHITE);

			summaryPanel.add(panel[i]);
			count += sizes[i];
		}

		avePanel = new JPanel[4];
		aveField = new JTextField[3];
		avePane = new JScrollPane[3];
		count = 0;

		for(int i = 0; i < 4; i++) {
			avePanel[i] = new JPanel(null);
			avePanel[i].setBounds(count, 165, aveSizes[i], 25);
			avePanel[i].setBackground(Color.WHITE);
			if(i == 0) {
				JLabel label = new JLabel(average, SwingConstants.CENTER);
				label.setFont(font1);
				label.setBounds(0, 0, aveSizes[i], 25);

				avePanel[i].add(label);
			} else {
				aveField[i - 1] = new JTextField(5);
				aveField[i - 1].setFont(font2);
				aveField[i - 1].setHorizontalAlignment(JTextField.CENTER);
				aveField[i - 1].setEditable(false);
				aveField[i - 1].setBackground(Color.WHITE);
				aveField[i - 1].setBorder(BorderFactory.createEmptyBorder());

				avePane[i - 1] = new JScrollPane(aveField[i - 1], JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane. HORIZONTAL_SCROLLBAR_NEVER);
				avePane[i - 1].setBounds(0, 0, aveSizes[i], 25);
				if(i != 3)
					avePane[i - 1].setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));
				else
					avePane[i - 1].setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));

				avePanel[i].add(avePane[i - 1]);
			}

			summaryPanel.add(avePanel[i]);
			count += aveSizes[i];
		}

		add(summaryPanel);
	}

	private class MyAdjustmentListener implements AdjustmentListener {
		
		public void adjustmentValueChanged(AdjustmentEvent e) {
			
			if(e.getSource() == bar && e.getAdjustmentType() == AdjustmentEvent.TRACK) {
				for(int i = 0; i < 4; i++) {
					pane[i].getVerticalScrollBar().setValue(e.getValue());
				}
			}
		}
	}

	public void addToTime(int maxProcesses, int[] firstExecution, int[] completion, int[] burstTime, int[] arrivalTime, String algo) {

		// int[] process = new int[maxProcesses];
		// int size = queue.getIndex();
		// Process sample = null;

		// while(queue.getIndex() > 0) {
		// 	sample = queue.dequeue();

		// }

		int[] rTime = responseTime(firstExecution, arrivalTime/*, algo*/);
		int[] tTime = turnaroundTime(completion, arrivalTime, algo);
		int[] wTime = waitingTime(tTime, burstTime);
		float[] aTime = averageTimes(rTime, tTime, wTime);

		String pIDs = "", f = "", c = "", w = "";
		for(int i = 0; i < maxProcesses; i++) {
			pIDs += "    " + (i + 1) + "\n";
			f += "           " + rTime[i] + "\n";
			c += "           " + tTime[i] + "\n";
			w += "           " + wTime[i] + "\n";
		}
		area[0].append(pIDs);
		area[1].append(f);
		area[2].append(c);
		area[3].append(w);

		aveField[0].setText("" + aTime[0]);
		aveField[1].setText("" + aTime[1]);
		aveField[2].setText("" + aTime[2]);
	}

	public int[] responseTime(int[] firstExecution, int[] arrivalTime/*, String algo*/) {

		// response time = first execution - arrival time
		int[] rTime = new int[firstExecution.length];
		// System.out.print(algo + "|");
		for(int i = 0; i < firstExecution.length; i++) {
			// if(algo.equals("Round Robin"))
				// rTime[i] = firstExecution[i];
			// else
				rTime[i] = firstExecution[i] - arrivalTime[i];
		}

		return rTime;
	}

	public int[] turnaroundTime(int[] completion, int[] arrivalTime, String algo) {

		// turnaround time = completion - arrival time || waitingTime + burst time
		int[] tTime = new int[completion.length];
		System.out.println();
		for(int i = 0; i < completion.length; i++) {
			// if(algo.equals("Round Robin"))
			// 	tTime[i] = completion[i];
			// else
				tTime[i] = completion[i] - arrivalTime[i];
		}

		return tTime;
	}

	public int[] waitingTime(int[] tTime, int[] burstTime) {

		// waiting time = turnaround time - burst time || first execution - arrival time
		int[] wTime = new int[tTime.length];
		for(int i = 0; i < tTime.length; i++) {
			// if(algo == "Round Robin")
				// wTime[i]
			wTime[i] = tTime[i] - burstTime[i];
		}

		return wTime;
	}

	public float[] averageTimes(int[] rTime, int[] tTime, int[] wTime) {

		float[] aTime = new float[3];
		for(int i = 0; i < rTime.length; i++) {
			aTime[0] += (float)rTime[i];
			aTime[1] += (float)tTime[i];
			aTime[2] += (float)wTime[i];
		}

		for(int i = 0; i < 3; i++) {
			aTime[i] /= rTime.length;
		}

		return aTime;
	}

	public void clearSummaryPanel() {

		area[0].setText("");
		area[1].setText("");
		area[2].setText("");
		area[3].setText("");

		aveField[0].setText("");
		aveField[1].setText("");
		aveField[2].setText("");
	}
}