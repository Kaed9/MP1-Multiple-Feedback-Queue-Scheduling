import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class InfoPanel extends JPanel {
	
	private JLabel label;
	
	private Font font = new Font("Verdana", Font.BOLD, 28);
	
	public InfoPanel() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		label = new JLabel("ADDITIONAL INFORMATION", JLabel.CENTER);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		label.setFont(font);
		label.setSize(700, 50);
		label.setLocation(20, 10);
		
		add(label);
	}
}