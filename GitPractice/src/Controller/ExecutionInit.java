package Controller;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

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
import Model.FileOperation;
import View.CommandInputPane;

public class ExecutionInit {
	static List<File> dfile=new ArrayList<File>();
	public boolean executeCommand(String[] parameter) {
		//git init명령어를 구현한 class이다.
		if(parameter==null)
		{
			//현재 workspace가 .git파일이 없는경우, git init이 되지 않은 것으로 간주한다.
			File gitpath = new File(CurrentLocation.workspace.getPath());
			dfile.add(gitpath);
			if(!new File(gitpath.getPath()+File.separator+".git").exists())
			{
				//git init이 되지 않은 workspace인 경우, 폴더 내부에 .git과 처음 시작브랜치인 master브랜치를 생성한다.
				new File(gitpath.getPath()+File.separator+".git").mkdir();
				new File(gitpath.getPath()+File.separator+"master").mkdir();
				new File(gitpath.getPath()+File.separator+"master"+File.separator+"workspace").mkdir();
				new File(gitpath.getPath()+File.separator+".git"+File.separator+"add").mkdir();
				new File(gitpath.getPath()+File.separator+"master"+File.separator+"commit").mkdir();
				//git 명령어를 사용할 수 있게 되었으므로, branchList와 commitList도 초기화한다.
				BranchFunction bf = new BranchFunction();
				CurrentLocation.changeBranch("master");
				CurrentLocation.BranchList=new ArrayList<String>();
				CurrentLocation.addBranch("master");
				List<String> branchList = CurrentLocation.getBranchList();
				bf.setArray(branchList);
				bf.BranchListSave();
				bf.BranchListOpen();
				new CommitArray().init();
				//초기화 한 후에는 폴더를 업로드 할 경우, workspace가 아닌 현재 브랜치 내부의 workspace폴더로 이동하게 되며, 처음 초기화 한 경우에는 모든 파일을 master/workspace로 이동시킨다.
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
		else
		{
			JOptionPane.showMessageDialog(null, "유효하지 않은 인자입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public boolean cancelCommand(String[] parameter) {
		File gitpath = dfile.get(dfile.size()-1);
		dfile.remove(dfile.size()-1);
		//초기화를 취소할 경우, .git폴더와 생성되었던 master폴더를 삭제한다.
		if(new File(gitpath.getPath()+File.separator+".git").exists())
		{
			FileOperation.copyFileAll(new File(gitpath.getPath()+File.separator+"master"+File.separator+"workspace"),CurrentLocation.workspace);
			FileOperation.deleteFile(new File(gitpath.getPath()+File.separator+".git"));
			FileOperation.deleteFile(new File(gitpath.getPath()+File.separator+"master"));
			new File(gitpath.getPath()+File.separator+".git").delete();
			new File(gitpath.getPath()+File.separator+"master").delete();
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "init되지 않은 파일입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	//workspace->master/workspace로 이동시켜주는 메소드이다.
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
	      br.close();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		return text;
	}
	//master/workspace로 이동 후, 남아있는 파일들은 삭제한다.
	private void workspaceDelete(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()){
				File[] folder_list = folder.listFiles();
				for (int i = 0; i < folder_list.length; i++) {
					if(!folder.getName().equals(".git"))
					{
						if(!folder.getName().equals("master"))
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
			}
	} catch (Exception e) {
		e.getStackTrace();
		}
		
	}

}
