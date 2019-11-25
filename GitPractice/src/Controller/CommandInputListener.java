package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import Model.CommandStack;


public class CommandInputListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		//textfield로부터 입력 값을 가져온다.
		JTextField txtField = (JTextField) e.getSource();
		String textFieldValue = txtField.getText();
	
		/* 텍스트 필드에서 명령어, 옵션, 인자값 등으로 분할한 후 명령어와 옵션 만으로 실행할 클래스 파일 결정되게 해야함. 이후 실행 메소드엔 인자값 넘겨주고 */
		String input[] = devideInputText(textFieldValue);	//[0]: git, [1]: 명령어  [2],[3],[4]: 명령어 옵션, [5],[6],[7]: 인자 값
		
		//Model에 정의 되어있는 명령어리스트를 가져옴
		Model.CommandList cmdlist = new Model.CommandList();
		String cmds[] = cmdlist.getCommandList();
		Arrays.sort(cmds);
			
		//배열에 입력된 명령어가 있는지 검색
		int index = Arrays.binarySearch(cmds, textFieldValue); /*****예외처리 해야함, 찾는 값이 없으면 음수값을 반환.**/
		Model.CommandStack.push(textFieldValue);	//예외처리 후 명령어가 있으면 명령어 스택에 저장
		
		//명령어를 파일 이름으로 저장했으므로 해당 명령어파일 내부에 적힌 클래스 이름을 얻기 위해 파일 객체와  FileReader를 사용함.
		String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdlist";
		String Clazz = getFileReadData(cmdlistPath, cmds, index);	//파일 내부 데이터를 읽어오는 메소드 호출
        
        //명렁어 실행
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
	}
	
	private String getFileReadData(String cmdlistPath, String[] cmds, int index) {
		File f = new File(cmdlistPath,cmds[index]);
		FileReader fin;
		char []buf = new char [1024];
		try {
			fin = new FileReader(cmdlistPath + File.separator + cmds[index]);
			fin.read(buf);
			fin.close();
		} catch (FileNotFoundException e1) {} catch (IOException e1) {}
		
		return String.valueOf(buf).trim();	//버퍼에 공백 제거
	}
	
	private String[] devideInputText(String textValue) {
		/*[0]: git, [1]: 명령어  [2],[3],[4]: 명령어 옵션, [5],[6],[7]: 인자 값으로 나눠야 한다.*/
		String input[] = textValue.split(" ");
		int length = input.length;
				
		if(input[0].equals("git")) {
			//옵션과 인자값들 나눠야함
			if(input[2].charAt(0) == '-') {
				if(input[3].charAt(0) == '-') {
					if(input[4].charAt(0) == '-') {
						if(input.length < 8) {
							String[] tmp = new String[input.length];
							for(int i = 0; i < input.length; i++)
								tmp[i] = input[i];
							String[] tmp2 = new String[8];
							input = tmp2;
							for(int i = 0; i < tmp.length; i++)
								input[i] = tmp[i];
						}
						if(input.length==8) {
							/*큰 따옴표 사이 공백의 경우 예외처리*/
							if(input[5]!= null && input[5].charAt(0)=='"'){	
								if(input[6].charAt(input[6].length()-1) == '"') {
									input[5] = input[5].concat(input[6]);
									input[6] = "";
								} else if (input[7] != null && input[7].charAt(input[7].length()-1) == '"'){
									input[5] = input[5].concat(input[6]);
									input[5] = input[5].concat(input[7]);
									input[6] = ""; input[7] = "";
								} else {
									JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
									String[] error = new  String[2];
									error[0] = "error"; error[1] = "";
									return error;
								}
								
							} else if(input[6] != null && input[6].charAt(0)=='"'){
								if (input[7].charAt(input[7].length()-1) == '"'){
									input[6] = input[6].concat(input[7]);
									input[7] = "";
								} else {
									JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
									String[] error = new  String[2];
									error[0] = "error"; error[1] = "";
									return error;
								}
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
							String[] error = new  String[2];
							error[0] = "error"; error[1] = "";
							return error;
						}
							
					} else {
						String[] tmp = new String[input.length];
						for(int i = 0; i < input.length; i++)
							tmp[i] = input[i];
						String[] tmp2 = new String[input.length+1];
						input = tmp2;
						for(int i = 0; i < tmp.length; i++)
							input[i] = tmp[i];
						
						//[4]가 명령어 옵션이 아니므로 [4]를 비우고 [5]부터 시작하게 한다.
						for(int i = 0; i <= length-5; i++) {
							input[length-i] = input[length-i-1];
						}
						input[4] ="";
						/*큰 따옴표 사이 공백의 경우 예외처리*/
						if(input[5]!= null && input[5].charAt(0)=='"'){	
							if(input[6].charAt(input[6].length()-1) == '"') {
								input[5] = input[5].concat(input[6]);
								input[6] = "";
							} else if (input[7] != null && input[7].charAt(input[7].length()-1) == '"'){
								input[5] = input[5].concat(input[6]);
								input[5] = input[5].concat(input[7]);
								input[6] = ""; input[7] = "";
							} else {
								JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
								String[] error = new  String[2];
								error[0] = "error"; error[1] = "";
								return error;
							}
							
						} else if(input[6] != null && input[6].charAt(0)=='"'){
							if (input[7].charAt(input[7].length()-1) == '"'){
								input[6] = input[6].concat(input[7]);
								input[7] = "";
							} else {
								JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
								String[] error = new  String[2];
								error[0] = "error"; error[1] = "";
								return error;
							}
						}
					}
				} else {
					String[] tmp = new String[input.length];
					for(int i = 0; i < input.length; i++)
						tmp[i] = input[i];
					String[] tmp2 = new String[input.length+2];
					input = tmp2;
					for(int i = 0; i < tmp.length; i++)
						input[i] = tmp[i];
					
					//[3]가 명령어 옵션이 아니므로 [3],[4]를 비우고 [5]부터 시작하게 한다.
					for(int i = 0; i <= length-4; i++) {
						input[length-i+1] = input[length-i-1];
					}
					input[3] =""; input[4] ="";
					/*큰 따옴표 사이 공백의 경우 예외처리*/
					if(input[5]!= null && input[5].charAt(0)=='"'){	
						if(input[6].charAt(input[6].length()-1) == '"') {
							input[5] = input[5].concat(input[6]);
							input[6] = "";
						} else if (input[7] != null && input[7].charAt(input[7].length()-1) == '"'){
							input[5] = input[5].concat(input[6]);
							input[5] = input[5].concat(input[7]);
							input[6] = ""; input[7] = "";
						} else {
							JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
							String[] error = new  String[2];
							error[0] = "error"; error[1] = "";
							return error;
						}
						
					} else if(input[6] != null && input[6].charAt(0)=='"'){
						if (input[7].charAt(input[7].length()-1) == '"'){
							input[6] = input[6].concat(input[7]);
							input[7] = "";
						} else {
							JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
							String[] error = new  String[2];
							error[0] = "error"; error[1] = "";
							return error;
						}
					}
				}
			} else {
				String[] tmp = new String[input.length];
				for(int i = 0; i < input.length; i++)
					tmp[i] = input[i];
				String[] tmp2 = new String[input.length+3];
				input = tmp2;
				for(int i = 0; i < tmp.length; i++)
					input[i] = tmp[i];
				
				//[2]가 명령어 옵션이 아니므로 [2],[3],[4]를 비우고 [5]부터 시작하게 한다.
				for(int i = 0; i <= length-3; i++) {
					input[length-i+2] = input[length-i-1];
				}
				input[2] =""; input[3] =""; input[4] ="";
				/*큰 따옴표 사이 공백의 경우 예외처리*/
				if(input[5]!= null && input[5].charAt(0)=='"'){	
					if(input[6].charAt(input[6].length()-1) == '"') {
						input[5] = input[5].concat(input[6]);
						input[6] = "";
					} else if (input[7] != null && input[7].charAt(input[7].length()-1) == '"'){
						input[5] = input[5].concat(input[6]);
						input[5] = input[5].concat(input[7]);
						input[6] = ""; input[7] = "";
					} else {
						JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
						String[] error = new  String[2];
						error[0] = "error"; error[1] = "";
						return error;
					}
					
				} else if(input[6] != null && input[6].charAt(0)=='"'){
					if (input[7].charAt(input[7].length()-1) == '"'){
						input[6] = input[6].concat(input[7]);
						input[7] = "";
					} else {
						JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
						String[] error = new  String[2];
						error[0] = "error"; error[1] = "";
						return error;
					}
				}
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "깃 명령어가 아닙니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			String[] error = new  String[2];
			error[0] = "error"; error[1] = "";
			return error;
		}
		if(input.length < 8) {
			String[] tmp = new String[input.length];
			for(int i = 0; i < input.length; i++)
				tmp[i] = input[i];
			String[] tmp2 = new String[8];
			input = tmp2;
			for(int i = 0; i < tmp.length; i++)
				input[i] = tmp[i];
		}
		return input;
	}
}