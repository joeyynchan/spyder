#!/usr/bin/env python3

# IMPORTANCE: in order to run this server, flask framework is required
# simply just execute "sudo pip3 install flask" to get it
# then execute this as normal python3 script

__author__ = 'Gun Pinyo (gunpinyo@gmail.com)'

import os

from flask import (
    Flask,
    json,
    request,
    session,
    render_template,
    redirect,
    url_for
)
from flask.ext.cors import CORS, cross_origin

import db_api

app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'image/png'


def is_currently_login():
    return 'current_user_username' in session


def render(filepath, args=None):
    if args is None:
        args = {}
    if is_currently_login():
        args['current_user_username'] = session['current_user_username']
    args.update(session.pop('next_page_param_dict', {}))

    return render_template(filepath, **args)


@app.route('/static/<path:filepath>', methods=['GET'])
def serve_static(filepath):
    print(filepath)
    return app.send_static_file(filepath)


def redirect_to_default(error_message=None, stay_on_current_page=False):
    if error_message is not None:
        session['next_page_param_dict'] = {
            'error_message': error_message
        }

    if is_currently_login():
        return redirect(url_for('dashboard'))
    else:
        return redirect(url_for('welcome_page'))


@app.route('/', methods=['GET'])
def index():
    return redirect_to_default()


@app.route('/welcome', methods=['GET'])
def welcome_page():
    if is_currently_login():
        return redirect_to_default(
            'You need to logout in order to go to welcome page.')

    return render('welcome.html.jinja')


@app.route('/register', methods=['GET'])
def register_page():
    if is_currently_login():
        return redirect_to_default(
            'You need to logout in order to go to register page.')

    return render('register.html.jinja')


@app.route('/register', methods=['POST'])
def register():
    if is_currently_login():
        return redirect_to_default(
            'You need to logout in order to register.')

    param_dict = {
        'username': request.form['username'],
        'hashed_password': request.form['password'],
        'hashed_confirm_password': request.form['confirm_password'],
    }

    register_dict = db_api.register(**param_dict)

    if register_dict['is_success']:
        session['current_user_username'] = param_dict['username']
        session['next_page_param_dict'] = register_dict
        return redirect(url_for('dashboard'))
    else:
        session['next_page_param_dict'] = {
            'register_username': param_dict['username']
        }
        session['next_page_param_dict'].update(register_dict)
        return redirect(url_for('register_page'))


@app.route('/login', methods=['POST'])
def login():
    if is_currently_login():
        return redirect_to_default(
            'You need to logout in order to login.')

    param_dict = {
        'username': request.form['username'],
        'hashed_password': request.form['password']
    }

    login_dict = db_api.login(**param_dict)

    if(login_dict['is_success']):
        session['current_user_username'] = param_dict['username']
        session['next_page_param_dict'] = login_dict
        return redirect(url_for('dashboard'))
    else:
        session['next_page_param_dict'] = {
            'login_username': param_dict['username']
        }
        session['next_page_param_dict'].update(login_dict)
        return redirect(url_for('welcome_page'))


@app.route('/logout', methods=['POST'])
def logout():
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to logout.')

    session.clear()
    session['next_page_param_dict'] = {
        'success_message': 'You have successfully signed out.'
    }

    return redirect(url_for('welcome_page'))


@app.route('/dashboard', methods=['GET'])
def dashboard():
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to see dashboard.')
    else:
        return redirect(url_for('user_profile'))


@app.route('/user/profile', methods=['GET', 'OPTIONS'])
@app.route('/user/profile/<username>', methods=['GET', 'OPTIONS'])
@cross_origin()
def user_profile(username=None):
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to see user profile.')

    if username is None:
        username = session['current_user_username']

    user_dict = db_api.get_user(username)

    if user_dict['is_success']:
        for event in user_dict['events']:
            event['can_join_event'] = can_join_event(
                event['event_id'], username)

        organised_events = []
        spoken_events = []
        for event in db_api.get_all_event_profile():
            event['can_join_event'] = can_join_event(
                event['event_id'], username)
            if event.get('organiser', '') == username:
                organised_events.append(event.copy())
            if event.get('speaker', '') == username:
                spoken_events.append(event.copy())

        friends = db_api.get_friends(username, include_detail=True)
        print(friends)
        param_dict = user_dict.copy()
        param_dict['username'] = username
        param_dict['friends'] = friends
        param_dict['is_current_user_friend'] = friends
        param_dict['organised_events'] = organised_events
        param_dict['spoken_events'] = spoken_events
        return render('user_profile.html.jinja', param_dict)
    else:
        session.setdefault('next_page_param_dict', {})
        session['next_page_param_dict'].update(user_dict)
        return redirect_to_default(stay_on_current_page=True)


@app.route('/user/add/<username>', methods=['POST'])
def add_friend(username):
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to add friend.')

    add_friends_dict = db_api.add_friends(
        session['current_user_username'], [username])

    if add_friends_dict['is_success']:
        session['next_page_param_dict'] = add_friends_dict.copy()
        return redirect(url_for(
            'user_profile', username=username))
    else:
        session['next_page_param_dict'] = add_friends_dict.copy()
        return redirect_to_default(stay_on_current_page=True)


