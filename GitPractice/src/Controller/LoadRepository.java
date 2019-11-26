package Controller;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import View.TemporaryExplorerPane;

public class LoadRepository {
	private JList repoList;
	
	public LoadRepository(TemporaryExplorerPane view) {
		this.repoList = view.getJList();
	}
	
	public void setRepositoryList() {
		Model.RepositoryList rpList = new Model.RepositoryList();
		String repos[] = rpList.getRepoList();
		
		DefaultListModel listModel = new DefaultListModel();
		
		for (String rp : repos) {
			if (rp.charAt(0) == '.') {
				continue;
			}
			listModel.addElement(rp);
		}
		repoList.setModel(listModel);
		repoList.repaint();
	}
	
}
