#!/bin/bash

mysql.server stop
mysql.server start
mysql -u root -D mysql -p

source /Users/aclima/Dropbox/universidade/MEI - Sistemas Inteligentes/2_ano/1_semestre/IS/mule_tests/basic_database/database_files/create_and_populate.sql;
