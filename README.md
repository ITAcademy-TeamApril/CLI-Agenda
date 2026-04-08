# CLI-Agenda 📋

CLI-Agenda es una aplicación de consola escrita en **Java** que permite gestionar tareas, notas y eventos directamente desde la terminal. El proyecto está desarrollado en equipo aplicando metodologías ágiles y buenas prácticas de desarrollo con **Git y GitHub**.

---

## 🚀 Funcionalidades

- Crear, editar, listar y eliminar **tareas**
- Filtrar tareas por prioridad, estado y fecha
- Crear, editar, listar y eliminar **notas** asociadas a tareas
- Registrar **eventos** importantes (cumpleaños, recordatorios, citas...)
- Notificaciones para eventos próximos
- Repetición de eventos anual o personalizada
- Todo gestionado desde la terminal con comandos claros

---

## 🛠️ Tecnologías utilizadas

- **Java** — lenguaje principal
- **Docker** — despliegue de la base de datos
- **Git & GitHub** — control de versiones y trabajo en equipo
- **GitHub Projects** — gestión de tareas con metodología ágil

---

## 🏗️ Arquitectura y buenas prácticas

- Patrón **Repository** para la gestión de persistencia
- Mínimo **tres patrones de diseño** (Singleton, Factory, Strategy...)
- Principios **SOLID**
- Uso de **DTOs** para la comunicación entre capas
- Estructura del proyecto separada **por funcionalidades**
- Uso de **Optional** para evitar NullPointerException

---

## 🌿 Flujo de trabajo con Git

- `main` → rama estable, lista para producción
- `dev` → rama de desarrollo, donde se integran las funcionalidades
- `feature/*` / `docs/*` / `chore/*` → ramas de trabajo individuales

Cada funcionalidad se desarrolla en una rama propia y se integra en `dev` mediante **Pull Requests** revisadas por el equipo.

---

## 🤝 Equipo

Proyecto desarrollado en equipo como práctica de metodologías ágiles y colaboración con Git.