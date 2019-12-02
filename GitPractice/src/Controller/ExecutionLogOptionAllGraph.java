package Controller;

public class ExecutionLogOptionAllGraph {
	public boolean executeCommand(String[] parameter) {
		/*parameter 예외 처리
		 * parameter엔 깃 명령어에서 사용자가 입력한 인자 값이 들어간다.
		 * 예를 들어 git clone처럼 인자가 최소 1개, 최대 2개 필요한 명령어의 경우 parameter.length가 최소1, 최대 2이어야 하고,
		 * parameter[0] 해당 주소 값이 존재하는지, length가 2일 때 parameter[1]의 하위 디렉터리가 존재해야한다.
		 * 위의 예외를 모두 통과하여 명령어가 실행되면 true, 실행시킬 수 없으면 false를 리턴한다.
		 */
		System.out.println("I'm git log --all --graph");
		return true; //명령어를 올바르게 입력하여 실행하면 true
		//return false; //명령어를 올바르지 않게 입력하여 에러가 뜨면 에러 문구 출력 후 false를 리턴
	}
public boolean cancelCommand(String[] parameter) {
		/* 리턴값 true, false 상관 없음. */
		System.out.println("Cancel git log --all --graph");
		return false;
	}
}