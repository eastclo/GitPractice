package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.json.simple.parser.ParseException;

import Model.CurrentLocation;
import Model.FileOperation;
import View.CommitInputDialog;

public class ExecutionCommit {
	
	CommitInputDialog comDialog;
	public boolean executeCommand(String[] parameter) {
		comDialog = new CommitInputDialog();

		JButton btn = comDialog.btn;
		btn.addActionListener(new btnclick());
		return true;
	}
	public void cancelCommand(String[] parameter) {
	}
	
	class btnclick implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String inputContent=comDialog.text.getText();
			comDialog.dispose();
			System.out.println(inputContent);
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

			FileOperation.makeBackup(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"));
			List<String> branchList = commit.BranchArray;
			List<Integer> check=new ArrayList<Integer>();
			for(int i=0;i<branchList.size();i++)
			{
				if(branchList.get(i).equals(CurrentLocation.getBranch()))
					check.add(i);
			}
			System.out.println(check);
			if(check.size()>1)
				commit.workspaceCopy(new File(CurrentLocation.workspace.getPath()+File.separator+CurrentLocation.getBranch()+File.separator+"commit"+File.separator+check.get(check.size()-2)), new File(CurrentLocation.workspace.getPath()+File.separator+CurrentLocation.getBranch()+File.separator+"commit"));
			FileOperation.deleteFile(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"));
			commit.commitListSave(commit.Path);
		}
	}

}
