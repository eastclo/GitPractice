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
				 File f = new File(cmdlistPath,cmd);
				 FileReader fin;
				 char []buf = new char [1024];
					try {
						fin = new FileReader(cmdlistPath + File.separator + cmd);
						fin.read(buf);
						fin.close();
					} catch (FileNotFoundException e1) {} catch (IOException e1) {}
				String Clazz = String.valueOf(buf).trim();
				try {
		        	Class<?> clazz = Class.forName(Clazz);	//Clazz를 통해 클래스 생성
		        	Object newObj = clazz.getDeclaredConstructor().newInstance();	//클래스로 객체생성
		        	
		        	Method m = clazz.getDeclaredMethod("cancelCommand", null);	//파라미터로 메소드 이름, 해당 메소000드의 파라미터들의 타입(.class 붙임)
		        	m.invoke(newObj,null);	//파라미터로 메소드의 클래스, 메소드의 파리미터들     	
		        } catch (ClassNotFoundException e1) {
		        	System.out.println("error1");
		        } catch (Exception e1) {
		        	System.out.println("error2");
		        }
				JOptionPane.showMessageDialog(null, "'"+cmd +"'가 취소되었습니다.", "뒤로 가기", JOptionPane.INFORMATION_MESSAGE);	//알림 팝업
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
				 File f = new File(cmdlistPath,cmd);
				 FileReader fin;
				 char []buf = new char [1024];
					try {
						fin = new FileReader(cmdlistPath + File.separator + cmd);
						fin.read(buf);
						fin.close();
					} catch (FileNotFoundException e1) {} catch (IOException e1) {}
				String Clazz = String.valueOf(buf).trim();
				try {
		        	Class<?> clazz = Class.forName(Clazz);	//Clazz를 통해 클래스 생성
		        	Object newObj = clazz.getDeclaredConstructor().newInstance();	//클래스로 객체생성
		        	
		        	Method m = clazz.getDeclaredMethod("executeCommand", null);	//파라미터로 메소드 이름, 해당 메소드의 파라미터들의 타입(.class 붙임)
		        	m.invoke(newObj,null);	//파라미터로 메소드의 클래스, 메소드의 파리미터들     	
		        } catch (ClassNotFoundException e1) {
		        	System.out.println("error1");
		        } catch (Exception e1) {
		        	System.out.println("error2");
		        }				
				JOptionPane.showMessageDialog(null, "'"+cmd +"'가 실행되었습니다.", "앞으로 가기", JOptionPane.INFORMATION_MESSAGE);	//알림 팝업
			 } else {
				 JOptionPane.showMessageDialog(null, "수행할 명령어가 없습니다.", "오류", JOptionPane.WARNING_MESSAGE);	//알림 팝업
			 }
		 }
	}
}

