package View;

import java.awt.*;
import javax.swing.*;
import Controller.CommandInputListener;
import Controller.GobackButtonListener;

public class CommandInputPane extends JPanel{
	
	public CommandInputPane(){
		JPanel CommandInputPane = new JPanel();
		CommandInputPane.setBounds(236, 0, 539, 409);
		
		MainFrame.contentPane.add(CommandInputPane);
		CommandInputPane.setLayout(null);
		
		JLabel lblCommandInput = new JLabel("��ɾ� �Է�");
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
		input.addActionListener(new CommandInputListener()); //������ ����
		
		ImageIcon BackIcon = new ImageIcon(".\\..\\res\\back1.png");
		JButton btnBack = new JButton(BackIcon);//�ڷΰ��� ��ư �̹��� �߰�
		btnBack.setName("back");	//������ ���ο��� �ڷΰ��� ��ư���� �ν��ϱ� ���� name ����
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(372, 11, 90, 20);
		btnBack.addActionListener(new GobackButtonListener());	//������ ����

		CommandInputPane.add(btnBack);
		
		ImageIcon ForwardIcon = new ImageIcon(".\\..\\res\\forward1.png"); //�����ΰ��� ��ư �߰�
		JButton btnForward = new JButton(ForwardIcon);//�ڷΰ��� ��ư �̹��� �߰�
		btnForward.setName("forward");	//������ ���ο��� �����ΰ��� ��ư���� �ν��ϱ� ���� name ����
		btnForward.setBorderPainted(false);
		btnForward.setContentAreaFilled(false);
		btnForward.setBounds(442, 11, 83, 20); 
		btnForward.addActionListener(new GobackButtonListener());	//������ ����
		CommandInputPane.add(btnForward);
		
		JButton btnUpload = new JButton("upload");
		btnUpload.setBounds(460, 371, 65, 26);
		CommandInputPane.add(btnUpload);
	

	}
}
