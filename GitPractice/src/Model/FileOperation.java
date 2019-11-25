package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FileOperation {
	public static void moveFileAll(String filePathStartingPoint, String filePathDestination) {
		File source = new File(filePathStartingPoint);
		File target = new File(filePathDestination);
		copyFileAll(source, target);	//source폴더에서 target폴더로 전부 복사
		deleteFile(source);	//source폴더 파일 전부 삭제
	}
	
	 public static void deleteFile(File target) {
			File folder = target;
			try {
				if(folder.exists()){	//경로에 디렉토리가 존재하는지 확인
				    File[] folder_list = folder.listFiles();
						
				    for (int i = 0; i < folder_list.length; i++) {
				    	if(folder_list[i].isFile()) {	//파일이면 그냥 삭제
				    		folder_list[i].delete();
				    	}else {
				    		deleteFile(folder_list[i]);	//디렉토리면 재귀호출
				    	}
				    	folder_list[i].delete();	//디레토리 삭제
				    }
				}
			} catch (Exception e) {
				e.getStackTrace();
			}
	 }
	
	public static void copyFileAll(File sourceF, File targetF){
		File[] target_file = sourceF.listFiles();
		for (File file : target_file) {
			File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
			if(file.isDirectory()){	//디렉토리면 만들고 재귀호출
				temp.mkdir();
				copyFileAll(file, temp);
			} else {
				FileInputStream fis = null;
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(temp) ;
					byte[] b = new byte[4096];
					int cnt = 0;
					while((cnt=fis.read(b)) != -1){
						fos.write(b, 0, cnt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					try {
						fis.close();
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}