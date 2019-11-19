package Controller;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.File;
import java.io.IOException;

/*
 new 버튼을 누르면 깃헙에서처럼 창이 뜨면서
 새로운 Repository를 생성할 수 있게 하는 클래스입니다.
 */

public class NewRepoClickListener extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	String input = "";
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		getRepoName();
	}
	
	public void getRepoName() {
		//String[] repoName = new String[100];
		//String[] buttons = {"create", "close"};
		// showInputDialog로는 확인/취소 버튼 문자열 조정이 안되네요ㅠㅠ
		// 방법이 있는지 더 찾아보고 없으면 이 주석 지우겠습니다 
		
		
		input = JOptionPane.showInputDialog(null, "Repository name", "Repository 생성",
								JOptionPane.PLAIN_MESSAGE);
		//System.out.println(input);
		
		if (input != null) {
			dirCreate();
			repoCreate();
		} 		
		
	}
	
	public void dirCreate() { // 로컬 저장소에 디렉토리를 만들어주는 함수입니다.
		String repoDir = "C:\\" + input;
		String repoFile = input;
		File fdir = new File(repoDir); // 파일 폴더를 다루기 위한 객체
		File file = new File(repoFile);
		
		if (!fdir.exists()) {
			if (fdir.mkdirs()) {
				//폴더 생성  
				if (!file.exists()) {
					try {
						if (file.createNewFile()) {
							// repository 생성  
						} else {
							System.out.println("Repository 생성에 실패했습니다.");
						}
					} catch (IOException e) {
						//TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("같은 이름의 Repository가 이미 존재합니다.");
				}
			} 
		}
		
	}
	
	public void repoCreate() { // GUI 창에서 새로운 JTextArea 창을 띄워주는 함수입니다.
		String init = "Quick setup - if you've done this kind of thing before" + 
				"/n HTTPS : https://github.com/users/" + input  + ".git"
				+ "\n\n ...or create a new repository on the command line"
				+ "\n echo '# " + input + "' >> README.md"
				+ "\n git init \n git add README.md \n git commit -m " + "first commit"
				+ "\n git remote and origin https://github.com/user/" + input + ".git"
				+ "\n git push -u origin master"
				+ "\n\n ...or push an existing repository from the command line"
				+ "\n git remote add origin https://github.com/user/" + input + ".git"
				+ "\n git push -u origin master";
		
		JTextArea ta = new JTextArea(init);
		JScrollPane scrollPane = new JScrollPane(ta);
		
		// 일단은 repository 생성 후 뜨는 첫 화면만 구성해봤습니다  
		
		
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
