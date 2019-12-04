package View;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MergeInputDialog extends JFrame implements ComponentListener {
	private JTextArea text1;
	private JTextArea text2;
	private JScrollPane first;
	private JScrollPane second;
	private JLabel file1;
	private JLabel file2;
	private JButton btn1;
	private JButton btn2;
	private JPanel pane;
	private String fileName;
	
	private int height = 1000;
	private int width = 1800;
	
	public MergeInputDialog(File conflictF1, File conflictF2) {
		fileName = conflictF1.getName();
		pane = new JPanel();
		
		//충돌하는 파일을 양쪽으로 띄움
		text1 = new JTextArea(readFileData(conflictF1));
		first = new JScrollPane(text1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		text2 = new JTextArea(readFileData(conflictF2));
		second = new JScrollPane(text2,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		btn1 = new JButton("클릭 시 1번 파일로 merge를 수행합니다.");
		btn1.setSize(200,30);
		btn1.setName("FirstFile");
		btn2 = new JButton("클릭 시 2번 파일로 merge를 수행합니다.");
		btn2.setSize(200,30);
		btn2.setName("SecondFile");
		file1 = new JLabel("하나");
		file2 = new JLabel("둘");
		
		pane.add(file1);
		pane.add(file2);
		pane.add(first);
		pane.add(second);
		pane.add(btn1);	 
		pane.add(btn2);
		this.add(pane);
		this.setLocation(50,50);
		this.setSize(width,height);
		this.setVisible(true);
		this.setTitle("해당 파일에서 충돌이 일어났습니다. 파일을 직접 수정 후 원하는 파일을 선택하세요.");
	}
	
	private String readFileData(File f) {
		 FileReader fin;
		 char []buf = new char [1024];
			try {
				fin = new FileReader(f.getPath());
				fin.read(buf);
				fin.close();
			} catch (FileNotFoundException e1) {} catch (IOException e1) {}
		String text = String.valueOf(buf).trim();	//버퍼에 공백 제거
		
		return text; 
	}
	
	public JButton getFirstBtn() {
		return btn1;
	}
	
	public JButton getSecondBtn() {
		return btn2;
	}
	
	public String getSecondFile() {
		return text2.getText();
	}
	
	public String getFristFile() {
		return text1.getText();
	}
	
	public String getFileName() {
		return fileName;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		width = e.getComponent().getWidth();
		height = e.getComponent().getHeight();
		first.setBounds(15,30,width/2-25,height-110);
		second.setBounds(width/2,30,width/2-25,height-110);
		btn1.setLocation(width*16/100, height-75);
		btn2.setLocation(width*68/100,height-75);
		file1.setLocation(15,5);
		file2.setLocation(width/2,5);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
