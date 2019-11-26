package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Controller.ManagementSetting;

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
			JOptionPane.showMessageDialog(null, "WorkSpace 경로가 설정되어 있지 않습니다.");	
			fcd.setCurrentDirectory(new File("."));
			fcd.setFileSelectionMode(fcd.DIRECTORIES_ONLY);
				int ret = fcd.showOpenDialog(null);
			
			if(ret!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
				return;
				}
			ManagementSetting.workspace=fcd.getSelectedFile();
			
		}
	
	}

}
