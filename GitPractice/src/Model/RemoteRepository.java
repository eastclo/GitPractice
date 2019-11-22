package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

// Model 내의 Remote Repository 를 저장하고, 불러오기 위한 Model 입니다.

public class RemoteRepository {
		File reposit;
		private String repositName;
		
		public RemoteRepository() {
			getRemoteRepository();
		}
	
		public void getRemoteRepository() {
			// Repository 자체는 로컬 파일로 받아오고
			// 4번 화면에 뜨는 리스트는 List로 구현할 계획입니다.
			reposit = new File("C:\\");
		}
}
