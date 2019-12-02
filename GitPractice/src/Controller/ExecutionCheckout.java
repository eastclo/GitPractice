package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.CurrentLocation;

public class ExecutionCheckout {
	public boolean executeCommand(String[] parameter) {
		if(parameter[0]!=null)
		{
			List<String> branchList = new ArrayList<String>();
			branchList=CurrentLocation.getBranchList();
			boolean findswt = false;
			for(String s : branchList) {
				if(s.equals(parameter[0]))
				{
					findswt=true;
					break;
				}
			}
			if(findswt==true)
			{
				CurrentLocation.changeBranch(parameter[0]);
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "존재하지 않는 브랜치입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		else
			return false;
	}
	public void cancelCommand(String[] parameter) {
		System.out.println("Cancel git log --all");
	}

}
