# Banking Backend (Spring Boot)

Plataforma bancaria modular construida con Spring Boot para simular un sistema completo de banca digital.

## 📋 Descripción

Backend API bancario que proporciona:

- Gestión de usuarios (registro, listado)
- Creación y administración de cuentas bancarias
- Operaciones de depósito y retiro
- Historial de transacciones
- Manejo seguro de dinero con precisión decimal

## 🏗️ Arquitectura

Arquitectura modular con tres módulos independientes:

- **users-module**: Gestión de usuarios
- **accounts-module**: Gestión de cuentas y transacciones
- **banking-app**: Aplicación principal Spring Boot

### Estructura de Capas por Módulo

Cada módulo sigue arquitectura en capas con separación clara de responsabilidades:

```
domain/          → Lógica de negocio pura (Entidades, Value Objects, Interfaces de Repositorio)
application/     → Casos de uso (Services)
infrastructure/  → Implementaciones técnicas (JPA Repositories, Adapters)
api/            → Controllers REST y DTOs
```

### Patrones Implementados

- **Domain-Driven Design (DDD)**: Lógica de negocio en el dominio
- **Repository Pattern**: Abstracción de persistencia
- **Adapter Pattern**: Implementación de repositorios con JPA
- **Value Objects**: `Money` para operaciones monetarias seguras
- **Factory Methods**: Creación de entidades de dominio

## 🛠️ Stack Tecnológico

- Java 21
- Spring Boot 3.3.5
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

## 💰 Precisión Monetaria

Este proyecto utiliza **BigDecimal** para todas las operaciones monetarias, garantizando:
- Precisión decimal exacta (sin errores de redondeo)
- Cálculos correctos en operaciones de depósito y retiro
- Almacenamiento de dinero en BD con columnas DECIMAL(19,2)
- Value Object `Money` que encapsula la lógica de operaciones aritméticas

## 🚀 Getting Started

### Requisitos

- Java 21+
- Maven 3.8+
- PostgreSQL 12+

### Instalación

1. Clonar el repositorio:
```bash
git clone <repository-url>
cd banking-backend
```

2. Crear la base de datos:
```bash
createdb banking
```

3. Actualizar credenciales en `banking-app/src/main/resources/config/application.yml`

4. Compilar y ejecutar:
```bash
mvn clean package
mvn -pl banking-app spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 📝 API Endpoints

### Usuarios

#### `POST /api/users` - Crear usuario

**Request:**
```json
{
  "name": "Juan Pérez",
  "email": "juan@example.com"
}
```

**Response (201 Created):**
```json
{
  "id": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
  "name": "Juan Pérez",
  "email": "juan@example.com"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez",
    "email": "juan@example.com"
  }'
```

---

#### `GET /api/users` - Listar usuarios

**Response (200 OK):**
```json
[
  {
    "id": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
    "name": "Juan Pérez",
    "email": "juan@example.com"
  },
  {
    "id": "b2c3d4e5-f6g7-48h9-i0j1-k2l3m4n5o6p7",
    "name": "María García",
    "email": "maria@example.com"
  }
]
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Content-Type: application/json"
```

---

### Cuentas

#### `POST /api/accounts` - Crear cuenta

**Request:**
```json
{
  "userId": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
  "type": "CHECKING"
}
```

**Tipos de cuenta disponibles:** `CHECKING`, `SAVINGS`

**Response (201 Created):**
```json
{
  "id": "c3d4e5f6-g7h8-49i0-j1k2-l3m4n5o6p7q8",
  "userId": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
  "type": "CHECKING",
  "balance": 0.00
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
    "type": "CHECKING"
  }'
```

---

#### `POST /api/accounts/{accountId}/deposit` - Depósito

**Request:**
```json
{
  "amount": 500.50
}
```

**Response (200 OK):**
```json
{
  "id": "c3d4e5f6-g7h8-49i0-j1k2-l3m4n5o6p7q8",
  "userId": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
  "type": "CHECKING",
  "balance": 500.50
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/accounts/c3d4e5f6-g7h8-49i0-j1k2-l3m4n5o6p7q8/deposit \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 500.50
  }'
```

---

#### `POST /api/accounts/{accountId}/withdraw` - Retiro

**Request:**
```json
{
  "amount": 100.00
}
```

**Response (200 OK):**
```json
{
  "id": "c3d4e5f6-g7h8-49i0-j1k2-l3m4n5o6p7q8",
  "userId": "a1b2c3d4-e5f6-47g8-h9i0-j1k2l3m4n5o6",
  "type": "CHECKING",
  "balance": 400.50
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/accounts/c3d4e5f6-g7h8-49i0-j1k2-l3m4n5o6p7q8/withdraw \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 100.00
  }'
```

---

#### `GET /api/accounts/{accountId}/transactions` - Ver transacciones

**Response (200 OK):**
```json
[
  {
    "id": "d4e5f6g7-h8i9-40j1-k2l3-m4n5o6p7q8r9",
    "accountId": "c3d4e5f6-g7h8-49i0-j1k2-l3m4n5o6p7q8",
    "amount": 500.50,
    "occurredAt": "2026-03-12T22:30:15.123456",
    "description": "Deposit"
  },
  {
    "id": "e5f6g7h8-i9j0-41k2-l3m4-n5o6p7q8r9s0",
    "accountId": "c3d4e5f6-g7h8-49i0-j1k2-l3m4n5o6p7q8",
    "amount": 100.00,
    "occurredAt": "2026-03-12T22:35:42.654321",
    "description": "Withdrawal"
  }
]
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/accounts/c3d4e5f6-g7h8-49i0-j1k2-l3m4n5o6p7q8/transactions \
  -H "Content-Type: application/json"
```

---

### Códigos de Respuesta

| Código | Descripción |
|--------|-------------|
| **200** | Éxito (GET) |
| **201** | Recurso creado (POST) |
| **400** | Solicitud inválida (validación fallida) |
| **404** | Recurso no encontrado |
| **500** | Error interno del servidor |

### Ejemplos de Errores

**Email duplicado (400 Bad Request):**
```json
{
  "error": "Email already registered",
  "status": 400
}
```

**Fondos insuficientes (400 Bad Request):**
```json
{
  "error": "Insufficient funds",
  "status": 400
}
```

**Cuenta no encontrada (404 Not Found):**
```json
{
  "error": "Account not found",
  "status": 404
}
```

---

### Validaciones

| Campo | Validación |
|-------|-----------|
| **name** | Requerido, no vacío |
| **email** | Requerido, formato email válido, único |
| **userId** | Requerido, UUID válido |
| **type** | Requerido, valores: CHECKING o SAVINGS |
| **amount** | Requerido, debe ser > 0.01 |

## 🧪 Testing

```bash
mvn test
```

---

**Versión**: 1.0.0 | **Java**: 21 | **Spring Boot**: 3.3.5

