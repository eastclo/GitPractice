package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import View.CommandExplainDialog;
import View.CommandIndexPane;

public class CommandMouseEventListener implements MouseListener {

	private JList list;
	
	public  CommandMouseEventListener (CommandIndexPane view) {
		this.list = view.getJList();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2) {
			String cmd = list.getSelectedValue().toString();
			
			String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdexplain";
			File f = new File(cmdlistPath,cmd);
			char []buf = new char [1024];
			try {
				FileReader fin = new FileReader(cmdlistPath + File.separator + cmd);
				fin.read(buf);
				fin.close();
			} catch (FileNotFoundException e1) {} catch (IOException e1) {}
			String explain = String.valueOf(buf).trim();
					
			new CommandExplainDialog(explain);
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
