import urllib.parse
import urllib.request
import urllib.error
import json

import http.client
import db_api
http.client.HTTPConnection.debuglevel

DB_URL = 'http://146.169.46.38:8080/MongoDBWebapp'
# DB_URL = 'http://146.169.32.147:55555'


def db_connect(url, param_dict=None, method=None):
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
        return error.code, None

    response_text = response.read().decode(encoding='UTF-8')
    response_dict = json.loads(response_text) if response_text != '' else None

    return response.status, response_dict


if __name__ == '__main__':
    # response = db_connect('/addEvent', {
    #     "start_time": "Mon May 04 09:51:52 CDT 2009",
    #     "end_time": "Mon May 05 18:51:52 CDT 2009",
    #     "address": "Imperial College London",
    #     "name": "eeeeeeeedadeeeevvvvvvvvvvtttt",
    #     "description": "I love add event botton.",
    #     "speaker_id": "demo1",
    #     "organiser_id": "demo1",
    #     "attendees": []
    # })
    # response = db_api.xhr_get_event_visualisation_data(
    #     '54775c5de4b0598ae9308641')
    # response = db_connect(
    #     '/event/interaction?event_id=%s' % '54775c5de4b0598ae9308641')
    response = db_connect('/getEvents?user_name=%s' % 'demo3')
    print(response)
