package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.CurrentLocation;
import View.CommandExplainDialog;
import View.CommandInputPane;

public class ExecutionBranch {
	public void executeCommand(String a, String b, String c) {
		List<String>branchList = new ArrayList<String>();
		branchList = CurrentLocation.getBranchList();
		boolean findswt = false;
		if(a==null)
		{
			String outputS = new String("////branch List////\n\n");
			for(int i=0;i<branchList.size();i++)
				outputS=outputS+branchList.get(i)+"\n";
			CommandInputPane.allCommandtxt.setText(outputS);
		}
		else
		{
			CurrentLocation.addBranch(a);
			BranchFunction bf = new BranchFunction();
			bf.BranchListSave();
			findswt=false;
		}
	}
	
	public void cancelCommand(String a, String b, String c) {
		System.out.println("Cancel git log --all --graph");
	}

}
