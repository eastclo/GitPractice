package Model;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Component;

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
