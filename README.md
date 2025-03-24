Run the below command to bring up the application
mvn spring-boot:run

Spring security is enabled in this application. It is required to login to access the API's through swagger.
Login URL: http://localhost:8080/seaexplorer/api/login
Username: John
Password: 1234

Once you logged in, execute API's uring Swagger documentation
http://localhost:8080/seaexplorer/api/swagger-ui/index.html

Command to execute the test
mvn test
