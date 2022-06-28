FROM ruby:2.3.4
FROM openjdk:11-oracle
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
EXPOSE 8091
ADD target/payment-processor-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]