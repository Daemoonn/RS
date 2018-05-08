CREATE DATABASE rsmovie;
USE rsmovie;
/*
Look through table structure, compatible data type and some MySql settings
suggested by mahout API 0.13.0
http://mahout.apache.org/docs/0.13.0/api/docs/mahout-integration/

Page path:
  org.apache.mahout.cf.taste.impl.model.jdbc
    Class MySQLJDBCDataModel
*/
CREATE TABLE movies (
	movieId BIGINT NOT NULL AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
    published_year VARCHAR(255) DEFAULT 'no published year',
	genres VARCHAR(255) DEFAULT 'no genres listed',
	PRIMARY KEY (movieId)
);
CREATE TABLE taste_preferences (
   user_id BIGINT NOT NULL,
   item_id BIGINT NOT NULL,
   preference FLOAT NOT NULL,
   -- before 2038
   timestamp INTEGER NOT NULL DEFAULT 0,
   PRIMARY KEY (user_id, item_id),
   FOREIGN KEY (item_id) REFERENCES movies(movieId) ON DELETE CASCADE,
   INDEX (user_id),
   INDEX (item_id)
);
-- maybe won't use the two below
CREATE TABLE taste_item_similarity (
  item_id_a BIGINT NOT NULL,
  item_id_b BIGINT NOT NULL,
  similarity DOUBLE NOT NULL,
  PRIMARY KEY (item_id_a, item_id_b)
);
CREATE TABLE recommendations (
	user_id BIGINT NOT NULL,
    item_ids VARCHAR(255) DEFAULT 'no recommendations',
    PRIMARY KEY (user_id)
);
-- save the movie details crawled from douban
CREATE TABLE movies_details (
	movieId BIGINT NOT NULL,
    url_id VARCHAR(255) DEFAULT NULL,
    en_title VARCHAR(255) DEFAULT NULL,
    cn_title VARCHAR(255) DEFAULT NULL,
    published_year VARCHAR(255) DEFAULT NULL,
    img_link VARCHAR(255) DEFAULT NULL,
    page_link VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (movieId)
);
-- after fill up data in the tables
-- backup movies_details into movies_details_back
CREATE TABLE movies_details_back (SELECT * FROM movies_details);
-- add summary column into movies_details
ALTER TABLE movies_details ADD summary VARCHAR(5000) DEFAULT NULL;

CREATE TABLE movies_details_back_3
SELECT movies.movieId, url_id, title as 'en_title', cn_title, genres, movies.published_year, img_link, page_link, summary
FROM movies LEFT JOIN movies_details
ON movies.movieId = movies_details.movieId;
-- change structure of movies_details
DROP TABLE movies_details;
CREATE TABLE movies_details SELECT * FROM movies_details_back_3;
-- create user table
CREATE TABLE `user` (
	loginId BIGINT NOT NULL,
    pwd VARCHAR(50) NOT NULL,
    -- before 2038
    createTime INTEGER NOT NULL DEFAULT 0,
    updateTime INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (loginId)
);
-- add PRIMARY INDEX to movies_details for speeding up pagination
ALTER TABLE movies_details ADD PRIMARY KEY(movieId);
-- add FULLTEXT KEY to movies_details for searching
ALTER TABLE movies_details ADD FULLTEXT INDEX cn_ft_index (cn_title) WITH PARSER ngram;
ALTER TABLE movies_details ADD FULLTEXT INDEX en_ft_index (en_title);