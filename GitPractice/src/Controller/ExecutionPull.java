package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import Model.CurrentLocation;
import Model.FileOperation;
import View.CommitGraphPane;
import View.CommitInputDialog;

public class ExecutionPull {
	
	private File localBranch;
	private File remoteBranch;
	private File workspace;
	private ArrayList<String> conflictList;
	
	public boolean executeCommand(String[] parameter) {
		workspace = Model.CurrentLocation.workspace;
		conflictList = new ArrayList<String>();
		
		// parameter[0],[1]의 리모트와 브랜치가 실존하는지 확인후 merge인자로 각각의 브랜치를 넘겨주면 된다.
		if(parameter.length == 2) {
			String repoName = Model.CurrentLocation.RemoteList.get(parameter[0]);
			if(repoName!=null) {
				if(makeLocalBranchObject(parameter[1])) {//parameter[1]의 이름으로 멤버변수 localBranch 초기화
					remoteBranch = new File("."+File.separator+"GitHub"+File.separator+repoName,parameter[1]);	//parameter[1]의 이름으로 멤버변수 remoteBranch초기화
					
					File localBranchRecentCommit = maxChecksum(localBranch);	//로컬 저장소의 최근커밋 폴더
					File remoteBranchRecentCommit = maxChecksum(remoteBranch);	//리모트 저장소의 최근커밋 폴더
					
					File fileLocal;
					if(localBranchRecentCommit != null) {
						for(File fileRemote : remoteBranchRecentCommit.listFiles()) {
							fileLocal = new File(localBranchRecentCommit, fileRemote.getName());
							if(fileLocal.exists()) {
								String fileLocalData = Model.FileOperation.getFileReadData(fileLocal.getPath(), fileLocal.getName());
								String fileRemoteData = Model.FileOperation.getFileReadData(fileRemote.getPath(), fileRemote.getName());
								if(!fileLocalData.equals(fileRemoteData)) {	//파일이름은 같은데 내용이 다른 파일이 있다면 충돌.
									conflictList.add(fileLocal.getName());
								}
							}
						}
					}
					if(conflictList.isEmpty()) {
							/*add폴더에 병합할 파일들을 넣고 git commit의 executecommand을 호출한다.*/
							File add = new File(workspace.getPath()+ File.separator + ".git", "add");
							Model.FileOperation.makeBackup(add);	//기존의 add폴더 백업
							Model.FileOperation.deleteFile(add);	//기존 add폴더 내용 삭제
							
							//add로 복사
							if(localBranchRecentCommit != null) {
								Model.FileOperation.copyFileAll(localBranchRecentCommit, add);
								for(File fileRemote : remoteBranchRecentCommit.listFiles()) {
									fileLocal = new File(localBranchRecentCommit, fileRemote.getName());
									if(!fileLocal.exists()) {
										Model.FileOperation.copyFile(fileRemote, add);
									}
								}
							} else {
								Model.FileOperation.copyFileAll(remoteBranchRecentCommit, add);
							}
							//머지 커밋 생성
							autoCommit(localBranch.getName(),remoteBranch.getName());
							loadAddBackup(add);	//기존의 add폴더 복원
							return true;
		/**이하 예외처리**/
					} else {	//충돌 에러
						String conflictFileList = new String(); 
						Iterator<String> it = conflictList.iterator();
						while(it.hasNext()) {
							conflictFileList = conflictFileList.concat(it.next()+" ");
						}
						JOptionPane.showMessageDialog(null, "해당 파일에서 충돌이 발생하였습니다 :\n" + conflictFileList, "충돌", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				} else {	//브랜치 입력 에러
					JOptionPane.showMessageDialog(null, "브랜치 '"+parameter[1]+"'를 잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} else {	//리모트 입력 에러
				JOptionPane.showMessageDialog(null,"리모트 저장소 '"+parameter[0] +"'이 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {	//명령어 인자 에러
			JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public boolean cancelCommand(String[] parameter) {
	
		return true;
	}
	
	//add폴더를 백업폴더로 복원 후 add백업폴더만 삭제
	private void loadAddBackup(File add) {
		//백업 폴더 불러오기.
		String backupFolder = Model.CommandStack.loadBackup();
		File backup = new File(backupFolder);
		backup.mkdir();
		
		//백업폴더 이름을 토대로 add의 백업 폴더 불러오기.
		File addBackup = new File(backup, add.getName());
		addBackup.mkdir();
		
		//백업본 복사
		FileOperation.copyFileAll(addBackup, add);
		
		//add백업폴더 삭제
		FileOperation.deleteFile(addBackup);
		addBackup.delete();
	}
	
	//인자로 넘긴 commit폴더에서 가장 최근 커밋의 체크섬을 가져옴
	private File maxChecksum(File commit) {
		int num = -1;
		File f = null;
		for(File checkSum : commit.listFiles()) {
			if(checkSum.exists()) {
				if(num <Integer.parseInt(checkSum.getName()))
					f = checkSum;
			}
		}
		if(num != -1)
			return f;
		else
			return null;
	}
	
	//현재 저장소에 inputName의 브랜치가 존재하면 멤버변수 localBranch에 객체로 만들고 true 리턴.
	private boolean makeLocalBranchObject(String inputName) {
		List<String> branchList = Model.CurrentLocation.getBranchList();
		Iterator<String> it = branchList.iterator();
		String localBranchName = null;
		
		while(it.hasNext()) {
			localBranchName = (String)it.next();
			if(localBranchName.equals(inputName)) {
				localBranch = new File(workspace.getPath()+File.separator+inputName, "commit");
				return true;
			}
		}
		return false;
	}
	
	//merge 메시지를 자동으로 생성하여 커밋함 
	private void autoCommit(String localBranch, String remoteBranch) {
		String inputContent= new String("Merge branch ‘"+ localBranch +"’ of remote into ‘"+ remoteBranch +"’");
		
		CommitFunction commit = new CommitFunction();
		try {
			commit.commitListOpen();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//입력받은 커밋 내용을 현재 정보와 조합하여 커밋에 저장한다.
		commit.commitAdd(inputContent,CurrentLocation.getBranch(),CurrentLocation.AuthorName,CurrentLocation.AuthorAddress,commit.CMArray.size());
		commit.workspaceCopy(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"),new File(CurrentLocation.workspace.getPath()+File.separator+CurrentLocation.getBranch()+File.separator+"commit"));

		List<String> branchList = commit.BranchArray;
		List<Integer> check=new ArrayList<Integer>();
		for(int i=0;i<branchList.size();i++)
		{
			if(branchList.get(i).equals(CurrentLocation.getBranch()))
				check.add(i);
		}
		//add에있던 파일들은 commit을 했으므로, commit폴더로 이동한다. 이때 커밋했을때의 브랜치로 이동하는 것이며, checksum은 총 commit의 count로 한다.
		//ex) master로 처음 commit 시 commit/master/0에 저장, 다시한번 커밋시 /master/commit/1에 저장, 그 후 test 브랜치로 커밋시 test/commit/2에 저장
		if(check.size()>1)
			commit.workspaceCopy(new File(CurrentLocation.workspace.getPath()+File.separator+CurrentLocation.getBranch()+File.separator+"commit"+File.separator+check.get(check.size()-2)), new File(CurrentLocation.workspace.getPath()+File.separator+CurrentLocation.getBranch()+File.separator+"commit"));
		FileOperation.deleteFile(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"));
		commit.commitListSave(commit.Path);

		int checki=0;
		for(int i=0;i<branchList.size();i++)
		{
			if(branchList.get(i).equals(CurrentLocation.getBranch()))
			{
				checki=i;
			}
		}
		CurrentLocation.HEADLocation=checki;
		CommitGraphPane.canvas.repaint();
	}
}