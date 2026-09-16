FROM maven:3.9.16-eclipse-temurin-21-alpine AS build
WORKDIR /project
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -B

FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache dumb-init && \
    addgroup --system javauser && \
    adduser -S -s /bin/false -G javauser javauser
WORKDIR /app
COPY --from=build --chown=javauser:javauser /project/target/*.jar app.jar
USER javauser
ENTRYPOINT ["dumb-init", "--"]
CMD ["java", "-jar", "app.jar"]