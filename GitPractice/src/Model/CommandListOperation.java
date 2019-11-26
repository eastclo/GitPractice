package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

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
			if(input[i]!=null)
				numberOfOption++;
		for(int i = 0; i < numberOfCmd; i++) {
			for(int j = 2; j < 5; j++) 
				if(cmdsDevide[i][j]!=null)
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
	public static String[] devideInputText(String cmd) {
		/*[0]: git, [1]: 명령어  [2],[3],[4]: 명령어 옵션, [5],[6],[7]: 인자 값으로 나눠야 한다.*/
		
		//공백이 여러번이면 한 번만 들어가게 수정
		String cmdFix = cmd.trim().replaceAll(" +", " ");
		
		String input[] = cmdFix.split(" ");
		if(input.length < 5) {
			String[] tmp = new String[5];
			for(int i = 0; i<input.length; i++)
				tmp[i] = input[i];
			input = tmp;
		}
		
		//git 명령어인지 확인
		if(input[0].equals("git")) {
			//옵션영역 확보
			if(input[2] == null) 
				;
			else if(input[2].charAt(0) != '-') {	//옵션이 없을 때	
				String[] tmp = new String[input.length+3];
				tmp[0] = input[0]; tmp[1] = input[1];
				
				for(int i = input.length-1; i>=2; i--)
					tmp[i+3] = input[i];
				
				input = tmp;
			} else if(input[2].charAt(0) == '-') {
				if(input[3] == null) 
					;
				else if(input[3].charAt(0) != '-') {	//옵션이 하나만 있을 때
					String[] tmp = new String[input.length+2];
					tmp[0] = input[0]; tmp[1] = input[1]; tmp[2] = input [2];
					
					for(int i = input.length-1; i>=3; i--)
						tmp[i+2] = input[i];
					
					input = tmp;
				} else if(input[3].charAt(0) == '-') {	//옵션이 두 개 있을 때
					if(input[4] == null)
						;
					else if(input[4].charAt(0) != '-') {
						String[] tmp = new String[input.length+1];
						tmp[0] = input[0]; tmp[1] = input[1]; tmp[2] = input [2]; tmp[3] = input[3];
						
						for(int i = input.length-1; i>=4; i--)
							tmp[i+1] = input[i];
						
						input = tmp;
					}
				}
			}
			
			if(input.length < 8) {
				String[] tmp = new String[8];
				for(int i = 0; i<input.length; i++)
					tmp[i] = input[i];
				input = tmp;
			}
			
			//예외처리. 옵션 영역 이후에 옵션이 있으면 에러
			for(int i = 5; i<input.length; i++) {
				if(input[i] != null && input[i].charAt(0) == '-')
					return printError("잘못 입력하였습니다.");
			}
			
			//예외처리. 큰 따옴표로 메시지를 입력할 경우 띄어쓰기가 분할되므로 메시지는 합치기.
			boolean checkEndDoubleQuotationMarks = false;	//큰 따옴표가 쌍으로 오지 않으면 false
			int checkEndDoubleQuotationIndex = 0;
			int checkStartDoubleQuotationIndex = 0;
			for(int i = 5; i<input.length; i++) {
				if(input[i] != null && input[i].charAt(0) == '"') {
					checkStartDoubleQuotationIndex = i;
					for(int j = i; j <input.length; j++) {
						if(input[j] != null && input[j].charAt(input[j].length()-1) == '"') {
							checkEndDoubleQuotationIndex = j;
							for(int k = i+1; k < j; k++) {
								input[i] = input[i].concat(" "+input[k]);
								input[k] = null;
							}
							checkEndDoubleQuotationMarks = true;
							break;
						}
					}
					break;
				}
			}
			//예외처리. 큰 따옴표가 쌍으로 오지 않으면 에러
			if(checkStartDoubleQuotationIndex != 0 && !checkEndDoubleQuotationMarks)
				return printError("잘못 입력하였습니다.");
			
			//예외처리.따옴표가 있든 없든 입력 값(8) 초과시 에러.
			if(input.length-(checkEndDoubleQuotationIndex-checkStartDoubleQuotationIndex) > 8) {
				printError("잘못 입력하였습니다.");
			} else {
				String[] tmp = new String[8];
				int j = 0;
				for(int i = 0; i<input.length; i++) {
					if(checkStartDoubleQuotationIndex < j && j <= checkEndDoubleQuotationIndex) {
						j++; continue;
					}
					tmp[i] = input[j++];
				}
				input = tmp;
			}
			return input;
		}
		
		return printError("깃 명령어가 아닙니다.");
	}
	
	//에러 메시지창 호출 메소드, 0번 째 인덱스에 "error"리턴
	private static String[] printError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
		String[] error = new  String[2];
		error[0] = "error"; error[1] = null;
		return error;
	}

	public static void execute(String action, String Clazz, String[] input){
		try {
        	Class<?> clazz = Class.forName(Clazz);	//Clazz를 통해 클래스 생성
        	Object newObj = clazz.getDeclaredConstructor().newInstance();	//클래스로 객체생성
        	
        	Method m = clazz.getDeclaredMethod(action, String.class, String.class, String.class);	//파라미터로 메소드 이름, 해당 메소드의 파라미터들의 타입(.class 붙임)
        	m.invoke(newObj,input[5],input[6],input[7]);	//파라미터로 메소드의 클래스, 메소드의 파리미터들     	
        } catch (ClassNotFoundException e1) {
        	System.out.println("error1");
        } catch (Exception e1) {
        	System.out.println("error2");
        }
	}
}
