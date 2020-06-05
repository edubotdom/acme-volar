-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');

INSERT INTO users(username,password,enabled) VALUES ('client1','client1',TRUE);
INSERT INTO authorities VALUES ('client1','client');
INSERT INTO clients(id,name, identification, birth_date, phone, email, creation_date, username) VALUES (1,'Sergio Pérez','53933261-P', '1994-09-07', '644584458', 'checoperez@gmail.com', '2005-09-07','client1');

INSERT INTO users(username,password,enabled) VALUES ('client2','client2',TRUE);
INSERT INTO authorities VALUES ('client2','client');
INSERT INTO clients(id,name, identification, birth_date, phone, email, creation_date, username) VALUES (2,'Gastón Pereira','53949661-A', '1984-09-07', '644584458', 'gastonpere@gmail.com', '2012-09-07','client2');



INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO users(username,password,enabled) VALUES ('airline1','airline1',TRUE);
INSERT INTO authorities VALUES ('airline1','airline');
INSERT INTO users(username,password,enabled) VALUES ('airline2','airline2',TRUE);
INSERT INTO authorities VALUES ('airline2','airline');
INSERT INTO airlines(id,name, identification, country, phone, email, creation_date, reference, username) 
VALUES (1,'Sevilla Este Airways','61333744-N', 'Spain', '644584458', 'minardi@gmail.com', '2010-11-07', 'SEA-001','airline1');
INSERT INTO airlines(id,name, identification, country, phone, email, creation_date, reference, username) 
VALUES (2,'Montellano Airways','61333778-N', 'Spain', '654789321', 'arrows@gmail.com', '2010-11-07', 'MA-002','airline2');

INSERT INTO flight_status_type VALUES (1, 'on_time');
INSERT INTO flight_status_type VALUES (2, 'delayed');
INSERT INTO flight_status_type VALUES (3, 'cancelled');

INSERT INTO runway_type VALUES (1, 'take_off');
INSERT INTO runway_type VALUES (2, 'landing');

INSERT INTO book_status_type VALUES (1, 'approved');
INSERT INTO book_status_type VALUES (2, 'cancelled');

INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (1, 'Sevilla Airport', 50, 600, 37.4180000, -5.8931100, 'SVQ', 'Sevilla');
INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (2, 'Adolfo Suárez Madrid-Barajas Airport', 50, 600, 37.4180000, -5.8931100, 'MDR', 'Madrid');
INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (3, 'El Prat Airport', 50, 600, 37.4180000, -5.8931100, 'BCN', 'Barcelona');
INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (4, 'Charles de Gaulle Airport', 50, 600, 37.4180000, -5.8931100, 'CDG', 'París');
INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (5, 'Aeropuerto Federico García Lorca Granada-Jaén', 20, 1000, 37.1119, -3.4638, 'GRX', 'Granada');
INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (6, 'Aeropuerto de Almería', 30, 500, 37.1119, -3.4638, 'LEI', 'Almería');
INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (7, 'Aeropuerto de Alicante-Elche', 40, 1500, 37.1119, -3.4638, 'ALC', 'Alicante');
/*INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (11, 'Josep Tarradellas Barcelona-El Prat', 59, 1600, 37.1119, -3.4638, 'BCN', 'Barcelona');*/
INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (9, 'Aeropuerto de Málaga-Costa del Sol', 35, 600, 37.1119, -3.4638, 'AGP', 'Málaga');
INSERT INTO airports(id, name, max_number_of_planes, max_number_of_clients, latitude, longitude, code, city ) VALUES (10, 'Aeropuerto de Huesca-Pirineos', 25, 200, 42.0451, 0.1924, 'HSK', 'Huesca');



INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (1, 'V14-5', 150, 'This is a description', 'Boeing', 'B747', 500000.23, 2000000.0, '2011-04-17',1);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (2, 'RB7', 581, 'This is a description', 'Boeing', '747-8', 500000.23, 2000000.0, '2019-04-17',1);	
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (3, 'RB9', 100, 'This is a description', 'Antónov', 'An-124', 500000.23, 2000000.0, '2020-02-17',1);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (4, 'W05', 40, 'This is a description', 'Mercedes', 'W05 Hybrid', 50000.23, 600000.0, '2014-01-17',1);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (5, 'FW18', 40, 'This is a description', 'Williams', 'Frank Williams 18', 50000.23, 600000.0, '1996-01-17',1);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (6, 'MP4-4', 60, 'This is a description', 'McLaren', 'McLaren-Honda MP4-4', 50000.23, 600000.0, '1986-01-17',1);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (7, 'F2004', 60, 'This is a description', 'Ferrari', 'Ferrari F2004', 50000.23, 600000.0, '2004-01-17',1);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (8, 'W07', 60, 'This is a description', 'Mercedes', 'Mercedes W07', 50000.23, 600000.0, '2016-01-17',1);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (9, 'K4M', 8, 'This is a description', 'Renault', 'Laguna Grand Tour 2', 50000.23, 600000.0, '2002-01-17',2);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (10, 'FW14B', 60, 'This is a description', 'Williams', 'Williams FW14B', 50000.23, 600000.0, '1992-01-17',2);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (11, 'F2002', 60, 'This is a description', 'Ferrari', 'Ferrari F2002', 50000.23, 600000.0, '2002-01-17',2);
INSERT INTO aeroplanes(id, reference, max_seats, description, manufacter, model, number_of_km, max_distance, last_maintenance,airline_id) 
	VALUES (12, 'RGS', 6, 'This is a description', 'Renault', 'Grand Scénic', 50000.23, 600000.0, '2016-01-17',2);

	

