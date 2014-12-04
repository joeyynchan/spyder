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


# def create_event(
#         organizer_id, username, name, start_time, end_time,
#         location, description):
#     event_id = len(event_db)

#     if organizer_id not in user_db.keys():
#         return {
#             'is_success': False,
#             'error_message': 'organizer_id is not valid.'
#         }

#     event_db[event_id] = {
#         'event_id': event_id,
#         'username': username,
#         'name': name,
#         'start_time': start_time,
#         'end_time': end_time,
#         'location': location,
#         'description': description,
#         'members': {
#             'accepted': {
#                 'organizer': [organizer_id],
#                 'speaker': [],
#                 'attendee': []
#             },
#             'requested': {
#                 'speaker': [],
#                 'attendee': []
#             },
#             'invited': {
#                 'speaker': [],
#                 'attendee': []
#             }
#         }
#     }

#     return {
#         'is_success': True,
#         'user_id': user_id
#     }


# def get_user(user_id):
#     if user_id in user_db.keys():
#         return {
#             'is_success': True,
#             'user_record': user_db[user_id]
#         }
#     else:
#         return {
#             'is_success': False,
#             'error_message': 'This user_id is invalid'
#         }


# def get_name_user(user_id):
#     return (user_db[user_id]['name']
#             if user_id in user_db.keys()
#             else 'unknown')


# def get_event(event_id):
#     if event_id in event_db.keys():
#         return {
#             'is_success': True,
#             'event_record': event_db[event_id]
#         }
#     else:
#         return {
#             'is_success': False,
#             'error_message': 'This event_id is invalid'
#         }


# def get_name_event(event_id):
#     return (event_db[event_id]['name']
#             if event_id in event_db.keys()
#             else 'unknown')

    # register
    # if IS_DUMMY_DB:
    #     if username in login_db.keys():
    #         return {
    #             'is_success': False,
    #             'error_message': 'This username has already taken'
    #         }
    #     elif hashed_password != hashed_confirm_password:
    #         return {
    #             'is_success': False,
    #             'error_message': 'This password and password_confirm is not match.'
    #         }

    #     user_id = len(user_db)

    #     login_db[username] = {
    #         'user_id': user_id,
    #         'hashed_password': hashed_password
    #     }

    #     user_db[user_id] = {
    #         'user_id': user_id,
    #         'username': username,
    #         'name': '',
    #         'gender': '',
    #         'occupation': '',
    #         'organization': '',
    #         'picture': '',
    #         'email': email,
    #         'phone': '',
    #         'external_link': '',
    #         'events': {
    #             'accepted': {
    #                 'organizer': [],
    #                 'speaker': [],
    #                 'attendee': []
    #             },
    #             'requested': {
    #                 'speaker': [],
    #                 'attendee': []
    #             },
    #             'invited': {
    #                 'speaker': [],
    #                 'attendee': []
    #             }
    #         }
    #     }

    #     return {
    #         'is_success': True,
    #         'user_id': user_id
    #     }

    # login api
    # if IS_DUMMY_DB:
    #     if username not in login_db.keys():
    #         return {
    #             'is_success': False,
    #             'error_message': 'The username is invalid'
    #         }
    #     elif hashed_password != login_db[username]['hashed_password']:
    #         return {
    #             'is_success': False,
    #             'error_message': 'The password is invalid'
    #         }
    #     else:
    #         return {
    #             'is_success': True,
    #             'user_id': login_db[username]['user_id']
    #         }

# def list_conferences():
#     """Return dict which are pairs of conference id and conference name """

#     return {conference_id: metadata['name']
#             for conference_id, metadata in conferences_metadata.items()}


# def contact_list(conference_id):
#     # TODO(gunpinyo): implement this
#     return {}


# def contact_interval(conference_id):
#     # TODO(gunpinyo): correct this
#     with open('static/example_data/ht09_contact_intervals.json', 'rt') as f:
#         ht09_json = json.load(f)
#     switch = {
#         0: {'0': {'1': [[20, 40], [80, 100]]}},
#         1: ht09_json,
#         2: {'0': {'1': [[40, 60]], '2': [[60, 100]]}, '1': {'2': [[300, 340]]}}
#     }
#     return switch[conference_id]


# def list_users_in_conference(conference_id):
#     """Return dict which are pairs of user id and user name"""

#     # TODO(gunpinyo): implement this
#     return {}


# def conference_metadata(conference_id):
#     """Return metadata about this conference"""

#     return conferences_metadata[conference_id]


# def report_user_profile(user_id):
#     """Return metadata about this conference"""

