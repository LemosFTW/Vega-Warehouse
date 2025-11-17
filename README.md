# Vega Warehouse - Sistema de Gerenciamento de Armazém

API RESTful para gerenciar o estoque e armazenamento de ingredientes em uma fábrica de alimentos.

## 🏗️ Arquitetura

O projeto segue os princípios de **Clean Architecture** e **SOLID**, organizado em camadas:

```
src/main/java/com/vega/warehouse/
├── api/                    # Camada de apresentação (Controllers, DTOs, Mappers)
├── application/            # Casos de uso (Use Cases, DTOs de aplicação)
├── domain/                 # Regras de negócio (Models, Enums, Ports)
└── infrastructure/         # Implementações técnicas (Persistence, Config)
```

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Docker e Docker Compose (opcional)

### Executar Localmente

1. **Clone o repositório**
```bash
git clone <repository-url>
cd vegaWarehouse
```

2. **Compile o projeto**
```bash
mvn clean install
```

3. **Execute a aplicação**
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 🐳 Executar com Docker

#### Opção 1: Docker Compose (Recomendado)

```bash
# Construir e iniciar o container
docker-compose up --build

# Executar em background
docker-compose up -d --build

# Ver logs
docker-compose logs -f

# Parar o container
docker-compose down

# Rebuild forçado
docker-compose up --build --force-recreate
```

#### Opção 2: Docker Build Manual

```bash
# Construir a imagem
docker build -t vega-warehouse:latest .

# Executar o container
docker run -d -p 8080:8080 --name vega-warehouse vega-warehouse:latest

# Ver logs
docker logs -f vega-warehouse

# Verificar saúde do container
docker ps

# Parar o container
docker stop vega-warehouse
docker rm vega-warehouse
```

#### Verificar se está funcionando

```bash
# Health check
curl http://localhost:8080/healthcheck

# Ou no navegador
# http://localhost:8080/healthcheck
```

## 📋 Endpoints da API

### 1. Cadastro e Consulta de Ingredientes

- **POST** `/ingredientes` - Criar novo ingrediente
- **GET** `/ingredientes` - Listar todos os ingredientes

### 2. Consulta de Volume Total por Tipo

- **GET** `/ingredientes/volume` - Retornar volume total por tipo (SECO, LIQUIDO, REFRIGERADO)

### 3. Compartimentos Disponíveis para Armazenamento

- **GET** `/compartimentos/disponiveis?quantidade={qtd}&tipo={tipo}` - Listar compartimentos com espaço suficiente

### 4. Compartimentos Disponíveis para Venda

- **GET** `/compartimentos/disponiveis-para-venda?tipo={tipo}` - Listar compartimentos com volume > 0 do tipo solicitado

### 5. Histórico de Movimentações

- **GET** `/historico?sortBy={date|compartment}&order={asc|desc}` - Listar movimentações ordenadas

### Health Check

- **GET** `/healthcheck` - Verificar status da aplicação

## 🗄️ Banco de Dados

A aplicação utiliza **H2 Database** (em memória) por padrão.

### Acessar H2 Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:warehouse`
- Username: `sa`
- Password: (vazio)

## 🧪 Executar Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com cobertura
mvn test jacoco:report

# Executar apenas testes unitários
mvn test -Dtest=*Test
```

## 📦 Estrutura do Projeto

```
vegaWarehouse/
├── src/
│   ├── main/
│   │   ├── java/com/vega/warehouse/
│   │   │   ├── api/              # Controllers, DTOs, Mappers
│   │   │   ├── application/     # Use Cases, DTOs
│   │   │   ├── domain/          # Models, Enums, Ports
│   │   │   └── infrastructure/  # Persistence, Config
│   │   └── resources/
│   │       └── application.yaml
│   └── test/                    # Testes 
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🔧 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **JUnit 5**
- **Mockito**

## 📝 Regras de Negócio

### Capacidades Máximas por Tipo

- **SECO**: 600 kg
- **LIQUIDO**: 500 L
- **REFRIGERADO**: 400 kg

### Regras de Armazenamento

1. Um compartimento não pode misturar tipos diferentes no mesmo período
2. Um compartimento que armazenou ingredientes secos hoje só pode armazenar outro tipo amanhã
3. Ingredientes só podem ser armazenados se houver espaço suficiente
4. Toda entrada e saída deve ser registrada no histórico

## 🐳 Docker

### Build da Imagem

```bash
docker build -t vega-warehouse:latest .
```

### Executar Container

```bash
docker run -d -p 8080:8080 vega-warehouse:latest
```

### Docker Compose

```bash
# Iniciar
docker-compose up -d

# Parar
docker-compose down

# Ver logs
docker-compose logs -f

# Rebuild
docker-compose up --build
```

## 📊 Exemplos de Uso

### Criar Ingrediente

```bash
curl -X POST http://localhost:8080/ingredientes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Farinha",
    "type": "SECO",
    "quantity": 100,
    "unit": "kg"
  }'
```

### Buscar Compartimentos Disponíveis

```bash
curl "http://localhost:8080/compartimentos/disponiveis?quantidade=100&tipo=SECO"
```

### Buscar Compartimentos para Venda

```bash
curl "http://localhost:8080/compartimentos/disponiveis-para-venda?tipo=SECO"
```