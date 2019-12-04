package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import Model.CurrentLocation;

public class ExecutionCheckout {
	static List<File> dfile=new ArrayList<File>();
	static List<String> dcheck = new ArrayList<String>();
	public boolean executeCommand(String[] parameter) {
		if(parameter[0]!=null)
		{
			if(dcheck.size()==0)
			{
				dfile.add(CurrentLocation.workspace);
				dcheck.add(CurrentLocation.getBranch());
			}
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
				dfile.add(CurrentLocation.workspace);
				dcheck.add(CurrentLocation.getBranch());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "존재하지 않는 브랜치입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
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
		dfile.remove(dfile.size()-1);
		File gitpath = dfile.get(dfile.size()-1);
		CurrentLocation.workspace=gitpath;
		if(new File(CurrentLocation.workspace.getPath()+File.separator+".git").exists())
		{
			BranchFunction bf = new BranchFunction();
			CurrentLocation.changeBranch("master");
			bf.BranchListOpen();
			try {
				new CommitFunction().commitListOpen();
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		}
		dcheck.remove(dcheck.size()-1);
		String deletecheck = dcheck.get(dcheck.size()-1);
		System.out.println(deletecheck);
		CurrentLocation.changeBranch(deletecheck);
		return true;
	}

}
