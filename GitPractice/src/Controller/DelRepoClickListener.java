package Controller;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.io.File;

/*
 Repository를 삭제하는 기능도 필요할 것 같아 추가된 리스너입니다.
 기존 윈도우에 del 버튼이 있는 건 아닙니다.
 Repository의 링크를 클릭하고 새로운 창으로 넘어갔을 때
 흰색 화면의 제일 위쪽에 버튼이 위치하도록 할 예정입니다.
 */

public class DelRepoClickListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		delRepo();
	}
	
	public void delRepo() {
		File file = new File("C:/dir");
		
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
