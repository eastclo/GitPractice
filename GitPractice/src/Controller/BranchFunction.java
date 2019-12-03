package Controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Model.CurrentLocation;

public class BranchFunction {
	String Path = CurrentLocation.workspace.getPath()+File.separator+ ".git"+File.separator+"BranchList.ini";
	
	public List<String> branchArray;
	
	public BranchFunction() {
		branchArray=new ArrayList<String>();
	}
	
	public void BranchListOpen() {
		refreshPath();
		
		JSONParser parser = new JSONParser();
		branchArray=new ArrayList<String>();
		
		try {
			FileReader fr = new FileReader(new File(Path));
			Object obj = parser.parse(fr);
			
			JSONArray jsonArray=(JSONArray)obj;
			Iterator<JSONObject>iterator = jsonArray.iterator();
			
			while(iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject)iterator.next();
				String branch = String.valueOf(jsonObject.get("branch"));
				if(branch!=null&&branch!="null")
					branchArray.add(branch);
			}
			fr.close();
		}catch(Exception e) {
		}
		CurrentLocation.setBranchList(branchArray);
	}
	
	public void setArray(List<String> inputArray)
	{
		branchArray=inputArray;
	}

	public void BranchListSave() {
		setArray(CurrentLocation.getBranchList());
		JSONArray branchArrayS = new JSONArray();
		for(int i=0;i<branchArray.size();i++)
		{
			JSONObject inputJ=new JSONObject();
			inputJ.put("branch", branchArray.get(i));
			branchArrayS.add(inputJ);
		}
		try {
			refreshPath();
			FileWriter fw = new FileWriter(Path);
			if(new File(Path).exists())
				new File(Path).delete();
			fw.write(branchArrayS.toString());
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void refreshPath() {
		Path = CurrentLocation.workspace.getPath()+File.separator+ ".git"+File.separator+"BranchList.ini";
	}

}
