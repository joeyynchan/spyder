#!/usr/bin/env python3

# IMPORTANCE: in order to run this server, bottle framework is required
# simply just execute "sudo pip3 install bottle" to get it
# then execute this as normal python script

__author__ = 'Gun Pinyo (gunpinyo@gmail.com)'

from bottle import Bottle, static_file, response, request, run
import environment
import db_interface

app = Bottle()


@app.get('/')
def index():
    base_template = environment.JINJA_ENV.get_template(
        '/views/simple.html.jinja')
    return base_template.render({})


@app.get('/assets/<filepath:path>')
def serve_static_assets(filepath):
    return static_file(
        '/assets/'+filepath, root=environment.ROOT_PATH)


@app.get('/packages/<filepath:path>')
def serve_static_dart_package(filepath):
    return static_file(
        '/packages/'+filepath, root=environment.ROOT_PATH)


@app.get('/ajax/conferences')
def get_list_conferences():
    response.content_type = 'application/json'
    return db_interface.list_conferences()


# @app.get('/ajax/conference/<conference_id>/contact-list')
# def contact_list(conference_id):
#     response.content_type = 'application/json'
#     return db_interface.contact_list(conference_id)


@app.get('/ajax/contact-interval')
def contact_interval():
    response.content_type = 'application/json'
    return db_interface.contact_interval(int(request.query.conference_id))


run(app, host='localhost', port=8080, debug=True, reloader=True)
