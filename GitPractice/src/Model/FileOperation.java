package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperation {
	public static void moveFile(String filePathStartingPoint, String filePathDestination , String fileName) throws IOException {
		try {
			FileInputStream fin = new FileInputStream(filePathStartingPoint + File.separator + fileName);
			FileOutputStream fout = new FileOutputStream(filePathDestination + File.separator + fileName);
			int tmp = 0;
            while ((tmp = fin.read()) != -1) {
                fout.write(tmp);                
            }
            fin.close();
            fout.close();
            
            deleteFile(filePathStartingPoint, fileName);	//원본 파일 삭제
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(String filePath, String fileName) {
		File del = new File(filePath + File.separator + fileName);
        del.delete();
	}
}