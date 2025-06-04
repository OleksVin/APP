-- ===== СТВОРЕННЯ БАЗИ ДАНИХ AUTOPARTS.DB =====
-- SQL скрипт для SQLite бази даних з автомобілями та запчастинами

-- Створення таблиці vehicles
CREATE TABLE IF NOT EXISTS vehicles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    make TEXT NOT NULL,
    model TEXT NOT NULL,
    year_start INTEGER NOT NULL,
    year_end INTEGER NOT NULL,
    generation TEXT NOT NULL,
    body_type TEXT NOT NULL,
    engine_type TEXT NOT NULL,
    parts_count INTEGER NOT NULL DEFAULT 0
);

-- Створення таблиці parts
CREATE TABLE IF NOT EXISTS parts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    vehicle_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    category TEXT NOT NULL,
    part_number TEXT,
    description TEXT,
    specifications TEXT,
    replacement_interval TEXT,
    is_consumable INTEGER NOT NULL DEFAULT 0,
    image_url TEXT,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id) ON DELETE CASCADE
);

-- Створення індексів для оптимізації
CREATE INDEX IF NOT EXISTS idx_vehicles_make ON vehicles(make);
CREATE INDEX IF NOT EXISTS idx_vehicles_model ON vehicles(model);
CREATE INDEX IF NOT EXISTS idx_vehicles_make_model ON vehicles(make, model);
CREATE INDEX IF NOT EXISTS idx_parts_vehicle_id ON parts(vehicle_id);
CREATE INDEX IF NOT EXISTS idx_parts_category ON parts(category);
CREATE INDEX IF NOT EXISTS idx_parts_vehicle_category ON parts(vehicle_id, category);

-- ===== НАПОВНЕННЯ ТАБЛИЦІ VEHICLES =====

-- Німецькі марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('BMW', '3 Series', 2005, 2012, 'E90/E91/E92', 'Sedan/Wagon/Coupe', '2.0i, 3.0i, 2.0d', 142),
('BMW', '5 Series', 2003, 2010, 'E60/E61', 'Sedan/Wagon', '2.5i, 3.0i, 3.0d', 156),
('BMW', 'X5', 2006, 2013, 'E70', 'SUV', '3.0i, 4.8i, 3.0d', 134),
('Mercedes-Benz', 'C-Class', 2007, 2014, 'W204', 'Sedan/Wagon', '1.8, 2.5, 3.0, 2.2d', 148),
('Mercedes-Benz', 'E-Class', 2002, 2009, 'W211', 'Sedan/Wagon', '2.6, 3.2, 5.0, 3.2d', 162),
('Audi', 'A4', 2008, 2016, 'B8', 'Sedan/Wagon', '1.8T, 2.0T, 3.0, 2.0d', 145),
('Audi', 'A6', 2004, 2011, 'C6', 'Sedan/Wagon', '2.4, 3.2, 4.2, 3.0d', 158),
('Volkswagen', 'Passat', 2005, 2015, 'B6/B7', 'Sedan/Wagon', '1.8T, 2.0T, 3.6, 2.0d', 128),
('Volkswagen', 'Golf', 2008, 2013, 'Mk6', 'Hatchback', '1.4T, 1.6, 2.0T, 1.6d', 115),
('Volkswagen', 'Tiguan', 2007, 2016, '5N', 'SUV', '1.4T, 2.0T, 2.0d', 108);

-- Японські марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('Toyota', 'Camry', 2006, 2011, 'XV40', 'Sedan', '2.4, 3.5', 95),
('Toyota', 'Corolla', 2006, 2013, 'E140/E150', 'Sedan/Hatchback', '1.4, 1.6, 1.8', 87),
('Toyota', 'RAV4', 2005, 2012, 'XA30', 'SUV', '2.0, 2.4', 89),
('Honda', 'Civic', 2005, 2011, '8th Gen', 'Sedan/Hatchback', '1.3, 1.8, 2.0', 92),
('Honda', 'Accord', 2008, 2012, '8th Gen', 'Sedan', '2.0, 2.4, 3.5', 96),
('Honda', 'CR-V', 2006, 2012, '3rd Gen', 'SUV', '2.0, 2.4', 88),
('Mazda', '3', 2003, 2009, 'BK/BL', 'Hatchback/Sedan', '1.6, 2.0, 2.3', 85),
('Mazda', '6', 2002, 2008, 'GG/GY', 'Sedan/Wagon', '1.8, 2.0, 2.3', 91),
('Nissan', 'X-Trail', 2007, 2014, 'T31', 'SUV', '2.0, 2.5, 2.0d', 83),
('Nissan', 'Qashqai', 2006, 2013, 'J10', 'SUV', '1.6, 2.0, 1.5d', 79);

