package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import org.json.simple.parser.ParseException;

import Model.CurrentLocation;
import View.CommitInputDialog;

public class ExecutionCommit {
	
	CommitInputDialog comDialog;
	public void executeCommand(String a,String b,String c) {
		comDialog = new CommitInputDialog();

		JButton btn = comDialog.btn;
		btn.addActionListener(new btnclick());
		
	}
	public void cancelCommand(String a,String b,String c) {
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
			commit.commitListSave(commit.Path);
		}
	}

}
