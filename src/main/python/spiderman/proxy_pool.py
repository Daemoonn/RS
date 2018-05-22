import requests
import traceback
import json


def get_proxy():
    return requests.get("http://127.0.0.1:5010/get/").content


def delete_proxy(proxy):
    requests.get("http://127.0.0.1:5010/delete/?proxy={}".format(bytes.decode(proxy)))
    print('delete proxy')

# your spider code


def getHtml(url):
    retry_count = 1
    proxy = get_proxy()
    print("new proxy getted")
    header = {
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9",
        "Host": "img3.doubanio.com",
        "Connection": "keep-alive",
        "Upgrade-Insecure-Requests": "1",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
    }
    while retry_count > 0:
        try:
            response = requests.get(url, headers=header, proxies={"http": "http://{}".format(bytes.decode(proxy))}, timeout=5)
            print('response getted')
            # 使用代理访问
            return response
        except Exception as e:
            traceback.print_exc()
            retry_count -= 1
    # 出错1次, 删除代理池中代理
    delete_proxy(proxy)
    return None


# 待重构
def get_summaryJson(url_id):
    retry_count = 1
    proxy = get_proxy()
    print("new proxy getted")
    url = 'http://api.douban.com/v2/movie/subject/' + url_id
    header = {
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9",
        "Host": "api.douban.com",
        "Connection": "keep-alive",
        "Upgrade-Insecure-Requests": "1",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
    }
    while retry_count > 0:
        try:
            response = requests.get(url, headers=header, proxies={"http": "http://{}".format(bytes.decode(proxy))}, timeout=5)
            print('response getted')
            json_result = response.json()
            print('json getted')
            # 使用代理访问
            return json_result
        except Exception as e:
            traceback.print_exc()
            retry_count -= 1
    # 出错1次, 删除代理池中代理
    delete_proxy(proxy)
    return None


def getJson(keyword=None):
    # ....
    retry_count = 1
    proxy = get_proxy()
    print("new proxy getted")
    url = 'http://api.douban.com/v2/movie/search?q=' + keyword
    # params = {'q': keyword}
    header = {
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9",
        "Cache-Control": "max-age=0",
        "Host": "api.douban.com",
        "Connection": "keep-alive",
        "Upgrade-Insecure-Requests": "1",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
    }
    while retry_count > 0:
        try:
            response = requests.get(url, headers=header, proxies={"http": "http://{}".format(bytes.decode(proxy))}, timeout=5)
            print('response getted')
            json_result = response.json()
            print('json getted')
            # 使用代理访问
            return json_result
        except Exception as e:
            traceback.print_exc()
            retry_count -= 1
    # 出错1次, 删除代理池中代理
    delete_proxy(proxy)
    return None


if __name__ == '__main__':
    res = getJson('toy story')
    with open("./hmm.json", 'w', encoding='utf-8') as json_file:
        json.dump(res, json_file, ensure_ascii=True)