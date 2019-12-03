package Controller;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Model.CommitArray;
import Model.CurrentLocation;
import View.CommandInputPane;

public class ExecutionInit {
	public boolean executeCommand(String[] parameter) {
		File gitpath = new File(CurrentLocation.workspace.getPath());
		if(!new File(gitpath.getPath()+File.separator+".git").exists())
		{
			new File(gitpath.getPath()+File.separator+".git").mkdir();
			new File(gitpath.getPath()+File.separator+"master").mkdir();
			new File(gitpath.getPath()+File.separator+"master"+File.separator+"workspace").mkdir();
			new File(gitpath.getPath()+File.separator+"master"+File.separator+"add").mkdir();
			new File(gitpath.getPath()+File.separator+"master"+File.separator+"commit").mkdir();
			BranchFunction bf = new BranchFunction();
			CurrentLocation.changeBranch("master");
			CurrentLocation.BranchList=new ArrayList<String>();
			CurrentLocation.addBranch("master");
			List<String> branchList = CurrentLocation.getBranchList();
			bf.setArray(branchList);
			bf.BranchListSave();
			bf.BranchListOpen();
			new CommitArray().init();
			workspaceCopy(CurrentLocation.workspace,new File(gitpath.getPath()+File.separator+"master"+File.separator+"workspace"));
			workspaceDelete(CurrentLocation.workspace.getPath());
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "이미 초기화 되어있습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public boolean cancelCommand(String[] parameter) {
		File gitpath = new File(CurrentLocation.workspace.getPath());
		if(!new File(gitpath.getPath()+File.separator+".git").exists())
		{
			new File(gitpath.getPath()+File.separator+".git").mkdir();
			new File(gitpath.getPath()+File.separator+"master").mkdir();
			new File(gitpath.getPath()+File.separator+"master"+File.separator+"workspace").mkdir();
			new File(gitpath.getPath()+File.separator+"master"+File.separator+"add").mkdir();
			new File(gitpath.getPath()+File.separator+"master"+File.separator+"commit").mkdir();
			BranchFunction bf = new BranchFunction();
			CurrentLocation.changeBranch("master");
			CurrentLocation.BranchList=new ArrayList<String>();
			CurrentLocation.addBranch("master");
			List<String> branchList = CurrentLocation.getBranchList();
			bf.setArray(branchList);
			bf.BranchListSave();
			bf.BranchListOpen();
			new CommitArray().init();
			workspaceCopy(CurrentLocation.workspace,new File(gitpath.getPath()+File.separator+"master"+File.separator+"workspace"));
			workspaceDelete(CurrentLocation.workspace.getPath());
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "이미 초기화 되어있습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	private void workspaceCopy(File sourceF,File targetF) {
		File[] ff = sourceF.listFiles();
		for (File file : ff) {
			if(!file.getName().equals(".git")){
				if(!file.getName().equals("master"))
				{

					File temp = new File(targetF+File.separator+file.getName());
					if(file.isDirectory()) {
						temp.mkdir();
						workspaceCopy(file,temp);
						}
					else {
						try {
							FileWriter fileWriter = new FileWriter(temp);
							fileWriter.write(getTextFromFile(file));
							fileWriter.close();
						}catch(IOException e) {
							e.printStackTrace();
						}
				}
				}
			}
		}

	}
	private String getTextFromFile(File txtFile){
	    String text = "";
	 
	    BufferedReader br = null;
	    try{
	      br = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile)));
	      String line;
	      while((line = br.readLine()) != null){
	        text= text + line + "\n";
	      }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		return text;
	}
	private void workspaceDelete(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()){
				File[] folder_list = folder.listFiles();
				for (int i = 0; i < folder_list.length; i++) {
					if(folder.getName().equals(".git")||folder.getName().equals("master"))
					{
						if(folder_list[i].isFile()) {
							folder_list[i].delete();
						}
						else {
							workspaceDelete(folder_list[i].getPath());
						}
						folder_list[i].delete();
					}
				}
			}
	} catch (Exception e) {
		e.getStackTrace();
		}
		
	}

}
