package View;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	static JPanel contentPane;
	static JTextField textField;
		
	//실행
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//메인 프레임 생성
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		new CommandIndexPane();
		new CommandInputPane();		
		new CommitGraphPane();
		new CommitCanvas(); //그래픽 작업 중, 커밋그래프를 그려줌
		new TemporaryExplorerPane();
		this.setJMenuBar(new SettingMenuBar());

		
		this.addWindowListener((WindowListener) new WindowAdapter(){	//창을 닫으면 백업파일 삭제 후 프로그램 종료
            public void windowClosing(WindowEvent e) { 
            	File backup = new File(".","BackUp");
            	Model.FileOperation.deleteFile(backup);
            	System.exit(0);
            }
		});
	}
	
	
}
