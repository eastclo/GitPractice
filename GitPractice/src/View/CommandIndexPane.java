package View;

import java.awt.*;
import javax.swing.*;

import Controller.CommandSearchListener;
import Controller.CommandMouseEventListener;
import Controller.UpdateCommandIndexPane;

import java.util.Scanner;

public class CommandIndexPane  extends JPanel{
	
	//모든 element 멤버변수로 선언
	private JPanel CommandIndexPane;
	private JLabel lblGitCommand;
	private JScrollBar scrollIndex;
	private JList list;
	
	public CommandIndexPane() {
	
	CommandIndexPane = new JPanel();
	CommandIndexPane.setBounds(0, 0, 236, 604);
	MainFrame.contentPane.add(CommandIndexPane);
	CommandIndexPane.setLayout(null);
	
	lblGitCommand = new JLabel("git 명령어 검색");
	lblGitCommand.setBounds(14, 12, 132, 18);
	CommandIndexPane.add(lblGitCommand);
	
	MainFrame.textField = new JTextField();
	MainFrame.textField.setBounds(14, 42, 208, 24);
	CommandIndexPane.add(MainFrame.textField);
	MainFrame.textField.setBackground(Color.WHITE);
	MainFrame.textField.setColumns(10);
	
	scrollIndex = new JScrollBar();
	scrollIndex.setBounds(209, 78, 13, 514);
	CommandIndexPane.add(scrollIndex);
	
	list = new JList();
	list.setBounds(14, 78, 208, 514);
	list.addMouseListener(new CommandMouseEventListener(this)); //명령어 선택 리스너 적용
	CommandIndexPane.add(list);
	
	UpdateCommandIndexPane init = new UpdateCommandIndexPane(this);
	init.setCommandList();	//list에 명령어 목록 나열하기 위한 controller 호출
	
	MainFrame.textField.addActionListener(new CommandSearchListener(this));	//명령어 검색 리스너 적용	
	}
	
	public JList getJList() {
		return list;
	}
}