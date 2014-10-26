#!/usr/bin/env python3

# IMPORTANCE: order to run this server bottle framework required
# simply just execute "sudo pip3 install bottle" to get it
# then execute this as normal python script

from bottle import route, run, template


@route('/')
def it_works():
    return '<h1>Hey, you see me.</br> YESss it work!!!</h1>'


@route('/hello')
@route('/hello/<name>')
def greet(name='anonymous'):
    return template('<b>Hello {{name}}</b>!', name=name)

run(host='localhost', port=8080)
