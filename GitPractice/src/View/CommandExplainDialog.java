package View;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class CommandExplainDialog extends JFrame {
	private JLabel jlb = new JLabel("");
	
	public CommandExplainDialog(String str) {
		getContentPane().add(jlb);
		
		jlb.setText(str.toString());
		
		this.setSize(200,100);
		this.setVisible(true);
	}
}
