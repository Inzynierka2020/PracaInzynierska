FROM maven:latest

WORKDIR /app

COPY . ./
RUN mvn clean package -Dspring.profiles.active=prod -DskipTests

ENTRYPOINT [  "mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-Dspring.profiles.active=prod'" ]