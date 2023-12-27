# Ktor ORM

This project compares different ORM libraries for Ktor.

# Libraries

Every library is implemented in a separate branch.

- Ktorm (SQL DSL): `ktorm/sql`
- Ktorm (Entity API): `ktorm/entity`
- Exposed (SQL DSL): `exposed/sql`
- Exposed (DAO API): `exposed/dao`

# Prerequisites

- Kotlin
- MySQL

# How to run

1. Create a MySQL database named `ktorm_orm` (username: `root`, empty password)
2. Run the database migrations in `resources/setup.sql`
3. Checkout one of the implementation branches
4. Start the application by running `./gradlew run` or directly from `Application.kt`
5. Execute the HTTP requests inside `Requests.http`
