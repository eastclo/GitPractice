package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.CurrentLocation;
import Model.FileOperation;
import View.CommandExplainDialog;
import View.CommandInputPane;

public class ExecutionBranch {
	static List<String>branchList = new ArrayList<String>();
	static List<File> dfile=new ArrayList<File>();
	
	public boolean executeCommand(String[] parameter) {
		branchList = CurrentLocation.getBranchList();
		if(parameter==null)
		{
			String outputS = new String("////branch List////\n\n");
			for(int i=0;i<branchList.size();i++)
			{
				outputS=outputS+branchList.get(i);
				if(branchList.get(i).equals(CurrentLocation.getBranch()))
					outputS=outputS+" * \n";
				else
					outputS=outputS+"\n";
			}
			CommandInputPane.allCommandtxt.setText(outputS);
			return true;
		}
		else if(parameter.length==1)
		{
			dfile.add(CurrentLocation.workspace);
			if(!CurrentLocation.addBranch(parameter[0]))
			{
				JOptionPane.showMessageDialog(null, "이미 존재하는 브랜치입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else
			{
				BranchFunction bf = new BranchFunction();
				bf.BranchListSave();
				new File(CurrentLocation.workspace.getPath()+File.separator+parameter[0]).mkdir();
				new File(CurrentLocation.workspace.getPath()+File.separator+parameter[0]+File.separator+"workspace").mkdir();
				FileOperation.copyFileAll(new File(CurrentLocation.workspace.getPath()+File.separator+"master"+File.separator+"workspace"), new File(CurrentLocation.workspace.getPath()+File.separator+parameter[0]+File.separator+"workspace"));
				new File(CurrentLocation.workspace.getPath()+File.separator+parameter[0]+File.separator+"commit").mkdir();
				return true;
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "유효하지 않은 인자입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public boolean cancelCommand(String[] parameter) {
		if(parameter==null)
		{
			CommandInputPane.allCommandtxt.setText("");
		}
		else
		{
			String dbranch = CurrentLocation.getBranchList().get(CurrentLocation.BranchList.size()-1);
			CurrentLocation.BranchList.remove(CurrentLocation.BranchList.size()-1);
			BranchFunction bf = new BranchFunction();
			bf.BranchListSave();
			FileOperation.deleteFile(new File(CurrentLocation.workspace.getPath()+File.separator+dbranch));
			new File(CurrentLocation.workspace.getPath()+File.separator+dbranch).delete();
		}
		return true;
	}

}
