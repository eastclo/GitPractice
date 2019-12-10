package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CommitInputDialog extends JFrame implements ComponentListener {
	
	private int height = 500;
	private int width = 800;
	
	public JTextArea text;
	public JButton btn;
	public JScrollPane first;
	
	public CommitInputDialog() {
		JPanel pane = new JPanel();
		
		this.add(pane);
		pane.setLayout(null);

		text = new JTextArea();
		first = new JScrollPane(text);
		pane.add(first);

		btn = new JButton("입력");
		btn.setSize(100,30);
		
		first.setBounds(15,30,width/6*5,height/4*3);
		btn.setLocation(width/2-50, height-75);
		
		//btn.setBounds(425,400,150,30);
		pane.add(btn);
		
		
		this.add(pane);
		this.setLocation(50,50);
		this.setSize(width,height);
		this.setVisible(true);
		this.setTitle("커밋 내용 입력");
		
		//this.setMinimumSize(new Dimension(1000,500));
		//this.pack();
		//this.setLocation(200,200);
		//this.setVisible(true);
	}
	

	@Override
	public void componentResized(ComponentEvent e) {
		width = e.getComponent().getWidth();
		height = e.getComponent().getHeight();
		first.setBounds(15,30,width-45,height/4*3);
		btn.setLocation(width/2, height-75);
	}


	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
