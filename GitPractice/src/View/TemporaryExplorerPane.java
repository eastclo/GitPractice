package View;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Controller.DelRepoClickListener;
import Controller.ExplorerClickListener;
import Controller.LoadRepository;
import Controller.NewRepoClickListener;

public class TemporaryExplorerPane extends JPanel{
	
	private JList repoList;
	private JScrollPane repoScroll = new JScrollPane();
	private String repositoryName;
	JButton btnDelRepo = new JButton("del");
	DelRepoClickListener delete = new DelRepoClickListener(this, repositoryName);

	
	public TemporaryExplorerPane() {
		
		JPanel TemporaryExplorerPane = new JPanel();
		TemporaryExplorerPane.setBounds(777, 0, 265, 604);
		
		MainFrame.contentPane.add(TemporaryExplorerPane);
		TemporaryExplorerPane.setLayout(null);
		
		JLabel lblGitSimul = new JLabel("모의 깃허브 창");
		lblGitSimul.setBounds(14, 12, 106, 18);
		TemporaryExplorerPane.add(lblGitSimul);
		
		//혹시 JList가 오류가 나면 TextField로 변경하고 싶을 수 있으니 살려 둠
    //MainFrame.textField = new JTextField();
		//MainFrame.textField.setBounds(14, 42, 237, 550);
		//TemporaryExplorerPane.add(MainFrame.textField);
		//MainFrame.textField.setBackground(Color.WHITE);
		//MainFrame.textField.setColumns(10);
		//JList Repository = new JList(); //저장소를 리스트에 지속적으로 추가해서 보여주는 형식
		//Repository.setBounds(14, 42, 237, 550);
		//TemporaryExplorerPane.add(Repository);
		
		// Repository를 가져오기 위한 new 버튼을 만들었습니다.
		// NewRepoClickListener와 연동됩니다.
		JButton btnNewRepo = new JButton("new");
		btnNewRepo.setBounds(190, 11, 60, 20);
		TemporaryExplorerPane.add(btnNewRepo);
		btnNewRepo.addMouseListener(new NewRepoClickListener(this));
		
		//JButton btnDelRepo = new JButton("del");
		btnDelRepo.setBounds(122, 11, 60, 20);
		TemporaryExplorerPane.add(btnDelRepo);
		//DelRepoClickListener delete = new DelRepoClickListener(this, repositoryName);
		btnDelRepo.addMouseListener(delete);
		System.out.println("뭐가 문젠데~~~~" + repositoryName);
		//btnDelRepo.addMouseListener(new DelRepoClickListener(this, repoName));
		
		repoList = new JList();
		repoList.setBounds(14, 42, 237, 550);
		TemporaryExplorerPane.add(repoList);
		repoList.addMouseListener(new ExplorerClickListener(this));
		
		repoScroll.setBounds(14, 42, 237, 550); //저장소 내 스크롤바 수정(세로스크롤 자동 생성)
		TemporaryExplorerPane.add(repoScroll);
		repoScroll.setViewportView(repoList); //자동으로 스크롤 조절
		
		LoadRepository init = new LoadRepository(this);
		init.setRepositoryList();
		
		//repoList.addMouseListener(new DelRepoClickListener());
		
	}
	
	
	public JList getJList() {
		return repoList;
	}

	public void getRepoName(String repoName) {
		// TODO Auto-generated method stub
		this.repositoryName = repoName;
		System.out.println("ㅜㅜㅜ" +repositoryName);
		//btnDelRepo.addMouseListener(delete);
	}

}
