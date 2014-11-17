#!/usr/bin/env python3

# IMPORTANCE: in order to run this server, flask framework is required
# simply just execute "sudo pip3 install flask" to get it
# then execute this as normal python3 script

__author__ = 'Gun Pinyo (gunpinyo@gmail.com)'

from os import urandom

from flask import (
    Flask,
    request,
    session,
    # json,
    render_template,
    redirect,
    url_for
)

import db_api

app = Flask(__name__)


@app.route('/', methods=['GET'])
def index():
    if 'current_user_id' in session:
        return redirect(url_for('user_profile'))
    else:
        return redirect(url_for('welcome'))


@app.route('/static/<path:filepath>', methods=['GET'])
def serve_static(filepath):
    return app.send_static_file(filepath)


@app.route('/welcome', methods=['GET'])
@app.route('/welcome/error_message/<error_message>', methods=['GET'])
@app.route('/welcome/success_message/<success_message>', methods=['GET'])
def welcome(**input_dict):
    return render_template('welcome.html.jinja', **input_dict)


@app.route('/login', methods=['POST'])
def login():
    # TODO(gunpinyo): change to assertion to redirection
    assert ('current_user_id' not in session)
    print(request.form['username'] + 'iii' + request.form['password'])
    param_dict = {
        'username': request.form['username'],
        'hashed_password': request.form['password']
    }

    login_dict = db_api.login(**param_dict)

    if(login_dict['is_success']):
        session['current_user_id'] = login_dict['user_id']
        return redirect(url_for('user_profile'))
    else:
        return redirect(
            url_for('welcome', error_message=login_dict['error_message']))


@app.route('/logout', methods=['POST'])
def logout():
    # TODO(gunpinyo): change to assertion to redirection
    assert ('current_user_id' in session)

    session.pop('current_user_id')

    return redirect(url_for(
        'welcome', success_message='You have successfully signed out.'))


@app.route('/user/profile', methods=['GET'])
@app.route('/user/profile/<int:user_id>', methods=['GET'])
def user_profile(user_id=None):
    # TODO(gunpinyo): change to assertion to redirection
    assert ('current_user_id' in session)

    if user_id is None:
        user_id = session['current_user_id']

    param_dict = {
        'user_id': user_id,
        'user_record': db_api.get_user(user_id)
    }

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
    app.secret_key = urandom(40)

    app.run(host='0.0.0.0', port=55556, debug=True)
