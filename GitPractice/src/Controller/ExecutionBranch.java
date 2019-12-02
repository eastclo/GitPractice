package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Model.CurrentLocation;
import View.CommandExplainDialog;
import View.CommandInputPane;

public class ExecutionBranch {
	public boolean executeCommand(String[] parameter) {
		List<String>branchList = new ArrayList<String>();
		branchList = CurrentLocation.getBranchList();
		if(parameter==null)
		{
			String outputS = new String("////branch List////\n\n");
			for(int i=0;i<branchList.size();i++)
				outputS=outputS+branchList.get(i)+"\n";
			CommandInputPane.allCommandtxt.setText(outputS);
			return true;
		}
		else if(parameter.length==1)
		{
			CurrentLocation.addBranch(parameter[0]);
			BranchFunction bf = new BranchFunction();
			bf.BranchListSave();
			new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+parameter[0]).mkdir();
			new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+parameter[0]+File.separator+"workspace").mkdir();
			new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+parameter[0]+File.separator+"add").mkdir();
			new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+parameter[0]+File.separator+"commit").mkdir();
			return true;
		}
		else
			return false;
	}
	
	public boolean cancelCommand(String[] parameter) {
		return true;
	}

}
