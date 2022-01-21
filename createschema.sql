CREATE SCHEMA dat107oblig3;
SET search_path TO dat107oblig3;


--AVDELING
CREATE TABLE avdeling
(
    avdelingId SERIAL,
    navn VARCHAR (45) not null,
    sjef Integer not null,
    CONSTRAINT avdeling_pk Primary Key (avdelingId)
);

--ANSATT
CREATE TABLE ansatt
(
    ansattId SERIAL, 
    brukernavn VARCHAR (4) NOT null unique,
    fornavn VARCHAR (45) NOT null,
    etternavn VARCHAR NOT null,
    dato DATE NOT null,
    stilling VARCHAR NOT null,
    lonn integer NOT null,
    avdelingId Integer NOT null,
    CONSTRAINT ansatt_pk Primary Key (ansattId),
    FOREIGN KEY(avdelingId) references avdeling(avdelingId),
    CONSTRAINT ansattBr CHECK ((LENGTH(brukernavn) >= 3) AND (LENGTH(brukernavn) <= 4))
);

--PROSJEKT
CREATE TABLE prosjekt
(
    prosjektId SERIAL,
    navn VARCHAR (45) not null,
    beskrivelse VARCHAR (30),
    CONSTRAINT prosjekt_pk Primary Key (prosjektId)
);

INSERT INTO 
prosjekt(Navn,Beskrivelse)
Values 
('Prosjekt1','Prosjekt1 Beskrivelse'),
('Prosjekt2','Prosjekt2 Beskrivelse'),
('Prosjekt3','Prosjekt3 Beskrivelse');



INSERT INTO
  avdeling(navn, sjef)
        VALUES
        ('Avdeling1', 1),
        ('Avdeling2', 2),
        ('Avdeling3', 3);

        
--prosjektdeltakere
CREATE TABLE prosjektdeltakere
(
 prosjektDiD SERIAL,	
 ansattId integer not null,
 prosjektId Integer not null,
 rolle varChar(45),
 timer Integer,

  
 CONSTRAINT prosjekt_pk1 Primary Key(prosjektDiD),	 
 CONSTRAINT idUnique UNIQUE (ansattId, prosjektId),	
 FOREIGN key (prosjektId) REFERENCES prosjekt(prosjektId),
 FOREIGN KEY (ansattId) REFERENCES ansatt(ansattId)
	
);


INSERT INTO ansatt(brukernavn, fornavn, etternavn, dato, stilling, lonn, avdelingid)
VALUES
    ('JL0', 	'Joachim', 		'Leiros',	'01.01.2020','Assistent',		300000,1),
    ('JL1', 	'Joachim1', 	'Leiros1',	'01.02.2020','Assistent',		300000,2),
    ('JL2', 	'Joachim2', 	'Leiros2',	'01.03.2020','Assistent',		305000,3),
    ('JL3', 	'Joachim3', 	'Leiros3',	'01.04.2020','Resepsjon',		270000,1),
    ('JL4', 	'Joachim4', 	'Leiros4',	'01.05.2020','Resepsjon',		275000,2),
    ('JL5', 	'Joachim5', 	'Leiros5',	'01.01.2021','Resepsjon',		280000,3),
    ('JL6', 	'Joachim6', 	'Leiros6',	'03.02.2020','Utvikler',		350000,1),
    ('JL7', 	'Joachim7', 	'Leiros7',	'25.07.2020','Utvikler',		400000,2),
    ('JL8', 	'Joachim8', 	'Leiros8',	'18.06.2020','Utvikler',		325000,3),
    ('JL9', 	'Joachim9', 	'Leiros9',	'03.12.2019','Personal',		400000,1),
    ('JL10', 	'Joachim10', 	'Leiros10',	'20.10.1999','Personal',		350000,2),
	('JL11', 	'Joachim11', 	'Leiros11',	'10.10.2010','Personal',		330000,3);
	
INSERT INTO prosjektdeltakere(ansattId, prosjektId, rolle, timer)
VALUES
(1,1,	'Prosjektansvarlig',45),
(2,1,	'Teamansvarlig',	90),
(3,1,	'Kundeansvarlig',	95),

(4,2,	'Prosjektansvarlig',45),
(5,2,	'Teamansvarlig',	80),
(6,2,	'Kundeansvarlig',	83),

(7,3,	'Prosjektansvarlig',23),
(8,3,	'Teamansvarlig',	49),
(9,3,	'Kundeansvarlig',	40);


--Legger til sjef(sjef) på ansattID i avdeling
ALTER TABLE avdeling
ADD FOREIGN KEY(sjef) references ansatt(ansattId);