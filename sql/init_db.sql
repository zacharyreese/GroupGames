DROP DATABASE IF EXISTS groupgames;
CREATE DATABASE groupgames;

USE groupgames;

# Create a user account for the server instance
DROP USER IF EXISTS 'gg_server'@'%';
FLUSH PRIVILEGES;
CREATE USER 'gg_server'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON GroupGames.* TO 'gg_server'@'%';

## Table creation should be inserted below.

DROP TABLE IF EXISTS `GameStateData`;
CREATE TABLE `GameStateData` (
  `GameId` INT(20) NOT NULL,
  `LastCall` TIME NOT NULL,
  PRIMARY KEY (`GameId`)
);

DROP TABLE IF EXISTS `Player`;
CREATE TABLE `Player` (
  `PlayerId` INT(20) NOT NULL,
  `PName` varchar(50) NOT NULL,
  `GameId` INT(20) NOT NULL,
  PRIMARY KEY (`PlayerId`)
);

DROP TABLE IF EXISTS `KaH_PlayerHand`;
CREATE TABLE `KaH_PlayerHand` (
  `CardId` INT(20) NOT NULL,
  `PlayerId` INT(20) NOT NULL,
  `GameId` INT(20) NOT NULL,
  PRIMARY KEY (`CardId`)
);

DROP TABLE IF EXISTS `KaH_BlackCard`;
CREATE TABLE `KaH_BlackCard` (
  `CardId` INT(20) NOT NULL AUTO_INCREMENT,
  `CardText` varchar(256) NOT NULL,
  PRIMARY KEY (`CardId`)
);

DROP TABLE IF EXISTS `KaH_WhiteCard`;
CREATE TABLE `KaH_WhiteCard` (
  `CardId` INT(20) NOT NULL AUTO_INCREMENT,
  `CardText` varchar(256) NOT NULL,
  PRIMARY KEY (`CardId`)
);

ALTER TABLE `Player` ADD CONSTRAINT `Player_fk0` FOREIGN KEY (`GameId`) REFERENCES `GameStateData`(`GameId`);

ALTER TABLE `KaH_PlayerHand` ADD CONSTRAINT `KaH_PlayerHand_fk0` FOREIGN KEY (`CardId`) REFERENCES `KaH_WhiteCard`(`CardId`);

ALTER TABLE `KaH_PlayerHand` ADD CONSTRAINT `KaH_PlayerHand_fk1` FOREIGN KEY (`PlayerId`) REFERENCES `Player`(`PlayerId`);

ALTER TABLE `KaH_PlayerHand` ADD CONSTRAINT `KaH_PlayerHand_fk2` FOREIGN KEY (`GameId`) REFERENCES `GameStateData`(`GameId`);



## Initial data insertion should be inserted below

INSERT INTO KaH_BlackCard (CardText)
VALUES ('_______________ of Unusual Size? I don''t believe in them.'),
       ('"_______________ victims should make the best of a bad situation." - Rick Santorum'),
       ('A loaf of bread, a glass of wine, and _______________.'),
       ('Each freckle is a soul that a ginger has _______________.'),
       ('I got a job once cleaning _______________.'),
       ('I was thinking of the immortal words of Socrates who said, "I drank _______________???"'),
       ('I''d like to force the members of the Westboro Baptist Church to _______________ each other. Repeatedly.'),
       ('I''d make a card with Star Trek references, but the _______________ at Paramount are lawsuit-happy.'),
       ('If I hear _______________ one more time, I''m going to punch a baby.'),
       ('"If it''s a legitimate _______________, the female body has ways of shutting that whole thing down." - Todd Akin'),
       ('Is this a dagger I see before me? No, it''s _______________.'),
       ('It''s 106 miles to Chicago, we got a full tank of gas, _______________, it''s darkâ€¦ and we''re wearing sunglasses.'),
       ('My dissertation wasn''t complete until I figured out the connection between Milton''s "Paradise Lost" and _______________.'),
       ('My Love is like a _______________.'),
       ('The latest miracle is a statue whose eyes bleed when in the presence of _______________.'),
       ('Why can''t we have nice things?');

INSERT INTO KaH_WhiteCard (CardText)
VALUES ('Nipple hair.'),
       ('"Working" from home.'),
       ('15 wild badgers living in my trousers.'),
       ('A Coffee Break For Sisyphus.'),
       ('A solution in search of a problem.'),
       ('A towel.'),
       ('A White Panel Van with a Sign That Says "Free Candy."'),
       ('Action Nerd.'),
       ('All natural.'),
       ('Bad Girl Shenanigans.'),
       ('Bath Salts.'),
       ('Big Data.'),
       ('Boat Nectar.'),
       ('Bow Ties.'),
       ('Cake Is The Only Thing That Matters.'),
       ('Camp Cosmos.'),
       ('Can''t Stop The Signal.'),
       ('Captain Jack Harkness.'),
       ('Clinical Depression.'),
       ('Cthulhu.'),
       ('Do Not Bounce.'),
       ('Do you want a Nutter Butter? They''re Delicious!'),
       ('Don''t Blink.'),
       ('Ending Women''s Suffrage.'),
       ('For the love of coffee!'),
       ('God Less America'),
       ('God of Cake.'),
       ('Groped By An Angel.'),
       ('I would lick that for hours.'),
       ('Invisible Pink Unicorn.'),
       ('It''s Frothy!'),
       ('Jalapeños as a basic food group.'),
       ('Just Glue Some Gears On It'),
       ('Just turned 18.'),
       ('Kenny Loggins can read minds.'),
       ('Laundry day.'),
       ('Mezcal of the Gods'),
       ('Mid-Air Birth.'),
       ('No results found.'),
       ('Not feeling... fresh?'),
       ('Organic.'),
       ('Out of your league.'),
       ('Oxygen Thieves.'),
       ('Plot Twist!'),
       ('Poser in a parka.'),
       ('Psychiatrists Without Borders.'),
       ('Raccoons With Lightsabers.'),
       ('Red Shirts.'),
       ('Rule 34.'),
       ('Sapiosexual.'),
       ('Screaming and Then Silence.'),
       ('Setting an elderly relative adrift on an ice flow.'),
       ('Shiny!'),
       ('Some douchebag yelling "More Cowbell."'),
       ('Some of God''s Tears.'),
       ('Sparkadelica.'),
       ('Sparkle Pony.'),
       ('Spaying and neutering irresponsible pet owners.'),
       ('Stage troll'),
       ('Steampunk.'),
       ('Stem cells aren''t people. Soylent Green is people.'),
       ('That kills people!'),
       ('The Alot.'),
       ('The Helvetica Scenario.'),
       ('The Many-Angled Ones.'),
       ('Thin Mints.'),
       ('Unitarian Jihad.'),
       ('Vegemite.'),
       ('Veni, Vidi, Venti. (I came, I saw, I had a large coffee).'),
       ('You Can Do It!'),
       ('You wish.');