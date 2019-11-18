package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.swing.JTextField;


public class CommandInputListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		//textfield로부터 입력 값을 가져온다.
		JTextField txtField = (JTextField) e.getSource();
		String textFieldValue = txtField.getText();
		
		//Model에 정의 되어있는 명령어리스트를 가져옴
		Model.CommandList cmdlist = new Model.CommandList();
		String cmds[] = cmdlist.getCommandList();
		Arrays.sort(cmds);
			
		//배열에 입력된 명령어가 있는지 검색
		int index = Arrays.binarySearch(cmds, textFieldValue); /*****예외처리 해야함, 찾는 값이 없으면 음수값을 반환.**/
		
		//명령어를 파일 이름으로 저장했으므로 해당 명령어파일 내부에 적힌 클래스 이름을 얻기 위해 파일 객체와  FileReader를 사용함.
		String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdlist";
		File f = new File(cmdlistPath,cmds[index]);
		FileReader fin;
		char []buf = new char [1024];
		try {
			fin = new FileReader(cmdlistPath + File.separator + cmds[index]);
			fin.read(buf);
			fin.close();
		} catch (FileNotFoundException e1) {} catch (IOException e1) {}
		
		//버퍼의 남는 공백을 제거
		String Clazz = String.valueOf(buf).trim();
        
        //명렁어 실행
        try {
        	Class<?> clazz = Class.forName(Clazz);	//Clazz를 통해 클래스 생성
        	Object newObj = clazz.getDeclaredConstructor().newInstance();	//클래스로 객체생성
        	
        	Method m = clazz.getDeclaredMethod("executeCommand", null);	//파라미터로 메소드 이름, 해당 메소000드의 파라미터들의 타입(.class 붙임)
        	m.invoke(newObj,null);	//파라미터로 메소드의 클래스, 메소드의 파리미터들     	
        } catch (ClassNotFoundException e1) {
        	System.out.println("error1");
        } catch (Exception e1) {
        	System.out.println("error2");
        }
	}
}