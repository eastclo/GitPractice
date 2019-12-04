package View;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CommitCanvas extends Canvas{ //커밋 그래프를 그려주는 캔버스
	
	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D graph = (Graphics2D)g;
		
		graph.setColor(Color.YELLOW); //첫째 커밋 내역
		graph.fillOval(20, 40, 70, 35);	
		
		graph.setColor(Color.YELLOW);
		graph.fillOval(140, 40, 70, 35);
		
		graph.setColor(Color.YELLOW);
		graph.fillOval(260, 40, 70, 35);
		
		graph.setColor(Color.YELLOW);
		graph.fillOval(380, 40, 70, 35);
		
		graph.setColor(Color.ORANGE); //master branch
		graph.fillRoundRect(385, 12, 60, 20, 2, 2);
		
		graph.setColor(Color.DARK_GRAY);
		Font font = new Font("Serif", Font.PLAIN, 11);
		graph.setFont(font);
		graph.drawString("master", 400, 25); //추후에 branch 이름을 받아오도록
		
		//현재 스크롤Pane에 인식이 안 되는 현상 발생
		
	}

}
