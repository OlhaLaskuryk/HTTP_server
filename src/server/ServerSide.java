package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerSide extends Thread {
	Socket sock = null;

	public ServerSide(Socket _sock) {
		sock = _sock;
	}

	public void run() {
		try {
			System.out.println("Server-client-side");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					sock.getOutputStream()));
			String st = br.readLine();
			System.out.println(st);
			if (st.equals("get count")) {
				bw.write("100");
				bw.newLine();
				bw.flush();
			}
			if (st.substring(0, 8).equals("get item")) {
				Integer index = Integer
						.parseInt(st.substring(st.indexOf("=") + 1));
				double price = 10.67;
				bw.write("name|"+price*index);
				bw.newLine();
				bw.flush();
			}
			sock.close();
		} catch (Exception er) {
			er.printStackTrace();
		}
	}
}