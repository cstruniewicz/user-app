# userapp 
To build the application go to the project directory and run ./mvnw clean install
#
To build and start the application go to the project and run ./mvnw spring-boot:run
#
The application exposes POST, PUT, DELETE endpoints on url localhost:8090/userapp/v1/user 
# Sample body structure 
{    
  "id":"1",
	"userName":"username",
	"password":"password",
	"name":"Jon Snow",
	"email":"email.snow@dot.com",
	"state":"ACTIVE",
	"roleSet":["USER","ADMIN"],
	"comment":"You know nothing"
}
#
GET on localhost:8090/userapp/v1/user/all

with request parameters [username, name, email]
