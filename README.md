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
- `POST /api/users` - Crear usuario
- `GET /api/users` - Listar usuarios

### Cuentas
- `POST /api/accounts` - Crear cuenta
- `POST /api/accounts/{accountId}/deposit` - Depósito
- `POST /api/accounts/{accountId}/withdraw` - Retiro
- `GET /api/accounts/{accountId}/transactions` - Ver transacciones

## 🧪 Testing

```bash
mvn test
```

---

**Versión**: 1.0.0 | **Java**: 21 | **Spring Boot**: 3.3.5

