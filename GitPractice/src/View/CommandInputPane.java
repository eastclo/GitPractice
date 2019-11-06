package View;

import java.awt.*;
import javax.swing.*;
import Controller.CommandInputListener;

public class CommandInputPane extends JPanel{
	
	public CommandInputPane(){
		JPanel CommandInputPane = new JPanel();
		CommandInputPane.setBounds(236, 0, 539, 409);
		
		MainFrame.contentPane.add(CommandInputPane);
		CommandInputPane.setLayout(null);
		
		JLabel lblCommandInput = new JLabel("명령어 입력");
		lblCommandInput.setBounds(14, 12, 100, 18);
		CommandInputPane.add(lblCommandInput);
		
		JScrollBar scrollInput = new JScrollBar();
		scrollInput.setBounds(512, 40, 13, 325);
		CommandInputPane.add(scrollInput);
		
		JTextArea allCommandtxt = new JTextArea();
		allCommandtxt.setBounds(14, 40, 511, 325);
		CommandInputPane.add(allCommandtxt);
		
		JTextField input = new JTextField();
		input.setBounds(14, 373, 442, 24);
		CommandInputPane.add(input);
		input.addActionListener(new CommandInputListener());	//리스너 적용
		
		JButton btnUpload = new JButton("upload");
		btnUpload.setBounds(460, 371, 65, 26);
		CommandInputPane.add(btnUpload);
	

	}
}
