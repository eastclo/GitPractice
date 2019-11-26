package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CurrentLocation {
	private static String repo;
	private static String branch;
	public static File workspace=new File(".");
	public static List<String> BranchList=new ArrayList<String>();
	
	public static void changeRepo(String targetRepo) {
		repo = new String(targetRepo);
	}
	
	public static void changeBranch(String targetBranch) {
		branch = new String(targetBranch);
	}
	
	public static String getRepo() {
		return repo;
	}
	
	public static String getBranch() {
		return branch;
	}
}