-- Американські марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('Ford', 'Focus', 2004, 2011, '2nd Gen', 'Hatchback/Sedan', '1.4, 1.6, 1.8, 2.0', 78),
('Ford', 'Mondeo', 2007, 2014, 'Mk4', 'Sedan/Wagon', '1.6, 2.0, 2.5, 2.0d', 94),
('Ford', 'Kuga', 2008, 2012, '1st Gen', 'SUV', '2.5T, 2.0d', 76),
('Chevrolet', 'Cruze', 2008, 2016, 'J300', 'Sedan/Hatchback', '1.6, 1.8, 1.4T', 72),
('Chevrolet', 'Captiva', 2006, 2017, 'C100/C140', 'SUV', '2.4, 3.2, 2.0d', 84);

-- Французькі марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('Renault', 'Megane', 2002, 2009, 'II', 'Hatchback/Sedan', '1.4, 1.6, 2.0, 1.5d', 68),
('Renault', 'Scenic', 2003, 2009, 'II', 'MPV', '1.4, 1.6, 2.0, 1.5d', 71),
('Peugeot', '307', 2001, 2008, '1st Gen', 'Hatchback/Sedan', '1.4, 1.6, 2.0, 1.6d', 65),
('Peugeot', '407', 2004, 2010, '1st Gen', 'Sedan/Wagon', '1.8, 2.0, 2.2, 2.0d', 73),
('Citroen', 'C4', 2004, 2010, '1st Gen', 'Hatchback', '1.4, 1.6, 2.0, 1.6d', 67);

-- Корейські марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('Hyundai', 'Elantra', 2006, 2011, 'HD', 'Sedan', '1.6, 2.0', 64),
('Hyundai', 'Santa Fe', 2006, 2012, 'CM', 'SUV', '2.4, 2.7, 3.3, 2.2d', 81),
('Hyundai', 'Tucson', 2004, 2010, 'JM', 'SUV', '2.0, 2.7, 2.0d', 75),
('Kia', 'Cerato', 2004, 2009, 'LD', 'Sedan/Hatchback', '1.6, 2.0', 62),
('Kia', 'Sportage', 2010, 2015, 'SL', 'SUV', '1.6T, 2.0, 1.7d', 74);

-- Італійські марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('Fiat', 'Grande Punto', 2005, 2012, '199', 'Hatchback', '1.2, 1.4, 1.6, 1.3d', 58),
('Alfa Romeo', '159', 2005, 2011, '939', 'Sedan/Wagon', '1.8, 2.2, 3.2, 1.9d', 69);

-- Чеські марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('Skoda', 'Octavia', 2004, 2013, 'A5', 'Hatchback/Wagon', '1.4, 1.6, 1.8T, 2.0, 1.9d', 86),
('Skoda', 'Superb', 2008, 2015, 'B6', 'Sedan/Wagon', '1.4T, 1.8T, 2.0T, 2.0d', 91),
('Skoda', 'Fabia', 2007, 2014, '5J', 'Hatchback/Wagon', '1.2, 1.4, 1.6, 1.2d', 67);

-- Російські марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('Lada', 'Priora', 2007, 2018, '2170', 'Sedan/Hatchback', '1.6', 54),
('Lada', 'Kalina', 2004, 2013, '1118', 'Hatchback/Sedan', '1.4, 1.6', 52);

-- Інші марки
INSERT INTO vehicles (make, model, year_start, year_end, generation, body_type, engine_type, parts_count) VALUES
('Mitsubishi', 'Outlander', 2006, 2012, 'CW', 'SUV', '2.0, 2.4, 3.0', 77),
('Mitsubishi', 'Lancer', 2007, 2017, 'CY', 'Sedan', '1.5, 1.6, 1.8, 2.0', 66),
('Subaru', 'Forester', 2008, 2012, 'SH', 'SUV', '2.0, 2.5, 2.5T', 82),
('Subaru', 'Legacy', 2003, 2009, 'BP/BL', 'Sedan/Wagon', '2.0, 2.5, 3.0', 79),
('Volvo', 'XC90', 2002, 2014, '1st Gen', 'SUV', '2.5T, 3.2, 4.4, 2.4d', 98),
('Volvo', 'S60', 2000, 2009, 'P24', 'Sedan', '2.0T, 2.3T, 2.5T, 2.4d', 89),
('Land Rover', 'Freelander', 2006, 2014, 'LR2', 'SUV', '2.2d, 3.2', 93),
('Jeep', 'Grand Cherokee', 2005, 2010, 'WK', 'SUV', '3.7, 4.7, 5.7, 3.0d', 96);

-- ===== НАПОВНЕННЯ ТАБЛИЦІ PARTS =====
-- Додаємо запчастини для кожного автомобіля за категоріями

