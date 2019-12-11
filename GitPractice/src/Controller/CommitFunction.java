package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.CommitArray;
import Model.CurrentLocation;
import Model.FileOperation;

public class CommitFunction {
	
	//Commit List의 정보가 담긴 파일이 생성될 ComitArray.ini의 경로. 상대경로이며, 프로젝트의 root폴더로 설정되어있음.
	public String Path = CurrentLocation.workspace.getPath()+File.separator+ ".git" +File.separator+"CommitList.ini";
	
	//다른 기능에서도 쉽게 정보를 출력하거나 사용하기 위해 CommitList를 ArrayList로도 관리 
	public List<String> CMArray;
	public List<String> BranchArray;
	public List<String> AuthorNameArray;
	public List<String> AuthorAddressArray;
	public List<Integer> ChecksumArray;
	
	//실제로 Controller 내부에서 관리할 CommitList로 JSON을 사용.
	CommitArray JsonArray;
	
	int cnt;
	
	//클래스 선언시, 메소드에 사용될 리스트들을 초기화함.
	public CommitFunction() {
		CMArray = new ArrayList<String>();
		BranchArray = new ArrayList<String>();
		AuthorNameArray = new ArrayList<String>();
		AuthorAddressArray = new ArrayList<String>();
		ChecksumArray = new ArrayList<Integer>();
		JsonArray = new CommitArray();
		cnt =0;
		
	}
	
	//저장된 Commit List 파일을 여는 함수. 내용을 가져와 JSON Object화 한후, 필요한 정보를 ArrayList에 저장.
	//동시에 Object화 한 JSON 정보들을 Array로 저장하여 Model로 보냄. Commit 내용들의 관리의 형태는 JSON Array로 유지.
	public void commitListOpen() throws IOException, ParseException {
		
		if(!new File(Path).exists())
			this.commitListSave(Path);
		
		JSONParser parser = new JSONParser();
		
		//JSON Array로 파일에 저장, 및 불러오기를 위하여, JSON parser기능을 사용.
		try {
			FileReader fr= new FileReader(new File(Path));
			Object obj = parser.parse(fr);
			
			JSONArray jsonArray = (JSONArray)obj;
			Iterator<JSONObject> iterator =jsonArray.iterator();
			JsonArray.init();
			while(iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject)iterator.next();
				String content =String.valueOf(jsonObject.get("content"));
				String branch = String.valueOf(jsonObject.get("branch"));
				String Authorname = String.valueOf(jsonObject.get("AuthorName"));
				String Authoraddress=String.valueOf(jsonObject.get("AuthorAddress"));
				String checksum=String.valueOf(jsonObject.get("checksum"));
				if(content!=null&&content!="null")
					commitAdd(content,branch,Authorname,Authoraddress,Integer.parseInt(checksum));
			}
			fr.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//Commit 저장. 명령어 Controller에서는 직접 Model에 접속하는 것이 아닌, 이 명령어를 통해 접속.
	public void commitAdd(String content,String branch,String Authorname,String Authoraddress,int checksum) {
		CMArray.add(content);
		BranchArray.add(branch);
		AuthorNameArray.add(Authorname);
		AuthorAddressArray.add(Authoraddress);
		ChecksumArray.add(checksum);
		JsonArray.commit(content,branch,Authorname,Authoraddress,checksum);
	}
	
	//CommitList를 파일로 저장.
	public void commitListSave(String path) {
		try {
			FileWriter fw = new FileWriter(path);
			if(new File(Path).exists())
				new File(Path).delete();
			fw.write(CommitArray.Arrayreturn());
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	//add의 파일들을 새로운 커밋에 저장하는 메소드이다.
	public void workspaceCopy(File sourceF,File targetF) {
		File[] ff = sourceF.listFiles();
		String Filepath = targetF.getPath()+File.separator+(JsonArray.totalSize()-1);
		if(!new File(Filepath).exists()) {
			new File(Filepath).mkdir();
		}
		FileOperation.copyFileAll(sourceF, new File(Filepath));
	}
	
	public void reArrangeCommit() {
		JsonArray=new CommitArray();
		for(int i=0;i<ChecksumArray.size();i++)
		{
			int k=0;
			for(int j=1;j<ChecksumArray.size();j++)
			{
				if(ChecksumArray.get(k)>ChecksumArray.get(j))
					k=j;
			}
			System.out.println("yes");
			JsonArray.commit(CMArray.get(k), BranchArray.get(k), AuthorNameArray.get(k), AuthorAddressArray.get(k), ChecksumArray.get(k));
			ChecksumArray.remove(k);
			CMArray.remove(k);
			BranchArray.remove(k);
			AuthorAddressArray.remove(k);
			AuthorNameArray.remove(k);
		}
		JsonArray.commit(CMArray.get(0), BranchArray.get(0), AuthorNameArray.get(0), AuthorAddressArray.get(0), ChecksumArray.get(0));
		
		commitListSave(Path);

		CMArray = new ArrayList<String>();
		BranchArray = new ArrayList<String>();
		AuthorNameArray = new ArrayList<String>();
		AuthorAddressArray = new ArrayList<String>();
		ChecksumArray = new ArrayList<Integer>();
		JsonArray = new CommitArray();
		
		try {
			commitListOpen();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
