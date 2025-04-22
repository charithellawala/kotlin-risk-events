INSERT INTO supply_chain.suppliers (name, country, industry)
VALUES
    ('ElectroParts Co', 'CN', 'ELECTRONICS'),
    ('AutoSupplies GmbH', 'DE', 'AUTOMOTIVE'),
    ('TextileMakers Ltd', 'BD', 'APPAREL');

-- Set updated_at explicitly for seeded data
UPDATE supply_chain.suppliers SET updated_at = NOW();