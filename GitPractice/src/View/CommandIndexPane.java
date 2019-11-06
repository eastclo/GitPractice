package View;

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;

public class CommandIndexPane  extends JPanel{
	
	public CommandIndexPane() {
	
	JPanel CommandIndexPane = new JPanel();
	CommandIndexPane.setBounds(0, 0, 236, 604);
	MainFrame.contentPane.add(CommandIndexPane);
	CommandIndexPane.setLayout(null);
	
	JLabel lblGitCommand = new JLabel("git 명령어 검색");
	lblGitCommand.setBounds(14, 12, 132, 18);
	CommandIndexPane.add(lblGitCommand);
	
	MainFrame.textField = new JTextField();
	MainFrame.textField.setBounds(14, 42, 208, 24);
	CommandIndexPane.add(MainFrame.textField);
	MainFrame.textField.setBackground(Color.WHITE);
	MainFrame.textField.setColumns(10);
	
	JScrollBar scrollIndex = new JScrollBar();
	scrollIndex.setBounds(209, 78, 13, 514);
	CommandIndexPane.add(scrollIndex);
	
	JList list = new JList();
	list.setBounds(14, 78, 208, 514);
	CommandIndexPane.add(list);
	}
	
}