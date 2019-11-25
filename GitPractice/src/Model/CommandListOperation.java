package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class CommandListOperation {
	
	//cmdlistpath 경로에 있는 cmds[index] 파일의 내부 정보를 읽어서 String 타입으로 리턴해주는 메소드 
	public static String getFileReadData(String cmdlistPath, String[] cmds, int index) {
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
	
	//cmdlistpath 경로에 있는 cmd 파일의 내부 정보를 읽어서 String 타입으로 리턴해주는 메소드 
	public static String getFileReadData(String cmdlistPath, String cmd) {
		File f = new File(cmdlistPath,cmd);
		 FileReader fin;
		 char []buf = new char [1024];
			try {
				fin = new FileReader(cmdlistPath + File.separator + cmd);
				fin.read(buf);
				fin.close();
			} catch (FileNotFoundException e1) {} catch (IOException e1) {}
		String Clazz = String.valueOf(buf).trim();
		
		return String.valueOf(buf).trim();	//버퍼에 공백 제거
	}
	
	//cmdslist에서 input 명령어가 없으면 -1, 있으면 해당 인덱스를 리턴해준다.
	public static int searchCommand(String[][] cmdsDevide, String[] input) {
		int numberOfCmd = cmdsDevide.length;	//명령어 리스트에 있는 명령어 개수
		int numberOfOption = 0;	//input의 명령어 옵션 수
		int[] numberOfOptionList = new int[cmdsDevide.length];	//명령어 리스트에 있는 명령어들의 옵션 수
		
		//입력한 명령어와 명령어 리스트의 옵션 개수 체크
		for(int i = 2; i < 5; i++) 
			if(input[i]!="")
				numberOfOption++;
		for(int i = 0; i < numberOfCmd; i++) {
			for(int j = 2; j < 5; j++) 
				if(cmdsDevide[i][j]!="")
					numberOfOptionList[i]++;
		}
		
		
		for(int i = 0; i < numberOfCmd; i++) {
			if(cmdsDevide[i][1].equals(input[1])) {	//명령어 부가 같을 때
				if(numberOfOption == 0 && numberOfOptionList[i] == 0) {	//둘 다 옵션 없으면 찾음
					return i;
				} else if(numberOfOption == 1 && numberOfOptionList[i] == 1 && (cmdsDevide[i][2].equals(input[2]))) {	//둘 다 옵션이 하나만 있고 같으면 찾음
					return i;
				} else if(numberOfOption == 2 && numberOfOptionList[i] == 2 &&
						((cmdsDevide[i][2].equals(input[2]) && cmdsDevide[i][3].equals(input[3])) || (cmdsDevide[i][3].equals(input[2]) && cmdsDevide[i][2].equals(input[3])))) {	//둘 다 옵션이 2개 있고 같으면 찾음
					return i;
				} else if(numberOfOption == 3 && numberOfOptionList[i] == 3 && ((cmdsDevide[i][2].equals(input[2]) && cmdsDevide[i][3].equals(input[3]) && cmdsDevide[i][4].equals(input[4])) || 
						(cmdsDevide[i][2].equals(input[2]) && cmdsDevide[i][4].equals(input[3]) && cmdsDevide[i][3].equals(input[4])) ||
						(cmdsDevide[i][3].equals(input[2]) && cmdsDevide[i][2].equals(input[3]) && cmdsDevide[i][4].equals(input[4])) ||	//옵션 3개가 같을 경우의 수는 6가지.
						(cmdsDevide[i][3].equals(input[2]) && cmdsDevide[i][4].equals(input[3]) && cmdsDevide[i][2].equals(input[4])) ||
						(cmdsDevide[i][4].equals(input[2]) && cmdsDevide[i][2].equals(input[3]) && cmdsDevide[i][3].equals(input[4])) ||
						(cmdsDevide[i][4].equals(input[2]) && cmdsDevide[i][3].equals(input[3]) && cmdsDevide[i][2].equals(input[4])))) {	//둘 다 옵션이 3개 있고 같으면 찾음
					return i;
				}
			}
		}
		
		return -1;	//정의된 명령어가 없으면 -1 리턴
	}
	
	//textValue 명령어를  [0]: git, [1]: 명령어  [2],[3],[4]: 명령어 옵션, [5],[6],[7]: 인자 값으로 분할하여 String[] 타입으로 리턴한다.
	public static String[] devideInputText(String textValue) {
		/*[0]: git, [1]: 명령어  [2],[3],[4]: 명령어 옵션, [5],[6],[7]: 인자 값으로 나눠야 한다.*/
		String input[] = textValue.split(" ");
		int length = input.length;
		if(input.length < 8) {
			String[] tmp = new String[input.length];
			for(int i = 0; i < input.length; i++)
				tmp[i] = input[i];
			String[] tmp2 = new String[8];
			input = tmp2;
			for(int i = 0; i < tmp.length; i++)
				input[i] = tmp[i];
		}
				
		if(input[0].equals("git")) {
			//옵션과 인자값들 나눠야함
			if(input[2]!=null && input[2].charAt(0) == '-') {
				if(input[3] != null && input[3].charAt(0) == '-') {
					if(input[4] != null && input[4].charAt(0) == '-') {
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
		return input;
	}
}
