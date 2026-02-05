CREATE TABLE IF NOT EXISTS customer_snapshot (
    customer_id BIGINT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    identificacion VARCHAR(50) NOT NULL,
    estado BOOLEAN NOT NULL
    );

CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(30) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo NUMERIC(18,2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id BIGINT NOT NULL
);

CREATE TABLE movement (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES account(id),
    fecha TIMESTAMPTZ NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor NUMERIC(18,2) NOT NULL,
    saldo_disponible NUMERIC(18,2) NOT NULL
);

CREATE INDEX idx_account_cliente ON account(cliente_id);
CREATE INDEX idx_movement_fecha ON movement(fecha);
