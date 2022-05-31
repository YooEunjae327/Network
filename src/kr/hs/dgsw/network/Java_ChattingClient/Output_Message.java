package kr.hs.dgsw.network.Java_ChattingClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Output_Message extends Thread {
	private Socket sc = null;
	
	
	public Output_Message(Socket sc) {
		this.sc = sc;
	}
	
	public void run() {
		Scanner scan = new Scanner(System.in);
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			OutputStream os = sc.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);

			String msg = "";
			while((msg=br.readLine())!=null){
				pw.println(msg);
			}
		} catch(Exception e) {
			System.out.println("접속 종료");
			e.printStackTrace();
		}
	}
}
