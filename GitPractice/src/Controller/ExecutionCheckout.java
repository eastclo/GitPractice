package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.CurrentLocation;

public class ExecutionCheckout {
	public void executeCommand(String a, String b, String c) {
		if(a!=null)
		{
			List<String> branchList = new ArrayList<String>();
			branchList=CurrentLocation.getBranchList();
			boolean findswt = false;
			for(String s : branchList) {
				if(s.equals(a))
				{
					findswt=true;
					break;
				}
			}
			if(findswt==true)
				CurrentLocation.changeBranch(a);
			else
				JOptionPane.showMessageDialog(null, "존재하지 않는 브랜치입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void cancelCommand(String a, String b, String c) {
		System.out.println("Cancel git log --all");
	}

}
