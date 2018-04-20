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
	private JPanel[][] panelArr;
	private JLabel[][] labelArr;
	
	private JLabel[] queueLabel;
	private JPanel[] quPane;

	private Font font = new Font("Verdana", Font.BOLD, 38);
	private Font font1 = new Font("Verdana", Font.BOLD, 30);
	private Font font2 = new Font("Verdana", Font.PLAIN, 20);

	// private AdjustmentListener listener = new MyAdjustmentListener();
	private int value = 0, y = 120;
	private Random rand = new Random();
	
	public QueuesPanel() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		// setBackground(Color.lightGray);
		
		label = new JLabel("QUEUES", JLabel.CENTER);
		label.setFont(font);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		label.setSize(700, 70);
		label.setLocation(20, 20);
		
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
			quPane[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));

			quHolder.add(quPane[i]);
		}

		scrollPane = new JScrollPane(quHolder, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setBounds(100, 120, 620, 70);

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
	
	public void addToQueue(Process[] temp) {

		int maxBurstTime = 0;
		int index = 0;
		for(int i = 0; i < temp.length; i++) {
			maxBurstTime += temp[i].getBurstTime();
		}

		// bar.addAdjustmentListener(listener);
		panelArr = new JPanel[temp.length][];
		labelArr = new JLabel[temp.length][];

		new Thread() {
			public void run() {
				Color color = null;
				try {
					for(int i = 0; i < temp.length; i++) {
						panelArr[i] = new JPanel[temp[i].getBurstTime()];
						labelArr[i] = new JLabel[temp[i].getBurstTime()];
						color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
						for(int j = 0; j < temp[i].getBurstTime(); j++) {
							repaint();
							revalidate();
							labelArr[i][j] = new JLabel("P" + temp[i].getProcessID());
							labelArr[i][j].setOpaque(true);
							labelArr[i][j].setBackground(color);
							labelArr[i][j].setFont(font2);
							labelArr[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

							panelArr[i][j] = new JPanel();
							panelArr[i][j].add(labelArr[i][j]);

							quPane[index].add(panelArr[i][j]);

							Thread.sleep(100);
						}

					}
				} catch(InterruptedException iEx) { }
			}
		}.start();

		// bar.removeAdjustmentListener(listener);
	}

	public void clearQueuesPanel() {

		removeAll();
		repaint();
		revalidate();

		label = new JLabel("QUEUES", JLabel.CENTER);
		label.setFont(font);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		label.setSize(700, 70);
		label.setLocation(20, 20);
		
		add(label);
	}
}