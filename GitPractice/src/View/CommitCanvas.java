package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import org.json.simple.parser.ParseException;

import Controller.CommitFunction;
import Model.CurrentLocation;

public class CommitCanvas extends JLabel{ //커밋 그래프를 그려주는 캔버스

	public String test = "hotfix";
	int SIZEX=550;
	int SIZEY=200;
	
	Color[] colorList= {Color.YELLOW,Color.GREEN,Color.BLUE,Color.ORANGE};
	
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D graph = (Graphics2D)g;
		
		/*graph.setColor(Color.YELLOW); //첫째 커밋 내역
		graph.fillOval(20, 40, 70, 35);	
		
		graph.setColor(Color.YELLOW);
		graph.fillOval(140, 40, 70, 35);
		
		graph.setColor(Color.YELLOW);
		graph.fillOval(260, 40, 70, 35);
		
		
		graph.setColor(Color.YELLOW);
		graph.fillOval(380, 40, 70, 35);
		
		graph.setColor(Color.YELLOW);
		graph.fillOval(500, 40, 70, 35);
		
		
		graph.setColor(Color.ORANGE); //master branch
		graph.fillRoundRect(265, 12, 60, 20, 2, 2);
		
		graph.setColor(Color.DARK_GRAY);
		Font font1 = new Font("Serif", Font.PLAIN, 11);
		graph.setFont(font1);
		graph.drawString("master", 280, 25); //추후에 branch 이름을 받아오도록
	
		
		graph.setColor(Color.ORANGE); //hotfix branch
		graph.fillRoundRect(265, 82, 60, 20, 2, 2);
		
		graph.setColor(Color.RED); //HEAD
		graph.drawRoundRect(265, 82, 60, 20, 2, 2);
		
		graph.setColor(Color.DARK_GRAY);
		Font font2 = new Font("Serif", Font.PLAIN, 11);
		graph.setFont(font2);
		graph.drawString(test, 280, 96);*/
		
		//현재 스크롤Pane에 인식이 안 되는 현상 발생

		CommitFunction commit ;
		int x=20;
		int y=40;
		int cnt=0;
		int HEADX=0,HEADY=0;
		if(new File(CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+"CommitList.ini").exists())
		{
			try {
				commit = new CommitFunction();
				commit.commitListOpen();
				
				List<String> readBranch = new ArrayList<String>();
				List<Integer> saveX = new ArrayList<Integer>();
				readBranch.add(commit.BranchArray.get(0));
				saveX.add(x);
				for(int i=0;i<commit.CMArray.size();i++)
				{
					for(int j=0;j<readBranch.size();j++)
					{
						if(!readBranch.get(j).equals(commit.BranchArray.get(i)))
						{
							y+=70;
							cnt++;
						}
						else
						{
							x=saveX.get(j);
							break;
						}
					}
					if(cnt>=readBranch.size())
					{
						x=saveX.get(0);
						saveX.add(x+120);
						readBranch.add(commit.BranchArray.get(i));
					}
					graph.setColor(colorList[cnt%4]); //첫째 커밋 내역
					graph.fillOval(x, y, 70, 35);
					
					graph.setColor(Color.BLACK);
					Font font1 = new Font("Serif", Font.PLAIN, 11);
					graph.setFont(font1);
					graph.drawString(Integer.toString(i), x+30, y+20);

					if(i==CurrentLocation.HEADLocation)
					{
						HEADX=x;
						HEADY=cnt;
					}
					x+=120;
					y=40;
					cnt=0;
					
					
					for(int j=0;j<readBranch.size();j++)
					{
						if(readBranch.get(j).equals(commit.BranchArray.get(i)))
						{
							saveX.set(j, x);
							break;
						}
					}
				}
				SIZEX=x+100;
				SIZEY=readBranch.size()*80+120;
				
				for(int i=0;i<saveX.size();i++)
				{
					graph.setColor(Color.ORANGE); //master branch
					graph.fillRoundRect(saveX.get(i)-115, (i*70)+12, 60, 20, 2, 2);
					
					graph.setColor(Color.DARK_GRAY);
					Font font1 = new Font("Serif", Font.PLAIN, 11);
					graph.setFont(font1);
					graph.drawString(readBranch.get(i), saveX.get(i)-100, (i*70)+25);
				}
				
				graph.setColor(Color.RED); //HEAD
				graph.drawRoundRect(HEADX+5, (HEADY*70)+12, 60, 20, 2, 2);
				
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		}
		else
		{
			super.paint(g);
			graph = (Graphics2D)g;
		}
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZEX,SIZEY);
	}

}
