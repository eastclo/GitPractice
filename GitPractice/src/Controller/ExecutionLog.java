package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import Model.CurrentLocation;
import View.CommandInputPane;

public class ExecutionLog {
	public boolean executeCommand(String[] parameter) {
		if(parameter==null)
		{
			String logtext = new String();
			CommitFunction openCommit = new CommitFunction();
			try {
				openCommit.commitListOpen();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<String> content = new ArrayList<String>();
			List<String> Authorname=new ArrayList<String>();
			List<String> AuthorAddress=new ArrayList<String>();
			List<String> branchList = new ArrayList<String>();
			content = openCommit.CMArray;
			Authorname=openCommit.AuthorNameArray;
			AuthorAddress=openCommit.AuthorAddressArray;
			branchList=openCommit.BranchArray;
			int check=0;
			for(int i=0;i<branchList.size();i++)
			{
				if(branchList.get(i).equals(CurrentLocation.getBranch()))
				{
					check=i;
					break;
				}
			}
			for(int i=0;i<check;i++)
			{
				logtext = logtext+"commit " + Integer.toString(i) + "\n";
				logtext = logtext + "Author: "+Authorname.get(i) +" <"+AuthorAddress.get(i) +">\n";
				logtext = logtext+"\n" + "\t" + content.get(i)+"\n\n";
			}
			for(int i=check;i<content.size();i++)
			{
				if(branchList.get(i).equals(CurrentLocation.getBranch()))
				{
					logtext = logtext+"commit " + Integer.toString(i) + "\n";
					logtext = logtext + "Author: "+Authorname.get(i) +" <"+AuthorAddress.get(i) +">\n";
					logtext = logtext+"\n" + "\t" + content.get(i)+"\n\n";
				}
			}
			CommandInputPane.allCommandtxt.setText(logtext);
			return true;
		}
		else if(parameter.length==1)
		{
			boolean cnt=false;
			for(int i=0;i<CurrentLocation.getBranchList().size();i++)
			{
				if(parameter[0].equals(CurrentLocation.getBranchList().get(i)))
				{
					cnt=true;
					break;
				}
			}
			if(cnt==false)
			{
				JOptionPane.showMessageDialog(null, "유효하지 않은 인자입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
				
			String logtext = new String();
			CommitFunction openCommit = new CommitFunction();
			try {
				openCommit.commitListOpen();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<String> content = new ArrayList<String>();
			List<String> Authorname=new ArrayList<String>();
			List<String> AuthorAddress=new ArrayList<String>();
			List<String> branchList = new ArrayList<String>();
			content = openCommit.CMArray;
			Authorname=openCommit.AuthorNameArray;
			AuthorAddress=openCommit.AuthorAddressArray;
			branchList=openCommit.BranchArray;
			int check=0;
			for(int i=0;i<branchList.size();i++)
			{
				if(branchList.get(i).equals(parameter[0]))
				{
					check=i;
					break;
				}
			}
			for(int i=0;i<check;i++)
			{
				logtext = logtext+"commit " + Integer.toString(i) + "\n";
				logtext = logtext + "Author: "+Authorname.get(i) +" <"+AuthorAddress.get(i) +">\n";
				logtext = logtext+"\n" + "\t" + content.get(i)+"\n\n";
			}
			for(int i=check;i<content.size();i++)
			{
				if(branchList.get(i).equals(parameter[0]))
				{
					logtext = logtext+"commit " + Integer.toString(i) + "\n";
					logtext = logtext + "Author: "+Authorname.get(i) +" <"+AuthorAddress.get(i) +">\n";
					logtext = logtext+"\n" + "\t" + content.get(i)+"\n\n";
				}
			}
			CommandInputPane.allCommandtxt.setText(logtext);
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "유효하지 않은 인자입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public boolean cancelCommand(String[] parameter) {
		CommandInputPane.allCommandtxt.setText("");
		return true;
	}

}
