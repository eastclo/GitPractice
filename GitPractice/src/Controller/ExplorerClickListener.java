package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JList;

import View.MainFrame;
import View.RepoInfoDialog;
import View.TemporaryExplorerPane;

public class ExplorerClickListener implements MouseListener {
	
	private JList list;
	
	public ExplorerClickListener(TemporaryExplorerPane view) {
		this.list = view.getJList();
	}

	@Override
	public void mouseClicked(MouseEvent e) { // 마우스가 클릭 되었을 때 
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2) 
			popUpRepo();
		else if (e.getClickCount() == 1) {
			View.TemporaryExplorerPane.repo = list.getSelectedValue().toString();
			delRepo();
			//MainFrame frame = new MainFrame();
			//frame.setVisible(true);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//delRepo();
	}
	
	public void popUpRepo() {
		String repo = list.getSelectedValue().toString();
		String repoPath = "." + File.separator + "GitHub";
			
		//System.out.println(repo);
			
		File f = new File(repoPath + File.separator + repo + File.separator + "address.txt");
			
		System.out.println(f);
			
		char[] buf = new char[1024];
		try {
			FileReader fr = new FileReader(f);
			
			fr.read(buf);
			fr.close();
		} catch(FileNotFoundException e1) {
			System.out.println("error1");
			e1.getStackTrace();
		} catch (IOException e1) {
			System.out.println("error2");
			e1.getStackTrace();
		}
		String repoInfo = String.copyValueOf(buf).trim();
		
		new RepoInfoDialog(repoInfo);
		
	}
	
	public void delRepo() {
		String repoName = list.getSelectedValue().toString();
		
		TemporaryExplorerPane view = new TemporaryExplorerPane();
		view.connectToDel(repoName);
		//view.getRepoName(repoName);
		
		//new DelRepoClickListener(view, repoName).delReposit(repoName);
	}
	
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
