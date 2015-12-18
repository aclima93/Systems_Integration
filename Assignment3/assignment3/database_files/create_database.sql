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
	id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,

	email VARCHAR(254) NOT NULL UNIQUE, /* as per RFC 5321 */
	name VARCHAR(50) NOT NULL,
	brand_filter VARCHAR(50) NOT NULL,
	min_price INT NOT NULL,
	max_price INT NOT NULL,
	is_validated TINYINT(1) unsigned NOT NULL DEFAULT 0,

	PRIMARY KEY (id)
) AUTO_INCREMENT=1;


/* Table for Smartphones */
CREATE TABLE smartphones (
	id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,

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
	num_cur_smartphones INT DEFAULT 0,
	num_sent_emails INT DEFAULT 0,
	num_sent_tweets INT DEFAULT 0
);


/* Automatic incrementing of smartphone insertions counter*/
DELIMITER $$
CREATE PROCEDURE update_smartphone_statistics()
BEGIN
    UPDATE statistics
    SET num_updates_to_smartphones = num_updates_to_smartphones + 1,
    	num_cur_smartphones = (SELECT COUNT(*) FROM smartphones);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_smartphone_insert 
    AFTER INSERT ON smartphones
    FOR EACH ROW
BEGIN
    CALL update_smartphone_statistics();
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_smartphone_update 
    AFTER UPDATE ON smartphones
    FOR EACH ROW
BEGIN
    CALL update_smartphone_statistics();
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_smartphone_delete
    AFTER DELETE ON smartphones
    FOR EACH ROW
BEGIN
    CALL update_smartphone_statistics();
END$$
DELIMITER ;


/* Initialize Statistics Counters */
INSERT INTO statistics () VALUES ();


/* For debugging purposes */
/* before any action */
/*SELECT * FROM statistics;

/* after insert */
/*INSERT INTO smartphones (technical_data, url, name, brand, currency, summary_data, price) VALUES ('Some technical mumbo jumbo','www.my.url.com','Generic Smartphone','Generic Brand','€','My summary is bigger than yours!', 1337);
/*SELECT * FROM statistics;
/*SELECT * FROM smartphones;*/

/* after update */
/*UPDATE smartphones SET price = 666 WHERE id = 1;
/*SELECT * FROM statistics;
/*SELECT * FROM smartphones;*/

/* after delete */
/*DELETE FROM smartphones WHERE id = 1;
/*SELECT * FROM statistics;
/*SELECT * FROM smartphones;*/









