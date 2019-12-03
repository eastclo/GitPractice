package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.CommitArray;
import Model.FileOperation;


public class ExecutionPush {
	private File currentWorkspace = Model.CurrentLocation.workspace;	//현재 저장소이름
	private String sep = File.separator;	//File.separator
	private String url = null;	//현재 로컬저장소에서 입력한 remote이름으로 연결된 원격 저장소 url
	private File remote = null;	//url을 가진 원격 저장소
	
	public boolean executeCommand(String[] parameter){	
		if(parameter.length==2) {
			try {
				searchRemoteList(parameter[0]); //origin에 접근
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}	
			
			if(url == null) {	//예외처리
				JOptionPane.showMessageDialog(null,"원격 저장소'"+parameter[0]+"'가 지정되지 않았습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;	
			}
			searchRemoteRepository();
			
			if(!remote.exists()) {	//예외처리
				JOptionPane.showMessageDialog(null,"지정되지 않은 원격 저장소입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			File localBranch = new File(currentWorkspace, parameter[1]);
			
			if(!localBranch.exists()) {	//예외처리
				JOptionPane.showMessageDialog(null,"로컬저장소에 '"+parameter[1]+"' 브랜치가 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
				
			//백업
			Model.FileOperation.makeBackup(remote);
			
			File remoteBranch = new File(remote,parameter[1]);
			if(!remoteBranch.exists()) {
				remoteBranch.mkdir();
				Model.FileOperation.copyFileAll(localBranch, remoteBranch);
			} else {	//원격 저장소에 푸시한 내역이 있으면 업데이트 되지 않은 커밋만 푸시함
				File commitFolder = new File(localBranch,"commit");
				String[] localCommitList = commitFolder.list();
				for(String localCommit : localCommitList) {
					File commit = new File(remoteBranch, localCommit);
					if(!commit.exists()) {
						commit.mkdir();
						Model.FileOperation.copyFileAll(new File(commitFolder, localCommit), commit);
					}
				}
			}
			return true;
		} else {	//인자는 반드시 2개가 와야 한다.
			JOptionPane.showMessageDialog(null, "잘못된 명령어입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public boolean cancelCommand(String[] parameter) {
		try {
			searchRemoteList(parameter[0]);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		searchRemoteRepository();
		
		//복원
		Model.FileOperation.loadBackup(remote);
		return true;
	}
		
	/*입력한 remote 값을 토대로 url을 읽어오는 메소드*/
	private void searchRemoteList(String remote) throws IOException, ParseException {
		String url = null;
		JSONParser parser = new JSONParser();
		CommitArray json = new CommitArray();
		
		if(remote == null)	//리모트 입력값이 없다면 default는 origin
			remote = new String("origin");
		try {
			FileReader rd = new FileReader(new File(currentWorkspace.getPath()+sep+".git", "RemoteList.ini"));
			Object obj = parser.parse(rd);
			JSONArray jsonArray = (JSONArray)obj;
			Iterator<JSONObject> iterator =jsonArray.iterator();
			
			while(iterator.hasNext()) {
				//RemoteList.ini 파일에서 remote 입력 값이 같으면 url 읽어오기
				JSONObject jsonObject = (JSONObject)iterator.next();
				if(remote.equals(String.valueOf(jsonObject.get(json.remoteKey))))	
					url = String.valueOf(jsonObject.get(json.urlKey));
			}
			rd.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.url = url;
	}
	
	/*읽어온 url을 토대로 해당 url을 가진 레포지토리의 경로를 저장하는 메소드 */
	private void searchRemoteRepository() {
		Model.RepositoryList repo = new Model.RepositoryList();
		String[] repoList = repo.getRepoList();
		String repoPath = null;
		
		for(String remote : repoList) {
			repoPath = new String("."+sep+"GitHub"+sep+remote);
			if(url.equals(Model.FileOperation.getFileReadData(repoPath, "address.txt")))
				this.remote = new File(repoPath);
		}
	}
}
