# Game schema

# --- !Ups

CREATE TABLE game_state (
    line text
);
CREATE TABLE game_source (
    line text
);
INSERT INTO game_source(line) VALUES('Ambohimanga is a hill and traditional fortified royal settlement (rova) in Madagascar, located approximately 24 kilometres (15 mi) northeast of the capital city of Antananarivo. The hill and the rova that stands on top are considered the most significant symbol of the cultural identity of the Merina people and the most important and best-preserved monument of the precolonial Kingdom of Madagascar and its precursor, the Kingdom of Imerina. The walled historic village includes residences and burial sites of several key monarchs. The site, one of the twelve sacred hills of Imerina, is associated with strong feelings of national identity and has maintained its spiritual and sacred character both in ritual practice and the popular imagination for at least four hundred years. It remains a place of worship to which pilgrims come from Madagascar and elsewhere.');
CREATE TABLE player_state (
    line text
);

# --- !Downs

DROP TABLE game_state;
DROP TABLE game_source;
DROP TABLE player_state;