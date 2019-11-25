package Controller;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import View.CommandIndexPane;

public class UpdateCommandIndexPane {
	
	private JList list;

	public UpdateCommandIndexPane(CommandIndexPane view) {
		this.list = view.getJList();
	}
	
	public void setCommandList() {
		//명령어 종류 가져오기
		Model.CommandExplainList cmdlist = new Model.CommandExplainList();
		String cmds[] = cmdlist.getCommandList();
		
		/*list에 목록 추가하기 위한 임시 모델 생성
		* list엔 addElement 같은 메소드가 없고 완성된 아이템 리스트를 넘겨 줘야 한다.
		*/
		DefaultListModel listModel = new DefaultListModel();
		
		for(String cmd : cmds) {
			listModel.addElement(cmd);
		}
		list.setModel(listModel);
		list.repaint();
	}
}
