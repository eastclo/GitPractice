package Controller;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.FileOperation;

public class ExecutionGitClone {
	private JComboBox repoComboBox;
	private DefaultComboBoxModel comboModel;
	private String sep = File.separator;
	
	public void executeCommand(String a, String b, String c) {
		if(b == null && c == null) {
			repoComboBox = View.CommandInputPane.getComboBox();	//View에서 콤보박스 가져오기
			//레포지토리 리스트 가져오기
			Model.RepositoryList tmp = new Model.RepositoryList();
			String[] repoList = tmp.getRepoList();
			int index = 0;
			for(int i = 0; i < repoList.length; i++) {
				String path = new String("." + sep + "GitHub" + sep + repoList[i]);
				if (a.equals(Model.CommandListOperation.getFileReadData(path, "address.txt"))) {
					index = i; break;
				}
			}
			//백업(clone한 레포지토리 이름으로 된 폴더 안에 현재 선택된 레포지토리 이름으로 된 파일)
			String backupPath = Model.CommandStack.createBackup();
			File backupFolder = new File(backupPath);
			backupFolder.mkdir();
			
			File backup = new File(backupFolder, repoList[index]);
			backup.mkdir();
			
			String beforeRepo = Model.CurrentLocation.getRepo();
			if(beforeRepo != null) {
				File backupBeforeRepo = new File(backup, beforeRepo);
				try {
					backupBeforeRepo.createNewFile();	//파일 생성
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//콤보박스에 레포지토리 넣기
	//		comboModel = View.CommandInputPane.getComboModel();
	//		comboModel.addElement(repoList[index]);
	//		comboModel.setSelectedItem(repoList[index]);
	//		repoComboBox.setModel(comboModel);
			repoComboBox.addItem(repoList[index]);
			repoComboBox.setSelectedItem(repoList[index]);
			repoComboBox.repaint();	
			//clone한 레포지토리를 현재 레포지토리로 설정 
			Model.CurrentLocation.changeRepo(repoList[index]);
		} else
			JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
	}
	public void cancelCommand(String a, String b, String c) {		
		//clone하기 전에 가지고 있던 레포지토리 값과 콤보박스에 추가한 값을 가져와야한다.
		String backupPath = Model.CommandStack.loadBackup();
		File backupFolder = new File(backupPath);
		
		//콤보 박스에 추가한 레포지토리 삭제
		String[] addedRepoName = backupFolder.list();
		File addedRepo = new File(backupPath, addedRepoName[0]);		
		repoComboBox = View.CommandInputPane.getComboBox();
		comboModel = View.CommandInputPane.getComboModel();
		comboModel.removeElement(addedRepoName[0]);

		//레포지토리 위치는 이전 레포지토리로 변경
		String[] beforeRepoName = addedRepo.list();
		if(beforeRepoName.length != 0) {
			Model.CurrentLocation.changeRepo(beforeRepoName[0]);
			comboModel.setSelectedItem(beforeRepoName[0]);
		} else
			Model.CurrentLocation.changeRepo(null);
		repoComboBox.repaint();
		
		//백업본 삭제
		FileOperation.deleteFile(backupFolder);
		backupFolder.delete();
	}
}
