#!/usr/bin/env python3

# IMPORTANCE: in order to run this server, flask framework is required
# simply just execute "sudo pip3 install flask" to get it
# then execute this as normal python3 script

__author__ = 'Gun Pinyo (gunpinyo@gmail.com)'

from flask import Flask, render_template, request, json
import db_interface

app = Flask(__name__)


@app.route('/', methods=['GET'])
def index():
    return render_template('simple.html.jinja')


@app.route('/static/<path:filepath>', methods=['GET'])
def serve_static(filepath):
    return app.send_static_file(filepath)


@app.route('/packages/<path:filepath>', methods=['GET'])
def serve_static_dart_package(filepath):
    return app.send_static_file('packages/' + filepath)


@app.route('/ajax/conferences', methods=['GET'])
def get_list_conferences():
    return json.jsonify(db_interface.list_conferences())


# @app.get('/ajax/conference/<conference_id>/contact-list')
# def contact_list(conference_id):
#     response.content_type = 'application/json'
#     return db_interface.contact_list(conference_id)


@app.route('/ajax/contact-interval', methods=['GET'])
def contact_interval():
    return json.jsonify(
        db_interface.contact_interval(
            int(request.args.get('conference_id', '-1'))))


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
