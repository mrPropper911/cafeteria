#### Stage 1: Build the application
FROM adoptopenjdk/openjdk16:ubi as build

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Stage 2: A minimal docker image with command to run the app
FROM adoptopenjdk/openjdk16:ubi

ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","by.belyahovich.cafiteria_back.CafiteriaBackApplication"]

###############OLD_SCRIPT######################
#ARG JAR_FILE=target/*.jar
#ENV BOT_DB_USERNAME=root
#ENV BOT_DB_PASSWORD=qwer4321
#COPY ${JAR_FILE} app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-Dspring.datasource.username=${BOT_DB_USERNAME}","-Dspring.datasource.password=${BOT_DB_PASSWORD}","-jar","/app.jar"]