@app.route('/user/delete/<username>', methods=['POST'])
def delete_friend(username):
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to delete friend.')

    delete_friends_dict = db_api.delete_friends(
        session['current_user_username'], [username])

    if delete_friends_dict['is_success']:
        session['next_page_param_dict'] = delete_friends_dict.copy()
        return redirect(url_for(
            'user_profile', username=username))
    else:
        session['next_page_param_dict'] = delete_friends_dict.copy()
        return redirect_to_default(stay_on_current_page=True)


@app.route('/user/update-profile', methods=['GET'])
def update_profile_page():
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to update profile.')

    user_profile = db_api.get_user_profile(session['current_user_username'])

    # remember for unused profile friend list
    if user_profile:
        session['profile_connections'] = user_profile['connections']
        return render('update_profile.html.jinja', user_profile)
    else:
        session['profile_connections'] = '[]'
        return render('update_profile.html.jinja')


@app.route('/user/update-profile', methods=['POST'])
def update_profile():
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to update profile.')

    param_dict = {
        'name': request.form['name'],
        'job': request.form['job'],
        'company': request.form['company'],
        'email': request.form['email'],
        'phone': request.form['phone'],
        'external_link': request.form['external_link'],
        'gender': request.form['gender'],
        'photo': request.form['photo'],
        'connections': session['profile_connections']
    }

    update_profile_dict = db_api.update_profile(
        session['current_user_username'], **param_dict)

    session['next_page_param_dict'] = update_profile_dict.copy()
    return redirect_to_default()


@app.route('/event/add', methods=['GET'])
def add_event_page():
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to add event.')

    return render('add_event.html.jinja')


@app.route('/event/add', methods=['POST'])
def add_event():
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to add event.')

    param_dict = {
        'start_time': request.form['start_time'],
        'end_time': request.form['end_time'],
        'address': request.form['address'],
        'name': request.form['name'],
        'description': request.form['description'],
        'speaker_id': request.form['speaker_id'],
        'organiser_id': session['current_user_username'],
        'attendees': request.form['attendees']
    }

    add_event_dict = db_api.add_event(**param_dict)

    if add_event_dict['is_success']:
        session['next_page_param_dict'] = add_event_dict
        return redirect(url_for(
            'event_profile', event_id=add_event_dict['event_id']))
    else:
        session['next_page_param_dict'] = param_dict.copy()
        session['next_page_param_dict'].update(add_event_dict)
        return redirect(url_for('add_event_page'))


@app.route('/event/join/<event_id>', methods=['POST'])
def join_event(event_id):
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to join event.')

    join_event_dict = db_api.join_event(
        event_id, session['current_user_username'])

    if join_event_dict['is_success']:
        session['next_page_param_dict'] = join_event_dict.copy()
        return redirect(url_for(
            'event_profile', event_id=event_id))
    else:
        session['next_page_param_dict'] = join_event_dict.copy()
        return redirect_to_default(stay_on_current_page=True)


def can_join_event(event_id, username=None):
    if username is None:
        username = session['current_user_username']
    attendees_dict = db_api.get_event_attendees(event_id)
    if attendees_dict['is_success']:
        attendees_list = (
            record['user_name'] for record in attendees_dict['user_mappings'])
        return username not in attendees_list
    else:
        return False


@app.route('/event/profile/<event_id>', methods=['GET'])
@app.route('/event/profile/<event_id>/personal/<username>', methods=['GET'])
@cross_origin()
def event_profile(event_id, username=None):
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to see event profile.')

    param_dict = {
        'event_id': event_id,
        'profile': db_api.get_event_profile(event_id)
    }
    param_dict.update(db_api.xhr_get_event_visualisation_data(event_id))
    param_dict['can_join_event'] = can_join_event(event_id)
    if username:
        param_dict['pinned_username'] = username
        param_dict['pinned_friend'] = db_api.get_friends(username)
    return render('event_profile.html.jinja', param_dict)


@app.route('/event/list', methods=['GET'])
def event_list():
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to see event list.')

    profiles = []
    for profile in db_api.get_all_event_profile():
        profile['can_join_event'] = can_join_event(profile['event_id'])
        profiles.append(profile)

    return render('event_list.html.jinja', {'profiles': profiles})


@app.route('/xhr/event_visualisation_data/<event_id>', methods=['GET'])
def xhr_get_event_visualisation_data(event_id):
    return json.jsonify(db_api.xhr_get_event_visualisation_data(event_id))


@app.route('/fake_xhr/event_visualisation_data/<event_id>', methods=['GET'])
def fake_xhr_get_event_visualisation_data(event_id):
    return json.jsonify(db_api.fake_xhr_get_event_visualisation_data(event_id))


if __name__ == '__main__':
    # secret key for flask session
    app.secret_key = os.urandom(40)

    app.run(host='0.0.0.0', port=55556, debug=True)
