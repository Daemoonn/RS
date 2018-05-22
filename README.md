# RS
A recommend system based on SpringBoot,Mahout

Files in src/main/python/spiderman are web crawlers, to get summary and pictures for the
movies in movieLens.It may cost a few hours for ten thousand movies.It uses a proxy pool
from https://github.com/jhao104/proxy_pool.All text data can be found in sql/Dump20180509
and pictures can be found in my cloud disk link: https://pan.baidu.com/s/1rcCQtQ7IgDoSyZN4djAP0w
password: h09q.

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

home_page1
![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/home_page1.png)

home_page2
![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/home_page2.png)

detail_page1
![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/detail_page1.png)

detail_page2
![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/detail_page2.png)

search_page1
![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/search_page1.png)

search_page2
![Alt text](https://github.com/Daemoonn/RS/raw/master/screen_shots/search_page2.png)
