# Spring Framework

## Controller and RestController
* When @Controller returns String, it is interpreted as the name of the html page from resources/static, such as: return "about.html"
* When @RestController returns String, it is interpreted as the actual content to be sent to the client, such as: return "text"
