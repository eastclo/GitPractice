package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentLocation {
	private static String repo;
	private static String branch;
	public static File workspace = new File("."+File.separator+"root");
	public static List<String> BranchList=new ArrayList<String>();
	public static Map<String,String> RemoteList = new HashMap<String,String>(); //key에 브랜치에서 지정한 리모트 이름, value에 실제 저장소 이름
	public static String AuthorName = "user";
	public static String AuthorAddress = "user@example.com";
	public static int HEADLocation;
	public final static int REPO_NAME_SAME = -1, REPO_URL_NULL = 0, REPO_ADDED = 1;	//리스트에 이름이 존재할 경우, 해당 url이 존재하지 않는 경우, add에 성공한 경우
	
	public static void changeRepo(String targetRepo) {
		if(targetRepo != null)
			repo = new String(targetRepo);
		else
			repo = null;
	}
	
	public static void changeBranch(String targetBranch) {
		branch = new String(targetBranch);
	}
	
	public static boolean addBranch(String targetBranch) {
		boolean findswt=false;
		for(String s : BranchList) {
			if(s.equals(targetBranch))
			{
				findswt=true;
				break;
			}
		}
		if(findswt==false)
		{
			BranchList.add(targetBranch);
			return true;
		}
		return false;
	}
	
	public static int addRemote(String targetRemote, String targetRepoUrl) {
		if(RemoteList.get(targetRemote)!=null)	//repo 리스트에 이미 같은 이름으로 존재할경우
			return REPO_NAME_SAME;
		
		Model.RepositoryList repo = new Model.RepositoryList();
		String[] repoList = repo.getRepoList();	//원격저장소 리스트 가져오기.
		String repoUrl;	//각 레포지토리의 url
		boolean check = false;	//targetRepoUrl과 동일한 url이 존재하면 true
		
		for(String repoName : repoList) {
			repoUrl = Model.FileOperation.getFileReadData("."+File.separator+"GitHub"+File.separator + repoName, "address.txt");
			if(repoUrl.equals(targetRepoUrl)) {	//url이 존재하면
				targetRepoUrl = repoName;		//해당 저장소의 이름을 저장
				check = true; break;
			}
		}
		
		if(check) {
			RemoteList.put(targetRemote,targetRepoUrl);
			return REPO_ADDED;
		} else
			return REPO_URL_NULL;	//해당 URL이 존재하지 않을 경우
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
	public static void setRemoteList(Map<String,String> remoteList) {
		RemoteList=remoteList;
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
	
	//리모트 저장소가 저장된 리스트 리턴
	public static Map<String,String> getRemoteList(){
		return RemoteList;
	}
}