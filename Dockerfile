# ── Etapa 1: compilar con Maven ──────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Compilar y empaquetar (saltar tests para acelerar)
RUN mvn -pl carpinteria-app -am package -DskipTests -q

# ── Etapa 2: imagen final liviana ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiar el JAR generado
COPY --from=build /app/carpinteria-app/target/*.jar app.jar

# Puerto de la aplicación
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