INSERT INTO runway(id,name,runway_type_id, airport_id) VALUES (1,'A-01',1 ,1);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (2,'A-02',1 ,2);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (3,'A-03',2 ,2);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (4,'A-04',2 ,2);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (5,'A-05',1 ,2);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (6,'A-06',2 ,1);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (7,'A-07',1 ,2);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (8,'A-08',2 ,1);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (9,'A-09',1 ,1);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (10,'A-10',2 ,3);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (11,'A-11',1 ,3);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (12,'A-12',2 ,3);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (13,'A-13',1 ,3);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (14,'A-14',2 ,4);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (15,'A-15',1 ,4);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (16,'A-16',2 ,4);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (17,'A-17',1 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (18,'A-18',2 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (19,'A-19',1 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (20,'A-20',2 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (21,'A-21',1 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (22,'A-22',2 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (23,'A-23',1 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (24,'A-24',2 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (25,'A-25',1 ,6);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (26,'A-26',2 ,6);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (27,'A-27',1 ,6);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (28,'A-28',2 ,6);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (29,'A-29',1 ,6);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (30,'A-30',2 ,6);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (31,'A-31',1 ,7);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (32,'A-32',2 ,7);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (33,'A-33',1 ,7);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (35,'A-35',2 ,7);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (36,'A-36',1 ,7);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (37,'A-37',2 ,7);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (38,'A-38',1 ,1);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (39,'A-39',2 ,2);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (40,'A-40',1 ,3);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (41,'A-41',2 ,4);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (42,'A-42',1 ,5);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (43,'A-43',2 ,9);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (44,'A-44',1 ,9);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (45,'A-45',2 ,9);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (46,'A-46',2 ,9);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (47,'A-47',1 ,9);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (48,'A-48',2 ,9);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (49,'A-49',1 ,10);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (50,'A-50',2 ,10);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (51,'A-51',2 ,10);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (52,'A-52',2 ,10);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (53,'A-53',1 ,10);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (54,'A-54',2 ,10);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (55,'A-55',1 ,10);
INSERT INTO runway(id,name,runway_type_id,airport_id) VALUES (56,'A-56',2 ,10);


INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (1, 'R-01', 250, 150.0, 1, 1, TRUE, 1, 2, 1,'2020-06-06 15:00','2020-06-06 14:05');
	
INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (2, 'R-02', 215, 100.5, 1, 1, FALSE, 9, 16, 2,'2020-06-07 16:30','2020-06-07 15:05');

INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (3, 'R-03', 260, 550.0, 1, 1, TRUE, 15, 54, 1,'2022-10-25 15:00','2022-10-25 14:05');
	
INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (4, 'R-04', 410, 340.5, 2, 2, FALSE, 23, 50, 1,'2021-06-07 16:30','2021-06-07 15:05');

INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (8, 'R-08', 214, 360.0, 1, 1, TRUE, 25, 46, 2,'2022-06-06 15:00','2022-06-06 14:05');
	
INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (5, 'R-05', 215, 240.5, 1, 1, FALSE, 27, 40, 2,'2023-06-07 16:30','2023-06-07 15:05');

INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (6, 'R-06', 260, 145.0, 1, 1, TRUE, 31, 36, 1,'2024-10-25 15:00','2024-10-25 14:05');
	
INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (7, 'R-07', 236, 198.5, 2, 2, TRUE, 37, 30, 1,'2025-06-07 16:30','2025-06-07 15:05');
	
INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (9, 'R-09', 521, 310.5, 1, 1, FALSE,41, 24, 1,'2023-06-07 16:30','2023-06-07 15:05');

INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (10, 'R-10', 147, 58.0, 3, 1, TRUE,47, 20, 2,'2024-10-25 15:00','2024-10-25 14:05');
	
INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (11, 'R-11', 256, 354.5, 2, 2, TRUE, 51, 14, 1,'2025-06-07 16:30','2025-06-07 15:05');
	
INSERT INTO flights(id, reference, seats, price, flight_status_id, plane_id, published, departes_id, lands_id, airline_id, land_date, depart_date) 
	VALUES (12, 'R-00', 256, 354.5, 2, 1, TRUE, 51, 14, 1,'1999-06-08 16:30','1999-06-07 15:05');
	
	
INSERT INTO books(id, quantity, price, moment, book_status_type_id, client_id, flight_id) VALUES (1, 2, 300.0, '2020-05-05', 1, 1, 1);
INSERT INTO books(id, quantity, price, moment, book_status_type_id, client_id, flight_id) VALUES (2, 5, 500.0, '2020-06-05', 1, 2, 2);