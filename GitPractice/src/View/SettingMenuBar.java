package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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

import Controller.BranchFunction;
import Model.CurrentLocation;

public class SettingMenuBar extends JMenuBar {
	
	JFileChooser fcd;
	
	public SettingMenuBar(){
		JMenu menu = new JMenu("Setting");
		
		
		JMenuItem openworkspace = new JMenuItem("workSpace");
		openworkspace.addActionListener(new workspaceSetting());
		menu.add(openworkspace);
		
		JMenuItem openauthor = new JMenuItem("Author");
		openauthor.addActionListener(new AuthorSetting());
		menu.add(openauthor);
		
		
		this.add(menu);
		

		BranchFunction bf = new BranchFunction();
		if(!new File("." + File.separator +"Commit" + File.separator +"BranchArray.ini").exists())
		{
			CurrentLocation.BranchList=new ArrayList<String>();
			CurrentLocation.changeBranch("master");
			CurrentLocation.addBranch("master");
			List<String> branchList = CurrentLocation.getBranchList();
			bf.setArray(branchList);
			bf.BranchListSave();
		}
		bf.BranchListOpen();
	}

	//Workspace의 경로를 세팅. 폴더만 선택가능
	class workspaceSetting implements ActionListener {
		JFileChooser fcd;
		workspaceSetting(){
			fcd=new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			fcd.setCurrentDirectory(new File("."));
			fcd.setFileSelectionMode(fcd.DIRECTORIES_ONLY);
				int ret = fcd.showOpenDialog(null);
			
			if(ret!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
				return;
				}
			CurrentLocation.workspace=fcd.getSelectedFile();
			BranchFunction bf = new BranchFunction();
			if(!new File(CurrentLocation.workspace+File.separator+"BranchArray.ini").exists())
			{
				CurrentLocation.BranchList=new ArrayList<String>();
				CurrentLocation.changeBranch("master");
				CurrentLocation.addBranch("master");
				List<String> branchList = CurrentLocation.getBranchList();
				bf.setArray(branchList);
				bf.BranchListSave();
			}
			bf.BranchListOpen();
			System.out.println(CurrentLocation.getBranchList().toString());
			
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
				System.out.println(CurrentLocation.AuthorName + " " + CurrentLocation.AuthorAddress);
				ad.dispose();
			}
		}
	}

}
