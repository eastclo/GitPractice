package Model;

import java.io.File;

public class CommandStack {
	private static String[] commandStack = new String[50];
	private static int top = -1;
	private static int max = -1;
	
	//명령어 최초 입력시 호출
	public static void push(String cmd) {
		commandStack[++top] = cmd;
		max = top;
	}
	
	//뒤로가기 버튼 클릭시 호출
	public static String pop() {
		if(top != -1) 
			return commandStack[top--];
		
		else
			return null;
	}
	
	//앞으로가기 버튼 클릭시 호출
	public static String go() {
		if(top < max) 
			return commandStack[++top];
		else
			return null;
	} 
	
	public static int printTop() {
		return top;
	}
	
	public static String createBackup() {
		int fileName = Model.CommandStack.printTop()+2;
		String backupPath = new String("."+File.separator+"BackUp"+File.separator+fileName);
		return backupPath;
	}
	
	public static String loadBackup() {
		int fileName = Model.CommandStack.printTop()+2;
		String backupPath = new String("."+File.separator+"BackUp"+File.separator+fileName);
		return backupPath;
	}
}