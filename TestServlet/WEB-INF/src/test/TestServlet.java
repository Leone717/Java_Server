// To save as "<CATALINA_HOME>\webapps\TestServlet\WEB-INF\src\test\TestServlet.java"
package test;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;
import java.util.Base64;

@WebServlet(name = "t", urlPatterns = {"/tests/*"})
public class TestServlet extends HttpServlet {
	
	private static final String LOCATION = "C:\\Program Files\\Java\\apache-tomcat-10.0.0-M1\\webapps\\TestServlet\\WEB-INF\\config.conf";
	private String USERNAME = "admin";
	private String PASSWORD = "password";
	
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
				   
      boolean success = checkAuth(request);
	  
	  if (success){
		  // Set the response message's MIME type
		  response.setContentType("text/html;charset=UTF-8");
		  // Allocate a output writer to write the response message into the network socket
		  PrintWriter out = response.getWriter();
	 
		  // Write the response message, in an HTML page
		  try {
			 out.println("<!DOCTYPE html>");
			 out.println("<html><head>");
			 out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			 out.println("<title>Hello, Gyula</title></head>");
			 out.println("<body>");
			 out.println("<h1>Hello, Gyula!</h1>");  // says Hello
			 // Echo client's request information
			 out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
			 out.println("<p>Protocol: " + request.getProtocol() + "</p>");
			 out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
			 out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
			 // Generate a random number upon each request
			 out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
			 out.println("</body>");
			 out.println("</html>");
		  } finally {
			 out.close();  // Always close the output writer
		  }			  
			  
	    }else{
			noAccess(response);
		}
				   

   }
   
   
   private boolean checkAuth(HttpServletRequest request){
	   String authValue = request.getHeader("Authorization");
	   if(authValue == null){
		   return false;
	   }else{
		   if(!authValue.toUpperCase().startsWith("BASIC ")){
			   return false;
		   }else{
			   String userpass = new String(Base64.getDecoder().decode(authValue.substring(6))); //admin:password
			   int counter = userpass.indexOf(":");
			   String observedUsername = userpass.substring(0,counter);
			   String observedPassword = userpass.substring(counter+1);
			   if (observedUsername.equals(USERNAME) && observedPassword.equals(PASSWORD))
				   return true;
		   }
	   }
	   return false;
   }
   
   private void noAccess(HttpServletResponse response){
	   try{
	    response.sendError(401);
	   }catch (Exception e){
		System.out.println(""+ e);		
	   }
   }
   
   
   
   
   @Override
   public void init(){
	   try{
		   Properties pr = new Properties();
		   File conf = new File(LOCATION);
		   if (conf.exists() && conf.canRead()){
			   System.out.println("I say the datas from the file:");
			   pr.load(new FileInputStream(conf));
			   String username = pr.getProperty("dbusername");
			   String password = pr.getProperty("dbpassword");
			   System.out.println("user: "  + username + " password: " + password);
			   
		   }else{
			  System.out.println("I don't find the file");
		   }
	   } catch (Exception e){
			e.printStackTrace(System.err);
	   }
   }
   
   @Override
   public void destroy(){
     
   }
   
   @Override
   public String getServletInfo(){
    return "This is a Servlet";
   }
}