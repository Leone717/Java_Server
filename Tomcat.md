# Apache Tomcat®

http://tomcat.apache.org/

###### 64-bit Windows zip (pgp, sha512)

- download and copy into Java folder(where is JDK)
```
C:\Program Files\Java
```

#### Check system variables

```
JAVA_HOME
C:\Program Files\Java\jdk1.8.0_231

JRE_HOME
C:\Program Files\Java\jre1.8.0_231

TOMCAT Home
C:\Program Files\Java\apache-tomcat-10.0.0-M1

Path
C:\Program Files\Java\jdk1.8.0_231
C:\Program Files\Java\jre1.8.0_231
C:\Program Files\Java\apache-tomcat-10.0.0-M1

```

Run commands in CMD(java, javac)

#### Configuration

- Edit with notepad++:

```
cd C:\Program Files\Java\apache-tomcat-10.0.0-M1\conf
server.xml
```
- Setting the port:

```xml
  <!--  Define a non-SSL/TLS HTTP/1.1 Connector on port 8080
    -->
    <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
    <!-- A "Connector" using the shared thread pool-->

```
## Tomcat structure

```
setting up port: server.xml
adding users:     tomcat-users.xml
listing:* libraries web.xml
```

#### web.xml

- Listing is need to be false:
```xml
<servlet>
       <servlet-name>default</servlet-name>
       <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
       <init-param>
           <param-name>debug</param-name>
           <param-value>0</param-value>
       </init-param>
       <init-param>
           <param-name>listings</param-name>
           <param-value>false</param-value>
       </init-param>
       <load-on-startup>1</load-on-startup>
   </servlet>
```
#### tomcat-users.xml

```xml
 -->
   <role rolename="manager-gui"/>
   <user username="manager" password="root" roles="manager-gui"/>
 </tomcat-users>
```

#### Starting Tomcat Server (in CMD)

```
cd C:\Program Files\Java\apache-tomcat-10.0.0-M1\bin
startup.bat
```
- Enable firewall, it works.

- Use Chrome: http://localhost:8080/

If there is Http Status code 500 you have to allow:
```
apache-tomcat-10.0.0-M1--> Properties--> Security --> Change permissions in Users(DESKTOP-Xyz)
```
- Refresh the Server

- Try Tomcat in browser --> Manager App button (Authentication Required) -->admin, password

##### We need to modify the tomcat-users.xml:


```xml
  <role rolename="manager-gui"/>
  <role rolename="manager-script"/>
  <role rolename="manager-jmx"/>
  <role rolename="manager-status"/>
  <role rolename="admin-gui"/>
  <role rolename="admin-script"/>
  <user username="tomcat" password="root" roles="manager-gui, manager-script, manager-jmx, manager-status, admin-gui, admin-script"/>

</tomcat-users>
```
Stop (CTRL + C in CMD)

Log in the browser with the details above and there is the manager interface.

```
/manager
```