#     switch = {
#         # TODO(gunpinyo): to write
#     }
#     return switch[user_id]


# login_db = {
#     'alice': {
#         'user_id': 0,
#         'hashed_password': 'pwaaaeiei'
#     },
#     'bob': {
#         'user_id': 1,
#         'hashed_password': 'pwbbbeiei'
#     },
#     'charlie': {
#         'user_id': 2,
#         'hashed_password': 'pwccceiei'
#     },
# }

# user_db = {
#     0: {
#         'user_id': 0,
#         'username': 'alice',
#         'name': 'Miss Alice Ant',
#         'gender': 'female',
#         'occupation': 'student',
#         'organization': 'Imperial College London',
#         'picture': '...',
#         'email': '...',
#         'phone': '...',
#         'external_link': 'www.example.com/alice',
#         'events': {
#             'accepted': {
#                 'organizer': [0],
#                 'speaker': [],
#                 'attendee': [1, 2]
#             },
#             'requested': {
#                 'speaker': [],
#                 'attendee': []
#             },
#             'invited': {
#                 'speaker': [],
#                 'attendee': []
#             }
#         }
#     },
#     1: {
#         'user_id': 1,
#         'username': 'bob',
#         'name': 'Mr Bob Bird',
#         'gender': 'male',
#         'occupation': 'student',
#         'organization': 'Imperial College London',
#         'picture': '...',
#         'email': '...',
#         'phone': '...',
#         'external_link': 'www.example.com/alice',
#         'events': {
#             'accepted': {
#                 'organizer': [1, 2],
#                 'speaker': [],
#                 'attendee': [0]
#             },
#             'requested': {
#                 'speaker': [],
#                 'attendee': []
#             },
#             'invited': {
#                 'speaker': [],
#                 'attendee': []
#             }
#         }
#     },
#     2: {
#         'user_id': 2,
#         'username': 'charlie',
#         'name': 'Mr Charlie Cat',
#         'gender': 'male',
#         'occupation': 'student',
#         'organization': 'Imperial College London',
#         'picture': '...',
#         'email': '...',
#         'phone': '...',
#         'external_link': 'www.example.com/alice',
#         'events': {
#             'accepted': {
#                 'organizer': [],
#                 'speaker': [],
#                 'attendee': [0, 2]
#             },
#             'requested': {
#                 'speaker': [],
#                 'attendee': []
#             },
#             'invited': {
#                 'speaker': [],
#                 'attendee': []
#             }
#         }
#     },
# }


# event_db = {
#     0: {
#         'event_id': 0,
#         'name': 'first conference',
#         'start_time': '1900-01-01T10:00:00Z',
#         'end_time': '1900-01-01T10:30:00Z',
#         'location': 'somewhere on earth',
#         'description': 'as I told you, this is the first',
#         'members': {
#             'accepted': {
#                 'organizer': [0],
#                 'speaker': [],
#                 'attendee': [1, 2]
#             },
#             'requested': {
#                 'speaker': [],
#                 'attendee': []
#             },
#             'invited': {
#                 'speaker': [],
#                 'attendee': []
#             }
#         }
#     },
#     1: {
#         'event_id': 1,
#         'name': 'ACM Hypertext 2009',
#         'start_time': '1900-02-01T10:00:00Z',
#         'end_time': '1900-02-04T10:00:00Z',
#         'location': 'Viale S. Severo 65, Torino, Italy',
#         'description': 'The ACM Hypertext Conference is the main venue'
#                        'for high quality peer-reviewed research on'
#                        '"linking." The Web, the Semantic Web, the Web 2.0,'
#                        'and Social Networks are all manifestations'
#                        'of the success of the link.',
#         'members': {
#             'accepted': {
#                 'organizer': [1],
#                 'speaker': [],
#                 'attendee': [0]
#             },
#             'requested': {
#                 'speaker': [],
#                 'attendee': []
#             },
#             'invited': {
#                 'speaker': [],
#                 'attendee': []
#             }
#         }
#     },
#     2: {
#         'event_id': 2,
#         'name': 'third conference',
#         'start_time': '1200-01-01T10:00:00Z',
#         'end_time': '1200-01-01T10:30:00Z',
#         'location': 'somewhere on earth',
#         'description': 'as I told you, this is the first',
#         'members': {
#             'accepted': {
#                 'organizer': [1],
#                 'speaker': [],
#                 'attendee': [0, 2]
#             },
#             'requested': {
#                 'speaker': [],
#                 'attendee': []
#             },
#             'invited': {
#                 'speaker': [],
#                 'attendee': []
#             }
#         }
#     }
# }
