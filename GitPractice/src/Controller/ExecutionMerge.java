package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import View.MergeInputDialog;

public class ExecutionMerge {
	private File branch;
	private File sourceBranch;
	private File add;
	private String sep = File.separator;
	private String text;
	private int sameCnt = 0;
	private int mergeCnt = 0;
	
	public boolean executeCommand(String[] parameter) {
		if(parameter.length == 2) {
			File git = new File(Model.CurrentLocation.workspace,".git");
			add = new File(git,"add");
			if(add.list().length == 0){	//add폴더가 비어있어야함.
				File branch = new File(Model.CurrentLocation.workspace,parameter[0]);
				File sourceBranch = new File(Model.CurrentLocation.workspace,parameter[1]);
				if(sourceBranch.exists() && branch.exists()) {
					Model.FileOperation.makeBackup(branch);
					
					//prameter[1] 커밋 폴더의 모든 내용을 복사. 
					File commit =  new File(branch,"commit");
					File sourceCommit = new File(sourceBranch,"commit");
					
					String branchChecksum = maxChecksum(commit);
					String sourceBranchChecksum = maxChecksum(sourceCommit);
					Model.FileOperation.copyFileAll(sourceCommit,commit);
					
					File branchRecentCommit = new File(commit,branchChecksum);	//parameter[0]의 가장 최근 커밋 폴더
					for (String fileName : branchRecentCommit.list()) {
						Model.FileOperation.copyFile(new File(branchRecentCommit,fileName), add);
					}
					
					File sourceF;
					File targetF;
					File sourceBranchRecentCommit = new File(commit,sourceBranchChecksum);	//parameter[1]의 가장 최근 커밋 폴더
					for (String fileName : sourceBranchRecentCommit.list()) {
						sourceF = new File(sourceBranchRecentCommit,fileName);
						targetF = new File(add,fileName);
						if(targetF.exists()) {
							sameCnt++;
							makeNewDialog(sourceF,targetF,fileName);
						} else {
							Model.FileOperation.copyFile(sourceF, add);
						}
					}
					
					return true;
				} else {	//예외처리: parameter의 브랜치가 존재하지 않을 때
					JOptionPane.showMessageDialog(null, "브랜치를 잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} else {	//예외처리: add폴더가 비어있지 않을 때
				JOptionPane.showMessageDialog(null, "StaingArea에 파일이 존재합니다. 커밋을 진행한 후 merge를 실행해 주세요.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}		
		} else {	//예외처리	: parameter를 2개 입력하지 않았을 때
			JOptionPane.showMessageDialog(null, "잘못 입력하였습니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public void makeNewDialog(File sourceF, File targetF, String fileName) {
		MergeInputDialog merDialog = new MergeInputDialog(sourceF,targetF);
		merDialog.addComponentListener(merDialog);
		JButton btn1 = merDialog.getFirstBtn();
		JButton btn2 = merDialog.getSecondBtn();
		btn1.addActionListener(new mergeSelectListener(merDialog.getFristFile(),fileName, merDialog));
		btn2.addActionListener(new mergeSelectListener(merDialog.getSecondFile(),fileName, merDialog));
	}
	
	public boolean cancelCommand(String[] parameter) {
		File branch = new File(Model.CurrentLocation.workspace,parameter[0]);
		Model.FileOperation.loadBackup(branch);
		return true;
	}
	
	public String maxChecksum(File commit) {
		int num = 0;
		for(String checkSum : commit.list())
			if(Integer.parseInt(checkSum)>num)
				num = Integer.parseInt(checkSum);
		return String.valueOf(num);
	}
		
	class mergeSelectListener implements ActionListener {
		private String text;
		private String fileName;
		private MergeInputDialog merDialog;
		
		public mergeSelectListener(String text, String fileName, MergeInputDialog merDialog){
			this.text = text;
			this.fileName = fileName;
			this.merDialog = merDialog;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			File f = new File(add,fileName);
			merDialog.dispose();
			Model.FileOperation.writeFile(f, text);
			mergeCnt++;
			if(mergeCnt == sameCnt) {
				ExecutionCommit a = new ExecutionCommit();
				a.executeCommand(null);
			}
		}
	}
}
