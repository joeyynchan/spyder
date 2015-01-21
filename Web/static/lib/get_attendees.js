function getAttendees() 
{
	/*
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://146.169.46.38:8080/MongoDBWebapp/eventUsers?event_id=5463faffe4b0c72e310cff42", true );
    xmlHttp.send( null );
	var data = xmlHttp.responseText;
	alert(data);
	*/
	var data = '{"user_mappings":[{"mac_address": "98:0D:2E:BD:E9:B7","name": "cherrie","user_name": "kuo"},' 
						   + '{"mac_address": "38:2D:D1:1B:09:2A","name": "demo001","user_name": "demo001"},'
						   + '{"mac_address": "98:0D:2E:BD:E9:B7","name": "Demo Test","user_name": "gun"},'
						   + '{"mac_address": "","name": "khoa","user_name": "khoa"},'
						   + '{"mac_address": "98:0D:2E:BD:E9:B7","name": "","user_name": "iPhone"}]}';
	var json = JSON.parse(data);
	return json.user_mappings;
}