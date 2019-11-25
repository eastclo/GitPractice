package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.CommitArray;

public class CommitFunction {
	
	//Commit List의 정보가 담긴 파일이 생성될 ComitArray.ini의 경로. 상대경로이며, 프로젝트의 root폴더로 설정되어있음.
	String Path = "."+"CommitArray.ini";
	
	//다른 기능에서도 쉽게 정보를 출력하거나 사용하기 위해 CommitList를 ArrayList로도 관리 
	public List<Object> CMArray;
	
	//실제로 Controller 내부에서 관리할 CommitList로 JSON을 사용.
	CommitArray JsonArray;
	
	public CommitFunction() {
		CMArray = new ArrayList<Object>();
		JsonArray = new CommitArray();
	}
	
	//저장된 Commit List 파일을 여는 함수. 내용을 가져와 JSON Object화 한후, 필요한 정보를 ArrayList에 저장.
	//동시에 Object화 한 JSON 정보들을 Array로 저장하여 Model로 보냄. Commit 내용들의 관리의 형태는 JSON Array로 유지.
	public void commitListOpen() throws IOException, ParseException {
		File file = new File(Path);
		JSONParser parser = new JSONParser();
		
		//JSON Array로 파일에 저장, 및 불러오기를 위하여, JSON parser기능을 사용.
		try {
			Object obj = parser.parse(new FileReader(file));
			
			JSONArray jsonArray = (JSONArray)obj;
			Iterator<JSONObject> iterator =jsonArray.iterator();
			
			while(iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject)iterator.next();
				int checksum = ((Long) jsonObject.get("checksum")).intValue();
				commitAdd(checksum);
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//Commit 저장. 명령어 Controller에서는 직접 Model에 접속하는 것이 아닌, 이 명령어를 통해 접속.
	public void commitAdd(int checksum) {
		CMArray.add(checksum);
		JsonArray.commit(checksum);
		
	}
	//CommitList를 파일로 저장.
	public void commitListSave() {
		try {
			FileWriter fw = new FileWriter(Path);
			fw.write(CommitArray.Arrayreturn());
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
