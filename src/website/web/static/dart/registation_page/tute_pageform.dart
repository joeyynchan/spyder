// Copyright (c) 2012, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:html';
import 'dart:convert';
import 'package:polymer/polymer.dart';

@CustomTag('tute-page-form')
class PageComponent extends FormElement with Polymer, Observable {
  
  PageComponent.created() : super.created() { 
    polymerCreated();
  }
  
  @observable String name = "username";
  
  // This map contains the rest of the forma data.
  @observable Map theData = toObservable({
    'fullName':       'fullname',
    'username':       'username',
    'email':          'email',
    'password':       'password',
    'repeatPassword': 'repeatPassword',
    'birthday':       '1993-08-30'
    // Add favoriteThings later...can't do it here...there is no this.
  });

  @observable String serverResponse = '';
  HttpRequest request;

  void submitForm(Event e, var detail, Node target) {
    e.preventDefault(); // Don't do the default submit.
       
    request = new HttpRequest();
    
    request.onReadyStateChange.listen(onData); 
    
    // POST the data to the server.
    var url = 'http://127.0.0.1:4040';
    request.open('POST', url);
    request.send(_pageAsJsonData());
  }
  
  void onData(_) {
    if (request.readyState == HttpRequest.DONE &&
        request.status == 200) {
      // Data saved OK.
      serverResponse = 'Server Sez: ' + request.responseText;
    } else if (request.readyState == HttpRequest.DONE &&
        request.status == 0) {
      // Status is 0...most likely the server isn't running.
      serverResponse = 'No server';
    }
  }
   
  void resetForm(Event e, var detail, Node target) {
    e.preventDefault(); // Default behavior clears elements,
                        // but bound values don't follow
                        // so have to do this explicitly.
    
    theData['fullName'] = '';
    theData['username'] = '';
    theData['email'] = '';
    theData['password'] = '';
    theData['repeatPassword'] = '';
    theData['birthday'] = '1993-01-01';
    serverResponse = 'Data cleared.';
  }
  
  String _pageAsJsonData() {
    // Put favoriteThings in the map.
    return JSON.encode(theData);
  }
}
