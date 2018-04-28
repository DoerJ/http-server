package http_webServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

// this class is for reading(parsing) the request message and writing response message
// back to browser
// this class extends Thread because we want to keep the web server socket listening 
// to more TCP connection while the current request accepted is parsed on one thread
public class ConnectionHandler extends Thread{
	
	Socket s;
	ParseRequest req;
	WriteResponse res;
	
	// BufferedReader is a object reading and buffering the characters from a stream
	BufferedReader Read;
	
	// PrintWriter is a object printing the characters from a stream in browser 
	PrintWriter Write;
	
	// InputStream object stores the binary sequence in request message
	InputStream input;
	
	// OutputStream objects stores the character sequence in response message
	OutputStream output;
	
	// InputStreamReader object decodes the binary message into character sequence
	InputStreamReader char_input;
	
	// this string stores the first line of the request message, which indicates
	// the type of the request
	String first_line;
	String current_line;
	
	// the constructor
	public ConnectionHandler(Socket container) throws Exception{
		
		this.s = container;
		
		// input
		// "Read" finally stores the characters in the request message to be parsed
		input = s.getInputStream();
		char_input = new InputStreamReader(input);
		Read = new BufferedReader(char_input);
		
		// output to the requesting browser
		output = s.getOutputStream();
		Write = new PrintWriter(output, true);
	}
	
	public void handle() throws Exception{
		
	try {
		
		current_line = Read.readLine();
		first_line = current_line;
		
		// the BufferedReader starts to buffer the characters from the input stream
		while(Read.ready()){
			
			System.out.println(current_line);
			current_line = Read.readLine();
		}
			
			System.out.println(" \r\n");
		
		// we pass the first line of request message to ParseRequest class
		// for writing response message back to the requesting browser
		req = new ParseRequest(first_line);
				
		// we pass the ParseRequest object to WriteResponse class in order to write response
		res = new WriteResponse(req);
		Write.print(res.response);
		
		// close the connection
		Write.close();
		Read.close();
		s.close();
		
		} catch (Exception e){
			
			e.printStackTrace();
		}
	
	// TCP connection on this thread is closed
	System.out.println("The TCP connection on this thread is closed!");
	} 
}
