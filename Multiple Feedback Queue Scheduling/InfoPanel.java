import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class InfoPanel extends JPanel {
	
	private JLabel label, algo, out, pol, e1, e2;
	private JPanel panel, left, right, one, two;
	private JScrollPane scrollPane, fieldPane;
	private JTextArea textArea;
	private JTextField field1, field2;

	private JLabel[] header = new JLabel[2];

	// private String[] string = {"Classical Scheduling Algorithm", "Output"};
	
	private Font font = new Font("Verdana", Font.BOLD, 28);
	private Font font1 = new Font("Verdana", Font.BOLD, 15);
	private Font font2 = new Font("Verdana", Font.PLAIN, 15);
	
	public InfoPanel() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		label = new JLabel("ADDITIONAL INFORMATION", JLabel.CENTER);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		label.setFont(font);
		label.setBounds(20, 10, 610, 50);
		// label.setSize(700, 50);
		// label.setLocation(20, 10);
		
		add(label);

		panel = new JPanel(null);
		panel.setBounds(20, 70, 610, 200);
		panel.setBackground(Color.WHITE);

		algo = new JLabel("Classical Scheduling Algorithm", JLabel.CENTER);
		algo.setFont(font1);
		algo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(font2);
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		left = new JPanel(new BorderLayout());
		left.setBackground(Color.WHITE);
		left.add(algo, BorderLayout.NORTH);
		left.add(scrollPane, BorderLayout.CENTER);
		left.setBounds(0, 90, 610, 100);

		out = new JLabel("Output", JLabel.CENTER);
		out.setFont(font1);
		out.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		field1 = new JTextField();
		field1.setHorizontalAlignment(JTextField.CENTER);
		field1.setEditable(false);
		field1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		field1.setFont(font2);
		field1.setBackground(Color.WHITE);
		fieldPane = new JScrollPane(field1, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// fieldPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 15));
		fieldPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		fieldPane.setBackground(Color.WHITE);
		e1 = new JLabel(" ", JLabel.CENTER);
		one = new JPanel(new BorderLayout());
		one.setBackground(Color.WHITE);
		one.add(out, BorderLayout.NORTH);
		one.add(fieldPane, BorderLayout.CENTER);
		one.add(e1, BorderLayout.SOUTH);

		pol = new JLabel("Priority Policy", JLabel.CENTER);
		pol.setFont(font1);
		pol.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
		field2 = new JTextField();
		field2.setHorizontalAlignment(JTextField.CENTER);
		field2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		field2.setEditable(false);
		field2.setFont(font2);
		field2.setBackground(Color.WHITE);
		e2 = new JLabel(" ", JLabel.CENTER);
		two = new JPanel(new BorderLayout());
		two.setBackground(Color.WHITE);
		two.add(pol, BorderLayout.NORTH);
		two.add(field2, BorderLayout.CENTER);
		two.add(e2, BorderLayout.SOUTH);

		right = new JPanel(new GridLayout(1, 2));
		right.setBackground(Color.WHITE);
		right.setBounds(0, 0, 610, 80);
		right.add(one);
		right.add(two);

		panel.add(left);
		panel.add(right);
		add(panel);
	}

	public void additionalInfo(String string, String policy) {

		// scrollPane.setBorder(BorderFactory.createEmptyBorder());

		int i = 1;
		for(String temp : string.split("\n")) {
			textArea.setText("Q" + i + "    " + temp + "\n");
			i++;
		}

		field2.setText(policy);

	}

	public void addToField(Queue queue, int length) {

		String combine = "  ";
		while(queue.getIndex() > 0) {
			combine += "P" + queue.dequeue().getProcessID() + " ";
		}
		field1.setText(combine);
	}

	public void clearInfoPanel() {

		textArea.setText("");
		field1.setText("");
		field2.setText("");
	}
}