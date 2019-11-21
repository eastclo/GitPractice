package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import View.CommandIndexPane;


public class CommandSearchListener implements ActionListener {
	
	private JList list;
	
	public CommandSearchListener(CommandIndexPane view) {
		this.list = view.getJList();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//텍스트 필드로 부터 입력 값 받아오기
		JTextField txtField = (JTextField) e.getSource();
		String textFieldValue = txtField.getText();
		
		Model.CommandExplainList cmdlist = new Model.CommandExplainList();
		String cmds[] = cmdlist.getCommandList();
		
		/*list에 목록 추가하기 위한 임시 모델 생성
		* list엔 addElement 같은 메소드가 없고 완성된 아이템 리스트를 넘겨 줘야 한다.
		*/
		DefaultListModel listModel = new DefaultListModel();
		
		for(String cmd : cmds) {
			//명령어 내부 설명이 검색될 경우를 위해 파일 내부 읽어오기
			String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdexplain";
			File f = new File(cmdlistPath,cmd);
			char []buf = new char [1024];
			try {
				FileReader fin = new FileReader(cmdlistPath + File.separator + cmd);
				fin.read(buf);
				fin.close();
			} catch (FileNotFoundException e1) {} catch (IOException e1) {}
			String explain = String.valueOf(buf).trim();
			
			if(cmd.contains(textFieldValue)) {	//명령어가 검색될 경우
				listModel.addElement(cmd);
			} else if(explain.contains(textFieldValue)) {	//명령어 내부 설명에 검색한 내용이 있을 경우
				listModel.addElement(cmd);
			}
		}
		list.setModel(listModel);
		list.repaint();
	}
}