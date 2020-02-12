package org.sprong.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
	public static void main(String[] args) throws IOException {
		
		ServerSocket myServer = null;
		
		// important vars used inside listening loop
		Socket mySocket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		String reqPart = null;
		
		try {
			// init myServer and then drop into listening loop
			myServer = new ServerSocket(8080);
			myServer.setSoTimeout(1000);
			
			while (true) {
				
				try {
					mySocket = myServer.accept();
					
					try {
						// read the input in and print it to the screen
						reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
						do {
							reqPart = reader.readLine();
							System.out.println(reqPart);
						}
						while (reqPart != null && reqPart.length() > 0);
							
						
						// send standard output back
						writer = new PrintWriter(mySocket.getOutputStream(), true);
						writer.println("HTTP/1.1 200 OK\r\n\r\nHello, world!");
						
						mySocket.close();
					 
					} catch (Exception e) {
						System.out.println("a bad happened - " + e.getLocalizedMessage());
					}
				} catch (SocketTimeoutException ex) {
					if (Thread.currentThread().isInterrupted()) {
						break;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					break;
				}
			}
		} finally {
			if (null != myServer) {
				myServer.close();
			}
			if (null != writer) {
				writer.close();
			}
			if (null != reader) {
				reader.close();
			}
		}
	}
}
