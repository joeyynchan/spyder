from db_connection import db_connect
import http


def register(
        username, hashed_password, hashed_confirm_password):
    if hashed_password != hashed_confirm_password:
        return {
            'is_success': False,
            'error_message': 'Password and retyped password is not match.'
        }

    status_code, _ = db_connect('/register', {
        'user_name': username,
        'password': hashed_password,
    })

    response_dict = {
        http.client.CREATED: {
            'is_success': True,
            'success_message': 'You have successfully registered.'
        },
        http.client.CONFLICT: {
            'is_success': False,
            'error_message':
                'Username "%s" is already in use by another user.' % username
        }
    }

    return response_dict[status_code]


def login(username, hashed_password):
    status_code, _ = db_connect('/login', {
        'user_name': username,
        'password': hashed_password,
        'mac_address': ''  # use for mobile, website parse empty string instead
    })

    response_dict = {
        http.client.OK: {
            'is_success': True,
            'success_message': 'You have successfully signed in.'
        },
        http.client.CREATED: {
            'is_success': True,
            'success_message':
                'You have successfully signed in with new device.'
        },
        http.client.NOT_FOUND: {
            'is_success': False,
            'error_message': 'Username or password not found.'
        },
        http.client.CONFLICT: {
            'is_success': False,
            'error_message': 'You are currently logging in another device'
        }
    }

    return response_dict[status_code]


def add_event(start_time, end_time, address, name,
              description, speaker_id, organiser_id, attendees):
    attendee_list = attendees.split(' ') if attendees != '' else []
    status_code, content = db_connect('/addEvent', {
        'start_time': start_time,
        'end_time': end_time,
        'address': address,
        'name': name,
        'description': description,
        'speaker_id': speaker_id,
        'organiser_id': organiser_id,
        'attendees': [{'user_name': attendee} for attendee in attendee_list]
    })

    response_dict = {
        http.client.CREATED: {
            'is_success': True,
            'success_message': content['Message'],
            'event_id': content['event_id']
        },
        http.client.CONFLICT: {
            'is_success': False,
            'error_message':
                'Somebody has already used "%s" as event name.' % name
        }
    }

    return response_dict[status_code]


# ad = ('{"attendees":[{"name":"A","userid":"1",'
#       '"mac_address":"5463f8d4e4b0952cfce4d426"},'
#       '{"name":"B","userid":"2","mac_address":"5463f8d4e4b0952cfce4d426"},'
#       '{"name":"C","userid":"3","mac_address":"5463f8d4e4b0952cfce4d426"},'
#       '{"name":"D","userid":"4","mac_address":"5463f8d4e4b0952cfce4d426"},'
#       '{"name":"E","userid":"5","mac_address":"5463f8d4e4b0952cfce4d426"}]}')

# ir = ('{"interaction":[{"start_t":"3 Jun 2008 11:05:30","end_t":"3 Jun 2008'
#       ' 11:05:32","duration":"00:00:02","user1":"A","user2":"B"},'
#       '{"start_t":"3 Jun 2008 11:05:35","end_t":"3 Jun 2008 11:05:36",'
#       '"duration":"00:00:02","user1":"A","user2":"B"},'
#       '{"start_t":"3 Jun 2008 11:05:31","end_t":"3 Jun 2008 11:05:34",'
#       '"duration":"00:00:03","user1":"C","user2":"D"}],'
#       '"start_t":"3 Jun 2008 11:05:28","end_t":"3 Jun 2008 11:05:37"}')


# def fake_xhr_get_event_visualisation_data(event_id):
#     return {
#         'interaction': json.loads(ir),
#         'attendees': json.loads(ad)['attendees']
#     }


def xhr_get_event_visualisation_data(event_id):
    interaction_code, interaction_content = db_connect(
        '/event/interaction?event_id=%s' % event_id)
    attendees_code, attendees_content = db_connect(
        '/eventUsers?event_id=%s' % event_id)
    if interaction_code == http.client.OK and attendees_code == http.client.OK:
        return {
            'interaction': interaction_content,
            'attendees': attendees_content
        }
    else:
        return None


def get_event_attendees(event_id):
    status_code, content = db_connect(
        '/eventUsers?event_id=%s' % event_id)

    response_dict = {
        http.client.OK: {
            'is_success': True,
            'user_mappings': content['user_mappings']
        },
        http.client.CONFLICT: {
            'is_success': False,
            'error_message': 'Event "%s" does not exist.' % event_id
        }
    }

    return response_dict[status_code]


def join_event(event_id, username, status="Attendee"):
    status_code, content = db_connect(
        '/event/join?event_id=%s' % event_id, {
            'user_name': username,
            'status': status})

    response_dict = {
        http.client.OK: {
            'is_success': True,
            'success_message':
                'You have successfully joined event %s' % event_id
        },
        http.client.CONFLICT: {
            'is_success': False,
            'error_message': ('Event "%s" or username %s does not exist.'
                              % (event_id, username))
        }
    }

    return response_dict[status_code]


def get_user(username):
    events_code, events_content = db_connect(
        '/getEvents?user_name=%s' % username)
    profile_code, profile_content = db_connect(
        '/user/profile?user_name=%s' % username)

    if (events_code == http.client.NOT_FOUND
            or profile_code == http.client.NOT_FOUND):
        return {
            'is_success': False,
            'error_message':
                '%s does not exist (redirect to dashboard).' % username
        }
    elif events_code == http.client.OK:
        if profile_code == http.client.NO_CONTENT:
            return {
                'is_success': True,
                'events': events_content
            }
        elif profile_code == http.client.OK:
            return {
                'is_success': True,
                'profile': profile_content,
                'events': events_content
            }


def get_event_profile(event_id):
    records = get_all_event_profile()
    for record in records:
        if record.get('event_id', '') == event_id:
            return record
    else:
        return None


def get_all_event_profile():
    return db_connect('/getEvents?user_name=')[1]