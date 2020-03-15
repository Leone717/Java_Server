# Writing Java Servlet

There is a tree:
```
$Catlina_HOME
  webapps/
      Mywebapplication/
          WEB-INF/
              classes/
              src/
              lib/
              web.xml
          META-INF/
              contect.xml
          *.html
          css/
          images
          .....

```
Here is the place of our webapplication:
```
C:\Program Files\Java\apache-tomcat-10.0.0-M1\webapps
```
- we make a new directory here:

```
TestServlet --> This is the application(not Servlet)
```
Everthing in this folder, is available for the user. Everything in the WEB-INF/ and META-INF/ is not available for the user. It is important:

```
$Catlina_HOME
  webapps/
      TestServlet/                      -> User see | PUBLIC
      ------------------------------------------
          WEB-INF/      
              classes/
              src/                      -> User don't see | PRIVATE
              lib/
              web.xml
          META-INF/     
              contect.xml
      ------------------------------------------        
          *.html                        -> User see | PUBLIC  
          css/
          images
          .....

```

#### Writing Servlet


Here is is the code, copy into the web.xml:
https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html

We have to make a new folder as test and save this servlet as TestServlet.java:

```
$Catlina_HOME
  webapps/
      Mywebapplication/
          WEB-INF/
              classes/
              src/
                *test/
                    TestServlet.java**
          META-INF/
```


```Java
// To save as "<CATALINA_HOME>webapps\TestServlet\WEB-INF\src\test\TestServlet.java"
package test;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class TestServlet extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      // Set the response message's MIME type
      response.setContentType("text/html;charset=UTF-8");
      // Allocate a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      // Write the response message, in an HTML page
      try {
         out.println("<!DOCTYPE html>");
         out.println("<html><head>");
         out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
         out.println("<title>Hello, World</title></head>");
         out.println("<body>");
         out.println("<h1>Hello, world!</h1>");  // says Hello
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
   }
}
```
Use Notepad++ and set Java language, check the package and class name! - change it  


#### Webapplication Compiling
In CMD:
```
$ cd C:\Program Files\Java\apache-tomcat-10.0.0-M1\webapps\TestServlet\WEB-INF
$ javac -d classes src/test/TestServlet.java
error: package javax.servlet does not exist
```
1, We have the dependency
```
C:\Program Files\Java\apache-tomcat-10.0.0-M1\lib
servlet-api.jar*
el-api.jar*
jsp-api.jar*
```
Copy the servlet-api.jar here:
```
C:\Program Files\Java\jre1.8.0_231\lib\ext
servlet-api.jar*
el-api.jar*
jsp-api.jar*
```
We have to allow the writing in the apache-tomcat-10.0.0-M1 folder properties

2, We can add the dependency in CMD:
```
javac -d classes -cp ..\..\..\lib\servlet-api.jar  src\test\TestServlet.java
```
Currently, the import is changed, so you have to apply theese pacakges to compile the Testservlet:

```Java
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
```

