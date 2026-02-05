<div align="center">

  <h1>🏦 Banking Microservices Challenge</h1>

  <p>
    Solución de <b>microservicios bancarios</b> desarrollada con <b>Spring Boot 3</b> y <b>Java 17</b>, basada en
    <b>Arquitectura Limpia</b>, separación de responsabilidades y mensajería asíncrona.
  </p>

  <p>
    <img alt="Java" src="https://img.shields.io/badge/Java-17-informational" />
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-3.3.5-informational" />
    <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-16-informational" />
    <img alt="RabbitMQ" src="https://img.shields.io/badge/RabbitMQ-Message%20Broker-informational" />
    <img alt="Docker" src="https://img.shields.io/badge/Docker-Compose-informational" />
    <img alt="Tests" src="https://img.shields.io/badge/Tests-JUnit5%20%7C%20MockMvc-informational" />
  </p>

</div>

<hr/>

<h2>📌 Descripción</h2>
<p>
  Este proyecto implementa una plataforma para la gestión de <b>clientes</b>, <b>cuentas</b>, <b>movimientos</b>
  y <b>reportes</b>, con persistencia en <b>PostgreSQL</b>, migraciones con <b>Flyway</b> y mensajería asíncrona con
  <b>RabbitMQ</b>.
</p>

<h2>🧩 Microservicios</h2>
<ul>
  <li><b>customer-service</b> → Gestión de clientes y datos personales.</li>
  <li><b>account-service</b> → Gestión de cuentas bancarias, movimientos y reportes.</li>
</ul>

<h2>🏗️ Arquitectura del Proyecto</h2>
<pre><code>banking-microservices-challenge
│
├── customer-service
│   ├── controller
│   ├── domain
│   ├── dto
│   ├── repository
│   ├── service
│   └── resources
│       └── db/migration
│
├── account-service
│   ├── controller
│   ├── domain
│   ├── dto
│   ├── repository
│   ├── service
│   └── resources
│       └── db/migration
│
├── docker-compose.yml
├── BaseDatos.sql
└── pom.xml
</code></pre>

<h2>🛠️ Tecnologías Utilizadas</h2>
<ul>
  <li><b>Java 17</b></li>
  <li><b>Spring Boot 3.3.5</b></li>
  <li>Spring Web</li>
  <li>Spring Data JPA</li>
  <li>Flyway</li>
  <li>PostgreSQL 16</li>
  <li>RabbitMQ</li>
  <li>Maven</li>
  <li>JUnit 5</li>
  <li>MockMvc</li>
  <li>Docker / Docker Compose</li>
</ul>

<h2>🐳 Infraestructura (Docker)</h2>

<h3>Contenedores</h3>

<h4>PostgreSQL</h4>
<ul>
  <li><b>Puerto:</b> 5433</li>
  <li><b>Usuario:</b> banking</li>
  <li><b>Password:</b> banking</li>
</ul>

<h4>RabbitMQ</h4>
<ul>
  <li><b>Puerto AMQP:</b> 5672</li>
  <li><b>Puerto UI:</b> 15672</li>
</ul>

<h3>▶️ Levantar infraestructura</h3>
<pre><code>docker compose up -d</code></pre>

<h3>Verificar contenedores</h3>
<pre><code>docker ps</code></pre>

<h2>🗄️ Base de Datos</h2>

<h3>Migraciones (Flyway)</h3>
<p>
  Las tablas se crean automáticamente al iniciar los microservicios mediante <b>Flyway</b>.
</p>

<h3>Tablas principales</h3>
<ul>
  <li><code>person</code></li>
  <li><code>customer</code></li>
  <li><code>customer_snapshot</code></li>
  <li><code>account</code></li>
  <li><code>movement</code></li>
</ul>

<h3>Script manual</h3>
<p>
  El archivo <b>BaseDatos.sql</b> contiene el esquema completo para recrear manualmente la base de datos si se requiere.
</p>

<h3>🔍 Ver datos en la base</h3>
<pre><code>docker exec -it banking-postgres psql -U banking -d customer_db</code></pre>

<p><b>Consultas útiles:</b></p>
<pre><code>SELECT * FROM person;
SELECT * FROM customer;
SELECT * FROM account;
SELECT * FROM movement;</code></pre>

<h2>🚀 Ejecución de los Microservicios</h2>

<h3>Customer Service</h3>
<pre><code>mvn -pl customer-service spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=it"</code></pre>
<ul>
  <li><b>Puerto por defecto:</b> 8081</li>
</ul>

<h3>Account Service</h3>
<pre><code>mvn -pl account-service spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=it"</code></pre>
<ul>
  <li><b>Puerto por defecto:</b> 8082</li>
</ul>

<h2>🧪 Testing</h2>

<h3>Tests Unitarios</h3>
<p>Ejecutan pruebas de entidades y lógica de negocio <b>sin base de datos</b>:</p>
<pre><code>mvn clean test</code></pre>

<h3>Integration Tests (IT)</h3>
<p>
  Los IT utilizan la infraestructura real levantada por Docker (sin Testcontainers).
</p>
<pre><code>mvn -P it clean verify</code></pre>

<h2>✅ Validaciones</h2>
<p>Las validaciones se implementan mediante <b>Bean Validation (jakarta.validation)</b>:</p>
<ul>
  <li>Campos obligatorios (<code>@NotBlank</code>, <code>@NotNull</code>)</li>
  <li>Longitudes máximas</li>
  <li>Reglas de negocio (ej. contraseña mínima)</li>
</ul>


