package Model;

public class CommandStack {
	private static String[] commandStack = new String[50];
	private static int top = -1;
	private static int max = -1;
	
	//��ɾ� ���� �Է½� ȣ��
	public static void push(String cmd) {
		commandStack[++top] = cmd;
		max = top;
	}
	
	//�ڷΰ��� ��ư Ŭ���� ȣ��
	public static String pop() {
		if(top != -1) 
			return commandStack[top--];
		
		else
			return null;
	}
	
	//�����ΰ��� ��ư Ŭ���� ȣ��
	public static String go() {
		if(top < max) 
			return commandStack[++top];
		else
			return null;
	} 
	
}