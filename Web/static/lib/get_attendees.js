function getAttendees() 
{
	/*
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://146.169.46.38:8080/MongoDBWebapp/eventUsers?event_id=5463faffe4b0c72e310cff42", true );
    xmlHttp.send( null );
	var data = xmlHttp.responseText;
	alert(data);
	*/
	var data = '{"attendees":[{"name":"A","userid":"1","mac_address":"5463f8d4e4b0952cfce4d426"},' 
						   + '{"name":"B","userid":"2","mac_address":"5463f8d4e4b0952cfce4d426"},'
						   + '{"name":"C","userid":"3","mac_address":"5463f8d4e4b0952cfce4d426"},'
						   + '{"name":"D","userid":"4","mac_address":"5463f8d4e4b0952cfce4d426"},'
						   + '{"name":"E","userid":"5","mac_address":"5463f8d4e4b0952cfce4d426"}]}';
	var json = JSON.parse(data);
	return json.attendees;
}