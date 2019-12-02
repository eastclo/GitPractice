package Model;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.ItemList;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;

//JSON형태로 Commit Array를 관리하는 Model
//받아온 정보를 Array에 저장하는 기능을 함.
public class CommitArray {
	static JSONArray commitArray;
	private JSONArray json;
	public String repoKey = "repoName";	//json에 key값으로 들어갈 "repoName"
	public String workspaceKey = "workspaceName"; //json에 key값으로 들어갈 "workspaceName"
	public String existKey = "exist";
	public String remoteKey = "remote"; 
	public String urlKey = "url"; 
	
	public CommitArray() {
		commitArray = new JSONArray();
		json = new JSONArray();
	}
	
	public void commit(String content,String branch,String AuthorName,String AuthorAddress) {
		JSONObject jsonOb=new JSONObject();
		jsonOb.put("content", content);
		jsonOb.put("branch",branch);
		jsonOb.put("AuthorName", AuthorName);
		jsonOb.put("AuthorAddress", AuthorAddress);
		commitArray.add(jsonOb);
	}
	
	public String backupClone(String clonedRepo, String currWorkspace, boolean exist) {
		JSONObject jsonOb = new JSONObject();
		jsonOb.put(repoKey, clonedRepo);
		jsonOb.put(workspaceKey, currWorkspace);
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

	public void init() {
		commitArray = new JSONArray();
	}
	

}
