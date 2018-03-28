# RS
A recommend system based on SpringBoot,Mahout

Run sql/init.sql to create mysql tables.

Run util/ImportMovies util/ImportRatings to import data into mysql tables from .csv

Download mahout 0.13 from http://mahout.apache.org/ and import into project

Solve some dependencies by importing google-collections-0.9.jar

Look through table structure, compatible data type and some MySql settings
suggested by mahout API 0.13.0
http://mahout.apache.org/docs/0.13.0/api/docs/mahout-integration/

Page path:
  org.apache.mahout.cf.taste.impl.model.jdbc
    Class MySQLJDBCDataModel
