package View;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CommitCanvas extends Canvas{ //커밋 그래프를 그려주는 캔버스
	
	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D graph = (Graphics2D)g;
		
		graph.setColor(Color.RED); //첫째 커밋 내역
		graph.fillOval(20, 30, 70, 35);	
		
		graph.setColor(Color.RED);
		graph.fillOval(140, 30, 70, 35);
		
		graph.setColor(Color.RED);
		graph.fillOval(260, 30, 70, 35);
		
		graph.setColor(Color.RED);
		graph.fillOval(380, 30, 70, 35);
		
		graph.setColor(Color.RED);
		graph.fillOval(500, 30, 70, 35);
		
		graph.setColor(Color.RED);
		graph.fillOval(620, 30, 70, 35);
		
		//현재 스크롤Pane에 인식이 안 되는 현상 발생
		
	}

}
