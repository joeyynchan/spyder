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

import db_api

app = Flask(__name__)


def is_currently_login():
    return 'current_user_username' in session


@app.route('/static/<path:filepath>', methods=['GET'])
def serve_static(filepath):
    return app.send_static_file(filepath)


def redirect_to_default(error_message=None):
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

    param_dict = session.pop('next_page_param_dict', {})
    return render_template('welcome.html.jinja', **param_dict)


@app.route('/register', methods=['GET'])
def register_page():
    if is_currently_login():
        return redirect_to_default(
            'You need to logout in order to go to register page.')

    param_dict = session.pop('next_page_param_dict', {})
    return render_template('register.html.jinja', **param_dict)


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

    if(register_dict['is_success']):
        session['current_user_username'] = param_dict['username']
        session['next_page_param_dict'] = register_dict
        return redirect(url_for('dashboard'))
    else:
        session['next_page_param_dict'] = register_dict
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
        session['next_page_param_dict'] = login_dict
        return redirect(url_for('welcome_page'))


@app.route('/logout', methods=['POST'])
def logout():
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to logout.')

    # TODO(gunpiyo): research how to disable undo cache after logout
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
        # TODO(gunpinyo): fix this
        param_dict = session.pop('next_page_param_dict')
        return render_template('privileged_base.html.jinja', **param_dict)
        # return redirect(url_for('user_profile'))


@app.route('/user/profile', methods=['GET'])
@app.route('/user/profile/<int:user_id>', methods=['GET'])
def user_profile(user_id=None):
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to see user profile.')

    if user_id is None:
        user_id = session['current_user_username']

    user_dict = db_api.get_user(user_id)

    if user_dict['is_success']:
        param_dict = {
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

    else:
        return redirect_to_default(user_dict['error_message'])


@app.route('/event/profile/<event_id>', methods=['GET'])
def event_profile(event_id):
    if not is_currently_login():
        return redirect_to_default(
            'You need to login in order to see event profile.')

    # TODO(gunpinyo): re-enable this
    # event_dict = db_api.get_event(event_id)

    # if event_dict['is_success']:
    #     param_dict = {
    #         'event_record': event_dict['event_record'],
    #         'event_member_table': [
    #             {
    #                 'user_id': user_id,
    #                 'username': db_api.get_name_user(user_id),
    #                 'role': role,
    #                 'status': status
    #             }
    #             for status, helper_1
    #             in event_dict['event_record']['members'].items()
    #             for role, helper_2 in helper_1.items()
    #             for user_id in helper_2
    #         ]
    #     }
    #     param_dict.update(session.pop('next_page_param_dict', {}))

    #     return render_template('event_profile.html.jinja', **param_dict)

    # else:
    #     return redirect_to_default(event_dict['error_message'])

    return render_template('event_profile.html.jinja', event_id=event_id)


@app.route('/xhr/event_interaction/<event_id>', methods=['GET'])
def xhr_get_event_interaction(event_id):
    return json.jsonify(db_api.xhr_get_event_interaction(event_id))


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
