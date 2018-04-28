// Author: YUHAO HE
// Student number: 301255434
// Date: 2017/7/5
// This java project creates a http web server which accepts the request from browser, 
// parses the request message (specifically, identify the "GET" and "POST" in the header), 
// and writes the response back to the browser (the response message contains the file requested).


package http_webServer;

import java.net.ServerSocket;
import java.net.Socket;

// This is the main function which the entry of the program
// This class is for creating socket and accepting connection to this socket
public class Main{
	
	 // create the socket for the web server, which handles all request messages
	ServerSocket webserver_socket;
	
	int queue;
	
	public static void main(String[] abc) throws Exception{
		
		new Main().newSocket();
	}
	
	// this method initializes the socket by assigning a port number to it
	public void newSocket() throws Exception{
		
		// the port number of the web server socket is 8000
		int port_num = 8000;   
		webserver_socket = new ServerSocket(port_num);
		
		// the web server is on
		System.out.println("The http web server is on! \r\n");
		
		// start the web server
		handleRequest();
	}
	
	// this method accepts the request message 
	public void handleRequest() throws Exception{
	
		// we use while(true) because we want to keep the socket opened for all request messages
		while(true){
			
			// ServerSocket.accept() listens to TCP connection made to the web server socket 
			// and accept it
			// here the Socket "container" accepts the TCP connection and contains
			// both the request message and the written response message to be sent back to browser
			Socket container = webserver_socket.accept();
			
			// pass the socket "container" to ConnectionHandle for reading request message
			ConnectionHandler connect = new ConnectionHandler(container);
			
			// start reading and parsing the request message
			connect.handle();
		}
	}
	
}
