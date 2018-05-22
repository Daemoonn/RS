import pymysql
import requests
import imghdr
import proxy_pool

db = pymysql.connect("localhost", "root", "root", "rsmovie")
cursor = db.cursor()

sql = "SELECT movieId, img_link FROM movies_details"
# 执行SQL语句
cursor.execute(sql)
# 获取所有记录列表
results = cursor.fetchall()
db.close()

print('length: %d' % len(results))


for i, x in enumerate(results):
    print('i: %d' % i)
    movieId, img_link = x
    print(movieId, img_link)
    c = 0
    while True:
        response = proxy_pool.getHtml(img_link)
        if response and response.status_code == 200 and (imghdr.what(None, response.content) is not None):
            print('img getted')
            break
        c = c + 1
        # 得到代理正常，但目标资源本身不正常，可能陷入死循环
        # 比如目标服务器始终返回的都不是图片，此时换代理就没用
        # 数据量不大，可以观察下，希望资源都是正常的
        # 有的代理返回的不是图片使用imghdr检测字节流是否为图片，更换代理，此外目前douban服务器返回一切正常
        if response and imghdr.what(None, response.content) is None:
            print("response is not img")
        print("Change proxy and try again: %d" % c)
    # print('status_code: %d' % response.status_code)
    # print(response.content)
    extension = img_link.split('/')[-1].split('.')[-1]
    with open('pic/' + str(movieId) + '.' + extension, 'wb') as file:
        file.write(response.content)
