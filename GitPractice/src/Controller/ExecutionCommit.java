package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import Model.CommitArray;
import Model.CurrentLocation;
import Model.FileOperation;
import View.CommitGraphPane;
import View.CommitInputDialog;

public class ExecutionCommit {
	
	CommitInputDialog comDialog;
	
	static List<File> dfile=new ArrayList<File>();
	
	public boolean executeCommand(String[] parameter) {
		//git commit 명령어를 구현한 class이다.
		if(parameter==null)
		{
			//dfile에 커밋했을 때의 workspace위치를 저장한다. backbutton을 통해 명령어 취소 시 사용될 예정이다.
			dfile.add(CurrentLocation.workspace);
			//Commit내용을 입력할 dialog를 띄운다.
			comDialog = new CommitInputDialog();
			
			comDialog.addComponentListener(comDialog);
			JButton btn = comDialog.btn;
			//add에있는 파일들을 백업해둔다. backbutton을 통해 명령어 취소 시 사용될 예정이다.
			FileOperation.makeBackup(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"));
			btn.addActionListener(new btnclick());
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "유효하지 않은 인자가 입력되었습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public boolean cancelCommand(String[] parameter) {
		//dfile의 최근값을 가져와 명령어를 실행했을때의 workspace로 이동한다.
		CurrentLocation.workspace = dfile.get(dfile.size()-1);
		dfile.remove(dfile.size()-1);
		//backup해놨던 add폴더의 내용을 가져다놓는다.(commit->add로 다시 이동을 표현)
		FileOperation.loadBackup(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"add"));
		
		//commit list를 열어서 가장최근 커밋 내용을 지운다.
		CommitFunction cm = new CommitFunction();
		try {
			cm.commitListOpen();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cm.CMArray.remove(cm.CMArray.size()-1);
		cm.BranchArray.remove(cm.BranchArray.size()-1);
		cm.AuthorNameArray.remove(cm.AuthorNameArray.size()-1);
		cm.AuthorAddressArray.remove(cm.AuthorAddressArray.size()-1);
		cm.ChecksumArray.remove(cm.AuthorAddressArray.size()-1);
		CommitArray ca = new CommitArray();
		ca.init();
		//다시 저장하고, 현재 커밋을 CommitGraphPane에 최신화한다.
		for(int i=0;i<cm.CMArray.size();i++)
			ca.commit(cm.CMArray.get(i), cm.BranchArray.get(i), cm.AuthorNameArray.get(i), cm.AuthorAddressArray.get(i),cm.ChecksumArray.get(i));
		cm.commitListSave(CurrentLocation.workspace.getPath()+File.separator+ ".git" +File.separator+"CommitList.ini");
		
		int checki=0;
		for(int i=0;i<cm.BranchArray.size();i++)
		{
			if(cm.BranchArray.get(i).equals(CurrentLocation.getBranch()))
			{
				checki=i;
			}
		}
		CurrentLocation.HEADLocation=checki;
		CommitGraphPane.canvas.repaint();
		return true;
	}
	
	//Commit dialog에서 확인 버튼 누를때의 버튼리스너이다.
	public class btnclick implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String inputContent=comDialog.text.getText();
			comDialog.dispose();
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

}
