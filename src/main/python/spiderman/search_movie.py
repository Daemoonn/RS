import dbutil
import proxy_pool


def search_movie(keyword=None, myear=None):
    c = 0
    while True:
        movie_result = proxy_pool.getJson(keyword)
        if movie_result and movie_result.get('code') != 112:
            break
        c = c + 1
        # 得到代理正常，但目标资源本身不正常，可能陷入死循环
        # 比如代理正常，而目标服务器返回一堆乱码，json解析失败，但此时不是代理的锅
        # 数据量不大，可以观察下，希望资源都是正常的 ---最终结果证明目标服务器返回结果都是正确的
        print("Change proxy and try again: %d" % c)

    if movie_result.get('subjects'):
        Id, en_title, cn_title, year, image, alt = (None,) * 6
        for x in movie_result['subjects']:
            year = x.get('year')
            if year == myear:
                Id = x.get('id')
                en_title = x.get('original_title')
                cn_title = x.get('title')
                if x.get('images'):
                    image = x['images'].get('small')
                alt = x.get('alt')
                if not Id and not en_title and not cn_title \
                        and not year and not image and not alt:
                    return None
                # to string
                details = [Id, en_title, cn_title, year, image, alt]
                for i, x in enumerate(details):
                    details[i] = str(x)
                return details
        return None
    else:
        return None #此处可能由于IP被禁用，回来可能要改


def show_save_moviedetail(id_indb, details):
    if not details:
        print('No movie details')
        return

    print('-------------------------------------')
    print('id:' + details[0])
    print('en_title:' + details[1])
    print('cn_title:' + details[2])
    print('year:' + details[3])
    print('img Link:' + details[4])
    print('link:' + details[5])
    print('-------------------------------------')
    dbutil.insert(id_indb, details)


if __name__ == '__main__':
    results = dbutil.getresults()
    length = len(results)
    print('length: %d' % length)
    i = 0
    while i < length:
        id_indb = str(results[i][0]).strip()
        title = results[i][1].strip()
        published_year = results[i][2].strip()
        print('i: %d' % i)
        print(id_indb, title, published_year)
        show_save_moviedetail(id_indb=id_indb, details=search_movie(title, published_year))
        # time.sleep(random.random() * 2)
        i = i + 1

