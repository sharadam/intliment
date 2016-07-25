For Packaging
mvn clean install --- generates war file

For compiling Test cases
mvn test-compile

Jdk 1.8

Commands to see response in Curl tool:

curl http://localhost:8080/counter-api/search -d searchText=sharada,honey,bharath -H"Authorization: Basic aW50ZWxpbWVudDpwYXNzMTIz"  -H"Content- Type: application/json" -X POST

curl http://localhost:8080/counter-api/top/6 -d searchText=sharada,honey,bharath -H"Authorization: Basic aW50ZWxpbWVudDpwYXNzMTIz"  -H"Content- Type: application/json" -X POST