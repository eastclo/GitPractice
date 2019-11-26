package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import View.CommandInputPane;

public class ExecutionLog {
	public void executeCommand(String a, String b, String c) {
		if(a==null)
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
			content = openCommit.CMArray;
			Authorname=openCommit.AuthorNameArray;
			AuthorAddress=openCommit.AuthorAddressArray;
			System.out.println(content.get(0));
			for(int i=0;i<content.size();i++)
			{
				logtext = logtext+"commit " + Integer.toString(i) + "\n";
				System.out.println("here2");
				logtext = logtext + "Author: "+Authorname.get(i) +" <"+AuthorAddress.get(i) +">\n";
				System.out.println("here3");
				logtext = logtext+"\n" + "\t" + content.get(i)+"\n\n";
				System.out.println("here4");
			}
			CommandInputPane.allCommandtxt.setText(logtext);
		
		}
	}
	public void cancelCommand(String a, String b, String c) {
		System.out.println("Cancel git log --all");
	}

}
