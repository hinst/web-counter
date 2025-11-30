# Web Counter

Track web page visits.

Environment variable JAVA_HOME must point to Java 21 folder when VSCode starts, to avoid errors.

# Spring Framework Notes

# Web Server
* To enable web server, Include `spring-boot-starter-web` from group `org.springframework.boot`
* To add URL prefix to all endpoints, set `server.servlet.contextPath=/my-app`
* To retrieve URL query parameter in controller method, use `@RequestParam` annotation on method parameter
	* Example: `public String myMethod(@RequestParam String input)`
		* Extracts VALUE from: `http://localhost:8080/endpoint?input=VALUE`

# Annotations
* Use @Value("${configuration.parameter.key}") on a field to set the value from `application.properties`

## Controller and RestController
* When @Controller returns String, it is interpreted as the name of the html page from resources/static, such as: return "about.html"
* When @RestController returns String, it is interpreted as the actual content to be sent to the client, such as: return "text"
