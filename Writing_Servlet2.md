
We develop our Servlets:

```
EchoServlet.java
TestServlet.java
```

The final code is long, so check some important parts:

- in the init method define database connection, and read datas from config file:

```Java
@Override
  public void init(){
    try{
      Properties pr = new Properties();
      File conf = new File(LOCATION);
      if (conf.exists() && conf.canRead()){
        System.out.println("Mondom a fájlból kiszedett adatokat:");
        pr.load(new FileInputStream(conf));
        String username = pr.getProperty("dbusername");
        String password = pr.getProperty("dbpassword");
        System.out.println("user: "  + username + " password: " + password);

      }else{
       System.out.println("Nincs meg a fájl");
      }
    } catch (Exception e){
     e.printStackTrace(System.err);
    }
  }
```
- we define destroy method, which run when Servlet is stopped. Why is important? Because we have to say to database, the Server doesn't work and close the database connection.
- destroy = closing database conncetion
- if you don't close it, the database realises open connection after Servlet stopping and nobody can join to there from other server  
```Java
@Override
   public void destroy(){
   }
```

Here other java codes part definitons and copy codes. Check packages, class names, references in the Java files. Don't complie yet.  



In the TestServlet.java import java util packages, and rename the location of your tomcat file. Check with NetBeans if you need.
```Java
import java.util.Properties;
import java.util.Base64;

private static final String LOCATION = "C:\\Program Files\\Java\\apache-tomcat-10.0.0-M1\\webapps\\TestServlet\\WEB-INF\\config.conf";
```

#### JSP and Config file

JavaServer Pages (JSP) is a collection of technologies that helps software developers create dynamically generated web pages based on HTML, XML, SOAP, or other document types.

Let's create index.jsp in \webapps\TestServlet:

```JSP
<html>
   <head><title>Index JSP oldalunk</title></head>

   <body>
    Ez egy igazi JSP!
	 <%
	   out.println("Ez itt az IP címed:" + request.getRemoteAddr());
	 %>

   </body>

 </html>
```
- don't forget to add jsp-api.jar dependency. (we added already)
- delete classes folder files
- compile our new codes and run Servlet
```
cd C:\Program Files\Java\apache-tomcat-10.0.0-M1\webapps\TestServlet\WEB-INF
javac -d classes src/test/*
cd C:\Program Files\Java\apache-tomcat-10.0.0-M1\bin
startup.bat
```
- chechk http://localhost:8080/TestServlet/, you see the jsp file

### Property files

It can be:
```
config.cof
properties.prop
whatyouwant.info
```
It includes usernames, passwords. Advantage, without recompiling the code, we can change this datas.

- Make it:
```
TestServlet\WEB-INF
config.conf*
```
Edit with noetpad++ and fill it and save:
```
dbusername=felhasznalo
dbpassword=jelszo
```

## Connection with conf file

```Java
@WebServlet(name = "t", urlPatterns = {"/tests/*"})
public class TestServlet extends HttpServlet {

	private static final String LOCATION = "C:\\Program Files\\Java\\apache-tomcat-10.0.0-M1\\webapps\\TestServlet\\WEB-INF\\config.conf";
	private String USERNAME = "admin";
	private String PASSWORD = "password";
}
   @Override
```


## Basic Authentication and Postman

Download and setup the Postman. We can test our Server answers in the original format. Do not register.

- set: GET method, check http://localhost:8080/TestServlet/tests/asd, you will see (401)
- set authorization authorization--> Basic Auth type --> Username: admin, Password: password --> send button
- you will see "Hello, Gyula!" (200)

Http Request, Response:
- HEADER --> Status Code/ MIME Type,
- BODY --> XML/JSON,HTML/BLOB

#### POST
- parameteres in the body
- in this case we can ask username and password
- firstly in the head we check, we attach a basic authorization
- we get: admin:password -->encode with base64
- this is not enough, it is important the we have to use HTPPS - SSL/TLS connection
- basic authentication + https is ok




- using Base64 process, where admin:password gets a code for the server. It is not enough, becuse it is easy to decode this signal. We have to use HTPPS - SSL/TLS
- start Tomcat, loclahost:http://localhost:8080/TestServlet/form_input.html
- use postman GET and add
- try set authorization authorization-->Username: admin, Password: password
- update
- Headers --> Temporary Headers Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- in our code there is checkauth() method:
```Java
private boolean checkAuth(HttpServletRequest request){
	   String authValue = request.getHeader("Authorization");
	   if(authValue == null){
		   return false;
	   }else{
		   if(!authValue.toUpperCase().startsWith("BASIC ")){ // Basic key in the String*
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
```

- try get without authorization and get 401 error
- set up basic authentication with parameters and update and it works
- try admin2 user -->401 again
