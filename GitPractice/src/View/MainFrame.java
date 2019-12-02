package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	static JPanel contentPane;
	static JTextField textField;

		Container container; //그래프 그릴 작업 중
		
		void GraphicsColorFontEx() {
			container = getContentPane();
			MyPanel panel = new MyPanel();
			container.add(panel , BorderLayout.CENTER);
			setVisible(true);
		}
		
		class MyPanel extends JPanel {
		public void paintComponent(Graphics g) { //그래픽 TEST, 지금 실행 자체가 안됨..ㅠ
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.drawString("Test", 30,30);

		}
		}

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
		//new DrawingGraph(); //그래픽 작업 중, 커밋그래프를 그려줌
		new TemporaryExplorerPane();
		this.setJMenuBar(new SettingMenuBar());
		new GraphicsColorFontEx();
		
	}
	
	
}
