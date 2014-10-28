# TODO(gunpinyo): setup db connection


def list_conferences():
    """Return dict which are pairs of conference id and conference name """

    return {
        0: 'first conference',
        1: 'second conference'
    }


def contect_list(conference_id):
    # TODO(gunpinyo): implement this
    return {}


def contect_interval(conference_id):
    # TODO(gunpinyo): implement this
    return {}


def list_users():
    """Return dict which are pairs of user id and user name"""
    return {i: "mynameis"+str(i) for i in range(10000)}


def report_conference_detail(conference_id):
    """Return metadata about this conference"""

    switch = {
        0: {
            'name': 'first conference',
            'organizer': 'the abc group',
            'description': 'as I told you, this is the first',
            'when': '1900-01-01T10:00:00.000Z',
            'where': 'somewhere on earth'
        },
        1: {
            'name': 'second conference',
            'organizer': 'the efg group',
            'description': 'as I told you, this is the first',
            'when': '1900-01-01T10:00:00.000Z',
            'where': 'somewhere on earth'
        }
    }
    return switch[conference_id]


def report_user_profile(user_id):
    """Return metadata about this conference"""

    switch = {
        # TODO(gunpinyo): to write
    }
    return switch[user_id]
