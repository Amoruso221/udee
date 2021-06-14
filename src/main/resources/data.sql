INSERT INTO clients(name, surname, dni, address, user) values("Back", "Office", 123456, 1, 1), ("Client", "Client", 654321, 2, 2);

INSERT INTO users(username, password, rol, client) values ('employee1', '1234', 'ROLE_BACKOFFICE', null), ('client1','1234', 'ROLE_CLIENT',1);

INSERT INTO rates(description, amount, address) values ("rate1", 1.0, 1);

INSERT INTO addresses(address, city, state, country, client, rate) values("client", "mar del plata", "buenos aires", "argentina", 1, 1);

INSERT INTO meters(brand, model, address) values("brand", "model", 1);

INSERT INTO measurements(kwh, dateTime, meter, billed) value(1.0, now(), 1, false);