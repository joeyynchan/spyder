#!/usr/bin/env python3

# IMPORTANCE: in order to run this server, flask framework is required
# simply just execute "sudo pip3 install flask" to get it
# then execute this as normal python3 script

__author__ = 'Gun Pinyo (gunpinyo@gmail.com)'

from flask import (
    Flask,
    request,
    json,
    render_template,
    redirect,
    url_for
)

import db_api

app = Flask(__name__)


@app.route('/', methods=['GET'])
def index():
    return redirect(url_for('welcome'))


@app.route('/static/<path:filepath>', methods=['GET'])
def serve_static(filepath):
    return app.send_static_file(filepath)


@app.route('/welcome', methods=['GET'])
def welcome():
    return render_template('welcome.html')


@app.route('/login', methods=['POST'])
def login():
    input_dict = {
        'username': request.form['username'],
        'hashed_password': request.form['password']
    }

    login_dict = db_api.login(**input_dict)

    if(login_dict['is_success']):
        redirect
    return redirect(url_for('welcome'))


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


@app.route('/xhr/event/create', methods=['POST'])
def create_event():
    return json.jsonify(db_api.create_event(**request.get_json()))


@app.route('/xhr/event/get/<int:event_id>', methods=['GET'])
def get_event(event_id):
    return json.jsonify(db_api.get_event(event_id))

# @app.route('/xhr/user/create', methods=['POST']):
# def create_user(user_id):
#     return json.jsonify(db_api.create_())


@app.route('/xhr/user/get/<int:user_id>', methods=['GET'])
def get_user(user_id):
    return json.jsonify(db_api.get_user(user_id))


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=55556, debug=True)
