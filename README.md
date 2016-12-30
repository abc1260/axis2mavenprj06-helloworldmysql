My Basic MySql Axis2 Hello World App Service - Brilliant!
--------------------------------------------------------------
- More on proveing that I got a college degree!
- Prior to this, we created a Hello World Axis2 Web Service (WS). See axis2mavenprj02-helloworld git project.
- We continue with the theme, but now we will log the hellos to a MySQL DB.
- Not only can I say hello, but I can create a WS to do it.
- A WS that connects to the DB.



Create Mysql Database And Tables
---------------------------------------------
CREATE DATABASE HELLOWORLD;

USE HELLOWORLD;
			 
CREATE TABLE IF NOT EXISTS MESSAGE(
	message VARCHAR(100) NOT NULL, 
	PRIMARY KEY(message)
);



Build It
-----------------------------
mvn clean install package

- Created in the root directory is: HelloWorldMysql.aar


Deploy It
--------------------

- Start your axis2 version
- Plop HelloWorldMysql.aar into the directory: ../repository/services/
- You should see this deployed service:

HelloWorldMysql
Available operations
    message


Prove it!
----------------------------------------
- See! I told you I went to college!
- Now for the other parts of my live, as a Java Developer, Web Developer and Architect?\


Create Axis2 Maven Bare Bone Project Maven POM Info
--------------------------------------------------------------------
o Use Axis2 Maven Plugin to build WSDL (Web Services Definition Language) From Java code

			<plugin>
				<groupId>org.apache.axis2</groupId>
				<artifactId>axis2-java2wsdl-maven-plugin</artifactId>
				<configuration>
					<className>com.aikiinc.axis2.hello.service.HelloMySqlService</className>
					<outputFileName>${project.build.directory}/HelloWorldMysql.wsdl</outputFileName>
				</configuration>
				<executions>
					<execution>
						<phase>process-classes</phase>
						<goals>
							<goal>java2wsdl</goal>
						</goals>
					</execution>
				</executions>
			</plugin>

o Use Axis2 Maven Plugin to build Axis Archive (*.aar) From Axis Deployment Descripto service.xml

			<plugin>
				<groupId>org.apache.axis2</groupId>
				<artifactId>axis2-aar-maven-plugin</artifactId>
				<configuration>
					<servicesXmlFile>${basedir}/src/main/resources/services.xml</servicesXmlFile>
					<wsdlFile>${project.build.directory}/HelloWorldMysql.wsdl</wsdlFile>
				</configuration>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>aar</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
	



The Pojo that Handles the Service
-----------------------------------------
-  Send a 'name' to the service! Log the unique hello message in the database.

public class HelloMySqlService {
	private DBConnection dbConnection;

	public HelloMySqlService() {
		try {
			dbConnection = DBConnection.getDBConnection();
		} catch (Exception e) {
		}
	}

	public String message(String msg) {
		try {
			if(msg == null)
				throw new Exception("Message can not be empty!");
			
			Hello hello = dbConnection.getUniqueMessage(msg);

			return hello.getMessage();
		} catch (Exception e) {
			return "Message '" + msg + "' error: " + e.getMessage();
		}
	}
}


Running the Client
-----------------------------------------
- Use WSDL
http://localhost:8080/axis2/services/HelloWorldMysql?wsdl

- Say Hello
http://localhost:8080/axis2/services/HelloWorldMysql/message?msg=amubato!

The web service responds with:
<ns:messageResponse><ns:return>Hello amubato!</ns:return></ns:messageResponse>








