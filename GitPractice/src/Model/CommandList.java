package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CommandList {
	private String commands[];
	
	public CommandList() {
		/*cmdlist ������ ������ ��ɾ� �̸����� ���� �����Ͽ� ����*/
		String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdlist";
		File f = new File(cmdlistPath);
		this.commands = f.list();
		
		/*Model���� commandlist ���� �ȿ� ��ɾ� ��� �ۼ�*/
//		FileReader fin = new FileReader(".\\commandlist.txt");
	}
	
	public String[] getCommandList() {
		return commands;
	}
}
