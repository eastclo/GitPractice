package View;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CommitCanvas extends Canvas{
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graph = (Graphics2D)g;
		
		graph.setColor(Color.RED);
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
		
		
	}

}
