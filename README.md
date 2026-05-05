# 🔗 Encurtador de URL

Um serviço de encurtamento de URLs construído com **Spring Boot** que utiliza **Base62** para codificação eficiente de URLs curtas. O projeto segue padrões de arquitetura limpa com separação de responsabilidades, mediador de comandos e repositório de dados desacoplado.

---

## 📋 Índice

- [Características](#-características)
- [Tecnologias](#-tecnologias)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Executando o Projeto](#-executando-o-projeto)
- [API Endpoints](#-api-endpoints)
- [Exemplos de Uso](#-exemplos-de-uso)
- [Arquitetura](#-arquitetura)
- [Fluxo de Dados](#-fluxo-de-dados)
- [Banco de Dados](#-banco-de-dados)
- [Docker](#-docker)
- [Testes](#-testes)
- [Contribuindo](#-contribuindo)
- [Licença](#-licença)

---

## ✨ Características

- ✅ **Encurtamento de URLs** - Converta URLs longas em códigos curtos usando Base62
- ✅ **Redirecionamento 302** - Redirecione para a URL original automaticamente
- ✅ **Rastreamento de Cliques** - Registre o número de vezes que cada URL foi acessada
- ✅ **Listagem de URLs** - Obtenha todas as URLs encurtadas cadastradas
- ✅ **Deleção de URLs** - Remova URLs encurtadas do sistema
- ✅ **H2 Console** - Interface web para gerenciar o banco de dados
- ✅ **Docker Support** - Execute em containerização com Docker e Docker Compose
- ✅ **Traefik Integration** - Proxy reverso e load balancer configurado
- ✅ **Arquitetura Limpa** - Use Cases, Mediador de Comandos e Repository Pattern
- ✅ **Desacoplamento de BD** - Separação entre Domain e Data Layer

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Java** | 25 | Linguagem de programação |
| **Spring Boot** | 4.0.5 | Framework web e gerenciamento de dependências |
| **Spring Data JPA** | - | ORM para persistência de dados |
| **H2 Database** | - | Banco de dados em memória para desenvolvimento |
| **Lombok** | - | Redução de boilerplate com anotações |
| **Maven** | 3.9+ | Gerenciador de dependências e build |
| **Docker** | - | Containerização da aplicação |
| **Traefik** | 3.0 | Proxy reverso e roteamento |
| **Redis** | - | Configuração futura para cache/contador |

---

## 📁 Estrutura do Projeto

```
encurtador-url/
├── src/
│   ├── main/
│   │   ├── java/com/projetos/encurtador_url/
│   │   │   ├── EncurtadorUrlApplication.java        # Main Application
│   │   │   ├── LinkResponse.java                     # DTO de Resposta
│   │   │   ├── ResourceNotFoundException.java        # Exceção customizada
│   │   │   ├── base62/                               # Codificação Base62
│   │   │   │   ├── enums/
│   │   │   │   │   └── CaracteresBase62Enum.java    # Enum com caracteres (0-9, a-z, A-Z)
│   │   │   │   └── services/
│   │   │   │       └── Base62Service.java           # Conversão Base10 ↔ Base62
│   │   │   ├── controllers/                          # Endpoints REST
│   │   │   │   ├── LinkController.java              # Controlador principal
│   │   │   │   └── requests/
│   │   │   │       └── ShortenRequest.java          # DTO de requisição
│   │   │   ├── data/                                 # Data Layer (BD)
│   │   │   │   ├── LinkEntity.java                  # Entidade JPA
│   │   │   │   └── mappers/
│   │   │   │       └── LinkMapper.java              # Mapeador Domain → Entity
│   │   │   ├── domain/                               # Domain Layer (Lógica)
│   │   │   │   ├── entities/
│   │   │   │   │   └── Link.java                    # Entidade de Domínio
│   │   │   │   └── repositories/
│   │   │   │       └── ILinkRepository.java         # Interface do Repositório
│   │   │   ├── mediator/                             # Padrão Mediador
│   │   │   │   ├── ICommand.java                    # Interface de Comando
│   │   │   │   ├── IUseCase.java                    # Interface de Use Case
│   │   │   │   ├── Mediator.java                    # Implementação do Mediador
│   │   │   │   └── ICommandBus.java                 # Interface do Bus
│   │   │   ├── services/                             # Serviços
│   │   │   │   ├── LinkService.java                 # Lógica de Links
│   │   │   │   └── ContadorService.java            # Gerador de Identificadores
│   │   │   └── useCases/                             # Casos de Uso
│   │   │       ├── CommandBus.java                  # Implementação do Bus
│   │   │       ├── ICommandBus.java                 # Interface do Bus
│   │   │       ├── buscarLinkPorLinkCurto/          # UC: Buscar por Código
│   │   │       │   ├── BuscarLinkPorLinkCurtoCommand.java
│   │   │       │   └── BuscarLinkPorLinkCurtoUseCase.java
│   │   │       ├── deletarLink/                      # UC: Deletar Link
│   │   │       │   ├── DeletarLinkCommand.java
│   │   │       │   └── DeletarLinkUseCase.java
│   │   │       ├── encurtarUrl/                      # UC: Encurtar URL
│   │   │       │   ├── EncurtarUrlCommand.java
│   │   │       │   └── EncurtarUrlUseCase.java
│   │   │       └── listarLinks/                      # UC: Listar URLs
│   │   │           ├── ListarLinksCommand.java
│   │   │           └── ListarLinksUseCase.java
│   │   └── resources/
│   │       ├── application.properties                # Configurações
│   │       └── static/                               # Arquivos estáticos
│   └── test/                                         # Testes
│       └── java/com/projetos/encurtador_url/
│           ├── EncurtadorUrlApplicationTests.java
│           └── services/
│               └── LinkServiceTest.java
├── Dockerfile                                        # Docker Image
├── docker-compose.yml                               # Orquestração de containers
├── pom.xml                                          # Dependências Maven
└── README.md                                        # Este arquivo

```

### 📊 Organização em Camadas

```
┌─────────────────────────────────────┐
│      API REST (Controllers)         │  ← Recebe requisições HTTP
├─────────────────────────────────────┤
│    Use Cases (Casos de Uso)        │  ← Orquestra o fluxo de negócio
│    + Command Bus (Mediador)        │
├─────────────────────────────────────┤
│   Services (LinkService)            │  ← Lógica de negócio
├─────────────────────────────────────┤
│   Domain Layer                      │  ← Entidades de domínio (Link)
├─────────────────────────────────────┤
│   Data Layer                        │  ← Persistência (LinkEntity, Mapper)
├─────────────────────────────────────┤
│   H2 Database                       │  ← Armazenamento de dados
└─────────────────────────────────────┘
```

---

## 📋 Pré-requisitos

Certifique-se de ter instalado:

- **Java 25+** - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.9+** - [Download Maven](https://maven.apache.org/download.cgi)
- **Docker** (opcional) - [Download Docker](https://www.docker.com/products/docker-desktop)
- **Git** - [Download Git](https://git-scm.com/downloads)

### Verificar Instalações

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar Docker (opcional)
docker --version
```

---

## 🚀 Instalação e Configuração

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/encurtador-url.git
cd encurtador-url
```

### 2. Configuração do Banco de Dados

O projeto usa **H2 Database** (em memória) por padrão, configurado em `src/main/resources/application.properties`:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

> **Nota**: O banco é recriado a cada execução (`create-drop`)

### 3. Instalar Dependências

```bash
mvn clean install
```

---

## ▶️ Executando o Projeto

### Opção 1: Execução Local com Maven

```bash
# Executar a aplicação
mvn spring-boot:run
```

A aplicação iniciará em `http://localhost:8080`

### Opção 2: Build e Execução do JAR

```bash
# Compilar e criar o JAR
mvn clean package

# Executar o JAR
java -jar target/encurtador-url-0.0.1-SNAPSHOT.jar
```

### Opção 3: Docker Compose

```bash
# Construir e executar com Docker Compose
docker-compose up --build
```

A aplicação estará disponível em:
- API REST: `http://encurtador.localhost/api/`
- H2 Console: `http://localhost:8080/h2-console/`
- Traefik Dashboard: `http://localhost:8080/`

---

## 📡 API Endpoints

### 1. **Encurtar URL** - `POST /api/shorten`

Cria um encurtamento para uma URL.

**Request:**
```json
{
  "url": "https://www.exemplo.com/pagina/muito/longa/com/muitos/parametros?param1=valor1&param2=valor2"
}
```

**Response:**
```json
{
  "shortUrl": "http://encurtador.localhost/api/abc123",
  "originalUrl": "https://www.exemplo.com/pagina/muito/longa/com/muitos/parametros?param1=valor1&param2=valor2",
  "clicks": 0
}
```

**Status HTTP:** `201 Created`

---

### 2. **Redirecionar para URL Original** - `GET /api/{shortCode}`

Redireciona para a URL original e incrementa o contador de cliques.

**Exemplo:**
```
GET http://localhost:8080/api/abc123
```

**Response:** Redirecionamento HTTP 302 para a URL original

**Status HTTP:** `302 Found`

**Headers:**
```
Location: https://www.exemplo.com/pagina/muito/longa/...
```

---

### 3. **Listar Todas as URLs** - `GET /api/links`

Retorna todas as URLs encurtadas cadastradas.

**Response:**
```json
[
  {
    "shortUrl": "http://encurtador.localhost/api/abc123",
    "originalUrl": "https://www.exemplo.com/...",
    "clicks": 5
  },
  {
    "shortUrl": "http://encurtador.localhost/api/def456",
    "originalUrl": "https://github.com/usuario/repo",
    "clicks": 12
  }
]
```

**Status HTTP:** `200 OK`

---

### 4. **Deletar URL Encurtada** - `DELETE /api/{shortCode}`

Remove uma URL encurtada do sistema.

**Exemplo:**
```
DELETE http://localhost:8080/api/abc123
```

**Response:**
```json
{
  "message": "Link deleted successfully"
}
```

**Status HTTP:** `200 OK`

---

## 💡 Exemplos de Uso

### Usando cURL

```bash
# 1. Encurtar uma URL
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{"url":"https://www.google.com/search?q=spring+boot"}'

# 2. Acessar a URL encurtada (irá redirecionar)
curl -L http://localhost:8080/api/abc123

# 3. Listar todas as URLs
curl http://localhost:8080/api/links

# 4. Deletar uma URL
curl -X DELETE http://localhost:8080/api/abc123
```

### Usando Postman

1. **Import Collection:**
   - File → Import → Selecione `postman_collection.json`

2. **Encurtar URL:**
   - Método: `POST`
   - URL: `http://localhost:8080/api/shorten`
   - Body (raw JSON): `{"url":"https://www.exemplo.com"}`
   - Send

3. **Redirecionar:**
   - Método: `GET`
   - URL: `http://localhost:8080/api/{shortCode}`
   - Send

### Usando JavaScript/Fetch

```javascript
// Encurtar URL
async function shortenUrl(url) {
  const response = await fetch('http://localhost:8080/api/shorten', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ url })
  });
  return await response.json();
}

// Listar URLs
async function getAllLinks() {
  const response = await fetch('http://localhost:8080/api/links');
  return await response.json();
}

// Exemplo de uso
(async () => {
  const result = await shortenUrl('https://www.google.com');
  console.log(result);
})();
```

---

## 🏗️ Arquitetura

### Padrão de Design: Clean Architecture

O projeto segue os princípios de **Clean Architecture**, separando as responsabilidades em camadas:

#### **Domain Layer** (`/domain`)
- Contém as entidades de negócio (`Link.java`)
- Define interfaces de repositório (`ILinkRepository`)
- Independente de frameworks e detalhes de implementação
- **Regra**: Não conhece nada sobre BD, Controllers ou Frameworks

#### **Data Layer** (`/data`)
- Implementação de persistência com JPA
- Entidades JPA (`LinkEntity.java`)
- Mapeadores entre Domain e Data (`LinkMapper.java`)
- Acoplamento com banco de dados isolado aqui

#### **Services** (`/services`)
- Lógica de negócio (`LinkService.java`)
- Orquestração entre camadas
- Cálculo de Base62 e gerenciamento de URLs

#### **Use Cases** (`/useCases`)
- Representam ações específicas do sistema
- Implementam `IUseCase<Command, Response>`
- Exemplo: `EncurtarUrlUseCase`, `BuscarLinkPorLinkCurtoUseCase`

#### **Mediador** (`/mediator`)
- Implementa o **padrão Mediador**
- `CommandBus`: Roteador de comandos para use cases
- Desacoplamento entre Controllers e Use Cases

#### **Controllers** (`/controllers`)
- Endpoints REST
- Recebem requisições HTTP
- Delegam ao CommandBus

### Fluxo de Requisição

```
HTTP Request
    ↓
LinkController.shortenUrl()
    ↓
CommandBus.send(ShortenCommand)
    ↓
Mediator (encontra EncurtarUrlUseCase)
    ↓
EncurtarUrlUseCase.executar()
    ↓
LinkService.shortenUrl()
    ↓
ContadorService.getNext() → Base62Service.base10ToBase62()
    ↓
LinkRepository.save(LinkEntity)
    ↓
H2 Database
    ↓
LinkResponse
    ↓
HTTP Response (201 Created)
```

---

## 🔄 Fluxo de Dados

### Encurtamento de URL

```
1. Cliente envia: POST /api/shorten
   {
     "url": "https://www.exemplo.com/pagina/longa"
   }

2. LinkController recebe ShortenRequest

3. Cria EncurtarUrlCommand(url)

4. CommandBus roteia para EncurtarUrlUseCase

5. EncurtarUrlUseCase chama LinkService.shortenUrl()

6. LinkService:
   a. Chama ContadorService.getNext() → obtém número sequencial
   b. Chama Base62Service.base10ToBase62() → converte para Base62
   c. Cria LinkEntity com (codigoBase62, urlOriginal, clicks=0)
   d. Salva no banco via ILinkRepository
   e. Converte para LinkResponse

7. Response é retornado ao cliente:
   {
     "shortUrl": "http://encurtador.localhost/api/abc123",
     "originalUrl": "https://www.exemplo.com/pagina/longa",
     "clicks": 0
   }
```

### Acesso à URL Encurtada

```
1. Cliente acessa: GET /api/abc123

2. LinkController.getLinkByShortCode(shortCode)

3. CommandBus roteia para BuscarLinkPorLinkCurtoUseCase

4. LinkService.getLinkByShortCode():
   a. Busca LinkEntity pelo linkCurto
   b. Se não encontra → ResourceNotFoundException
   c. Incrementa contador de cliques (click++)
   d. Salva LinkEntity atualizada
   e. Retorna LinkResponse

5. LinkController retorna:
   - Status: 302 (Found)
   - Header: Location: {originalUrl}
   - Browser automaticamente redireciona

6. Cliente é redirecionado para a URL original
```

---

## 💾 Banco de Dados

### H2 Database

**Tipo**: Em memória (in-memory)  
**Console**: `http://localhost:8080/h2-console/`

**Credenciais:**
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (vazio)

### Schema

```sql
-- Tabela de Links
CREATE TABLE TB_LINK (
    LINK_ID INT AUTO_INCREMENT PRIMARY KEY,
    LINK_CURTO VARCHAR(255) NOT NULL UNIQUE,
    LINK_ORIGINAL VARCHAR(2048) NOT NULL,
    QT_CLICKS INT NOT NULL DEFAULT 0
);

-- Índices para melhor performance
CREATE INDEX IDX_LINK_CURTO ON TB_LINK(LINK_CURTO);
```

### Estrutura de Dados

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `LINK_ID` | INT (Auto-increment) | Chave primária |
| `LINK_CURTO` | VARCHAR(255) | Código Base62 (ex: "abc123") |
| `LINK_ORIGINAL` | VARCHAR(2048) | URL original completa |
| `QT_CLICKS` | INT | Contador de acessos |

### Acessar H2 Console

1. Inicie a aplicação
2. Acesse: `http://localhost:8080/h2-console/`
3. Faça login com as credenciais acima
4. Execute queries SQL para explorar os dados

**Queries Úteis:**

```sql
-- Ver todos os links
SELECT * FROM TB_LINK;

-- Ver links ordenados por cliques
SELECT * FROM TB_LINK ORDER BY QT_CLICKS DESC;

-- Contar total de links
SELECT COUNT(*) FROM TB_LINK;

-- Links criados por dia
SELECT DATE(CURRENT_TIMESTAMP) AS data, COUNT(*) FROM TB_LINK GROUP BY DATE(CURRENT_TIMESTAMP);
```

---

## 🐳 Docker

### Dockerfile

```dockerfile
# Stage 1 - Build
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2 - Runtime
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose

O projeto inclui `docker-compose.yml` com:

- **App Service**: Aplicação Spring Boot
- **Traefik**: Proxy reverso e load balancer
- **H2 Database**: Banco de dados em memória
- **Redis** (futuro): Para cache e contador

### Executar com Docker

```bash
# Build e start
docker-compose up --build

# Start (sem rebuild)
docker-compose up

# Stop
docker-compose down

# Logs
docker-compose logs -f app
```

### Acessar Serviços

| Serviço | URL |
|---------|-----|
| **API** | http://encurtador.localhost/api/ |
| **H2 Console** | http://localhost:8080/h2-console/ |
| **Traefik Dashboard** | http://localhost:8080/ |

---

## 🧪 Testes

### Estrutura de Testes

```
src/test/java/com/projetos/encurtador_url/
├── EncurtadorUrlApplicationTests.java
└── services/
    └── LinkServiceTest.java
```

### Executar Testes

```bash
# Executar todos os testes
mvn test

# Executar com cobertura
mvn jacoco:report

# Executar teste específico
mvn test -Dtest=LinkServiceTest
```

### Exemplo de Teste

```java
@SpringBootTest
class LinkServiceTest {
    
    @Autowired
    private LinkService linkService;

    @Test
    void testShortenUrl() {
        String originalUrl = "https://www.google.com";
        
        LinkResponse response = linkService.shortenUrl(originalUrl);
        
        assertNotNull(response);
        assertEquals(originalUrl, response.getOriginalUrl());
        assertTrue(response.getShortUrl().contains("api/"));
        assertEquals(0, response.getClicks());
    }
}
```

---

## 📈 Melhorias Futuras

- [ ] **Redis**: Implementar cache para URLs frequentes
- [ ] **Redis**: Usar para contador distribuído ao invés de BD
- [ ] **Analytics**: Dashboard com estatísticas de acessos
- [ ] **Rate Limiting**: Limitar requisições por IP
- [ ] **Custom Codes**: Permitir definir códigos personalizados
- [ ] **QR Code**: Gerar QR codes para URLs encurtadas
- [ ] **Expiration**: URLs que expiram após determinado tempo
- [ ] **API Key**: Autenticação com chaves API
- [ ] **Batch Operations**: Encurtar múltiplas URLs em uma requisição
- [ ] **URL Validation**: Validar URLs com regex avançado
- [ ] **Logging**: Implementar logging estruturado (SLF4J + Logback)
- [ ] **Monitoring**: Métricas com Micrometer e Prometheus

---

## 🔗 Base62 - Como Funciona

### O que é Base62?

Base62 usa 62 caracteres diferentes: `0-9` (10), `a-z` (26), `A-Z` (26)

Isso permite codificar números muito longos em strings curtas.

### Exemplo

```
Número (Base 10) → Base62
1                 → 1
10                → A
61                → z
62                → 10
1000              → GY
1000000           → 4C92
1000000000        → 15FTGy
```

### Por que Base62?

| Encoding | Tamanho para 1 bilhão |
|----------|----------------------|
| Base 10 | 10 caracteres (1000000000) |
| Base 16 (Hex) | 8 caracteres (3B9ACA00) |
| Base 62 | 6 caracteres (15FTGy) |
| Base 64 | 6 caracteres (AQIDBQ) |

**Vantagem**: Sem caracteres especiais, mais seguro em URLs

### Implementação

Enum `CaracteresBase62Enum`:

```java
0-9:  CHAR_0 a CHAR_9       (valores 0-9)
a-z:  CHAR_a a CHAR_z       (valores 10-35)
A-Z:  CHAR_A a CHAR_Z       (valores 36-61)
```

Conversão:
```java
Long base10 = 1234;
while (base10 > 0) {
    Long index = base10 % 62;  // Resto da divisão
    char character = CaracteresBase62Enum.fromValue(index).getCharacter();
    base10 = base10 / 62;      // Próxima iteração
}
```

---

## 📝 Configurações Importantes

### application.properties

```properties
# Spring Application
spring.application.name=encurtador-url
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Redis (futuro)
spring.data.redis.host=${SPRING_REDIS_HOST:localhost}
spring.data.redis.port=${SPRING_REDIS_PORT:6379}
```

### Variáveis de Ambiente

```bash
# Redis
export SPRING_REDIS_HOST=redis.example.com
export SPRING_REDIS_PORT=6379

# Banco de Dados
export SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
export SPRING_DATASOURCE_USERNAME=sa
```

---


## 👨‍💻 Autor

**João Gabriel** - [@joaoobarreto](https://github.com/joaoobarreto)

---

## 📞 Suporte

Para dúvidas ou problemas:

1. Abra uma **Issue** no repositório
2. Envie um **Email** para gabrielbcardoso@hotmail.com
---

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://www.h2database.com/)
- [Docker Documentation](https://docs.docker.com/)
- [Base62 Encoding](https://en.wikipedia.org/wiki/Base64)
- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

---

## 🎯 Quick Start

```bash
# 1. Clonar
git clone https://github.com/seu-usuario/encurtador-url.git && cd encurtador-url

# 2. Instalar dependências
mvn clean install

# 3. Executar
mvn spring-boot:run

# 4. Testar API
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{"url":"https://www.google.com"}'

# 5. Acessar H2 Console
# http://localhost:8080/h2-console/
```

---

**Última Atualização**: Maio de 2026

**Status**: ✅ Em Desenvolvimento | 🚀 Pronto para Produção Básica | 📋 Melhorias Planejadas

