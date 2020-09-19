FROM maven:latest

WORKDIR /app

COPY . ./
RUN mvn clean package -Dspring.profiles.active=prod -DskipTests
RUN chmod +x ./entrypoint.sh

ENTRYPOINT [ "bash", "./entrypoint.sh" ]