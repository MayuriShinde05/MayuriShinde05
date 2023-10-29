## AOP Custom Application Implementation


### Requirement
We have a product service on top, and we need to create a custom service that gives the logs of the executing methods.

Product service etl-upload-rest Service

Add logging to the above service.


### Require technical language
-- JAVA
-- Springboot


### Pom.xml requires only 4 dependencies. -


<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
</dependency>
<dependency>
	<groupId>org.apache.commons</groupId>
	<artifactId>commons-lang3</artifactId>
</dependency>


### Planning
When you have to develop, Please check out the basics of AOP.
I refer you to the below link. It will help to develop


https://www.youtube.com/watch?v=Og9Fyew8ltQ&t=70s
https://medium.com/@KosteRico/spring-aop-in-2021-level-up-your-logging-8d1498242ba2
https://www.techgeeknext.com/spring-boot/spring-boot-logging-aop-example
https://howtodoinjava.com/spring-boot2/logging/performance-logging-aspectj-aop/


### At the time, the issue I faced was
-- Develop the AOP application as a separate application. Don't know how to add an AOP project dependency on another parent project?
[ans]: I create the maven jar using "mvn clean install".
	- Then move this jar to a folder like "/custom".
	- In STS/Eclipse, press Alt + enter of the parent project and open "Java build path" -> Libraries -> click on "Add External Jar" and add a custom folder jar.
	- Click on the "Apply" button and click on the "Apply and Close" button.
	- The project added the custom jar. Please check Ctrl + Shift + T and open the custom project class for verification.


-- When the custom AOP application is developing, please check that the parent project package structure is the same as the custom project.
For example, the parent project structure is com.crif.contact.etl.
The Custom AOP project package structure is com.crif.contact.
Then what happened while you deployed this jar in any environment? While package scanning, the class was unable to find the application, and it was not properly developed.
- So, please make sure the package structure is the same for both projects.


-- We have to set the configured max time. If API execution exceeds the configured time, then we lock the request payload.
-- We already configured the package structure; for that, we need to add context.xml in the resource folder. which gets the dynamic value that is presented in application.properties.
please add properties in etl properties file -
#AOP configuration
contact.grab.maxExecutionTime=60000
#200
contact.grab.packageStructure=com.crif.contact.etl.upload



### Testing on local to add a dependency
Yes, with the above steps. Run the test class and/or the whole application.


### Testing on the Internal server with deployment as well as setup
-- We tested this application locally, and it's working as expected.
-- While we need to test this on any internal server, Then we already had the Docker container on the server.
when I create the custom folder on the server and move the custom AOP project jar.
Also, add this path in the volume mapping section of the "docker-compose.yml" file with the below
 - ./etl-upload/custom/custom-aop.jar:/custom-aop.jar
The above is called Mounting or volume mapping.
After that, stop all containers and docker-compose down.
Then remove the images. docker system prune -a
Start the container: docker-compose up -d
Check if all services are up or running or not: docker ps -a
It automatically picks the classes and starts printing logs in a log file.
