package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import Controller.BranchFunction;
import Controller.CommitFunction;
import Controller.WorkspaceSetting;
import Model.CommitArray;
import Model.CurrentLocation;

public class SettingMenuBar extends JMenuBar {
	
	JFileChooser fcd;
	
	public SettingMenuBar(){
		JMenu menu = new JMenu("Setting");
		
		
		JMenuItem openworkspace = new JMenuItem("workSpace");
		openworkspace.addActionListener(new workspaceAdd());
		menu.add(openworkspace);
		
		JMenuItem openauthor = new JMenuItem("Author");
		openauthor.addActionListener(new AuthorSetting());
		menu.add(openauthor);
		
		
		this.add(menu);
		

		BranchFunction bf = new BranchFunction();
		if(!new File("." + File.separator + "root").exists())
		{
			new File("." + File.separator + "root").mkdir();
			WorkspaceSetting.settingComboBox("."+File.separator+"root");
		}
		else if(!new File("."+File.separator+"root"+File.separator+".git").exists()){
			WorkspaceSetting.settingComboBox("."+File.separator+"root");
		}
		else
		{
			WorkspaceSetting.settingComboBox("."+File.separator+"root");
			BranchFunction bf1 = new BranchFunction();
			CurrentLocation.changeBranch("master");
			bf1.BranchListOpen();
			try {
				CommitFunction cm = new CommitFunction();
				cm.commitListOpen();
				int checki=0;
				for(int i=0;i<cm.BranchArray.size();i++)
				{
					if(cm.BranchArray.get(i).equals(CurrentLocation.getBranch()))
					{
						checki=i;
					}
				}
				CurrentLocation.HEADLocation=checki;
				CommitGraphPane.canvas.repaint();
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		}
	}

	//Workspace의 경로를 세팅. 폴더만 선택가능
	class workspaceAdd implements ActionListener {
		File workspaceName;
		workspaceAdd(){
		}
		public void actionPerformed(ActionEvent e) {

			String workspaceName = JOptionPane.showInputDialog(null, "New workspace", "workspace 생성",
									JOptionPane.PLAIN_MESSAGE);
			
			if (workspaceName != null) {
				File workPath = new File("." + File.separator + "root" + File.separator + workspaceName);
				if(!workPath.exists())
				{
					new File("." + File.separator + "root" + File.separator + workspaceName).mkdir();
					WorkspaceSetting.settingComboBox("." + File.separator + "root" + File.separator + workspaceName);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "이미 존재하는 workspace입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
					System.out.println("error");
				}
			} 		
		}
	}
	class AuthorSetting implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new AuthorDialog();
		}
	}
	
	class AuthorDialog extends JFrame{
		public JTextField nameText;
		public JTextField addressText;
		public JButton btn;
		
		public AuthorDialog() {
			JPanel pane = new JPanel();
			pane.setBounds(100,10,500,180);
			
			this.add(pane);
			pane.setLayout(null);

			nameText = new JTextField();
			nameText.setBounds(100,10,500,30);
			pane.add(nameText);
			
			addressText = new JTextField();
			addressText.setBounds(100,60,500,30);
			pane.add(addressText);
			
			JLabel name = new JLabel("Author Name");
			name.setBounds(10,10,100,30);
			pane.add(name);

			JLabel address = new JLabel("Author Address");
			address.setBounds(10,60,100,30);
			pane.add(address);

			btn = new JButton("확인");
			btn.setBounds(300,120,100,30);
			pane.add(btn);
			btn.addActionListener(new getAuthor(this));
			
			this.setMinimumSize(new Dimension(700,200));
			this.pack();
			this.setLocation(200,200);
			this.setVisible(true);
		}
		class getAuthor implements ActionListener{
			AuthorDialog ad;
			public getAuthor(AuthorDialog authorDialog) {
				ad = authorDialog;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				CurrentLocation.changeAuthorname(nameText.getText());
				CurrentLocation.changeAuthorAddress(addressText.getText());
				ad.dispose();
			}
		}
	}

}
