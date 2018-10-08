
DROP DATABASE IF EXISTS GroupGames;
CREATE DATABASE GroupGames;

USE GroupGames;

# Create a user account for the server instance
CREATE USER 'gg_server'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON GroupGames.* TO 'gg_server'@'localhost';

## Table creation should be inserted below.


## Initial data insertion should be inserted below

