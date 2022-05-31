package kr.hs.dgsw.network.Java_ChattingClient;

import java.net.Socket;

public class ChattingClientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Socket sc = new Socket("10.80.161.182",5010);
			
			Output_Message om = new Output_Message(sc);
			om.start();
			
			Input_Message im = new Input_Message(sc);
			im.start();
		} catch(Exception e) {
			System.out.println("연결 종료");
			e.printStackTrace();
		}
	}

}
