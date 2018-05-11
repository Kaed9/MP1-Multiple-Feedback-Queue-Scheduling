import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class InfoPanel extends JPanel {
	
	private JLabel label, algo, out, pol, e1, e2;
	private JPanel panel, left, right, one, two;
	private JScrollPane scrollPane/*, fieldPane*/;
	private JTextArea textArea;
	private JTextField field1, field2;

	private JLabel[] header = new JLabel[2];

	// private String[] string = {"Classical Scheduling Algorithm", "Output"};
	
	private Font font = new Font("Verdana", Font.BOLD, 28);
	
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
		panel.setBounds(20, 70, 610, 140);
		panel.setBackground(Color.WHITE);

		algo = new JLabel("Classical Scheduling Algorithm", JLabel.CENTER);
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		left = new JPanel(new BorderLayout());
		left.add(algo, BorderLayout.NORTH);
		left.add(scrollPane, BorderLayout.CENTER);
		left.setBounds(0, 0, 300, 140);

		out = new JLabel("Output", JLabel.CENTER);
		field1 = new JTextField();
		field1.setHorizontalAlignment(JTextField.CENTER);
		field1.setEditable(false);
		// fieldPane = new JScrollPane(field1, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		e1 = new JLabel(" ", JLabel.CENTER);
		one = new JPanel(new BorderLayout());
		one.add(out, BorderLayout.NORTH);
		one.add(field1, BorderLayout.CENTER);
		one.add(e1, BorderLayout.SOUTH);

		pol = new JLabel("Priority Policy", JLabel.CENTER);
		field2 = new JTextField();
		field2.setHorizontalAlignment(JTextField.CENTER);
		field2.setEditable(false);
		e2 = new JLabel(" ", JLabel.CENTER);
		two = new JPanel(new BorderLayout());
		two.add(pol, BorderLayout.NORTH);
		two.add(field2, BorderLayout.CENTER);
		two.add(e2, BorderLayout.SOUTH);

		right = new JPanel(new GridLayout(2, 1));
		right.setBounds(320, 0, 290, 140);
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

	public void addToField(String[] string, int length) {

		String combine = "";
		for(int i = 0; i < length; i++) {
			combine += "P" + string[i] + " ";
		}
		field1.setText(combine);
	}

	public void clearInfoPanel() {

		textArea.setText("");
		field1.setText("");
		field2.setText("");
	}
}