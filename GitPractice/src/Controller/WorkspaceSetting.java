package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.CurrentLocation;
import View.CommandInputPane;

public class WorkspaceSetting {
	
	public static JSONArray workspaceArray;
	public static List<String> workspaceList;
	String Path = "."+File.separator+"workspaceList.ini";
	
	public WorkspaceSetting() {
		workspaceArray=new JSONArray();
		workspaceList=new ArrayList<String>();
		if(new File(Path).exists())
			workspaceOpen();
		workspaceSave(CurrentLocation.workspace);
			
	}
	private void workspaceSave(File workspace) {
		JSONArray workspaceJList = new JSONArray();
		if(workspaceList!=null)
		{
			for(int i=0;i<workspaceList.size();i++)
			{
				JSONObject inputJ=new JSONObject();
				inputJ.put("path", workspaceList.get(i));
				workspaceJList.add(inputJ);
			}
		}
		boolean findswt=false;
		for(String s : workspaceList) {
			if(s.equals(workspace.getPath()))
			{
				findswt=true;
				break;
			}
		}
		if(findswt==false)
		{
			JSONObject inputJ=new JSONObject();
			inputJ.put("path", workspace.getPath());
			workspaceJList.add(inputJ);
			workspaceList.add(workspace.getPath());
		}
		try {
			FileWriter fw = new FileWriter(Path);
			if(new File(Path).exists())
				new File(Path).delete();
			fw.write(workspaceJList.toString());
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		workspaceArray=workspaceJList;
	}
	private void workspaceOpen() {
		System.out.println("open");
		JSONParser parser = new JSONParser();
		
		//JSON Array로 파일에 저장, 및 불러오기를 위하여, JSON parser기능을 사용.
		try {
			FileReader fr= new FileReader(new File(Path));
			Object obj;
			obj = parser.parse(fr);
			
			JSONArray jsonArray = (JSONArray)obj;
			Iterator<JSONObject> iterator =jsonArray.iterator();
			while(iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject)iterator.next();
				String wspath = String.valueOf(jsonObject.get("path"));
				workspaceArray.add(jsonObject);
				workspaceAdd(wspath);
			}
			fr.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public void workspaceAdd(String workspace) {

		boolean findswt=false;
		for(String s : workspaceList) {
			if(s.equals(workspace))
			{
				findswt=true;
				break;
			}
		}
		if(findswt==false)
			workspaceList.add(workspace);
	}
	public boolean workspaceExist() {
		if(new File("."+File.separator+"workspaceList.ini").exists())
			return true;
		else
			return false;
	}
	public static void initList() {
		workspaceArray=new JSONArray();
		workspaceList=new ArrayList<String>();
	}
	public static void settingComboBox(String filePath) {
		new WorkspaceSetting();
		JComboBox cb = CommandInputPane.getComboBox();
		for(int i=0;i<workspaceList.size();i++)
			cb.removeItem(new String(workspaceList.get(i)));
		CurrentLocation.workspace=new File(filePath);
		new WorkspaceSetting();
		for(int i=0;i<workspaceList.size();i++)
			cb.addItem(new String(workspaceList.get(i)));
	}
	public static void deleteComboBox(String filePath) {
		JComboBox cb = CommandInputPane.getComboBox();
		new WorkspaceSetting();
		for(int i=0;i<workspaceList.size();i++)
			cb.removeItem(new String(workspaceList.get(i)));
		CurrentLocation.workspace=new File("."+File.separator+"root");
		JSONObject inputJ=new JSONObject();
		inputJ.put("path", filePath);
		WorkspaceSetting ws=new WorkspaceSetting();
		workspaceArray.remove(inputJ);
		workspaceList.remove(new String(filePath));
		ws.workspaceSave(new File("."+File.separator+"root"));
		System.out.println(workspaceArray);
		System.out.println(workspaceList);
		for(int i=0;i<workspaceList.size();i++)
			cb.addItem(new String(workspaceList.get(i)));
	}

}
