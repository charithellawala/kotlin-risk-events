
CREATE TABLE IF NOT EXISTS supply_chain.suppliers (
                                                       id BIGSERIAL PRIMARY KEY,
                                                       name VARCHAR(255) NOT NULL,
    country VARCHAR(2) NOT NULL,
    industry VARCHAR(50) NOT NULL,
    risk_score DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    risk_level VARCHAR(10) NOT NULL DEFAULT 'LOW',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT valid_risk_level CHECK (risk_level IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL'))
    );

-- Fixed syntax (removed NOT EXISTS after CREATE TABLE)
CREATE TABLE supply_chain.risk_events (
                                           id BIGSERIAL PRIMARY KEY,
                                           supplier_id BIGINT NOT NULL,
                                           event_type VARCHAR(50) NOT NULL,
                                           severity DECIMAL(3,1) NOT NULL,
                                           source VARCHAR(20) NOT NULL,
                                           description TEXT NOT NULL,
                                           detected_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                           location VARCHAR(100) NOT NULL,
                                           FOREIGN KEY (supplier_id) REFERENCES ${riskSchema}.suppliers(id) ON DELETE CASCADE,
                                           CONSTRAINT valid_severity CHECK (severity BETWEEN 0.0 AND 100.0)
);

-- Fixed index syntax (removed NOT EXISTS)
CREATE INDEX idx_risk_events_supplier ON supply_chain.risk_events(supplier_id);
CREATE INDEX idx_risk_events_detected ON supply_chain.risk_events(detected_at);