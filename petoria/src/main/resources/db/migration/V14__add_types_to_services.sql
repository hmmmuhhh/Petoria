INSERT INTO service_provider_types (provider_id, type)
VALUES
  ((SELECT id FROM service_providers WHERE name = 'Dosty'), 'PET_SHOP'),
  ((SELECT id FROM service_providers WHERE name = 'Dosty'), 'MARKET'),
  ((SELECT id FROM service_providers WHERE name = 'Dosty'), 'OTHER'),
  ((SELECT id FROM service_providers WHERE name = 'Zoomart'), 'MARKET'),
  ((SELECT id FROM service_providers WHERE name = 'Petstory'), 'MARKET'),
  ((SELECT id FROM service_providers WHERE name = 'Petstory'), 'PET_SHOP'),
  ((SELECT id FROM service_providers WHERE name = 'Petstory'), 'VET'),
  ((SELECT id FROM service_providers WHERE name = 'Petstory'), 'SHELTER'),
  ((SELECT id FROM service_providers WHERE name = 'Petstory'), 'OTHER'),
  ((SELECT id FROM service_providers WHERE name = 'Doghome'), 'SHELTER'),
  ((SELECT id FROM service_providers WHERE name = 'GSPSA'), 'SHELTER'),
  ((SELECT id FROM service_providers WHERE name = 'Groom'), 'GROOMING'),
  ((SELECT id FROM service_providers WHERE name = 'Aibo'), 'VET');