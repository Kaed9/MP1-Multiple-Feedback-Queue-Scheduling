import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
// additional
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
//
import javax.swing.text.DefaultCaret;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
//
// import java.util.Scanner;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.BadLocationException;


public class HawkFrame extends JFrame {

  private JPanel mainPanel;
  private JTextArea textArea = new JTextArea();
  private JTextArea textArea2 = new JTextArea();

  // additional
  private JTextPane textPane;
  //

  
  private JLabel menuLabel = new JLabel(new ImageIcon("img/menu.png"));
  private JLabel openLabel = new JLabel(new ImageIcon("img/open.png"));
  private JLabel writeLabel = new JLabel(new ImageIcon("img/write.png"));
  private JLabel exitLabel = new JLabel(new ImageIcon("img/exit.png"));
  private JLabel titlebarLabel = new JLabel(new ImageIcon("img/titlebar.png"));
  private JLabel closeLabel = new JLabel(new ImageIcon("img/close.png"));
  private JLabel minimizeLabel = new JLabel(new ImageIcon("img/minimize.png"));
  private JLabel maximizeLabel = new JLabel(new ImageIcon("img/maximize.png"));
  
  
  private String openFileString = "";
  private MouseListener myMouse = new MouseListener();
  
  private JTabbedPane tabbedPane = new JTabbedPane();
  private JScrollPane scrollingArea;
  
  public HawkFrame(){
    

	mainPanel = new BackgroundPanel("img/background.png");
	mainPanel.setLayout(null);
	add(mainPanel);
	
	openLabel.setBounds(440, 575, 172, 119);
	mainPanel.add(openLabel);
	
	writeLabel.setBounds(610, 575, 172, 119);
	mainPanel.add(writeLabel);

	exitLabel.setBounds(770, 575, 172, 119);
	mainPanel.add(exitLabel);


	menuLabel.setBounds(380,30,622,725);
	mainPanel.add(menuLabel);
	
	addListener();
  }

  public void addListener(){
    openLabel.addMouseListener(myMouse);
    writeLabel.addMouseListener(myMouse);
    exitLabel.addMouseListener(myMouse);
	closeLabel.addMouseListener(myMouse);
	minimizeLabel.addMouseListener(myMouse);
	maximizeLabel.addMouseListener(myMouse);

  }  
  
  public void removeObjects(){
	mainPanel.removeAll();
    repaint();
    revalidate();	
	  
  }
  
  
  
