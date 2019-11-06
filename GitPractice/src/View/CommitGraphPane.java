package View;

import javax.swing.*;

public class CommitGraphPane extends JPanel{

	public CommitGraphPane() {
	JPanel CommitGraphPane = new JPanel();
	CommitGraphPane.setBounds(236, 409, 539, 195);
	MainFrame.contentPane.add(CommitGraphPane);
	CommitGraphPane.setLayout(null);
	
	JLabel lblGraphResult = new JLabel("Ä¿¹Ô ³»¿ª");
	lblGraphResult.setBounds(14, 12, 78, 18);
	CommitGraphPane.add(lblGraphResult);
	
	JScrollBar scrollGraph = new JScrollBar();
	scrollGraph.setOrientation(JScrollBar.HORIZONTAL);
	scrollGraph.setBounds(14, 170, 511, 13);
	CommitGraphPane.add(scrollGraph);
	
	JTextArea DrawingGraph = new JTextArea();
	DrawingGraph.setBounds(14, 38, 511, 145);
	CommitGraphPane.add(DrawingGraph);
	
	
}
}