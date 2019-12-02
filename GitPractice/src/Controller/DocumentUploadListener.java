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

import Model.CurrentLocation;
import View.CommandInputPane;


//Workspace로 파일을 업로드하는 Controller
public class DocumentUploadListener implements ActionListener {
	JFileChooser fc;
	File file;
	public String fileName;
	public String fileIn;
	
	public DocumentUploadListener(){
		fc = new JFileChooser();
		
	}
	
	@Override
	//버튼 Click시 실행.
	public void actionPerformed(ActionEvent e) {
		
		//업로드할 파일 Open
		fileOpen();
		// TODO Auto-generated method stub
		
	}
	
	public void fileOpen() {
		int ret = fc.showOpenDialog(null);
		
		if(ret!=JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		file=fc.getSelectedFile();
		fileName = file.getName();
		fileIn =getTextFromFile(file);
		String wsFile;
		if(new File(CurrentLocation.workspace.getPath()+File.separator+".git").exists())
			wsFile= CurrentLocation.workspace.getPath()+File.separator+".git"+File.separator+CurrentLocation.getBranch()+File.separator+"workspace"+File.separator+fileName;
		else
			wsFile=CurrentLocation.workspace.getPath()+File.separator+fileName;
		JOptionPane.showMessageDialog(null, fileIn);
		try {
			FileWriter fileWriter = new FileWriter(wsFile);
			fileWriter.write(fileIn);
			fileWriter.close();
		}catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  }
		CommandInputPane.allCommandtxt.setText(fileIn);
		
		
	}
	//CommandInputPane에 출력할 파일내용을 String형태로  Return
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

}
