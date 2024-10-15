
/* Drop Tables */

DROP TABLE IF EXISTS login_user;




/* Create Tables */

CREATE TABLE login_user
(
	user_id serial NOT NULL,
	user_name varchar(20),
	password varchar(100),
	birthday date,
	email varchar(50),
	PRIMARY KEY (user_id)
) WITHOUT OIDS;



