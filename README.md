# CLI-Agenda

CLI-Agenda es una aplicacion de consola escrita en Java que permite gestionar tareas, notas y eventos directamente desde la terminal.

---

## Funcionalidades

### Tareas (Tasks)
- Crear, listar, buscar, actualizar y eliminar tareas
- Filtrar tareas por estado: todas, incompletas, completadas
- Cada tarea puede marcarse como completada/incompleta
- Las tareas pueden asociarse a un evento

### Notas (Notes)
- Crear, listar, buscar, actualizar y eliminar notas
- Las notas deben asociarse a una tarea existente
- Al crear una nota, se muestra la lista de tareas disponibles

### Eventos (Events)
- Crear, listar, buscar, actualizar y eliminar eventos
- Validacion de formato de fecha con reintento en caso de error
- Los eventos pueden tener repeticion (recurring)
- Al buscar un evento, se muestran las tareas asociadas

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

## Menu de la aplicacion

### Menu principal
- 1 - TASKS
- 2 - NOTES
- 3 - EVENTS
- 0 - Salir

### Submenu Tasks
- 1 - Create task
- 2 - List all tasks
- 3 - List incomplete tasks
- 4 - List completed tasks
- 5 - Find task
- 6 - Update task
- 7 - Delete task
- 0 - Volver

### Submenu Notes
- 1 - Create note
- 2 - List notes
- 3 - Find note
- 4 - Update note
- 5 - Delete note
- 0 - Volver

### Submenu Events
- 1 - Create event
- 2 - List events
- 3 - Find event
- 4 - Update event
- 5 - Delete event
- 0 - Volver

---

## Tecnologias utilizadas

- Java — lenguaje principal
- JDBC — conexion a base de datos
- Docker — despliegue de la base de datos
- Maven — gestion de dependencias
- JUnit 5 — pruebas unitarias
- MySQL 8.0 — base de datos

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
│   ├── event/             # Gestion de eventos (model, service, repository, cli)
│   ├── task/              # Gestion de tareas (model, service, repository, cli)
│   ├── note/              # Gestion de notas (model, service, repository, cli)
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

Copyleft 2025 Ulises Lafuente, Daniel Vila

CLI-Agenda es software libre; puedes redistribuirlo y/o modificarlo bajo los terminos de la Licencia Apache 2.0.

CLI-Agenda se distribuye con la esperanza de que sea util, pero SIN NINGUNA GARANTIA; sin siquiera la garantia implicita de COMERCIABILIDAD o APTITUD PARA UN PROPOSITO PARTICULAR. Consulta la Licencia Apache 2.0 para mas detalles.
