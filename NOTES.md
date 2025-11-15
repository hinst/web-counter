# Spring Framework

# Web Server
* Include `spring-boot-starter-web` from group `org.springframework.boot` to enable web server
* Set `server.servlet.contextPath=/my-app` to add URL prefix to all endpoints

# Annotations
* Use @Value("${configuration.parameter.key}") on a field to set the value from `application.properties`

## Controller and RestController
* When @Controller returns String, it is interpreted as the name of the html page from resources/static, such as: return "about.html"
* When @RestController returns String, it is interpreted as the actual content to be sent to the client, such as: return "text"
