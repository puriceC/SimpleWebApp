INSERT INTO users (username, password)
	values ('user',  '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a');
INSERT INTO users (username, password)
	values ('admin',  '$2a$10$urHM.x/eJwBMncOEgC.SiuZKuboTFQtOVRArMbL6ZtWRFOCpKrvRW');

INSERT INTO authorities (username, authority)
	values ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority)
	values ('admin', 'ROLE_ADMIN');
