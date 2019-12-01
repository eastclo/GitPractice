package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

public class CommandListOperation {
	
	//cmdslist에서 input 명령어가 없으면 -1, 있으면 해당 인덱스를 리턴해준다.
	public static int searchCommand(String[][] cmdsDevide, String[] input) {
		int numberOfCmd = cmdsDevide.length;	//명령어 리스트에 있는 명령어 개수
		int numberOfOption = 0;	//input의 명령어 옵션 수
		int[] numberOfOptionList = new int[cmdsDevide.length];	//명령어 리스트에 있는 명령어들의 옵션 수
		
		//입력한 명령어와 명령어 리스트의 옵션 개수 체크
		for(int i = 2; i < input.length; i++) 
			if(input[i]!=null)
				numberOfOption++;
			else
				break;
		for(int i = 0; i < numberOfCmd; i++) {
			for(int j = 2; j < cmdsDevide[i].length; j++) 
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
	
	//textValue 명령어를  [0]: git, [1]: 명령어  [2],[3],[4]: 명령어 옵션, [5]이후 인덱스: 인자 값으로 분할하여 String[] 타입으로 리턴한다.
	public static String[] devideInputText(String cmd) {
		/*[0]: git, [1]: 명령어  [2],[3],[4]: 명령어 옵션, [5],[6],[7]: 인자 값으로 나눠야 한다.*/
		/**1. 순차 탐색하여 큐에 push
		 * 2. 공백이 들어오면 pop하여 concat하면서 String으로 만든 후 다음 배열 인덱스에 넣기
		 * 3. "를 만나면 다시 "를 만날 때 까지 큐에 push
		 */
		int capacity = 0, index = 0;
		Queue<Character> q = new LinkedList<Character>();
		boolean checkDoubleQuotationMarks = true;
		String[] input = new String[capacity];
		char pre = 0;
		
		for(int i = 0; i<cmd.length(); i++) {	//cmd를 공백을 기준으로 split한다.
			switch(cmd.charAt(i)) {
			case '"':
				q.offer(cmd.charAt(i));
				if(checkDoubleQuotationMarks) {	//큰 따옴표가 시작하면 false
					if(pre!=' ')
						return printError("잘못 입력하였습니다.");
					checkDoubleQuotationMarks = false;
				}else 	//큰 따옴표가 끝나면 true
					checkDoubleQuotationMarks = true;
				pre = '"';
				break;
			case ' ':
				if(checkDoubleQuotationMarks) {	//공백을 만나면 pop하여 String으로 만들고 cmdfix 배열에 삽입
					if(q.isEmpty())//q가 공백이면 무시 
						break;
					input = increaseArray(input,1); //input 배열 크기 1 늘리기
					input[index]="";
					while(q.isEmpty()==false) {
						input[index] = input[index].concat(Character.toString(q.poll()));
					}
					index++;
				} else	//큰 따옴표 안의 내용은 공백이면 그대로 큐에 삽입
					q.offer(cmd.charAt(i));
				pre = ' ';
				break;
			default:
				if(cmd.charAt(i)=='-' && pre != '-' && pre != ' ' && pre != '"')
					return printError("잘못 입력하였습니다.");
				q.offer(cmd.charAt(i));
				pre = cmd.charAt(i);
				break;
			}
		}
		
		//q에 남아있는거 전부 빼내기
		if(q.isEmpty()==false) {
			input = increaseArray(input,1);
			input[index]="";
			while(q.isEmpty()==false) {
				input[index] = input[index].concat(Character.toString(q.poll()));
			}
		}
		
		if(checkDoubleQuotationMarks == false)
			return printError("잘못 입력하였습니다. Error:'\"' 입력 오류 ");
		
		//git 명령어인지 확인
		if(input[0].equals("git")) {
			//옵션영역 확보
			if(input.length>2) { //옵션 체크
				if(input[2].charAt(0) != '-') {	//옵션이 없을 때	
					String[] tmp = new String[input.length+3];
					tmp[0] = input[0]; tmp[1] = input[1];
					
					for(int i = input.length-1; i>=2; i--)
						tmp[i+3] = input[i];
					
					input = tmp;
				} else if(input[2].charAt(0) == '-') {
					if(input.length>3) {
						if(input[3].charAt(0) != '-') {	//옵션이 하나만 있을 때
							String[] tmp = new String[input.length+2];
							tmp[0] = input[0]; tmp[1] = input[1]; tmp[2] = input [2];
							
							for(int i = input.length-1; i>=3; i--)
								tmp[i+2] = input[i];
							
							input = tmp;
						} else if(input[3].charAt(0) == '-') {	//옵션이 두 개 있을 때
							if(input.length > 4) {
								if(input[4].charAt(0) != '-') {
									String[] tmp = new String[input.length+1];
									tmp[0] = input[0]; tmp[1] = input[1]; tmp[2] = input [2]; tmp[3] = input[3];
									
									for(int i = input.length-1; i>=4; i--)
										tmp[i+1] = input[i];
									
									input = tmp;
								}
							}
						}
					}
				}
				//예외처리. 옵션 영역 이후에 옵션이 있으면 에러
				for(int i = 5; i<input.length; i++) {
					if(input[i] != null && input[i].charAt(0) == '-')
						return printError("잘못 입력하였습니다.");
				}
			}
			return input;
		}
		return printError("깃 명령어가 아닙니다.");
	}
	
	private static String[] increaseArray(String[] input, int amount){
		String[] tmp = new String[input.length+amount];
		for(int i=0; i<input.length; i++)
			tmp[i] = input[i];
		input = tmp;
		return input;
	}
	
	//에러 메시지창 호출 메소드, 0번 째 인덱스에 "error"리턴
	private static String[] printError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
		String[] error = new  String[2];
		error[0] = "error"; error[1] = null;
		return error;
	}

	public static boolean execute(String action, String Clazz, String[] input){
		String[] parameter;
		//입력한 명령어의 인자가가 없으면  paramter 변수에 null을 설정
		if(input.length>5) {
			parameter = new String[input.length-5];
			for(int i=0; i<input.length-5; i++)
				parameter[i] = input[i+5];
		} else
			parameter = null;
		boolean retMethod = false;
		try {
        	Class<?> clazz = Class.forName(Clazz);	//Clazz를 통해 클래스 생성
        	Object newObj = clazz.getDeclaredConstructor().newInstance();	//클래스로 객체생성

        	Object arglist = new Object();
        	arglist = parameter;

        	Method m = clazz.getDeclaredMethod(action, String[].class);	//파라미터로 메소드 이름, 해당 메소드의 파라미터들의 타입(.class 붙임)
        	retMethod = (boolean)m.invoke(newObj,arglist);	//파라미터로 메소드의 클래스, 메소드의 파리미터들   
        } catch (ClassNotFoundException e1) {
        	System.out.println("error1");
        } catch (Exception e1) {
        	System.out.println("error2");
        }
		return retMethod;
	}
}
