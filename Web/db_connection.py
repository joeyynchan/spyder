import urllib.parse
import urllib.request
import urllib.error
import json

DB_URL = 'http://146.169.46.38:8080/MongoDBWebapp'
# DB_URL = 'http://146.169.32.147:55555'


def db_connect(url, param_dict=None):
    if param_dict is not None:
        param = json.dumps(param_dict)
        param = param.encode('utf-8')
        request = urllib.request.Request(
            DB_URL + url,
            data=param,
            headers={'Content-Type': 'application/json'},
            method='POST')
    else:
        request = urllib.request.Request(DB_URL + url)

    try:
        response = urllib.request.urlopen(request)
    except urllib.error.HTTPError as error:
        return error.code, None

    response_text = response.read().decode(encoding='UTF-8')
    response_dict = json.loads(response_text) if response_text != '' else None

    return response.status, response_dict


if __name__ == '__main__':
    response = db_connect(
        '/event/interaction?event_id=54751551e4b08c8af4a64db3')
    print(response)
