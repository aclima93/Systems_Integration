DROP DATABASE smartphone_mailinglist;
CREATE DATABASE smartphone_mailinglist;

/*
DROP USER generatedata@localhost;
FLUSH PRIVILEGES;
CREATE USER generatedata@localhost IDENTIFIED BY 'generatedata';
*/

GRANT ALL PRIVILEGES ON smartphone_mailinglist.* TO generatedata@localhost IDENTIFIED BY 'generatedata';
FLUSH PRIVILEGES;

USE smartphone_mailinglist;

CREATE TABLE subscriptions (
	id mediumint(8) unsigned NOT NULL auto_increment,
	
	email VARCHAR(254) NOT NULL, /* as per RFC 5321 */
	name VARCHAR(50) NOT NULL,
	brand_filter VARCHAR(50) NOT NULL,
	min_price INT NOT NULL,
	max_price INT NOT NULL,
	is_validated TINYINT(1) unsigned INT NOT NULL DEFAULT 0,

	PRIMARY KEY (id)
) AUTO_INCREMENT=1;

CREATE TABLE smartphones (
	id mediumint(8) unsigned NOT NULL auto_increment,

	technical_data TEXT,
    url VARCHAR(256) NOT NULL,
	name VARCHAR(50) NOT NULL,
	brand VARCHAR(50) NOT NULL,
	currency VARCHAR(50) NOT NULL,
	technical_data TEXT NOT NULL,
	price DECIMAL NOT NULL,

	PRIMARY KEY (id)
) AUTO_INCREMENT=1;



/* clear console */
system clear;
