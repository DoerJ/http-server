package http_webServer;


// this class is for parsing the request message
// specifically, this class extracts the filename of which file is requested, and passes 
// that filename to WriteResponse class in order to write the response back to the requesting browser
public class ParseRequest {

	String request;
	
	// "filename" is the name of the file being requested
	String filename;
	
	// "http_version" indicates the version of the HTTP
	String http_version;
	
	// constructor
	public ParseRequest(String s){
		
		this.request = s;
		
		// note: the first line of the request message contains three items: request type (e.g: GET, POST),
		// filename being requested, and the version of HTTP
		ExtractFilename();
		ExtractVersion();
	}
	
	// this method extracts the filename requested out of the request message
	public void ExtractFilename(){
		
		// specifically, we split the first line of the request message with " " (space)
		// the filename is the second item of the substrings  
		filename = request.split(" ")[1];
	}
	
	// this method extracts the version number of the request message
	// to check if the browser uses a non-compatible version of HTTP
	public void ExtractVersion(){
		
		// the HTTP version is the third item of the substrings
		http_version = request.split(" ")[2];
	}
}
