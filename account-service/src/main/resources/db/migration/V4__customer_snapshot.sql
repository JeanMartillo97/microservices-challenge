CREATE TABLE IF NOT EXISTS customer_snapshot (
    customer_id BIGINT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    identificacion VARCHAR(50) NOT NULL,
    estado BOOLEAN NOT NULL
    );
