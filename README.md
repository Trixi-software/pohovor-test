# Municipalities Parser - Kopidlno (Obce a Části Obcí)

A lightweight Java / Spring Boot application designed to download, process, and persist territorial administration data from the Czech State Administration of Land Surveying and Cadastre (RÚIAN / ČÚZK). 

On startup, the application automatically downloads an official zipped XML dataset for the municipality of **Kopidlno**, parses municipality and municipality part records using high-performance streaming (StAX), and stores them into a relational PostgreSQL database.

---

## 🛠️ Technology Stack

- **Language:** Java 21
- **Framework:** Spring Boot (Spring Data JPA, Hibernate)
- **XML Processing:** StAX
- **Database:** PostgreSQL
- **Database GUI:** Adminer
- **Testing:** JUnit 5
- **Containerization & Orchestration:** Docker, Docker Compose
- **Boilerplate & Logging:** Project Lombok, SLF4J

---

## 📦 Prerequisites

To run this project, you only need:
- **Docker** and **Docker Compose** installed on your machine.

*(Optional for local development without Docker: JDK 21 and Maven).*

---

## 🚀 How to Run

### 1. Build and Start the Containers
Open your terminal in the project root directory and run:

```bash
docker compose up --build
```

### 2. Execution Flow
The system will automatically:
1. Initialize the **PostgreSQL** database and wait until its healthcheck passes.
2. Start **Adminer** (web database client).
3. Start the **Parser** application, which will:
   - Download `kopidlno.xml.zip` from [smartform.cz](https://www.smartform.cz/download/kopidlno.xml.zip).
   - Unpack the archive to a temporary directory.
   - Parse the XML file in a single pass using the StAX stream reader.
   - Insert the parsed `Municipality` and `MunicipalityPart` records into PostgreSQL.

---

## 🔍 Inspecting the Data (Adminer Web UI)

You can inspect the saved records using the bundled Adminer web client:

1. Open your browser and navigate to:
   👉 **[http://localhost:8080](http://localhost:8080)**

2. Log in using the following credentials:
   - **System:** `PostgreSQL`
   - **Server:** `db`
   - **Username:** `admin`
   - **Password:** `admin`
   - **Database:** `municipalities_db`

3. Explore the tables:
   - `municipalities` — contains the municipality code and name (e.g. `573060` - `Kopidlno`).
   - `municipality_parts` — contains the municipality parts (e.g. `Kopidlno`, `Ledkov`, `Mlýnec`, `Drahoraz`, `Pševes`) linked via foreign key.

---

## 🗄️ Database Schema

The database model follows a one-to-many relationship:

```
+-----------------------------------+       +-------------------------------------+
|          municipalities           |       |         municipality_parts          |
+-----------------------------------+       +-------------------------------------+
| code (PK, Long)                   |<------| code (PK, Long)                     |
| name (VARCHAR, NOT NULL)          |  1:N  | name (VARCHAR, NOT NULL)            |
|                                   |       | municipality_id (FK -> code, Long)  |
+-----------------------------------+       +-------------------------------------+
```