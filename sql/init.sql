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