DROP DATABASE mailinglist;
CREATE DATABASE mailinglist;

/*
DROP USER generatedata@localhost;
FLUSH PRIVILEGES;
CREATE USER generatedata@localhost IDENTIFIED BY 'generatedata';
*/

GRANT ALL PRIVILEGES ON mailinglist.* TO generatedata@localhost IDENTIFIED BY 'generatedata';
FLUSH PRIVILEGES;

USE mailinglist;


/* Table for Subscriptions to the mailing list */
CREATE TABLE subscriptions (
	id mediumint(8) unsigned NOT NULL auto_increment,

	email VARCHAR(254) NOT NULL, /* as per RFC 5321 */
	name VARCHAR(50) NOT NULL,
	brand_filter VARCHAR(50) NOT NULL,
	min_price INT NOT NULL,
	max_price INT NOT NULL,
	is_validated TINYINT(1) unsigned NOT NULL DEFAULT 0,

	PRIMARY KEY (id)
) AUTO_INCREMENT=1;


/* Table for Smartphones */
CREATE TABLE smartphones (
	id mediumint(8) unsigned NOT NULL auto_increment,

	technical_data TEXT,
    url VARCHAR(256) NOT NULL,
	name VARCHAR(50) NOT NULL,
	brand VARCHAR(50) NOT NULL,
	currency VARCHAR(50) NOT NULL,
	summary_data TEXT NOT NULL,
	price DECIMAL NOT NULL,

	PRIMARY KEY (id)
) AUTO_INCREMENT=1;


/* Table for System Statistics */
CREATE TABLE statistics (
	num_updates_to_smartphones INT DEFAULT 0,
	num_total_smartphones INT DEFAULT 0,
	num_sent_emails INT DEFAULT 0,
	num_sent_tweets INT DEFAULT 0
);


/* automatic incrementing of smartphone update counter and total number of */
DELIMITER $$
CREATE TRIGGER before_smartphone_insert 
    BEFORE INSERT ON smartphones
    FOR EACH ROW
BEGIN
    UPDATE statistics
    SET num_updates_to_smartphones = num_updates_to_smartphones + 1; 
END $$
DELIMITER ;


/* Initialize Statistics Counters */
INSERT INTO statistics (num_updates_to_smartphones, num_total_smartphones, num_sent_emails, num_sent_tweets) VALUES (0,0,0,0);


/* For debugging purposes */
INSERT INTO smartphones (technical_data, url, name, brand, currency, summary_data, price) VALUES ('Some technical mumbo jumbo','www.my.url.com','Generic Smartphone','Generic Brand','€','My summary is bigger than yours!', 1337);










