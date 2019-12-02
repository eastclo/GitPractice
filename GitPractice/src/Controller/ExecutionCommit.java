package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;

import org.json.simple.parser.ParseException;

import Model.CurrentLocation;
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
		System.out.println("Cancel git log --all");
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
			commit.workspaceCopy(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+CurrentLocation.getBranch()+File.separator+"add"),new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+CurrentLocation.getBranch()+File.separator+"commit"));
			
			commit.commitListSave(commit.Path);
		}
	}

}
