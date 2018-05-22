import pymysql
import traceback
import time

def getresults():
    db = pymysql.connect("localhost", "root", "root", "rsmovie")
    cursor = db.cursor()
    sql = "SELECT * FROM rsmovie.movies"

    # 执行SQL语句
    cursor.execute(sql)
    # 获取所有记录列表
    results = cursor.fetchall()
    # for row in results:
    #     movieId = row[0]
    #     title = row[1]
    #     published_year = row[2]
    #     genres = row[3]
    #     # 打印结果
    #     print("movieId=%s,title=%s,published_year=%s,genres=%s" % (movieId, title, published_year, genres))

    # 关闭数据库连接
    db.close()
    return results


def showresults(results):
    for row in results:
        movieId = row[0]
        title = row[1]
        published_year = row[2]
        genres = row[3]
        # 打印结果
        print("movieId=%s,title=%s,published_year=%s,genres=%s" % (movieId, title, published_year, genres))


def insert(id_indb, details):
    db = pymysql.connect("localhost", "root", "root", "rsmovie", charset='utf8')
    cursor = db.cursor()
    for i, x in enumerate(details):
        details[i] = pymysql.escape_string(x)
    sql = "INSERT INTO movies_details VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')" % ((id_indb,) + tuple(details))
    print(sql)
    # time.sleep(1000000)
    try:
        cursor.execute(sql)
        db.commit()
        print('insert one row into movies_details')
    except Exception as e:
        traceback.print_exc()
        db.rollback()
    db.close()


if __name__ == '__main__':
    showresults(getresults())
