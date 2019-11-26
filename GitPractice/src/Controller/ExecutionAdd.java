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
		filePath = new String("."+sls+"GitHub"+sls+currentRepository+sls+"origin"+sls+currentBranch+sls);
		File add = new File(filePath+"add");
		File workspace = Model.CurrentLocation.workspace;
		
		if(a!=null) {
			if(a.equals("*")||a.equals(".")) {	//workspace내의 모든 파일 add
				/**.gitignore 예외처리 해야함
				 * */
				makeBackup(add);	//백업
				FileOperation.copyFileAll(workspace,add);
			} else {	//workspace에서 파라미터의 파일을 add
				//파일이 존재하는지 예외처리
				boolean error = false;
				int cnt = 0;
				String[] parameter = {a, b, c};
				for(int i = 0; i < 3; i++) {
					File f = new File(workspace.getPath()+sls+parameter[i]);
					if(parameter[i] == null) {
						break;
					} else if(f.exists()) {
						cnt++;
					} else {
						error = true;
						cnt = i;
					}
				}
				
				if(error)
					JOptionPane.showMessageDialog(null, "'"+parameter[cnt]+"' 파일이 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				else {	//파라미터의 파일을 add
					makeBackup(add);	//백업
					for(int i = 0; i < cnt; i++) {
						File source = new File(workspace.getPath()+sls+parameter[i]);
						if(source.isDirectory()) {	//디렉토리일 경우 디렉토리를 만들어주고 내용물을 복사함
							File sourceCopy = new File(add.getPath()+sls+parameter[i]);
							sourceCopy.mkdir();
							FileOperation.copyFileAll(source,add);
						} else {	//파일일 경우
							FileOperation.copyFile(source,add);
						}
					}
				}
			}
			
		} else
			JOptionPane.showMessageDialog(null, "잘못된 명령어입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
	}

	public void cancelCommand(String a, String b, String c) {
//		currentRepository = Model.CurrentLocation.getRepo();
//		currentBranch = Model.CurrentLocation.getBranch();
		currentRepository = "MyProject";
		currentBranch = "master";
		filePath = new String("."+sls+"GitHub"+sls+currentRepository+sls+"origin"+sls+currentBranch+sls);
		File add = new File(filePath+"add");
		
		loadBackup(add);
	}
	
	private void makeBackup(File sourceFile) {
		//백업 폴더 생성
		String backupFolder = Model.CommandStack.createBackup();
		File backup = new File(backupFolder);
		backup.mkdir();
		
		//백업폴더 내부에 sourceFile 이름으로 백업 폴더 생성
		String sourceBackupPath = new String(backupFolder+sls+sourceFile.getName());
		File sourceBackup = new File(sourceBackupPath);
		sourceBackup.mkdir();
		
		//백업
		FileOperation.copyFileAll(sourceFile, sourceBackup);
	}
	
	private void loadBackup(File sourceFile) {
		//백업 폴더 불러오기.
		String backupFolder = Model.CommandStack.loadBackup();
		File backup = new File(backupFolder);
		backup.mkdir();
		//백업폴더 이름을 토대로 sourFile의 백업 폴더 불러오기.
		String sourceBackupPath = new String(backupFolder+sls+sourceFile.getName());
		File sourceBackup = new File(sourceBackupPath);
		sourceBackup.mkdir();
		
		//sourceFile 삭제 후 백업본 복사
		FileOperation.deleteFile(sourceFile);
		FileOperation.copyFileAll(sourceBackup, sourceFile);
		
		//백업본 삭제
		FileOperation.deleteFile(backup);
		backup.delete();
	}
}
