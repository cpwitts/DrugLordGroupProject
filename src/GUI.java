import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1121075646735683564L;
	JTextField dField = new JTextField(8);
	JTextField eField = new JTextField(8);
	JTextField mField = new JTextField(8);
	JButton bButton = new JButton("Buy");
	JButton sButton = new JButton("Sell");
	JButton aButton = new JButton("Attack");
	JButton stButton = new JButton("Stats");
	JButton rButton = new JButton("Retire");
	JButton ndButton = new JButton("Next Day");
	JLabel newLine = new JLabel ("\n");
	
	JTextArea textArea = new JTextArea(10, 12);
	JTextArea textArea2 = new JTextArea(10, 12);
	JTextArea textArea3 = new JTextArea(10, 12);

	public GUI() {
		setSize(800, 600) ;
		JPanel panel = new JPanel() ;
		panel.setLayout(new BorderLayout());
		add(panel) ;
		panel.setBackground(Color.orange);
		
		JPanel subPanelNorth = new JPanel();
		JLabel label1 = new JLabel("Day: ") ;
		subPanelNorth.add(label1) ;
		
		subPanelNorth.add(dField) ;
		
		
		JLabel label2 = new JLabel("Employees: ") ;
		subPanelNorth.add(label2) ;
		
		subPanelNorth.add(eField);
		
		JLabel label3 = new JLabel("Money: ") ;
		subPanelNorth.add(label3) ;
		
		subPanelNorth.add(mField);
		
		panel.add(subPanelNorth, BorderLayout.NORTH);
		
		JPanel subPanelCenter = new JPanel();
		JScrollPane scrollPane = new JScrollPane(textArea);
		JScrollPane scrollPane2 = new JScrollPane(textArea2);
		JScrollPane scrollPane3 = new JScrollPane(textArea3);
		subPanelCenter.add(scrollPane) ;
		subPanelCenter.add(bButton);
		subPanelCenter.add(newLine);
		subPanelCenter.add(scrollPane2) ;
		subPanelCenter.add(sButton);
		subPanelCenter.add(newLine);
		subPanelCenter.add(scrollPane3) ;
		subPanelCenter.add(aButton);
		subPanelCenter.add(newLine);
		panel.add(subPanelCenter, BorderLayout.CENTER);

		
		JPanel subPanelSouth = new JPanel();
		subPanelSouth.add(stButton);
		subPanelSouth.add(rButton);
		subPanelSouth.add(ndButton);
		panel.add(subPanelSouth, BorderLayout.SOUTH);
		
		
		setVisible(true);
	}

	
}
