package Model;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.ItemList;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//JSON형태로 Commit Array를 관리하는 Model
//받아온 정보를 Array에 저장하는 기능을 함.
public class CommitArray {
	static JSONArray commitArray;
	static List<String> contentC;
	static List<String> branchC;
	static List<String> AuthorNameC;
	static List<String> AuthorAddressC;
	
	public CommitArray() {
		commitArray = new JSONArray();
		contentC=new ArrayList<String>();
		branchC=new ArrayList<String>();
		AuthorNameC=new ArrayList<String>();
		AuthorAddressC=new ArrayList<String>();
		json = new JSONArray();
	}
	private JSONArray json;
	public String repoKey = "repoName";	//json에 key값으로 들어갈 "repoName"
	public String currworkspaceKey = "currworkspaceName"; //json에 key값으로 들어갈 "workspaceName"
	public String clonedworkspaceKey = "clonedworkspaceName";
	public String existKey = "exist";
	public String remoteKey = "remote"; 
	public String urlKey = "url"; 
	public String branchKey = "branch"; 

	//commit을 추가하는 메소드이다. 들어가는 인자로는 커밋내용(content),브랜치,저자이름,저자주소 가 있다.
	public void commit(String content,String branch,String AuthorName,String AuthorAddress,int checksum) {
		JSONObject jsonOb=new JSONObject();
		jsonOb.put("content", content);
		jsonOb.put("branch",branch);
		jsonOb.put("AuthorName", AuthorName);
		jsonOb.put("AuthorAddress", AuthorAddress);
		jsonOb.put("checksum", Integer.toString(checksum));
		commitArray.add(jsonOb);
		contentC.add(content);
		branchC.add(branch);
		AuthorNameC.add(AuthorName);
		AuthorAddressC.add(AuthorAddress);
	}
	
	public String backupClone(String clonedRepo, String currWorkspace, String clonedWorkspace , String currBranch , boolean exist) {
		JSONObject jsonOb = new JSONObject();
		jsonOb.put(repoKey, clonedRepo);
		jsonOb.put(currworkspaceKey, currWorkspace);
		jsonOb.put(clonedworkspaceKey, clonedWorkspace);
		jsonOb.put(branchKey,currBranch);
		jsonOb.put(existKey,exist);
		json.add(jsonOb);
		return json.toString();
	}
	
	public String clone(String url) {
		JSONObject jsonOb = new JSONObject();
		jsonOb.put(remoteKey, "origin");
		jsonOb.put(urlKey,url);
		json.add(jsonOb);
		return json.toString();
	}
	
	public static String Arrayreturn() {
		return commitArray.toString();
	}
	//CommitList에서 해당 branch의 가장 최근 checksum을 찾는다.(checksum의 기준은, commit의 수이다.)
	public int ArrayCountreturn(String branch) {
		int cnt=0;
		JSONObject jsonOb=new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			Object obj= parser.parse(commitArray.toString());
			JSONArray ja = commitArray;
			Iterator<JSONObject> iterator =ja.iterator();
			while(iterator.hasNext()) {
				jsonOb = iterator.next();
				String str=(String) jsonOb.get("branch");
				if(str.equals(branch))
					cnt++;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	public List<String> returnContent(){
		return contentC;
	}
	public List<String> returnbranchC(){
		return branchC;
	}
	public List<String> returnAuthorName(){
		return AuthorNameC;
	}
	public List<String> returnAuthorAddress(){
		return AuthorAddressC;
	}
	
	public int totalSize() {
		return commitArray.size();
	}
	public void init() {
		commitArray = new JSONArray();
	}
	

}
