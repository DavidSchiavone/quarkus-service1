# quarkus-service1
To run the application with docker you have to execute following shell scripts:

Create blog-nw
```shell script
docker network create blog-nw
```

Start redpanda container
```shell script
docker run -d --name=redpanda-1 -p 9092:9092 --network blog-nw docker.redpanda.com/redpandadata/redpanda:v23.3.5 redpanda start --advertise-kafka-addr redpanda-1:9092
```

Start mysql container
```shell script
docker run -d --name mysql-container --network blog-nw -e MYSQL_ROOT_PASSWORD=test05 -e MYSQL_DATABASE=blog-db -e MYSQL_USER=bloguser -e MYSQL_PASSWORD=test05 -p 3306:3306 mysql:8
```

Wait till the previous containers are fully running and then start the service1 (rest-endpoint)
```shell script
docker run -d --name quarkus-service-1 --network blog-nw -p 8080:8080 ghcr.io/davidschiavone/docker-service-1:latest
```

Start the service2. (validation-service)
```shell script
docker run -d --name quarkus-service-2 --network blog-nw -p 8081:8081 ghcr.io/davidschiavone/docker-service-2:latest
```
## Test the application

To add a new blog execute the following HTTP request
```http
POST localhost:8080/blogs/

{
    "title": "Test Blog",
    "content": "Dies ist ein Test Blog"
}
```

To check if the blog has been validated successfully, run the following HTTP request
```http
GET localhost:8080/blogs/
```

If the valid-flag in the response is set to true, the communication between service1 and service2 with redpanda was successfully!
