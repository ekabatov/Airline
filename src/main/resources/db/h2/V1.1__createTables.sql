CREATE TABLE AIRLINE (
  AIRLINE_ID INTEGER auto_increment,
  NAME       VARCHAR(250),
  PRIMARY KEY (AIRLINE_ID)
);

CREATE TABLE PLANE (
  PLANE_ID INTEGER auto_increment,
  NAME     VARCHAR(100),
  CAPACITY INTEGER,
  PRIMARY KEY (PLANE_ID)
);

CREATE TABLE AIRPORT (
  AIRPORT_ID INTEGER auto_increment,
  NAME       VARCHAR(250),
  PRIMARY KEY (AIRPORT_ID)
);

CREATE TABLE FLIGHT (
  FLIGHT_ID            INTEGER auto_increment,
  NUMBER               VARCHAR(50),
  FOREIGN KEY(PLANE_ID) REFERENCES PLANE (PLANE_ID) ,
  FOREIGN KEY(DEPARTURE_AIRPORT_ID) REFERENCES AIRPORT (AIRPORT_ID),
  FOREIGN KEY(ARRIVAL_AIRPORT_ID) REFERENCES AIRPORT (AIRPORT_ID),
  FOREIGN KEY(AIRLINE_ID) REFERENCES AIRLINE (AIRLINE_ID),
  DATE                 DATE,
  TIME                 TIME,
  PRIMARY KEY (FLIGHT_ID)
);

CREATE TABLE FLIGHTPRICE (
  FLIGHTPRICE_ID INTEGER auto_increment,
  FOREIGN KEY()FLIGHT_ID REFERENCES FLIGHT (FLIGHT_ID),
  LEVEL          VARCHAR(20),
  PRICE          INTEGER,
  PRIMARY KEY (FLIGHTPRICE_ID)
);

CREATE TABLE ROLE (
  ROLE_ID INTEGER auto_increment,
  NAME    VARCHAR(50),
  PRIMARY KEY (ROLE_ID)
);

CREATE TABLE USERAIRLINE (
  USER_ID        INTEGER auto_increment,
  LOGIN          VARCHAR(50),
  PASSWORD       VARCHAR(100),
  EMAIL          VARCHAR(50),
  PHONE          VARCHAR(30),
  FOREIGN KEY(ROLE_ID) REFERENCES ROLE (ROLE_ID),
  LASTNAME       VARCHAR(50),
  FIRSTNAME      VARCHAR(50),
  PATRONYM       VARCHAR(50),
  PASSPORTNUMBER VARCHAR(50),
  PRIMARY KEY (USER_ID)
);

CREATE TABLE TICKET (
  NUMBER         INTEGER auto_increment,
  FOREIGN KEY(FLIGHT_ID) REFERENCES FLIGHT (FLIGHT_ID),
  FOREIGN KEY (FLIGHTPRICE_ID)REFERENCES FLIGHTPRICE (FLIGHTPRICE_ID),
  PLACE          VARCHAR(20),
  FOREIGN KEY (USER_ID)REFERENCES userairline (USER_ID),
  PRIMARY KEY (NUMBER)
);