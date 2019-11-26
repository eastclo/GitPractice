package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import org.json.simple.parser.ParseException;

import View.CommitInputDialog;

public class ExecutionCommit {
	
	CommitInputDialog comDialog;
	public void executeCommand() {
		comDialog = new CommitInputDialog();

		JButton btn = comDialog.btn;
		btn.addActionListener(new btnclick());
		
	}
	public void cancelCommand() {
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
			commit.commitAdd(inputContent,ManagementSetting.currentBranch);
			commit.commitListSave();
		}
	}

}
