import java.awt.*;
// import java.io.IOException;
// import java.net.MalformedURLException;
// import java.net.URL;
import javax.swing.*;
// import javax.imageio.ImageIO;
import java.awt.event.*;

public class JOptionPaneExample {

    private static JPanel panel, top, first, second, third;
    private static JLabel choose_num, rand_or_def;
    private static JTextField field;
    private static JButton button;
    private static JRadioButton random, defined;
    private static ButtonGroup group;
    private static JPanel[] pane = new JPanel[4];
    private static JTextArea[] area = new JTextArea[4];
    private static JLabel[] label = new JLabel[4];

    private static String[] inputs = new String[5];
    private static String[] strings = {"PID", "Arrival Time", "CPU Burst Time", "Priority"};

    public static String[] displayGUI() {

        Object[] values = {"Submit", "Cancel"};
        UIManager.put("OptionPane.minimumSize", new Dimension(500, 550)); 
        int value = JOptionPane.showOptionDialog(null, getPanel(), "JOptionPane Example : ", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, values, values[0]);
        if(value == 0) {
            if(inputs[1] == "user-defined") {
                inputs[2] = area[1].getText();
                inputs[3] = area[2].getText();
                inputs[4] = area[3].getText();
            } else if(inputs[1] == "randomized") {
                inputs[2] = " ";
                inputs[3] = " ";
                inputs[4] = " ";
            }
        }

        return inputs;
    }

    private static JPanel getPanel() {

        panel = new JPanel(new BorderLayout());
        /*JLabel label = new JLabel("Java Technology Dive Log");
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(
                    new URL("http://i.imgur.com/6mbHZRU.png")));
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } */

        first = new JPanel();
        choose_num = new JLabel("Enter number of processes to generate (1 - 20):");
        field = new JTextField(4);
        button = new JButton("OK");

        second = new JPanel();
        rand_or_def = new JLabel("Choose process generation mode:");
        random = new JRadioButton("Randomized");
        random.setActionCommand("randomized");
        random.setEnabled(false);
        defined = new JRadioButton("User-defined");
        defined.setActionCommand("user-defined");
        defined.setEnabled(false);

        group = new ButtonGroup();
        group.add(random);
        group.add(defined); 

        third = new JPanel();

        first.add(choose_num);
        first.add(field);
        first.add(button);
        second.add(rand_or_def);
        second.add(random);
        second.add(defined);

        top = new JPanel(new BorderLayout());

        top.add(first, BorderLayout.NORTH);
        top.add(second, BorderLayout.SOUTH);
        panel.add(top, BorderLayout.NORTH);
        panel.add(third, BorderLayout.CENTER);
        // label.setIcon(image);
        // panel.add(label);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String string = field.getText();

                try {
                    int num = Integer.parseInt(string);

                    if(num >= 0 || num <= 20) {
                        button.setEnabled(false);
                        field.setEnabled(false);
                        random.setEnabled(true);
                        defined.setEnabled(true);
                        // System.out.println(field.getText());
                        inputs[0] = field.getText();
                    } else {
                        System.out.println("Select a number between 0 to 20!");
                    }
                } catch(NumberFormatException nfEx) {
                    System.out.println("Input not a number!");
                    // JOptionPane.showMessageDialog(null, "Input not an number!", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
                }
                // if(string )
            }
        });

        random.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println(e.getActionCommand());
                inputs[1] = e.getActionCommand();
                random.setEnabled(false);
                defined.setEnabled(false);
            }
        });

        defined.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println(e.getActionCommand());
                inputs[1] = e.getActionCommand();
                random.setEnabled(false);
                defined.setEnabled(false);

                int max = Integer.parseInt(field.getText());
                String temp = "";

                for(int i = 0; i < 4; i++) {
                    pane[i] = new JPanel(new BorderLayout());

                    label[i] = new JLabel(strings[i]);
                    area[i] = new JTextArea(max, 5);

                    pane[i].add(label[i], BorderLayout.NORTH);
                    pane[i].add(area[i], BorderLayout.CENTER);

                    third.add(pane[i]);
                }
                area[0].setEditable(false);

                for(int i = 0; i < max; i++) {
                    if(i != max - 1)
                        temp += "" + (i + 1) + "\n";
                    else
                        temp += "" + (i + 1);
                    area[0].setText(temp);
                }
            }
        });

        return panel;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JOptionPaneExample().displayGUI();
            }
        });
    }
}