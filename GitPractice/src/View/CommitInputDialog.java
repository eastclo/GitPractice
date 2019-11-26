package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CommitInputDialog extends JFrame {
	
	public JTextArea text;
	public JButton btn;
	
	public CommitInputDialog() {
		JPanel pane = new JPanel();
		pane.setBounds(100,100,900,400);
		
		this.add(pane);
		pane.setLayout(null);

		text = new JTextArea();
		text.setBounds(50,50,900,300);
		pane.add(text);

		btn = new JButton("입력");
		btn.setBounds(425,400,150,30);
		pane.add(btn);
		
		this.setMinimumSize(new Dimension(1000,500));
		this.pack();
		this.setLocation(200,200);
		this.setVisible(true);
	}
}
