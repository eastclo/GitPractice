package View;

import javax.swing.JFrame;
import javax.swing.text.View;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class AutomaticSizeFrame extends JFrame { //창의 비율을 자동으로 설정하는 클래스

	//Test
	StackPane pane = new StackPane();
	Scene scene = new Scene(pane, 500, 500);
	
		/* 브랜치 이름 변경
	* 
	* View auto = new View();
	* auto.setPreserveRatio(true);
	* auto.setFitWidth(1024);
	* 
	* pane.getChilderen().add(auto);
	* 
		*/
	
}
