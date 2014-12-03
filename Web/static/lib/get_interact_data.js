function getInteraction()
{
	/*
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://146.169.46.38:8080/MongoDBWebapp/event/interaction?event_id=5463faffe4b0c72e310cff42", true );
    xmlHttp.send( null );
	var data = xmlHttp.responseText;
	alert(data);
	*/
	var data = '{"event_end_time": "Tue Dec 02 01:51:33 GMT 2014","event_start_time": "Sun Nov 30 12:54:26 GMT 2014",' 
	           +'"interaction":[{"end_time": "Sun Nov 30 12:54:41 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Sun Nov 30 12:54:26 GMT 2014","user1": "kuo","user2": "khoa"},' 
							+  '{"end_time": "Sun Nov 30 12:56:53 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Sun Nov 30 12:56:14 GMT 2014","user1": "kuo","user2": "khoa"},' 
							+  '{"end_time": "Sun Nov 30 13:28:00 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Sun Nov 30 13:25:30 GMT 2014","user1": "demo002","user2": "iPhone"},' 
							+  '{"end_time": "Mon Dec 01 23:28:58 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Mon Dec 01 23:28:13 GMT 2014","user1": "demo001","user2": "iPhone"},' 
							+  '{"end_time": "Tue Dec 02 00:31:42 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Tue Dec 02 00:30:42 GMT 2014","user1": "demo001","user2": "iPhone"},' 
							+  '{"end_time": "Tue Dec 02 00:35:27 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Tue Dec 02 00:34:27 GMT 2014","user1": "demo001","user2": "iPhone"},' 
							+  '{"end_time": "Tue Dec 02 00:57:44 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Tue Dec 02 00:57:29 GMT 2014","user1": "demo001","user2": "iPhone"},' 
							+  '{"end_time": "Tue Dec 02 01:45:44 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Tue Dec 02 01:45:14 GMT 2014","user1": "demo001","user2": "iPhone"},' 
							+  '{"end_time": "Tue Dec 02 01:51:33 GMT 2014","event_id": "54775c5de4b0598ae9308641","start_time": "Tue Dec 02 01:50:19 GMT 2014","user1": "demo001","user2": "iPhone"}]}'; 

	var json = JSON.parse(data);
	return json;
}