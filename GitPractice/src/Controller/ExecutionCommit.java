package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.json.simple.parser.ParseException;

import Model.CommitArray;
import Model.CurrentLocation;
import Model.FileOperation;
import View.CommitInputDialog;

public class ExecutionCommit {
	
	CommitInputDialog comDialog;
	
	static List<File> dfile=new ArrayList<File>();
	
	public boolean executeCommand(String[] parameter) {
		dfile.add(CurrentLocation.workspace);
		comDialog = new CommitInputDialog();

		JButton btn = comDialog.btn;
		FileOperation.makeBackup(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"));
		btn.addActionListener(new btnclick());
		return true;
	}
	public boolean cancelCommand(String[] parameter) {
		CurrentLocation.workspace = dfile.get(dfile.size()-1);
		dfile.remove(dfile.size()-1);
		FileOperation.loadBackup(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"));
		CommitFunction cm = new CommitFunction();
		try {
			cm.commitListOpen();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cm.CMArray.remove(cm.CMArray.size()-1);
		cm.BranchArray.remove(cm.BranchArray.size()-1);
		cm.AuthorNameArray.remove(cm.AuthorNameArray.size()-1);
		cm.AuthorAddressArray.remove(cm.AuthorAddressArray.size()-1);
		CommitArray ca = new CommitArray();
		ca.init();
		for(int i=0;i<cm.CMArray.size();i++)
			ca.commit(cm.CMArray.get(i), cm.BranchArray.get(i), cm.AuthorNameArray.get(i), cm.AuthorAddressArray.get(i));
		cm.commitListSave(CurrentLocation.workspace.getPath()+File.separator+ ".git" +File.separator+"CommitList.ini");
		return true;
	}
	
	class btnclick implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String inputContent=comDialog.text.getText();
			comDialog.dispose();
			CommitFunction commit = new CommitFunction();
			try {
				commit.commitListOpen();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			commit.commitAdd(inputContent,CurrentLocation.getBranch(),CurrentLocation.AuthorName,CurrentLocation.AuthorAddress);
			commit.workspaceCopy(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"),new File(CurrentLocation.workspace.getPath()+File.separator+CurrentLocation.getBranch()+File.separator+"commit"));

			List<String> branchList = commit.BranchArray;
			List<Integer> check=new ArrayList<Integer>();
			for(int i=0;i<branchList.size();i++)
			{
				if(branchList.get(i).equals(CurrentLocation.getBranch()))
					check.add(i);
			}
			if(check.size()>1)
				commit.workspaceCopy(new File(CurrentLocation.workspace.getPath()+File.separator+CurrentLocation.getBranch()+File.separator+"commit"+File.separator+check.get(check.size()-2)), new File(CurrentLocation.workspace.getPath()+File.separator+CurrentLocation.getBranch()+File.separator+"commit"));
			FileOperation.deleteFile(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"));
			commit.commitListSave(commit.Path);
		}
	}

}
