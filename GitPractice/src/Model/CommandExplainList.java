package Model;

import java.io.File;

public class CommandExplainList {
	private String commands[];
	
	public CommandExplainList() {
		String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdexplain";
		File f = new File(cmdlistPath);
		this.commands = f.list();
	}
	
	public String[] getCommandList() {
		return commands;
	}
}
