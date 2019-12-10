package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import Model.CommitArray;
import Model.CurrentLocation;
import View.CommitGraphPane;

public class ExecutionCheckout {
	static List<File> dfile=new ArrayList<File>();
	static List<String> dcheck = new ArrayList<String>();
	public boolean executeCommand(String[] parameter) {
		//git checkout을 구현한 클래스이다.
		if(parameter[0]!=null)
		{
			//dfile과 dcheck는 backbutton으로 명령어 취소를 실행시 이용하기 위함이다. 각각 명령어 입력시의 workspace와 브랜치를 저장한다.
			if(dcheck.size()==0)
			{
				dfile.add(CurrentLocation.workspace);
				dcheck.add(CurrentLocation.getBranch());
			}
			//현재 workspace의 브랜치 리스트를 받아온다.
			List<String> branchList = new ArrayList<String>();
			branchList=CurrentLocation.getBranchList();
			boolean findswt = false;
			for(String s : branchList) {
				if(s.equals(parameter[0]))
				{
					findswt=true;
					break;
				}
			}
			if(findswt==true)
			{
				//입력한 브랜치가 있을경우 해당 브랜치로 이동한다.
				CurrentLocation.changeBranch(parameter[0]);
				dfile.add(CurrentLocation.workspace);
				dcheck.add(CurrentLocation.getBranch());


				if(new File(CurrentLocation.workspace+File.separator+".git"+File.separator+"CommitList.ini").exists())
				{
					try {
						CommitFunction cm = new CommitFunction();
						cm.commitListOpen();
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
					} catch (IOException | ParseException e1) {
						e1.printStackTrace();
					}
				}

				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "존재하지 않는 브랜치입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		else if(parameter==null)
		{
			JOptionPane.showMessageDialog(null, "CHECKOUT할 브랜치를 입력해주십시오.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "유효하지 않은 인자입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	public boolean cancelCommand(String[] parameter) {
		//dfile을 통해 취소할 workspace로 이동한다.
		dfile.remove(dfile.size()-1);
		File gitpath = dfile.get(dfile.size()-1);
		CurrentLocation.workspace=gitpath;
		//해당 workspace로 설정을 바꾼다.
		if(new File(CurrentLocation.workspace.getPath()+File.separator+".git").exists())
		{
			BranchFunction bf = new BranchFunction();
			CurrentLocation.changeBranch("master");
			bf.BranchListOpen();
			try {
				CommitFunction cm = new CommitFunction();
				cm.commitListOpen();
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
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		}
		//checkout 이전 branch로 바꾼다.
		dcheck.remove(dcheck.size()-1);
		String deletecheck = dcheck.get(dcheck.size()-1);
		CurrentLocation.changeBranch(deletecheck);
		return true;
	}

}
