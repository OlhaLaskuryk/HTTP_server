package http.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import command.CommandHTTPUtil;

public class ServerSideHTTP extends Thread {
	private Socket sock = null;

	// private FactoryDAO instanceDAO;

	public ServerSideHTTP(Socket _sock) {
		sock = _sock;

	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					sock.getOutputStream()));
			String st = br.readLine();
			String result;
			String[] parseReq = st.split(" ");
			if (parseReq[0].compareTo("GET") == 0) {
				String[] link = parseReq[1].split("/");
				if (link[1].equals("shop")) {
					if (link.length > 1) {
						int getArguments = link[2].indexOf("?");
						if (getArguments == -1) {
							result = CommandHTTPUtil.getCommand(link[2])
									.execute("");
						} else {
							String command = link[2].substring(0, getArguments);
							result = CommandHTTPUtil.getCommand(command)
									.execute(
											link[2].substring(getArguments,
													link[2].length()));
						}
						bw.write(result);
					}
				}
			}
			bw.flush();
			sock.close();
		} catch (Exception er) {
			er.printStackTrace();
		}
	}
}