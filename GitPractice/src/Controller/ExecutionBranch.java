package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import Model.CurrentLocation;
import Model.FileOperation;
import View.CommandExplainDialog;
import View.CommandInputPane;

public class ExecutionBranch {
	static List<String>branchList = new ArrayList<String>();
	
	static List<File> dfile=new ArrayList<File>();
	//git branch를 실행하는 메소드이다.
	public boolean executeCommand(String[] parameter) {
		//실행하게 되면 현재 브랜치의 정보를 가져온다.
		branchList = CurrentLocation.getBranchList();
		//일반적인 git branch의 경우
		if(parameter==null)
		{
			//현재 workspace의 브랜치들을 보여준다. 자신이 checkout중인 브랜치는 옆에 *표시를 한다.
			String outputS = new String("////branch List////\n\n");
			for(int i=0;i<branchList.size();i++)
			{
				outputS=outputS+branchList.get(i);
				if(branchList.get(i).equals(CurrentLocation.getBranch()))
					outputS=outputS+" * \n";
				else
					outputS=outputS+"\n";
			}
			CommandInputPane.allCommandtxt.setText(outputS);
			return true;
		}
		else if(parameter.length==1)//git branch "INPUT" 의 경우
		{
			//dfile은 workspace를 변경하면서 실행한 경우에도 취소기능을 사용할 수 있도록 구현하였다.
			dfile.add(CurrentLocation.workspace);
			//입력한 브랜치이름이 존재할 경우
			if(!CurrentLocation.addBranch(parameter[0]))
			{
				JOptionPane.showMessageDialog(null, "이미 존재하는 브랜치입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else
			{
				//그렇지 않은 경우, 새로 BranchList에 추가하고, branchList.ini를 저장하며, 해당 브랜치의 폴더와 하위폴더(commit,workspace)들을 생성한다.
				BranchFunction bf = new BranchFunction();
				bf.BranchListSave();
				new File(CurrentLocation.workspace.getPath()+File.separator+parameter[0]).mkdir();
				new File(CurrentLocation.workspace.getPath()+File.separator+parameter[0]+File.separator+"workspace").mkdir();
				FileOperation.copyFileAll(new File(CurrentLocation.workspace.getPath()+File.separator+"master"+File.separator+"workspace"), new File(CurrentLocation.workspace.getPath()+File.separator+parameter[0]+File.separator+"workspace"));
				new File(CurrentLocation.workspace.getPath()+File.separator+parameter[0]+File.separator+"commit").mkdir();
				return true;
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "유효하지 않은 인자입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public boolean cancelCommand(String[] parameter) {
		if(parameter==null)
		{
			CommandInputPane.allCommandtxt.setText("");
		}
		else
		{
			//git branch를 실행했을때의 workspace로 이동한다.
			CurrentLocation.workspace=(dfile.get(dfile.size()-1));
			dfile.remove(dfile.size()-1);

			if(new File(CurrentLocation.workspace.getPath()+File.separator+".git").exists())
			{
				BranchFunction bf = new BranchFunction();
				CurrentLocation.changeBranch("master");
				bf.BranchListOpen();
				try {
					new CommitFunction().commitListOpen();
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
			}
			
			//이동한 workspace에서 가장 최근 브랜치를 선택후 삭제한다.
			String dbranch = CurrentLocation.getBranchList().get(CurrentLocation.BranchList.size()-1);
			CurrentLocation.BranchList.remove(CurrentLocation.BranchList.size()-1);
			
			//삭제된 상태로 다시 branch들을 저장한다.
			BranchFunction bf = new BranchFunction();
			bf.BranchListSave();
			
			//삭제한 브랜치의 폴더를 삭제한다.
			FileOperation.deleteFile(new File(CurrentLocation.workspace.getPath()+File.separator+dbranch));
			new File(CurrentLocation.workspace.getPath()+File.separator+dbranch).delete();
		}
		return true;
	}

}
