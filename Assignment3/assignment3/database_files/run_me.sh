#!/bin/bash

mysql.server stop
mysql.server start
mysql -u root -D mysql -p

source create_and_populate.sql;
