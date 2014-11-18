#!/usr/bin/env python3

# IMPORTANCE: in order to run this server, flask framework is required
# simply just execute "sudo pip3 install flask" to get it
# then execute this as normal python3 script

__author__ = 'Gun Pinyo (gunpinyo@gmail.com)'

import os
import json

from flask import (
    Flask,
    request,
    session,
    render_template,
    redirect,
    url_for
)

import db_api

app = Flask(__name__)


@app.route('/', methods=['GET'])
def index():
    if 'current_user_id' in session:
        return redirect(url_for('dashboard'))
    else:
        return redirect(url_for('welcome'))


@app.route('/static/<path:filepath>', methods=['GET'])
def serve_static(filepath):
    return app.send_static_file(filepath)


@app.route('/welcome', methods=['GET'])
def welcome():
    param_dict = session.pop('next_page_param_dict', {})
    return render_template('welcome.html.jinja', **param_dict)


@app.route('/login', methods=['POST'])
def login():
    # TODO(gunpinyo): change to assertion to redirection
    assert ('current_user_id' not in session)

    param_dict = {
        'username': request.form['username'],
        'hashed_password': request.form['password']
    }

    login_dict = db_api.login(**param_dict)

    if(login_dict['is_success']):
        session['current_user_id'] = login_dict['user_id']
        session['next_page_param_dict'] = {
            'success_message': 'You have successfully signed in.'
        }
        return redirect(url_for('dashboard'))
    else:
        session['next_page_param_dict'] = {
            'error_message': login_dict['error_message']
        }
        return redirect(url_for('welcome'))


@app.route('/logout', methods=['POST'])
def logout():
    # TODO(gunpinyo): change to assertion to redirection
    assert ('current_user_id' in session)

    session.pop('current_user_id')
    session['next_page_param_dict'] = {
        'success_message': 'You have successfully signed out.'
    }

    return redirect(url_for('welcome'))


@app.route('/dashboard', methods=['GET'])
def dashboard():
    # TODO(gunpinyo): (low-priority) implement a proper dashboard
    return redirect(url_for('user_profile'))


@app.route('/user/profile', methods=['GET'])
@app.route('/user/profile/<int:user_id>', methods=['GET'])
def user_profile(user_id=None):
    # TODO(gunpinyo): change to assertion to redirection
    assert ('current_user_id' in session)

    if user_id is None:
        user_id = session['current_user_id']

    user_dict = db_api.get_user(user_id)

    # if not user_dict['is_success']:
    #     session['next_page_param_dict'] = {
    #         'error_message': user_dict['error_message']
    #     }

    param_dict = {
        'user_id': user_id,
        'user_record': user_dict['user_record'],
        'user_event_table': [
            {
                'event_id': event_id,
                'event_name': db_api.get_name_event(event_id),
                'role': role,
                'status': status
            }
            for status, helper_1 in user_dict['user_record']['events'].items()
            for role, helper_2 in helper_1.items()
            for event_id in helper_2
        ]
    }
    param_dict.update(session.pop('next_page_param_dict', {}))

    return render_template('user_profile.html.jinja', **param_dict)


# @app.route('/ajax/conferences', methods=['GET'])
# def get_list_conferences():
#     return json.jsonify(db_interface.list_conferences())


# # @app.get('/ajax/conference/<conference_id>/contact-list')
# # def contact_list(conference_id):
# #     response.content_type = 'application/json'
# #     return db_interface.contact_list(conference_id)


# @app.route('/ajax/contact-interval', methods=['GET'])
# def contact_interval():
#     return json.jsonify(
#         db_interface.contact_interval(
#             int(request.args.get('conference_id', '-1'))))


# @app.route('/xhr/event/create', methods=['POST'])
# def create_event():
#     return json.jsonify(db_api.create_event(**request.get_json()))


# @app.route('/xhr/event/get/<int:event_id>', methods=['GET'])
# def get_event(event_id):
#     return json.jsonify(db_api.get_event(event_id))

# @app.route('/xhr/user/create', methods=['POST']):
# def create_user(user_id):
#     return json.jsonify(db_api.create_())


# @app.route('/xhr/user/get/<int:user_id>', methods=['GET'])
# def get_user(user_id):
#     return json.jsonify(db_api.get_user(user_id))

if __name__ == '__main__':
    # secret key for flask session
    app.secret_key = os.urandom(40)

    app.run(host='0.0.0.0', port=55556, debug=True)
