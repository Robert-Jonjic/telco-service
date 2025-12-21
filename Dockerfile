# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and pom first for caching
COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN chmod +x mvnw && ./mvnw -q -B dependency:go-offline

# Copy source code
COPY src src

# Build jar
RUN ./mvnw -q -B clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
