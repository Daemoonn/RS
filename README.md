# RS
A recommend system based on SpringBoot,Mahout

Run files in sql/Dump20180509 to create mysql tables and import data into MySQL.

Run util/ImportMovies util/ImportRatings to import data into mysql tables from .csv

Download mahout 0.13 from http://mahout.apache.org/ and import into project

Solve some dependencies by importing jars https://drive.google.com/open?id=11L6D4xXBCvUIeLsbqjClTms0mF2wIraT
Or BaiduNetdisk link：https://pan.baidu.com/s/1RuYB5hOkw03R5lALmrag2w password：kzdi

Look through table structure, compatible data type and some MySql settings
suggested by mahout API 0.13.0
http://mahout.apache.org/docs/0.13.0/api/docs/mahout-integration/

Page path:
  org.apache.mahout.cf.taste.impl.model.jdbc
    Class MySQLJDBCDataModel

![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/home_page1.png)

![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/home_page2.png)

![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/detail_page1.png)

![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/detail_page2.png)
