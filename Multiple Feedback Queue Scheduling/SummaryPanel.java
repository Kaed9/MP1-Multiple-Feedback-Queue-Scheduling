import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class SummaryPanel extends JPanel {

	private JLabel title;

	private Font font = new Font("Verdana", Font.BOLD, 28);

	public SummaryPanel() {

		setLayout(null);
		setBackground(Color.WHITE);

		title = new JLabel("TEMP TITLE", JLabel.CENTER);
		title.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		title.setFont(font);
		title.setBounds(20, 10, 550, 50);

		add(title);
	}
}