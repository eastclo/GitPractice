package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class ExplorerClickListener implements MouseListener {
	
	MouseEvent e;
	
	public ExplorerClickListener() {
		// Model에 정의되어 있는 Remote Repository 리스트
		Model.RemoteRepository rmrepo = new Model.RemoteRepository();
	}

	@Override
	public void mouseClicked(MouseEvent e) { // 마우스가 클릭 되었을 때 
		// TODO Auto-generated method stub
		mkRepository();
		
		
	}
	
	private void mkRepository() {
		JButton linkButton = (JButton) e.getSource();
		// 리스너 객체 생성
		ExplorerClickListener listener = new ExplorerClickListener();
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
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
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
