CREATE DATABASE rsmovie;
USE rsmovie;
CREATE TABLE movies (
	movieId BIGINT NOT NULL AUTO_INCREMENT,
	title varchar(255) NOT NULL,
    published_year varchar(255) default 'no published year',
	genres varchar(255) default 'no genres listed',
	PRIMARY KEY (movieId)
);
CREATE TABLE taste_preferences (
   user_id BIGINT NOT NULL,
   item_id BIGINT NOT NULL,
   preference FLOAT NOT NULL,
   timestamp INTEGER not null default 0,
   PRIMARY KEY (user_id, item_id),
   FOREIGN KEY (item_id) REFERENCES movies(movieId) ON DELETE CASCADE,
   INDEX (user_id),
   INDEX (item_id)
);
