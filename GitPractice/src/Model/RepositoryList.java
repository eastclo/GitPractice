package Model;

import java.io.File;

public class RepositoryList {
	private String repoList[];
	
	public RepositoryList() {
		String repoListPath = "." + File.separator + "GitHub";
		File f = new File(repoListPath);
		this.repoList = f.list();
	}
	
	public String[] getRepoList() {
		return repoList;
	}
}
