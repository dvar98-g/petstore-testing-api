# Petstore API Test Automation

Framework de automatización de pruebas de API para la [Swagger Petstore](https://petstore.swagger.io), desarrollado como ejercicio técnico QA. Automatiza las funcionalidades solicitadas en la historia de usuario de la tienda de mascotas **"PerfDog"**, usando Rest Assured y TestNG, siguiendo buenas prácticas de código limpio para testing de APIs.

## Historia de usuario

> Como usuario quiero poder loguearme y obtener productos de la tienda, para tener todo listo y agilizar mi compra.

## Funcionalidades cubiertas

| # | Funcionalidad | Endpoint | Test |
|---|---|---|---|
| 1 | Crear un usuario | `POST /user` | `CreateUserTest` |
| 2 | Login con usuario recién creado | `GET /user/login` | `LoginTest` |
| 3 | Listar mascotas con status "disponible" | `GET /pet/findByStatus` | `FindByStatusTest` |
| 4 | Consultar una mascota específica | `GET /pet/{petId}` | `GetPetTest` |
| 5 | Crear una orden (compra) para una mascota | `POST /store/order` | `CreateOrderTest` |
| 6 | Logout | `GET /user/logout` | `LogoutTest` |

Adicionalmente, `CreatePetTest` valida la creación de mascotas como flujo base reutilizado por el resto del módulo Pet y por Store.

## Stack técnico

- **Java 25**
- **Maven** (gestor de dependencias y build)
- **Rest Assured 5.5.6** (cliente HTTP)
- **TestNG 7.10.2** (test runner)
- **Hamcrest 2.2** (assertions)
- **Jackson Databind 2.18.2** ((de)serialización de POJOs)
- **GitHub Actions** (CI/CD)

## Estructura del proyecto

```
src/test/java/com/qa/testing/
├── models/              # POJOs de las entidades de la API
│   ├── user/            # User
│   ├── pet/             # Pet, Category, Tag
│   └── order/           # Order
├── constants/           # Endpoints, params, status codes y enums, centralizados
├── config/              # Lectura de configuración (Config.java)
├── api/                 # Clases que ejecutan los requests HTTP por recurso
│   ├── BaseApi.java     # RequestSpecification compartido (baseUri, timeouts, content-type)
│   ├── UserApi.java
│   ├── PetApi.java
│   └── StoreApi.java
└── test/                # Tests organizados por módulo
    ├── user/
    ├── pet/
    └── store/

src/test/resources/
├── config.template.properties   # Plantilla versionada
├── config.properties            # Config real (NO versionado, ver .gitignore)
└── suites/
    ├── testng.xml                # Suite completa (los 3 módulos)
    ├── user.xml                  # Suite del módulo User
    ├── pet.xml                   # Suite del módulo Pet
    └── store.xml                 # Suite del módulo Store

.github/workflows/
└── api-tests.yml         # Pipeline de CI/CD (matrix por módulo)
```

## Configuración

El framework no tiene ningún valor hardcodeado. La configuración (`base.url`, `default.timeout.seconds`) se resuelve con esta prioridad:

1. **Variables de entorno** (`BASE_URL`, `DEFAULT_TIMEOUT_SECONDS`)
2. **`config.properties`** (si las env vars no están definidas)

Para correr el proyecto localmente:

```bash
cp src/test/resources/config.template.properties src/test/resources/config.properties
```

Y completar los valores en `config.properties`. Este archivo está en `.gitignore` y nunca se sube al repositorio.

## Cómo correr los tests

Suite completa:

```bash
mvn test
```

Un módulo puntual (usando el suite XML correspondiente):

```bash
mvn test -DsuiteXmlFiles=src/test/resources/suites/user.xml
mvn test -DsuiteXmlFiles=src/test/resources/suites/pet.xml
mvn test -DsuiteXmlFiles=src/test/resources/suites/store.xml
```

Clases puntuales:

```bash
mvn test -Dtest=CreateUserTest,LoginTest
```

## CI/CD

El pipeline (`.github/workflows/api-tests.yml`) corre en cada push/PR a `main`, con una **matrix por módulo** (`user`, `pet`, `store`) ejecutada en jobs independientes y paralelos (`fail-fast: false`), seleccionando las clases de cada módulo vía `-Dtest=` para no requerir cambios en la configuración de Surefire.

Requiere los siguientes **Repository Secrets** configurados en GitHub:

| Secret | Descripción |
|---|---|
| `BASE_URL` | URL base de la Petstore API |
| `DEFAULT_TIMEOUT_SECONDS` | Timeout de conexión/socket en segundos |

## Principios de diseño aplicados

- **Sin valores hardcodeados**: toda configuración, endpoint y status code vive en `config`/`constants`.
- **POJOs con factories estáticas**: `User.random()`, `Pet.random()`, `Order.randomForPet(petId)`, evitando instanciación repetida en los tests.
- **Separación de responsabilidades**: las clases `api` solo ejecutan requests y devuelven la `Response` cruda; ninguna assertion vive ahí.
- **`RequestSpecification` compartido**: `BaseApi` centraliza base URL, content-type y timeouts, reutilizado por `UserApi`, `PetApi` y `StoreApi`.
- **Tests 100% independientes**: cada test crea su propia precondición (usuario, mascota, login, etc.) y hace su propio cleanup en `@AfterMethod`. Se pueden ejecutar en cualquier orden, en paralelo, o de forma aislada, sin depender de que otro test haya corrido antes.
- **Assertions con Hamcrest**, con mensajes descriptivos por cada validación.
- **Documentación del código**: todas las clases (`models`, `constants`, `config`, `api`, `test`) incluyen Javadoc explicando su propósito.
