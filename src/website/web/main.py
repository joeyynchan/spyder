#!/usr/bin/env python3

# IMPORTANCE: in order to run this server, bottle framework is required
# simply just execute "sudo pip3 install bottle" to get it
# then execute this as normal python script

__author__ = 'Gun Pinyo (gunpinyo@gmail.com)'

import bottle
import environment
import db_interface

app = bottle.Bottle()


@app.get('/')
def index():
    base_template = environment.JINJA_ENV.get_template('/views/simple.html')
    return base_template.render({})


@app.get('/assets/<filepath:path>')
def static_assets(filepath):
    return bottle.static_file('/assets/'+filepath, root=environment.ROOT_PATH)


@app.get('/ajax/conferences')
def list_conferences():
    return db_interface.list_conferences()


@app.get('/ajax/conference/<conference_id>/contact-list')
def contact_list(conference_id):
    return db_interface.contact_list(conference_id)


@app.get('/ajax/conference/<conference_id>/contact-interval')
def contact_interval(conference_id):
    return db_interface.contact_interval(conference_id)


bottle.run(app, host='localhost', port=8080, debug=True)
