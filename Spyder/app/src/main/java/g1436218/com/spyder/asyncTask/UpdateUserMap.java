package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.backbone.JSONParser;
import g1436218.com.spyder.backbone.QueryExecutor;
import g1436218.com.spyder.object.UserMap;

public class UpdateUserMap extends AsyncTask<Void, Void, Void> {

    Activity activity;
    
    UserMap userMap = UserMap.getInstance();

    public UpdateUserMap(Activity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        userMap.updateList(JSONParser.toJSONObject(QueryExecutor.test()));
        return null;
    }
    
    @Override
    protected void onPostExecute(Void result) {
        TextView textView = (TextView) activity.findViewById(R.id.textView3);
        textView.setText(userMap.toString());
    }
}
