package Controller;

import java.io.File;

import javax.swing.JOptionPane;

import Model.FileOperation;

public class ExecutionAdd {
	private String currentRepository;	//현재 레포지토리 이름
	private String currentBranch;	//현재 브랜치 이름
	private String sls = File.separator;	//File.separator
	private String filePath;	//파일 디렉토리 경로
	
	public void executeCommand(String a, String b, String c) {
//		currentRepository = Model.CurrentLocation.getRepo();
//		currentBranch = Model.CurrentLocation.getBranch();
		currentRepository = "MyProject";
		currentBranch = "master";
		filePath = new String("."+sls+"GitHub"+sls+currentRepository+sls+"origin"+currentBranch+sls);
		File add = new File(filePath+"add");
		File workspace = Model.CurrentLocation.workspace;
		
		if(a!=null) {
			if(a.equals("*")||a.equals(".")) {	//workspace내의 모든 파일 add
				/**.gitignore 예외처리 해야함
				 * */
				makeBackup(add);
				makeBackup(workspace);
				
				FileOperation.moveFileAll(workspace.getPath() , add.getPath());
			} else {
				
			}
			
		} else
			JOptionPane.showMessageDialog(null, "잘못된 명령어입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
	}
	public void cancelCommand(String a, String b, String c) {

	}
	
	private void makeBackup(File sourceFile) {
		String backupFolder = Model.CommandStack.createBackup();
		File backup = new File(backupFolder);
		backup.mkdir();
		
		String sourceBackupPath = new String(backupFolder+sls+sourceFile.getName());
		File sourceBackup = new File(sourceBackupPath);
		sourceBackup.mkdir();
		
		FileOperation.copyFileAll(sourceFile, sourceBackup);
	}
	
	private void loadBackup(File sourceFile) {
		
	}
}
