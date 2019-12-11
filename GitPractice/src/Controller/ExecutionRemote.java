package Controller;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Model.CurrentLocation;
import View.CommandInputPane;

public class ExecutionRemote {
	private Map<String,String> remoteList;
	
	public boolean executeCommand(String[] parameter) {
		remoteList = Model.CurrentLocation.getRemoteList();
		if(parameter == null) {
			//현재 workspace에 연결된 원격 저장소의 이름들을 보여준다.
			String outputS = new String("////remote List////\n\n");
			
			Set set = remoteList.keySet();
			Iterator repoIt = set.iterator();
			while(repoIt.hasNext()) {
				String key = (String)repoIt.next();
				outputS = outputS.concat(key);
				outputS = outputS.concat("\n");
			}
			CommandInputPane.allCommandtxt.setText(outputS);
			return true;
		} else if(parameter.length == 2 && parameter[0].equals("remove")) {
			if(remoteList.get(parameter[1])!=null) {
				Model.FileOperation.makeBackup(new File(Model.CurrentLocation.workspace,".git"));
				remoteList.remove(parameter[1]);	//리모트 리스트에 입력한 리모트 삭제
				Model.CurrentLocation.setRemoteList(remoteList);	//갱신
				RemoteFunction rf = new RemoteFunction();
				rf.remoteListSave();	//입력한 리모트가 삭제된 리스트로 파일 덮어씌우기
				return true;
			} 
			JOptionPane.showMessageDialog(null, "해당 리모트 저장소가 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;

		} else if(parameter.length == 3 && parameter[0].equals("add") ) {
			int check = Model.CurrentLocation.addRemote(parameter[1], parameter[2]);
			if(check == Model.CurrentLocation.REPO_NAME_SAME) {
				JOptionPane.showMessageDialog(null, "해당 리모트 저장소가 이미 존재합니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			} else if(check == Model.CurrentLocation.REPO_URL_NULL) {
				JOptionPane.showMessageDialog(null, "해당 주소가 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			} else {
				Model.FileOperation.makeBackup(new File(Model.CurrentLocation.workspace,".git"));
				RemoteFunction rf = new RemoteFunction();
				rf.remoteListSave();	//입력한 리모트가 삭제된 리스트로 파일 덮어씌우기
				return true;
			}
		}else {
			JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public boolean cancelCommand(String[] parameter) {
		if(parameter==null)
			CommandInputPane.allCommandtxt.setText("");
		else {
			Model.FileOperation.loadBackup(new File(Model.CurrentLocation.workspace,".git"));
			RemoteFunction rf = new RemoteFunction();
			rf.remoteListOpen();
		}
		return true;
	}
}