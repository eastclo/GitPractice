package Controller;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import View.TemporaryExplorerPane;

public class DelRepoClickListener implements MouseListener{
	
	String textFieldValue;
	String repoName;
	
	private TemporaryExplorerPane view;
	
	public DelRepoClickListener() {
		
	}
	
	public DelRepoClickListener(TemporaryExplorerPane view) {
		this();
	}
	
	public DelRepoClickListener(TemporaryExplorerPane view, String repoName) {
		this(view);
		this.repoName = repoName;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//JTextField txtField = (JTextField) e.getSource();
		//textFieldValue = txtField.getText();
		delReposit(repoName);
	}
	
	public void delReposit(String rn) {
		int result = JOptionPane.showConfirmDialog(null, "파일을 삭제하시겠습니까?",
												"파일 삭제", JOptionPane.YES_NO_OPTION);
		
		if (result == JOptionPane.CLOSED_OPTION) { //사용자가 창을 닫은 경우  
			
		} else if (result == JOptionPane.YES_OPTION) { // 사용자가 예 를 선택한 경우 
			File file = new File("." + File.separator + "GitHub" + File.separator + rn );
			
			if (file.exists()) {
				if (file.isDirectory()) {
					File[] files = file.listFiles();
					
					for (int i = 0; i < files.length; i++) {
						if (files[i].delete()) {
							System.out.println(files[i].getName() + "파일을 삭제했습니다.");
						} else {
							System.out.println(files[i].getName() + "파일을 삭제하는 데 실패했습니다.");
						}
					}
				}
			} else {
				System.out.println("파일이 존재하지 않습니다.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Repository 삭제를 취소했습니다.", "삭제 취소", JOptionPane.PLAIN_MESSAGE);
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
