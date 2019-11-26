package Controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import Model.FileOperation;

public class ExecutionPush {
	private String currentRepository;	//현재 레포지토리 이름
	private String sls = File.separator;	//File.separator
	private String filePath;	//파일 디렉토리 경로
	
	public void executeCommand(String a, String b, String c) {
		currentRepository = "MyProject";	/**임시. 추후 모델에서 입력 받기*/
		filePath = new String("."+sls+"GitHub"+sls);
		
		if(c == null) {
			if(b != null) {	//a/b에 push
				filePath = filePath.concat("a의 레포지토리"+sls+a+sls+b+sls);
			} else {	
				if(a != null) {	//a/master에 push
					filePath = filePath.concat("a의 레포지토리"+sls+a+sls+"master"+sls);
				} else {	//origin/master에 push
					filePath = filePath.concat(currentRepository+sls+"origin"+sls+"master"+sls);
				}
			}
			String backupFolder = Model.CommandStack.createBackup();
			File backup = new File(backupFolder);
			backup.mkdir();
			
			//push이므로 백업폴더 안에 commit, push 내용 복사
			String commitFolder = new String(backupFolder+sls+"commit");
			File commit = new File(commitFolder);
			commit.mkdir();
			String pushFolder = new String(backupFolder+sls+"push");
			File push = new File(pushFolder);
			push.mkdir();
			
			File originCommit = new File(filePath+"commit");
			File originPush = new File(filePath+"push");
			
			FileOperation.copyFileAll(originCommit, commit);
			FileOperation.copyFileAll(originPush, push);
			
			FileOperation.moveFileAll(filePath+"commit",filePath+"push");	//commit폴더 내부 push폴더로 전부 옮기기.
		} else	//인자는 두 개만 와야 함.
			JOptionPane.showMessageDialog(null, "잘못된 명령어입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
	}
	
	public void cancelCommand(String a, String b, String c) {
		currentRepository = "MyProject";	/**임시. 추후 모델에서 입력 받기*/
		filePath = new String("."+sls+"GitHub"+sls);
		if(b != null) {	//a/b에 push
			filePath = filePath.concat("a의 레포지토리"+sls+a+sls+b+sls);
		} else {	
			if(a != null) {	//a/master에 push
				filePath = filePath.concat("a의 레포지토리"+sls+a+sls+"master"+sls);
			} else {	//origin/master에 push
				filePath = filePath.concat(currentRepository+sls+"origin"+sls+"master"+sls);
			}
		}
		String backupFolder = Model.CommandStack.loadBackup();
		File backup = new File(backupFolder);
		backup.mkdir();
		
		String commitFolder = new String(backupFolder+sls+"commit");
		File commit = new File(commitFolder);
		commit.mkdir();
		
		String pushFolder = new String(backupFolder+sls+"push");
		File push = new File(pushFolder);
		backup.mkdir();
		
		File originCommit = new File(filePath+"commit");
		File originPush = new File(filePath+"push");
		
		//현재 commit, push폴더 삭제
		FileOperation.deleteFile(originCommit);
		FileOperation.deleteFile(originPush);
		
		originCommit = new File(filePath+"commit");
		originPush = new File(filePath+"push");
					
		//백업본 복사 후 삭제
		FileOperation.copyFileAll(commit, originCommit);
		FileOperation.copyFileAll(push, originPush);
		FileOperation.deleteFile(backup);
		backup.delete();
	}
}
