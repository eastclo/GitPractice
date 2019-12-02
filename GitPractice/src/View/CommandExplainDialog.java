package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.FlowLayout;

public class CommandExplainDialog extends JFrame {
	
	private JTextArea explainArea = new JTextArea("");
	
	public CommandExplainDialog(String str) {

		JPanel dialogPane = new JPanel();
        dialogPane.setBackground(Color.LIGHT_GRAY);
		explainArea.setEditable(false);
		explainArea.setBackground(Color.LIGHT_GRAY);
		dialogPane.add(explainArea);
		dialogPane.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));	//내부 패딩 설정
		explainArea.setText(str.toString());
		
		getContentPane().add(dialogPane, BorderLayout.CENTER);
				
		JScrollPane scrollPane = new JScrollPane();
		dialogPane.add(scrollPane);
		scrollPane.setViewportView(explainArea);	//스크롤바->스크롤페인 변경(오류수정)
		getContentPane().add(scrollPane, BorderLayout.EAST);
		dialogPane.add(scrollPane);		
		
		this.pack();
		this.setLocation(200,200);
		this.setMaximumSize(new Dimension(450,325));
		this.setVisible(true);
		
	}
}

