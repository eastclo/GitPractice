package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

public class RepoInfoDialog extends JFrame{
	private JTextArea infoArea = new JTextArea("");
	
	public RepoInfoDialog(String str) {
		JPanel pane = new JPanel();
		
		FlowLayout flowLayout = (FlowLayout)pane.getLayout();
		pane.setBackground(Color.CYAN);
		infoArea.setEditable(false);
		infoArea.setBackground(Color.GREEN);
		pane.add(infoArea);
		pane.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
		infoArea.setText(str.toString());
		
		getContentPane().add(pane, BorderLayout.WEST);
		
		JScrollBar infoScrollBar = new JScrollBar();
		getContentPane().add(infoScrollBar, BorderLayout.EAST);
		
		this.pack();
		this.setLocation(800, 200);
		this.setVisible(true);
	}
}
