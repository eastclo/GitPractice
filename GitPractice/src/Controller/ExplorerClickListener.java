package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class ExplorerClickListener implements MouseListener {
	

	@Override
	public void mouseClicked(MouseEvent e) { // 마우스가 클릭 되었을 때 
		// TODO Auto-generated method stub
		/*
		 * 이거 Repository 리스트가 여러 개 나올텐데 레퍼런스 하나로 해도 되나요?
		 * */
		JButton linkButton = (JButton) e.getSource();
		
		// 리스너 객체 생성
		ExplorerClickListener listener = new ExplorerClickListener();
		
		// Model에 정의되어 있는 Remote Repository 리스트
		// Model.RemoteRepository rmrepo = new Model.getRemoteRepository();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// 클릭했을 때 말고도 마우스가 올라갔을 때 Repository의 정보?가 간단하게 나오게 하는 건 어떤가요?
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
