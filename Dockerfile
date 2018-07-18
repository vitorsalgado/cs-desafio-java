# build stage
# -----------------------------------------------------
FROM openjdk:8-jdk

WORKDIR /app
COPY . .
RUN ./gradlew build

# run stage
# -----------------------------------------------------
FROM openjdk:8-alpine
WORKDIR /app

COPY --from=0 /app/build/libs/cs-java-code-challenge-1.0.jar .
EXPOSE 8080

CMD java -jar cs-java-code-challenge-1.0.jar
