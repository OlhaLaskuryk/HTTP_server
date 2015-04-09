package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP extends Thread {

	public static final int SERVER_PORT = 3000;
	public static final String SERVER_HOST = "localhost";
	private ServerSocket serverTCP;

	public ServerTCP() {
		try {
			serverTCP = new ServerSocket(SERVER_PORT, 10,
					InetAddress.getByName(SERVER_HOST));
			serverTCP.setReuseAddress(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket sock = serverTCP.accept();
				System.out.println("Houston, we have new connection!");
				sock.setTcpNoDelay(true);
				new ServerSide(sock).start();
			} catch (Exception er) {
				er.printStackTrace();
				break;
			}
		}
		try {
			serverTCP.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}