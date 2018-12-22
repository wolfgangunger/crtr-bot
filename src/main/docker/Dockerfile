FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD target/crtr.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
RUN sh -c 'touch /app.jar'