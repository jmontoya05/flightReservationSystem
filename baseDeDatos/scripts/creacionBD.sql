drop database if exists dbSitemaVuelos;
create database dbSitemaVuelos;
use dbSitemaVuelos;

create table paises (
	idPais int unsigned primary key auto_increment,
    pais varchar(20) not null
);

create table ciudades (
	idCiudad int unsigned primary key auto_increment,
    ciudad varchar(30) not null,
    idPais int unsigned not null,
    foreign key (idPais) references paises(idPais)
);

create table aeropuertos (
	idAeropuerto int unsigned primary key auto_increment,
    nombreAeropuerto varchar(50) not null,
    idCiudad int unsigned not null,
    foreign key (idCiudad) references ciudades(idCiudad)
);

create table aerolineas (
	idAerolinea int unsigned primary key auto_increment,
    nombreAerolinea varchar(30) not null
);

create table tiposVuelo (
	idTipoVuelo int unsigned primary key auto_increment,
    tipoVuelo varchar(20) not null
);

create table vuelos (
	idVuelo int unsigned primary key auto_increment,
    codigoVuelo varchar(10) not null unique,
    idAerolinea int unsigned not null,
    idTipoVuelo int unsigned not null,
    idAeropuertoOrigen int unsigned not null,
    idAeropuertoDestino int unsigned not null,
    fechaDeSalida datetime not null,
    fechaDeLlegada datetime not null,
    precio double not null,
    asientosDisponibles int not null,
    foreign key (idAerolinea) references aerolineas(idAerolinea),
    foreign key (idTipoVuelo) references tiposVuelo(idTipoVuelo),
    foreign key (idAeropuertoOrigen) references aeropuertos(idAeropuerto),
    foreign key (idAeropuertoDestino) references aeropuertos(idAeropuerto)
);

create table pasajeros (
	idPasajero int unsigned primary key auto_increment,
    nombresPasajero varchar(50) not null,
    apellidosPasajero varchar(50) not null,
    pasaporte varchar(15) not null,
    nacionalidad varchar(20) not null,
    correo varchar(30),
    telefono varchar(15) not null,
    contactoEmergencia varchar(30) not null,
    telefonoContacto varchar(15) not null
);

create table reservas (
	idReserva int primary key auto_increment,
    idVuelo int unsigned not null,
    idPasajero int unsigned not null,
    fechaReserva datetime not null,
    foreign key (idVuelo) references vuelos(idVuelo),
    foreign key (idPasajero) references pasajeros(idPasajero)
);