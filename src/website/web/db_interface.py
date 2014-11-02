# TODO(gunpinyo): setup db connection

import json


conferences_metadata = {
    0: {
        'name': 'first conference',
        'organizer': 'the abc group',
        'description': 'as I told you, this is the first',
        'when': '1900-01-01T10:00:00.000Z',
        'where': 'somewhere on earth'
    },
    1: {
        'name': 'ACM Hypertext 2009',
        'organizer': 'ACM',
        'description': 'The ACM Hypertext Conference is the main venue'
                       'for high quality peer-reviewed research on'
                       '"linking." The Web, the Semantic Web, the Web 2.0,'
                       'and Social Networks are all manifestations'
                       'of the success of the link.',
        'when': '1900-01-01T10:00:00.000Z',
        'where': 'Viale S. Severo 65, Torino, Italy'
    },
    2: {
        'name': 'third conference',
        'organizer': 'the efg group',
        'description': 'as I told you, this is the third',
        'when': '1900-01-01T10:00:00.000Z',
        'where': 'somewhere on mars'
    }
}


def list_conferences():
    """Return dict which are pairs of conference id and conference name """

    return {conference_id: metadata['name']
            for conference_id, metadata in conferences_metadata.items()}


def contact_list(conference_id):
    # TODO(gunpinyo): implement this
    return {}


def contact_interval(conference_id):
    # TODO(gunpinyo): correct this
    with open('static/example_data/ht09_contact_intervals.json', 'rt') as f:
        ht09_json = json.load(f)
    switch = {
        0: {'0': {'1': [[20, 40], [80, 100]]}},
        1: ht09_json,
        2: {'0': {'1': [[40, 60]], '2': [[60, 100]]}, '1': {'2': [[300, 340]]}}
    }
    return switch[conference_id]


def list_users_in_conference(conference_id):
    """Return dict which are pairs of user id and user name"""

    # TODO(gunpinyo): implement this
    return {}


def conference_metadata(conference_id):
    """Return metadata about this conference"""

    return conferences_metadata[conference_id]


def report_user_profile(user_id):
    """Return metadata about this conference"""

    switch = {
        # TODO(gunpinyo): to write
    }
    return switch[user_id]