-- Функція для додавання основних запчастин для кожного vehicle_id
-- Категорія: Двигун та система охолодження

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Моторна олива', 'Двигун', 'ENG-OIL-001', 'Синтетична моторна олива', '5W-30, 4-6 літрів', '10,000-15,000 км', 1, 'https://example.com/engine_oil.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Повітряний фільтр', 'Двигун', 'ENG-AIR-001', 'Фільтр повітря двигуна', 'Папір/синтетика', '15,000-20,000 км', 1, 'https://example.com/air_filter.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Паливний фільтр', 'Двигун', 'ENG-FUEL-001', 'Фільтр очищення палива', 'Для бензину/дизелю', '20,000-30,000 км', 1, 'https://example.com/fuel_filter.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Свічки запалювання', 'Двигун', 'ENG-SPARK-001', 'Запалювальні свічки', 'Іридієві/платинові', '30,000-60,000 км', 1, 'https://example.com/spark_plugs.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Ремінь ГРМ', 'Двигун', 'ENG-BELT-001', 'Ремінь газорозподільного механізму', 'Гумовий з кордом', '60,000-100,000 км', 1, 'https://example.com/timing_belt.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Антифриз', 'Двигун', 'ENG-COOL-001', 'Охолоджувальна рідина', 'G12/G13, -40°C', '40,000-60,000 км', 1, 'https://example.com/antifreeze.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Термостат', 'Двигун', 'ENG-THERM-001', 'Регулятор температури', '80-90°C відкриття', '80,000-120,000 км', 0, 'https://example.com/thermostat.jpg'
FROM vehicles;

-- Категорія: Гальмівна система
INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Гальмівні колодки передні', 'Гальма', 'BRK-PAD-F001', 'Передні гальмівні колодки', 'Керамічні/напівметалічні', '30,000-50,000 км', 1, 'https://example.com/brake_pads_front.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Гальмівні колодки задні', 'Гальма', 'BRK-PAD-R001', 'Задні гальмівні колодки', 'Керамічні/напівметалічні', '40,000-60,000 км', 1, 'https://example.com/brake_pads_rear.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Гальмівні диски передні', 'Гальма', 'BRK-DISC-F001', 'Передні гальмівні диски', 'Вентильовані/суцільні', '60,000-80,000 км', 0, 'https://example.com/brake_discs_front.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Гальмівна рідина', 'Гальма', 'BRK-FLUID-001', 'Гідравлічна рідина DOT4', 'DOT4/DOT5.1', '24 місяці', 1, 'https://example.com/brake_fluid.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Гальмівні шланги', 'Гальма', 'BRK-HOSE-001', 'Гнучкі гальмівні шланги', 'Армовані гумові', '80,000-100,000 км', 0, 'https://example.com/brake_hoses.jpg'
FROM vehicles;

-- Категорія: Електрика та освітлення
INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Акумулятор', 'Електрика', 'ELC-BAT-001', 'Стартерна батарея', '12V 60-90Ah', '48-60 місяців', 0, 'https://example.com/battery.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Лампа H7', 'Електрика', 'ELC-H7-001', 'Галогенова лампа фар', 'H7 55W 12V', '12-24 місяці', 1, 'https://example.com/h7_bulb.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Лампа H4', 'Електрика', 'ELC-H4-001', 'Галогенова лампа фар', 'H4 60/55W 12V', '12-24 місяці', 1, 'https://example.com/h4_bulb.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Запобіжники набір', 'Електрика', 'ELC-FUSE-001', 'Набір автомобільних запобіжників', '5A-30A різні типи', 'За потребою', 1, 'https://example.com/fuses_set.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Генератор', 'Електрика', 'ELC-ALT-001', 'Генератор змінного струму', '80-150A', '100,000-150,000 км', 0, 'https://example.com/alternator.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Стартер', 'Електрика', 'ELC-START-001', 'Електростартер двигуна', '1.0-2.5 kW', '100,000-200,000 км', 0, 'https://example.com/starter.jpg'
FROM vehicles;

-- Категорія: Підвіска
INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Амортизатори передні', 'Підвіска', 'SUS-SHOCK-F001', 'Передні амортизатори', 'Газові/олійні', '60,000-80,000 км', 0, 'https://example.com/shock_absorbers_front.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Амортизатори задні', 'Підвіска', 'SUS-SHOCK-R001', 'Задні амортизатори', 'Газові/олійні', '60,000-80,000 км', 0, 'https://example.com/shock_absorbers_rear.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Пружини передні', 'Підвіска', 'SUS-SPR-F001', 'Передні спіральні пружини', 'Сталеві пружини', '100,000-150,000 км', 0, 'https://example.com/springs_front.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Сайлентблоки', 'Підвіска', 'SUS-BUSH-001', 'Гумометалічні втулки', 'Гума з металом', '50,000-80,000 км', 0, 'https://example.com/bushings.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Кульові опори', 'Підвіска', 'SUS-BALL-001', 'Кульові шарніри підвіски', 'Сталеві з підшипником', '60,000-100,000 км', 0, 'https://example.com/ball_joints.jpg'
FROM vehicles;

