#!/bin/bash

mysql.server stop
mysql.server start

# the password is the empty string	
mysql -u root -D mysql -p

# do this afterwards
# mysql> source /Users/aclima/Documents/Repositories/Systems_Integration/Assignment3/assignment3/database_files/create_database.sql;
