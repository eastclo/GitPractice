package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.FlowLayout;

public class CommandExplainDialog extends JFrame {
	
	private JTextArea explainArea = new JTextArea("");
	
	public CommandExplainDialog(String str) {
		
		JPanel pane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pane.getLayout();
        pane.setBackground(Color.LIGHT_GRAY);
		explainArea.setEditable(false);
		explainArea.setBackground(Color.LIGHT_GRAY);
		pane.add(explainArea);
		pane.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));	//내부 패딩 설정
		explainArea.setText(str.toString());
		
		getContentPane().add(pane, BorderLayout.WEST);
		
		JScrollBar explainScrollBar = new JScrollBar();
		getContentPane().add(explainScrollBar, BorderLayout.EAST);
		//explainScrollBar.setBounds(10, 40, 13, 325);

		this.pack();
		this.setLocation(200,200);
		//this.setMaximumSize(new Dimension(30,20));
		this.setVisible(true);
	}

}
