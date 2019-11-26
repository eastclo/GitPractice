package View;

import javax.swing.JFrame;
import javax.swing.text.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

class AutomaticSizeFrame extends Application {  //크기를 자동으로 조절

	
	@Override
	public void start(Stage stage) {
		try {

			//stage > scene > container > node
			Button button1 = new Button();
			button1.setText("Test");
			button1.prefWidthProperty().bind(pane.widthProperty()); //프로그램 사이즈에 맞춰 자동 조절
			button1.prefHeightProperty().bind(pane.maxHeightProperty());
			pane.getChildren().add(grid);//버튼은 조절 불가
			Button button2 = new Button();
			button2.setText("Test2");
			button2.setMinWidth(50); //최소 사이즈 유지
			button2.setMinHeight(100); //최소 사이즈 유지 
			grid.addRow(0, button1, button2);
			
			//label.setAlignment(Pos.CENTER); //가운데 정렬
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	GridPane grid = new GridPane();
	StackPane pane = new StackPane();
	Scene scene = new Scene(pane, 500, 500);
	
	
		/* 
	* 
	* View auto = new View();
	* auto.setPreserveRatio(true);
	* auto.setFitWidth(1024);
	* 
	* pane.getChilderen().add(auto);
	* 
		*/
}
