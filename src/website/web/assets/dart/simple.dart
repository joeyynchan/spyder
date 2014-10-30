import 'dart:html';
import 'dart:convert';

var curContactIntervalJson = null;

void displayContactInterval(
        Map<String, Map<String, List<List<int>>>> contactIntervalJson) {
    var mainContent = querySelector('#main-content');
    var table = new Element.table();
    table.setAttribute('style', 'width: 100%; table-layout: fixed;');
    var tr = new Element.tr();
    var thFstUser = new Element.th();
    thFstUser.text = "First user's id";
    var thSndUser = new Element.th();
    thSndUser.text = "Second user's id";
    var thStart = new Element.th();
    thStart.text = 'Start';
    var thStop = new Element.th();
    thStop.text = 'Stop';
    tr.children = [thFstUser, thSndUser, thStart, thStop];
    table.children.add(tr);
    contactIntervalJson.forEach((fstUser, helperMap) {
//        window.alert('fstU $fstUser');
        helperMap.forEach((sndUser, intervals) {
//            window.alert('sndU $sndUser');
            bool isFirstInterval = true;
            for(var interval in intervals) {
//                window.alert('i $interval');
                var tr = new Element.tr();
                var tdFstUser = new Element.td();
                tdFstUser.text = isFirstInterval ? fstUser : '';
                var tdSndUser = new Element.td();
                tdSndUser.text = isFirstInterval ? sndUser : '';
                var tdStart = new Element.td();
                tdStart.text = interval[0].toString();
                var tdStop = new Element.td();
                tdStop.text = interval[1].toString();
                isFirstInterval = false;
                tr.children = [tdFstUser, tdSndUser, tdStart, tdStop];
                table.children.add(tr);
            }
        });
    });
    mainContent.children = [table];
}

void requestContactInterval(int conferenceId) {
    HttpRequest
        .getString('/ajax/contact-interval?conference_id=$conferenceId')
        .then((String respondText) {
            curContactIntervalJson = JSON.decode(respondText);
            window.alert(respondText);
            displayContactInterval(curContactIntervalJson);
        });
}

void main() {
    HttpRequest.getString('/ajax/conferences').then((String respondText) {
        var conferenceList = JSON.decode(respondText);
        var menuBar = querySelector('core-menu');
        conferenceList.forEach((int conferenceId, String conferenceName) {
            var newItem = new Element.tag('core-item');
            newItem.setAttribute('icon', 'account-child');
            newItem.setAttribute('label', conferenceName);
            newItem.onClick.listen((MouseEvent e) {
                requestContactInterval(conferenceId);
            });
            menuBar.children.add(newItem);
        });
    });
}
