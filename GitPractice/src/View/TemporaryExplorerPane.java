package View;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class TemporaryExplorerPane extends JPanel{
	
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
		
		JList Repository = new JList(); //저장소를 리스트에 지속적으로 추가해서 보여주는 형식
		Repository.setBounds(14, 42, 237, 550);
		TemporaryExplorerPane.add(Repository);
	}

}
