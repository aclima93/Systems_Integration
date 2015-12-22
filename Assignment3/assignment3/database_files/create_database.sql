DROP DATABASE mailinglist;
CREATE DATABASE mailinglist;

/*
DROP USER generatedata@localhost;
FLUSH PRIVILEGES;
CREATE USER generatedata@localhost IDENTIFIED BY 'generatedata';
GRANT ALL PRIVILEGES ON mailinglist.* TO generatedata@localhost IDENTIFIED BY 'generatedata';
FLUSH PRIVILEGES;
*/

USE mailinglist;


/* Table for Subscriptions to the mailing list */
CREATE TABLE subscriptions (
	id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,

	channel VARCHAR(254) NOT NULL,
	email VARCHAR(254) NOT NULL UNIQUE, /* as per RFC 5321 */
	name VARCHAR(50) NOT NULL,
	brand VARCHAR(50) NOT NULL,
	min_price INT NOT NULL,
	max_price INT NOT NULL,
	is_validated TINYINT(1) unsigned NOT NULL DEFAULT 0,

	PRIMARY KEY (id)
) AUTO_INCREMENT=1;


/* Table for Smartphones */
CREATE TABLE smartphones (
	id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,

	technical_data TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ,
    url VARCHAR(256) NOT NULL,
	name VARCHAR(50) NOT NULL,
	brand VARCHAR(50) NOT NULL,
	currency VARCHAR(50) NOT NULL,
	summary_data TEXT NOT NULL,
	price DECIMAL NOT NULL,

	ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	PRIMARY KEY (id)
) AUTO_INCREMENT=1;


/* Table for System Statistics */
CREATE TABLE statistics (
	num_updates_to_smartphones INT DEFAULT 0,
	num_cur_smartphones INT DEFAULT 0,
	num_sent_emails INT DEFAULT 0,
	num_sent_tweets INT DEFAULT 0
);


/* Initialize Statistics Counters */
INSERT INTO statistics () VALUES ();
