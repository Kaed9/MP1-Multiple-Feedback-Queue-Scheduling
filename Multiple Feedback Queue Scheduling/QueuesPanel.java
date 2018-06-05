import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.Random;
// import javax.swing.text.DefaultCaret;
// import javax.swing.text.AttributeSet;
// import javax.swing.text.SimpleAttributeSet;
// import javax.swing.text.StyleConstants;
// import javax.swing.text.StyleContext;
// import javax.swing.text.StyledDocument;

public class QueuesPanel extends JPanel {
	
	private JLabel label/*, q1, q2, q3*/;
	private JPanel panel/*, queuesPanel, quPane1*/;
	// private JTextPane textPane;
	private JScrollPane scrollPane;
	// private JScrollBar bar;
	private JPanel quHolder;

	// private JPanel[] panelArr = new JPanel[100];
	// private JLabel[] labelArr = new JLabel[100];
	private JPanel[] panelArr;
	private JLabel[] labelArr, time;
	
	private JLabel[] queueLabel;
	private JPanel[] quPane;

	private Font font = new Font("Verdana", Font.BOLD, 32);
	private Font font1 = new Font("Verdana", Font.BOLD, 30);
	private Font font2 = new Font("Verdana", Font.PLAIN, 20);

	// private AdjustmentListener listener = new MyAdjustmentListener();
	private int value = 0, y = 80;
	private Random rand = new Random();
	
	public QueuesPanel() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		// setBackground(Color.lightGray);
		
		label = new JLabel("GANTT CHART", JLabel.CENTER);
		label.setFont(font);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		label.setBounds(20, 20, 700, 45);
		// label.setSize(700, 70);
		// label.setLocation(20, 20);
		
		add(label);

