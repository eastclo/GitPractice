package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

import Model.CommandStack;

public class GobackButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmdlistPath = "." + File.separator + "src" + File.separator + "Model" + File.separator + "cmdlist";
		 
		if(e.toString().contains("back")) {	//�ڷΰ��� ��ư�� ���
			 String cmd = Model.CommandStack.pop();	//��ɾ� ���ÿ��� �ڷΰ��� �޼ҵ�� ��ɾ� �޾ƿ���
			 if (cmd != null) {	//�ڷΰ��� �� ��ɾ ������ null
				 /*	
				  * �޾ƿ� ��ɾ�� cancelCommand �޼ҵ� ����
				  * ����� CommandInputListener���� �����ϴ� ��İ� ����
				  */
				 File f = new File(cmdlistPath,cmd);
				 FileReader fin;
				 char []buf = new char [1024];
					try {
						fin = new FileReader(cmdlistPath + File.separator + cmd);
						fin.read(buf);
						fin.close();
					} catch (FileNotFoundException e1) {} catch (IOException e1) {}
				String Clazz = String.valueOf(buf).trim();
				try {
		        	Class<?> clazz = Class.forName(Clazz);	//Clazz�� ���� Ŭ���� ����
		        	Object newObj = clazz.getDeclaredConstructor().newInstance();	//Ŭ������ ��ü����
		        	
		        	Method m = clazz.getDeclaredMethod("cancelCommand", null);	//�Ķ���ͷ� �޼ҵ� �̸�, �ش� �޼�000���� �Ķ���͵��� Ÿ��(.class ����)
		        	m.invoke(newObj,null);	//�Ķ���ͷ� �޼ҵ��� Ŭ����, �޼ҵ��� �ĸ����͵�     	
		        } catch (ClassNotFoundException e1) {
		        	System.out.println("error1");
		        } catch (Exception e1) {
		        	System.out.println("error2");
		        }
			 }
		}
		
		else {	//�����ΰ��� ��ư�� ���
			 String cmd = Model.CommandStack.go();	//��ɾ� ���ÿ��� �����ΰ��� �޼ҵ�� ��ɾ� �޾ƿ���
			 if (cmd != null) {	//�����ΰ��� �� ��ɾ ������ null
				 /*	
				  * �޾ƿ� ��ɾ�� executeCommand �޼ҵ� ����
				  * ����� CommandInputListener���� �����ϴ� ��İ� ����
				  */
				 File f = new File(cmdlistPath,cmd);
				 FileReader fin;
				 char []buf = new char [1024];
					try {
						fin = new FileReader(cmdlistPath + File.separator + cmd);
						fin.read(buf);
						fin.close();
					} catch (FileNotFoundException e1) {} catch (IOException e1) {}
				String Clazz = String.valueOf(buf).trim();
				try {
		        	Class<?> clazz = Class.forName(Clazz);	//Clazz�� ���� Ŭ���� ����
		        	Object newObj = clazz.getDeclaredConstructor().newInstance();	//Ŭ������ ��ü����
		        	
		        	Method m = clazz.getDeclaredMethod("executeCommand", null);	//�Ķ���ͷ� �޼ҵ� �̸�, �ش� �޼�000���� �Ķ���͵��� Ÿ��(.class ����)
		        	m.invoke(newObj,null);	//�Ķ���ͷ� �޼ҵ��� Ŭ����, �޼ҵ��� �ĸ����͵�     	
		        } catch (ClassNotFoundException e1) {
		        	System.out.println("error1");
		        } catch (Exception e1) {
		        	System.out.println("error2");
		        }
			 }	
		 }
	}
}

