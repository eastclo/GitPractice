package View;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import Controller.BranchFunction;
import Controller.CommandInputListener;
import Controller.CommitFunction;
import Controller.DocumentUploadListener;
import Controller.GobackButtonListener;
import Model.CurrentLocation;

import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.json.simple.parser.ParseException;


public class CommandInputPane extends JPanel{
	
	public static JTextArea allCommandtxt;	
	private static final String EXIT_ON_CLOSE = null;
	private JButton btnBack; 
	private static JComboBox repoComboBox;
	private static DefaultComboBoxModel comboModel;
	private static JScrollPane inputScroll = new JScrollPane();
	private static JTextField input;

	public CommandInputPane(){
		
		JPanel CommandInputPane = new JPanel();
		CommandInputPane.setBounds(236, 0, 539, 409);
		
		MainFrame.contentPane.add(CommandInputPane);
		CommandInputPane.setLayout(null);
		
		JLabel lblCommandInput = new JLabel("명령어 입력");
		lblCommandInput.setBounds(14, 12, 100, 18);
		CommandInputPane.add(lblCommandInput);
		
		allCommandtxt = new JTextArea(); //모든 명령어 창
		allCommandtxt.setBounds(14, 40, 511, 325);
		CommandInputPane.add(allCommandtxt);
		inputScroll.setViewportView(allCommandtxt);  //스크롤바->스크롤페인 변경(오류수정)
		inputScroll.setBounds(14, 42, 511, 325);
		CommandInputPane.add(inputScroll);
		/*
		allCommandtxt = new JTextArea();
		allCommandtxt.setBounds(14, 40, 511, 325);
		CommandInputPane.add(allCommandtxt);

		JScrollBar scrollInput = new JScrollBar();
		scrollInput.setBounds(498, 0, 13, 325);
		allCommandtxt.add(scrollInput);*/

  
		input = new JTextField(); //명령어 1줄 입력창
		input.setBounds(14, 373, 429, 24);
		CommandInputPane.add(input);
		input.addActionListener(new CommandInputListener()); //리스너 적용
		
		ImageIcon BackIcon = new ImageIcon("."+File.separator+".."+File.separator+"res"+File.separator+"back1.png");
		JButton btnBack = new JButton(BackIcon);//뒤로가기 버튼 이미지 추가
		btnBack.setName("back");	//리스너 내부에서 뒤로가기 버튼임을 인식하기 위한 name 설정
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(372, 11, 90, 20);
		btnBack.addActionListener(new GobackButtonListener());	//리스너 적용
		
		CommandInputPane.add(btnBack);
		
		ImageIcon ForwardIcon = new ImageIcon("."+File.separator+".."+File.separator+"res"+File.separator+"forward1.png"); //앞으로가기 버튼 추가
		JButton btnForward = new JButton(ForwardIcon);//뒤로가기 버튼 이미지 추가
		btnForward.setName("forward");	//리스너 내부에서 앞으로가기 버튼임을 인식하기 위한 name 설정
		btnForward.setBorderPainted(false);
		btnForward.setContentAreaFilled(false);
		btnForward.setBounds(442, 11, 83, 20); 
		btnForward.addActionListener(new GobackButtonListener());	//리스너 적용
		CommandInputPane.add(btnForward);
		
		JButton btnUpload = new JButton("upload");
		btnUpload.setBounds(448, 372, 77, 26);
		CommandInputPane.add(btnUpload);
		btnUpload.addActionListener(new DocumentUploadListener());
		
		comboModel = new DefaultComboBoxModel();
		repoComboBox = new JComboBox();
		repoComboBox.setModel(comboModel); //clone할 시 저장소를 배열에 추가하도록 해야 함, 비워둠
		repoComboBox.setBounds(201, 9, 100, 24);
		repoComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(repoComboBox.getSelectedItem()!=null)
				{
					CurrentLocation.workspace=new File(repoComboBox.getSelectedItem().toString());
					if(new File(repoComboBox.getSelectedItem().toString()+File.separator+".git").exists())
					{
						BranchFunction bf = new BranchFunction();
						CurrentLocation.changeBranch("master");
						bf.BranchListOpen();
						if(new File(repoComboBox.getSelectedItem().toString()+File.separator+".git"+File.separator+"CommitList.ini").exists())
						{
							try {
								new CommitFunction().commitListOpen();
							} catch (IOException | ParseException e1) {
								e1.printStackTrace();
							}
						}
					}
					
				}
			}
		});
		CommandInputPane.add(repoComboBox);

		JLabel lblSelectRepo = new JLabel("저장소 선택"); //저장소를 선택하는 라벨로 변경
		lblSelectRepo.setBounds(119, 12, 83, 18); 
		CommandInputPane.add(lblSelectRepo);


	}
	public static JTextField getTextField() {
		return input;
	}

	public static JComboBox getComboBox() {
		return repoComboBox;
	}
	
	public static DefaultComboBoxModel getComboModel() {
		return comboModel;
	}
	
	public static JScrollPane getScrollPane() {
		return inputScroll;
	}
	
		 class buttonPress implements ActionListener {
		        @Override
		        public void actionPerformed(ActionEvent arg0) {
		            CommandInputPane  pF = new CommandInputPane();
		          // btnBack.dispose();
		    }
		 }	
	 }
