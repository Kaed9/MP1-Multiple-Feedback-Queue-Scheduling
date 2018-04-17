import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class QueuesPanel extends JPanel {
	
	private JLabel label, q1, q2, q3;
	private JPanel panel, queuesPanel;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	
	private Font font = new Font("Verdana", Font.BOLD, 38);
	private Font font1 = new Font("Verdana", Font.BOLD, 30);
	
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
	
	public void queuesPart() {
		
		q1 = new JLabel("Q1", JLabel.CENTER);
		q1.setFont(font1);
		q1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.lightGray));
		q1.setBounds(20, 120, 80, 38);
		
		/*
		q2 = new JLabel("Q2", JLabel.CENTER);
		q2.setFont(font);
		q2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.lightGray));
		q2.setSize(80, 80);
		q2.setLocation(20, 200);
		
		q3 = new JLabel("Q3", JLabel.CENTER);
		q3.setFont(font);
		q3.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.lightGray));
		q3.setSize(80, 80);
		q3.setLocation(20, 280);
		*/
		
		add(q1);
		// add(q2);
		// add(q3);
	}
	
	public void queuesHolder() {
		
		textPane = new JTextPane();
		textPane.setFont(font1);
		textPane.setBorder(BorderFactory.createEmptyBorder());
		DefaultCaret caret = (DefaultCaret)textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		panel = new JPanel(new BorderLayout());

		panel.add(scrollPane, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		panel.setBounds(100, 120, 620, 260);

		add(panel);

		/*
		panel = new JPanel(new BorderLayout());
		// panel.setBackground(Color.RED);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		queuesPanel = new JPanel();
		panel.add((new JScrollPane(queuesPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS)), BorderLayout.CENTER);
		panel.setSize(620, 260);
		panel.setLocation(100, 120);
		
		add(panel);
		*/
	}
	
	public void addToQueue(String string, int i) {

		// textPane.setText("1    ");

		StyledDocument document = textPane.getStyledDocument();

		SimpleAttributeSet attrib = new SimpleAttributeSet();
		StyleConstants.setForeground(attrib, Color.WHITE);
		StyleConstants.setBackground(attrib, Color.RED);
		StyleConstants.setBold(attrib, true);

		SimpleAttributeSet attrib1 = new SimpleAttributeSet();
		StyleConstants.setForeground(attrib1, Color.YELLOW);
		StyleConstants.setBackground(attrib1, Color.MAGENTA);
		StyleConstants.setBold(attrib1, true);

		SimpleAttributeSet attrib2 = new SimpleAttributeSet();
		StyleConstants.setForeground(attrib2, Color.CYAN);
		StyleConstants.setBackground(attrib2, Color.BLACK);
		StyleConstants.setBold(attrib1, true);

		try {
			// document.insertString(document.getLength(), "Start of text.\n", attrib1);
			// document.insertString(document.getLength(), "\nEnd of text.", attrib);

			if(i % 3 == 0) {
				document.insertString(document.getLength(), string, null);
			} else if(i % 3 == 1) {
				document.insertString(document.getLength(), "=", null);
			} else if(i % 3 == 2) {
				document.insertString(document.getLength(), "_", null);
			}
		} catch(Exception ex) { }

	}
}