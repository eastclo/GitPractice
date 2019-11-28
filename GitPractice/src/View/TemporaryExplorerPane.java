package View;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import Controller.DelRepoClickListener;
import Controller.ExplorerClickListener;
import Controller.LoadRepository;
import Controller.NewRepoClickListener;

public class TemporaryExplorerPane extends JPanel{
	private JScrollBar scrollIndex;
	private JList repoList;
	
	public TemporaryExplorerPane() {
		
		JPanel TemporaryExplorerPane = new JPanel();
		TemporaryExplorerPane.setBounds(777, 0, 265, 604);
		
		MainFrame.contentPane.add(TemporaryExplorerPane);
		TemporaryExplorerPane.setLayout(null);
		
		JLabel lblGitSimul = new JLabel("모의 깃허브 창");
		lblGitSimul.setBounds(14, 12, 106, 18);
		TemporaryExplorerPane.add(lblGitSimul);
		
		JScrollBar scrollGitSimul = new JScrollBar();
		scrollGitSimul.setBounds(238, 42, 13, 550);
		TemporaryExplorerPane.add(scrollGitSimul);
		
		
		// Repository를 가져오기 위한 new 버튼을 만들었습니다.
		// NewRepoClickListener와 연동됩니다.
		JButton btnNewRepo = new JButton("new");
		btnNewRepo.setBounds(190, 11, 60, 20);
		TemporaryExplorerPane.add(btnNewRepo);
		btnNewRepo.addMouseListener(new NewRepoClickListener(this));
		
		// LoadClickListener와 연동되는 load 버튼입니다.
		// 시작하자마자 저장소의 repository 파일을 불러오는 쪽으로 결정될 경우 
		// 이 버튼은 삭제될 예정입니다.
		JButton btnDelRepo = new JButton("delete");
		btnDelRepo.setBounds(122, 11, 60, 20);
		TemporaryExplorerPane.add(btnDelRepo);
		btnDelRepo.addMouseListener(new DelRepoClickListener());
		
		repoList = new JList();
		repoList.setBounds(14, 50, 237, 550);
		TemporaryExplorerPane.add(repoList);
		repoList.addMouseListener(new ExplorerClickListener(this));
		
		LoadRepository init = new LoadRepository(this);
		init.setRepositoryList();
		
		//repoList.addMouseListener(new DelRepoClickListener());
		
	}
	
	public JList getJList() {
		return repoList;
	}

}
