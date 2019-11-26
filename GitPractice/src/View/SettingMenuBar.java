package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Controller.BranchFunction;
import Model.CurrentLocation;

public class SettingMenuBar extends JMenuBar {
	
	JFileChooser fcd;
	
	public SettingMenuBar(){
		JMenu menu = new JMenu("Setting");
		JMenuItem openItem = new JMenuItem("workSpace");
		openItem.addActionListener(new workspaceSetting());
		menu.add(openItem);
		this.add(menu);
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

}
