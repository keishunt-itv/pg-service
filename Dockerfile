FROM alpine:latest
RUN apk --update add openjdk11-jre
COPY build/libs/project_name-*.jar /opt/java-app/java-app.jar
CMD ["/usr/bin/java", "-Dsun.net.inetaddr.ttl=30", "-XX:MaxRAMPercentage=80.0","-jar", "/opt/java-app/java-app.jar"]
EXPOSE 8080
