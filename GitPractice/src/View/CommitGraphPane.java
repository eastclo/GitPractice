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
	
	JScrollBar scrollGraph = new JScrollBar();
	scrollGraph.setOrientation(JScrollBar.HORIZONTAL);
	scrollGraph.setBounds(14, 170, 511, 13);
	CommitGraphPane.add(scrollGraph);
	
	JTextArea DrawingGraph = new JTextArea();
	DrawingGraph.setBounds(14, 38, 511, 145);
	CommitGraphPane.add(DrawingGraph);
	
	//Commit 내용을 저장하는 TEST입니다.
	CommitFunction commit = new CommitFunction();
	commit.commitAdd(1);
	commit.commitAdd(2);
	commit.commitListSave();
	//Commit 내용을 불러오는 TEST입니다.
	try {
		CommitFunction commitOpen = new CommitFunction();
		commitOpen.commitListOpen();
		List<Object> Array = commitOpen.CMArray;
		for(int i=0;i<Array.size();i++)
			System.out.printf("%d\n",Array.get(i));
	}
	catch (ParseException e) {
	      e.printStackTrace();
	    } 
	catch(IOException e) {
		e.printStackTrace();
	}
}
}