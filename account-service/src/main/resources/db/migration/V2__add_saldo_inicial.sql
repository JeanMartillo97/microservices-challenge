ALTER TABLE account
    ADD COLUMN IF NOT EXISTS saldo_inicial NUMERIC(18,2) NOT NULL DEFAULT 0;

-- Para cuentas existentes: asumimos que el saldo actual era su saldo inicial
-- (si ya hubo movimientos, el saldo inicial real se calcula por ledger antes de fechaInicio)
UPDATE account
SET saldo_inicial = saldo
WHERE saldo_inicial = 0;
