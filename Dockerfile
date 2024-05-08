FROM openjdk:23-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ecomm-payment.jar
ENTRYPOINT ["java","-jar","/ecomm-payment.jar"]
EXPOSE 8095