  public void addEditor(){
	System.out.print("ADI NA");	
	
	 // adjustment

    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet dataTypes = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(35, 16, 216));
    final AttributeSet voidKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 155, 0));
    final AttributeSet loopKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(190, 19, 42));
    final AttributeSet conditionalKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(12, 142, 126));
    final AttributeSet booleanKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(142, 45, 136));
    final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    DefaultStyledDocument doc = new DefaultStyledDocument() {
        public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
        	if(str.contains("("))
            	super.insertString(offset, str + ")", a);
            else if(str.contains("{"))
            	super.insertString(offset, str + "}", a);
            else if(str.contains("["))
            	super.insertString(offset, str + "]", a);
            else
            	super.insertString(offset, str, a);

            String text = getText(0, getLength());
            int before = findLastNonWordChar(text, offset);
            if (before < 0) before = 0;
            int after = findFirstNonWordChar(text, offset + str.length());
            int wordL = before;
            int wordR = before;

            while (wordR <= after) {
                if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                    if (text.substring(wordL, wordR).matches("(\\W)*(program|boolean|char|float|int|String)"))
                        setCharacterAttributes(wordL, wordR - wordL, dataTypes, false);
                    else if(text.substring(wordL, wordR).matches("(\\W)*(void|exit|nil|null)")) // nil or null not sure
                    	setCharacterAttributes(wordL, wordR - wordL, voidKeyword, false);
                    else if(text.substring(wordL, wordR).matches("(\\W)*(for|do|while|repeat|times|break)"))
                    	setCharacterAttributes(wordL, wordR - wordL, loopKeyword, false);
                    else if(text.substring(wordL, wordR).matches("(\\W)*(if|else|elsif|incase|is)"))
                    	setCharacterAttributes(wordL, wordR - wordL, conditionalKeyword, false);
                    else if(text.substring(wordL, wordR).matches("(\\W)*(true|false|TRUE|FALSE)")) // di ak sure hehe
                    	setCharacterAttributes(wordL, wordR - wordL, booleanKeyword, false);
                    else
                        setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                    wordL = wordR;
                }
                wordR++;
            }
        }

        public void remove (int offs, int len) throws BadLocationException {
            super.remove(offs, len);

            String text = getText(0, getLength());
            int before = findLastNonWordChar(text, offs);
            if (before < 0) before = 0;
            int after = findFirstNonWordChar(text, offs);

            if (text.substring(before, after).matches("(\\W)*(program|boolean|char|float|int|String)"))
                setCharacterAttributes(before, after - before, dataTypes, false);
            else if(text.substring(before, after).matches("(\\W)*(void|exit|nil|null)")) // nil or null not sure
            	setCharacterAttributes(before, after - before, voidKeyword, false);
            else if(text.substring(before, after).matches("(\\W)*(for|do|while|repeat|times|break)"))
            	setCharacterAttributes(before, after - before, loopKeyword, false);
            else if(text.substring(before, after).matches("(\\W)*(if|else|elsif|incase|is|default)"))
            	setCharacterAttributes(before, after - before, conditionalKeyword, false);
             else if(text.substring(before, after).matches("(\\W)*(true|false|TRUE|FALSE)")) // di ak sure hehe
            	setCharacterAttributes(before, after - before, booleanKeyword, false);
            else
                setCharacterAttributes(before, after - before, attrBlack, false);
        }
    };

    // adjustment

    textPane = new JTextPane(doc);
	
	JPanel textEditorPanel = new JPanel(null);
	textEditorPanel.setBounds(0,0,1366,768);
	textEditorPanel.setBackground(new Color(68,109,107));

	closeLabel.setBounds(6, 6, 13, 12);
	mainPanel.add(closeLabel);
	
	minimizeLabel.setBounds(23, 6, 13,12);
	mainPanel.add(minimizeLabel);
	
	maximizeLabel.setBounds(40, 6, 13,12);
	mainPanel.add(maximizeLabel);
	
    titlebarLabel.setBounds(0,0,1366, 24);
    mainPanel.add(titlebarLabel);
	
	mainPanel.add(textEditorPanel);
	
	JLayeredPane lp = getLayeredPane();
    //scrollingArea = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	//textArea.setBackground(new Color(40,40,40));
	scrollingArea = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	// textPane.setBackground(new Color(40,40,40));
    lp.add(scrollingArea);
  
    //textArea.setFont(new Font("Consolas", Font.PLAIN, 20));
    //textArea.setForeground(new Color(240,240,240));
    textPane.setFont(new Font("Consolas", Font.PLAIN, 20));
    //textPane.setForeground(new Color(240,240,240));	
    
	tabbedPane.addTab(openFileString, scrollingArea);  
	tabbedPane.setBounds(5,50,770,712);
	tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	textEditorPanel.add(tabbedPane);
	tabbedPane.addMouseListener(myMouse);
	//DefaultCaret caret = (DefaultCaret)textArea.getCaret();
	DefaultCaret caret = (DefaultCaret)textPane.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
  }
  public void addConsole(){

	  JLayeredPane lp = getLayeredPane();
    JScrollPane scrollingArea2 = new JScrollPane(textArea2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    scrollingArea2.setBounds(776,80,585,682);
	textArea2.setBackground(new Color(20,20,20));
    textArea2.setEditable(false);
    lp.add(scrollingArea2);

    DefaultCaret caret = (DefaultCaret)textArea2.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    textArea2.setFont(new Font("Consolas", Font.PLAIN, 15));
    textArea2.setForeground(new Color(240,240,240));
  
    textArea2.append("Hawk Programming Console [Version 1.0]\n(c) 2018 Seven Deadly Sins. All rights reserved.");
	  
  }
 
  private class MouseListener extends MouseAdapter{
	public void mouseClicked(MouseEvent event){
		if(event.getSource() == exitLabel){
		  System.exit(0);
		}
		if(event.getSource() == closeLabel){
		  System.exit(0);
		}
		if(event.getSource() == minimizeLabel){ 
		  setState(ICONIFIED);	
		}

		if(event.getSource() == openLabel){
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("bin"));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".hawk", "hawk");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
				openFileString = chooser.getSelectedFile().getName();
				removeObjects();
				addEditor();
				addConsole();
				
				try{
					BufferedReader br = new BufferedReader(new FileReader(("bin/"+openFileString)));
					String line = null;
					while ((line = br.readLine()) != null) {
		
						//textArea.append(line + "\n");
						for(String temp : line.split(" ")) {
							// if(temp.equals("program")) {
							// 	appendToPane(textPane, temp + " ", new Color(0, 155, 0));
							// } else if(temp.equals("void")) {
							// 	appendToPane(textPane, temp + " ", new Color(35, 16, 216));
							// } else {
								appendToPane(textPane, temp + " ", Color.BLACK);
							// }
						}
						// appendToPane(textPane, "\n", Color.BLACK);

					}
				}catch(Exception IO){}
            }
		}
		if(event.getSource() == writeLabel){
		  removeObjects();
		  addEditor();
		  addConsole();
		}
		if(event.getSource() == tabbedPane){
		 
		 
		}


	}
    public void mouseEntered(MouseEvent event){
      if(event.getSource() == exitLabel){
		  exitLabel.setIcon(new ImageIcon("img/exitInv.png"));
	  }
	  if(event.getSource() == openLabel){
		  openLabel.setIcon(new ImageIcon("img/openInv.png"));
		  
	  }
	  if(event.getSource() == writeLabel){
		  writeLabel.setIcon(new ImageIcon("img/writeInv.png"));
		  
	  }
    }

    public void mouseExited(MouseEvent event){
      if(event.getSource() == exitLabel){
		  exitLabel.setIcon(new ImageIcon("img/exit.png"));
	  }
	  if(event.getSource() == openLabel){
		  openLabel.setIcon(new ImageIcon("img/open.png"));
		  
	  }
	  if(event.getSource() == writeLabel){
		  writeLabel.setIcon(new ImageIcon("img/write.png"));
		  
	  }	
	}		
  }	  

  
  private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Consolas");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

	private int findLastNonWordChar (String text, int index) {
		while (--index >= 0) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
			    break;
			}
		}
		return index;
	}

	private int findFirstNonWordChar (String text, int index) {
		while (index < text.length()) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
			    break;
			}
			index++;
		}
		return index;
	}
    
}