press enter(if ok, you don't get feedback)
you have to check:
```
C:\Program Files\Java\apache-tomcat-10.0.0-M1\webapps\TestServlet\WEB-INF\classes\test
TestServlet.class
```

#### web.xml = Deployment Descriptor
```
C:\Program Files\Java\apache-tomcat-10.0.0-M1\webapps\TestServlet\WEB-INF
web.xml*
```
open with NotePad++ and copy the code from here:
https://www.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html#helloservlet
```Java
<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app version="3.0"
  xmlns="http://java.sun.com/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">

  <!-- To save as <CATALINA_HOME>\webapps\helloservlet\WEB-INF\web.xml -->

   <servlet>
      <servlet-name>HelloWorldServlet</servlet-name>
      <servlet-class>mypkg.HelloServlet</servlet-class>
   </servlet>

   <!-- Note: All <servlet> elements MUST be grouped together and
         placed IN FRONT of the <servlet-mapping> elements -->

   <servlet-mapping>
      <servlet-name>HelloWorldServlet</servlet-name>
      <url-pattern>/sayhello</url-pattern>
   </servlet-mapping>
</web-app>
```
We add name and place for the Servlet:
```Java
<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app version="3.0"
  xmlns="http://java.sun.com/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">

  <!-- To save as <CATALINA_HOME>\webapps\helloservlet\WEB-INF\web.xml -->

   <servlet>
      <servlet-name>TestServlet</servlet-name>
      <servlet-class>test.TestServlet</servlet-class>
   </servlet>

   <!-- Note: All <servlet> elements MUST be grouped together and
         placed IN FRONT of the <servlet-mapping> elements -->

   <servlet-mapping>
      <servlet-name>TestServlet</servlet-name>
      <url-pattern>/showme</url-pattern>
   </servlet-mapping>
</web-app>
```
Start the Tomcat Server
```
cd C:\Program Files\Java\apache-tomcat-10.0.0-M1\bin
startup.bat
```
- Use Chrome: http://localhost:8080/
- use: http://localhost:8080/TestServlet/showme --> we see new page
- open with Notepad++(in Java language): TestServlet.Java

- check the browser, F12 --> here we don't see the source Java code just the response.

The deployment descriptor link the url sample(showme) and which Servlet. We gave where is the the Servlet and what is the name.

#### Making form_input.html

- in the TestServlet folder
- copy the html sample from here https://www.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html#helloservlet

```HTML
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
  <title>User Input Form</title>
</head>

<body>
<h2>User Input Form</h2>
<form method="get" action="echo">
  <fieldset>
    <legend>Personal Particular</legend>
    Name: <input type="text" name="username" /><br /><br />
    Password: <input type="password" name="password" /><br /><br />
    Gender: <input type="radio" name="gender" value="m" checked />Male
    <input type="radio" name="gender" value="f" />Female<br /><br />
    Age: <select name = "age">
      <option value="1">&lt; 1 year old</option>
      <option value="99">1 to 99 years old</option>
      <option value="100">&gt; 99 years old</option>
    </select>
  </fieldset>

  <fieldset>
    <legend>Languages</legend>
    <input type="checkbox" name="language" value="java" checked />Java
    <input type="checkbox" name="language" value="c" />C/C++
    <input type="checkbox" name="language" value="cs" />C#
  </fieldset>

  <fieldset>
    <legend>Instruction</legend>
    <textarea rows="5" cols="30" name="instruction">Enter your instruction here...</textarea>
  </fieldset>

  <input type="hidden" name="secret" value="888" />
  <input type="submit" value="SEND" />
  <input type="reset" value="CLEAR" />
</form>
</body>
</html>
```

- CTRL + C and start Tomcat in CMD

Here is the get method which is important:
```HTML
<form method="get" action="echo">
```
- Try the form in browser: http://localhost:8080/TestServlet/form_input.html


## GET

- it sends parameters in the URL
- you can save the query with parameters
- there is limited characters
- just ASCII characters
- NOT SAFE
- if we just check the datas, this is better
- NEVER USE for password and username

## POST

- it sends paramteres in the body
- support every types of datas (for exmaple binary)
- SAFE
- if we send datas, this is better

So we modify this line. Firstly, let's try send details with the original get method and check the head in the browser. (404 code)

##### We can see in the headline there is the user and password so it is not safe.

#### Making echo file

- https://www.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html#helloservlet
- rename the pacakge(not mypkg)
```
EchoServlet.java*
TestServlet.java
C:\Program Files\Java\apache-tomcat-10.0.0-M1\webapps\TestServlet\WEB-INF\src\test
```

In the next step we connect EchoServlet.java* with the form_input.html.
What is the key? - Deployment Descriptor(web.xml) Let's make EchoServlet inside web.xml:

```xml
<servlet>
   <servlet-name>TestServlet</servlet-name>
   <servlet-class>test.TestServlet</servlet-class>
</servlet>

<servlet>
   <servlet-name>EchoServlet</servlet-name>
   <servlet-class>test.EchoServlet</servlet-class>
</servlet>
```
And add the path:
```
form_input.html
```

```HTML
<body>
<h2>User Input Form</h2>
<form method="get" action="echo">
```
"echo" - if you write this keyword, then program is searching echo in the web.xml

```
web.xml
```
```xml
    <servlet-mapping>
      <servlet-name>EchoServlet</servlet-name>
      <url-pattern>/echo</url-pattern>
   </servlet-mapping>
```

#### Compling the file

The original import packages are incorrect, so modify as in TestServlet. If yo open by NetBeans it helps to find the right ones.

```Java
package test;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

```
After compile:
```
cd C:\Program Files\Java\apache-tomcat-10.0.0-M1\webapps\TestServlet\WEB-INF
javac -d classes src/test/*

```
Restart Tomcat and check the server in the browser.

### POST

We change get to post:
```HTML
<body>
<h2>User Input Form</h2>
<form method="post" action="echo">
```
This is all. The server knows, this is a POST method.

Http methods: GET, POST, PUT, DELETE, CONNECT. Our Server knows this query types.

doPost: It use secret transfer, but receives same parameters as doGet method.In EchoServlet:
```Java
@Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
             throws IOException, ServletException {
         request.setCharacterEncoding("UTF-8");
    doGet(request, response);
 }
```
Restart server and check, fill the form and we don't see private datas in the headline. F12, 200 status, ok.

#### @WebServlet Annotation

```Java
@WebServlet(name= "TestS", urlPatterns= {"/tests/*"})
public class EchoServlet extends HttpServlet {}
```

If you use this, you don't need deployment descriptor. This is more popular than xml annotation.

#### WebServlet Annotation(in EchoServlet.java)
```Java
@WebServlet(name= "TestS", urlPatterns= {"/tests/*"})
```
#### XML Configuration in web.xml
```xml
<servlet-mapping>
   <servlet-name>TestServlet</servlet-name>
   <url-pattern>/mutasd</url-pattern>
</servlet-mapping>

 <servlet-mapping>
   <servlet-name>EchoServlet</servlet-name>
   <url-pattern>/echo</url-pattern>
</servlet-mapping>
```

```Java
import jakarta.servlet.annotation.WebServlet;
```

rename:
```Java
@WebServlet(name= "t", urlPatterns= {"/tests/*"})
```
- Delete classes from classes folder

```
javac -d classes -cp ..\..\..\lib\servlet-api.jar  src\test/*
```

- int he browser: http://localhost:8080/TestServlet/tests/valami

The adress of the server: localhost
The port, where is available: 8080
Webapplication name : TestServlet
url sample: tests → a The written annotation. This is really usefull.
We create our TestServlet once time and we give back the same instance.
