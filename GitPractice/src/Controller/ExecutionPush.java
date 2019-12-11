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
			String remoteName = Model.CurrentLocation.getRemoteList().get(parameter[0]);
			if(remoteName != null) {
				File localBranch = new File(currentWorkspace, parameter[1]);
				
				if(!localBranch.exists()) {	//예외처리
					JOptionPane.showMessageDialog(null,"로컬저장소에 '"+parameter[1]+"' 브랜치가 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				remote = new File("."+File.separator+"GitHub",remoteName);
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
			}else {//리모트 입력 에러
				JOptionPane.showMessageDialog(null,"리모트 저장소 '"+parameter[0] +"'이 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {	//인자는 반드시 2개가 와야 한다.
			JOptionPane.showMessageDialog(null, "잘못된 명령어입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public boolean cancelCommand(String[] parameter) {
		String remoteName = Model.CurrentLocation.getRemoteList().get(parameter[0]);
		remote = new File("."+File.separator+"GitHub",remoteName);
		
		//복원	
		Model.FileOperation.loadBackup(remote);
		return true;
	}
}
