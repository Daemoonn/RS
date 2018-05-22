import proxy_pool
import pymysql
import traceback
import requests


def fetch_summary(url_id):
    c = 0
    while True:
        movie_result = proxy_pool.get_summaryJson(url_id)
        if movie_result and movie_result.get('code') != 112:
            break
        c = c + 1
        print("Change proxy and try again: %d" % c)

    summary = movie_result.get('summary')
    if summary:
        print('summary getted')
        return summary
    else:
        print('no summary')
        return None


# 待重构insert
def show_save_summary(url_id, summary):
    if summary:
        conn = pymysql.connect("localhost", "root", "root", "rsmovie", charset='utf8')
        cursor = conn.cursor()
        sql = "UPDATE movies_details SET summary = '%s' WHERE url_id = '%s'" % (pymysql.escape_string(summary), pymysql.escape_string(url_id))
        print(sql)
        try:
            cursor.execute(sql)
            conn.commit()
            print('insert one summary into movies_details')
        except Exception as e:
            traceback.print_exc()
            conn.rollback()
        conn.close()


if __name__ == '__main__':
    conn = pymysql.connect("localhost", "root", "root", "rsmovie")
    cursor = conn.cursor()
    sql = "SELECT url_id FROM movies_details"
    cursor.execute(sql)
    conn.commit()
    res = cursor.fetchall()
    conn.close()

    for i, x in enumerate(res):
        print('i: %d' % i)
        url_id = x[0]
        print('url_id:' + url_id)
        show_save_summary(url_id, fetch_summary(url_id))

