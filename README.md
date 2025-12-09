# 📘 Backend - Gerenciamento de Filmes e Séries

## 👥 Integrantes do Projeto

* **Lucas de Amigo**
* **Carlos Eduardo**
* **Vitor Dias**

Backend desenvolvido com **Spring Boot**, utilizando **Spring Security**, **JWT**, **JPA/Hibernate**, **PostgreSQL**, **Validation** e **Lombok**. A aplicação permite gerenciar filmes e séries, além de os usuários poderem favoritar seus títulos preferidos.

---

## 🚀 Tecnologias Utilizadas

* **Spring Boot**
* **Swagger / OpenAPI (Documentação automática da API)**
* **Spring Security + JWT**
* **Spring Data JPA**
* **PostgreSQL**
* **Jakarta Validation**
* **Lombok**

---

## 📌 Funcionalidades Principais

* Registro e autenticação de usuários via JWT
* CRUD completo de filmes e séries
* Favoritar e desfavoritar títulos
* Controle de acesso baseado em papéis (USER/ADMIN)
* Validação de dados

---

## 🧱 Arquitetura

* **Controller** — Endpoints REST
* **Service** — Regras de negócio
* **Repository** — Acesso ao banco via JPA
* **DTOs** — Estruturas de entrada e saída de dados
* **Config** — JWT, CORS e segurança

---

## 🗂️ Entidades

### **User**

* `id`
* `username`
* `email`
* `password`
* `roles`

### **Title**

* `id`
* `title`
* `description`
* `type` (MOVIE/SERIES)
* `releaseDate`
* `posterUrl`

### **Favorite**

* `id`
* `userId`
* `titleId`
* `favoritedAt`

---

## 🔗 Endpoints Principais

### 🔐 **Autenticação**

| Método | Endpoint             | Descrição                    |
| ------ | -------------------- | ---------------------------- |
| POST   | `/api/auth/register` | Registrar usuário            |
| POST   | `/api/auth/login`    | Autenticar e gerar token JWT |

### 🎬 **Títulos (Filmes/Séries)**

| Método | Endpoint           | Descrição                  |
| ------ | ------------------ | -------------------------- |
| GET    | `/api/titles`      | Listar títulos             |
| GET    | `/api/titles/{id}` | Buscar por ID              |
| POST   | `/api/titles`      | Criar título *(ADMIN)*     |
| PUT    | `/api/titles/{id}` | Atualizar título *(ADMIN)* |
| DELETE | `/api/titles/{id}` | Remover *(ADMIN)*          |

### ⭐ **Favoritos**

| Método | Endpoint                    | Descrição                   |
| ------ | --------------------------- | --------------------------- |
| POST   | `/api/titles/{id}/favorite` | Favoritar título            |
| DELETE | `/api/titles/{id}/favorite` | Desfavoritar                |
| GET    | `/api/users/me/favorites`   | Listar favoritos do usuário |

---

## 🔑 Autenticação JWT

1. Usuário faz login.
2. Servidor gera um token JWT assinado.
3. O client envia o token no header:

   ```
   Authorization: Bearer <token>
   ```
4. API valida o token e libera o acesso.

---

## ▶️ Como Executar

### **1️⃣ Configurar variáveis de ambiente**

###

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/filmesdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=senha
JWT_SECRET=chave-secreta
JWT_EXPIRATION_MS=3600000
```

### **2️⃣ Rodar o projeto**

```bash
mvn spring-boot:run
```

---

## 🎥 Integração com TMDB

A aplicação consome dados de filmes e séries utilizando a **API do TMDB (The Movie Database)**, permitindo:

* Buscar filmes e séries por nome
* Listar detalhes completos de cada título
* Obter posters, datas, sinopses e informações adicionais
* Preencher automaticamente o catálogo no backend

Para usar, é necessário configurar a variável de ambiente:

```env
TMDB_API_KEY=sua_chave_aqui
```

A comunicação é feita via chamadas HTTP para os endpoints públicos do TMDB.

---

