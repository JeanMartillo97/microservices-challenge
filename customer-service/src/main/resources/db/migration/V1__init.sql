CREATE TABLE IF NOT EXISTS person (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    genero VARCHAR(20) NOT NULL,
    edad INT NOT NULL CHECK (edad >= 0),
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(30) NOT NULL
    );

CREATE TABLE IF NOT EXISTS customer (
    cliente_id BIGINT PRIMARY KEY REFERENCES person(id),
    contrasena_hash VARCHAR(200) NOT NULL,
    estado BOOLEAN NOT NULL
    );
