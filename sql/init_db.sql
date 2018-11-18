
DROP DATABASE IF EXISTS groupgames;
CREATE DATABASE groupgames;

USE groupgames;

# Create a user account for the server instance
DROP USER 'gg_server'@'localhost';
FLUSH PRIVILEGES;
CREATE USER 'gg_server'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON GroupGames.* TO 'gg_server'@'localhost';

## Table creation should be inserted below.


## Initial data insertion should be inserted below

