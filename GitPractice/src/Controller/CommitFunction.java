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
	
	String Path = "C:"+File.separator+"test"+File.separator+"CommitArray.ini";
	public List<Object> CMArray;
	CommitArray JsonArray;
	
	public CommitFunction() {
		CMArray = new ArrayList<Object>();
		JsonArray = new CommitArray();
	}
	
	public void commitListOpen() throws IOException, ParseException {
		File file = new File(Path);
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(file));
			
			JSONArray jsonArray = (JSONArray)obj;
			Iterator<JSONObject> iterator =jsonArray.iterator();
			
			while(iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject)iterator.next();
				int checksum = ((Long) jsonObject.get("checksum")).intValue();
				commitAdd(checksum);
				CMArray.add(checksum);
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void commitAdd(int checksum) {
		JsonArray.commit(checksum);
		
	}
	public void commitListSave() {
		try {
			FileWriter fw = new FileWriter(Path);
			fw.write(CommitArray.Arrayreturn());
			fw.flush();
			fw.close();
			System.out.println(CommitArray.Arrayreturn());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
