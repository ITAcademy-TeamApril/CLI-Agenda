# CLI-Agenda

CLI-Agenda es una aplicacion de consola escrita en Java que permite gestionar tareas, notas y eventos directamente desde la terminal.

---

## Funcionalidades

- Crear, editar, listar y eliminar tareas
- Filtrar tareas por prioridad, estado y fecha
- Crear, editar, listar y eliminar notas asociadas a tareas
- Registrar eventos importantes (cumpleanos, recordatorios, citas...)
- Notificaciones para eventos proximos
- Repeticion de eventos anual o personalizada
- Todo gestionado desde la terminal con comandos claros

---

## Requisitos previos

- Java 17 o superior
- Docker y Docker Compose
- Maven 3.x

---

## Instrucciones de uso

### 1. Compilar el proyecto

```bash
mvn clean package
```

Esto generara el archivo `target/CLI-agenda-1.0-jar-with-dependencies.jar`.

### 2. Iniciar la base de datos MySQL

```bash
docker-compose up -d mysql
```

El contenedor MySQL iniciara en el puerto 3306 con persistencia de datos.

### 3. Ejecutar la aplicacion

```bash
java -jar target/CLI-agenda-1.0-jar-with-dependencies.jar
```

### 4. Detener la base de datos (opcional)

```bash
docker-compose down
```

---

## Tecnologias utilizadas

- Java — lenguaje principal
- JDBC — conexion a base de datos
- Docker — despliegue de la base de datos
- Maven — gestion de dependencias
- JUnit 5 — pruebas unitarias

---

## Arquitectura

- Patrones de diseno: Singleton, Repository
- Principios SOLID
- Estructura separada por funcionalidades (events, tasks, notes)
- Uso de Optional para evitar NullPointerException

---

## Estructura del proyecto

```
src/
├── main/java/com/itacademy/cliagenda/
│   ├── application/       # Punto de entrada y menu
│   ├── event/             # Gestion de eventos
│   ├── task/              # Gestion de tareas
│   ├── note/              # Gestion de notas
│   ├── infrastructure/    # Acceso a datos (SQL DAO)
│   └── common/            # Utilidades compartidas
└── test/                  # Pruebas unitarias
```

---

## Ramas de trabajo

- `main` — rama estable, lista para produccion
- `dev` — rama de desarrollo, donde se integran las funcionalidades
- `feature/*` / `docs/*` / `chore/*` — ramas de trabajo individuales

Cada funcionalidad se desarrolla en una rama propia y se integra en `dev` mediante Pull Requests revisadas por el equipo.


COPYRIGHT and LICENSE

Copyleft 2025 Ulises Lafuente, Daniel vila

BNotas es software libre; puedes redistribuirlo y/o modificarlo bajo los términos de la Licencia Apache 2.0.

BNotas se distribuye con la esperanza de que sea útil, pero SIN NINGUNA GARANTÍA; sin siquiera la garantía implícita de COMERCIABILIDAD o APTITUD PARA UN PROPÓSITO PARTICULAR. Consulta la Licencia Licencia Apache 2.0. para más detalles.