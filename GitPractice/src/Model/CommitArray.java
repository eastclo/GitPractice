package Model;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Component;

//JSON형태로 Commit Array를 관리하는 Model
//받아온 정보를 Array에 저장하는 기능을 함.
public class CommitArray {
	static JSONArray commitArray;
	
	public CommitArray() {
		commitArray = new JSONArray();
	}
	
	public void commit(int checksum) {
		JSONObject jsonOb=new JSONObject();
		jsonOb.put("checksum", checksum);
		commitArray.add(jsonOb);
		
	}
	public static String Arrayreturn() {
		return commitArray.toString();
	}
	

}
