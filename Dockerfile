# Multi-stage build para otimizar o tamanho da imagem
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar arquivo de configuração do Maven primeiro (para cache de dependências)
COPY pom.xml .

# Baixar dependências (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Compilar e empacotar a aplicação
RUN mvn clean package -DskipTests

# Stage de runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Instalar curl para health check e criar usuário não-root para segurança
RUN apk add --no-cache curl && \
    addgroup -S spring && \
    adduser -S spring -G spring

USER spring:spring

# Copiar o JAR da aplicação
COPY --from=build /app/target/*.jar app.jar

# Expor porta da aplicação
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/healthcheck || exit 1

# Executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

