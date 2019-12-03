package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CurrentLocation {
	private static String repo;
	private static String branch;
	public static File workspace = new File("."+File.separator+"root");
	public static List<String> BranchList=new ArrayList<String>();
	public static String AuthorName = "user";
	public static String AuthorAddress = "user@example.com";
	
	public static void changeRepo(String targetRepo) {
		if(targetRepo != null)
			repo = new String(targetRepo);
		else
			repo = null;
	}
	
	public static void changeBranch(String targetBranch) {
		branch = new String(targetBranch);
	}
	
	public static void addBranch(String targetBranch) {
		boolean findswt=false;
		for(String s : BranchList) {
			if(s.equals(targetBranch))
			{
				findswt=true;
				break;
			}
		}
		if(findswt==false)
			BranchList.add(targetBranch);
	}
	public static void changeAuthorname(String name) {
		AuthorName=new String(name);
	}
	public static void changeAuthorAddress(String address) {
		AuthorAddress=new String(address);
	}
	
	public static void setBranchList(List<String> branchList) {
		BranchList=branchList;
	}
	
	public static String getRepo() {
		return repo;
	}
	
	public static String getBranch() {
		return branch;
	}
	
	public static List<String> getBranchList() {
		return BranchList;
	}
}