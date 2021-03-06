package http.client;

import http.server.ServerHTTP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSideHTTP {
	public static void main(String[] args) {
		Socket sock;
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Client-side");
			sock = new Socket("localhost", ServerHTTP.SERVER_PORT);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					sock.getOutputStream()));
			
			String command = sc.nextLine();
			bw.write("HTTP/1.1 200 OK");
			bw.newLine();
			bw.flush();
			String stmp  = br.readLine();
			System.out.println(stmp);
			sock.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
