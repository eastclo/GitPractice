package View;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class CommandExplainDialog extends JFrame {
	private JLabel jlb = new JLabel("");
	
	public CommandExplainDialog(String str) {
		JPanel pane = new JPanel();
		pane.add(jlb);
		pane.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));	//내부 패딩 설정
		jlb.setText(str.toString());
		
		this.add(pane, BorderLayout.WEST);
		this.setMinimumSize(new Dimension(500,200));	//최소 크기 설정
		this.pack();
		this.setLocation(200,200);
		this.setVisible(true);
	}
}