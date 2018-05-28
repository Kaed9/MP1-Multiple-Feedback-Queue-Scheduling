import java.awt.*;
// import java.io.IOException;
// import java.net.MalformedURLException;
// import java.net.URL;
import javax.swing.*;
// import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.Random;

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

    private static JPanel panel1, top1, first1, second1, third1, comboHold;
    private static JLabel choose_queue, choose_policy, choose_algo;
    private static JTextField field1;
    private static JButton button1;
    private static JRadioButton higher_lower, fixed;
    private static ButtonGroup group1;
    private static JComboBox[] comboBox;
    private static JLabel[] boxName;
    private static JPanel[] boxHold;

    private static JPanel panel2, top2, mid, right;
    private static JLabel testAlgoLabel, testNumLabel;
    private static JComboBox algoBox;
    private static JRadioButton[] tests = new JRadioButton[3];
    private static ButtonGroup group2;

    private static JPanel[] quantumPanel;
    private static JTextField[] quantumField;

    private static JLabel qLabel;
    private static JTextField qField;
    private static JPanel sPanel;

    private static String[] inputs = new String[5];
    private static String[] inputs1 = new String[4];
    private static String[] inputs2 = new String[3];
    private static String[] strings = {"PID", "Arrival Time", "CPU Burst Time", "Priority"};
    private static Object[] values = {"Submit", "Cancel"};
    private static String[] algorithms = {"First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Preemptive Priority Scheduling", "Non-preemptive Priority Scheduling", "Round Robin"};
    private static String[] testsNum = {"Test #1", "Test #2", "Test #3"};
    private static int maxQueue;

    private static Random rand = new Random();

    public static String[] displayGUI() {

        // UIManager.put("JOptionPane.minimumSize", new Dimension(500, 550)); 
        int value = JOptionPane.showOptionDialog(null, getPanel(), "Generate Processes", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, values, values[0]);
        if(value == 0) {
            // if(inputs[1] == "user-defined") {
            inputs[2] = area[1].getText();
            inputs[3] = area[2].getText();
            inputs[4] = area[3].getText();
            /*} else if(inputs[1] == "randomized") {
                inputs[2] = " ";
                inputs[3] = " ";
                inputs[4] = " ";
            }*/
        }

        return inputs;
    }

    private static JPanel getPanel() {

        panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(450, 450));
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

                    if(num >= 0 && num <= 20) {
                        button.setEnabled(false);
                        field.setEnabled(false);
                        random.setEnabled(true);
                        defined.setEnabled(true);
                        // System.out.println(field.getText());
                        inputs[0] = field.getText();
                    } else {
                        // System.out.println("Select a number between 0 to 20!");
                        JOptionPane.showMessageDialog(null, "Select a number between 0 to 20!", "Number Boundary Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch(NumberFormatException nfEx) {
                    // System.out.println("Input not a number!");
                    JOptionPane.showMessageDialog(null, "Input not an number!", "Number Format Exception", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        random.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println(e.getActionCommand());
                inputs[1] = e.getActionCommand();
                random.setEnabled(false);
                defined.setEnabled(false);

                int max = Integer.parseInt(field.getText());
                String temp = "", temp1 = "", temp2 = "", temp3 = "";

                for(int i = 0; i < 4; i++) {
                    pane[i] = new JPanel(new BorderLayout());

                    label[i] = new JLabel(strings[i]);
                    area[i] = new JTextArea(max, 5);

                    pane[i].add(label[i], BorderLayout.NORTH);
                    pane[i].add(area[i], BorderLayout.CENTER);

                    third.add(pane[i]);
                }
                area[0].setEditable(false);
                area[0].setBackground(Color.lightGray);

                for(int i = 0; i < max; i++) {
                    if(i != max - 1)
                        temp += "" + (i + 1) + "\n";
                    else
                        temp += "" + (i + 1);
                    area[0].setText(temp);
                }

                for(int i = 0; i < max; i++) {
                    if(i != max - 1) {
                        temp1 += "" + rand.nextInt(50) + "\n";
                        temp2 += "" + (rand.nextInt(50) + 1) + "\n";
                        temp3 += "" + (rand.nextInt(40) + 1) + "\n";
                    } else {
                        temp1 += "" + rand.nextInt(50);
                        temp2 += "" + (rand.nextInt(50) + 1);
                        temp3 += "" + (rand.nextInt(40) + 1);
                    }
                    area[1].setText(temp1);
                    area[2].setText(temp2);
                    area[3].setText(temp3);
                }
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
                area[0].setBackground(Color.lightGray);

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

    public static String[] displayGUI1() {

        // UIManager.put("JOptionPane.minimumSize", new Dimension(600, 300));
        int value = JOptionPane.showOptionDialog(null, getPanel1(), "Implement MLFQ", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, values, values[0]);
        if(value == 0) {
            inputs1[0] = field1.getText();
            inputs1[2] = "";
            inputs1[3] = "";
            for(int i = 0; i < maxQueue; i++) {
                inputs1[2] += String.valueOf(comboBox[i].getSelectedItem()) + "\n";
                inputs1[3] += quantumField[i].getText() + "\n";
                // System.out.println(String.valueOf(comboBox[i].getSelectedItem()));
            }

            // System.out.println(inputs1[0]);
            // System.out.println(inputs1[1]);
            // System.out.println(inputs1[2]);
        }

        return inputs1;
    }

    private static JPanel getPanel1() {

        panel1 = new JPanel(new BorderLayout());
        panel1.setPreferredSize(new Dimension(450, 300));

        choose_queue = new JLabel("Enter number of queues: ");
        field1 = new JTextField(4);
        button1 = new JButton("OK");
        //
        field1.setText("1");
        field1.setEditable(false);
        //
        first1 = new JPanel();
        first1.add(choose_queue);
        first1.add(field1);
        first1.add(button1);

        choose_policy = new JLabel("Choose priority policy: ");
        higher_lower = new JRadioButton("Higher before lower");
        higher_lower.setActionCommand("Higher before lower");
        higher_lower.setEnabled(false);
        fixed = new JRadioButton("Fixed time slots");
        fixed.setActionCommand("Fixed time slots");
        fixed.setEnabled(false);

        group1 = new ButtonGroup();
        group1.add(higher_lower);
        group1.add(fixed); 

        second1 = new JPanel();
        second1.add(choose_policy);
        second1.add(higher_lower);
        second1.add(fixed);

        comboHold = new JPanel();

        choose_algo = new JLabel("Choose classical scheduling algorithm for each queue:");

        third1 = new JPanel(new BorderLayout());
        third1.add(choose_algo, BorderLayout.NORTH);
        third1.add(comboHold, BorderLayout.CENTER);

        top1 = new JPanel(new BorderLayout());
        top1.add(first1, BorderLayout.NORTH);
        top1.add(second1, BorderLayout.SOUTH);

        panel1.add(top1, BorderLayout.NORTH);
        panel1.add(third1, BorderLayout.CENTER);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String string = field1.getText();

                try {
                    int num = Integer.parseInt(string);
                    button1.setEnabled(false);
                    // field1.setEnabled(false);
                    higher_lower.setEnabled(true);
                    fixed.setEnabled(true);
                } catch(NumberFormatException nfEx) {
                    // System.out.println("Input not a number!");
                    JOptionPane.showMessageDialog(null, "Input not an number!", "Number Format Exception", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        higher_lower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                higher_lower.setEnabled(false);
                fixed.setEnabled(false);

                inputs1[1] = e.getActionCommand();
                int maximum = Integer.parseInt(field1.getText());
                maxQueue = maximum;
                boxName = new JLabel[maximum];
                comboBox = new JComboBox[maximum];
                boxHold = new JPanel[maximum];
                quantumField = new JTextField[maximum];
                quantumPanel = new JPanel[maximum];
                for(int i = 0; i < maximum; i++) {
                    boxName[i] = new JLabel("Q" + (i + 1) + "\t");
                    comboBox[i] = new JComboBox(algorithms);
                    comboBox[i].setSelectedIndex(0);

                    quantumField[i] = new JTextField(2);
                    JLabel quantumLabel = new JLabel("Quantum Time\t", JLabel.RIGHT);
                    quantumPanel[i] = new JPanel(new BorderLayout());
                    quantumPanel[i].add(quantumLabel, BorderLayout.WEST);
                    quantumPanel[i].add(quantumField[i], BorderLayout.CENTER);

                    boxHold[i] = new JPanel(new BorderLayout());
                    boxHold[i].add(boxName[i], BorderLayout.WEST);
                    boxHold[i].add(comboBox[i], BorderLayout.CENTER);
                    boxHold[i].add(quantumPanel[i], BorderLayout.EAST);
                    comboHold.add(boxHold[i]);
                    comboHold.repaint();
                    comboHold.revalidate();
                }

                for(int i = 0; i < maximum; i++) {
                    comboBox[i].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JComboBox cb = (JComboBox)e.getSource();
                            String name = (String)cb.getSelectedItem();
                            // System.out.println(name);
                        }
                    });
                }
            }
        });

        fixed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                higher_lower.setEnabled(false);
                fixed.setEnabled(false);

                inputs1[1] = e.getActionCommand();
                int maximum = Integer.parseInt(field1.getText());
                maxQueue = maximum;
                boxName = new JLabel[maximum];
                comboBox = new JComboBox[maximum];
                boxHold = new JPanel[maximum];
                quantumField = new JTextField[maximum];
                quantumPanel = new JPanel[maximum];
                for(int i = 0; i < maximum; i++) {
                    boxName[i] = new JLabel("Q" + (i + 1) + "\t");
                    comboBox[i] = new JComboBox(algorithms);
                    comboBox[i].setSelectedIndex(0);

                    quantumField[i] = new JTextField(2);
                    quantumField[i].setText("0");
                    JLabel quantumLabel = new JLabel("Time Quantum\t", JLabel.RIGHT);
                    quantumPanel[i] = new JPanel(new BorderLayout());
                    quantumPanel[i].add(quantumLabel, BorderLayout.WEST);
                    quantumPanel[i].add(quantumField[i], BorderLayout.CENTER);

                    boxHold[i] = new JPanel(new BorderLayout());
                    boxHold[i].add(boxName[i], BorderLayout.WEST);
                    boxHold[i].add(comboBox[i], BorderLayout.CENTER);
                    boxHold[i].add(quantumPanel[i], BorderLayout.EAST);
                    comboHold.add(boxHold[i]);
                    comboHold.repaint();
                    comboHold.revalidate();
                }

                for(int i = 0; i < maximum; i++) {
                    comboBox[i].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JComboBox cb = (JComboBox)e.getSource();
                            String name = (String)cb.getSelectedItem();
                            // System.out.println(name);
                        }
                    });
                }
            }
        });

        return panel1;
    }

    public static String[] chooseTests() {

        int value = JOptionPane.showOptionDialog(null, getTestPanel(), "Test Cases", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, values, values[0]);
        if(value == 0) {
            inputs2[0] = String.valueOf(algoBox.getSelectedItem());
            inputs2[2] = qField.getText();
        }

        return inputs2;
    }

    private static JPanel getTestPanel() {

        panel2 = new JPanel(new BorderLayout());
        panel2.setPreferredSize(new Dimension(300, 100));

        top2 = new JPanel(new BorderLayout());
        mid = new JPanel(new BorderLayout());
        right = new JPanel(new GridLayout(1, 3));

        testAlgoLabel = new JLabel("Choose classical scheduling algorithm: ", JLabel.CENTER);
        algoBox = new JComboBox(algorithms);
        algoBox.setSelectedIndex(0);

        qLabel = new JLabel("Enter desired time quantum: ", JLabel.CENTER);
        qField = new JTextField(2);
        qField.setText("0");

        sPanel = new JPanel(new FlowLayout());
        sPanel.add(qLabel);
        sPanel.add(qField);

        testNumLabel = new JLabel("Choose test number: ", JLabel.CENTER);
        group2 = new ButtonGroup();
        for(int i = 0; i < 3; i++) {
            tests[i] = new JRadioButton(testsNum[i]);
            tests[i].setActionCommand(testsNum[i]);
            // tests[i].setEnabled(false);
            group2.add(tests[i]);
            right.add(tests[i]);
        }

        top2.add(testAlgoLabel, BorderLayout.NORTH);
        top2.add(algoBox, BorderLayout.SOUTH);
        mid.add(testNumLabel, BorderLayout.NORTH);
        mid.add(right, BorderLayout.CENTER);
        panel2.add(top2, BorderLayout.NORTH);
        panel2.add(mid, BorderLayout.SOUTH);
        panel2.add(sPanel, BorderLayout.CENTER);

        algoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // for(int i = 0; i < 3; i++) {
                    // tests[i].setEnabled(true);
                inputs2[0] = String.valueOf(algoBox.getSelectedItem());
                // }
            }
        });

        for(int i = 0; i < 3; i++) {
            tests[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    inputs2[1] = e.getActionCommand();
                }
            });
        }

        return panel2;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JOptionPaneExample().displayGUI1();
            }
        });
    }
}