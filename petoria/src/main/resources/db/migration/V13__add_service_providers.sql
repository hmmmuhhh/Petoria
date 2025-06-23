ALTER TABLE service_provider_types
    DROP CONSTRAINT service_provider_types_type_check;

ALTER TABLE service_provider_types
    ADD CONSTRAINT service_provider_types_type_check
    CHECK (type IN ('VET', 'GROOMING', 'PET_SHOP', 'SHELTER', 'MARKET', 'OTHER'));

INSERT INTO service_providers (name, phone, location, website_url, logo_url, description)
VALUES
  ('Dosty', '551311437', 'Georgia, Tbilisi, Vazha-Pshavela 1 block, 45', 'https://dosty.pet/en', 'https://dosty.pet/_next/image?url=%2Fassets%2Fimages%2Flogo%2FlogoWithText.webp&w=256&q=75', 'Buy-sell or report a lost-found pet. Shop for food, medicine and accessories in one place.'),
  ('Zoomart', '555664725', 'Georgia, Tbilisi, Ana Politkovskaia st. 3', 'https://zoomart.ge/', 'https://zoomart.ge/assets/zoomart/images/logo.png', 'Everything for your pet.'),
  ('Petstory', null, 'Georgia, Tbilisi', 'https://petstory.ge/', 'https://forum.petstory.ge/themes/wowonder/img/logo.png', 'Everything about animals.'),
  ('Doghome', '591947626', 'Georgia, Tbilisi, Grigol Lortqipanidze street 9', 'https://www.doghome.ge/', 'https://www.doghome.ge/wp-content/uploads/2022/06/logo.png', 'Tamaz Elizbarashvili''s dog shelter'),
  ('GSPSA', '557923910', 'Georgia, Tbilisi, Nutsubidze II m/d, Quarter I, Building №3a', 'https://gspsa.org.ge/en/', 'https://gspsa.org.ge/wp-content/uploads/LOGOs/GSPSA-LOGO-A-1290-Transparent.png', 'Georgian Society for the Protection and Safety of Animals'),
  ('Groom', '596566566', 'Georgia, Tbilisi, Vake – Nikoloz Berdzenishvili 6', 'https://groom.ge/en/', 'https://groom.ge/wp-content/uploads/2021/12/groom.ge-logo.svg', 'N1 grooming salon chain - stressless environment for your pets'),
  ('Aibo', '0322911253', 'Georgia, Tbilisi, Sulkhan Tsintsadze 45', 'https://www.aibo.ge/en', 'https://static.wixstatic.com/media/8c07d4_2bd8512f183945bb90d26d1cdd6e3972~mv2.jpg/v1/fill/w_239,h_239,al_c,q_80,usm_0.66_1.00_0.01,enc_avif,quality_auto/8c07d4_2bd8512f183945bb90d26d1cdd6e3972~mv2.jpg', 'Highest Quality Care For Our Companion Animals');