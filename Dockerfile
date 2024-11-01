FROM openjdk:17
ARG JAR_FILE=target/proyectoTingesoBackend.jar
COPY ${JAR_FILE} proyectoTingesoBackend.jar
ENTRYPOINT ["java", "-jar", "/proyectoTingesoBackend.jar"]
