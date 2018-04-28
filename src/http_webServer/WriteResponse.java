package http_webServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// this class is for writing the response message back to the browser
public class WriteResponse {
	
	ParseRequest req;
	File requested_file;
	FileInputStream readfile;
	
	// the filename of the file requested
	String file;
	
	// the version of HTTP that requesting browser is using
	String version;
	
	// the response message to be displayed in console
	String response;
	
	// the pathname of the file
	String pathname;
	
	// the current time
	String Date;
	
	// constructor
	public WriteResponse(ParseRequest request) throws IOException{
		
		this.req = request;
		
		// the location of the folder containing the web server files
		pathname = "/Users/Rockman/Desktop/Summer 2017/CMPT 371/Lab #2/http_webServer/src/http_webServer";
		file = req.filename;
		version = req.http_version;	
		response = "";
		
		// we create a File object that stores the file requested
		requested_file = new File(pathname + file);
		
		// parse the filename and the version for sending correct corresponding 
		// response message back to the requesting browser
		
		// get the current time
		Date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		
		if(version.equals("HTTP/1.1")){
			
			NormalResponse();
		}
		
		else{

			WrongVersionResponse();
		}
	}
		
	// this method writes "OK" response message (status code: 200) back to the requesting browser,
	// and sends the requested file (e.g: html, image) to browser for displaying
	public void NormalResponse() throws IOException{
	
	try {
		
		// writes response message (header lines)
		response += "HTTP/1.1 200 OK \r\n";
		response += "Date: " + Date + " \r\n";
		response += "Server: Jserver/1.1.11 (Mac OS X) \n";
		response += "Last-Modified: Fri, 7 Jul 2017 10:15:50 GMT \r\n";
		response += "Content-Length: " + requested_file.length() + "\r\n";
		response += "Content-Type: text/html \r\n";
		response += "Connection: Closed \r\n";
		
		// the black line that separates the header lines and content of the requested file
		response += "\r\n";
	
		// "readfile" reads the content of the requested file
		readfile = new FileInputStream(requested_file);
		
		// writes response message (content of the requested file)
		int content;
		
		// FileInputStrea.read() returns a integer
		// -1 represents the end of the file
		while((content = readfile.read()) != -1){
			
			response += (char) content;
		}
			
		response += "\r\n";
		
		} catch (FileNotFoundException e){
			
			// when a requested file is not found in web server file system, 
			// "404 Not Found" will be displayed in response message
			String length = Long.toString(requested_file.length());
			response = response.replace("200 OK", "404 Not Found");
			response = response.replace(length, "0");
		}
	
	// display the response message
	System.out.println(response);
	}
	
	// this method writes "VERSION NOT SUPPORTED" response message (status code: 505)
	// back to the requesting browser
	public void WrongVersionResponse(){
		
		System.out.println("The version of HTTP is non-compatible!");
		response += version + "505 HTTP Version Not Supported \r\n";
		response += "Date" + Date + "\r\n";
		response += "Server: Jserver/1.1.11 (Mac OS X) \n";
		response += "Last-Modified: Fri, 7 Jul 2017 10:15:50 GMT \r\n";
		response += "Content-Length: " + requested_file.length() + "\r\n";
		response += "Content-Type: text/html \r\n";
		response += "Connection: Closed \r\n";
	}

}

