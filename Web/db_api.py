# TODO(gunpinyo): setup db connection

login_db = {
    'alice': {
        'user_id': '0',
        'hashed_password': 'pwaaaeiei'
    },
    'bob': {
        'user_id': '1',
        'hashed_password': 'pwbbbeiei'
    },
    'charlie': {
        'user_id': '2',
        'hashed_password': 'pwccceiei'
    },
}

user_db = {
    0: {
        'user_id': 0,
        'username': 'alice',
        'name': 'Miss Alice Ant',
        'gender': 'female',
        'occupation': 'student',
        'organization': 'Imperial College London',
        'picture': '...',
        'email': '...',
        'phone': '...',
        'external_link': 'www.example.com/alice',
        'events': {
            'accepted': {
                'organizer': [0],
                'speaker': [],
                'attendee': [1, 2]
            },
            'requested': {
                'speaker': [],
                'attendee': []
            },
            'invited': {
                'speaker': [],
                'attendee': []
            }
        }
    },
    1: {
        'user_id': 1,
        'username': 'bob',
        'name': 'Mr Bob Bird',
        'gender': 'male',
        'occupation': 'student',
        'organization': 'Imperial College London',
        'picture': '...',
        'email': '...',
        'phone': '...',
        'external_link': 'www.example.com/alice',
        'events': {
            'accepted': {
                'organizer': [1, 2],
                'speaker': [],
                'attendee': [0]
            },
            'requested': {
                'speaker': [],
                'attendee': []
            },
            'invited': {
                'speaker': [],
                'attendee': []
            }
        }
    },
    2: {
        'user_id': 2,
        'username': 'charlie',
        'name': 'Mr Charlie Cat',
        'gender': 'male',
        'occupation': 'student',
        'organization': 'Imperial College London',
        'picture': '...',
        'email': '...',
        'phone': '...',
        'external_link': 'www.example.com/alice',
        'events': {
            'accepted': {
                'organizer': [],
                'speaker': [],
                'attendee': [0, 2]
            },
            'requested': {
                'speaker': [],
                'attendee': []
            },
            'invited': {
                'speaker': [],
                'attendee': []
            }
        }
    },
}


event_db = {
    0: {
        'event_id': 0,
        'name': 'first conference',
        'start_time': '1900-01-01T10:00:00Z',
        'end_time': '1900-01-01T10:30:00Z',
        'location': 'somewhere on earth',
        'description': 'as I told you, this is the first',
        'members': {
            'accepted': {
                'organizer': [0],
                'speaker': [],
                'attendee': [1, 2]
            },
            'requested': {
                'speaker': [],
                'attendee': []
            },
            'invited': {
                'speaker': [],
                'attendee': []
            }
        }
    },
    1: {
        'event_id': 1,
        'name': 'ACM Hypertext 2009',
        'start_time': '1900-02-01T10:00:00Z',
        'end_time': '1900-02-04T10:00:00Z',
        'location': 'Viale S. Severo 65, Torino, Italy',
        'description': 'The ACM Hypertext Conference is the main venue'
                       'for high quality peer-reviewed research on'
                       '"linking." The Web, the Semantic Web, the Web 2.0,'
                       'and Social Networks are all manifestations'
                       'of the success of the link.',
        'members': {
            'accepted': {
                'organizer': [1],
                'speaker': [],
                'attendee': [0]
            },
            'requested': {
                'speaker': [],
                'attendee': []
            },
            'invited': {
                'speaker': [],
                'attendee': []
            }
        }
    },
    2: {
        'event_id': 2,
        'name': 'third conference',
        'start_time': '1200-01-01T10:00:00Z',
        'end_time': '1200-01-01T10:30:00Z',
        'location': 'somewhere on earth',
        'description': 'as I told you, this is the first',
        'members': {
            'accepted': {
                'organizer': [1],
                'speaker': [],
                'attendee': [0, 2]
            },
            'requested': {
                'speaker': [],
                'attendee': []
            },
            'invited': {
                'speaker': [],
                'attendee': []
            }
        }
    }
}


def login(username, hashed_password):
    if username not in login_db.keys():
        return {
            'is_valid': False,
            'error_massage': 'The username is invalid'
        }
    elif hashed_password != login_db[username]['hashed_password']:
        return {
            'is_valid': False,
            'error_massage': 'The password is invalid'
        }
    else:
        return {
            'is_valid': True,
            'user_id': login_db[username]['user_id']
        }


def create_event(
        organizer_id, username, name, start_time, end_time,
        location, description):
    event_id = len(event_db)

    if organizer_id not in user_db.keys():
        return False

    event_db[event_id] = {
        'event_id': event_id,
        'username': username,
        'name': name,
        'start_time': start_time,
        'end_time': end_time,
        'location': location,
        'description': description,
        'members': {
            'accepted': {
                'organizer': [organizer_id],
                'speaker': [],
                'attendee': []
            },
            'requested': {
                'speaker': [],
                'attendee': []
            },
            'invited': {
                'speaker': [],
                'attendee': []
            }
        }
    }

    return True


def get_user(user_id):
    if user_id in user_db.keys():
        return {
            'is_valid': True,
            'user_profile': user_db[user_id]
        }
    else:
        return {
            'is_valid': False,
            'error_massage': 'This user_id is invalid'
        }


def get_event(event_id):
    if event_id in event_db.keys():
        return {
            'is_valid': True,
            'event_profile': event_db[event_id]
        }
    else:
        return {
            'is_valid': False,
            'error_massage': 'This event_id is invalid'
        }


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
