package Controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Model.CurrentLocation;

public class RemoteFunction {
	
	//.git 폴더 내부의 remoteList 파일 경로
	String path =  CurrentLocation.workspace.getPath()+File.separator+ ".git"+File.separator+"RemoteList.ini";

	public Map<String,String> remoteList;
	
	public RemoteFunction() {
		remoteList = new HashMap<String,String>();
	}
	
	//RemoteList를 RemoteList.ini에서 읽어와 갱신해 주는 메소드
	public void remoteListOpen() {
		//실행 전, workspace의 경로가 바뀌어있을 수도 있으므로, 경로를 최신화한다.
		refreshPath();
		
		//RemoteList.ini 파일을 열어서 remoteList에 저장
		JSONParser parser = new JSONParser();
		try {
			FileReader fr = new FileReader(new File(path));
			Object obj = parser.parse(fr);
			
			JSONArray jsonArray=(JSONArray)obj;
			Iterator<JSONObject>iterator = jsonArray.iterator();
			String key, value;
			
			while(iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject)iterator.next();
				key = String.valueOf(jsonObject.get("key"));
				value = String.valueOf(jsonObject.get("value"));
				
				remoteList.put(key, value);
			}
			fr.close();
		}catch(Exception e) {}
		
		Model.CurrentLocation.setRemoteList(remoteList);	//현재 리모트 리스트 갱신
	}
	
	//RemoteList를 RemoteList.ini에 갱신해 주는 메소드
	public void remoteListSave() {
		remoteList = Model.CurrentLocation.getRemoteList();
		Set set = remoteList.entrySet();
		Iterator repoIt = set.iterator();
		
		JSONArray remoteArray = new JSONArray();
		while(repoIt.hasNext()) {
			Map.Entry entry = (Map.Entry)repoIt.next();
			JSONObject inputJ = new JSONObject();
			inputJ.put("key",(String)entry.getKey());
			inputJ.put("value", (String)entry.getValue());
			remoteArray.add(inputJ);
		}
		
		try {
			//실행 전, workspace의 경로가 바뀌어있을 수도 있으므로, 경로를 최신화한다.
			refreshPath();
			
			//Remote들의 List를 파일화하여 저장한다.
			FileWriter fw = new FileWriter(path);
			if(new File(path).exists())
				new File(path).delete();
			fw.write(remoteArray.toString());
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void refreshPath() {
		path =  CurrentLocation.workspace.getPath()+File.separator+ ".git"+File.separator+"RemoteList.ini";
	}
}
