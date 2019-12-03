package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.swing.*;

import Model.CommandStack;
import Model.CommandListOperation;
import Model.FileOperation;

public class GobackButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdlist";
		 
		if(e.toString().contains("back")) {	//뒤로가기 버튼일 경우
			 String cmd = Model.CommandStack.pop();	//명령어 스택에서 뒤로가기 메소드로 명령어 받아오기
			 if (cmd != null) {	//뒤로가기 할 명령어가 없으면 null
				 /*	
				  * 받아온 명령어로 cancelCommand 메소드 실행
				  * 방식은 CommandInputListener에서 실행하는 방식과 동일
				  */
				 String input[] = CommandListOperation.devideInputText(cmd);
				 
				 String Clazz = FileOperation.getFileReadData(cmdlistPath, combineCmd(input));	//명령어 받아오기
				 
				 CommandListOperation.execute("cancelCommand",Clazz, input);
				JOptionPane.showMessageDialog(null, "'"+cmd.trim().replaceAll(" +", " ") +"'가 취소되었습니다.", "뒤로 가기", JOptionPane.INFORMATION_MESSAGE);	//알림 팝업
			 } else {
				 JOptionPane.showMessageDialog(null, "수행할 명령어가 없습니다.", "오류", JOptionPane.WARNING_MESSAGE);	//알림 팝업
			 }
		}
		
		else {	//앞으로가기 버튼일 경우
			 String cmd = Model.CommandStack.go();	//명령어 스택에서 앞으로가기 메소드로 명령어 받아오기
			 if (cmd != null) {	//앞으로가기 할 명령어가 없으면 null
				 /*	
				  * 받아온 명령어로 executeCommand 메소드 실행
				  * 방식은 CommandInputListener에서 실행하는 방식과 동일
				  */
				 String input[] = CommandListOperation.devideInputText(cmd);
				 
				 String Clazz = FileOperation.getFileReadData(cmdlistPath, combineCmd(input));	//명령어 받아오기
				 
				 CommandListOperation.execute("executeCommand",Clazz, input);		
				 JOptionPane.showMessageDialog(null, "'"+cmd.trim().replaceAll(" +", " ") +"'가 실행되었습니다.", "앞으로 가기", JOptionPane.INFORMATION_MESSAGE);	//알림 팝업
				 Model.CommandStack.push(cmd);
			 } else {
				 JOptionPane.showMessageDialog(null, "수행할 명령어가 없습니다.", "오류", JOptionPane.WARNING_MESSAGE);	//알림 팝업
			 }
		 }
	}
	
	private String combineCmd(String[] input) {
		if(input[2] == null)
			return input[0].concat(" "+input[1]);
		else if (input[3] == null) {
			return input[0].concat(" "+input[1]).concat(" "+input[2]);
		}
		else if (input[4] == null) {
			return input[0].concat(" "+input[1]).concat(" "+input[2]).concat(" "+input[3]);
		} else
			return input[0].concat(" "+input[1]).concat(" "+input[2]).concat(" "+input[3]).concat(" "+input[4]);
	}
}

