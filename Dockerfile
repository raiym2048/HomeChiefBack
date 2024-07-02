# Maven Build Stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app/
COPY mvnw /app/
COPY .mvn /app/.mvn/
RUN chmod +x /app/mvnw
ENV MAVEN_OPTS="--add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"
RUN ./mvnw clean package -DskipTests
