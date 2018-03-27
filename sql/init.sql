CREATE DATABASE rsmovie;
USE rsmovie;
CREATE TABLE movies ( -- 保存电影相关的信息
	movieId INTEGER NOT NULL AUTO_INCREMENT,
	title varchar(255) NOT NULL,
    published_year varchar(255) default 'no published year',
	genres varchar(255) default 'no genres listed',
	PRIMARY KEY (movieId)
);
CREATE TABLE ratings (  -- 保存用户对电影的评分，即喜好程度
    userId INTEGER NOT NULL,
    movieId INTEGER NOT NULL,
    rating FLOAT NOT NULL DEFAULT 0,
    timestamp INTEGER not null default 0,
    FOREIGN KEY (movieId) REFERENCES movies(movieId) ON DELETE CASCADE
);