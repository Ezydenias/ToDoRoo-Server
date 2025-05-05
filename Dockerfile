﻿FROM amazoncorretto:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 3030
ENTRYPOINT ["java","-jar","/app.jar"]