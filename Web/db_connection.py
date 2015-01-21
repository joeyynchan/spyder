import urllib.parse
import urllib.request
import urllib.error
import json

import http.client
http.client.HTTPConnection.debuglevel

DB_URL = 'http://146.169.46.38:55555/MongoDBWebapp'
# DB_URL = 'http://146.169.46.38:8080/MongoDBWebapp'
# DB_URL = 'http://146.169.32.147:55555'


def db_connect(url, param_dict=None, method=None, verbose=True):
    if verbose:
        print('\n>>> %s >>> %s' % (url, param_dict))
    if param_dict is not None:
        if method is None:
            method = 'POST'
        param = json.dumps(param_dict)
        param = param.encode('utf-8')
        request = urllib.request.Request(
            DB_URL + url,
            data=param,
            headers={'Content-Type': 'application/json'},
            method=method)
    else:
        if method is None:
            method = 'GET'
        request = urllib.request.Request(DB_URL + url, method=method)

    try:
        response = urllib.request.urlopen(request)
    except urllib.error.HTTPError as error:
        if verbose:
            print('... db 4xx returning %s\n' % error.code)
        return error.code, None

    response_text = response.read().decode(encoding='UTF-8')
    response_dict = json.loads(response_text) if response_text != '' else None

    if verbose:
        print('... db returning %s %s\n' % (response.code, response_dict))
    return response.code, response_dict


if __name__ == '__main__':
    # response = db_connect('/addEvent', {
    #     "start_time": "Mon May 04 09:51:52 CDT 2009",
    #     "end_time": "Mon May 05 18:51:52 CDT 2018",
    #     "address": "Imperial College London",
    #     "name": "test event new ...",
    #     "description": "I love add event botton so much.",
    #     "speaker_id": "demo002",
    #     "organiser_id": "demo1",
    #     "attendees": [{"user_name": "demo3"}]
    # })
    # response = db_api.xhr_get_event_visualisation_data(
    #     '54775c5de4b0598ae9308641')
    # response = db_connect(
    #     '/event/interaction?event_id=%s' % '54775c5de4b0598ae9308641')
    # response = db_connect('/getEvents?user_name=%s' % 'demo002')
    # response = db_connect('/user/profile?user_name=%s' % 'peterpk')
    # response = db_connect('''/getEvents?user_name=''')
    # response = db_connect('/searchEvent', {
    #     "user_name": "",
    #     "event_search_string": ""
    # })
    # response = db_connect(
    #     '/event_data?event_id=%s' % '54ae9620e4b0f2503a7a449d')
    response = db_connect('/user/connections?user_name=peterpk')
    # response = db_connect(
    #     '/user/connections?user_name=peterpk',
    #     {'operation': 'add',
    #      'connections': ['demo009']
    #      })
    print(response)
