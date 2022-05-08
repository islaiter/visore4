CREATE DATABASE DDI;

USE DDI;

CREATE OR REPLACE TABLE CUENTAS(
	NumeroCuenta TINYINT (1) NOT NULL,
	Titular VARCHAR (50) NOT NULL,
	FechaApertura DATE NOT NULL,
    Saldo DOUBLE (7,2) NOT NULL,
    Nacionalidad VARCHAR(50) NOT NULL,
	PRIMARY KEY (NumeroCuenta)
);


INSERT INTO CUENTAS(NumeroCuenta, Titular, FechaApertura, Saldo, Nacionalidad) VALUES(1,'Roberto','2020-07-15',1000.11,'Española');
INSERT INTO CUENTAS(NumeroCuenta, Titular, FechaApertura, Saldo, Nacionalidad) VALUES(2,'Margarita','2020-10-30',2000.22,'Española');
INSERT INTO CUENTAS(NumeroCuenta, Titular, FechaApertura, Saldo, Nacionalidad) VALUES (3, 'Jose Carlos','2020-04-12',50000.33,'Española');
INSERT INTO CUENTAS(NumeroCuenta, Titular, FechaApertura, Saldo, Nacionalidad) VALUES (4, 'Angel','2020-07-06',15000.44,'Portuguesa');
INSERT INTO CUENTAS(NumeroCuenta, Titular, FechaApertura, Saldo, Nacionalidad) VALUES (5, 'Andrea','2021-09-20', 500.55, 'Francesa');




