package g1436218.com.spyder.backbone;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class QueryExecutor {

    private final static String URL   = "http://146.169.46.38:8080/MongoDBWebapp/eventUsers?event_id=545ad315e4b0f46082caaef3";

    private static JSONParser parser 	= JSONParser.getInstance();
    private static List<NameValuePair> params;

    // empty Constructor
    public QueryExecutor(){}

    public static String test() {
        //public static JSONObject login(String username, String password){
        params = new ArrayList<NameValuePair>();
        return parser.getStringFromUrl(URL, params);
        //return parser.getJSONFromUrl(URL, params);
    }


}
