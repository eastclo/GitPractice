package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import Model.CommandStack;
import Model.CommandListOperation;
import Model.FileOperation;


public class CommandInputListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean check = true;
		//textfield로부터 입력 값을 가져온다.
		JTextField txtField = (JTextField) e.getSource();
		String textFieldValue = txtField.getText();
	
		/* 텍스트 필드에서 명령어, 옵션, 인자값 등으로 분할한 후 명령어와 옵션 만으로 실행할 클래스 파일 결정되게 해야함. 이후 실행 메소드엔 인자값 넘겨주고 */
		String input[] = CommandListOperation.devideInputText(textFieldValue);	//[0]: git, [1]: 명령어  [2],[3],[4]: 명령어 옵션, [5],[6],[7]: 인자 값
		
		if(!input[0].equals("error")) {
			//Model에 정의 되어있는 명령어리스트를 가져옴
			Model.CommandList cmdlist = new Model.CommandList();
			String cmds[] = cmdlist.getCommandList();
			
			//cmdlist를 명령어, 옵션, 인자값 등으로 분할
			String cmdsDevide[][] = new String[cmds.length][];
			for(int i=0; i<cmds.length; i++) {
				cmdsDevide[i] = CommandListOperation.devideInputText(cmds[i]);
			}
			if(!(new File(Model.CurrentLocation.workspace,".git").exists())) {
				if(!input[1].equals("clone") && !input[1].equals("init")) {
					JOptionPane.showMessageDialog(null, "깃 명령어를 사용할 수 없습니다. 깃 레포지토리로 초기화한 후 사용하시기 바랍니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
					check = false;
				}
			}
			if(check) {
				int index = CommandListOperation.searchCommand(cmdsDevide, input); //배열에 입력된 명령어가 있는지 검색
				
				if(index == -1) {	/**예외처리: 입력한 명령어가 없으면 오류 출력*/
					JOptionPane.showMessageDialog(null, "없는 명령어입니다.", "명령어 입력 오류", JOptionPane.ERROR_MESSAGE);
				} else {				
					//명령어를 파일 이름으로 저장했으므로 해당 명령어파일 내부에 적힌 클래스 이름을 얻기 위해 파일 객체와  FileReader를 사용함.
					String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdlist";
					String Clazz = FileOperation.getFileReadData(cmdlistPath, cmds, index);	//파일 내부 데이터를 읽어오는 메소드 호출
			        
			        //명렁어 실행
					JOptionPane.showMessageDialog(null, textFieldValue.trim().replaceAll(" +", " ") + " 명령어를 수행합니다.", "명령어 입력", JOptionPane.INFORMATION_MESSAGE);
					if(CommandListOperation.execute("executeCommand",Clazz, input))
						Model.CommandStack.push(textFieldValue);
					JTextField textField = View.CommandInputPane.getTextField();
					textField.setText("");
				}
			}
		}
	}
}