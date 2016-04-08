DROP DATABASE IF EXISTS DrugLord;
CREATE DATABASE DrugLord;
USE DrugLord;

DROP TABLE IF EXISTS Random;

CREATE TABLE Random
(
	ID INTEGER NOT NULL AUTO_INCREMENT,
	FirstName VARCHAR(20) NOT NULL,
    LastName VARCHAR(20) NOT NULL,
    Location VARCHAR(20) NOT NULL,
    GangName VARCHAR(20) NOT NULL,
    PRIMARY KEY (ID)
);

INSERT INTO Random VALUES
(
	null,
    'Treasa', 
    'Cummins',
    'Amsterdam',
    'Ravagers'
);

INSERT INTO Random VALUES
(
	null,
    'Barry',
    'Donagh', 
    'London',
    'Skinheads'
);

INSERT INTO Random VALUES
(
	null,
    'Dolan',
    'Finn', 
    'New York',
    'Optomon'
);

INSERT INTO Random VALUES
(
	null,
    'Carey',
    'Ele', 
    'El Santero',
    'Guacamole'
);

INSERT INTO Random VALUES
(
	null,
    'Denis', 
    'Fox',
    'Chigago',
    'Pinstripes'
);

INSERT INTO Random VALUES
(
	null,
    'Larry', 
    'Wachowski',
    'Dublin',
    'THE RA'
);

INSERT INTO Random VALUES
(
	null,
    'Allah', 
    'Ackbar',
    'Syria',
    'ISIS'
);

INSERT INTO Random VALUES
(
	null,
    'Jill', 
    'MacCarthy',
    'AIT',
    'NONE'
);

DROP TABLE IF EXISTS Gang;

CREATE TABLE Gang
(
	GangName VARCHAR(20) NOT NULL,
    Location VARCHAR(20) NOT NULL,
    Agression INTEGER NOT NULL,
    Intelligence INTEGER NOT NULL,
    PRIMARY KEY (GangName)
);

INSERT INTO Gang VALUES
(
	'Ravagers',
    'Amsterdam',
    7,
    60
);

INSERT INTO Gang VALUES
(
    'Skinheads',
    'London',
    6,
    60
);

INSERT INTO Gang VALUES
(
    'Optomon',
    'New York',
    2,
    95
);

INSERT INTO Gang VALUES
(
    'Guacamole',
    'El Santero',
    4,
    75
);

INSERT INTO Gang VALUES
(
    'Pinstripes',
    'Chigago',
    5,
    80
);

INSERT INTO Gang VALUES
(
    'THE RA',
    'Dublin',
    7,
    50
);

INSERT INTO Gang VALUES
(
    'ISIS',
    'Syria',
    10,
    50
);

INSERT INTO Gang VALUES
(
    'NONE',
    'AIT',
    10,
    50
);

DROP TABLE IF EXISTS People;

CREATE TABLE People
(
	ID INTEGER NOT NULL AUTO_INCREMENT,
    FirstName VARCHAR(20) NOT NULL,
    LastName VARCHAR(20) NOT NULL,
    Loyalty INTEGER NOT NULL,
	Location VARCHAR(20) NOT NULL,
    Strength INT NOT NULL,
    Wage DOUBLE NOT NULL,
    GangName VARCHAR(20),
    PRIMARY KEY (ID),
    FOREIGN KEY (GangName) REFERENCES Gang (GangName)
);

DROP TABLE IF EXISTS Product;

CREATE TABLE Product
(
	Name VARCHAR(20) NOT NULL,
    Price DOUBLE NOT NULL,
    Amount DOUBLE NOT NULL,
    PRIMARY KEY(Name)
);

INSERT INTO Product values
(
	'PCP',
    50,
    0
);

INSERT INTO Product values
(
	'Weed',
    5,
	0
);

INSERT INTO Product values
(
	'Heroin',
    25,
    0
);

INSERT INTO Product values
(
	'LSD',
    100,
    0
);

INSERT INTO Product values
(
	'Eden',
    5000,
    0
);

DROP TABLE IF EXISTS Player;

CREATE TABLE Player
(
	Name VARCHAR(20) NOT NULL,
    Money DOUBLE,
    Amount INTEGER,
    ProductName VARCHAR(20),
    PRIMARY KEY (Name),
    FOREIGN KEY (ProductName) REFERENCES Product(Name)
);