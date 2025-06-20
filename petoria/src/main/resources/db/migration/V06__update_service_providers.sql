ALTER TABLE official_providers RENAME TO service_providers;

ALTER TABLE service_providers
    ADD COLUMN description TEXT;

CREATE TABLE service_provider_types (
    provider_id BIGINT NOT NULL,
    type VARCHAR(30) NOT NULL CHECK (
        type IN ('VET', 'GROOMING', 'PET_SHOP', 'SHELTER', 'MARKET')
    ),
    FOREIGN KEY (provider_id) REFERENCES service_providers(id) ON DELETE CASCADE
);