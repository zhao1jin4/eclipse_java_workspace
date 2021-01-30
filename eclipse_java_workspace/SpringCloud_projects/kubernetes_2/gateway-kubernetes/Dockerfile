#FROM tomcat:9.0.34-jdk11  #源码是从 FROM openjdk:11-jdk (/usr/local/openjdk-11版本是11.0.7,/usr/local/tomcat)
FROM openjdk:11-jdk
RUN mkdir -p /app  /tmp/logs/
WORKDIR /app
COPY target/gateway-k8s.jar /app/
#ADD target/${JAR_FILE} /app/
VOLUME ["/tmp/logs/"]
EXPOSE 8082
CMD ["--spring.profiles.active=dev"]
ENTRYPOINT ["java","-jar","gateway-k8s.jar"]