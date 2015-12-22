#!/bin/bash

mysql.server stop
mysql.server start

# the password is the empty string or 'generatedata', for reasons unbeknownst
mysql -u root -D mysql -p 