-- Категорія: Кондиціонер та клімат
INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Фільтр салону', 'Кондиціонер', 'AC-CABIN-001', 'Фільтр очищення повітря салону', 'Паперовий/вугільний', '15,000-20,000 км', 1, 'https://example.com/cabin_filter.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Фреон R134a', 'Кондиціонер', 'AC-FREON-001', 'Хладагент для кондиціонера', 'R134a екологічний', '24-36 місяців', 1, 'https://example.com/freon.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Компресор кондиціонера', 'Кондиціонер', 'AC-COMP-001', 'Компресор системи A/C', 'Електричний/механічний', '80,000-120,000 км', 0, 'https://example.com/ac_compressor.jpg'
FROM vehicles;

-- Категорія: Кузов та екстер'єр
INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Дзеркало бокове ліве', 'Кузов', 'BOD-MIR-L001', 'Ліве зовнішнє дзеркало', 'З підігрівом/без', 'За потребою', 0, 'https://example.com/side_mirror_left.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Дзеркало бокове праве', 'Кузов', 'BOD-MIR-R001', 'Праве зовнішнє дзеркало', 'З підігрівом/без', 'За потребою', 0, 'https://example.com/side_mirror_right.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Лобове скло', 'Кузов', 'BOD-WIND-001', 'Переднє вітрове скло', 'Загартоване скло', 'За потребою', 0, 'https://example.com/windshield.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Бампер передній', 'Кузов', 'BOD-BUMP-F001', 'Передній бампер', 'Пластиковий', 'За потребою', 0, 'https://example.com/front_bumper.jpg'
FROM vehicles;

-- Категорія: Трансмісія
INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Олива КПП', 'Трансмісія', 'TRN-OIL-001', 'Трансмісійна олива', 'ATF/MTF різні типи', '40,000-80,000 км', 1, 'https://example.com/transmission_oil.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Зчеплення комплект', 'Трансмісія', 'TRN-CLUTCH-001', 'Комплект зчеплення', 'Диск+кошик+підшипник', '80,000-120,000 км', 0, 'https://example.com/clutch_kit.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'ШРУС зовнішній', 'Трансмісія', 'TRN-CV-OUT001', 'Зовнішній шарнір рівних кутових швидкостей', 'З пильником', '100,000-150,000 км', 0, 'https://example.com/cv_joint_outer.jpg'
FROM vehicles;

-- Категорія: Витратні матеріали
INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Омивач скла', 'Витратні', 'CON-WASH-001', 'Рідина для омивання скла', 'Літня/зимова -20°C', 'За потребою', 1, 'https://example.com/windshield_washer.jpg'
FROM vehicles;

INSERT INTO parts (vehicle_id, name, category, part_number, description, specifications, replacement_interval, is_consumable, image_url)
SELECT id, 'Щітки склоочисника', 'Витратні', 'CON-WIPER-001', 'Гумові щітки дворників', 'Різні розміри 450-650мм', '12-24 місяці', 1, 'https://example.com/wiper_blades.jpg'
FROM vehicles;

-- Оновлення лічильника запчастин для кожного автомобіля
UPDATE vehicles SET parts_count = (
    SELECT COUNT(*) FROM parts WHERE parts.vehicle_id = vehicles.id
);

-- Створення view для зручного перегляду
CREATE VIEW IF NOT EXISTS vehicle_parts_summary AS
SELECT
    v.id as vehicle_id,
    v.make,
    v.model,
    v.generation,
    v.year_start || '-' || v.year_end as year_range,
    COUNT(p.id) as total_parts,
    COUNT(CASE WHEN p.is_consumable = 1 THEN 1 END) as consumable_parts,
    COUNT(CASE WHEN p.is_consumable = 0 THEN 1 END) as non_consumable_parts
FROM vehicles v
LEFT JOIN parts p ON v.id = p.vehicle_id
GROUP BY v.id;

-- Створення view для категорій запчастин
CREATE VIEW IF NOT EXISTS parts_by_category AS
SELECT
    v.make,
    v.model,
    p.category,
    COUNT(p.id) as parts_count
FROM vehicles v
JOIN parts p ON v.id = p.vehicle_id
GROUP BY v.id, p.category
ORDER BY v.make, v.model, p.category;

-- Перевірочні запити для валідації даних
-- SELECT COUNT(*) as total_vehicles FROM vehicles;
-- SELECT COUNT(*) as total_parts FROM parts;
-- SELECT category, COUNT(*) as parts_count FROM parts GROUP BY category;
-- SELECT * FROM vehicle_parts_summary LIMIT 10;