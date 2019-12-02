package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.json.simple.parser.ParseException;

import Controller.CommitFunction;

public class CommitGraphPane extends JPanel{

	public CommitGraphPane() {
	JPanel CommitGraphPane = new JPanel();
	CommitGraphPane.setBounds(236, 409, 539, 195);
	MainFrame.contentPane.add(CommitGraphPane);
	CommitGraphPane.setLayout(null);
	
	JLabel lblGraphResult = new JLabel("커밋 내역");
	lblGraphResult.setBounds(14, 12, 78, 18);
	CommitGraphPane.add(lblGraphResult);
	
	JTextArea DrawingGraph = new JTextArea();
	DrawingGraph.setBounds(14, 38, 511, 145);
	CommitGraphPane.add(DrawingGraph);
	
	JScrollPane graphScroll = new JScrollPane();
	graphScroll.setViewportView(DrawingGraph);  //스크롤바->스크롤페인 변경(오류수정)
	graphScroll.setBounds(14, 38, 511, 145);
	CommitGraphPane.add(graphScroll);

	
}
}