		// queuesPart();
		// queuesHolder();
		// addToQueue();
	}
	
	// public void queuesPart() {
		
	// 	q1 = new JLabel("Q1", JLabel.CENTER);
	// 	q1.setFont(font1);
	// 	q1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.lightGray));
	// 	q1.setBounds(20, 120, 80, 38);
		
		/*
		q2 = new JLabel("Q2", JLabel.CENTER);
		q2.setFont(font);
		q2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.lightGray));
		q2.setSize(80, 80);
		q2.setLocation(20, 200);
		
		q3 = new JLabel("Q3", JLabel.CENTER);
		q3.setFont(font);y
		q3.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.lightGray));
		q3.setSize(80, 80);
		q3.setLocation(20, 280);
		*/
		
		// add(q1);
		// add(q2);
		// add(q3);
	// }
	
	public void queuesHolder(String inputs1) {
		
		// System.out.println(inputs1[0] + " " + inputs1[1] + " " + inputs1[2]);
		value = Integer.parseInt(inputs1);
		queueLabel = new JLabel[value];

		for(int i = 0; i < value; i++) {
			String st = "Q" + (i + 1);
			queueLabel[i] = new JLabel(st, JLabel.CENTER);
			queueLabel[i].setFont(font1);
			queueLabel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.lightGray));
			queueLabel[i].setBounds(20, y, 80, 38);
			add(queueLabel[i]);
		}

		quPane = new JPanel[value];
		quHolder = new JPanel(new GridLayout(value, 1));

		for(int i = 0; i < value; i++) {
			// quPane[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			quPane[i] = new JPanel(null);

			quHolder.add(quPane[i]);
		}

		scrollPane = new JScrollPane(quHolder, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setBounds(100, 80, 620, 250);

		panel.add(scrollPane, BorderLayout.CENTER);
		add(panel);

		repaint();
		revalidate();
		
		/*
		quPane1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		scrollPane = new JScrollPane(quPane1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		panel = new JPanel(new BorderLayout());
		// panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setBounds(100, 120, 620, 70);

		panel.add(scrollPane, BorderLayout.CENTER);
		add(panel);

		bar = scrollPane.getHorizontalScrollBar();
		// bar.addAdjustmentListener(listener);
		*/
	}

	/*private class MyAdjustmentListener implements AdjustmentListener {
		
		public void adjustmentValueChanged(AdjustmentEvent e) {
			
			if(e.getSource() == bar && e.getAdjustmentType() == AdjustmentEvent.TRACK) {
				int extent = scrollPane.getHorizontalScrollBar().getModel().getExtent();
				// scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + extent);
				// System.out.print(scrollPane.getHorizontalScrollBar().getWidth() + " ");
			}
		}
	}*/
	
	public void addToQueue(Queue printable, int globalMaxProcesses, SummaryPanel summaryPanel, String algo) {

		// Queue printable = printQueue;
		Process after = null, before = null;
		Queue valid = new Queue();
		Queue minimized = new Queue();
		int checker = printable.getIndex(), arrival = 0, interval = 0;
		int combi1 = 0;

		while(printable.getIndex() > 0) {
			after = printable.dequeue();
			if(printable.getIndex() == checker - 1) {
				before = after;

				arrival = after.getArrivalTime();
				if(arrival > 0) {
					if(minimized.getIndex() == 0)
						minimized.initialProcess(new Process(0, 0, 0, 0));
					else
						minimized.enqueue(new Process(0, 0, 0, 0));
					while(arrival > 0) {
						if(valid.getIndex() == 0)
							valid.initialProcess(new Process(0, 0, 0, 0));
						else
							valid.enqueue(new Process(0, 0, 0, 0));

						arrival--;
						combi1++;
					}
				}
			}

			if(after.getProcessID() != before.getProcessID() && before.getBurstTime() == 0) {
				if(after.getArrivalTime() > combi1) {
					interval = after.getArrivalTime() - combi1;
					minimized.enqueue(new Process(0, 0, 0, 0));
					for(int i = 0; i < interval; i++) {
						valid.enqueue(new Process(0, 0, 0, 0));
						combi1++;
					}
				} else {
					valid.enqueue(after);
					minimized.enqueue(after);
					combi1++;
				}
				// System.out.print("|");
			} else {
				if(valid.getIndex() == 0)
					valid.initialProcess(after);
				else
					valid.enqueue(after);

				if(after.getProcessID() != before.getProcessID()) {
					if(minimized.getIndex() == 0)
						minimized.initialProcess(before);
					else
						minimized.enqueue(before);
				}

				if(printable.getIndex() == 0)
					minimized.enqueue(after);

				combi1++;
			}

			before = after;
		}

		// System.out.println(valid.getIndex() + " " + printable.getIndex() + "\n");
		// System.out.print("\n|");
		// while(combi1 > 0) {
		// 	System.out.print(" P" + valid.dequeue().getProcessID() + " |");
		// 	combi1--;
		// }

		// System.out.println("\n");
		// while(minimized.getIndex() > 0) {
		// 	System.out.print("P" + minimized.dequeue().getProcessID() + " ");
		// }

		// int maxBurstTime = minimized.getIndex();
		int maxBurstTime = valid.getIndex();
		int index = 0;
		int x1 = maxBurstTime * 40;
		final int combi = combi1;
		quHolder.setPreferredSize(new Dimension(x1, 80));
		
		Color[] color = new Color[globalMaxProcesses + 1];
		color[0] = new Color(255, 255, 255);
		for(int i = 1; i < globalMaxProcesses + 1; i++) {
			color[i] = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		}

		int[] firstExecution = new int[globalMaxProcesses];
		int[] completion = new int[globalMaxProcesses];
		int[] burstChecker = new int[globalMaxProcesses];
		int[] arrivalChecker = new int[globalMaxProcesses];
		for(int i = 0; i < globalMaxProcesses; i++) {
			firstExecution[i] = 0;
			completion[i] = 0;
			burstChecker[i] = 0;
			arrivalChecker[i] = 0;
		}

		panelArr = new JPanel[maxBurstTime];
		labelArr = new JLabel[maxBurstTime];
		time = new JLabel[maxBurstTime];

		new Thread() {
			public void run() {
				int x = 0, maxi = 0;
				Process assign = null, min = minimized.dequeue();
				try {
					for(int i = 0; i < combi; i++) {
						assign = valid.dequeue();
						// System.out.print("P" + assign.getProcessID() + "|" + maxi + " ");
						repaint();
						revalidate();

						panelArr[i] = new JPanel(null);

						if(assign.getProcessID() == 0)
							labelArr[i] = new JLabel(" ", JLabel.CENTER);
						else {
							labelArr[i] = new JLabel("P" + assign.getProcessID(), JLabel.CENTER);

							if(firstExecution[assign.getProcessID() - 1] == 0) {
								if(assign.getArrivalTime() == 0)
									firstExecution[assign.getProcessID() - 1] = 0;
								else //{
									firstExecution[assign.getProcessID() - 1] = maxi - 1;
									// System.out.print(maxi + " ");
								// }
								arrivalChecker[assign.getProcessID() - 1] = assign.getArrivalTime();
							}

							burstChecker[assign.getProcessID() - 1] += 1;
							if(burstChecker[assign.getProcessID() - 1] == assign.getBurstTime())
								completion[assign.getProcessID() - 1] = maxi;
						}

						if(maxi == 0) {
							maxi++;
							time[i] = new JLabel("0         " + maxi, JLabel.LEFT);
						} else
							time[i] = new JLabel("" + maxi, JLabel.RIGHT);

						labelArr[i].setOpaque(true);
						labelArr[i].setBackground(color[assign.getProcessID()]);
						labelArr[i].setFont(font2);
						labelArr[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
						labelArr[i].setBounds(0, 0, 40, 38);
						time[i].setBounds(0, 40, 40, 38);

						panelArr[i].add(labelArr[i]);
						panelArr[i].add(time[i]);
						panelArr[i].setBounds(x, 0, 40, 76);
						x += 40;

						quPane[index].add(panelArr[i]);

						Thread.sleep(50);
						maxi++;
					}
					summaryPanel.addToTime(globalMaxProcesses, firstExecution, completion, burstChecker, arrivalChecker, algo);
				} catch(InterruptedException iEx) { }
			}
		}.start();
	}

	public void clearQueuesPanel() {

		removeAll();
		repaint();
		revalidate();

		label = new JLabel("GANTT CHART", JLabel.CENTER);
		label.setFont(font);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		label.setBounds(20, 20, 700, 45);
		
		add(label);
	}
}