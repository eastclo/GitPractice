package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CommandList {
	private String commands[];
	
	public CommandList() {
		/*cmdlist 폴더에 각각의 명령어 이름으로 파일 생성하여 저장*/
		String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdlist";
		File f = new File(cmdlistPath);
		this.commands = f.list();
		
		/*Model하위 commandlist 파일 안에 명령어 목록 작성*/
//		FileReader fin = new FileReader(".\\commandlist.txt");
	}
	
	public String[] getCommandList() {
		return commands;
	}
}
