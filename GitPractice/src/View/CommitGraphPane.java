package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.json.simple.parser.ParseException;

import Controller.CommitFunction;
import java.awt.Panel;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CommitGraphPane extends JPanel{


	public CommitGraphPane() {
	JPanel CommitGraphPane = new JPanel();
	CommitGraphPane.setBounds(236, 409, 539, 195);
	MainFrame.contentPane.add(CommitGraphPane);
	CommitGraphPane.setLayout(null);
	
	JLabel lblGraphResult = new JLabel("커밋 내역");
	lblGraphResult.setBounds(14, 12, 78, 18);
	CommitGraphPane.add(lblGraphResult);
	//CommitGraphPane.add(DrawingGraph);
	
	JScrollPane graphScroll = new JScrollPane();
	graphScroll.setBounds(14, 38, 511, 145);
	CommitGraphPane.add(graphScroll);
	
	CommitCanvas canvas = new CommitCanvas();
	canvas.setBackground(Color.WHITE);
	graphScroll.setViewportView(canvas);
	
	}
	
	
}