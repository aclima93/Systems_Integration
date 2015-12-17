#!/bin/bash

mysql.server stop
mysql.server start

# the password is the empty string	
mysql -u root -D mysql -p < create_database.sql
