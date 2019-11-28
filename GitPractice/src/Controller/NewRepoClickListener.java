package Controller;

import java.awt.event.MouseEvent;


import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import View.TemporaryExplorerPane;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;



/*
 new 버튼을 누르면 깃헙에서처럼 창이 뜨면서
 새로운 Repository를 생성할 수 있게 하는 클래스입니다.
 */

public class NewRepoClickListener extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	String input = "";
	private TemporaryExplorerPane view;
	
	public NewRepoClickListener (TemporaryExplorerPane view) {
		this.view = view;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		getRepoName();
	}
	
	public void getRepoName() {
		input = JOptionPane.showInputDialog(null, "Repository name", "Repository 생성",
								JOptionPane.PLAIN_MESSAGE);
		
		if (input != null) {
			dirCreate();
		} 		
		
	}
	
	public void dirCreate() { // 로컬 저장소에 디렉토리를 만들어주는 함수입니다.
		String repoName = input;
		String commonFilePath = "." + File.separator + "GitHub";
		
		File fdir = new File(commonFilePath, repoName);
		
		
		
		String adrText = "https://github.com/username/" + input;
		File file = new File(commonFilePath + File.separator + repoName, repoName);
		
		if (!fdir.exists()) {
			if (fdir.mkdirs()) {
				try {
				    OutputStream output = new FileOutputStream(commonFilePath + File.separator + repoName + File.separator + "address.txt");
				    String str ="https://github.com/userName/" + repoName;
				    byte[] by=str.getBytes();
				    output.write(by);
						
				} catch (Exception e) {
			            e.getStackTrace();
				}
				
			}
		}
		
		LoadRepository init = new LoadRepository(view);
		init.setRepositoryList();
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
