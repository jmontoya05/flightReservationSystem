DROP DATABASE IF EXISTS flight_reservation_system;
CREATE DATABASE flight_reservation_system;
USE flight_reservation_system;

CREATE TABLE countries (
	country_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    country VARCHAR(20) NOT NULL
);

CREATE TABLE cities (
	city_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    city VARCHAR(30) NOT NULL,
    country_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries(country_id)
);

CREATE TABLE airports (
	airport_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    airport_name VARCHAR(50) NOT NULL,
    city_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities(city_id)
);

CREATE TABLE airlines (
	airline_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    airline_name VARCHAR(30) NOT NULL
);

CREATE TABLE flight_types (
	flight_type_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    flight_type VARCHAR(20) NOT NULL
);

CREATE TABLE flights (
    flight_code VARCHAR(10) PRIMARY KEY,
    airline_id INT UNSIGNED NOT NULL,
    flight_type_id INT UNSIGNED NOT NULL,
    airport_origin_id INT UNSIGNED NOT NULL,
    airport_destination_id INT UNSIGNED NOT NULL,
    departure_date DATETIME NOT NULL,
    arrival_date DATETIME NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    available_seats int NOT NULL,
    FOREIGN KEY (airline_id) REFERENCES airlines(airline_id),
    FOREIGN KEY (flight_type_id) REFERENCES flight_types(flight_type_id),
    FOREIGN KEY (airport_origin_id) REFERENCES airports(airport_id),
    FOREIGN KEY (airport_destination_id) REFERENCES airports(airport_id)
);

CREATE TABLE passengers (
	passenger_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    passport VARCHAR(15) NOT NULL,
    nationality VARCHAR(20) NOT NULL,
    email VARCHAR(30),
    phone_number VARCHAR(15) NOT NULL,
    emergency_contact VARCHAR(30) NOT NULL,
    contact_phone_number VARCHAR(15) NOT NULL
);

CREATE TABLE reservations (
	reservation_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    flight_code VARCHAR(10) NOT NULL,
    passenger_id INT UNSIGNED NOT NULL,
    reservation_date DATETIME NOT NULL,
    FOREIGN KEY (flight_code) REFERENCES flights(flight_code),
    FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id)
);