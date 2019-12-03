package Controller;

import java.io.File;

import javax.swing.JOptionPane;

import Model.FileOperation;

public class ExecutionAdd {
	private File currentBranch;	//현재 브랜치 이름
	private String sep = File.separator;	//File.separator
	private String filePath;	//파일 디렉토리 경로
	
	public boolean executeCommand(String[] parameter) {
		currentBranch = new File(Model.CurrentLocation.workspace,Model.CurrentLocation.getBranch());
		File add = new File(currentBranch,"add");	//현재 저장소 현재 브랜치의 add폴더 접근
		File workspace = new File(currentBranch, "workspace");
		
		if(parameter!=null) {	//입력값이 하나라도 있어야 함
			Model.FileOperation.makeBackup(add);	//백업
			if(parameter[0].equals("*")||parameter[0].equals(".")) {	//workspace내의 모든 파일 add
				/**.gitignore 예외처리 해야함
				 * */
				if(workspace.listFiles() != null)
					FileOperation.copyFileAll(workspace,add);
				else {
					deleteBackup();
					JOptionPane.showMessageDialog(null, "저장소에 파일이 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} else {	//workspace에서 파라미터의 파일을 add
				//파라미터의 파일을 add
				for(int i = 0; i < parameter.length; i++) {
					File f = new File(workspace,parameter[i]);
					if(!f.exists()) {	//예외처리: 파일이 workspace에 존재하지 않으면 false 리턴
						deleteBackup();
						JOptionPane.showMessageDialog(null, "'"+parameter[i]+"' 파일이 존재하지 않습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
						return false;
					}
					if(f.isDirectory()) {	//디렉토리일 경우 디렉토리를 만들어주고 내용물을 복사함
						File sourceCopy = new File(add,parameter[i]);
						sourceCopy.mkdir();
						FileOperation.copyFileAll(f,add);
					} else {	//파일일 경우
						FileOperation.copyFile(f,add);
					}
				}
			}
			return true;
		} else {
			//입력값이 하나라도 있어야 함
			JOptionPane.showMessageDialog(null, "잘못된 명령어입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean cancelCommand(String[] parameter) {
		currentBranch = new File(Model.CurrentLocation.workspace,Model.CurrentLocation.getBranch());
		File add = new File(currentBranch,"add");	//현재 저장소 현재 브랜치의 add폴더 접근

		Model.FileOperation.loadBackup(add);
		return true;
	}
	

	
	private void deleteBackup() {	//백업 삭제
		String backupFolder = Model.CommandStack.loadBackup();
		File backup = new File(backupFolder);
		backup.mkdir();
		FileOperation.deleteFile(backup);
		backup.delete();
	}
}
