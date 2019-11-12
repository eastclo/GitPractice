package Controller;
import java.awt.*;
import java.awt.ActiveEvent;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;
import javax.swing.JFileChooser.*;
import javax.swing.event.*;



public class DocumentUploadListener implements ActionListener {
	JFileChooser fc;
	JFileChooser fcd;
	File file;
	File workSpace;
	public String fileName;
	public String fileIn;
	boolean wsSetting=false;
	
	public DocumentUploadListener(){
		fc = new JFileChooser();
		fcd = new JFileChooser();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		workspaceSetting();
		menuOpen();
		// TODO Auto-generated method stub
		
	}
	
	public void menuOpen() {
		int ret = fc.showOpenDialog(null);
		
		if(ret!=JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		file=fc.getSelectedFile();
		fileName = file.getName();
		fileIn =getTextFromFile(file);
		
		String wsFile = workSpace + "\\" + fileName;
		JOptionPane.showMessageDialog(null, fileIn);
		try {
			FileWriter fileWriter = new FileWriter(wsFile);
			fileWriter.write(fileIn);
			fileWriter.close();
		}catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  }
		
		
	}
	public String getTextFromFile(File txtFile){
	    String text = "";
	 
	    BufferedReader br = null;
	    try{
	      br = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile)));
	      String line;
	      while((line = br.readLine()) != null){
	        text= text + line + "\n";
	      }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		return text;
	}
	public void workspaceSetting() {
		if(!wsSetting)
		{
			JOptionPane.showMessageDialog(null, "WorkSpace 경로가 설정되어 있지 않습니다.");	
			fcd.setCurrentDirectory(new File("C:\\"));
			fcd.setFileSelectionMode(fcd.DIRECTORIES_ONLY);

			int ret = fcd.showOpenDialog(null);
			
			if(ret!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
				return;
			}
			workSpace=fcd.getSelectedFile();
			wsSetting=true;
		}
		
	}

}
