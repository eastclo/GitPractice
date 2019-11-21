package View;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import Controller.DocumentUploadListener;
import Controller.NewRepoClickListener;

public class TemporaryExplorerPane extends JPanel{
	
	public TemporaryExplorerPane() {
		
		JPanel TemporaryExplorerPane = new JPanel();
		TemporaryExplorerPane.setBounds(777, 0, 265, 604);
		
		MainFrame.contentPane.add(TemporaryExplorerPane);
		TemporaryExplorerPane.setLayout(null);
		
		JLabel lblGitSimul = new JLabel("���� ����� â");
		lblGitSimul.setBounds(14, 12, 106, 18);
		TemporaryExplorerPane.add(lblGitSimul);
		
		JScrollBar scrollGitSimul = new JScrollBar();
		scrollGitSimul.setBounds(238, 42, 13, 550);
		TemporaryExplorerPane.add(scrollGitSimul);
		
		JList Repository = new JList(); //����Ҹ� ����Ʈ�� ���������� �߰��ؼ� �����ִ� ����
		Repository.setBounds(14, 42, 237, 550);
		TemporaryExplorerPane.add(Repository);
		
		// Repository�� �������� ���� new ��ư�� ��������ϴ�.
		// NewRepoClickListener�� �����˴ϴ�.
		JButton btnNewRepo = new JButton("new");
		btnNewRepo.setBounds(200, 11, 50, 20);
		TemporaryExplorerPane.add(btnNewRepo);
		// ������ ����  
		btnNewRepo.addMouseListener(new NewRepoClickListener());
		
		// LoadClickListener�� �����Ǵ� load ��ư�Դϴ�.
		// �������ڸ��� ������� repository ������ �ҷ����� ������ ������ ��� 
		// �� ��ư�� ������ �����Դϴ�.
		JButton btnLoadRepo = new JButton("load");
		btnLoadRepo.setBounds(140, 11, 55, 20);
		TemporaryExplorerPane.add(btnLoadRepo);
		btnLoadRepo.addActionListener(new DocumentUploadListener());
		
		
	}

}
