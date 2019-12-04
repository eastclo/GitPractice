package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.CommitArray;
import Model.CurrentLocation;
import Model.FileOperation;

public class ExecutionGitClone {
	private JComboBox repoComboBox = View.CommandInputPane.getComboBox();	//View에서 콤보박스 가져오기
	private DefaultComboBoxModel comboModel;
	private String sep = File.separator;
	private File currWorkspace;
	private File clonedWorkspace;
	
	public boolean executeCommand(String[] parameter) {
		if(parameter.length == 1 || parameter.length == 2) {
			//레포지토리 리스트 가져오기
			Model.RepositoryList tmp = new Model.RepositoryList();
			String[] repoList = tmp.getRepoList();
			int index = -1;
			for(int i = 0; i < repoList.length; i++) {
				String path = new String("." + sep + "GitHub" + sep + repoList[i]);
				if (parameter[0].equals(Model.FileOperation.getFileReadData(path, "address.txt"))) {
					index = i; break;
				}
			}
			if(index == -1) {
				JOptionPane.showMessageDialog(null, "잘못된 주소입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			currWorkspace = Model.CurrentLocation.workspace;
							
			/*currWorkspace에 master브랜치, .git폴더를 만들고 .git폴더에 RemoteList.ini 만들기*/
			if(parameter.length == 2) {	//파라미터가 있을경우 파라미터 이름으로, 없을 경우 저장소 이름으로 만듬.
				clonedWorkspace = new File(currWorkspace, parameter[1]);
			} else
				clonedWorkspace = new File(currWorkspace, repoList[index]);
			
			//폴더가 존재하지 않거나, 존재하는데 내부가 비어있는지 체크.
			if(clonedWorkspace.exists()) {
				if(clonedWorkspace.list() != null) {	//존재하는데 내부가 비어있지 않으면 false
					JOptionPane.showMessageDialog(null, "이미 존재하는 디렉터리입니다.", "중복 파일", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} else {
				clonedWorkspace.mkdir();
			}
			/*원격저장소에 master 브랜치 만들기*/
			File masterFolder = new File("." + sep + "GitHub" + sep + repoList[index], "master");
			if(!masterFolder.exists()) { //master 폴더가 존재하지 않으면 새로 만든다.(clone이 한 번도 되지 않았을 경우)
				masterFolder.mkdir();
				//백업
				makeBackup(repoList,index,currWorkspace.getPath(),clonedWorkspace.getPath(),false);
			}
			else
				makeBackup(repoList,index, currWorkspace.getPath(), clonedWorkspace.getPath(),true);
			
			//현재 저장소 위치 수정
			Model.CurrentLocation.workspace = clonedWorkspace;
			
			//깃 폴더와 마스터브랜치 폴더 생성
			File masterBranch = new File(clonedWorkspace,"master");
			File gitFolder = new File(clonedWorkspace,".git");
			masterBranch.mkdir();
			gitFolder.mkdir();
			File workspace = new File(masterBranch, "workspace");
			File add = new File(gitFolder, "add");
			File commit = new File(masterBranch, "commit");
			workspace.mkdir(); add.mkdir(); commit.mkdir();
			//RemoteList.ini 생성
			makeRemoteList(gitFolder, parameter[0]);
			//BranchList.ini 생성, 현재 브랜치 위치 수정
			makeBranchList();
			
			//저장소 선택 추가
			WorkspaceSetting.settingComboBox(clonedWorkspace.getPath());
			repoComboBox.setSelectedItem(clonedWorkspace.getPath());

			
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public boolean cancelCommand(String[] parameter) throws IOException, ParseException {		
		//clone하기 전에 가지고 있던 레포지토리 값과 콤보박스에 추가한 값을 가져와야한다.
		String backupPath = Model.CommandStack.loadBackup();
		File backupFolder = new File(backupPath);
		
		//backup을 읽음.
		JSONParser parser = new JSONParser();
		CommitArray json = new CommitArray();
		String repoName = null, currworkspaceName = null, clonedworkspaceName = null, exist = null, branch = null;
		try {
			FileReader rd = new FileReader(new File(backupPath+File.separator+"backup.ini"));
			Object obj = parser.parse(rd);
			JSONArray jsonArray = (JSONArray)obj;
			Iterator<JSONObject> iterator =jsonArray.iterator();
			
			JSONObject jsonObject = (JSONObject)iterator.next();
			repoName = String.valueOf(jsonObject.get(json.repoKey));
			currworkspaceName = String.valueOf(jsonObject.get(json.currworkspaceKey));
			clonedworkspaceName = String.valueOf(jsonObject.get(json.clonedworkspaceKey));
			exist = String.valueOf(jsonObject.get(json.existKey));
			branch = String.valueOf(jsonObject.get(json.branchKey));
			rd.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (exist.equals("false")) {
			File remote = new File("."+File.separator+"GitHub"+File.separator+repoName+File.separator+"master");
			Model.FileOperation.deleteFile(remote);
			remote.delete();
		}
		File clonedworkspace = new File(clonedworkspaceName);
		Model.FileOperation.deleteFile(clonedworkspace);
		clonedworkspace.delete();
		
		Model.FileOperation.deleteFile(backupFolder);
		backupFolder.delete();
				
		//위치 값 바꾸기
		Model.CurrentLocation.changeBranch(branch);
		Model.CurrentLocation.workspace = new File(currworkspaceName);
		
    //콤보박스 바꾸기
		WorkspaceSetting.deleteComboBox(clonedworkspaceName);
		repoComboBox.setSelectedItem(currworkspaceName);

		return true;
	}
	
	public void makeBranchList() {
		BranchFunction bf = new BranchFunction();
		CurrentLocation.changeBranch("master");
		CurrentLocation.BranchList = new ArrayList<String>();
		CurrentLocation.addBranch("master");
		List<String> branchList = CurrentLocation.getBranchList();
		bf.setArray(branchList);
		bf.BranchListSave();
	}
	
	public void makeRemoteList(File gitFolder, String url) {
		String json;
		CommitArray JSONArray = new CommitArray();
		json = JSONArray.clone(url);
		try {
			FileWriter fw = new FileWriter(gitFolder.getPath()+File.separator+"RemoteList.ini");
			fw.write(json);
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeBackup(String[] repoList, int index, String currWorkspace, String clonedWorkspace , boolean exist) {
		/* clone한 저장소, 현재  workspace의 이름으로 된 폴더 생성
		 * 나중에 
		 * */
		String backupPath = Model.CommandStack.createBackup();
		File backupFolder = new File(backupPath);
		backupFolder.mkdir();

		String json;
		CommitArray JSONArray = new CommitArray();
		//clone한 레포지토리 이름과, clone한 로컬저장소의 위치 리턴
		json = JSONArray.backupClone(repoList[index],currWorkspace,clonedWorkspace,Model.CurrentLocation.getBranch(),exist);
	
		try {
			FileWriter fw = new FileWriter(backupFolder.getPath()+File.separator+"Backup.ini");
			fw.write(json);
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
