package Model;

public class CurrentLocation {
	private static String repo;
	private static String branch;
	
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